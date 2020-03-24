package cn.sbx0.zhibei.logic.technical.project;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.message.MessageBaseService;
import cn.sbx0.zhibei.logic.technical.achievements.TechnicalAchievements;
import cn.sbx0.zhibei.logic.technical.achievements.TechnicalAchievementsService;
import cn.sbx0.zhibei.logic.user.info.UserInfoService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/project/base")
public class ProjectBaseController extends BaseController<ProjectBase, Integer> {
    @Resource
    private ProjectBaseService service;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private TechnicalAchievementsService technicalAchievementsService;
    @Resource
    private MessageBaseService messageBaseService;

    @Override
    public BaseService<ProjectBase, Integer> getService() {
        return service;
    }

    @GetMapping("/my")
    public ObjectNode my() {
        ObjectNode json = initJSON();
        Integer userId = userInfoService.getLoginUserId();
        List<ProjectBase> list = service.findAllByUserId(userId);
        json.set(jsonObs, service.convertToJsons(list));
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @GetMapping("/apply")
    public ObjectNode apply(Integer id) {
        ObjectNode json = initJSON();
        Integer userId = userInfoService.getLoginUserId();
        TechnicalAchievements technicalAchievements = technicalAchievementsService.findById(id);
        if (technicalAchievements == null) {
            json.put(statusCode, ReturnStatus.nullError.getCode());
            return json;
        }
        ProjectBase projectBase = new ProjectBase();
        projectBase.setStatus(0);
        projectBase.setAchievementId(id);
        projectBase.setApplicantId(userId);
        projectBase.setCreateTime(new Date());
        projectBase.setName(technicalAchievements.getName());
        projectBase.setReceiveId(technicalAchievements.getUserId());
        service.save(projectBase);

        // send message
        messageBaseService.notification(
                "你的技术成果'" + technicalAchievements.getName() + "'收到了一个合作请求，请查看。",
                "achievements|" + technicalAchievements.getId(),
                technicalAchievements.getUserId()
        );

        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @GetMapping("/handle")
    public ObjectNode handle(Integer id, Integer type) {
        ObjectNode json = initJSON();
        Integer userId = userInfoService.getLoginUserId();
        ProjectBase projectBase = service.findById(id);
        if (projectBase == null) {
            json.put(statusCode, ReturnStatus.nullError.getCode());
            return json;
        }
        TechnicalAchievements technicalAchievements = technicalAchievementsService.findById(projectBase.getAchievementId());
        if (technicalAchievements == null) {
            json.put(statusCode, ReturnStatus.nullError.getCode());
            return json;
        }
        // pass
        if (type == 1) {
            if (!projectBase.getReceiveId().equals(userId)) {
                json.put(statusCode, ReturnStatus.noPermission.getCode());
                return json;
            }
            projectBase.setStatus(1);
            service.save(projectBase);
            // send message
            messageBaseService.notification(
                    "你的技术合作申请'" + projectBase.getName() + "'已通过。",
                    "project|" + projectBase.getId(),
                    projectBase.getApplicantId()
            );
        } else {
            // reject
            if (!projectBase.getReceiveId().equals(userId)) {
                json.put(statusCode, ReturnStatus.noPermission.getCode());
                return json;
            }
            projectBase.setStatus(2);
            // send message
            messageBaseService.notification(
                    "你的技术合作申请'" + projectBase.getName() + "'被拒绝，非常抱歉。",
                    "project|" + projectBase.getId(),
                    projectBase.getApplicantId()
            );
        }
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }
}
