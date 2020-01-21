package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.UploadFile;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.UploadFileService;
import cn.sbx0.zhibei.tool.FileTools;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;

/**
 * 文件上传 服务层
 */
@Controller
@RequestMapping("/file")
public class UploadFileController extends BaseController<UploadFile, Integer> {
    @Resource
    private UploadFileService uploadFileService;

    @Override
    public BaseService<UploadFile, Integer> getService() {
        return uploadFileService;
    }

    @Autowired
    public UploadFileController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 删除文件
     *
     * @param id id
     * @return
     */
    @Override
    public ObjectNode delete(Integer id) {
        UploadFile uploadFile = uploadFileService.findById(id);
        if (uploadFile == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
            return json;
        } else {
            // 从cookie中获取登陆用户信息
            User user = userService.getUser();
            if (user != null) {
                // 检测权限
                if (userService.checkPermission(user)) {
                    // 先删除真实文件，后删除数据库
                    if (uploadFileService.deleteFile(uploadFile)) {
                        return super.delete(id);
                    } else {
                        json.put(STATUS_NAME, STATUS_CODE_FILED);
                    }
                } else {
                    json.put(STATUS_NAME, STATUS_CODE_NO_PERMISSION);
                }
            } else {
                json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            }
        }
        return json;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping("/upload")
    public ObjectNode upload(MultipartFile file) {
        // 从cookie中获取登陆用户信息
        json = mapper.createObjectNode();
        User user = userService.getUser();
        // 未登录 直接否
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
        } else {
            // 无权限 直接否
            if (!userService.checkPermission(user)) {
                json.put(STATUS_NAME, STATUS_CODE_NO_PERMISSION);
            } else {
                String md5;
                try {
                    md5 = FileTools.getFileMD5String(file.getInputStream());
                    UploadFile uploadFile = uploadFileService.md5Check(md5);
                    if (uploadFile != null) {
                        json.put(STATUS_NAME, STATUS_CODE_REPEAT);
                        json.put("oName", uploadFile.getOriginalName());
                        json.put("name", uploadFile.getName());
                        json.put("type", uploadFile.getType());
                        json.put("md5", md5);
                    } else {
                        String path = uploadFileService.getPath();
                        String fileName = uploadFileService.createFileName(file.getOriginalFilename());
                        String filePath = uploadFileService.createPath(fileName);
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
                            uploadFile = new UploadFile();
                            uploadFile.setOriginalName(file.getOriginalFilename());
                            uploadFile.setName(fileName);
                            uploadFile.setMd5(md5);
                            uploadFile.setTime(new Date());
                            uploadFile.setExt(uploadFileService.getFileExt(fileName));
                            uploadFile.setType(uploadFileService.checkType(uploadFile.getExt()));
                            uploadFile.setBanned(false);
                            File newFile = new File(uploadFileService.getPath() + filePath);
                            uploadFile.setSize(newFile.length());
                            if (uploadFileService.save(uploadFile)) {
                                json.put("oName", uploadFile.getOriginalName());
                                json.put("name", uploadFile.getName());
                                json.put("type", uploadFile.getType());
                                json.put("md5", md5);
                                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
                            } else {
                                json.put(STATUS_NAME, STATUS_CODE_FILED);
                            }
                        } catch (IOException e) {
                            json.put(STATUS_NAME, STATUS_CODE_EXCEPTION);
                            json.put("msg", e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    json.put(STATUS_NAME, STATUS_CODE_EXCEPTION);
                    json.put("msg", e.getMessage());
                }
            }
        }
        return json;
    }

    /**
     * 根据md5将上传的文件设置为头像
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping("/avatar")
    public ObjectNode avatar(String md5) {
        json = mapper.createObjectNode();
        User user = userService.getUser();
        // 未登录
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        // 无权限
        if (!userService.checkPermission(user)) {
            json.put(STATUS_NAME, STATUS_CODE_NO_PERMISSION);
            return json;
        }
        UploadFile uploadFile = uploadFileService.md5Check(md5);
        if (uploadFile != null) {
            user.setAvatar("/upload/" + uploadFile.getType() + "/" + uploadFile.getName());
            userService.save(user);
            json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
        } else {
            json.put(STATUS_NAME, STATUS_CODE_FILED);
        }
        return json;
    }

}
