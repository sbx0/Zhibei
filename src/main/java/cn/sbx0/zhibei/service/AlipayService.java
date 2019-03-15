package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.dao.AlipayDao;
import cn.sbx0.zhibei.entity.Alipay;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AlipayService extends BaseService<Alipay, Integer> {
    @Resource
    private AlipayDao alipayDao;

    @Override
    public PagingAndSortingRepository<Alipay, Integer> getDao() {
        return alipayDao;
    }

    @Override
    public Alipay getEntity() {
        return new Alipay();
    }

    /**
     * 创建唯一交易号
     *
     * @return 创建唯一交易号
     */
    public String createTradeNo() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = simpleDateFormat.format(date);
        int randomNum = (int) (Math.random() * 899 + 100);
        String fileName = dateString + randomNum;
        return fileName;
    }

    /**
     * 根据平台交易单号查询记录
     *
     * @param outTradeNo
     * @return
     */
    public Alipay findByOutTradeNo(String outTradeNo) {
        return alipayDao.findByOutTradeNo(outTradeNo);
    }
}
