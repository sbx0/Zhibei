package cn.sbx0.zhibei.logic.technical.classification;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 技术分类 数据层
 */
public interface TechnicalClassificationDao extends PagingAndSortingRepository<TechnicalClassification, Integer> {

}