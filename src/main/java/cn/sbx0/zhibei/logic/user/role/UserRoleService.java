package cn.sbx0.zhibei.logic.user.role;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.tool.DateTools;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户角色 服务层
 */
@Service
public class UserRoleService extends BaseService<UserRole, Integer> {
    @Resource
    private UserRoleDao dao;
    @Resource
    private UserRoleBindDao userRoleBindDao;

    @Override
    public PagingAndSortingRepository<UserRole, Integer> getDao() {
        return dao;
    }

    @Override
    public UserRole getEntity() {
        return new UserRole();
    }

    /**
     * 将用户绑定到初始角色
     *
     * @param id id
     */
    public ReturnStatus bindInit(Integer id) {
        try {
            UserRole userRole = dao.findByCode(UserRoleType.initial.getCode());
            // 如果初始角色不存在
            if (userRole == null) {
                userRole = new UserRole();
                userRole.setCode(UserRoleType.initial.getCode());
                userRole.setWeight(UserRoleType.initial.getWeight());
                userRole = dao.save(userRole);
            }
            UserRoleBind userRoleBind = new UserRoleBind();
            userRoleBind.setRoleId(userRole.getId());
            userRoleBind.setUserId(id);
            // 初始角色有效期100年
            Date time = DateTools.addDay(new Date(), 365 * 100);
            userRoleBind.setValidityTime(time);
            userRoleBindDao.save(userRoleBind);
        } catch (Exception e) {
            return ReturnStatus.failed;
        }
        return ReturnStatus.success;
    }

    /**
     * 创建初始角色
     */
    public void init() {
        List<UserRoleType> roles = UserRoleType.list();
        for (UserRoleType role : roles) {
            UserRole temp = dao.findByCode(role.getCode());
            if (temp == null) {
                logger.info(role.toString() + "未创建");
                logger.info(role.getCode() + "开始创建");
                temp = new UserRole();
                temp.setCode(role.getCode());
                temp.setWeight(role.getWeight());
                dao.save(temp);
            }
        }
    }

    /**
     * 查询用户的角色
     *
     * @param userId userId
     * @return list
     */
    public List<UserRoleBind> findAllByUserId(Integer userId) {
        return userRoleBindDao.findAllByUserId(userId);
    }
}
