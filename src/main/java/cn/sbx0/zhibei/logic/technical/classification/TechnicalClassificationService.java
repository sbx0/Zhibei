package cn.sbx0.zhibei.logic.technical.classification;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TechnicalClassificationService extends BaseService<TechnicalClassification, Integer> {
    @Resource
    private TechnicalClassificationDao dao;

    @Override
    public PagingAndSortingRepository<TechnicalClassification, Integer> getDao() {
        return dao;
    }

    @Override
    public TechnicalClassification getEntity() {
        return new TechnicalClassification();
    }

    @Override
    public boolean checkDataValidity(TechnicalClassification TechnicalClassification) {
        return true;
    }
}
