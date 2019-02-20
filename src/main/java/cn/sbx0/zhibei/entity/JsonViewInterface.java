package cn.sbx0.zhibei.entity;

/**
 * 用于 JsonView 过滤
 */
public class JsonViewInterface {
    // 只有几个必须的字段
    public interface Simple {

    }

    // 只有几个必须的字段
    public interface Normal extends Simple {

    }

    // 全部
    public interface All extends Normal {

    }
}
