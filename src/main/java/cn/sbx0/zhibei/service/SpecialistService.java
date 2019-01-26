package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.SpecialistDao;
import cn.sbx0.zhibei.entity.Specialist;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 专家 服务层
 */
@Service
public class SpecialistService extends BaseService<Specialist, Integer> {
    @Resource
    SpecialistDao specialistDao;

    @Override
    public PagingAndSortingRepository<Specialist, Integer> getDao() {
        return specialistDao;
    }
}
