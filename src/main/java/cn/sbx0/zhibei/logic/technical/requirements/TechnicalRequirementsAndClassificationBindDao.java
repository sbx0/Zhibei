package cn.sbx0.zhibei.logic.technical.requirements;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 技术需求和分类绑定 数据层
 */
public interface TechnicalRequirementsAndClassificationBindDao extends PagingAndSortingRepository<TechnicalRequirementsAndClassificationBind, Integer> {

}