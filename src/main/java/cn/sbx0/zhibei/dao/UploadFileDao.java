package cn.sbx0.zhibei.dao;

import cn.sbx0.zhibei.entity.UploadFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 上传文件 数据层
 */
public interface UploadFileDao extends PagingAndSortingRepository<UploadFile, Integer> {

    /**
     * 根据md5判断文件是否存在
     *
     * @param md5
     * @return
     */
    @Query(value = "select * from files where files.md5 = ?1", nativeQuery = true)
    UploadFile existsByMd5(String md5);

    @Query(value = "select * from files where files.type = ?1", nativeQuery = true)
    Page<UploadFile> findAllByType(String type, Pageable pageable);

}
