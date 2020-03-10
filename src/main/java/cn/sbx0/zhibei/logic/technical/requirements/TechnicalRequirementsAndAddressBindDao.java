package cn.sbx0.zhibei.logic.technical.requirements;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 技术需求和地区绑定 数据层
 */
public interface TechnicalRequirementsAndAddressBindDao extends PagingAndSortingRepository<TechnicalRequirementsAndAddressBind, Integer> {

}