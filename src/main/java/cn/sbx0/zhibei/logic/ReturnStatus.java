package cn.sbx0.zhibei.logic;

/**
 * 用于判断执行状态
 */
public enum ReturnStatus {
    failed(-1, "操作失败"),
    success(0, "操作成功"),
    nullError(1, "空值错误"),
    invalidValue(2, "无效数据"),
    repeatOperation(3, "重复操作"),
    wrongPassword(4, "密码错误"),
    notLogin(5, "暂未登录"),
    emptyResult(6, "结果为空"),
    noPermission(7, "暂无权限"),
    exception(8, "发生异常"),
    outRange(9, "超出限制");
    int code;
    String msg;

    ReturnStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ReturnStatus{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}