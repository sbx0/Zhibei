package cn.sbx0.zhibei.logic.user.certification;

/**
 * 认证状态
 */
public enum CertificationStatus {
    cancel(-2, "取消"),
    confirmFailed(-1, "未通过"),
    underConfirm(0, "审核中"),
    confirmPassed(1, "通过"),
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

    public static boolean judge(int value) {
        switch (value) {
            case -2:
            case -1:
            case 0:
            case 1:
                return true;
            default:
                return false;
        }
    }
}
