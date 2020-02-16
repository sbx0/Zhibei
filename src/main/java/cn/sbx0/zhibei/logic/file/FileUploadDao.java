package cn.sbx0.zhibei.logic.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 上传文件 数据层
 */
public interface FileUploadDao extends PagingAndSortingRepository<FileUpload, Integer> {
    @Query(value = "select * from file_upload where md5 = ?1", nativeQuery = true)
    FileUpload existsByMd5(String md5);

    @Query(value = "select * from file_upload where type = ?1", nativeQuery = true)
    Page<FileUpload> findAllByType(String type, Pageable pageable);
}