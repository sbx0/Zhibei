package cn.sbx0.zhibei.logic.technical.achievements;

import java.util.ArrayList;
import java.util.List;

/**
 * 技术合作方法
 */
public enum TechnicalCooperationMethod {
    completeTransfer(0, "完全转让"),
    licenseTransfer(1, "许可转让"),
    technologyShares(2, "技术入股"),
    cooperativeProduction(3, "合作生产"),
    other(4, "其他"),
    ;
    int value;
    String name;

    TechnicalCooperationMethod(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static List<TechnicalCooperationMethod> list() {
        List<TechnicalCooperationMethod> list = new ArrayList<>();
        list.add(completeTransfer);
        list.add(licenseTransfer);
        list.add(technologyShares);
        list.add(cooperativeProduction);
        list.add(other);
        return list;
    }

    public static boolean judge(int value) {
        switch (value) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }
}
