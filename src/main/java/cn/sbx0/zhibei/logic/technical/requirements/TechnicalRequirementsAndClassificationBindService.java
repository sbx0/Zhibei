package cn.sbx0.zhibei.logic.technical.requirements;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TechnicalRequirementsAndClassificationBindService extends BaseService<TechnicalRequirementsAndClassificationBind, Integer> {
    @Resource
    private TechnicalRequirementsAndClassificationBindDao dao;

    @Override
    public PagingAndSortingRepository<TechnicalRequirementsAndClassificationBind, Integer> getDao() {
        return dao;
    }

    @Override
    public TechnicalRequirementsAndClassificationBind getEntity() {
        return new TechnicalRequirementsAndClassificationBind();
    }

    @Override
    public boolean checkDataValidity(TechnicalRequirementsAndClassificationBind TechnicalRequirementAndClassificationBind) {
        return true;
    }
}
