package cn.sbx0.zhibei.tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CookieTools {
    private static String DOMAIN; // 域名
    public static List<String> COOKIE_NAMES = Arrays.asList("ID", "KEY", "NAME");

    /**
     * 设置域名 配置cookie
     *
     * @param DOMAIN 域名 从配置文件中读取
     */
    @Value("${config.DOMAIN}")
    public void setDOMAIN(String DOMAIN) {
        CookieTools.DOMAIN = DOMAIN;
    }

    /**
     * 创建cookie
     *
     * @param name  cookie名
     * @param value cookie值
     * @param day   cookie存活时间
     * @return 创建好的cookie
     */
    public static Cookie createCookie(String name, String value, int day) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(DOMAIN);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(day * 24 * 60 * 60);
        return cookie;
    }

    /**
     * 清空cookie
     *
     * @param response response
     */
    public static void removeCookies(HttpServletResponse response) {
        for (String COOKIE_NAME : COOKIE_NAMES) {
            Cookie cookie = new Cookie(COOKIE_NAME, null);
            cookie.setDomain(DOMAIN);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    /**
     * 在一群cookie中根据名称查找想要的
     *
     * @param name    cookie名
     * @param cookies cookie列表
     * @return 找到的cookie
     */
    public static Map<String, Cookie> getCookiesByName(List<String> name, Cookie[] cookies) {
        // 一群Cookie为空，放弃寻找
        if (cookies == null) return null;
        // 名字有几个就找几个
        Map<String, Cookie> getCookies = new HashMap<>();
        for (Cookie cookie : cookies) { // 遍历一群Cookie
            for (int j = 0; j < name.size(); j++) { // 匹配名称
                if (cookie.getName().equals(name.get(j))) { // 找到一个
                    getCookies.put(name.get(j), cookie); // 存下来
                    if (getCookies.size() == name.size()) break; // 全找到了
                }
            }
        }
        return getCookies;
    }

}
