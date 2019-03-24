package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.ExpFunction;
import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.Comment;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.CommentService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论 控制层
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController<Comment, Integer> {
    @Resource
    private CommentService commentService;

    @Override
    public BaseService<Comment, Integer> getService() {
        return commentService;
    }

    @Autowired
    public CommentController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 发布评论
     *
     * @param comment
     * @return
     */
    @ExpFunction(value = "5")
    @LogRecord
    @ResponseBody
    @PostMapping("/post")
    public ObjectNode post(Comment comment) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user != null) {
            if (commentService.post(comment, user)) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }

    /**
     * 任意页面获取评论
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/load")
    public ObjectNode load(String path, Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Comment> tPage = commentService.findByPath(path, (BaseService.buildPageable(page, size, attribute, direction)));
        List<Comment> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Comment c : tList) {
                ObjectNode object = mapper.convertValue(c, ObjectNode.class);
                jsons.add(object);
            }
            json.set("objects", jsons);
        } else {
            json.set("objects", null);
        }
        return json;
    }

    /**
     * 根据用户查询评论
     *
     * @param id
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/load/user")
    public ObjectNode loadByUser(Integer id, Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        User user = userService.findById(id);
        Page<Comment> tPage = commentService.findByPoster(user, (BaseService.buildPageable(page, size, attribute, direction)));
        List<Comment> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Comment c : tList) {
                ObjectNode object = mapper.convertValue(c, ObjectNode.class);
                jsons.add(object);
            }
            json.set("objects", jsons);
        } else {
            json.set("objects", null);
        }
        return json;
    }

}
