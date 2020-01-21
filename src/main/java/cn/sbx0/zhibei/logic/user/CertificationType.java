package cn.sbx0.zhibei.logic.user;

/**
 * 认证种类
 */
public enum CertificationType {
    WebSiteOwner(1024), // 网站所有者
    Advertisers(512), // 广告主
    Admin(256), // 管理员
    Auditors(128), // 审核人员
    University(985), // 大学
    Mechanism(12), // 机构
    Enterprise(996), // 企业
    Personal(0), // 个人
    ;
    int value;

    CertificationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
