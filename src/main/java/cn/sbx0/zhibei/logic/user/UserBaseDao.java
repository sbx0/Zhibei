package cn.sbx0.zhibei.logic.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 基础用户 数据层
 */
public interface UserBaseDao extends PagingAndSortingRepository<UserBase, Integer> {
    @Query(value = "select * from user_base where name = ?1", nativeQuery = true)
    UserBase findByName(String name);

    @Query(value = "select 1 from user_base where name = ?1", nativeQuery = true)
    String existsByName(String name);
}