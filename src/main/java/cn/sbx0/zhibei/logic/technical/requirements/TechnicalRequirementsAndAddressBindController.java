package cn.sbx0.zhibei.logic.technical.requirements;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/technical/requirements/address/bind")
public class TechnicalRequirementsAndAddressBindController extends BaseController<TechnicalRequirementsAndAddressBind, Integer> {
    @Resource
    private TechnicalRequirementsAndAddressBindService service;

    @Override
    public BaseService<TechnicalRequirementsAndAddressBind, Integer> getService() {
        return service;
    }
}
