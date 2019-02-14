package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.LogDao;
import cn.sbx0.zhibei.entity.Log;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 日志 服务层
 */
@Service
public class LogService extends BaseService<Log, Integer> {
    @Resource
    LogDao logDao;

    @Override
    public PagingAndSortingRepository<Log, Integer> getDao() {
        return logDao;
    }

    @Override
    public Log getEntity() {
        return new Log();
    }
}
