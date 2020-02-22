package cn.sbx0.zhibei.logic.user.group;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.tool.DateTools;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用户组 服务层
 */
@Service
public class UserGroupService extends BaseService<UserGroup, Integer> {
    @Resource
    private UserGroupDao dao;
    @Resource
    private UserGroupBindDao userGroupBindDao;

    @Override
    public PagingAndSortingRepository<UserGroup, Integer> getDao() {
        return dao;
    }

    @Override
    public UserGroup getEntity() {
        return new UserGroup();
    }

    @Override
    public boolean checkDataValidity(UserGroup userGroup) {
        return true;
    }

    /**
     * todo
     *
     * @param id
     * @return
     */
    public boolean isOutOfRange(Integer id) {
        Integer limitNumber = dao.findLimitById(id);
        Integer currentNumber = userGroupBindDao.countByGroup(id);
        return currentNumber >= limitNumber;
    }

    /**
     * 创建
     *
     * @param group group
     * @return ReturnStatus
     */
    public ReturnStatus create(UserGroup group) {
        if (!checkDataValidity(group)) return ReturnStatus.invalidValue;
        group.setCreateTime(new Date());
        group.setCurrentNumber(1);
        group.setLimitNumber(5);
        group.setValidityTime(DateTools.addDay(new Date(), 365 * 100));
        group = dao.save(group);
        if (group.getId() == null) return ReturnStatus.failed;
        UserGroupBind userGroupBind = new UserGroupBind();
        userGroupBind.setAdmin(true);
        userGroupBind.setUserId(group.getOwnerId());
        userGroupBind.setGroupId(group.getId());
        userGroupBind.setValidityTime(DateTools.addDay(new Date(), 365 * 100));
        userGroupBind = userGroupBindDao.save(userGroupBind);
        if (userGroupBind.getId() != null) return ReturnStatus.success;
        else return ReturnStatus.failed;
    }

    /**
     * 将指定用户添加到用户组中
     *
     * @param groupId groupId
     * @param userId  userId
     * @param isAdmin isAdmin
     * @param day     day
     * @return ReturnStatus
     */
    public ReturnStatus addUser(Integer groupId, Integer userId, boolean isAdmin, Integer day) {
        try {
            if (isOutOfRange(groupId)) {
                return ReturnStatus.outRange;
            }
            if (userGroupBindDao.existsByUser(userId, groupId) != null) {
                return ReturnStatus.repeatOperation;
            }
            UserGroup group = dao.findById(groupId).get();
            group.setCurrentNumber(group.getCurrentNumber() + 1);
            try {
                save(group);
            } catch (Exception e) {
                return ReturnStatus.exception;
            }
            UserGroupBind userGroupBind = userGroupBindDao.findByGroupAndUser(groupId, userId);
            if (userGroupBind == null) userGroupBind = new UserGroupBind();
            userGroupBind.setAdmin(true);
            userGroupBind.setUserId(userId);
            userGroupBind.setGroupId(groupId);
            userGroupBind.setValidityTime(DateTools.addDay(new Date(), day));
            try {
                userGroupBind = userGroupBindDao.save(userGroupBind);
                if (userGroupBind.getId() != null) return ReturnStatus.success;
                else return ReturnStatus.failed;
            } catch (Exception e) {
                return ReturnStatus.exception;
            }
        } catch (ObjectOptimisticLockingFailureException e) {
            return ReturnStatus.exception;
        }
    }

    /**
     * 删除指定用户
     *
     * @param groupId groupId
     * @param userId  userId
     * @return ReturnStatus
     */
    public ReturnStatus removeUser(Integer groupId, Integer userId) {
        try {
            if (userGroupBindDao.existsByUser(userId, groupId) == null) {
                return ReturnStatus.emptyResult;
            }
            UserGroup userGroup = findById(groupId);
            int currentNumber = userGroup.getCurrentNumber() - 1;
            if (currentNumber <= 0) {
                try {
                    delete(userGroup);
                    return ReturnStatus.success;
                } catch (Exception e) {
                    return ReturnStatus.exception;
                }
            }
            userGroup.setCurrentNumber(currentNumber);
            try {
                save(userGroup);
            } catch (Exception e) {
                return ReturnStatus.exception;
            }
            Integer id = userGroupBindDao.findIdByGroupAndUser(groupId, userId);
            UserGroupBind userGroupBind = new UserGroupBind();
            userGroupBind.setId(id);
            try {
                userGroupBindDao.delete(userGroupBind);
                return ReturnStatus.success;
            } catch (TransactionSystemException e) {
                return ReturnStatus.exception;
            }
        } catch (ObjectOptimisticLockingFailureException e) {
            return ReturnStatus.exception;
        }
    }

    /**
     * todo
     *
     * @param userId
     * @param pageable
     * @return
     */
    public Page<UserGroup> findAllByUser(Integer userId, Pageable pageable) {
        return dao.findAllByUser(userId, pageable);
    }

    /**
     * todo
     *
     * @param name
     * @param pageable
     * @return
     */
    public Page<UserGroup> findAllByName(String name, Pageable pageable) {
        return dao.findAllByName("%" + name + "%", pageable);
    }

    /**
     * todo
     *
     * @param id
     * @param loginUserId
     * @return
     */
    public ReturnStatus checkUser(Integer id, int loginUserId) {
        if (userGroupBindDao.existsByUser(loginUserId, id) != null) {
            return ReturnStatus.success;
        } else {
            return ReturnStatus.emptyResult;
        }
    }
}
