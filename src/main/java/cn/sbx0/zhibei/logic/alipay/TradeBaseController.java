package cn.sbx0.zhibei.logic.alipay;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import cn.sbx0.zhibei.logic.user.info.UserInfo;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/trade/base")
public class TradeBaseController extends BaseController<TradeBase, Integer> {
    @Resource
    private TradeBaseService service;
    @Resource
    private UserBaseService userBaseService;

    @Override
    public BaseService<TradeBase, Integer> getService() {
        return service;
    }

    /**
     * 充值
     *
     * @return
     */
    @LoginRequired
    @GetMapping(value = "/recharge")
    public ObjectNode recharge(Double price) {
        ObjectNode json = initJSON();
        if (price <= 0.00) {
            json.put(statusCode, ReturnStatus.invalidValue.getCode());
            return json;
        }
        UserInfo user = userBaseService.getLoginUser();
        TradeBase tradeBase = new TradeBase();
        tradeBase.setProductId("recharge");
        tradeBase.setPrice(price);
        tradeBase.setDiscount(1.00);
        tradeBase.setDiscountType("none");
        tradeBase.setNumbers(1);
        tradeBase.setTradeNo(service.createTradeNo());
        if (service.save(tradeBase) != null) {
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.failed.getCode());
        }
        json.put(jsonOb, tradeBase.getTradeNo());
        return json;
    }
}
