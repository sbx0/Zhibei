package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.CertificationDao;
import cn.sbx0.zhibei.entity.Certification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 认证 服务层
 */
@Service
public class CertificationService extends BaseService<Certification, Integer> {
    @Resource
    private CertificationDao certificationDao;

    @Override
    public PagingAndSortingRepository<Certification, Integer> getDao() {
        return certificationDao;
    }
}
