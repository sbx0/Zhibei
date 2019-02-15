package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.Permission;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.PermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限 控制层
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController<Permission, Integer> {
    @Resource
    private PermissionService permissionService;

    @Override
    public BaseService<Permission, Integer> getService() {
        return permissionService;
    }

    @Autowired
    public PermissionController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    WebApplicationContext applicationContext;

    @LogRecord
    @ResponseBody
    @GetMapping(value = "/initialize")
    public ObjectNode initialize() {
        json = mapper.createObjectNode();
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            RequestMappingInfo info = m.getKey();
            PatternsRequestCondition p = info.getPatternsCondition();
            for (String url : p.getPatterns()) {
                if (!permissionService.existsByUrlAndStr(url, url)) {
                    Permission permission = new Permission();
                    permission.setName(url);
                    permission.setAvailable(true);
                    permission.setFather(null);
                    permission.setIntroduction(url);
                    permission.setUrl(url);
                    permission.setStr("1111");
                    try {
                        permissionService.save(permission);
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        return json;
    }
}
