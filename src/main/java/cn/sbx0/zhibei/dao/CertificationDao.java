package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Certification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CertificationDao extends PagingAndSortingRepository<Certification, Integer> {
    /**
     * 判断是否有已提交但尚未审核或已通过的认证申请
     *
     * @param userId 用户ID
     * @return 有结果则存在 无则不存在
     */
    @Query(value = "SELECT 1 FROM Certification c WHERE c.user.id = ?1 AND c.passed = NULL OR c.passed = true")
    String existsByUserAndPassed(Integer userId);

    /**
     * 获取是否有已提交但尚未审核或已通过的认证申请
     *
     * @param userId 用户ID
     * @return 有结果则存在 无则不存在
     */
    @Query(value = "FROM Certification c WHERE c.user.id = ?1 AND c.passed = NULL OR c.passed = true")
    Certification findByUserAndPassed(Integer userId);
}