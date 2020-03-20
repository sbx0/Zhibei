package cn.sbx0.zhibei.logic.technical.achievements;

import lombok.Data;

@Data
public class Receives {
    Integer userId;
    String attribute;
    String direction;
    Integer maturity;
    Integer cooperationMethod;
    String[] addressId;
    String[] classificationId;
    String kind;
    String status;
    Integer page;
    Integer size;
}
