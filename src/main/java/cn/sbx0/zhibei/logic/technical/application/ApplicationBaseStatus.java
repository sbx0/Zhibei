package cn.sbx0.zhibei.logic.technical.application;

import java.util.ArrayList;
import java.util.List;

public enum ApplicationBaseStatus {
    ing(0, "申请中"),
    corp(1, "合作中"),
    reject(2, "被拒绝")
    ;
    int value;
    String name;

    ApplicationBaseStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static List<ApplicationBaseStatus> list() {
        List<ApplicationBaseStatus> list = new ArrayList<>();
        list.add(ing);
        list.add(corp);
        list.add(reject);
        return list;
    }

    public static String find(int v) {
        List<ApplicationBaseStatus> list = list();
        for (ApplicationBaseStatus projectStatus : list) {
            if (projectStatus.getValue() == v) {
                return projectStatus.getName();
            }
        }
        return "";
    }

    public static int findByName(String name) {
        List<ApplicationBaseStatus> list = list();
        for (ApplicationBaseStatus projectStatus : list) {
            if (projectStatus.getName().equals(name)) {
                return projectStatus.getValue();
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
                return true;
            default:
                return false;
        }
    }
}
