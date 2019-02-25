package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 评论 数据层
 */
public interface CommentDao extends PagingAndSortingRepository<Comment, Integer> {
    /**
     * 根据页面查询评论
     *
     * @param path
     * @param pageable
     * @return
     */
    @Query(value = "FROM Comment c WHERE c.path = ?1")
    Page<Comment> findByPath(String path, Pageable pageable);

    /**
     * 根据发布者查询评论
     *
     * @param userId
     * @param pageable
     * @return
     */
    @Query(value = "FROM Comment c WHERE c.poster.id = ?1")
    Page<Comment> findByPoster(Integer userId, Pageable pageable);
}
