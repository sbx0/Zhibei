package cn.sbx0.zhibei.logic.user;

/**
 * 基础用户 显示类
 * 用于登录/注册
 */
public class UserBaseView {
    private String name;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "UserBaseView{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
