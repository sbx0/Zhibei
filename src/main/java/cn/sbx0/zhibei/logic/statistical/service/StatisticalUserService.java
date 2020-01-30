package cn.sbx0.zhibei.logic.statistical.service;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.statistical.dao.StatisticalUserDao;
import cn.sbx0.zhibei.logic.statistical.entity.StatisticalUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StatisticalUserService extends BaseService<StatisticalUser, Integer> {
    @Resource
    private StatisticalUserDao dao;

    @Override
    public PagingAndSortingRepository<StatisticalUser, Integer> getDao() {
        return dao;
    }

    @Override
    public StatisticalUser getEntity() {
        return new StatisticalUser();
    }
}
