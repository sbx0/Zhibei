package cn.sbx0.zhibei.logic.user.role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 用户角色绑定 数据层
 */
public interface UserRoleBindDao extends PagingAndSortingRepository<UserRoleBind, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM user_role_bind WHERE user_id = ?1")
    List<UserRoleBind> findAllByUserId(Integer userId);
}