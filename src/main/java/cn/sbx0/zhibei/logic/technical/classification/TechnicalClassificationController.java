package cn.sbx0.zhibei.logic.technical.classification;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/technical/classification")
public class TechnicalClassificationController extends BaseController<TechnicalClassification, Integer> {
    @Resource
    private TechnicalClassificationService service;

    @Override
    public BaseService<TechnicalClassification, Integer> getService() {
        return service;
    }
}
