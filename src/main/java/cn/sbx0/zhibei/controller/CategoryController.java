package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.Category;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.CategoryService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分类 控制层
 */
@RequestMapping(value = "/category")
@Controller
public class CategoryController extends BaseController<Category, Integer> {
    @Resource
    private CategoryService categoryService;

    @Override
    public BaseService<Category, Integer> getService() {
        return categoryService;
    }

    @Autowired
    public CategoryController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @LogRecord
    @ResponseBody
    @GetMapping(value = "/name")
    public ObjectNode findByName(String name, Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Category> tPage = categoryService.findByName(name, (BaseService.buildPageable(page, size, attribute, direction)));
        List<Category> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Category t : tList) {
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
