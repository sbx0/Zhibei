package cn.sbx0.zhibei.logic.user.role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 用户角色 数据层
 */
public interface UserRoleDao extends PagingAndSortingRepository<UserRole, Integer> {
    @Query(value = "SELECT * FROM user_role where code = ?1", nativeQuery = true)
    UserRole findByCode(String code);

    @Query(value = "select 1 from user_role where id = ?1", nativeQuery = true)
    String existById(Integer roleId);

    @Query(value = "select * from user_role where id in (select role_id from user_role_bind where user_id = 1) order by weight desc", nativeQuery = true)
    List<UserRole> findAllByUser(Integer roleId);
}