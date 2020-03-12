package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.logic.user.certification.CertificationType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 成熟度
 */
public enum TechnicalMaturity {
    inDevelopment(0, "正在研发"),
    existingSample(1, "已有样品"),
    passTheQuiz(2, "通过小试"),
    passedThePilot(3, "通过中试"),
    canBeMassProduced(4, "可以量产"),
    ;
    int value;
    String name;

    TechnicalMaturity(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static List<TechnicalMaturity> list() {
        List<TechnicalMaturity> list = new ArrayList<>();
        list.add(inDevelopment);
        list.add(existingSample);
        list.add(passTheQuiz);
        list.add(passedThePilot);
        list.add(canBeMassProduced);
        return list;
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
            case 4:
                return true;
            default:
                return false;
        }
    }
}
