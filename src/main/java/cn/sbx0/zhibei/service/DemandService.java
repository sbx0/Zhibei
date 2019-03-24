package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.DemandDao;
import cn.sbx0.zhibei.entity.Demand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * 根据用户名查询需求
     *
     * @param id
     * @param buildPageable
     * @return
     */
    public Page<Demand> findByPoster(Integer id, Pageable buildPageable) {
        return demandDao.findByPoster(id, buildPageable);
    }

    /**
     * 根据标签查询需求
     *
     * @param id
     * @param buildPageable
     * @return
     */
    public Page<Demand> findByTag(Integer id, Pageable buildPageable) {
        return demandDao.findByTag(id, buildPageable);
    }
}
