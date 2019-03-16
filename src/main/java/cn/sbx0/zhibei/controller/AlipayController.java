package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.config.AlipayConfig;
import cn.sbx0.zhibei.entity.Alipay;
import cn.sbx0.zhibei.entity.Product;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.entity.Wallet;
import cn.sbx0.zhibei.service.AlipayService;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.ProductService;
import cn.sbx0.zhibei.service.WalletService;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * alipay 控制层
 */
@Controller
@RequestMapping(value = "/alipay")
public class AlipayController extends BaseController<Alipay, Integer> {
    @Resource
    private AlipayService alipayService;
    @Resource
    private ProductService productService;
    @Resource
    private WalletService walletService;
    @Resource
    private AlipayConfig alipayConfig;

    @Override
    public BaseService<Alipay, Integer> getService() {
        return alipayService;
    }

    @Autowired
    public AlipayController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 付款
     *
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping("/pay")
    public ObjectNode alipayTradePagePay(Product product) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        Alipay alipay = new Alipay();
        if (product == null || product.getId() == null) {
            json.put(STATUS_NAME, STATUS_CODE_PARAMETER_ERROR);
            return json;
        }
        product = productService.findById(product.getId());
        if (product == null) {
            json.put(STATUS_NAME, STATUS_CODE_PARAMETER_ERROR);
            return json;
        }
        alipay.addProduct(product);
        alipay.setOutTradeNo(alipayService.createTradeNo());
        alipay.setCreateTime(new Date());
        alipay.setBuyer(user);
        alipay.setCreateTime(new Date());
        alipay.setType("alipay");
        // 获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(), alipayConfig.getMerchantPrivateKey(), "json", alipayConfig.getCharset(), alipayConfig.getAlipayPublicKey(), alipayConfig.getSignType());
        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfig.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
        try {
            alipayRequest.setBizContent("{\"out_trade_no\":\"" + alipay.getOutTradeNo() + "\","
                    + "\"total_amount\":\"" + alipay.getRealPay() + "\","
                    + "\"subject\":\"" + alipay.getName() + "\","
                    + "\"body\":\"" + alipay.getDesc() + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            if (alipayService.save(alipay)) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
            }
            json.put("result", result);
        } catch (Exception e) {
            json.put(STATUS_NAME, STATUS_CODE_EXCEPTION);
            json.put("msg", e.getMessage());
        }
        return json;
    }

    /**
     * 支付宝服务器同步通知页面
     *
     * @return
     */
    @LogRecord
    @GetMapping("/return")
    public String alipayReturn(Map<String, String> params) {
        User user = userService.getUser();
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
                Alipay alipay = alipayService.findByOutTradeNo(outTradeNo);
                if (alipay == null) {
                    return "error";
                }
                alipay.setTradeNo(tradeNo);
                alipay.setAmount(Double.parseDouble(totalAmount));
                alipay.setEndTime(new Date());
                if (alipayService.save(alipay)) {
                    Wallet wallet = walletService.getUserWallet(user);
                    wallet.setMoney(wallet.getMoney() + alipay.getRealTotal());
                    if (walletService.save(wallet)) {
                        params.put("status", "success");
                    } else {
                        params.put("status", "failed");
                    }
                    params.put("username", user.getName());
                    params.put("nickname", user.getNickname());
                    params.put("out_trade_no", alipay.getOutTradeNo());
                    params.put("trade_no", alipay.getTradeNo());
                    params.put("total_amount", alipay.getAmount().toString());
                    params.put("wallet_money", wallet.getMoney().toString());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH mm:ss");
                    params.put("create_time", simpleDateFormat.format(alipay.getCreateTime()));
                    params.put("end_time", simpleDateFormat.format(alipay.getEndTime()));
                } else {
                    params.put("status", "failed");
                }
            } else {
                params.put("status", "failed");
            }
        } catch (Exception e) {
            params.put("status", "failed");
            params.put("e_msg", e.getMessage());
        }
        return "alipay";
    }

    /**
     * 支付宝服务器异步通知
     *
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping("/notify")
    public ObjectNode alipayNotify(Map<String, String> params) {
        // 获取支付宝POST过来反馈信息
        json = mapper.createObjectNode();
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
