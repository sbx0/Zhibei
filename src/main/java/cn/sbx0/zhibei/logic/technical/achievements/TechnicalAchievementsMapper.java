package cn.sbx0.zhibei.logic.technical.achievements;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 技术成果 dao mybatis
 */
@Mapper
public interface TechnicalAchievementsMapper {
    /**
     * todo
     *
     * @param userId
     * @param maturity
     * @param cooperationMethod
     * @param addressId
     * @param classificationId
     * @param start
     * @param length
     * @return
     */
    List<TechnicalAchievements> findAllComplex(Integer userId, String attribute, String direction, Integer maturity, Integer cooperationMethod, String addressId, String classificationId, int start, int length);


    /**
     * todo
     *
     * @param userId
     * @param maturity
     * @param cooperationMethod
     * @param addressId
     * @param classificationId
     * @return
     */
    Integer countAllComplex(Integer userId, Integer maturity, Integer cooperationMethod, String addressId, String classificationId);
}
