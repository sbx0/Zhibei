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

    @Override
    public Certification getEntity() {
        return new Certification();
    }

    /**
     * 判断是否有已提交但尚未审核的认证申请
     *
     * @param userId
     * @return
     */
    public boolean existsByUserAndPassed(Integer userId) {
        String result = certificationDao.existsByUserAndPassed(userId);
        return result != null;
    }

    /**
     * 获取是否有已提交但尚未审核的认证申请
     *
     * @param userId
     * @return
     */
    public Certification findByUserAndPassed(Integer userId) {
        Certification certification = certificationDao.findByUserAndPassed(userId);
        return certification;
    }
}
