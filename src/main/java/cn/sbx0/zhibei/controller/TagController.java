package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.Tag;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.TagService;
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
 * 标签 控制层
 */
@RequestMapping(value = "/tag")
@Controller
public class TagController extends BaseController<Tag, Integer> {
    @Resource
    private TagService tagService;

    @Override
    public BaseService<Tag, Integer> getService() {
        return tagService;
    }

    @Autowired
    public TagController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 查找所有一级标签
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/father")
    public ObjectNode father() {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Tag> tagPage = tagService.findFather(BaseService.buildPageable(1, 999, "id", "DESC"));
        List<Tag> tagList = tagPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tagList != null && tagList.size() > 0) {
            for (Tag t : tagList) {
                ObjectNode object = mapper.convertValue(t, ObjectNode.class);
                jsons.add(object);
            }
            json.set("objects", jsons);
        }
        return json;
    }

    /**
     * 根据父标签查找子标签
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/child")
    public ObjectNode child(Integer id) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Tag> tagPage = tagService.findByFather(id, BaseService.buildPageable(1, 999, "id", "DESC"));
        List<Tag> tagList = tagPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tagList != null && tagList.size() > 0) {
            for (Tag t : tagList) {
                ObjectNode object = mapper.convertValue(t, ObjectNode.class);
                jsons.add(object);
            }
            json.set("objects" + id, jsons);
        }
        return json;
    }

    /**
     * 根据名称搜索
     *
     * @param name
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/name")
    public ObjectNode findByName(String name, Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Tag> tPage = tagService.findByName(name, (BaseService.buildPageable(page, size, attribute, direction)));
        List<Tag> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Tag t : tList) {
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
