package cn.sbx0.zhibei.logic.technical.application;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApplicationBaseService extends BaseService<ApplicationBase, Integer> {
    @Resource
    private ApplicationBaseDao dao;

    @Override
    public PagingAndSortingRepository<ApplicationBase, Integer> getDao() {
        return dao;
    }

    @Override
    public ApplicationBase getEntity() {
        return new ApplicationBase();
    }

    @Override
    public boolean checkDataValidity(ApplicationBase o) {
        return true;
    }

    public List<ApplicationBase> findAllByUserId(Integer userId) {
        return dao.findAllByUserId(userId);
    }

    public List<ApplicationBase> findAllByReceive(Integer userId) {
        return dao.findAllByReceive(userId);
    }

    public List<ApplicationBase> findAllByApplicant(Integer userId) {
        return dao.findAllByApplicant(userId);
    }

    public List<ApplicationBase> findAllByIng(Integer userId) {
        return dao.findAllByIng(userId);
    }

    public List<ApplicationBase> findAllByReg(Integer userId) {
        return dao.findAllByReg(userId);
    }
}