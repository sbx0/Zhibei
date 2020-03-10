package cn.sbx0.zhibei.logic.technical.requirements;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 技术需求 数据层
 */
public interface TechnicalRequirementsDao extends PagingAndSortingRepository<TechnicalRequirements, Integer> {

}