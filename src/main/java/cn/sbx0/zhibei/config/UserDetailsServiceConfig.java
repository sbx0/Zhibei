package cn.sbx0.zhibei.config;

import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceConfig implements UserDetailsService {
    @Autowired
    private UserService userService;

    /**
     * 提供一种从用户名可以查到用户并返回的方法
     *
     * @param username 帐号
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 这里是数据库里的用户类
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("没有该用户 '%s'.", username));
        } else {
            //这里返回上面继承了 UserDetails  接口的用户类,为了简单我们写个工厂类
            return UserFactory.create(user);
        }
    }
}
