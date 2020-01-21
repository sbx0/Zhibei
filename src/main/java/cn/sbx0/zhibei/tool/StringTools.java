package cn.sbx0.zhibei.tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringTools {
    private static String KEY; // KEY
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * 验证邮箱格式
     *
     * @param emailStr 邮箱
     * @return 是否正确
     */
    public static boolean checkNotEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return !matcher.find();
    }

    /**
     * 设置KEY 用于加密
     *
     * @param KEY 从配置文件中读取
     */
    @Value("${config.KEY}")
    public void setKEY(String KEY) {
        StringTools.KEY = KEY;
    }

    /**
     * 加密密码
     *
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        return getHash(password + KEY, "MD5");
    }

    /**
     * 简单的cookie验证加密
     * 用于判断cookie是否合法
     *
     * @param id 一般为用户ID
     * @return 加密后的验证字符串
     */
    public static String getKey(int id) {
        return StringTools.getHash(id + KEY, "MD5");
    }

    /**
     * 提取request中的json
     *
     * @param request request
     * @return json字符串
     */
    public static String getJson(HttpServletRequest request) {
        String str = "NULL";
        try {
            ServletInputStream is = request.getInputStream();
            int nRead = 1;
            int nTotalRead = 0;
            byte[] bytes = new byte[10240];
            while (nRead > 0) {
                nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
                if (nRead > 0) nTotalRead = nTotalRead + nRead;
            }
            str = new String(bytes, 0, nTotalRead, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 只显示ip的头和尾
     * 例如：127.0.0.1 -> 127.*.*.1
     *
     * @param ip ip
     * @return 隐藏中间两位的ip
     */
    public static String hideFullIp(String ip) {
        String[] ipNumber = ip.split("\\.");
        StringBuilder ipBuilder = new StringBuilder(ipNumber[0] + ".");
        for (int i = 1; i < ipNumber.length - 1; i++) {
            ipBuilder.append("*.");
        }
        ip = ipBuilder.toString();
        ip += ipNumber[ipNumber.length - 1];
        return ip;
    }

    /**
     * 检测字符串是否为空
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean checkNullStr(String str) {
        if (str == null) return true;
        if (str.length() == 0) return true;
        if (str.trim().equals("")) return true;
        if (str.trim().length() == 0) return true;
        if (killHTML(str).trim().length() == 0) return true;
        return false;
    }

    /**
     * 去html标签
     *
     * @param content 需要去除html的字符串
     * @return 去除html后的字符串
     */
    public static String killHTML(String content) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?</script>"; // script
        String regEx_style = "<style[^>]*?>[\\s\\S]*?</style>"; // style
        String regEx_html = "<[^>]+>"; // HTML tag

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(content);
        content = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(content);
        content = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(content);
        content = m_html.replaceAll(""); // 过滤html标签

        return content;
    }

    /**
     * 密码哈希
     * 还可以用于字符串加密
     *
     * @param source   原始密码
     * @param hashType 加密种类 一般为MD5
     * @return 加密后的密码
     */
    public static String getHash(String source, String hashType) {
        StringBuilder sb = new StringBuilder();
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance(hashType);
            md5.update(source.getBytes());
            for (byte b : md5.digest()) {
                sb.append(String.format("%02X", b)); // 10进制转16进制，X 表示以十六进制形式输出，02 表示不足两位前面补0输出
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
