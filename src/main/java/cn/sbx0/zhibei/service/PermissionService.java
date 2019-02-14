package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.PermissionDao;
import cn.sbx0.zhibei.entity.Permission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 权限 服务层
 */
@Service
public class PermissionService extends BaseService<Permission, Integer> {
    @Resource
    PermissionDao permissionDao;

    @Override
    public PagingAndSortingRepository<Permission, Integer> getDao() {
        return permissionDao;
    }

    @Override
    public Permission getEntity() {
        return new Permission();
    }
}
