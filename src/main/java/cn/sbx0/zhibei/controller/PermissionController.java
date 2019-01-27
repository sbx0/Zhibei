package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Permission;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 权限 控制层
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController<Permission, Integer> {
    @Resource
    private PermissionService permissionService;

    @Override
    public BaseService<Permission, Integer> getService() {
        return permissionService;
    }
}
