package cn.sbx0.zhibei.logic.user.group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 用户组 数据层
 */
public interface UserGroupDao extends PagingAndSortingRepository<UserGroup, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM user_group WHERE name like ?1")
    Page<UserGroup> findAllByName(String name, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * FROM user_group WHERE owner_id = ?1 OR id IN (SELECT group_id FROM user_group_bind WHERE user_id = ?1)")
    Page<UserGroup> findAllByUser(Integer userId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT limit_number FROM user_group WHERE id = ?1")
    Integer findLimitById(Integer id);
}