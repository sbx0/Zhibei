package cn.sbx0.zhibei.logic.user;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.tool.StringTools;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户信息 服务层
 */
@Service
public class UserInfoService extends BaseService<UserInfo, Integer> {
    @Resource
    private UserInfoDao dao;

    /**
     * 注册
     *
     * @param id           ID
     * @param userBaseView 需要注册的信息
     * @return 状态码
     */
    public int register(Integer id, UserBaseView userBaseView) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(id);
        userInfo.setEmail(userBaseView.getEmail());
        // 加密密码
        userInfo.setPassword(StringTools.encryptPassword(userBaseView.getPassword()));
        userInfo.setRegisterTime(new Date());
        userInfo.setBanned(false);
        dao.save(userInfo);
        return ReturnStatus.success.getValue();
    }

    /**
     * 根据邮箱地址判断是否存在
     *
     * @param email
     * @return
     */
    public boolean existByEmail(String email) {
        return dao.existByEmail(email) != null;
    }

    @Override
    public PagingAndSortingRepository<UserInfo, Integer> getDao() {
        return dao;
    }

    @Override
    public UserInfo getEntity() {
        return new UserInfo();
    }
}
