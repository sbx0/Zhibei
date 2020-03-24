package cn.sbx0.zhibei.logic.technical.application;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.alipay.WalletBase;
import cn.sbx0.zhibei.logic.alipay.WalletBaseService;
import cn.sbx0.zhibei.logic.message.MessageBaseService;
import cn.sbx0.zhibei.logic.technical.achievements.TechnicalAchievements;
import cn.sbx0.zhibei.logic.technical.achievements.TechnicalAchievementsService;
import cn.sbx0.zhibei.logic.user.info.UserInfoService;
import cn.sbx0.zhibei.tool.StringTools;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/application/base")
public class ApplicationBaseController extends BaseController<ApplicationBase, Integer> {
    @Resource
    private ApplicationBaseService service;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private TechnicalAchievementsService technicalAchievementsService;
    @Resource
    private MessageBaseService messageBaseService;
    @Resource
    private WalletBaseService walletBaseService;

    @Override
    public BaseService<ApplicationBase, Integer> getService() {
        return service;
    }

    @GetMapping("/my")
    public ObjectNode my() {
        ObjectNode json = initJSON();
        Integer userId = userInfoService.getLoginUserId();
        List<ApplicationBase> list = service.findAllByUserId(userId);
        json.set(jsonObs, service.convertToJsons(list));
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @GetMapping("/apply")
    public ObjectNode apply(Integer id, Double quote,String context) {
        ObjectNode json = initJSON();
        Integer userId = userInfoService.getLoginUserId();
        TechnicalAchievements technicalAchievements = technicalAchievementsService.findById(id);
        if (StringTools.checkNullStr(context)) {
            json.put(statusCode, ReturnStatus.nullError.getCode());
            return json;
        }
        if (technicalAchievements == null) {
            json.put(statusCode, ReturnStatus.nullError.getCode());
            return json;
        }

        // wallet
        WalletBase walletBase = walletBaseService.getUserWallet(userId);
        if (walletBase == null) {
            walletBase = new WalletBase();
            walletBase.setFinished(false);
            walletBase.setMoney(0.00);
            walletBase.setUserId(userId);
            walletBaseService.save(walletBase);
            json.put(statusCode, ReturnStatus.noMoney.getCode());
            return json;
        }

        if (walletBase.getMoney() < technicalAchievements.getPrice() || walletBase.getMoney() < 0) {
            json.put(statusCode, ReturnStatus.noMoney.getCode());
            return json;
        }

        walletBase.setMoney(walletBase.getMoney() - technicalAchievements.getPrice());
        walletBaseService.save(walletBase);

        ApplicationBase applicationBase = new ApplicationBase();
        applicationBase.setStatus(0);
        applicationBase.setAchievementId(id);
        applicationBase.setApplicantId(userId);
        applicationBase.setCreateTime(new Date());
        applicationBase.setName(technicalAchievements.getName());
        applicationBase.setReceiveId(technicalAchievements.getUserId());
        applicationBase.setQuote(technicalAchievements.getPrice());
        applicationBase.setContext(context);
        applicationBase = service.save(applicationBase);

        // send message
        messageBaseService.notification(
                "你的技术成果'" + technicalAchievements.getName() + "'收到了一个请求，请查看。",
                "application|" + applicationBase.getId(),
                technicalAchievements.getUserId()
        );

        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    @GetMapping("/handle")
    public ObjectNode handle(Integer id, Integer type) {
        ObjectNode json = initJSON();
        Integer userId = userInfoService.getLoginUserId();
        ApplicationBase applicationBase = service.findById(id);
        if (applicationBase == null) {
            json.put(statusCode, ReturnStatus.nullError.getCode());
            return json;
        }
        TechnicalAchievements technicalAchievements = technicalAchievementsService.findById(applicationBase.getAchievementId());
        if (technicalAchievements == null) {
            json.put(statusCode, ReturnStatus.nullError.getCode());
            return json;
        }
        // pass
        if (type == 1) {
            if (!applicationBase.getReceiveId().equals(userId)) {
                json.put(statusCode, ReturnStatus.noPermission.getCode());
                return json;
            }
            applicationBase.setStatus(1);
            service.save(applicationBase);
            // send message
            messageBaseService.notification(
                    "你的技术合作申请'" + applicationBase.getName() + "'已通过。",
                    "application|" + applicationBase.getId(),
                    applicationBase.getApplicantId()
            );
        } else {
            // reject
            if (!applicationBase.getReceiveId().equals(userId)) {
                json.put(statusCode, ReturnStatus.noPermission.getCode());
                return json;
            }
            applicationBase.setStatus(2);
            // send message
            messageBaseService.notification(
                    "你的技术合作申请'" + applicationBase.getName() + "'被拒绝，非常抱歉。",
                    "application|" + applicationBase.getId(),
                    applicationBase.getApplicantId()
            );
        }
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }
}
