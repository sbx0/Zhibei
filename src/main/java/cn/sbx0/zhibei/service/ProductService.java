package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.ProductDao;
import cn.sbx0.zhibei.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductService extends BaseService<Product, Integer> {
    @Resource
    private ProductDao productDao;

    @Override
    public PagingAndSortingRepository<Product, Integer> getDao() {
        return productDao;
    }

    @Override
    public Product getEntity() {
        return new Product();
    }
}
