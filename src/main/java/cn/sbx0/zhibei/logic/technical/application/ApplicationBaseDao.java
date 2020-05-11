package cn.sbx0.zhibei.logic.technical.application;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ApplicationBaseDao extends PagingAndSortingRepository<ApplicationBase, Integer> {
    @Query(value = "select * from application_base where receive_id = ?1 or applicant_id = ?1", nativeQuery = true)
    List<ApplicationBase> findAllByUserId(Integer id);

    @Query(value = "select * from application_base where receive_id = ?1 and status = 0 order by quote desc", nativeQuery = true)
    List<ApplicationBase> findAllByReceive(Integer id);

    @Query(value = "select * from application_base where applicant_id = ?1 and status = 0", nativeQuery = true)
    List<ApplicationBase> findAllByApplicant(Integer id);

    @Query(value = "select * from application_base where (receive_id = ?1 or applicant_id = ?1) and status = 1", nativeQuery = true)
    List<ApplicationBase> findAllByIng(Integer id);

    @Query(value = "select * from application_base where applicant_id = ?1 and status = 2", nativeQuery = true)
    List<ApplicationBase> findAllByReg(Integer id);
}
