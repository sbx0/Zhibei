package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.annotation.LogRecord;
import cn.sbx0.zhibei.entity.Demand;
import cn.sbx0.zhibei.entity.JsonViewInterface;
import cn.sbx0.zhibei.entity.Project;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.DemandService;
import cn.sbx0.zhibei.service.MessageService;
import cn.sbx0.zhibei.service.ProjectService;
import com.fasterxml.jackson.databind.MapperFeature;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/project")
public class ProjectController extends BaseController<Project, Integer> {
    @Resource
    private ProjectService projectService;
    @Resource
    private DemandService demandService;
    @Resource
    private MessageService messageService;

    @Override
    public BaseService<Project, Integer> getService() {
        return projectService;
    }

    @Autowired
    public ProjectController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 申请列表
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/applyList")
    public ObjectNode applyList(Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.All.class));
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        Page<Project> tPage = projectService.findBySponsor(user.getId(), (BaseService.buildPageable(page, size, attribute, direction)));
        List<Project> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Project t : tList) {
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
     * 我的项目
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    @LogRecord
    @ResponseBody
    @GetMapping("/my")
    public ObjectNode user(Integer page, Integer size, String attribute, String direction) {
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.setConfig(mapper.getSerializationConfig().withView(JsonViewInterface.All.class));
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        Page<Project> tPage = projectService.findByApplicant(user.getId(), (BaseService.buildPageable(page, size, attribute, direction)));
        List<Project> tList = tPage.getContent();
        ArrayNode jsons = mapper.createArrayNode();
        if (tList != null && tList.size() > 0) {
            for (Project t : tList) {
                ObjectNode object = mapper.convertValue(t, ObjectNode.class);
                jsons.add(object);
            }
            json.set("objects", jsons);
        } else {
            json.set("objects", null);
        }
        return json;
    }

    @LogRecord
    @ResponseBody
    @GetMapping("/set")
    public ObjectNode set(Integer id, Integer status) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        Project project = projectService.findById(id);
        project.setStatus(status);
        if (status == 4) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (messageService.sendNotice("系统通知：您的需求<" + project.getDemand().getTitle() + ">于" + sdf.format(new Date()) + "收到一条合作申请。", project.getSponsor())) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
            }
        }
        if (projectService.save(project)) {
            json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        } else {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
        }
        return json;
    }

    @LogRecord
    @ResponseBody
    @GetMapping("/apply")
    public ObjectNode apply(Integer id) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        Demand demand = demandService.findById(id);
        if (demand == null) {
            json.put(STATUS_NAME, STATUS_CODE_PARAMETER_ERROR);
            return json;
        }
        if (projectService.existsByApplicantAndDemand(user.getId(), demand.getId())) {
            json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            return json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        } else {
            Project project = new Project();
            project.setApplicant(user);
            project.setSponsor(demand.getPoster());
            project.setDemand(demand);
            project.setCreateTime(new Date());
            project.setStatus(0);
            if (projectService.save(project)) {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            } else {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
            }
        }
        return json;
    }
}
