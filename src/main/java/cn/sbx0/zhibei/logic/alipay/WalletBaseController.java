package cn.sbx0.zhibei.logic.alipay;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wallet/base")
public class WalletBaseController extends BaseController<WalletBase, Integer> {
    @Resource
    private WalletBaseService service;

    @Override
    public BaseService<WalletBase, Integer> getService() {
        return service;
    }
}
