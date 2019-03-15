package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 商品 数据层
 */
public interface ProductDao extends PagingAndSortingRepository<Product, Integer> {

}
