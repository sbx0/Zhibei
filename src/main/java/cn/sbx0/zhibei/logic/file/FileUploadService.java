package cn.sbx0.zhibei.logic.file;

import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.tool.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 上传文件 服务层
 */
@Service
public class FileUploadService extends BaseService<FileUpload, Integer> {
    @Resource
    private FileUploadDao fileUploadDao;

    @Override
    public PagingAndSortingRepository<FileUpload, Integer> getDao() {
        return fileUploadDao;
    }

    @Override
    public FileUpload getEntity() {
        return new FileUpload();
    }

    @Override
    public boolean checkDataValidity(FileUpload fileUpload) {
        return true;
    }

    public String path; // 上传路径

    public String getPath() {
        return path;
    }

    @Value("${config.UploadFileFolder}")
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 删除文件
     *
     * @param fileUpload
     * @return
     */
    public boolean deleteFile(FileUpload fileUpload) {
        try {
            File file = new File(path + "/" + fileUpload.getType() + "/" + fileUpload.getName());
            System.out.println(file.getPath());
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 文件列表
     *
     * @param page
     * @param size
     * @return
     */
    public Page<FileUpload> findAll(Integer page, Integer size, String sort, String direction, String type) {
        // 页数控制
        if (page > 9999) page = 9999;
        if (page < 0) page = 0;
        // 条数控制
        if (size > 1000) size = 1000;
        if (size < 1) size = 1;
        if (size == 0) size = 10;
        switch (sort) {
            case "id":
            case "ext":
            case "type":
            case "size":
            case "time":
                break;
            default:
                sort = "id";

        }
        Sort s;
        switch (direction) {
            case "desc":
                s = new Sort(Sort.Direction.DESC, sort);
                break;
            case "asc":
                s = new Sort(Sort.Direction.ASC, sort);
                break;
            default:
                s = new Sort(Sort.Direction.DESC, sort);
        }
        Pageable pageable = PageRequest.of(page, size, s);
        try {
            // 分页查询
            Page<FileUpload> fileUploads;
            if (StringTools.checkNullStr(type))
                fileUploads = fileUploadDao.findAll(pageable);
            else {
                switch (type) {
                    case "image":
                    case "video":
                    case "doc":
                    case "zip":
                    case "other":
                        fileUploads = fileUploadDao.findAllByType(type, pageable);
                        break;
                    default:
                        fileUploads = fileUploadDao.findAll(pageable);
                }
            }
            if (fileUploads.getContent().size() > 0)
                return fileUploads;
            else
                return null;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 判断文件是否存在
     *
     * @param md5
     * @return
     */
    public FileUpload md5Check(String md5) {
        return fileUploadDao.existsByMd5(md5);
    }

    /**
     * 创建 路径
     *
     * @param fileName
     * @return
     */
    public String createPath(String fileName, Integer userId) {
        String ext = getFileExt(fileName);
        String type = checkType(ext) + "/";
        String dir = "user" + userId + "/" + type;
        File dirFile = new File(path + dir);
        if (!dirFile.exists()) { // 目录不存在，创建目录
            dirFile.mkdirs();
        }
        return dir + fileName;
    }

    /**
     * 获取文件扩展名
     *
     * @return string
     */
    public String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 创建唯一文件名
     *
     * @return 文件名
     */
    public String createFileName(String originalFilename) {
        String ext = getFileExt(originalFilename);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = simpleDateFormat.format(date);
        int randomNum = (int) (Math.random() * 899 + 100);
        String fileName = dateString + randomNum + ext;
        return fileName;
    }

    /**
     * 获取指定文件夹下的文件
     *
     * @param path
     * @return
     */
    public String getFile(String path) {
        String list = "";
        File file = new File(path);
        File[] array = file.listFiles();
        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                list += array[i].getPath();
            } else if (array[i].isDirectory()) {
                list += getFile(array[i].getPath());
            }
        }
        return list;
    }

    /**
     * 判断类型
     *
     * @param ext
     * @return
     */
    public String checkType(String ext) {
        switch (ext) {
            case ".jpg":
            case ".jpeg":
            case ".png":
            case ".gif":
            case ".bmp":
            case ".tif":
            case ".pcx":
            case ".tga":
            case ".exif":
            case ".fpx":
            case ".svg":
            case ".psd":
            case ".cdr":
            case ".pcd":
            case ".dxf":
            case ".ufo":
            case ".eps":
            case ".ai":
            case ".raw":
            case ".WMF":
            case ".webp":
                return "image";
            case ".zip":
            case ".7z":
            case ".XZ":
            case ".BZIP2":
            case ".GZIP":
            case ".TAR":
            case ".WIM":
                return "zip";
            case ".doc":
            case ".pdf":
            case ".txt":
            case ".md":
                return "doc";
            case ".mp4":
            case ".flv":
            case ".avi":
            case ".wmv":
            case ".asf":
            case ".wmvhd":
            case ".dat":
            case ".vob":
            case ".mpg":
            case ".mpeg":
            case ".3gp":
            case ".3g2":
            case ".mkv":
            case ".rm":
            case ".rmvb":
            case ".mov":
            case ".qt":
            case ".ogg":
            case ".ogv":
            case ".oga":
            case ".mod":
                return "video";
            default:
                return "other";
        }
    }
}
