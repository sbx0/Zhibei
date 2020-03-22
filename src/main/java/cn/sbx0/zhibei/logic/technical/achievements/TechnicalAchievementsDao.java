package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.logic.user.base.UserBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 技术成果 数据层
 */
public interface TechnicalAchievementsDao extends PagingAndSortingRepository<TechnicalAchievements, Integer> {
    @Query(value = "select * from technical_achievements where name = ?1", nativeQuery = true)
    TechnicalAchievements findByName(String name);
}