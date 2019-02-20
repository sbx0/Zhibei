package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.Article;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.ArticleService;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.tool.StringTools;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 文章 控制层
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController<Article, Integer> {
    @Resource
    private ArticleService articleService;

    @Override
    public BaseService<Article, Integer> getService() {
        return articleService;
    }

    @Autowired
    public ArticleController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 发布文章
     *
     * @param article
     * @param request
     * @return
     */
    @LogRecord
    @ResponseBody
    @PostMapping("/post")
    public ObjectNode post(Article article, HttpServletRequest request) {
        json = mapper.createObjectNode();
        User user = userService.getUser(request);
        article.setId(null);
        if (!StringTools.checkNullStr(article.getTitle())
                && !StringTools.checkNullStr(article.getIntroduction())
                && !StringTools.checkNullStr(article.getContent())
        ) {
            article.setTitle(StringTools.killHTML(article.getTitle().trim()));
            article.setContent(article.getContent().trim());
            article.setIntroduction(article.getIntroduction().trim());
            article.setTime(new Date());
            article.setComments(0);
            article.setLikes(0);
            article.setDislikes(0);
            article.setTop(0);
            article.setViews(0);
            article.setAuthor(user);
            article.setPassword(null);
            article.setLastChangeTime(null);
            json = add(article, request);
        } else {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
        }
        return json;
    }

    /**
     * 首页获取文章
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/index")
    public ObjectNode index(Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        if (page == null) page = 1;
        if (size == null) size = 10;
        if (attribute == null) attribute = "id";
        if (direction == null) direction = "desc";
        Sort sort = BaseService.buildSort(attribute, direction);
        Page<Article> tPage = getService().findAll(BaseService.buildPageable(page, size, sort));
        List<Article> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Article t : tList) {
                ObjectNode object = mapper.convertValue(t, ObjectNode.class);
                jsons.add(object);
            }
            json.set("objects", jsons);
        } else {
            json.set("objects", null);
        }
        return json;
    }

}
