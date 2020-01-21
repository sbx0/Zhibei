package cn.sbx0.zhibei.logic.user;

/**
 * 认证状态
 */
public enum CertificationStatus {
    ConfirmFailed(-1), // 审核为通过
    UnderConfirm(0), // 审核中
    ConfirmPassed(1),  // 审核通过
    ;
    int value;

    CertificationStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
