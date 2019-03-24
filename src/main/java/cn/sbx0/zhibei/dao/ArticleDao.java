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

    /**
     * 根据标签查询文章
     *
     * @param t_id
     * @param pageable
     * @return
     */
    @Query(value = "select * from articles a1 where a1.id in (select at1.article_id from articles_tags at1 where at1.tags_id = ?1) union select * from articles a2 where a2.id in (select at2.article_id from articles_tags at2 where at2.tags_id in (select t2.id from tags t2 where t2.father_id = ?1))", nativeQuery = true)
    Page<Article> findByTag(Integer t_id, Pageable pageable);
}
