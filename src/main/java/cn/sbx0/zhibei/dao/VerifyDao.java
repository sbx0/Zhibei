package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Verify;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 验证 数据层
 */
public interface VerifyDao extends PagingAndSortingRepository<Verify, Integer> {
    /**
     * 根据md5找寻验证
     *
     * @param md5
     * @return
     */
    @Query(value = "FROM Verify where md5 = ?1")
    Verify existsByMd5(String md5);
}
