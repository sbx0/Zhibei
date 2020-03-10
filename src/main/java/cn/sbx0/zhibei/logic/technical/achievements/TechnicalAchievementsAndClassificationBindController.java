package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/technical/achievements/classification/bind")
public class TechnicalAchievementsAndClassificationBindController extends BaseController<TechnicalAchievementsAndClassificationBind, Integer> {
    @Resource
    private TechnicalAchievementsAndClassificationBindService service;

    @Override
    public BaseService<TechnicalAchievementsAndClassificationBind, Integer> getService() {
        return service;
    }
}
