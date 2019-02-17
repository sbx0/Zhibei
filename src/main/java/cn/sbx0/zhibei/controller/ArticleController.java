package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Article;
import cn.sbx0.zhibei.service.ArticleService;
import cn.sbx0.zhibei.service.BaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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
}
