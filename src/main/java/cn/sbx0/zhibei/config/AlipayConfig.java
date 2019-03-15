package cn.sbx0.zhibei.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 支付宝 配置文件
 */

@Configuration
public class AlipayConfig {
    @Value("${pay.alipay.gatewayUrl}")
    private String gatewayUrl;
    @Value("${pay.alipay.appId}")
    private String appId;
    @Value("${pay.alipay.appPrivateKey}")
    private String merchantPrivateKey;
    @Value("${pay.alipay.alipayPublicKey}")
    private String alipayPublicKey;
    @Value("${pay.alipay.returnUrl}")
    private String returnUrl;
    @Value("${pay.alipay.notifyUrl}")
    private String notifyUrl;
    @Value("${pay.alipay.signType}")
    private String signType;
    @Value("${pay.alipay.format}")
    private String format;
    @Value("${pay.alipay.charset}")
    private String charset;
    @Value("${pay.alipay.maxQueryRetry}")
    private String maxQueryRetry;
    @Value("${pay.alipay.queryDuration}")
    private String queryDuration;
    @Value("${pay.alipay.maxCancelRetry}")
    private String maxCancelRetry;
    @Value("${pay.alipay.cancelDuration}")
    private String cancelDuration;

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getSignType() {
        return signType;
    }

    public String getFormat() {
        return format;
    }

    public String getCharset() {
        return charset;
    }

    public String getMaxQueryRetry() {
        return maxQueryRetry;
    }

    public String getQueryDuration() {
        return queryDuration;
    }

    public String getMaxCancelRetry() {
        return maxCancelRetry;
    }

    public String getCancelDuration() {
        return cancelDuration;
    }
}
