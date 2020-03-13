package cn.sbx0.zhibei.logic.user.base;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.info.UserInfo;
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

    @GetMapping(value = "/show")
    public ObjectNode show(Integer id) {
        ObjectNode json = initJSON();
        UserBase userBase = service.findById(id);
        if (userBase != null) {
            ObjectNode userBaseJson = getMapper().convertValue(userBase, ObjectNode.class);
            json.set(jsonOb, userBaseJson);
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.invalidValue.getCode());
        }
        return json;
    }

    /**
     * 当前活跃人数
     *
     * @return json
     */
    @GetMapping(value = "/active")
    public ObjectNode active() {
        ObjectNode json = initJSON();
        json.put(jsonOb, service.active());
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * 仅用于显示无权限
     *
     * @return json
     */
    @GetMapping(value = "/noPermission")
    public ObjectNode noPermission() {
        ObjectNode json = initJSON();
        json.put(statusMsg, ReturnStatus.noPermission.getMsg());
        json.put(statusCode, ReturnStatus.noPermission.getCode());
        return json;
    }

    /**
     * 仅用于显示未登录
     *
     * @return json
     */
    @GetMapping(value = "/notLogin")
    public ObjectNode notLogin() {
        ObjectNode json = initJSON();
        json.put(statusMsg, ReturnStatus.notLogin.getMsg());
        json.put(statusCode, ReturnStatus.notLogin.getCode());
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
        int id = service.getLoginUserId();
        if (id != 0) {
            service.heartbeat(service.getLoginUserId(), new Date());
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.notLogin.getCode());
        }
        return json;
    }

    /**
     * 返回最基础的登录用户信息
     *
     * @return json
     */
    @GetMapping(value = "/basic")
    public ObjectNode basic() {
        ObjectNode json = initJSON();
        UserBase userBase = service.findById(service.getLoginUserId());
        if (userBase != null) {
            ObjectNode userBaseJson = getMapper().convertValue(userBase, ObjectNode.class);
            json.set(jsonOb, userBaseJson);
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.notLogin.getCode());
        }
        return json;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return json
     */
    @LoginRequired
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
     * 退出登录
     *
     * @param request  request
     * @param response response
     * @return json
     */
    @LoginRequired
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
