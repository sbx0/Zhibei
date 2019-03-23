package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.RecordDao;
import cn.sbx0.zhibei.entity.Record;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 记录 服务层
 */
@Service
public class RecordService extends BaseService<Record, Integer> {
    @Resource
    private RecordDao recordDao;

    @Override
    public PagingAndSortingRepository<Record, Integer> getDao() {
        return recordDao;
    }

    @Override
    public Record getEntity() {
        return new Record();
    }

    /**
     * 根据页面和用户查询记录
     *
     * @param u_id
     * @return
     */
    public Record findByPathAndUser(String url, Integer u_id) {
        return recordDao.findByPathAndUser(url, u_id);
    }

}
