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

    // todo 认证申请列表 get
    @GetMapping("/list")
    public ObjectNode list(Integer id, String kind, String status) {
        ObjectNode json = initJSON();
        List<UserCertification> list = (List<UserCertification>) userCertificationMapper.selectAll(id, kind, status);
        ArrayNode jsons = initJSONs();
        for (UserCertification certification : list) {
            jsons.add(getMapper().convertValue(certification, ObjectNode.class));
        }
        json.set("objects", jsons);
        return json;
    }

}
