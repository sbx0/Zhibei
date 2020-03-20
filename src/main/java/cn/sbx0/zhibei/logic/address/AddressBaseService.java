package cn.sbx0.zhibei.logic.address;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressBaseService extends BaseService<AddressBase, String> {
    @Resource
    private AddressBaseDao dao;

    @Override
    public PagingAndSortingRepository<AddressBase, String> getDao() {
        return dao;
    }

    @Override
    public AddressBase getEntity() {
        return new AddressBase();
    }

    @Override
    public boolean checkDataValidity(AddressBase AddressBase) {
        return true;
    }

    public List<AddressBase> sonToFather(String sonId) {
        List<AddressBase> list = new ArrayList<>();
        AddressBase son = findById(sonId);
        while (son != null) {
            list.add(son);
            if (son.getFatherId() != null) {
                son = findById(son.getFatherId());
            } else {
                son = null;
            }
        }
        return list;
    }

    public ReturnStatus init() {
        ClassPathResource resource = new ClassPathResource("api/area_format_array.json");
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            logger.info(file.getPath());
            ObjectMapper mapper = getMapper();
            Optional<AddressBase> optionalAB = dao.findById("0");
            if (!optionalAB.isPresent()) {
                AddressBase china = new AddressBase();
                china.setId("0");
                china.setName("中国");
                china.setPinYinPrefix("z");
                save(china);
            }
            File finalFile = file;
            new Thread(() -> {
                try {
                    AddressJson[] jsons = mapper.readValue(finalFile, AddressJson[].class);
                    for (AddressJson json : jsons) {
                        Optional<AddressBase> optionalA = dao.findById(json.getI());
                        if (!optionalA.isPresent()) {
                            AddressBase a = new AddressBase();
                            a.setId(json.getI());
                            a.setName(json.getN());
                            a.setFatherId(json.getP());
                            a.setPinYinPrefix(json.getY());
                            save(a);
                        }
                    }
                    logger.info("address base init finish.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "thread-init").start();
            return ReturnStatus.success;
        } else {
            return ReturnStatus.failed;
        }

    }

    public List<AddressBase> findAllFather() {
        return dao.findAllFather();
    }

    public List<AddressBase> findAllSon(String fatherId) {
        return dao.findAllSon(fatherId);
    }
    public List<AddressBase> findAllSons(String[] fatherIds) {
        return dao.findAllSons(fatherIds);
    }
}
