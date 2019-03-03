package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.VerifyDao;
import cn.sbx0.zhibei.entity.Verify;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 验证 服务层
 */
@Service
public class VerifyService extends BaseService<Verify, Integer> {
    @Resource
    private VerifyDao verifyDao;

    @Override
    public PagingAndSortingRepository<Verify, Integer> getDao() {
        return verifyDao;
    }

    @Override
    public Verify getEntity() {
        return new Verify();
    }

    /**
     * 根据md5寻找验证
     *
     * @param md5
     * @return
     */
    public Verify existsByMd5(String md5) {
        return verifyDao.existsByMd5(md5);
    }
}
