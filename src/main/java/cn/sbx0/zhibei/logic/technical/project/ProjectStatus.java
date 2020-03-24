package cn.sbx0.zhibei.logic.technical.project;

import java.util.ArrayList;
import java.util.List;

public enum ProjectStatus {
    ing(0, "申请中"),
    corp(1, "合作中"),
    reject(2, "被拒绝")
    ;
    int value;
    String name;

    ProjectStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static List<ProjectStatus> list() {
        List<ProjectStatus> list = new ArrayList<>();
        list.add(ing);
        list.add(corp);
        list.add(reject);
        return list;
    }

    public static String find(int v) {
        List<ProjectStatus> list = list();
        for (ProjectStatus projectStatus : list) {
            if (projectStatus.getValue() == v) {
                return projectStatus.getName();
            }
        }
        return "";
    }

    public static int findByName(String name) {
        List<ProjectStatus> list = list();
        for (ProjectStatus projectStatus : list) {
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
