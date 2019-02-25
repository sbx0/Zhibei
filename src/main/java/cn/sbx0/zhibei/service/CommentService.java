package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.CommentDao;
import cn.sbx0.zhibei.entity.Comment;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.tool.StringTools;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 评论 服务层
 */
@Service
public class CommentService extends BaseService<Comment, Integer> {
    @Resource
    private CommentDao commentDao;

    @Override
    public PagingAndSortingRepository<Comment, Integer> getDao() {
        return commentDao;
    }

    @Override
    public Comment getEntity() {
        return new Comment();
    }

    /**
     * 发布评论
     *
     * @param comment
     * @return
     */
    public boolean post(Comment comment, User user) {
        if (user == null) return false;
        if (StringTools.checkNullStr(comment.getContent())) return false; // 内容为空
        if (StringTools.checkNullStr(comment.getPath())) return false; // 路径为空
        comment.setContent(StringTools.killHTML(comment.getContent()));
        comment.setTime(new Date());
        comment.setDislikes(0);
        comment.setLikes(0);
        comment.setTop(0);
        comment.setFloor(getFloor(comment.getPath()));
        comment.setPoster(user);
        return save(comment);
    }

    /**
     * 根据页面查找评论
     *
     * @param path
     * @param pageable
     * @return
     */
    public Page<Comment> findByPath(String path, Pageable pageable) {
        return commentDao.findByPath(path, pageable);
    }

    /**
     * 根据发布者查询评论
     *
     * @param user
     * @param pageable
     * @return
     */
    public Page<Comment> findByPoster(User user, Pageable pageable) {
        if (user == null) return null;
        return commentDao.findByPoster(user.getId(), pageable);
    }

    /**
     * 获取楼层
     *
     * @param path
     * @return
     */
    public Integer getFloor(String path) {
        Page<Comment> comments = commentDao.findByPath(path, buildPageable(1, 1, buildSort("floor", "desc")));
        if (comments.getTotalElements() == 0L) return 1;
        else return comments.getContent().get(0).getFloor() + 1;
    }
}
