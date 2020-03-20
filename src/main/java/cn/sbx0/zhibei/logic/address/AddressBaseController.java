package cn.sbx0.zhibei.logic.address;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/address/base")
public class AddressBaseController extends BaseController<AddressBase, String> {
    @Resource
    private AddressBaseService service;

    @Override
    public BaseService<AddressBase, String> getService() {
        return service;
    }

    @GetMapping("/sonToFather")
    public ObjectNode sonToFather(String sonId) {
        ObjectNode json = initJSON();
        List<AddressBase> list = service.sonToFather(sonId);
        json.set(jsonObs, service.convertToJsons(list));
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @LoginRequired
    @GetMapping("/init")
    public ObjectNode init() {
        ObjectNode json = initJSON();
        json.put(statusCode, service.init().getCode());
        return json;
    }

    @GetMapping("/father")
    public ObjectNode father() {
        ObjectNode json = initJSON();
        List<AddressBase> list = service.findAllFather();
        ArrayNode jsons = initJSONs();
        for (AddressBase o : list) {
            ObjectNode jsonOb = initJSON();
            jsonOb.put("name", o.getName());
            jsonOb.put("value", o.getId());
            jsons.add(jsonOb);
        }
        json.set(jsonObs, jsons);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @GetMapping("/son")
    public ObjectNode son(String fatherId) {
        ObjectNode json = initJSON();
        List<AddressBase> list = service.findAllSon(fatherId);
        ArrayNode jsons = initJSONs();
        for (AddressBase o : list) {
            ObjectNode jsonOb = initJSON();
            jsonOb.put("name", o.getName());
            jsonOb.put("value", o.getId());
            jsons.add(jsonOb);
        }
        json.set(jsonObs, jsons);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @PostMapping("/sons")
    public ObjectNode sons(ReceiveFatherIds receive) {
        ObjectNode json = initJSON();
        if (receive != null) {
            List<AddressBase> list = service.findAllSons(receive.getFatherIds());
            ArrayNode jsons = initJSONs();
            for (AddressBase o : list) {
                ObjectNode jsonOb = initJSON();
                jsonOb.put("name", o.getName());
                jsonOb.put("value", o.getId());
                jsons.add(jsonOb);
            }
            json.set(jsonObs, jsons);
        } else {
            json.set(jsonObs, initJSONs());
        }
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }
}
