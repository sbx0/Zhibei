package cn.sbx0.zhibei.logic.user;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 基础用户 控制层
 */
@RestController
@RequestMapping("/user/base")
public class UserBaseController extends BaseController<UserBase, Integer> {
    @Resource
    private UserBaseService service;

    /**
     * 获取登录信息
     *
     * @return json
     */
    @PostMapping(value = "/info")
    public ObjectNode info() {
        ObjectNode json = initJSON();
        UserInfo user = service.getLoginUser();
        if (user == null) {
            json.put(statusCode, ReturnStatus.notLogin.getCode());
            json.put(statusMsg, ReturnStatus.notLogin.getMsg());
            return json;
        }
        ObjectNode userInfoJson = getMapper().convertValue(user, ObjectNode.class);
        json.set(jsonOb, userInfoJson);
        json.put(statusCode, ReturnStatus.success.getCode());
        json.put(statusMsg, ReturnStatus.success.getMsg());
        return json;
    }

    /**
     * 登录
     *
     * @param user     user
     * @param request  request
     * @param response response
     * @return json
     */
    @PostMapping(value = "/login")
    public ObjectNode login(UserBaseView user, HttpServletRequest request, HttpServletResponse response) {
        ObjectNode json = initJSON();
        ReturnStatus status = service.login(user, request.getSession(true), response);
        json.put(statusCode, status.getCode());
        json.put(statusMsg, status.getMsg());
        return json;
    }

    /**
     * 注册
     *
     * @param user user
     * @return json
     */
    @PostMapping("/register")
    public ObjectNode register(UserBaseView user) {
        ObjectNode json = initJSON();
        ReturnStatus status = service.register(user);
        json.put(statusCode, status.getCode());
        json.put(statusMsg, status.getMsg());
        return json;
    }

    @Override
    public BaseService<UserBase, Integer> getService() {
        return service;
    }

}
