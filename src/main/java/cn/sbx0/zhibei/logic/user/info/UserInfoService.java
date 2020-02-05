package cn.sbx0.zhibei.logic.user.info;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBaseView;
import cn.sbx0.zhibei.tool.CookieTools;
import cn.sbx0.zhibei.tool.StringTools;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 用户信息 服务层
 */
@Service
public class UserInfoService extends BaseService<UserInfo, Integer> {
    @Resource
    private UserInfoDao dao;

    @Override
    public boolean checkDataValidity(UserInfo userInfo) {
        if (userInfo.getUserId() == null) return false;
        if (StringTools.checkNullStr(userInfo.getEmail())) return false;
        if (StringTools.checkNotEmail(userInfo.getEmail())) return false;
        if (StringTools.checkNullStr(userInfo.getPassword())) return false;
        if (userInfo.getRegisterTime() == null) return false;
        if (userInfo.getBanned() == null) return false;
        if (userInfo.getLevel() == null) return false;
        if (userInfo.getExp() == null) return false;
        return userInfo.getExpMax() != null;
    }

    /**
     * 指定时间内活跃的用户
     *
     * @param time time
     * @return int
     */
    public int countByTime(Date time) {
        return dao.countByTime(time);
    }

    /**
     * 心跳
     * 记录活跃用户
     */
    @Transactional
    public void heartbeat(int id, Date date) {
        dao.updateLastTimeLogin(id, date);
    }

    /**
     * 从cookie或session中获取登录的用户的Id
     *
     * @return int
     */
    public int getLoginUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpSession session = request.getSession(true);
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user != null && user.getUserId() != null && user.getEmail() != null) return user.getUserId();
        // 查找是否存在cookie
        Map<String, Cookie> cookies = CookieTools.getCookiesByName(CookieTools.COOKIE_NAMES, request.getCookies());
        if (cookies == null) return 0;
        if (cookies.size() == 0) return 0;
        // 为空
        for (int i = 0; i < cookies.size(); i++) {
            if (StringTools.checkNullStr(cookies.get(CookieTools.COOKIE_NAMES.get(i)).getValue())) return 0;
        }
        // Cookie中的ID
        int id = Integer.parseInt(cookies.get("ID").getValue());
        // Cookie中的KEY
        String key = cookies.get("KEY").getValue();
        // 正确的KEY
        String check = StringTools.getKey(id);
        // 匹配KEY
        if (!check.equals(key)) return 0;
        return id;
    }

    /**
     * 从cookie或session中获取登录的用户
     *
     * @return UserInfo
     */
    public UserInfo getLoginUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpSession session = request.getSession(true);
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user != null && user.getUserId() != null && user.getEmail() != null) return findByEmail(user.getEmail());
        // 查找是否存在cookie
        Map<String, Cookie> cookies = CookieTools.getCookiesByName(CookieTools.COOKIE_NAMES, request.getCookies());
        if (cookies == null) return null;
        if (cookies.size() == 0) return null;
        // 为空
        for (int i = 0; i < cookies.size(); i++) {
            if (StringTools.checkNullStr(cookies.get(CookieTools.COOKIE_NAMES.get(i)).getValue())) return null;
        }
        // Cookie中的ID
        int id = Integer.parseInt(cookies.get("ID").getValue());
        // Cookie中的KEY
        String key = cookies.get("KEY").getValue();
        // 正确的KEY
        String check = StringTools.getKey(id);
        // 匹配KEY
        if (!check.equals(key)) return null;
        return findByUserId(id);
    }


    /**
     * 注册
     *
     * @param id           ID
     * @param userBaseView 需要注册的信息
     * @return 状态码
     */
    public int register(Integer id, UserBaseView userBaseView) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(id);
        userInfo.setEmail(userBaseView.getEmail());
        // 加密密码
        userInfo.setPassword(StringTools.encryptPassword(userBaseView.getPassword()));
        userInfo.setRegisterTime(new Date());
        userInfo.setBanned(false);
        dao.save(userInfo);
        return ReturnStatus.success.getCode();
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param id id
     * @return UserInfo
     */
    public UserInfo findByUserId(int id) {
        return dao.findByUserId(id);
    }

    /**
     * 根据邮箱地址获取用户信息
     *
     * @param email email
     * @return UserInfo
     */
    public UserInfo findByEmail(String email) {
        return dao.findByEmail(email);
    }

    /**
     * 根据邮箱地址判断是否存在
     *
     * @param email email
     * @return boolean
     */
    public boolean existByEmail(String email) {
        return dao.existByEmail(email) != null;
    }

    @Override
    public PagingAndSortingRepository<UserInfo, Integer> getDao() {
        return dao;
    }

    @Override
    public UserInfo getEntity() {
        return new UserInfo();
    }
}
