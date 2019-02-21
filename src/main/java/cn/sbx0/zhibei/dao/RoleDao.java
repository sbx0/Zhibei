package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 角色 数据层
 */
public interface RoleDao extends PagingAndSortingRepository<Role, Integer> {

    /**
     * 根据名称查找
     *
     * @param name
     * @return
     */
    @Query(value = "FROM Role r WHERE r.name = ?1")
    Role findByName(String name);
}