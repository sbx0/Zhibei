package cn.sbx0.zhibei.logic.user.base;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.info.UserInfo;
import cn.sbx0.zhibei.logic.user.info.UserInfoService;
import cn.sbx0.zhibei.tool.CookieTools;
import cn.sbx0.zhibei.tool.DateTools;
import cn.sbx0.zhibei.tool.StringTools;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 基础用户 服务层
 */
@Service
public class UserBaseService extends BaseService<UserBase, Integer> {
    @Resource
    private UserBaseDao dao;
    @Resource
    private UserInfoService userInfoService;

    public int active() {
        Date now = new Date();
        // 间隔30分钟
        Date before = DateTools.rollSecond(now, 60 * 30);
        return userInfoService.countByTime(before);
    }

    /**
     * 心跳
     * 记录活跃用户
     */
    public void heartbeat(int id, Date date) {
        userInfoService.heartbeat(id, date);
    }

    /**
     * 从cookie或session中获取登录的用户的Id
     *
     * @return int
     */
    public int getLoginUserId() {
        return userInfoService.getLoginUserId();
    }

    /**
     * 从cookie或session中获取登录的用户
     *
     * @return UserInfo
     */
    public UserInfo getLoginUser() {
        return userInfoService.getLoginUser();
    }

    /**
     * 登录
     *
     * @param user user
     * @return ReturnStatus
     */
    public ReturnStatus login(UserBaseView user, HttpSession session, HttpServletResponse response) {
        if (StringTools.checkNullStr(user.getEmail())) return ReturnStatus.nullError;
        if (StringTools.checkNullStr(user.getPassword())) return ReturnStatus.nullError;
        if (StringTools.checkNotEmail(user.getEmail())) return ReturnStatus.invalidValue;
        if (!userInfoService.existByEmail(user.getEmail())) return ReturnStatus.invalidValue;
        UserInfo userInfo = userInfoService.findByEmail(user.getEmail());
        if (userInfo == null) return ReturnStatus.failed;
        // 密码是否正确
        if (!userInfo.getPassword().equals(StringTools.encryptPassword(user.getPassword()))) {
            // 清除Cookie
            CookieTools.removeCookies(response);
            // 清除session
            session.removeAttribute(userInfo.getUserId().toString());
            return ReturnStatus.wrongPassword;
        } else {
            // 登录成功 设置cookie
            response.addCookie(CookieTools.createCookie(CookieTools.COOKIE_NAMES.get(0), userInfo.getUserId().toString(), 30));
            response.addCookie(CookieTools.createCookie(CookieTools.COOKIE_NAMES.get(1), StringTools.getKey(userInfo.getUserId()), 30));
            // 登录成功 设置session
            session.setAttribute("user", userInfo);
        }
        return ReturnStatus.success;
    }

    /**
     * 注册
     *
     * @param user user
     * @return ReturnStatus
     */
    public ReturnStatus register(UserBaseView user) {
        if (StringTools.checkNullStr(user.getName())) return ReturnStatus.nullError;
        if (StringTools.checkNullStr(user.getEmail())) return ReturnStatus.nullError;
        if (StringTools.checkNullStr(user.getPassword())) return ReturnStatus.nullError;
        if (StringTools.checkNotEmail(user.getEmail())) return ReturnStatus.invalidValue;
        if (existByName(user.getName())) return ReturnStatus.repeatOperation;
        if (userInfoService.existByEmail(user.getEmail())) return ReturnStatus.repeatOperation;
        UserBase userBase = new UserBase();
        userBase.setName(user.getName());
        // 初始头像，后面可以改的
        userBase.setAvatar("/avatar.jpg");
        userBase = dao.save(userBase);
        if (userBase.getId() != null) {
            int status = userInfoService.register(userBase.getId(), user);
            if (status != ReturnStatus.success.getCode()) {
                // 如果用户信息创建失败，回滚基础用户
                dao.delete(userBase);
            }
        } else {
            return ReturnStatus.failed;
        }
        return ReturnStatus.success;
    }

    /**
     * 根据用户名判断用户是否存在
     *
     * @param name name
     * @return boolean
     */
    private boolean existByName(String name) {
        return dao.existsByName(name) != null;
    }

    @Override
    public PagingAndSortingRepository<UserBase, Integer> getDao() {
        return dao;
    }

    @Override
    public UserBase getEntity() {
        return new UserBase();
    }

}