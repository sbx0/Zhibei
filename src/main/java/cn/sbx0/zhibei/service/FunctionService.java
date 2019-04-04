package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.FunctionDao;
import cn.sbx0.zhibei.entity.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 操作 服务层
 */
@Service
public class FunctionService extends BaseService<Function, Integer> {
    @Resource
    private FunctionDao functionDao;

    @Override
    public PagingAndSortingRepository<Function, Integer> getDao() {
        return functionDao;
    }

    @Override
    public Function getEntity() {
        return new Function();
    }

    /**
     * 同一页面同一操作只能做一次
     *
     * @param path
     * @param u_id
     * @param type
     * @return
     */
    public boolean existsByPathAndUserAndType(String path, Integer u_id, String type) {
        String result = functionDao.existsByPathAndUserAndType(path, u_id, type);
        return result != null;
    }

    /**
     * 同一页面同一操作只能做一次
     *
     * @param path
     * @param u_id
     * @param type
     * @return
     */
    public Function findByPathAndUserAndType(String path, Integer u_id, String type) {
        return functionDao.findByPathAndUserAndType(path, u_id, type);
    }

    /**
     * 根据用户和路径查找
     *
     * @param path
     * @param u_id
     * @param pageable
     * @return
     */
    public Page<Function> findByPathAndUser(String path, Integer u_id, Pageable pageable) {
        return functionDao.findByPathAndUser(path, u_id, pageable);
    }

    /**
     * 统计次数
     *
     * @param path
     * @return
     */
    public Integer countByPath(String path, String type) {
        return functionDao.countByPath(path, type);
    }
}
