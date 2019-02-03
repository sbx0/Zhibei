package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.tool.CookieTools;
import cn.sbx0.zhibei.tool.StringTools;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;

/**
 * 基础用户 控制层
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

    @JsonView(User.Admin.class)
    @Override
    public ObjectNode list(Integer page, Integer size, HttpServletRequest request) {
        return super.list(page, size, request);
    }

    /**
     * 进入后台
     *
     * @return
     */
    @LogRecord
    @GetMapping("/admin")
    public String admin(HttpServletRequest request) {
        User user = userService.getUser(request);
        if (user != null) {
            if (userService.checkPermission(request, user)) {
                return "admin";
            }
        }
        return "error";
    }

    /**
     * 查看当前登录用户基本信息
     *
     * @param request
     * @return
     */
    @LogRecord
    @JsonView(User.Admin.class)
    @ResponseBody
    @GetMapping("/info")
    public ObjectNode info(HttpServletRequest request) {
        json = mapper.createObjectNode();
        User user = userService.getUser(request);
        if (user != null) {
            ObjectNode attr = mapper.convertValue(user, ObjectNode.class);
            json.set("user", attr);
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
     * @param request
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping(value = "/login")
    public ObjectNode login(User user, HttpServletResponse response, HttpServletRequest request) {
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
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            // 清除Cookie
            CookieTools.removeCookies(response);
            // 清除session
            session.removeAttribute("user");
            return "redirect:/login.html";
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * 注册
     *
     * @param u
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping("/register")
    public ObjectNode register(User u) {
        u.setBanned(false);
        u.setRegisterTime(new Date());
        json = mapper.createObjectNode();
        try {
            if (userService.save(u)) {
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
