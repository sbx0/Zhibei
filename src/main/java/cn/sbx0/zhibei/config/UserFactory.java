package cn.sbx0.zhibei.config;

import cn.sbx0.zhibei.entity.Role;
import cn.sbx0.zhibei.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

final class UserFactory {

    private UserFactory() {

    }

    static org.springframework.security.core.userdetails.User create(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
        );
    }

    //将与用户类一对多的角色类的名称集合转换为 GrantedAuthority 集合
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}