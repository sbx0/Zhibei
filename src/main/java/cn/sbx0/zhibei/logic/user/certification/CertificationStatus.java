package cn.sbx0.zhibei.logic.user.certification;

/**
 * 认证状态
 */
public enum CertificationStatus {
    confirmFailed(-1, "审核未通过"),
    underConfirm(0, "审核中"),
    confirmPassed(1, "审核通过"),
    ;
    int value;
    String name;

    CertificationStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
