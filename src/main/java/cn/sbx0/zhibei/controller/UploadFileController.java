package cn.sbx0.zhibei.controller;

import cn.sbx0.zhibei.entity.UploadFile;
import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.service.BaseService;
import cn.sbx0.zhibei.service.UploadFileService;
import cn.sbx0.zhibei.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;

/**
 * 文件上传 服务层
 */
@RequestMapping("/file")
@Controller
public class UploadFileController extends BaseController<UploadFile, Integer> {
    @Resource
    private UploadFileService uploadFileService;

    @Autowired
    public UploadFileController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BaseService<UploadFile, Integer> getService() {
        return uploadFileService;
    }

    /**
     * 删除文件
     *
     * @param id      id
     * @param session
     * @param request
     * @return
     */
    @Override
    public ObjectNode delete(Integer id, HttpSession session, HttpServletRequest request) {
        UploadFile uploadFile = uploadFileService.findById(id);
        if (uploadFile == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_FOUND);
            return json;
        } else {
            // 从cookie中获取登陆用户信息
            User user = userService.getUser(session, request);
            if (user != null) {
                // 检测权限
                if (userService.checkPermission(request, user)) {
                    // 先删除真实文件，后删除数据库
                    if (uploadFileService.deleteFile(uploadFile)) {
                        return super.delete(id, session, request);
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
     * 1.匹配md5检查文件存在与否
     *
     * @param md5File
     * @param session
     * @param request
     * @return
     */
    @PostMapping("/md5Check")
    @ResponseBody
    public ObjectNode md5Check(@RequestParam(value = "md5File") String md5File,
                               HttpSession session,
                               HttpServletRequest request) {
        json = mapper.createObjectNode();
        // 从cookie中获取登陆用户信息
        User user = userService.getUser(session, request);
        // 未登录 直接否
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        // 检测权限
        if (userService.checkPermission(request, user)) {
            UploadFile uploadFile = uploadFileService.md5Check(md5File);
            if (uploadFile != null) {
                json.put(STATUS_NAME, STATUS_CODE_FILED);
                json.put("oName", uploadFile.getOriginalName());
                json.put("name", uploadFile.getName());
                json.put("type", uploadFile.getType());
            } else {
                json.put(STATUS_NAME, STATUS_CODE_SUCCESS);
            }
        } else {
            json.put(STATUS_NAME, STATUS_CODE_NO_PERMISSION);
        }
        return json;
    }

    /**
     * 2. 检查切片是否存在
     *
     * @param md5File
     * @param chunk
     * @param session
     * @param request
     * @return
     */
    @PostMapping("/chunkCheck")
    @ResponseBody
    public Boolean chunkCheck(@RequestParam(value = "md5File") String md5File,
                              @RequestParam(value = "chunk") Integer chunk,
                              HttpSession session,
                              HttpServletRequest request) {
        // 从cookie中获取登陆用户信息
        User user = userService.getUser(session, request);
        // 未登录 直接否
        if (user == null) return false;
        // 无权限 直接否
        if (userService.checkPermission(request, user)) return false;
        Boolean exist = false;
        String path = uploadFileService.getPath() + "/temp/" + md5File + "/";//分片存放目录
        String chunkName = chunk + ".tmp";//分片名
        File file = new File(path + chunkName);
        if (file.exists()) {
            exist = true;
        }
        return exist;
    }

    /**
     * 3. 上传文件
     *
     * @param file
     * @param md5File
     * @param chunk
     * @param session
     * @param request
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public Boolean upload(@RequestParam(value = "file") MultipartFile file,
                          @RequestParam(value = "md5File") String md5File,
                          @RequestParam(value = "chunk", required = false) Integer chunk,
                          HttpSession session,
                          HttpServletRequest request) {
        // 从cookie中获取登陆用户信息
        User user = userService.getUser(session, request);
        // 未登录 直接否
        if (user == null) return false;
        // 无权限 直接否
        if (userService.checkPermission(request, user)) return false;
        String path = uploadFileService.getPath() + "/temp/" + md5File + "/";
        File dirFile = new File(path);
        if (!dirFile.exists()) { // 目录不存在，创建目录
            dirFile.mkdirs();
        }
        String chunkName;
        if (chunk == null) { // 表示是小文件，还没有一片
            chunkName = "0.tmp";
        } else {
            chunkName = chunk + ".tmp";
        }
        String filePath = path + chunkName;
        File saveFile = new File(filePath);
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile(); // 文件不存在，则创建
            }
            file.transferTo(saveFile); // 将文件保存
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 4. 将切片拼接成文件
     *
     * @param chunks
     * @param md5File
     * @param name
     * @param session
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/merge")
    @ResponseBody
    public ObjectNode merge(@RequestParam(value = "chunks", required = false) Integer chunks,
                            @RequestParam(value = "md5File") String md5File,
                            @RequestParam(value = "name") String name,
                            HttpSession session,
                            HttpServletRequest request) throws Exception {
        ObjectNode json = mapper.createObjectNode();
        // 从cookie中获取登陆用户信息
        User user = userService.getUser(session, request);
        // 未登录
        if (user == null) {
            json.put(STATUS_NAME, STATUS_CODE_NOT_LOGIN);
            return json;
        }
        // 无权限
        if (userService.checkPermission(request, user)) {
            json.put(STATUS_NAME, STATUS_CODE_NO_PERMISSION);
            return json;
        }
        String path = uploadFileService.getPath();
        String fileName = uploadFileService.createFileName(name);
        String filePath = uploadFileService.createPath(fileName);
        json.put("url", filePath);
        // 合成后的文件
        FileOutputStream fileOutputStream = new FileOutputStream(path + filePath);
        try {
            byte[] buf = new byte[1024];
            for (long i = 0; i < chunks; i++) {
                String chunkFile = i + ".tmp";
                File file = new File(path + "/temp/" + md5File + "/" + chunkFile);
                InputStream inputStream = new FileInputStream(file);
                int len;
                while ((len = inputStream.read(buf)) != -1) {
                    fileOutputStream.write(buf, 0, len);
                }
                inputStream.close();
            }
            // 删除临时文件
            File file = new File(path + "/temp/" + md5File);
            if (file.isDirectory()) {
                File[] childFiles = file.listFiles();
                // 文件夹有内容,先删除内容
                if (!(childFiles == null || childFiles.length == 0)) {
                    // 删除文件夹内容
                    boolean reslut = true;
                    for (File item : file.listFiles()) {
                        reslut = reslut && item.delete();
                    }
                }
                // 删除文件夹
                file = new File(path + "/temp/" + md5File);
                file.delete();
            }
        } catch (Exception e) {
            json.put("status", 1);
        } finally {
            json.put("status", 0);
            fileOutputStream.close();
        }
        // 保存到数据库
        UploadFile uploadFile = new UploadFile();
        uploadFile.setOriginalName(name);
        uploadFile.setName(fileName);
        uploadFile.setMd5(md5File);
        uploadFile.setTime(new Date());
        uploadFile.setExt(uploadFileService.getFileExt(fileName));
        uploadFile.setType(uploadFileService.checkType(uploadFile.getExt()));
        uploadFile.setBanned(false);
        File newFile = new File(uploadFileService.getPath() + filePath);
        uploadFile.setSize(newFile.length());
        uploadFileService.save(uploadFile);
        return json;
    }

}
