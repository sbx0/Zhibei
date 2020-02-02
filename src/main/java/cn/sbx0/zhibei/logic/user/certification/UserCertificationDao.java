package cn.sbx0.zhibei.logic.user.certification;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 用户认证 数据层
 */
public interface UserCertificationDao extends PagingAndSortingRepository<UserCertification, Integer> {
    @Query(nativeQuery = true, value = "select * from user_certification order by submit_time desc limit 1")
    UserCertification findByUserAndNewest(Integer id);

    @Query(nativeQuery = true, value = "select * from user_certification where user_id = ?1 and kind = ?2 and status = ?3")
    List<UserCertification> findByUserAndKindAndStatus(Integer id, String kind, String status);

    List<UserCertification> findAll();
}