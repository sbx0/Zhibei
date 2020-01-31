package cn.sbx0.zhibei.interceptor;

import cn.sbx0.zhibei.logic.user.UserInfo;
import cn.sbx0.zhibei.tool.CookieTools;
import cn.sbx0.zhibei.tool.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 登录拦截器
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    /**
     * 判断用户是否登录
     * 目标方法执行之前
     *
     * @param request  request
     * @param response response
     * @param handler  handler
     * @return boolean
     * @throws Exception exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (!(user != null && user.getUserId() != null && user.getEmail() != null)) {
            boolean isLogin;
            // 查找是否存在cookie
            Map<String, Cookie> cookies = CookieTools.getCookiesByName(CookieTools.COOKIE_NAMES, request.getCookies());
            if (cookies == null) isLogin = false;
            else if (cookies.size() == 0) isLogin = false;
            else {
                // 为空
                for (int i = 0; i < cookies.size(); i++) {
                    if (StringTools.checkNullStr(cookies.get(CookieTools.COOKIE_NAMES.get(i)).getValue()))
                        isLogin = false;
                }
                // Cookie中的ID
                int id = Integer.parseInt(cookies.get("ID").getValue());
                // Cookie中的KEY
                String key = cookies.get("KEY").getValue();
                // 正确的KEY
                String check = StringTools.getKey(id);
                // 匹配KEY
                isLogin = check.equals(key);
            }
            if (!isLogin) {
                request.getRequestDispatcher("/user/base/notLogin").forward(request, response);
            }
            return isLogin;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
