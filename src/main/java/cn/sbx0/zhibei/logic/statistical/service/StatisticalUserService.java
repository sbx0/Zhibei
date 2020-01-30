package cn.sbx0.zhibei.logic.statistical.service;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.statistical.dao.StatisticalUserDao;
import cn.sbx0.zhibei.logic.statistical.entity.StatisticalUser;
import cn.sbx0.zhibei.tool.DateTools;
import cn.sbx0.zhibei.tool.RequestTools;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class StatisticalUserService extends BaseService<StatisticalUser, Integer> {
    @Resource
    private StatisticalUserDao dao;

    /**
     * 一小时内访问的用户数
     *
     * @return int
     */
    public int view() {
        Date now = new Date();
        // 间隔一小时分钟
        Date before = DateTools.rollSecond(now, 60 * 60);
        return dao.countByTime(before);
    }

    /**
     * 相同ip和客户端记录间隔15分钟
     *
     * @param request 请求
     */
    public void handlerInterceptor(HttpServletRequest request) {
        String ip = RequestTools.getIpAddress(request);
        String client = RequestTools.getClient(request);
        Date now = new Date();
        // 间隔15分钟
        Date before = DateTools.rollSecond(now, 60 * 15);
        StatisticalUser user = dao.findByIpAndTime(ip, client, before);
        if (user == null) {
            user = new StatisticalUser();
            user.setIp(ip);
            user.setClient(client);
            user.setTime(now);
            save(user);
        }
    }

    @Override
    public PagingAndSortingRepository<StatisticalUser, Integer> getDao() {
        return dao;
    }

    @Override
    public StatisticalUser getEntity() {
        return new StatisticalUser();
    }
}
