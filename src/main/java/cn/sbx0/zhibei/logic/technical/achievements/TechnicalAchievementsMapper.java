package cn.sbx0.zhibei.logic.technical.achievements;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 技术成果 dao mybatis
 */
@Mapper
public interface TechnicalAchievementsMapper {
    List<TechnicalAchievements> findAllComplexs(Integer userId, String attribute, String direction, Integer maturity, Integer cooperationMethod, String[] addressId, String[] classificationId, int start, int length);
    Integer countAllComplexs(Integer userId, Integer maturity, Integer cooperationMethod, String[] addressId, String[] classificationId);
    List<TechnicalAchievements> findAllComplex(Integer userId, String attribute, String direction, Integer maturity, Integer cooperationMethod, String addressId, String classificationId, int start, int length);
    Integer countAllComplex(Integer userId, Integer maturity, Integer cooperationMethod, String addressId, String classificationId);
}
