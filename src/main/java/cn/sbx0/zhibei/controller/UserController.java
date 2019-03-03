package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.entity.Verify;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.EmailService;
import cn.sbx0.zhibei.service.VerifyService;
import cn.sbx0.zhibei.tool.CookieTools;
import cn.sbx0.zhibei.tool.StringTools;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
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
    @Resource
    EmailService emailService;
    @Resource
    VerifyService verifyService;

    @Override
    public BaseService<User, Integer> getService() {
        return userService;
    }

    @Autowired
    public UserController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 忘记密码
     *
     * @param email
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/lostPassword")
    public ObjectNode lostPassword(String email) {
        json = mapper.createObjectNode();
        User user = userService.findByEmail(email);
        if (user != null) {
            Verify verify = new Verify();
            verify.setMd5(StringTools.getHash(new Date() + user.getEmail(), "MD5"));
            verify.setTime(new Date());
            verify.setParameter("user:" + user.getId());
            verify.setType("password");
            verify.setUsed(false);
            if (verifyService.save(verify)) {
                String url = "http://m.sbx0.cn/verify/" + verify.getMd5();
                if (emailService.sendEmail(user, "安全提醒：正在修改密码。", "<p>您的账号" + user.getNickname() + "[@" + user.getName() + "]于" + new Date() + "尝试修改密码。</p><p>请点击<a href=" + url + ">修改密码</a></p>")) {
                    json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
                } else {
                    json.put(STATUS_NAME, STATUS_CODE_FILED);
                }
            } else {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
        }
        return json;
    }

    /**
     * 修改密码
     *
     * @param password
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/changePassword")
    public ObjectNode changePassword(String password, String md5) {
        json = mapper.createObjectNode();
        Verify verify = verifyService.existsByMd5(md5);
        if (verify == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
        } else {
            if (verify.getTime().getTime() + 30 * 60 * 1000 < new Date().getTime()) {
                json.put(STATUS_NAME, STATUS_CODE_TIME_OUT);
            } else {
                if (!verify.getType().equals("password")) {
                    json.put(STATUS_NAME, STATUS_CODE_PARAMETER_ERROR);
                } else {
                    String parameter = verify.getParameter();
                    String[] key = parameter.split(":");
                    if (key[0].equals("user")) {
                        User user = userService.findById(Integer.parseInt(key[1]));
                        if (user != null) {
                            user.setPassword(StringTools.encryptPassword(password));
                            if (userService.save(user)) {
                                emailService.sendEmail(user, "安全提醒：您的密码修改成功。", "<p>您的账号" + user.getNickname() + "[@" + user.getName() + "]于" + new Date() + "成功修改了密码。</p>");
                                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
                            } else {
                                emailService.sendEmail(user, "安全提醒：有人尝试修改您的密码。", "<p>您的账号" + user.getNickname() + "[@" + user.getName() + "]于" + new Date() + "尝试修改密码失败。</p>");
                                json.put(STATUS_NAME, STATUS_CODE_FILED);
                            }
                        } else {
                            json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
                        }
                    } else {
                        json.put(STATUS_NAME, STATUS_CODE_PARAMETER_ERROR);
                    }
                }
            }
        }
        return json;
    }

    /**
     * 修改当前用户的信息
     *
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping("/data")
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
