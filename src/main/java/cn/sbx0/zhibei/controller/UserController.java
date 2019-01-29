package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @Resource
    UserService userService;

    /**
     * 进入后台
     *
     * @param session
     * @return
     */
    @GetMapping("/admin")
    public String admin(HttpSession session, HttpServletRequest request) {
        User user = userService.getUser(session, request);
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
     * @param session
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/info")
    public ObjectNode info(HttpSession session, HttpServletRequest request) {
        json = mapper.createObjectNode();
        User user = userService.getUser(session, request);
        ObjectNode attr = mapper.createObjectNode();
        if (user != null) {
            attr.put("id", user.getId());
            attr.put("name", user.getName());
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
     * @param session
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/login")
    public ObjectNode login(User user, HttpServletResponse response, HttpSession session) {
        json = mapper.createObjectNode();
        // 判断是否为空
        if (BaseService.checkNullStr(user.getName()) || BaseService.checkNullStr(user.getPassword())) {
            // 操作状态保存
            json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
            return json;
        }
        // 调用登陆，若返回为null，则为密码错误
        user = userService.login(user);
        // 登陆成功
        if (user != null) {
            // 创建Cookie
            response.addCookie(BaseService.createCookie(BaseService.COOKIE_NAMES.get(0), user.getId() + "", 30));
            response.addCookie(BaseService.createCookie(BaseService.COOKIE_NAMES.get(1), BaseService.getKey(user.getId()), 30));
            response.addCookie(BaseService.createCookie(BaseService.COOKIE_NAMES.get(2), user.getName(), 30));
            session.setAttribute("user", user);
            // 操作状态保存
            json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        } else {
            // 清除Cookie
            BaseService.removeCookies(response);
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
    @GetMapping(value = "/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        try {
            // 清除session
            session.removeAttribute("user");
            // 清除Cookie
            BaseService.removeCookies(response);
            return "redirect:/login.html";
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * 增加 因为有几个属性需要初始化，所以重写了
     *
     * @param user
     * @return
     */
    @Override
    public ObjectNode add(User user) {
        user.setBanned(false);
        user.setRegisterTime(new Date());
        return super.add(user);
    }

    @Override
    public BaseService<User, Integer> getService() {
        return userService;
    }

    @Autowired
    public UserController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

}
