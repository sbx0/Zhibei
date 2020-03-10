package cn.sbx0.zhibei.logic.technical.requirements;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/technical/requirements")
public class TechnicalRequirementsController extends BaseController<TechnicalRequirements, Integer> {
    @Resource
    private TechnicalRequirementsService service;

    @Override
    public BaseService<TechnicalRequirements, Integer> getService() {
        return service;
    }
}
