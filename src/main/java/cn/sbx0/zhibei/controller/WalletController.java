package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Wallet;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
