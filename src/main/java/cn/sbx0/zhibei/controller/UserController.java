package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.tool.CookieTools;
import cn.sbx0.zhibei.tool.StringTools;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.Map;

/**
 * 用户 控制层
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User, Integer> {

    @Override
    public BaseService<User, Integer> getService() {
        return userService;
    }

    @Autowired
    public UserController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 修改当前用户的信息
     *
     * @return
     */
    public ObjectNode data(User oldUser) {
        json = mapper.createObjectNode();
        if (userService.data(oldUser)) {
            json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        } else {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
        }
        return json;
    }

    /**
     * 进入后台
     *
     * @return
     */
    @LogRecord
    @GetMapping("/admin")
    public String admin(Map<String, Object> map) {
        User user = userService.getUser();
        if (user != null) {
            if (userService.checkPermission(user)) {
                return "admin";
            }
        }
        map.put("status", "500");
        map.put("timestamp", new Date());
        map.put("message", "无权访问");
        return "error";
    }

    /**
     * 查看当前登录用户基本信息
     *
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/info")
    public ObjectNode info() {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Normal.class));
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user != null) {
            ObjectNode convertValue = mapper.convertValue(user, ObjectNode.class);
            json.set("user", convertValue);
            json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }

    /**
     * 登陆
     *
     * @param user
     * @param response
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping(value = "/login")
    public ObjectNode login(User user, HttpServletResponse response) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        json = mapper.createObjectNode();
        // 判断是否为空
        if (StringTools.checkNullStr(user.getName()) || StringTools.checkNullStr(user.getPassword())) {
            // 操作状态保存
            json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
            return json;
        }
        // 调用登陆，若返回为null，则为密码错误
        user = userService.login(user);
        // 登陆成功
        if (user != null) {
            // 创建Cookie
            response.addCookie(CookieTools.createCookie(CookieTools.COOKIE_NAMES.get(0), user.getId() + "", 30));
            response.addCookie(CookieTools.createCookie(CookieTools.COOKIE_NAMES.get(1), StringTools.getKey(user.getId()), 30));
            response.addCookie(CookieTools.createCookie(CookieTools.COOKIE_NAMES.get(2), user.getName(), 30));
            session.setAttribute("user", user);
            // 操作状态保存
            json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        } else {
            // 清除Cookie
            CookieTools.removeCookies(response);
            // 清除session
            session.removeAttribute("user");
            // 操作状态保存
            json.put(STATUS_NAME, STATUS_CODE_FILED);
        }
        // 返回json串
        return json;
    }

    /**
     * 退出登陆
     *
     * @param response
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping(value = "/logout")
    public ObjectNode logout(HttpServletRequest request, HttpServletResponse response) {
        json = mapper.createObjectNode();
        HttpSession session = request.getSession();
        try {
            // 清除Cookie
            CookieTools.removeCookies(response);
            // 清除session
            session.removeAttribute("user");
            json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        } catch (Exception e) {
            json.put(STATUS_NAME, STATUS_CODE_EXCEPTION);
            json.put("msg", e.getMessage());
        }
        return json;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping("/register")
    public ObjectNode register(User user) {
        json = mapper.createObjectNode();
        try {
            if (userService.register(user)) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
            }
        } catch (Exception e) {
            json.put(STATUS_NAME, STATUS_CODE_EXCEPTION);
            json.put("msg", e.getMessage());
        }
        return json;
    }

}
