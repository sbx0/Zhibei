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
}