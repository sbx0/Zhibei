package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 文章 数据层
 */
public interface ArticleDao extends PagingAndSortingRepository<Article, Integer> {
    /**
     * 根据用户ID查询文章
     *
     * @param userId
     * @return
     */
    @Query(value = "FROM Article a WHERE a.author.id = ?1")
    Page<Article> findByAuthor(Integer userId, Pageable pageable);
}
