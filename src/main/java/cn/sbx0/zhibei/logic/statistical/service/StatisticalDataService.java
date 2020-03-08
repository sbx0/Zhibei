package cn.sbx0.zhibei.logic.statistical.service;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.statistical.dao.StatisticalDataDao;
import cn.sbx0.zhibei.logic.statistical.entity.StatisticalData;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StatisticalDataService extends BaseService<StatisticalData, Integer> {
    @Resource
    private StatisticalDataDao dao;
    @Resource
    private UserBaseService userBaseService;
    @Resource
    private StatisticalUserService statisticalUserService;

    @Override
    public PagingAndSortingRepository<StatisticalData, Integer> getDao() {
        return dao;
    }

    @Override
    public StatisticalData getEntity() {
        return new StatisticalData();
    }

    @Override
    public boolean checkDataValidity(StatisticalData statisticalData) {
        return true;
    }

    public ObjectNode findByKindAndGrouping(int day, String kind, String group) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:ss");
        ObjectNode json = initJSON();
        ArrayNode jsons = initJSONs();
        List<StatisticalData> list = dao.findByKindAndGrouping(day, kind, group);
        if (list != null) {
            for (StatisticalData data : list) {
                ObjectNode j = initJSON();
                j.put("date", sdf.format(data.getRecordTime()));
                j.put(kind, data.getValue());
                jsons.add(j);
            }
            json.set("data", jsons);
        }
        return json;
    }

    /**
     * 根据天数查询数据
     *
     * @param day   day
     * @param kind  kind
     * @param group group
     * @return ObjectNode
     */
    public ObjectNode findByDay(Integer day, String kind, String group) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        ObjectNode json = initJSON();
        ArrayNode jsons = initJSONs();
        for (int i = day; i >= 0; i--) {
            StatisticalData data = dao.findByDay(i, kind, group);
            if (data != null) {
                ObjectNode j = initJSON();
                j.put("date", sdf.format(data.getRecordTime()));
                j.put(kind, data.getValue());
                jsons.add(j);
            }
        }
        json.set("data", jsons);
        return json;
    }

    /**
     * 每小时统计活跃人数
     */
    @Scheduled(cron = "00 00 * * * ?")
    public void active() {
        int count = userBaseService.active();
        StatisticalData data = new StatisticalData();
        data.setGroupBy("per_hour");
        data.setType("active");
        data.setRecordTime(new Date());
        data.setValue((double) count);
        logger.info("active = " + count);
        dao.save(data);
    }

    /**
     * 每小时统计访问人数
     */
    @Scheduled(cron = "00 10 * * * ?")
    public void view() {
        int count = statisticalUserService.view();
        StatisticalData data = new StatisticalData();
        data.setGroupBy("per_hour");
        data.setType("view");
        data.setRecordTime(new Date());
        data.setValue((double) count);
        logger.info("view = " + count);
        dao.save(data);
    }

}
