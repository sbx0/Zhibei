package cn.sbx0.zhibei.logic.user;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.tool.CookieTools;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 基础用户 控制层
 */
@RestController
@RequestMapping("/user/base")
public class UserBaseController extends BaseController<UserBase, Integer> {
    @Resource
    private UserBaseService service;

    /**
     * 返回最基础的登录用户信息
     *
     * @return json
     */
    @GetMapping(value = "/basic")
    public ObjectNode avatar() {
        ObjectNode json = initJSON();
        UserBase userBase = service.findById(service.getLoginUserId());
        ObjectNode userBaseJson = getMapper().convertValue(userBase, ObjectNode.class);
        json.set(jsonOb, userBaseJson);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * 心跳
     * 记录活跃用户
     *
     * @return json
     */
    @GetMapping(value = "/heartbeat")
    public ObjectNode heartbeat() {
        ObjectNode json = initJSON();
        service.heartbeat(service.getLoginUserId(), new Date());
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * 退出登录
     *
     * @param request  request
     * @param response response
     * @return json
     */
    @GetMapping(value = "/logout")
    public ObjectNode logout(HttpServletRequest request, HttpServletResponse response) {
        ObjectNode json = initJSON();
        CookieTools.removeCookies(response);
        HttpSession session = request.getSession(false);
        if (session != null) session.removeAttribute("user");
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * 获取登录信息
     *
     * @return json
     */
    @GetMapping(value = "/info")
    public ObjectNode info() {
        ObjectNode json = initJSON();
        UserInfo userInfo = service.getLoginUser();
        if (userInfo == null || userInfo.getUserId() == null) {
            json.put(statusCode, ReturnStatus.notLogin.getCode());
            return json;
        }
        UserBase userBase = service.findById(userInfo.getUserId());
        ObjectNode userBaseJson = getMapper().convertValue(userBase, ObjectNode.class);
        json.set("user", userBaseJson);
        ObjectNode userInfoJson = getMapper().convertValue(userInfo, ObjectNode.class);
        json.set(jsonOb, userInfoJson);
        json.put(statusCode, ReturnStatus.success.getCode());
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
        return json;
    }

    @Override
    public BaseService<UserBase, Integer> getService() {
        return service;
    }

}
