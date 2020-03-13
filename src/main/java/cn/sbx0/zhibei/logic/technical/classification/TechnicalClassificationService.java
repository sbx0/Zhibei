package cn.sbx0.zhibei.logic.technical.classification;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TechnicalClassificationService extends BaseService<TechnicalClassification, String> {
    @Resource
    private TechnicalClassificationDao dao;

    @Override
    public PagingAndSortingRepository<TechnicalClassification, String> getDao() {
        return dao;
    }

    @Override
    public TechnicalClassification getEntity() {
        return new TechnicalClassification();
    }

    @Override
    public boolean checkDataValidity(TechnicalClassification TechnicalClassification) {
        return true;
    }

    public ReturnStatus init() {
        ClassPathResource resource = new ClassPathResource("api/technical_area_format_array.json");
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            logger.info(file.getPath());
            ObjectMapper mapper = getMapper();
            File finalFile = file;
            new Thread(() -> {
                try {
                    TechnicalClassificationJson[] jsons = mapper.readValue(finalFile, TechnicalClassificationJson[].class);
                    for (TechnicalClassificationJson json : jsons) {
                        Optional<TechnicalClassification> optionalA = dao.findById(json.getI());
                        if (!optionalA.isPresent()) {
                            TechnicalClassification a = new TechnicalClassification();
                            a.setId(json.getI());
                            a.setName(json.getN());
                            a.setFatherId(json.getP());
                            a.setPinYinPrefix(json.getY());
                            a.setCover("");
                            save(a);
                        }
                    }
                    logger.info("technical classification init finish.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "thread-init").start();
            return ReturnStatus.success;
        } else {
            return ReturnStatus.failed;
        }
    }

    public List<TechnicalClassification> findAllFather() {
        return dao.findAllFather();
    }

    public List<TechnicalClassification> findAllSon(Integer fatherId) {
        return dao.findAllSon(fatherId);
    }
}
