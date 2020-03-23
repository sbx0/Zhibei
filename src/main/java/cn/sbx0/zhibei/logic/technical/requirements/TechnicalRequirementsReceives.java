package cn.sbx0.zhibei.logic.technical.requirements;

import lombok.Data;

@Data
public class TechnicalRequirementsReceives {
    Integer userId;
    String attribute;
    String direction;
    Integer cooperationMethod;
    String[] addressId;
    String[] classificationId;
    String kind;
    String status;
    Integer page;
    Integer size;
}
