package cn.sbx0.zhibei.logic.technical.requirements;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TechnicalRequirementsMapper {
    List<TechnicalRequirements> findAllComplexs(Integer userId, String attribute, String direction, Integer cooperationMethod, String[] addressId, String[] classificationId, int start, int length);

    Integer countAllComplexs(Integer userId, Integer cooperationMethod, String[] addressId, String[] classificationId);

    List<TechnicalRequirements> findAllComplex(Integer userId, String attribute, String direction, Integer cooperationMethod, String addressId, String classificationId, int start, int length);

    Integer countAllComplex(Integer userId, Integer cooperationMethod, String addressId, String classificationId);
}
