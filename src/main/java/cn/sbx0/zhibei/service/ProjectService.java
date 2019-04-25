package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.ProjectDao;
import cn.sbx0.zhibei.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProjectService extends BaseService<Project, Integer> {
    @Resource
    private ProjectDao projectDao;

    @Override
    public PagingAndSortingRepository<Project, Integer> getDao() {
        return projectDao;
    }

    @Override
    public Project getEntity() {
        return new Project();
    }

    public Page<Project> findBySponsor(Integer id, Pageable pageable) {
        return projectDao.findBySponsor(id, pageable);
    }

    public Page<Project> findByApplicant(Integer id, Pageable pageable) {
        return projectDao.findByApplicant(id, pageable);
    }

    public boolean existsByApplicantAndDemand(Integer id, Integer d_id) {
        String result = projectDao.existsByApplicantAndDemand(id, d_id);
        return result != null;
    }
}
