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
     * @param addressId
     * @param classificationId
     * @param start
     * @param end
     * @return
     */
    List<TechnicalAchievements> findAllComplex(Integer maturity, Integer cooperationMethod, String addressId, String classificationId, int start, int length);


    /**
     * todo
     *
     * @param addressId
     * @param classificationId
     * @return
     */
    Integer countAllComplex(Integer maturity, Integer cooperationMethod, String addressId, String classificationId);
}
