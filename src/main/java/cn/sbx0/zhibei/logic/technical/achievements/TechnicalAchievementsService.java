package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.user.certification.CertificationType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TechnicalAchievementsService extends BaseService<TechnicalAchievements, Integer> {
    @Resource
    private TechnicalAchievementsDao dao;

    @Override
    public PagingAndSortingRepository<TechnicalAchievements, Integer> getDao() {
        return dao;
    }

    @Override
    public TechnicalAchievements getEntity() {
        return new TechnicalAchievements();
    }

    @Override
    public boolean checkDataValidity(TechnicalAchievements technicalAchievements) {
        return true;
    }

    /**
     * todo
     *
     * @return
     */
    public ArrayNode technicalMaturityList() {
        ArrayNode jsons = initJSONs();
        for (TechnicalMaturity o : TechnicalMaturity.list()) {
            ObjectNode json = initJSON();
            json.put("name", o.getName());
            json.put("value", o.getValue());
            jsons.add(json);
        }
        return jsons;
    }

    /**
     * todo
     *
     * @return
     */
    public ArrayNode technicalCooperationMethodList() {
        ArrayNode jsons = initJSONs();
        for (TechnicalCooperationMethod o : TechnicalCooperationMethod.list()) {
            ObjectNode json = initJSON();
            json.put("name", o.getName());
            json.put("value", o.getValue());
            jsons.add(json);
        }
        return jsons;
    }
}
