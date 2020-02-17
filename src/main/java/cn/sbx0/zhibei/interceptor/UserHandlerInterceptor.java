package cn.sbx0.zhibei.interceptor;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.annotation.RoleCheck;
import cn.sbx0.zhibei.logic.user.info.UserInfo;
import cn.sbx0.zhibei.logic.user.role.UserRole;
import cn.sbx0.zhibei.logic.user.role.UserRoleBind;
import cn.sbx0.zhibei.logic.user.role.UserRoleService;
import cn.sbx0.zhibei.tool.CookieTools;
import cn.sbx0.zhibei.tool.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 登录拦截器
 */

@Configuration
public class UserHandlerInterceptor implements HandlerInterceptor {
    @Resource
    private UserRoleService service;

    /**
     * 用于判断用户是否登录
     * 前提所调用方法是否添加@LoginRequired注解
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return boolean
     * @throws Exception exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        LoginRequired loginRequiredAnnotation = method.getAnnotation(LoginRequired.class);
        RoleCheck roleCheckAnnotation = method.getAnnotation(RoleCheck.class);
        // 有 @LoginRequired 注解，判断是否登录
        if (loginRequiredAnnotation != null || roleCheckAnnotation != null) {
            HttpSession session = request.getSession(true);
            UserInfo user = (UserInfo) session.getAttribute("user");
            int userId = -1;
            boolean isLogin = false;
            if (!(user != null && user.getUserId() != null && user.getEmail() != null)) {
                // 查找是否存在cookie
                Map<String, Cookie> cookies = CookieTools.getCookiesByName(CookieTools.COOKIE_NAMES, request.getCookies());
                if (cookies == null) return false;
                else if (cookies.size() == 0) return false;
                else {
                    // 为空
                    for (int i = 0; i < cookies.size(); i++) {
                        if (StringTools.checkNullStr(cookies.get(CookieTools.COOKIE_NAMES.get(i)).getValue())) {
                            return false;
                        }
                    }
                    // Cookie中的ID
                    userId = Integer.parseInt(cookies.get("ID").getValue());
                    // Cookie中的KEY
                    String key = cookies.get("KEY").getValue();
                    // 正确的KEY
                    String check = StringTools.getKey(userId);
                    // 匹配KEY
                    isLogin = check.equals(key);
                    if (!isLogin) {
                        request.getRequestDispatcher("/user/base/notLogin").forward(request, response);
                        return false;
                    }
                }
            }
            // 已登录
            // 如果有检查角色注解
            if (roleCheckAnnotation != null) {
                String[] roles = roleCheckAnnotation.values();
                if (roles.length > 0) {
                    Date now = new Date();
                    List<UserRoleBind> userRoleBinds = service.findAllByUserId(userId);
                    List<UserRole> userRoles = new ArrayList<>();
                    for (UserRoleBind userRoleBind : userRoleBinds) {
                        // 如果用户角色过期
                        if (now.getTime() > userRoleBind.getValidityTime().getTime()) {
                            continue;
                        }
                        userRoles.add(service.findById(userRoleBind.getRoleId()));
                    }
                    for (UserRole userRole : userRoles) {
                        for (String role : roles) {
                            if (userRole.getCode().equals(role)) return true;
                        }
                    }
                    request.getRequestDispatcher("/user/base/noPermission").forward(request, response);
                    return false;
                }
            }
            return true;
        }
        return true;
    }
}
