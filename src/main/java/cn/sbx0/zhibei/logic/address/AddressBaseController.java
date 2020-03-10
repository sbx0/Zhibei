package cn.sbx0.zhibei.logic.address;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/address/base")
public class AddressBaseController extends BaseController<AddressBase, Integer> {
    @Resource
    private AddressBaseService service;

    @Override
    public BaseService<AddressBase, Integer> getService() {
        return service;
    }
}
