package cn.sbx0.zhibei.logic.user.role;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.certification.CertificationStatus;
import cn.sbx0.zhibei.logic.user.certification.UserCertification;
import cn.sbx0.zhibei.logic.user.info.UserInfo;
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
