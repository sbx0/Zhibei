package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Role;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 角色 控制层
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController<Role, Integer> {
    @Resource
    private RoleService roleService;

    @Override
    public BaseService<Role, Integer> getService() {
        return roleService;
    }
}
