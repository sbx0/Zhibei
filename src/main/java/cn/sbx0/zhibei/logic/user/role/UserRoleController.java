package cn.sbx0.zhibei.logic.user.role;

import cn.sbx0.zhibei.annotation.RoleCheck;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户角色 控制层
 */
@RestController
@RequestMapping("/user/role")
public class UserRoleController extends BaseController<UserRole, Integer> {
    @Resource
    private UserRoleService service;

    @Override
    public BaseService<UserRole, Integer> getService() {
        return service;
    }

    /**
     * 赋予某人权限
     * 只有站长拥有这个权力
     *
     * @param userId userId
     * @param roleId roleId
     * @param kind   kind 为0时增加，为其他时删除
     * @return json
     */
    @RoleCheck(values = {"webSiteOwner"})
    @GetMapping("/give")
    public ObjectNode give(Integer userId, Integer roleId, Integer kind) {
        ObjectNode json = initJSON();
        ReturnStatus status = service.give(userId, roleId, kind);
        json.put(statusCode, status.getCode());
        return json;
    }

    /**
     * 初始化角色列表
     *
     * @return json
     */
    @GetMapping("/init")
    public ObjectNode init() {
        ObjectNode json = initJSON();
        service.init();
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

}
