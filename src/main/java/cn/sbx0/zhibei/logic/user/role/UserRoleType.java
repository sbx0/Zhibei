package cn.sbx0.zhibei.logic.user.role;

import java.util.Arrays;
import java.util.List;

/**
 * 用户角色种类
 * 一个用户可以拥有多个角色
 * <p>
 * webSiteOwner 网站拥有者给所有操作都可执行
 * admin 管理 可执行大部分操作
 * auditors 审核 可执行审核批准以及拒绝的操作
 * advertisers 金主 可投放及撤回自己的广告
 * enterprise 企业 可执行企业限定操作
 * university 大学 可执行大学限定操作
 * mechanism 机构 可执行机构限定操作
 * initial 默认 用户注册后的默认角色
 * banner 封禁者，禁止一切操作
 */
public enum UserRoleType {
    webSiteOwner("webSiteOwner", "网站拥有者", 99),
    admin("admin", "管理", 50),
    auditors("auditors", "审核", 25),
    advertisers("advertisers", "金主", 3),
    enterprise("enterprise", "企业", 2),
    university("university", "大学", 2),
    mechanism("mechanism", "机构", 2),
    initial("initial", "默认", 1),
    banner("banner", "被封禁", 0);
    String name; // 名称
    String code; // 代号，存到数据库中
    int weight; // 权值，权值越大权力越大

    public static List<UserRoleType> list() {
        return Arrays.asList(
                webSiteOwner,
                admin,
                auditors,
                advertisers,
                enterprise,
                university,
                mechanism,
                initial,
                banner
        );
    }

    UserRoleType(String code, String name, int weight) {
        this.code = code;
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "UserRoleType{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", weight=" + weight +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String getCode() {
        return code;
    }
}
