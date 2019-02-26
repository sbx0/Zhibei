package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.DemandDao;
import cn.sbx0.zhibei.entity.Demand;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 需求 服务层
 */
@Service
public class DemandService extends BaseService<Demand, Integer> {
    @Resource
    private DemandDao demandDao;

    @Override
    public PagingAndSortingRepository<Demand, Integer> getDao() {
        return demandDao;
    }

    @Override
    public Demand getEntity() {
        return new Demand();
    }

}
