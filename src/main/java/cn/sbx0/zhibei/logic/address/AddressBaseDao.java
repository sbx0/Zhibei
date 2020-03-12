package cn.sbx0.zhibei.logic.address;

import cn.sbx0.zhibei.logic.user.base.UserBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * 地区 数据层
 */
public interface AddressBaseDao extends PagingAndSortingRepository<AddressBase, Integer> {
    @Query(value = "select * from address_base where father_id is null", nativeQuery = true)
    List<AddressBase> findAllFather();

    @Query(value = "select * from address_base where father_id = ?1", nativeQuery = true)
    List<AddressBase> findAllSon(Integer fatherId);
}