package cn.sbx0.zhibei.logic.technical.requirements;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TechnicalRequirementsService extends BaseService<TechnicalRequirements, Integer> {
    @Resource
    private TechnicalRequirementsDao dao;

    @Override
    public PagingAndSortingRepository<TechnicalRequirements, Integer> getDao() {
        return dao;
    }

    @Override
    public TechnicalRequirements getEntity() {
        return new TechnicalRequirements();
    }

    @Override
    public boolean checkDataValidity(TechnicalRequirements TechnicalRequirements) {
        return true;
    }
}
