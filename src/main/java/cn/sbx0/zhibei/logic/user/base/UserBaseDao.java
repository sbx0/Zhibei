package cn.sbx0.zhibei.logic.user.base;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 基础用户 数据层
 */
public interface UserBaseDao extends PagingAndSortingRepository<UserBase, Integer> {
    @Query(value = "select * from user_base where name = ?1", nativeQuery = true)
    UserBase findByName(String name);

    @Query(value = "select 1 from user_base where name = ?1", nativeQuery = true)
    String existsByName(String name);

    @Query(value = "select 1 from user_base where id = ?1", nativeQuery = true)
    String existById(Integer id);

    @Query(nativeQuery = true, value = "SELECT DISTINCT * FROM user_base WHERE id IN (SELECT user_id FROM user_group_bind WHERE group_id = ?1)")
    List<UserBase> findAllByGroup(Integer groupId);
}