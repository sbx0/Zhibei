package cn.sbx0.zhibei.logic.user.certification;

import java.util.Arrays;
import java.util.List;

/**
 * 认证种类
 */
public enum CertificationType {
    personal(0, "个人"),
    enterprise(996, "企业"),
    university(985, "大学"),
    mechanism(12, "机构"),
    advertisers(512, "广告主"),
    auditors(128, "审核人员"),
    admin(256, "管理员"),
    webSiteOwner(1024, "网站所有者"),
    ;
    int value;
    String name;

    CertificationType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static List<CertificationType> list() {
        return Arrays.asList(
                personal,
                enterprise,
                university,
                mechanism,
                advertisers,
                auditors,
                admin,
                webSiteOwner
        );
    }

    public static boolean judge(int value) {
        switch (value) {
            case 1024:
            case 512:
            case 256:
            case 128:
            case 985:
            case 12:
            case 996:
            case 0:
                return true;
            default:
                return false;
        }
    }
}
