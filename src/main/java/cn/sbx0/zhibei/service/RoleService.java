package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.RoleDao;
import cn.sbx0.zhibei.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色 服务层
 */
@Service
public class RoleService extends BaseService<Role, Integer> {
    @Resource
    RoleDao roleDao;

    @Override
    public PagingAndSortingRepository<Role, Integer> getDao() {
        return roleDao;
    }

    /**
     * 检查角色是否匹配
     *
     * @param permissionStr 类似 role:admin 或 admin
     * @param role
     * @return
     */
    public static boolean checkRole(String permissionStr, Role role) {
        String name;
        int index = permissionStr.indexOf(":");
        if (index == -1) {
            return false;
        } else {
            try {
                name = permissionStr.split(":")[1];
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return role.getName().equals(name);
    }

}
