package cn.sbx0.zhibei.logic.alipay;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wallet/base")
public class WalletBaseController extends BaseController<WalletBase, Integer> {
    @Resource
    private WalletBaseService service;
    @Resource
    private UserBaseService userBaseService;

    @Override
    public BaseService<WalletBase, Integer> getService() {
        return service;
    }

    @LoginRequired
    @GetMapping(value = "/my")
    public ObjectNode my() {
        ObjectNode json = initJSON();
        Integer userId = userBaseService.getLoginUserId();
        WalletBase walletBase = service.getUserWallet(userId);
        if (walletBase == null) {
            walletBase = new WalletBase();
            walletBase.setMoney(0.00);
            walletBase.setUserId(userId);
            walletBase.setFinished(false);
            service.save(walletBase);
        }
        json.set(jsonOb, service.convertToJson(walletBase));
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }
}
