package cn.sbx0.zhibei.logic.user.role;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户角色绑定 控制层
 */
@RestController
@RequestMapping("/user/role/bind")
public class UserRoleBindController extends BaseController<UserRoleBind, Integer> {
    @Resource
    private UserRoleBindService service;

    @Override
    public BaseService<UserRoleBind, Integer> getService() {
        return service;
    }
}
