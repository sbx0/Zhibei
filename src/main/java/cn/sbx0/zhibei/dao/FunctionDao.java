package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 操作 数据层
 */
public interface FunctionDao extends PagingAndSortingRepository<Function, Integer> {

    /**
     * 判断用户是否已经做过该操作了
     *
     * @param path
     * @param u_id
     * @param type
     * @return
     */
    @Query(value = "SELECT 1 FROM Function f WHERE f.path = ?1 AND f.user.id = ?2 AND f.type = ?3")
    String existsByPathAndUserAndType(String path, Integer u_id, String type);

    /**
     * 判断用户是否已经做过该操作了
     *
     * @param path
     * @param u_id
     * @param type
     * @return
     */
    @Query(value = "FROM Function f WHERE f.path = ?1 AND f.user.id = ?2 AND f.type = ?3")
    Function findByPathAndUserAndType(String path, Integer u_id, String type);

    /**
     * 根据路径和用户查找
     *
     * @param path
     * @param u_id
     * @param pageable
     * @return
     */
    @Query(value = "FROM Function f WHERE f.path = ?1 AND f.user.id = ?2")
    Page<Function> findByPathAndUser(String path, Integer u_id, Pageable pageable);

    /**
     * 统计次数
     *
     * @param path
     * @return
     */
    @Query(value = "SELECT COUNT (f) FROM Function f WHERE f.path = ?1 AND f.type = ?2")
    Integer countByPath(String path, String type);
}
