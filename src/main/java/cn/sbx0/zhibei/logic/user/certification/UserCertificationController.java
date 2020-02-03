package cn.sbx0.zhibei.logic.user.certification;

import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import cn.sbx0.zhibei.logic.user.info.UserInfo;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 用户认证 控制层
 */
@RestController
@RequestMapping("/user/certification")
public class UserCertificationController extends BaseController<UserCertification, Integer> {
    @Resource
    private UserCertificationService service;
    @Resource
    private UserBaseService userBaseService;
    @Autowired
    private UserCertificationMapper userCertificationMapper;

    @Override
    public BaseService<UserCertification, Integer> getService() {
        return service;
    }

    /**
     * 认证通过 拒绝 取消
     */
    @GetMapping("/judge")
    public ObjectNode judge(int status, int id) {
        ObjectNode json = initJSON();
        UserInfo user = userBaseService.getLoginUser();
        if (user == null) {
            json.put(statusCode, ReturnStatus.notLogin.getCode());
            return json;
        }
        UserCertification certification = service.findById(id);
        if (certification == null) {
            json.put(statusCode, ReturnStatus.nullError.getCode());
            return json;
        }
        // 判断状态合法性
        if (CertificationStatus.judge(status)) {
            certification.setStatus(status);
        } else {
            json.put(statusCode, ReturnStatus.invalidValue.getCode());
            return json;
        }
        if (service.save(certification)) {
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.failed.getCode());
        }
        return json;
    }

    /**
     * 取消认证
     *
     * @return json
     */
    @GetMapping("/cancel")
    public ObjectNode cancel() {
        ObjectNode json = initJSON();
        UserInfo user = userBaseService.getLoginUser();
        if (user == null) {
            json.put(statusCode, ReturnStatus.notLogin.getCode());
            return json;
        }
        UserCertification certification = service.findByUserAndNewest(user.getUserId());
        if (certification == null) {
            json.put(statusCode, ReturnStatus.nullError.getCode());
            return json;
        }
        // 设置为取消状态
        certification.setStatus(CertificationStatus.cancel.getValue());
        json.put(statusCode, ReturnStatus.invalidValue.getCode());
        if (service.save(certification)) {
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.failed.getCode());
        }
        return json;
    }

    /**
     * 获取认证类型
     *
     * @return json
     */
    @GetMapping("/type")
    public ObjectNode type() {
        ObjectNode json = initJSON();
        json.set("objects", service.type());
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

    /**
     * 提交认证
     *
     * @param certification certification
     * @return json
     */
    @PostMapping("/submit")
    public ObjectNode submit(UserCertification certification) {
        ObjectNode json = initJSON();
        ReturnStatus status = service.submit(certification);
        json.put(statusCode, status.getCode());
        return json;
    }

    /**
     * 查询认证
     *
     * @return json
     */
    @GetMapping("/check")
    public ObjectNode check() {
        ObjectNode json = initJSON();
        UserInfo user = userBaseService.getLoginUser();
        UserCertification certification = service.findByUserAndNewest(user.getUserId());
        if (certification != null) {
            json.set("object", getMapper().convertValue(certification, ObjectNode.class));
            json.put(statusCode, ReturnStatus.success.getCode());
        } else {
            json.put(statusCode, ReturnStatus.nullError.getCode());
        }
        return json;
    }

    /**
     * 认证申请列表
     *
     * @param id
     * @param kind
     * @param status
     * @return
     */
    @GetMapping("/list")
    public ObjectNode list(Integer userId, String kind, String status, Integer page, Integer size) {
        ObjectNode json = initJSON();
        Integer total = userCertificationMapper.countAllByUserAndKindAndStatusAndPage(userId, kind, status);
        List<UserCertification> list = (List<UserCertification>) service.findAllByUserAndKindAndStatusAndPage(page, size, total, userId, kind, status);
        if (list != null && list.size() > 0) {
            ArrayNode jsons = initJSONs();
            for (UserCertification certification : list) {
                jsons.add(getMapper().convertValue(certification, ObjectNode.class));
            }
            json.set("objects", jsons);
        }
        json.put("page", page);
        json.put("size", size);
        json.put("total", total);
        json.put(statusCode, ReturnStatus.success.getCode());
        return json;
    }

}
