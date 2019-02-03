package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.RoleDao;
import cn.sbx0.zhibei.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
