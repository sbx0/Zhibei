package cn.sbx0.zhibei.logic.technical.requirements;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 技术需求 数据层
 */
public interface TechnicalRequirementsDao extends PagingAndSortingRepository<TechnicalRequirements, Integer> {
    @Query(value = "select * from technical_requirements where name = ?1", nativeQuery = true)
    TechnicalRequirements findByName(String name);
}