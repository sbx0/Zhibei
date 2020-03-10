package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/technical/achievements")
public class TechnicalAchievementsController extends BaseController<TechnicalAchievements, Integer> {
    @Resource
    private TechnicalAchievementsService service;

    @Override
    public BaseService<TechnicalAchievements, Integer> getService() {
        return service;
    }
}
