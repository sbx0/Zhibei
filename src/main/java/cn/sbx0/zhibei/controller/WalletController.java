package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.entity.Wallet;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 钱包 控制层
 */
@Controller
@RequestMapping("/wallet")
public class WalletController extends BaseController<Wallet, Integer> {
    @Resource
    private WalletService walletService;

    @Override
    public BaseService<Wallet, Integer> getService() {
        return walletService;
    }

    @Autowired
    public WalletController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 获取钱包余额
     *
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/my")
    public ObjectNode alipayTradePagePay() {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        Wallet wallet = walletService.getUserWallet(user);
        if (wallet != null) {
            json.put("user_id", user.getId());
            json.put("money", wallet.getMoney());
            json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        } else {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
        }
        return json;
    }
}
