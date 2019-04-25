package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjectDao extends PagingAndSortingRepository<Project, Integer> {
    /**
     * 根据申请人查询
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query(value = "FROM Project p WHERE p.applicant.id = ?1")
    Page<Project> findByApplicant(Integer id, Pageable pageable);

    @Query(value = "SELECT 1 FROM Project p WHERE p.applicant.id = ?1 AND p.demand.id = ?2")
    String existsByApplicantAndDemand(Integer id, Integer d_id);
}
