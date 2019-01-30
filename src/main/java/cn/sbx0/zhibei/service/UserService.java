package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.UserDao;
import cn.sbx0.zhibei.entity.Permission;
import cn.sbx0.zhibei.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * 基础用户 服务层
 */
@Service
public class UserService extends BaseService<User, Integer> {
    @Resource
    UserDao userDao;

    @Override
    public PagingAndSortingRepository<User, Integer> getDao() {
        return userDao;
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
            case "add":
                return "0010";
            case "update":
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
     * @param request
     * @param user
     * @return
     */
    public boolean checkPermission(HttpServletRequest request, User user) {
        String method = request.getServletPath(); // 运行的方法
        if (method == null) return true;
        if (user.getRole() == null) return false;
        List<Permission> permissions = user.getRole().getPermissions();
        if (permissions == null) return false;
        for (Permission permission : permissions) {
            if (permission.getUrl().equals("*")) return true;
            if (permission.getUrl().equals(method)) {
                if (permission.getStr().equals("*")) return true;
                if (permission.getStr().equals("0")) {
                    return false;
                } else if (permission.getStr().equals("1")) {
                    return true;
                } else {
                    method = method.substring(1);
                    String methodType = method.split("/")[1];
                    char[] methodBinary = methodTypeToBinary(methodType).toCharArray();
                    char[] permissionStr = permission.getStr().toCharArray();
                    for (int i = 0; i < 4; i++) {
                        if (methodBinary[i] == permissionStr[i]) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 保存
     * 因为密码需要加密所以重写了
     *
     * @param user
     * @return
     */
    @Override
    public boolean save(User user) {
        encryptPassword(user); // 加密密码
        return super.save(user);
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
    @Transactional
    public User login(User user) {
        user.setName(BaseService.killHTML(user.getName()));
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
        user.setPassword(getHash(user.getPassword() + BaseService.KEY, "MD5"));
        return user;
    }

    /**
     * 根据session或cookie查找User
     */
    public User getUser(HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getId() != null) {
            return user;
        }
        // 查找是否存在cookie
        Map<String, Cookie> cookies = BaseService.getCookiesByName(COOKIE_NAMES, request.getCookies());
        if (cookies == null) return null;
        if (cookies.size() == 0) return null;
        // 为空
        for (int i = 0; i < cookies.size(); i++) {
            if (BaseService.checkNullStr(cookies.get(COOKIE_NAMES.get(i)).getValue()))
                return null;
        }
        // Cookie中的ID
        int id = Integer.parseInt(cookies.get("ID").getValue());
        // Cookie中的KEY
        String key = cookies.get("KEY").getValue();
        // Cookie中的用户名
        String name = cookies.get("NAME").getValue();
        // 正确的KEY
        String check = BaseService.getKey(id);
        // 匹配KEY
        if (!check.equals(key))
            return null;
        user = findById(id);
        if (user == null)
            return null;
        if (!user.getName().equals(name))
            return null;
        session.setAttribute("user", user); // 不知道有没有用
        return user;
    }

}
