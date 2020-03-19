package cn.sbx0.zhibei.logic.file;

import cn.sbx0.zhibei.annotation.RoleCheck;
import cn.sbx0.zhibei.logic.BaseController;
import cn.sbx0.zhibei.logic.BaseService;
import cn.sbx0.zhibei.logic.ReturnStatus;
import cn.sbx0.zhibei.logic.user.base.UserBase;
import cn.sbx0.zhibei.logic.user.base.UserBaseService;
import cn.sbx0.zhibei.logic.user.info.UserInfo;
import cn.sbx0.zhibei.tool.FileTools;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文件上传 服务层
 */
@RestController
@RequestMapping("/file/upload")
public class FileUploadController extends BaseController<FileUpload, Integer> {
    @Resource
    private FileUploadService fileUploadService;
    @Resource
    private UserBaseService userBaseService;

    @Override
    public BaseService<FileUpload, Integer> getService() {
        return fileUploadService;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @RoleCheck(values = {
            "initial",
            "mechanism",
            "university",
            "enterprise",
            "advertisers",
            "auditors",
            "admin",
            "webSiteOwner"
    })
    @PostMapping("/avatar")
    public ObjectNode avatar(MultipartFile file) {
        UserInfo user = userBaseService.getLoginUser();
        ObjectNode json = initJSON();
        String md5;
        try {
            md5 = FileTools.getFileMD5String(file.getInputStream());
            FileUpload fileUpload = fileUploadService.md5Check(md5);
            if (fileUpload != null) {
                UserBase userBase = userBaseService.findById(user.getUserId());
                userBase.setAvatar("/upload/user" + fileUpload.getUserId() + "/" + fileUpload.getType() + "/" + fileUpload.getName());
                userBase = userBaseService.save(userBase);
                json.put(statusCode, ReturnStatus.success.getCode());
            } else {
                String path = fileUploadService.getPath();
                String fileName = fileUploadService.createFileName(file.getOriginalFilename());
                String filePath = fileUploadService.createPath(fileName, user.getUserId());
                File dirFile = new File(path);
                if (!dirFile.exists()) { // 目录不存在，创建目录
                    dirFile.mkdirs();
                }
                File saveFile = new File(path + filePath);
                try {
                    if (!saveFile.exists()) {
                        saveFile.createNewFile(); // 文件不存在，则创建
                    }
                    file.transferTo(saveFile); // 将文件保存
                    // 保存到数据库
                    fileUpload = new FileUpload();
                    fileUpload.setOriginalName(file.getOriginalFilename());
                    fileUpload.setName(fileName);
                    fileUpload.setMd5(md5);
                    fileUpload.setTime(new Date());
                    fileUpload.setExt(fileUploadService.getFileExt(fileName));
                    fileUpload.setType(fileUploadService.checkType(fileUpload.getExt()));
                    fileUpload.setUserId(user.getUserId());
                    fileUpload.setPublic(true);
                    File newFile = new File(fileUploadService.getPath() + filePath);
                    fileUpload.setSize(newFile.length());
                    fileUpload = fileUploadService.save(fileUpload);
                    if (fileUpload != null) {
                        UserBase userBase = userBaseService.findById(user.getUserId());
                        userBase.setAvatar("/upload/user" + user.getUserId() + "/" + fileUpload.getType() + "/" + fileUpload.getName());
                        userBase = userBaseService.save(userBase);
                        json.put(statusCode, ReturnStatus.success.getCode());
                    } else {
                        json.put(statusCode, ReturnStatus.failed.getCode());
                    }
                } catch (IOException e) {
                    json.put(statusCode, ReturnStatus.failed.getCode());
                    json.put("msg", e.getMessage());
                }
            }
        } catch (Exception e) {
            json.put(statusCode, ReturnStatus.failed.getCode());
            json.put("msg", e.getMessage());
        }
        return json;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @RoleCheck(values = {
            "initial",
            "mechanism",
            "university",
            "enterprise",
            "advertisers",
            "auditors",
            "admin",
            "webSiteOwner"
    })
    @PostMapping("/submit")
    public ObjectNode submit(MultipartFile file) {
        UserInfo user = userBaseService.getLoginUser();
        ObjectNode json = initJSON();
        String md5;
        try {
            md5 = FileTools.getFileMD5String(file.getInputStream());
            FileUpload fileUpload = fileUploadService.md5Check(md5);
            if (fileUpload != null) {
                json.put(statusCode, ReturnStatus.repeatOperation.getCode());
                json.put("oName", fileUpload.getOriginalName());
                json.put("name", fileUpload.getName());
                json.put("type", fileUpload.getType());
                json.put("user", fileUpload.getUserId());
                json.put("md5", md5);
            } else {
                String path = fileUploadService.getPath();
                String fileName = fileUploadService.createFileName(file.getOriginalFilename());
                String filePath = fileUploadService.createPath(fileName, user.getUserId());
                File dirFile = new File(path);
                if (!dirFile.exists()) { // 目录不存在，创建目录
                    dirFile.mkdirs();
                }
                File saveFile = new File(path + filePath);
                try {
                    if (!saveFile.exists()) {
                        saveFile.createNewFile(); // 文件不存在，则创建
                    }
                    file.transferTo(saveFile); // 将文件保存
                    // 保存到数据库
                    fileUpload = new FileUpload();
                    fileUpload.setOriginalName(file.getOriginalFilename());
                    fileUpload.setName(fileName);
                    fileUpload.setMd5(md5);
                    fileUpload.setTime(new Date());
                    fileUpload.setExt(fileUploadService.getFileExt(fileName));
                    fileUpload.setType(fileUploadService.checkType(fileUpload.getExt()));
                    fileUpload.setUserId(user.getUserId());
                    fileUpload.setPublic(true);
                    File newFile = new File(fileUploadService.getPath() + filePath);
                    fileUpload.setSize(newFile.length());
                    fileUpload = fileUploadService.save(fileUpload);
                    if (fileUpload != null) {
                        json.put("oName", fileUpload.getOriginalName());
                        json.put("name", fileUpload.getName());
                        json.put("type", fileUpload.getType());
                        json.put("user", user.getUserId());
                        json.put("md5", md5);
                        json.put(statusCode, ReturnStatus.success.getCode());
                    } else {
                        json.put(statusCode, ReturnStatus.failed.getCode());
                    }
                } catch (IOException e) {
                    json.put(statusCode, ReturnStatus.failed.getCode());
                    json.put("msg", e.getMessage());
                }
            }
        } catch (Exception e) {
            json.put(statusCode, ReturnStatus.failed.getCode());
            json.put("msg", e.getMessage());
        }
        return json;
    }

}
