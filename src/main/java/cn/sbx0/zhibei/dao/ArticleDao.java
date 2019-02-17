package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Article;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 文章 数据层
 */
public interface ArticleDao extends PagingAndSortingRepository<Article, Integer> {

}
