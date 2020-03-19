package cn.sbx0.zhibei.logic.alipay;

import cn.sbx0.zhibei.logic.BaseService;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TradeBaseService extends BaseService<TradeBase, Integer> {
    @Resource
    private TradeBaseDao dao;

    @Override
    public PagingAndSortingRepository<TradeBase, Integer> getDao() {
        return dao;
    }

    @Override
    public TradeBase getEntity() {
        return new TradeBase();
    }

    @Override
    public boolean checkDataValidity(TradeBase o) {
        return true;
    }

    /**
     * todo
     *
     * @param tradeNo
     * @return
     */
    public List<TradeBase> findByTradeNo(String tradeNo) {
        return dao.findByTradeNo(tradeNo);
    }

    /**
     * todo
     *
     * @param tradeBases
     * @return
     */
    public Double countAmount(List<TradeBase> tradeBases) {
        double amount = 0.00;
        for (TradeBase tradeBase : tradeBases) {
            double count = tradeBase.getPrice() * tradeBase.getDiscount() * tradeBase.getNumbers();
            if (count < 0) continue;
            amount += count;
        }
        return amount;
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
        String name = dateString + randomNum;
        return name;
    }
}
