package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TechnicalAchievementsAndAddressBindService extends BaseService<TechnicalAchievementsAndAddressBind, Integer> {
    @Resource
    private TechnicalAchievementsAndAddressBindDao dao;

    @Override
    public PagingAndSortingRepository<TechnicalAchievementsAndAddressBind, Integer> getDao() {
        return dao;
    }

    @Override
    public TechnicalAchievementsAndAddressBind getEntity() {
        return new TechnicalAchievementsAndAddressBind();
    }

    @Override
    public boolean checkDataValidity(TechnicalAchievementsAndAddressBind TechnicalAchievementsAndAddressBind) {
        return true;
    }
}
