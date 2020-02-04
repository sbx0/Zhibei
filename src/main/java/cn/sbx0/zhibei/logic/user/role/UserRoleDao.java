package cn.sbx0.zhibei.logic.user.role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 用户角色 数据层
 */
public interface UserRoleDao extends PagingAndSortingRepository<UserRole, Integer> {
    @Query(value = "SELECT * FROM user_role where code = ?1", nativeQuery = true)
    UserRole findByCode(String code);
}