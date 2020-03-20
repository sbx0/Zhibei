package cn.sbx0.zhibei.logic.address;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 地区 数据层
 */
public interface AddressBaseDao extends PagingAndSortingRepository<AddressBase, String> {
    @Query(value = "select * from address_base where name = ?1", nativeQuery = true)
    AddressBase findOneByName(String name);

    @Query(value = "select * from address_base where father_id is null", nativeQuery = true)
    List<AddressBase> findAllFather();

    @Query(value = "select * from address_base where father_id = ?1", nativeQuery = true)
    List<AddressBase> findAllSon(String fatherId);

    @Query(value = "select * from address_base where father_id in ?1", nativeQuery = true)
    List<AddressBase> findAllSons(String[] fatherIds);
}