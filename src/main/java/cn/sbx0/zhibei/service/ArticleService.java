package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.ArticleDao;
import cn.sbx0.zhibei.entity.Article;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文章 服务层
 */
@Service
public class ArticleService extends BaseService<Article, Integer> {
    @Resource
    ArticleDao articleDao;

    @Override
    public PagingAndSortingRepository<Article, Integer> getDao() {
        return articleDao;
    }

    @Override
    public Article getEntity() {
        return new Article();
    }
}
