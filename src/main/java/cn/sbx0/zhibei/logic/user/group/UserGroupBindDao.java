package cn.sbx0.zhibei.logic.user.group;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 用户组与用户绑定 数据层
 */
public interface UserGroupBindDao extends PagingAndSortingRepository<UserGroupBind, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM user_group_bind WHERE group_id = ?1 and user_id = ?2 limit 1")
    UserGroupBind findByGroupAndUser(Integer groupId, Integer userId);

    @Query(nativeQuery = true, value = "SELECT id FROM user_group_bind WHERE group_id = ?1 and user_id = ?2 limit 1")
    Integer findIdByGroupAndUser(Integer groupId, Integer userId);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM user_group_bind WHERE group_id = ?1 and validity_time > NOW()")
    Integer countByGroup(Integer groupId);

    @Query(nativeQuery = true, value = "SELECT 1 FROM user_group_bind WHERE user_id = ?1 AND group_id = ?2")
    Integer existsByUser(Integer userId, Integer groupId);
}