package cn.sbx0.zhibei.logic.user.role;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import cn.sbx0.zhibei.tool.DateTools;
import cn.sbx0.zhibei.tool.StringTools;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private UserBaseService userBaseService;

    @Override
    public PagingAndSortingRepository<UserRole, Integer> getDao() {
        return dao;
    }

    @Override
    public UserRole getEntity() {
        return new UserRole();
    }

    @Override
    public boolean checkDataValidity(UserRole userRole) {
        if (StringTools.checkNullStr(userRole.getCode())) return false;
        return userRole.getWeight() != null;
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

    /**
     * 给某个用户添加角色
     *
     * @param userId userId
     * @param roleId roleId
     * @param kind   kind 为0时增加，为其他时删除
     * @return ReturnStatus
     */
    public ReturnStatus give(Integer userId, Integer roleId, Integer kind) {
        // 验证数据有效性
        if (!userBaseService.existById(userId)) return ReturnStatus.invalidValue;
        if (!existById(roleId)) return ReturnStatus.invalidValue;
        // 查询用户已经拥有的所有角色
        List<UserRoleBind> userRoleBinds = findAllByUserId(userId);
        if (userRoleBinds == null) userRoleBinds = new ArrayList<>();
        boolean exist = false;
        Date now = new Date();
        // 添加
        if (kind == 0) {
            for (UserRoleBind userRoleBind : userRoleBinds) {
                // 如果要添加的角色之前绑定过
                if (userRoleBind.getRoleId().equals(roleId)) {
                    exist = true;
                    // 且该角色还未过期
                    if (userRoleBind.getValidityTime().getTime() > now.getTime()) {
                        return ReturnStatus.repeatOperation;
                    } else {
                        // 角色过期，更新有效期，默认一年
                        userRoleBind.setValidityTime(DateTools.addDay(now, 365));
                        // 保存
                        userRoleBindDao.save(userRoleBind);
                    }
                    break;
                }
            }
            // 如果添加的角色并没有
            if (!exist) {
                UserRoleBind userRoleBind = new UserRoleBind();
                userRoleBind.setValidityTime(DateTools.addDay(now, 365));
                userRoleBind.setUserId(userId);
                userRoleBind.setRoleId(roleId);
                userRoleBindDao.save(userRoleBind);
            }
        } else {
            // 删除
            for (UserRoleBind userRoleBind : userRoleBinds) {
                // 如果要删除的角色之前绑定过
                if (userRoleBind.getRoleId().equals(roleId)) {
                    // 设置有效期为现在，也就是立即过期
                    userRoleBind.setValidityTime(now);
                    // 保存
                    userRoleBindDao.save(userRoleBind);
                    break;
                }
            }
        }
        return ReturnStatus.success;
    }

    /**
     * 查询角色是否存在
     *
     * @param roleId roleId
     * @return boolean
     */
    private boolean existById(Integer roleId) {
        return dao.existById(roleId) != null;
    }
}
