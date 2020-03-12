package cn.sbx0.zhibei.logic.address;

import cn.sbx0.zhibei.annotation.LoginRequired;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.certification.CertificationType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/address/base")
public class AddressBaseController extends BaseController<AddressBase, Integer> {
    @Resource
    private AddressBaseService service;

    @Override
    public BaseService<AddressBase, Integer> getService() {
        return service;
    }

    @LoginRequired
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

    @LoginRequired
    @GetMapping("/son")
    public ObjectNode son(Integer fatherId) {
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
}
