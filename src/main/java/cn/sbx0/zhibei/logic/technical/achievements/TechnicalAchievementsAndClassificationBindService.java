package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TechnicalAchievementsAndClassificationBindService extends BaseService<TechnicalAchievementsAndClassificationBind, Integer> {
    @Resource
    private TechnicalAchievementsAndClassificationBindDao dao;

    @Override
    public PagingAndSortingRepository<TechnicalAchievementsAndClassificationBind, Integer> getDao() {
        return dao;
    }

    @Override
    public TechnicalAchievementsAndClassificationBind getEntity() {
        return new TechnicalAchievementsAndClassificationBind();
    }

    @Override
    public boolean checkDataValidity(TechnicalAchievementsAndClassificationBind TechnicalAchievementsAndClassificationBind) {
        return true;
    }
}
