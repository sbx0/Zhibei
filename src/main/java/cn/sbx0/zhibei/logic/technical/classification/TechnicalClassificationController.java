package cn.sbx0.zhibei.logic.technical.classification;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.address.AddressBase;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/technical/classification")
public class TechnicalClassificationController extends BaseController<TechnicalClassification, Integer> {
    @Resource
    private TechnicalClassificationService service;

    @Override
    public BaseService<TechnicalClassification, Integer> getService() {
        return service;
    }

    @LoginRequired
    @GetMapping("/father")
    public ObjectNode father() {
        ObjectNode json = initJSON();
        List<TechnicalClassification> list = service.findAllFather();
        ArrayNode jsons = initJSONs();
        for (TechnicalClassification o : list) {
            ObjectNode jsonOb = initJSON();
            jsonOb.put("name", o.getName());
            jsonOb.put("value", o.getId());
            jsons.add(jsonOb);
        }
        json.set(jsonObs, jsons);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @LoginRequired
    @GetMapping("/son")
    public ObjectNode son(Integer fatherId) {
        ObjectNode json = initJSON();
        List<TechnicalClassification> list = service.findAllSon(fatherId);
        ArrayNode jsons = initJSONs();
        for (TechnicalClassification o : list) {
            ObjectNode jsonOb = initJSON();
            jsonOb.put("name", o.getName());
            jsonOb.put("value", o.getId());
            jsons.add(jsonOb);
        }
        json.set(jsonObs, jsons);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }
}
