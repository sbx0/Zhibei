package cn.sbx0.zhibei.logic.technical.classification;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 技术分类 数据层
 */
public interface TechnicalClassificationDao extends PagingAndSortingRepository<TechnicalClassification, String> {
    @Query(value = "select * from technical_classification where father_id is null", nativeQuery = true)
    List<TechnicalClassification> findAllFather();

    @Query(value = "select * from technical_classification where father_id = ?1", nativeQuery = true)
    List<TechnicalClassification> findAllSon(String fatherId);

    @Query(value = "select * from technical_classification where father_id in ?1", nativeQuery = true)
    List<TechnicalClassification> findAllSon(String[] fatherId);

    @Query(value = "select * from technical_classification where name = ?1 and father_id is null limit 0,1", nativeQuery = true)
    TechnicalClassification findFatherByName(String name);

    @Query(value = "select * from technical_classification where name = ?1 and father_id is not null limit 0,1", nativeQuery = true)
    TechnicalClassification findSonByName(String name);
}