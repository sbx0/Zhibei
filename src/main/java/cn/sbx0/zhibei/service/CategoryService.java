package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.CategoryDao;
import cn.sbx0.zhibei.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 分类 服务层
 */
@Service
public class CategoryService extends BaseService<Category, Integer> {
    @Resource
    private CategoryDao categoryDao;

    @Override
    public PagingAndSortingRepository<Category, Integer> getDao() {
        return categoryDao;
    }

    @Override
    public Category getEntity() {
        return new Category();
    }

    /**
     * 根据名称查询
     *
     * @param name
     * @param pageable
     * @return
     */
    public Page<Category> findByName(String name, Pageable pageable) {
        return categoryDao.findByName(name, pageable);
    }
}
