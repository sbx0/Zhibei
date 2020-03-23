package cn.sbx0.zhibei.logic.technical.requirements;

import java.util.ArrayList;
import java.util.List;

public enum TechnicalRequirementsStatus {
    look(0, "寻找中"),
    chat(1, "洽谈中"),
    sign(2, "已签约"),
    end(3, "已结束"),
    ;
    int value;
    String name;

    TechnicalRequirementsStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static List<TechnicalRequirementsStatus> list() {
        List<TechnicalRequirementsStatus> list = new ArrayList<>();
        list.add(look);
        list.add(chat);
        list.add(sign);
        list.add(end);
        return list;
    }

    public static String find(int v) {
        List<TechnicalRequirementsStatus> list = list();
        for (TechnicalRequirementsStatus technicalRequirementsStatus : list) {
            if (technicalRequirementsStatus.getValue() == v) {
                return technicalRequirementsStatus.getName();
            }
        }
        return "";
    }

    public static int findByName(String name) {
        List<TechnicalRequirementsStatus> list = list();
        for (TechnicalRequirementsStatus technicalRequirementsStatus : list) {
            if (technicalRequirementsStatus.getName().equals(name)) {
                return technicalRequirementsStatus.getValue();
            }
        }
        return 0;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static boolean judge(int value) {
        switch (value) {
            case 0:
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }
}
