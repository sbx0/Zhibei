package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.user.certification.CertificationType;
import cn.sbx0.zhibei.logic.user.certification.UserCertification;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TechnicalAchievementsService extends BaseService<TechnicalAchievements, Integer> {
    @Resource
    private TechnicalAchievementsDao dao;
    @Resource
    private TechnicalAchievementsMapper technicalAchievementsMapper;

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
     * @param classificationId
     * @param page
     * @param size
     * @param total
     * @return
     */
    public List<TechnicalAchievements> findAllComplex(Integer userId, String attribute, String direction, Integer maturity, Integer cooperationMethod, String addressId, String classificationId, Integer page, Integer size, Integer total) {
        if (page == null || page < 1) {
            return null;
        }
        if (size == null || size < 1) {
            return null;
        }
        int begin = (page - 1) * size;
        if (begin > total) begin = total;
        return technicalAchievementsMapper.findAllComplex(userId, attribute, direction, maturity, cooperationMethod, addressId, classificationId, begin, size);
    }

    public Integer countAllComplex(Integer userId, Integer maturity, Integer cooperationMethod, String addressId, String classificationId) {
        return technicalAchievementsMapper.countAllComplex(userId, maturity, cooperationMethod, addressId, classificationId);
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
