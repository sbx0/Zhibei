package cn.sbx0.zhibei.logic.technical.achievements;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.address.AddressBase;
import cn.sbx0.zhibei.logic.address.AddressBaseDao;
import cn.sbx0.zhibei.logic.address.AddressJson;
import cn.sbx0.zhibei.logic.technical.classification.TechnicalClassification;
import cn.sbx0.zhibei.logic.technical.classification.TechnicalClassificationDao;
import cn.sbx0.zhibei.logic.user.base.UserBase;
import cn.sbx0.zhibei.logic.user.base.UserBaseDao;
import cn.sbx0.zhibei.logic.user.certification.CertificationType;
import cn.sbx0.zhibei.logic.user.certification.UserCertification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TechnicalAchievementsService extends BaseService<TechnicalAchievements, Integer> {
    @Resource
    private TechnicalAchievementsDao dao;
    @Resource
    private AddressBaseDao addressBaseDao;
    @Resource
    private UserBaseDao userBaseDao;
    @Resource
    private TechnicalClassificationDao technicalClassificationDao;
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

    public ReturnStatus init() {
        ClassPathResource resource = new ClassPathResource("api/technical_achievements_data_source.json");
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            logger.info(file.getPath());
            ObjectMapper mapper = getMapper();

            List<AddressBase> addressBases = addressBaseDao.findAll();
            int addressIndex = 1;

            UserBase userBase = userBaseDao.findByName("智贝BOT");
            if (userBase == null)
                return ReturnStatus.failed;

            File finalFile = file;
            try {
                TechnicalAchievementsJson[] jsons = mapper.readValue(finalFile, TechnicalAchievementsJson[].class);
                for (TechnicalAchievementsJson json : jsons) {
                    TechnicalAchievements technicalAchievements = dao.findByName(json.getName());
                    if (technicalAchievements != null) continue;
                    TechnicalAchievements technicalAchievementsNew = new TechnicalAchievements();
                    technicalAchievementsNew.setUserId(userBase.getId());
                    if (addressIndex > addressBases.size() - 1) addressIndex = 1;
                    String addressId = addressBases.get(addressIndex).getId();
                    addressIndex++;
                    technicalAchievementsNew.setAddressId(addressId);
                    technicalAchievementsNew.setCover("https://zb.sbx0.cn/upload/user1/image/20200322133638361.jpg");
                    technicalAchievementsNew.setPostTime(new Date());
                    String industry = json.getIndustry();
                    String[] industries = industry.split("-");
                    TechnicalClassification technicalClassificationSon = technicalClassificationDao.findSonByName(industries[1]);
                    if (technicalClassificationSon != null) {
                        technicalAchievementsNew.setClassificationId(technicalClassificationSon.getId());
                    } else {
                        TechnicalClassification technicalClassificationFather = technicalClassificationDao.findFatherByName(industries[0]);
                        if (technicalClassificationFather != null) {
                            TechnicalClassification technicalClassificationNewSon = new TechnicalClassification();
                            technicalClassificationNewSon.setFatherId(technicalClassificationFather.getId());
                            technicalClassificationNewSon.setName(industries[1]);
                            double randomName = Math.random() * 100000 + 100;
                            technicalClassificationNewSon.setId(technicalClassificationFather.getId() + (int) randomName + "");
                            technicalClassificationNewSon.setCover("");
                            technicalClassificationNewSon = technicalClassificationDao.save(technicalClassificationNewSon);
                            if (technicalClassificationNewSon.getId() != null) {
                                technicalAchievementsNew.setClassificationId(technicalClassificationNewSon.getId());
                            } else {
                                continue;
                            }
                        } else {
                            technicalClassificationFather = new TechnicalClassification();
                            technicalClassificationFather.setName(industries[0]);
                            double randomName = Math.random() * 100000 + 100;
                            technicalClassificationFather.setId((int) randomName + "");
                            technicalClassificationFather.setCover("");
                            technicalClassificationFather = technicalClassificationDao.save(technicalClassificationFather);
                            if (technicalClassificationFather.getId() != null) {
                                TechnicalClassification technicalClassificationNewSon = new TechnicalClassification();
                                technicalClassificationNewSon.setFatherId(technicalClassificationFather.getId());
                                technicalClassificationNewSon.setName(industries[1]);
                                randomName = Math.random() * 100000 + 100;
                                technicalClassificationNewSon.setId(technicalClassificationFather.getId() + (int) randomName + "");
                                technicalClassificationNewSon.setCover("");
                                technicalClassificationNewSon = technicalClassificationDao.save(technicalClassificationNewSon);
                                if (technicalClassificationNewSon.getId() != null) {
                                    technicalAchievementsNew.setClassificationId(technicalClassificationNewSon.getId());
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    technicalAchievementsNew.setName(json.getName());
                    technicalAchievementsNew.setMaturity(TechnicalMaturity.findByName(json.getMaturity()));
                    technicalAchievementsNew.setCooperationMethod(TechnicalCooperationMethod.findByName(json.getCooperationMethod()));
                    technicalAchievementsNew.setContext(json.getContext());
                    double randomPrice = Math.random() * 1000000 + 1000;
                    technicalAchievementsNew.setPrice(randomPrice);
                    save(technicalAchievementsNew);
                }
                logger.info("address base init finish.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ReturnStatus.success;
        } else {
            return ReturnStatus.failed;
        }
    }

    public List<TechnicalAchievements> findAllComplexs(Integer userId, String attribute, String direction, Integer maturity, Integer cooperationMethod, String[] addressId, String[] classificationId, Integer page, Integer size, Integer total) {
        if (page == null || page < 1) return null;
        if (size == null || size < 1) return null;
        int begin = (page - 1) * size;
        if (begin > total) begin = total;
        return technicalAchievementsMapper.findAllComplexs(userId, attribute, direction, maturity, cooperationMethod, addressId, classificationId, begin, size);
    }

    public Integer countAllComplexs(Integer userId, Integer maturity, Integer cooperationMethod, String[] addressId, String[] classificationId) {
        return technicalAchievementsMapper.countAllComplexs(userId, maturity, cooperationMethod, addressId, classificationId);
    }

    public List<TechnicalAchievements> findAllComplex(Integer userId, String attribute, String direction, Integer maturity, Integer cooperationMethod, String addressId, String classificationId, Integer page, Integer size, Integer total) {
        if (page == null || page < 1) return null;
        if (size == null || size < 1) return null;
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
