package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TechnicalAchievementsService extends BaseService<TechnicalAchievements, Integer> {
    @Resource
    private TechnicalAchievementsDao dao;

    @Override
    public PagingAndSortingRepository<TechnicalAchievements, Integer> getDao() {
        return dao;
    }

    @Override
    public TechnicalAchievements getEntity() {
        return new TechnicalAchievements();
    }

    @Override
    public boolean checkDataValidity(TechnicalAchievements technicalAchievements) {
        return true;
    }
}
