package cn.sbx0.zhibei.logic.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

/**
 * 用户信息 数据层
 */
public interface UserInfoDao extends PagingAndSortingRepository<UserInfo, Integer> {
    @Query(value = "select 1 from user_info where email = ?1", nativeQuery = true)
    String existByEmail(String email);

    @Query(value = "select * from user_info where email = ?1", nativeQuery = true)
    UserInfo findByEmail(String email);

    @Query(value = "select * from user_info where user_id = ?1", nativeQuery = true)
    UserInfo findByUserId(int id);

    @Modifying
    @Query(value = "update user_info set last_time_online = ?2 where user_id = ?1", nativeQuery = true)
    void updateLastTimeLogin(int id, Date date);
}