package cn.sbx0.zhibei.logic.technical.project;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProjectBaseService extends BaseService<ProjectBase, Integer> {
    @Resource
    private ProjectBaseDao dao;

    @Override
    public PagingAndSortingRepository<ProjectBase, Integer> getDao() {
        return dao;
    }

    @Override
    public ProjectBase getEntity() {
        return new ProjectBase();
    }

    @Override
    public boolean checkDataValidity(ProjectBase o) {
        return true;
    }

    public List<ProjectBase> findAllByUserId(Integer userId) {
        return dao.findAllByUserId(userId);
    }
}