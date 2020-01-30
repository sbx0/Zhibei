package cn.sbx0.zhibei.logic.statistical.service;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.statistical.dao.StatisticalDataDao;
import cn.sbx0.zhibei.logic.statistical.entity.StatisticalData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StatisticalDataService extends BaseService<StatisticalData, Integer> {
    @Resource
    private StatisticalDataDao dao;

    @Override
    public PagingAndSortingRepository<StatisticalData, Integer> getDao() {
        return dao;
    }

    @Override
    public StatisticalData getEntity() {
        return new StatisticalData();
    }
}
