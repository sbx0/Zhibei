package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import cn.sbx0.zhibei.tool.StringTools;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/technical/achievements")
public class TechnicalAchievementsController extends BaseController<TechnicalAchievements, Integer> {
    @Resource
    private TechnicalAchievementsService service;
    @Resource
    private UserBaseService userBaseService;

    @Override
    public BaseService<TechnicalAchievements, Integer> getService() {
        return service;
    }

//    @LoginRequired
    @GetMapping("/init")
    public ObjectNode init() {
        ObjectNode json = initJSON();
        json.put(statusCode, service.init().getCode());
        return json;
    }

    /**
     * todo
     *
     * @return
     */
    @GetMapping("/attribute")
    public ObjectNode attribute() {
        ObjectNode json = initJSON();
        List<Map> attributes = getService().getAttribute();
        ArrayNode jsons = getMapper().createArrayNode();
        for (Map info : attributes) {
            ObjectNode objectNode = initJSON();
            objectNode.put("name", info.get("name").toString());
            objectNode.put("value", StringTools.humpToLine(info.get("name").toString()));
            jsons.add(objectNode);
        }
        json.set("objects", jsons);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @PostMapping("/mybatis/lists")
    public ObjectNode mybatisLists(Receives receives) {
        ObjectNode json = initJSON();
        Integer total = service.countAllComplexs(
                receives.getUserId(),
                receives.getMaturity(),
                receives.getCooperationMethod(),
                receives.getAddressId(),
                receives.getClassificationId()
        );
        List<TechnicalAchievements> list = service.findAllComplexs(
                receives.getUserId(),
                receives.getAttribute(),
                receives.getDirection(),
                receives.getMaturity(),
                receives.getCooperationMethod(),
                receives.getAddressId(),
                receives.getClassificationId(),
                receives.getPage(),
                receives.getSize(),
                total
        );
        if (list != null && list.size() > 0) {
            ArrayNode jsons = initJSONs();
            for (TechnicalAchievements technicalAchievements : list) {
                jsons.add(getMapper().convertValue(technicalAchievements, ObjectNode.class));
            }
            json.set("objects", jsons);
        }
        json.put("page", receives.getPage());
        json.put("size", receives.getSize());
        json.put("total", total);
        if (total > receives.getSize())
            json.put("total_pages", total / receives.getSize());
        else
            json.put("total_pages", 1);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @GetMapping("/mybatis/list")
    public ObjectNode mybatisList(Integer userId, String attribute, String direction, Integer maturity, Integer cooperationMethod, String addressId, String classificationId, String status, Integer page, Integer size) {
        ObjectNode json = initJSON();
        Integer total = service.countAllComplex(userId, maturity, cooperationMethod, addressId, classificationId);
        List<TechnicalAchievements> list = service.findAllComplex(userId, attribute, direction, maturity, cooperationMethod, addressId, classificationId, page, size, total);
        if (list != null && list.size() > 0) {
            ArrayNode jsons = initJSONs();
            for (TechnicalAchievements technicalAchievements : list) {
                jsons.add(getMapper().convertValue(technicalAchievements, ObjectNode.class));
            }
            json.set("objects", jsons);
        }
        json.put("page", page);
        json.put("size", size);
        json.put("total", total);
        if (total > size)
            json.put("total_pages", total / size);
        else
            json.put("total_pages", 1);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * todo
     *
     * @return
     */
    @GetMapping("/count")
    public ObjectNode count() {
        ObjectNode json = initJSON();
        long count = service.count();
        json.put(jsonOb, count);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * todo
     *
     * @param id
     * @return
     */
    @GetMapping("/one")
    public ObjectNode one(Integer id) {
        ObjectNode json = initJSON();
        TechnicalAchievements technicalAchievements = service.findById(id);
        if (technicalAchievements != null) {
            ObjectNode objectNode = service.convertToJson(technicalAchievements);
            objectNode.put("cooperationMethod", TechnicalCooperationMethod.find(technicalAchievements.getCooperationMethod()));
            objectNode.put("cooperationMethodId", technicalAchievements.getCooperationMethod());
            objectNode.put("maturity", TechnicalMaturity.find(technicalAchievements.getMaturity()));
            objectNode.put("maturityId", technicalAchievements.getMaturity());
            json.set(jsonOb, objectNode);
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.invalidValue.getCode());
        }
        return json;
    }

    /**
     * todo
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @GetMapping("/list")
    public ObjectNode list(Integer page, Integer size, String attribute, String direction) {
        ObjectNode json = initJSON();
        Page<TechnicalAchievements> tPage = normalList(page, size, attribute, direction);
        List<TechnicalAchievements> tList = tPage.getContent();
        ArrayNode jsons = getMapper().createArrayNode();
        if (tList.size() > 0) {
            for (TechnicalAchievements t : tList) {
                ObjectNode object = getMapper().convertValue(t, ObjectNode.class);
                jsons.add(object);
            }
        }
        json.set("objects", jsons);
        json.put("total_pages", tPage.getTotalPages());
        json.put("total_elements", tPage.getTotalElements());
        json.put("page", page);
        json.put("size", size);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * todo
     *
     * @return
     */
    @LoginRequired
    @PostMapping(value = "/post")
    public ObjectNode post(TechnicalAchievements achievements) {
        ObjectNode json = initJSON();
        Integer userId = userBaseService.getLoginUserId();
        if (service.checkDataValidity(achievements)) {
            achievements.setUserId(userId);
            achievements.setPostTime(new Date());
            service.save(achievements);
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.invalidValue.getCode());
        }
        return json;
    }

    /**
     * todo
     *
     * @return json
     */
    @GetMapping("/maturity/list")
    public ObjectNode maturityList() {
        ObjectNode json = initJSON();
        json.set("objects", service.technicalMaturityList());
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * todo
     *
     * @return json
     */
    @GetMapping("/cooperationMethod/list")
    public ObjectNode type() {
        ObjectNode json = initJSON();
        json.set("objects", service.technicalCooperationMethodList());
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }
}
