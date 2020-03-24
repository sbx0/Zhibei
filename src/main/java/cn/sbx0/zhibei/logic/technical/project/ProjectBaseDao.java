package cn.sbx0.zhibei.logic.technical.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProjectBaseDao extends PagingAndSortingRepository<ProjectBase, Integer> {
    @Query(value = "select * from project_base where receive_id = ?1 or applicant_id = ?1", nativeQuery = true)
    List<ProjectBase> findAllByUserId(Integer id);
}
