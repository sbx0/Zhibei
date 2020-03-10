package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/technical/achievements/address/bind")
public class TechnicalAchievementsAndAddressBindController extends BaseController<TechnicalAchievementsAndAddressBind, Integer> {
    @Resource
    private TechnicalAchievementsAndAddressBindService service;

    @Override
    public BaseService<TechnicalAchievementsAndAddressBind, Integer> getService() {
        return service;
    }
}
