package cn.sbx0.zhibei.logic.user;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.tool.StringTools;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 基础用户 服务层
 */
@Service
public class UserBaseService extends BaseService<UserBase, Integer> {
    @Resource
    private UserBaseDao dao;
    @Resource
    private UserInfoService userInfoService;

    /**
     * 注册
     *
     * @param userBaseView
     * @return
     */
    public int register(UserBaseView userBaseView) {
        if (StringTools.checkNullStr(userBaseView.getName())) return ReturnStatus.nullStr.getValue();
        if (StringTools.checkNullStr(userBaseView.getEmail())) return ReturnStatus.nullStr.getValue();
        if (StringTools.checkNullStr(userBaseView.getPassword())) return ReturnStatus.nullStr.getValue();
        if (StringTools.checkNotEmail(userBaseView.getEmail())) return ReturnStatus.invalidMail.getValue();
        if (existByName(userBaseView.getName())) return ReturnStatus.repectOperation.getValue();
        if (userInfoService.existByEmail(userBaseView.getEmail())) return ReturnStatus.repectOperation.getValue();
        UserBase userBase = new UserBase();
        userBase.setName(userBaseView.getName());
        // 初始头像，后面可以改的
        userBase.setAvatar("/avatar.jpg");
        userBase = dao.save(userBase);
        if (userBase.getId() != null) {
            int status = userInfoService.register(userBase.getId(), userBaseView);
            if (status != ReturnStatus.success.getValue()) {
                // 如果用户信息创建失败，回滚基础用户
                dao.delete(userBase);
            }
        } else {
            return ReturnStatus.failed.getValue();
        }
        return ReturnStatus.success.getValue();
    }

    /**
     * 根据用户名判断用户是否存在
     *
     * @param name
     * @return
     */
    private boolean existByName(String name) {
        return dao.existsByName(name) != null;
    }

    @Override
    public PagingAndSortingRepository<UserBase, Integer> getDao() {
        return dao;
    }

    @Override
    public UserBase getEntity() {
        return new UserBase();
    }
}
