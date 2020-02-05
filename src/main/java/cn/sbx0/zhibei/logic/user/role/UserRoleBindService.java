package cn.sbx0.zhibei.logic.user.role;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户角色绑定 服务层
 */
@Service
public class UserRoleBindService extends BaseService<UserRoleBind, Integer> {
    @Resource
    private UserRoleBindDao dao;

    @Override
    public PagingAndSortingRepository<UserRoleBind, Integer> getDao() {
        return dao;
    }

    @Override
    public UserRoleBind getEntity() {
        return new UserRoleBind();
    }

    @Override
    public boolean checkDataValidity(UserRoleBind UserRoleBind) {
        return true;
    }
}
