package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.ExpFunction;
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
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.suggest.Suggester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
     * 搜索
     *
     * @param keyword
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/search")
    public ObjectNode search(String keyword) {
        int size = 999;
        List<Article> articleList = getData(size);
        Suggester suggester = new Suggester();
        for (Article article : articleList) {
            suggester.addSentence(article.getTitle());
        }
        List<String> result = buildSuggester(size).suggest(keyword, size / 5);
        ArrayNode jsons = mapper.createArrayNode();
        for (String s : result) {
            for (Article article : articleList) {
                if (article.getTitle().equals(s)) {
                    ObjectNode object = mapper.convertValue(article, ObjectNode.class);
                    jsons.add(object);
                }
            }
        }
        json = mapper.createObjectNode();
        json.set("result", jsons);
        return json;
    }

    /**
     * 获取数据
     *
     * @param size
     * @return
     */
    private List<Article> getData(int size) {
        Page<Article> articles = articleService.findAll(BaseService.buildPageable(1, size, "id", "DESC"));
        return articles.getContent();
    }

    /**
     * 构建推荐器
     *
     * @param size
     * @return
     */
    private Suggester buildSuggester(int size) {
        List<Article> articleList = getData(size);
        Suggester suggester = new Suggester();
        for (Article article : articleList) {
            suggester.addSentence(article.getTitle());
        }
        return suggester;
    }

    /**
     * 根据关键词推荐
     *
     * @param keyword
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/suggest")
    public ObjectNode suggest(String keyword) {
        int size = 999;
        List<String> result = buildSuggester(size).suggest(keyword, size / 100);
        ArrayNode jsons = mapper.createArrayNode();
        List<String> keywordResult = new ArrayList<>();
        for (String s : result) {
            List<String> extractKeyword = HanLP.extractKeyword(s, 1);
            for (String k : extractKeyword) {
                boolean isRepeat = false;
                for (String key : keywordResult) {
                    if (key.equals(k)) {
                        isRepeat = true;
                    }
                }
                if (!isRepeat) {
                    keywordResult.add(k);
                    jsons.add(k);
                }
            }
        }
        json = mapper.createObjectNode();
        json.set("suggest", jsons);
        return json;
    }

    /**
     * 用户页获取文章
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/tag")
    public ObjectNode tag(Integer id, Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Article> tPage = articleService.findByTag(id, (BaseService.buildPageable(page, size, "a.time", direction)));
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

    /**
     * 用户页获取文章
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/user")
    public ObjectNode user(Integer id, Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Article> tPage = articleService.findByAuthor(id, (BaseService.buildPageable(page, size, attribute, direction)));
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

    /**
     * 发布文章
     *
     * @param article
     * @return
     */
    @ExpFunction(value = "25")
    @LogRecord
    @ResponseBody
    @PostMapping("/post")
    public ObjectNode post(Article article) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
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
            json = add(article);
        } else {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
        }
        return json;
    }

}
