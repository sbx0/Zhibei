package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 基础用户 数据层
 */
public interface UserDao extends PagingAndSortingRepository<User, Integer> {
    /**
     * 重写分页查询版查询全部
     *
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM users", nativeQuery = true)
    Page<User> findAll(Pageable pageable);

    /**
     * 查询某用户名是否存在
     *
     * @param name 用户名
     * @return 有结果则存在 无则不存在
     */
    @Query(value = "SELECT 1 FROM User u WHERE u.name = ?1")
    String existsByName(String name);

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return 用户
     */
    @Query(value = "FROM User u WHERE u.name = ?1")
    User findByName(String name);

    /**
     * 站点地图
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Query(value = "FROM User")
    List<User> findAll();
}