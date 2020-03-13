package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBase;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
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
            objectNode.put("maturity", TechnicalMaturity.find(technicalAchievements.getMaturity()));
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
    @LoginRequired
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
    @LoginRequired
    @GetMapping("/cooperationMethod/list")
    public ObjectNode type() {
        ObjectNode json = initJSON();
        json.set("objects", service.technicalCooperationMethodList());
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }
}
