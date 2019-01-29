package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.PermissionDao;
import cn.sbx0.zhibei.entity.Permission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 检测权限
     *
     * @param permissionStr 类似于 permission:user/admin:1 (允许访问user/admin) 、user:11或user:*或user (允许访问对user 进行查增改删)
     * @param permissions
     * @return
     */
    public static boolean checkPermission(String permissionStr, List<Permission> permissions) {

        return false;
    }

    /**
     * 检测权限
     *
     * @param permissionStr
     * @param permission
     * @return
     */
    public static boolean checkPermission(String permissionStr, Permission permission) {
        return false;
    }
}
