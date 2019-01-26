package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 角色 数据层
 */
public interface RoleDao extends PagingAndSortingRepository<Role, Integer> {

}