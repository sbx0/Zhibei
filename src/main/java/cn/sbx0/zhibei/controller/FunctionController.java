package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.Function;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.FunctionService;
import cn.sbx0.zhibei.tool.StringTools;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 操作 控制层
 */
@Controller
@RequestMapping(value = "/function")
public class FunctionController extends BaseController<Function, Integer> {
    @Resource
    private FunctionService functionService;

    @Override
    public BaseService<Function, Integer> getService() {
        return functionService;
    }

    @Autowired
    public FunctionController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 加载以前执行的操作
     *
     * @param path
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/load")
    public ObjectNode load(String path) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (StringTools.checkNullStr(path)) {
            json.put(STATUS_NAME, STATUS_CODE_PARAMETER_ERROR);
            return json;
        }
        if (user != null) {
            Page<Function> tPage = functionService.findByPathAndUser(path, user.getId(), BaseService.buildPageable(1, 10, "id", "DESC"));
            List<Function> tList = tPage.getContent();
            ArrayNode jsons = mapper.createArrayNode();
            if (tList != null && tList.size() > 0) {
                for (Function t : tList) {
                    ObjectNode object = mapper.convertValue(t, ObjectNode.class);
                    jsons.add(object);
                }
                json.set("objects", jsons);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }

    /**
     * 操作执行
     *
     * @param path
     * @param type
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/run")
    public ObjectNode run(String path, String type) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (StringTools.checkNullStr(path)) {
            json.put(STATUS_NAME, STATUS_CODE_PARAMETER_ERROR);
            return json;
        }
        if (user != null) {
            Function old = functionService.findByPathAndUserAndType(path, user.getId(), type);
            if (old != null) {
                if (functionService.delete(old)) {
                    json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
                    return json;
                } else {
                    json.put(STATUS_NAME, STATUS_CODE_FILED);
                    return json;
                }
            }
            Function function = new Function();
            function.setPath(path);
            function.setTime(new Date());
            function.setType(type);
            function.setUser(user);
            if (functionService.save(function)) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        }
        return json;
    }
}
