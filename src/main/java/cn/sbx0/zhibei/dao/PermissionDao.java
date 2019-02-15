package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 权限 数据层
 */
public interface PermissionDao extends PagingAndSortingRepository<Permission, Integer> {
    /**
     * 判断权限是否存在
     *
     * @param url
     * @param str
     * @return 有结果则存在 无则不存在
     */
    @Query(value = "SELECT 1 FROM Permission p WHERE p.url = ?1 AND p.str = ?2")
    String existsByUrlAndStr(String url, String str);
}