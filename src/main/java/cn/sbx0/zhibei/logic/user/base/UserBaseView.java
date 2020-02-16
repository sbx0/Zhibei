package cn.sbx0.zhibei.logic.user.base;

import lombok.Data;

/**
 * 基础用户 显示类
 * 用于登录/注册
 */
@Data
public class UserBaseView {
    private String name;
    private String email;
    private String password;
}
