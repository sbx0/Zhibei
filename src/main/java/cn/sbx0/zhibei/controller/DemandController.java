package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.ExpFunction;
import cn.sbx0.zhibei.entity.Demand;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.DemandService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 需求 控制层
 */
@Controller
@RequestMapping("/demand")
public class DemandController extends BaseController<Demand, Integer> {
    @Resource
    private DemandService demandService;

    @Override
    public BaseService<Demand, Integer> getService() {
        return demandService;
    }

    @Autowired
    public DemandController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 搜索
     *
     * @param keyword
     * @return
     */
    @ResponseBody
    @GetMapping("/search")
    public ObjectNode search(String keyword) {
        int size = 999;
        List<Demand> demandList = getData(size);
        Suggester suggester = new Suggester();
        for (Demand demand : demandList) {
            suggester.addSentence(demand.getTitle() + "。" + demand.getContent());
        }
        List<String> result = buildSuggester(size).suggest(keyword, size / 5);
        ArrayNode jsons = mapper.createArrayNode();
        for (String s : result) {
            for (Demand demand : demandList) {
                if (demand.getTitle().equals(s)) {
                    ObjectNode object = mapper.convertValue(demand, ObjectNode.class);
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
    private List<Demand> getData(int size) {
        Page<Demand> demands = demandService.findAll(BaseService.buildPageable(1, size, "id", "DESC"));
        return demands.getContent();
    }

    /**
     * 构建推荐器
     *
     * @param size
     * @return
     */
    private Suggester buildSuggester(int size) {
        List<Demand> demandList = getData(size);
        Suggester suggester = new Suggester();
        for (Demand demand : demandList) {
            suggester.addSentence(demand.getTitle());
        }
        return suggester;
    }

    /**
     * 分析提取关键词
     *
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/analysis")
    public ObjectNode analysis(Integer id) {
        json = mapper.createObjectNode();
        Demand demand = demandService.findById(id);
        String pool = demand.getContent();
        List<String> keywords = HanLP.extractPhrase(pool, 5);
        ArrayNode jsons = mapper.createArrayNode();
        for (String keyword : keywords) {
            jsons.add(keyword);
        }
        json.set("keywords", jsons);
        return json;
    }

    /**
     * 标签页获取需求
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @ResponseBody
    @GetMapping("/tag")
    public ObjectNode tag(Integer id, Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Demand> tPage = demandService.findByTag(id, (BaseService.buildPageable(page, size, "d.time", direction)));
        List<Demand> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Demand t : tList) {
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
     * 用户页获取需求
     *
     * @param id
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @ResponseBody
    @GetMapping("/user")
    public ObjectNode user(Integer id, Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.Simple.class));
        json = mapper.createObjectNode();
        Page<Demand> tPage = demandService.findByPoster(id, (BaseService.buildPageable(page, size, attribute, direction)));
        List<Demand> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Demand t : tList) {
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
     * 发布需求
     *
     * @param demand
     * @return
     */
    @ExpFunction(value = "25")
    @ResponseBody
    @PostMapping("/post")
    public ObjectNode post(Demand demand) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        demand.setId(null);
        if (!StringTools.checkNullStr(demand.getTitle())
                && !StringTools.checkNullStr(demand.getContent())
        ) {
            demand.setTitle(StringTools.killHTML(demand.getTitle().trim()));
            demand.setContent(demand.getContent().trim());
            demand.setTime(new Date());
            demand.setPoster(user);
            json = add(demand);
        } else {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
        }
        return json;
    }
}
