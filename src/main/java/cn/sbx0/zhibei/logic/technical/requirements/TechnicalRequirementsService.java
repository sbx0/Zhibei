package cn.sbx0.zhibei.logic.technical.requirements;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.address.AddressBase;
import cn.sbx0.zhibei.logic.address.AddressBaseDao;
import cn.sbx0.zhibei.logic.technical.achievements.TechnicalCooperationMethod;
import cn.sbx0.zhibei.logic.technical.achievements.TechnicalMaturity;
import cn.sbx0.zhibei.logic.technical.classification.TechnicalClassification;
import cn.sbx0.zhibei.logic.technical.classification.TechnicalClassificationDao;
import cn.sbx0.zhibei.logic.user.base.UserBase;
import cn.sbx0.zhibei.logic.user.base.UserBaseDao;
import cn.sbx0.zhibei.tool.DateTools;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class TechnicalRequirementsService extends BaseService<TechnicalRequirements, Integer> {
    @Resource
    private TechnicalRequirementsDao dao;
    @Resource
    private AddressBaseDao addressBaseDao;
    @Resource
    private UserBaseDao userBaseDao;
    @Resource
    private TechnicalClassificationDao technicalClassificationDao;

    @Override
    public PagingAndSortingRepository<TechnicalRequirements, Integer> getDao() {
        return dao;
    }

    @Override
    public TechnicalRequirements getEntity() {
        return new TechnicalRequirements();
    }

    @Override
    public boolean checkDataValidity(TechnicalRequirements TechnicalRequirements) {
        return true;
    }

    public ReturnStatus init() {
        ClassPathResource resource = new ClassPathResource("api/technical_requirements_data_source_part_1.json");
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
                TechnicalRequirementsJson[] jsons = mapper.readValue(finalFile, TechnicalRequirementsJson[].class);
                for (TechnicalRequirementsJson json : jsons) {
                    TechnicalRequirements technicalAchievements = dao.findByName(json.getName());
                    if (technicalAchievements != null) continue;
                    TechnicalRequirements technicalAchievementsNew = new TechnicalRequirements();
                    technicalAchievementsNew.setUserId(userBase.getId());
                    if (addressIndex > addressBases.size() - 1) addressIndex = 1;
                    String addressId = addressBases.get(addressIndex).getId();
                    addressIndex++;
                    technicalAchievementsNew.setAddressId(addressId);
                    technicalAchievementsNew.setCover("https://zb.sbx0.cn/upload/user1/image/20200322133638361.jpg");
                    technicalAchievementsNew.setPostTime(new Date());
                    String industry = json.getClassification();
                    TechnicalClassification technicalClassificationSon = technicalClassificationDao.findSonByName(industry);
                    if (technicalClassificationSon != null) {
                        technicalAchievementsNew.setClassificationId(technicalClassificationSon.getId());
                    } else {
                        TechnicalClassification technicalClassificationFather = technicalClassificationDao.findFatherByName(industry);
                        if (technicalClassificationFather != null) {
                            TechnicalClassification technicalClassificationNewSon = new TechnicalClassification();
                            technicalClassificationNewSon.setFatherId(technicalClassificationFather.getId());
                            technicalClassificationNewSon.setName(industry);
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
                            technicalClassificationFather.setName(industry);
                            double randomName = Math.random() * 100000 + 100;
                            technicalClassificationFather.setId((int) randomName + "");
                            technicalClassificationFather.setCover("");
                            technicalClassificationFather = technicalClassificationDao.save(technicalClassificationFather);
                            if (technicalClassificationFather.getId() != null) {
                                TechnicalClassification technicalClassificationNewSon = new TechnicalClassification();
                                technicalClassificationNewSon.setFatherId(technicalClassificationFather.getId());
                                technicalClassificationNewSon.setName(industry);
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
                    technicalAchievementsNew.setCooperationMethod(TechnicalCooperationMethod.findByName(json.getCooperationMethod()));
                    technicalAchievementsNew.setContext(json.getContext());
                    double randomPrice = Math.random() * 1000000 + 1000;
                    technicalAchievementsNew.setBudget(randomPrice);
                    int random = (int) (Math.random() * 4.0 + 1.0);
                    while (!TechnicalRequirementsStatus.judge(random)) {
                        random = (int) (Math.random() * 4.0 + 1.0);
                    }
                    technicalAchievementsNew.setEndTime(DateTools.addDay(new Date(), random * 10));
                    technicalAchievementsNew.setStatus(random);
                    save(technicalAchievementsNew);
                }
                logger.info("technical requirement init finish.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ReturnStatus.success;
        } else {
            return ReturnStatus.failed;
        }
    }
}
