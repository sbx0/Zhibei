package cn.sbx0.zhibei.controller;

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
     * 支持某人 为其打钱
     *
     * @param id
     * @param money
     * @return
     */
    @ResponseBody
    @GetMapping("/support")
    public ObjectNode support(Integer id, Double money) {
        json = mapper.createObjectNode();
        User to = userService.findById(id);
        User user = userService.getUser();
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        if (to == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
            return json;
        }
        if (money < 1 || money > 10000) {
            json.put(STATUS_NAME, STATUS_CODE_PARAMETER_ERROR);
            return json;
        }
        Wallet userWallet = walletService.getUserWallet(user);
        if (userWallet.getMoney() < money) {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
            return json;
        }
        Wallet toWallet = walletService.getUserWallet(to);
        userWallet.setMoney(userWallet.getMoney() - money);
        if (walletService.save(userWallet)) {
            toWallet.setMoney(toWallet.getMoney() + money);
            if (walletService.save(toWallet)) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
        }
        return json;
    }

    /**
     * 获取钱包余额
     *
     * @return
     */
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
