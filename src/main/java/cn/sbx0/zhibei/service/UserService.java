package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.RoleDao;
import cn.sbx0.zhibei.dao.UserDao;
import cn.sbx0.zhibei.entity.Permission;
import cn.sbx0.zhibei.entity.Role;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.tool.CookieTools;
import cn.sbx0.zhibei.tool.StringTools;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 基础用户 服务层
 */
@Service
public class UserService extends BaseService<User, Integer> {
    @Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;

    @Override
    public PagingAndSortingRepository<User, Integer> getDao() {
        return userDao;
    }

    @Override
    public User getEntity() {
        return new User();
    }

    /**
     * 修改当前用户的信息
     *
     * @param oldUser
     * @return
     */
    public boolean data(User oldUser) {
        User user = getUser();
        user.setNickname(StringTools.killHTML(oldUser.getNickname().trim()));
        user.setIntroduction(StringTools.killHTML(oldUser.getIntroduction().trim()));
        user.setBirthday(oldUser.getBirthday());
        user.setEmail(oldUser.getEmail());
        user.setPhone(oldUser.getPhone());
        user.setSex(oldUser.getSex());
        try {
            return save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    public boolean register(User user) {
        // 名称为空
        if (StringTools.checkNullStr(user.getName())) return false;
        // 密码为空
        if (StringTools.checkNullStr(user.getPassword())) return false;
        user.setName(StringTools.killHTML(user.getName().trim()));
        user = encryptPassword(user); // 加密密码
        user.setBanned(false); // 默认不封禁
        user.setRegisterTime(new Date()); // 注册时间
        // 默认角色 用户
        Role userRole = roleDao.findByName("user");
        if (userRole == null) { // 默认角色不存在
            userRole = new Role();
            userRole.setName("user");
            userRole.setIntroduction("default role");
            userRole.setAvailable(true);
            List<Permission> permissions = new ArrayList<>();
            userRole.setPermissions(permissions);
            try {
                roleDao.save(userRole);
            } catch (Exception e) {
                return false;
            }
            userRole = roleDao.findByName("user");
        }
        user.setRole(userRole);
        try {
            return save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将增删查改转换成二进制
     *
     * @param methodType
     * @return
     */
    public String methodTypeToBinary(String methodType) {
        switch (methodType) {
            case "list":
                return "0001";
            case "post":
            case "upload":
            case "avatar":
            case "data":
                return "0010";
            case "add":
                return "0100";
            case "delete":
                return "1000";
            default:
                return "0000";
        }
    }

    /**
     * 检测用户权限
     *
     * @param user
     * @return
     */
    public boolean checkPermission(User user) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String path = request.getServletPath(); // 运行的方法
        if (path == null) return true;
        if (user.getRole() == null) return false; // 无角色
        List<Permission> permissions = user.getRole().getPermissions(); // 权限列表
        if (permissions == null) return false; // 无权限
        for (Permission permission : permissions) {
            if (permission.getUrl().equals("*")) return true; // * 匹配所有
            if (permission.getUrl().equals(path)) { // 权限url与path匹配
                if (permission.getStr().equals("*")) return true; // * 匹配所有
                if (permission.getStr().equals("0")) { // 一般是页面 0 否
                    return false;
                } else if (permission.getStr().equals("1")) { // 1 是
                    return true;
                }
            } else {
                String url = permission.getUrl().substring(1); // /article/* -> article/*
                String urlType = url.split("/")[1]; // article/* -> *
                if (!path.split("/")[0].equals(permission.getUrl().split("/")[0])) {
                    return false;
                }
                if (!urlType.equals("*")) return false;
            }
            path = path.substring(1); // /article/list -> article/list
            String pathType = path.split("/")[1]; // article/list -> list
            if (permission.getStr().equals("*")) return true;
            char[] pathCharArray = methodTypeToBinary(pathType).toCharArray(); // list -> 0001
            char[] permissionCharArray = permission.getStr().toCharArray(); // 0001 / 0010 / 0011 / ...
            for (int i = 0; i < 4; i++) {
                if (pathCharArray[i] == permissionCharArray[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据用户名判断用户是否存在
     *
     * @param name
     * @return
     */
    private boolean existByName(String name) {
        String result = userDao.existsByName(name);
        return result != null;
    }

    /**
     * 根据用户名查找用户
     *
     * @param name
     * @return
     */
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    /**
     * 登陆
     *
     * @param user
     * @return
     */
    public User login(User user) {
        user.setName(StringTools.killHTML(user.getName()));
        // 密码加密
        user = encryptPassword(user);
        // 用户不存在
        if (!existByName(user.getName())) {
            return null;
        }
        // 查询数据库内的用户数据
        User databaseUser = userDao.findByName(user.getName());
        // 密码是否正确
        if (user.getPassword().equals(databaseUser.getPassword())) {
            return databaseUser;
        } else return null;
    }

    /**
     * 加密密码
     *
     * @param user
     * @return
     */
    public User encryptPassword(User user) {
        user.setPassword(StringTools.encryptPassword(user.getPassword()));
        return user;
    }

    /**
     * 根据session或cookie查找User
     */
    public User getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getId() != null) {
            return user;
        }
        // 查找是否存在cookie
        Map<String, Cookie> cookies = CookieTools.getCookiesByName(CookieTools.COOKIE_NAMES, request.getCookies());
        if (cookies == null) return null;
        if (cookies.size() == 0) return null;
        // 为空
        for (int i = 0; i < cookies.size(); i++) {
            if (StringTools.checkNullStr(cookies.get(CookieTools.COOKIE_NAMES.get(i)).getValue()))
                return null;
        }
        // Cookie中的ID
        int id = Integer.parseInt(cookies.get("ID").getValue());
        // Cookie中的KEY
        String key = cookies.get("KEY").getValue();
        // Cookie中的用户名
        String name = cookies.get("NAME").getValue();
        // 正确的KEY
        String check = StringTools.getKey(id);
        // 匹配KEY
        if (!check.equals(key))
            return null;
        user = findById(id);
        if (user == null)
            return null;
        if (!user.getName().equals(name))
            return null;
        return user;
    }

}
