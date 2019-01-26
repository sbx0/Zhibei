package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.UserDao;
import cn.sbx0.zhibei.entity.Permission;
import cn.sbx0.zhibei.entity.Role;
import cn.sbx0.zhibei.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userDao.findByName(username);
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
    private User encryptPassword(User user) {
        String password = user.getPassword();
        password = new BCryptPasswordEncoder().encode(password);
        user.setPassword(password);
        return user;
    }

}
