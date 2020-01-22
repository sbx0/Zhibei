package cn.sbx0.zhibei.logic;

/**
 * 用于判断执行状态
 */
public enum ReturnStatus {
    failed(-1, "操作失败"),
    success(0, "操作成功"),
    nullStr(1, "空字符串"),
    invalidMail(2, "无效邮箱"),
    repeatOperation(3, "重复操作"),
    wrongPassword(4, "密码错误"),
    notLogin(5, "暂未登录");
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
                "value=" + code +
                ", str='" + msg + '\'' +
                '}';
    }
}
