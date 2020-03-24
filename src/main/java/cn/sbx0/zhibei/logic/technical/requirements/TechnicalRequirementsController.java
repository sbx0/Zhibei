package cn.sbx0.zhibei.logic.technical.requirements;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.technical.achievements.TechnicalCooperationMethod;
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
@RequestMapping("/technical/requirements")
public class TechnicalRequirementsController extends BaseController<TechnicalRequirements, Integer> {
    @Resource
    private TechnicalRequirementsService service;
    @Resource
    private UserBaseService userBaseService;

    @Override
    public BaseService<TechnicalRequirements, Integer> getService() {
        return service;
    }

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
    public ObjectNode mybatisLists(TechnicalRequirementsReceives receives) {
        ObjectNode json = initJSON();
        Integer total = service.countAllComplexs(
                receives.getUserId(),
                receives.getCooperationMethod(),
                receives.getAddressId(),
                receives.getClassificationId()
        );
        List<TechnicalRequirements> list = service.findAllComplexs(
                receives.getUserId(),
                receives.getAttribute(),
                receives.getDirection(),
                receives.getCooperationMethod(),
                receives.getAddressId(),
                receives.getClassificationId(),
                receives.getPage(),
                receives.getSize(),
                total
        );
        if (list != null && list.size() > 0) {
            ArrayNode jsons = initJSONs();
            for (TechnicalRequirements technicalRequirements : list) {
                jsons.add(getMapper().convertValue(technicalRequirements, ObjectNode.class));
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
    public ObjectNode mybatisList(Integer userId, String attribute, String direction,   Integer cooperationMethod, String addressId, String classificationId, String status, Integer page, Integer size) {
        ObjectNode json = initJSON();
        Integer total = service.countAllComplex(userId, cooperationMethod, addressId, classificationId);
        List<TechnicalRequirements> list = service.findAllComplex(userId, attribute, direction,  cooperationMethod, addressId, classificationId, page, size, total);
        if (list != null && list.size() > 0) {
            ArrayNode jsons = initJSONs();
            for (TechnicalRequirements technicalRequirements : list) {
                jsons.add(getMapper().convertValue(technicalRequirements, ObjectNode.class));
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

    @GetMapping("/count")
    public ObjectNode count() {
        ObjectNode json = initJSON();
        long count = service.count();
        json.put(jsonOb, count);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @GetMapping("/one")
    public ObjectNode one(Integer id) {
        ObjectNode json = initJSON();
        TechnicalRequirements technicalRequirements = service.findById(id);
        if (technicalRequirements != null) {
            ObjectNode objectNode = service.convertToJson(technicalRequirements);
            objectNode.put("cooperationMethod", TechnicalCooperationMethod.find(technicalRequirements.getCooperationMethod()));
            objectNode.put("cooperationMethodId", technicalRequirements.getCooperationMethod());
            json.set(jsonOb, objectNode);
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.invalidValue.getCode());
        }
        return json;
    }

    @GetMapping("/list")
    public ObjectNode list(Integer page, Integer size, String attribute, String direction) {
        ObjectNode json = initJSON();
        Page<TechnicalRequirements> tPage = normalList(page, size, attribute, direction);
        List<TechnicalRequirements> tList = tPage.getContent();
        ArrayNode jsons = getMapper().createArrayNode();
        if (tList.size() > 0) {
            for (TechnicalRequirements t : tList) {
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

    @LoginRequired
    @PostMapping(value = "/post")
    public ObjectNode post(TechnicalRequirements technicalRequirements) {
        ObjectNode json = initJSON();
        Integer userId = userBaseService.getLoginUserId();
        if (service.checkDataValidity(technicalRequirements)) {
            technicalRequirements.setUserId(userId);
            technicalRequirements.setPostTime(new Date());
            service.save(technicalRequirements);
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.invalidValue.getCode());
        }
        return json;
    }

    @GetMapping("/status/list")
    public ObjectNode statusList() {
        ObjectNode json = initJSON();
        json.set("objects", service.technicalRequirementsStatusList());
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }
}
