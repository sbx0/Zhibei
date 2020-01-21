package cn.sbx0.zhibei.logic;

/**
 * 用于判断执行状态
 */
public enum ReturnStatus {
    failed(-1), // 操作失败
    success(0), // 操作成功
    nullStr(1), // 空字符串
    invalidMail(2), // 无效邮箱
    repectOperation(3), // 重复操作
    ;
    int value;

    ReturnStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
