package cn.sbx0.zhibei.logic.user.certification;

import cn.sbx0.zhibei.entity.Certification;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.info.UserInfo;
import cn.sbx0.zhibei.logic.user.info.UserInfoService;
import cn.sbx0.zhibei.tool.DateTools;
import cn.sbx0.zhibei.tool.StringTools;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户认证 服务层
 */
@Service
public class UserCertificationService extends BaseService<UserCertification, Integer> {
    @Resource
    private UserCertificationDao dao;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserCertificationMapper userCertificationMapper;

    @Override
    public PagingAndSortingRepository<UserCertification, Integer> getDao() {
        return dao;
    }

    @Override
    public UserCertification getEntity() {
        return new UserCertification();
    }

    public List<UserCertification> findAllByUserAndKindAndStatusAndPage(Integer page, Integer size, Integer total, Integer userId, String kind, String status) {
        if (page == null || page < 1) {
            return null;
        }
        if (size == null || size < 1) {
            return null;
        }
        int begin = (page - 1) * size;
        if (begin > total) begin = total;
        int end = begin + size;
        if (end > total) end = total;
        return userCertificationMapper.findAllByUserAndKindAndStatusAndPage(userId, kind, status, begin, end);
    }

    /**
     * 提交认证
     *
     * @param certification certification
     * @return ReturnStatus
     */
    public ReturnStatus submit(UserCertification certification) {
        // 判断是否登录
        UserInfo user = userInfoService.getLoginUser();
        if (user == null) {
            return ReturnStatus.notLogin;
        }
        // 提交的认证是空的
        if (certification == null) {
            return ReturnStatus.nullError;
        }
        // 提交的认证某个必需的字段为空
        if (certification.getKind() == null
                || StringTools.checkNullStr(certification.getMaterial())) {
            return ReturnStatus.nullError;
        }
        // 判断认证类型是否合法
        if (!CertificationType.judge(certification.getKind())) {
            return ReturnStatus.invalidValue;
        }
        // 数据库查询用户是否已经有认证了
        UserCertification userCertification = findByUserAndNewest(user.getUserId());
        if (userCertification == null
                || (userCertification.getValidityTime().getTime() > new Date().getTime())
                && userCertification.getStatus() == CertificationStatus.confirmPassed.getValue()) {
            // 1.没有认证记录
            // 2.有认证记录且认证通过但认证已过期
            // 所以新建认证
            // 有效期默认为一年
            Date validityTime = DateTools.addDay(new Date(), 365);
            certification.setValidityTime(validityTime);
            // 设置提交者
            certification.setUserId(user.getUserId());
            // 默认状态为待审核
            certification.setStatus(CertificationStatus.underConfirm.getValue());
            // 设置提交时间
            certification.setSubmitTime(new Date());
            try {
                dao.save(certification);
                return ReturnStatus.success;
            } catch (Exception e) {
                return ReturnStatus.failed;
            }
        } else if (userCertification.getStatus() == CertificationStatus.underConfirm.getValue()
                || userCertification.getValidityTime().getTime() < new Date().getTime()
                && userCertification.getStatus() == CertificationStatus.confirmPassed.getValue()) {
            // 3.有认证记录，但认证记录在审核中
            // 4.有认证记录且认证记录在认证有效期内
            return ReturnStatus.repeatOperation;
        } else {
            return ReturnStatus.failed;
        }
    }

    /**
     * 查询用户最新的一条认证记录
     *
     * @param id id
     * @return UserCertification
     */
    public UserCertification findByUserAndNewest(Integer id) {
        return dao.findByUserAndNewest(id);
    }

    public List<UserCertification> findByUserAndKindAndStatus(Integer id, String kind, String status) {
        return dao.findByUserAndKindAndStatus(id, kind, status);
    }

    /**
     * 获取类型
     */
    public ArrayNode type() {
        List<CertificationType> list = CertificationType.list();
        ArrayNode jsons = initJSONs();
        for (CertificationType type : list) {
            ObjectNode json = initJSON();
            json.put("name", type.getName());
            json.put("value", type.getValue());
            jsons.add(json);
        }
        return jsons;
    }
}
