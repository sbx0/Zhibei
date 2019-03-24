package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.TagDao;
import cn.sbx0.zhibei.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 标签 服务层
 */
@Service
public class TagService extends BaseService<Tag, Integer> {
    @Resource
    private TagDao tagDao;

    @Override
    public PagingAndSortingRepository<Tag, Integer> getDao() {
        return tagDao;
    }

    @Override
    public Tag getEntity() {
        return new Tag();
    }

    /**
     * 查找所有一级标签
     *
     * @param pageable
     * @return
     */
    public Page<Tag> findFather(Pageable pageable) {
        return tagDao.findFather(pageable);
    }

    /**
     * 根据父标签查找子标签
     *
     * @param f_id
     * @param pageable
     * @return
     */
    public Page<Tag> findByFather(Integer f_id, Pageable pageable) {
        return tagDao.findByFather(f_id, pageable);
    }

    /**
     * 根据名称查询
     *
     * @param name
     * @param pageable
     * @return
     */
    public Page<Tag> findByName(String name, Pageable pageable) {
        return tagDao.findByName(name, pageable);
    }
}
