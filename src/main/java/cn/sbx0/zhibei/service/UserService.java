package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.UserDao;
import cn.sbx0.zhibei.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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
     * 保存
     * 因为密码需要加密所以重写了
     *
     * @param user
     * @return
     */
    @Override
    public boolean save(User user) {
        user.setPassword(getHash(user.getPassword(), "MD5"));
        return super.save(user);
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
        user.setPassword(getHash(user.getPassword(), "MD5"));
        // 用户不存在
        if (!existByName(user.getName())) {
            return null;
        }
        // 查询数据库内的用户数据
        User databaseUser = userDao.findByName(user.getName());
        // 密码是否正确
        if (user.getPassword().equals(databaseUser.getPassword())) {
            // shiro 认证
            AuthenticationToken authenticationToken = new UsernamePasswordToken(user.getName(), user.getPassword());
            Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated()) {
                currentUser.login(authenticationToken); // 验证角色和权限
            }
            return databaseUser;
        } else return null;
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
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userDao.findByName(username);
    }

}
