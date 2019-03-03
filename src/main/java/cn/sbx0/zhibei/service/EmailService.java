package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.entity.User;
import cn.sbx0.zhibei.tool.StringTools;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送邮件类
 */
@Service
public class EmailService {
    private String FORM_WHERE; // 邮件发布地址
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


    @Value("${config.FORM_WHERE}")
    public void setFORM_WHERE(String FORM_WHERE) {
        this.FORM_WHERE = FORM_WHERE;
    }

    public boolean sendEmail(User user, String title, String content) {
        if (user == null) return false;
        if (StringTools.checkNullStr(user.getEmail())) return false;
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            //基本设置.
            helper.setFrom(FORM_WHERE); // 发送者
            helper.setTo(user.getEmail()); // 接收者
            helper.setSubject("[sbx0.cn]" + title + "（请勿回复此邮件）"); // 邮件主题
            Map<String, Object> model = new HashMap<>();
            model.put("username", user.getNickname() + "[@" + user.getName() + "]");
            model.put("content", content);
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("email.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            helper.setText(html, true);
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
