package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Permission;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 权限 数据层
 */
public interface PermissionDao extends PagingAndSortingRepository<Permission, Integer> {

}