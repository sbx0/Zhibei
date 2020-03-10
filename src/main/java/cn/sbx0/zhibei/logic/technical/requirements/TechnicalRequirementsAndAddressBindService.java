package cn.sbx0.zhibei.logic.technical.requirements;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TechnicalRequirementsAndAddressBindService extends BaseService<TechnicalRequirementsAndAddressBind, Integer> {
    @Resource
    private TechnicalRequirementsAndAddressBindDao dao;

    @Override
    public PagingAndSortingRepository<TechnicalRequirementsAndAddressBind, Integer> getDao() {
        return dao;
    }

    @Override
    public TechnicalRequirementsAndAddressBind getEntity() {
        return new TechnicalRequirementsAndAddressBind();
    }

    @Override
    public boolean checkDataValidity(TechnicalRequirementsAndAddressBind TechnicalRequirementAndAddressBind) {
        return true;
    }
}
