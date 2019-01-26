package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Role;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
     * 因为有几个属性需要初始化，所以重写了
     *
     * @param user
     * @return
     */
    @Override
    public ObjectNode add(User user) {
        user.setBanned(false);
        user.setRegisterTime(new Date());
        Role role = new Role();
        role.setId(1);
        user.setRole(role);
        return super.add(user);
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
    @RequestMapping(value = "/login", method = RequestMethod.POST)
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

    @Override
    public BaseService<User, Integer> getService() {
        return userService;
    }

    @Autowired
    public UserController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

}
