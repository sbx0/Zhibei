package cn.sbx0.zhibei.logic.technical.requirements;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/technical/requirements/classification/bind")
public class TechnicalRequirementsAndClassificationBindController extends BaseController<TechnicalRequirementsAndClassificationBind, Integer> {
    @Resource
    private TechnicalRequirementsAndClassificationBindService service;

    @Override
    public BaseService<TechnicalRequirementsAndClassificationBind, Integer> getService() {
        return service;
    }
}
