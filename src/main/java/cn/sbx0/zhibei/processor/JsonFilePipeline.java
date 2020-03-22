//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.sbx0.zhibei.processor;

import cn.sbx0.zhibei.tool.DateTools;
import com.alibaba.fastjson.JSON;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

public class JsonFilePipeline extends FilePersistentBase implements Pipeline {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public JsonFilePipeline() {
        this.setPath("/data/webmagic");
    }

    public JsonFilePipeline(String path) {
        this.setPath(path);
    }

    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getUUID() + PATH_SEPERATOR;
        try {
            Date now = new Date();
            PrintWriter printWriter = new PrintWriter(new FileWriter(this.getFile(
                    path + "data" + DateTools.format(now) + ".json"),
                    true));
            printWriter.write(JSON.toJSONString(resultItems.getAll()) + ',');
            printWriter.close();
        } catch (IOException var5) {
            this.logger.warn("write file error", var5);
        }

    }
}
