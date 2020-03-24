package cn.sbx0.zhibei.logic.alipay;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.config.AlipayConfig;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import cn.sbx0.zhibei.logic.user.info.UserInfo;
import cn.sbx0.zhibei.tool.StringTools;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/alipay")
public class AlipayBaseController extends BaseController<AlipayBase, Integer> {
    @Resource
    private AlipayBaseService service;
    @Resource
    private UserBaseService userBaseService;
    @Resource
    private TradeBaseService tradeBaseService;
    @Resource
    private WalletBaseService walletBaseService;
    @Resource
    private AlipayConfig alipayConfig;

    @Override
    public BaseService<AlipayBase, Integer> getService() {
        return service;
    }

    /**
     * 先创建订单 -》 带着订单号来付款
     *
     * @param tradeNo
     * @return
     */
    @ResponseBody
    @LoginRequired
    @PostMapping(value = "/pay")
    public ObjectNode pay(String tradeNo) {
        ObjectNode json = initJSON();
        UserInfo user = userBaseService.getLoginUser();
        AlipayBase alipayBase = new AlipayBase();
        if (!StringTools.checkNotEmail(tradeNo)) {
            json.put(statusCode, ReturnStatus.invalidValue.getCode());
            return json;
        }
        alipayBase.setOutTradeNo(tradeNo);
        alipayBase.setCreateTime(new Date());
        alipayBase.setBuyerId(user.getUserId());
        alipayBase.setFinished(false);
        // 通过订单号找到订单与商品的绑定，
        List<TradeBase> tradeBases = tradeBaseService.findByTradeNo(tradeNo);
        // 从而计算金额
        alipayBase.setAmount(tradeBaseService.countAmount(tradeBases));
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getMerchantPrivateKey(), "json", alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
        try {
            alipayRequest.setBizContent("{\"out_trade_no\":\"" + alipayBase.getOutTradeNo() + "\","
                    + "\"total_amount\":\"" + alipayBase.getAmount() + "\","
                    + "\"subject\":\"" + alipayBase.getOutTradeNo() + "\","
                    + "\"body\":\"" + alipayBase.getOutTradeNo() + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            if (service.save(alipayBase) != null) {
                json.put(statusCode, ReturnStatus.success.getCode());
            } else {
                json.put(statusCode, ReturnStatus.failed.getCode());
            }
            json.put(jsonOb, result);
        } catch (Exception e) {
            json.put(statusCode, ReturnStatus.exception.getCode());
            json.put(statusMsg, e.getMessage());
        }
        return json;
    }

    /**
     * 支付宝服务器同步通知页面
     *
     * @return
     */
    @GetMapping("/return")
    public String alipayReturn(Map<String, String> params) {
        UserInfo user = userBaseService.getLoginUser();
        if (user == null) {
            return "error";
        }
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }
        try {
            // 调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
            if (signVerified) {
                // 商户订单号
                String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                // 支付宝交易号
                String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
                // 付款金额
                String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
                AlipayBase alipayBase = service.findByOutTradeNo(outTradeNo);
                WalletBase wallet = new WalletBase();
                if (alipayBase == null) {
                    return "error";
                } else if (!alipayBase.getFinished()) {
                    alipayBase.setTradeNo(tradeNo);
                    alipayBase.setAmount(Double.parseDouble(totalAmount));
                    alipayBase.setEndTime(new Date());
                    alipayBase.setFinished(true);
                    if (service.save(alipayBase) != null) {
                        wallet = walletBaseService.getUserWallet(user.getUserId());
                        wallet.setMoney(wallet.getMoney() + alipayBase.getAmount());
                        if (walletBaseService.save(wallet) != null) {
                            params.put("status", "success");
                        } else {
                            params.put("status", "failed");
                        }
                    } else {
                        params.put("status", "failed");
                    }
                } else {
                    wallet = walletBaseService.getUserWallet(user.getUserId());
                }
                params.put("username", user.getUserId() + "");
                params.put("nickname", user.getEmail());
                params.put("out_trade_no", alipayBase.getOutTradeNo());
                params.put("trade_no", alipayBase.getTradeNo());
                params.put("total_amount", alipayBase.getAmount().toString());
                params.put("wallet_money", wallet.getMoney().toString());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH mm:ss");
                params.put("create_time", simpleDateFormat.format(alipayBase.getCreateTime()));
                params.put("end_time", simpleDateFormat.format(alipayBase.getEndTime()));
                params.put("status", "success");
            } else {
                params.put("status", "failed");
            }
        } catch (Exception e) {
            params.put("status", "failed");
            params.put("e_msg", e.getMessage());
        }
        return "alipay";
    }

    @PostMapping("/notify")
    public ObjectNode alipayNotify(Map<String, String> params) {
        // 获取支付宝POST过来反馈信息
        ObjectNode json = initJSON();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            try {
                // 乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }
        try {
            // 调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
            // ——请在这里编写您的程序（以下代码仅作参考）——
            /**
             * 实际验证过程建议商户务必添加以下校验：
             * 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
             * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
             * 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
             * 4、验证app_id是否为该商户本身。
             *
             */
            // 验证成功
            if (signVerified) {
                // 商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                // 支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
                // 交易状态
                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                json.put("out_trade_no", out_trade_no);
                json.put("trade_no", trade_no);
                json.put("trade_status", trade_status);
                if (trade_status.equals("TRADE_FINISHED")) {
                    // 判断该笔订单是否在商户网站中已经做过处理
                    // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    // 如果有做过处理，不执行商户的业务程序
                    // 注意：
                    // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    // 判断该笔订单是否在商户网站中已经做过处理
                    // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    // 如果有做过处理，不执行商户的业务程序
                    // 注意：
                    // 付款完成后，支付宝系统发送该交易状态通知
                } else {
                    // 验证失败
                    System.out.println("fail");
                    // 调试用，写文本函数记录程序运行情况是否正常
                    // String sWord = AlipaySignature.getSignCheckContentV1(params);
                    // AlipayConfig.logResult(sWord);
                }
                // ——请在这里编写您的程序（以上代码仅作参考）——
                System.out.println("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
