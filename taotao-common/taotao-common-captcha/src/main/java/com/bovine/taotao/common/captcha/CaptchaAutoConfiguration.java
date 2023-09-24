package com.bovine.taotao.common.captcha;

import com.anji.captcha.model.common.Const;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import com.anji.captcha.util.ImageUtils;
import com.anji.captcha.util.StringUtils;
import com.bovine.taotao.common.captcha.properties.CaptchaProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 人机验证自动装配
 */
@Configuration
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaAutoConfiguration {

    @Bean(name = "captchaCacheService")
    CaptchaCacheService captchaCacheService(CaptchaProperties prop){
        //缓存类型redis/local/....
        return CaptchaServiceFactory.getCache(prop.getCacheType().name());
    }

    @Bean
    @ConditionalOnMissingBean
    CaptchaService captchaService(CaptchaProperties prop) {
        Properties config = new Properties();
        config.put(Const.CAPTCHA_CACHETYPE, prop.getCacheType().name());
        config.put(Const.CAPTCHA_WATER_MARK, prop.getWaterMark());
        config.put(Const.CAPTCHA_FONT_TYPE, prop.getFontType());
        config.put(Const.CAPTCHA_TYPE, prop.getType().getCodeValue());
        config.put(Const.CAPTCHA_INTERFERENCE_OPTIONS, prop.getInterferenceOptions());
        config.put(Const.ORIGINAL_PATH_JIGSAW, prop.getJigsaw());
        config.put(Const.ORIGINAL_PATH_PIC_CLICK, prop.getPicClick());
        config.put(Const.CAPTCHA_SLIP_OFFSET, prop.getSlipOffset());
        config.put(Const.CAPTCHA_AES_STATUS, String.valueOf(prop.getAesStatus()));
        config.put(Const.CAPTCHA_WATER_FONT, prop.getWaterFont());
        config.put(Const.CAPTCHA_CACAHE_MAX_NUMBER, prop.getCacheNumber());
        config.put(Const.CAPTCHA_TIMING_CLEAR_SECOND, prop.getTimingClear());

        config.put(Const.HISTORY_DATA_CLEAR_ENABLE, prop.isHistoryDataClearEnable() ? "1" : "0");

        config.put(Const.REQ_FREQUENCY_LIMIT_ENABLE, prop.getReqFrequencyLimitEnable() ? "1" : "0");
        config.put(Const.REQ_GET_LOCK_LIMIT, prop.getReqGetLockLimit() + "");
        config.put(Const.REQ_GET_LOCK_SECONDS, prop.getReqGetLockSeconds() + "");
        config.put(Const.REQ_GET_MINUTE_LIMIT, prop.getReqGetMinuteLimit() + "");
        config.put(Const.REQ_CHECK_MINUTE_LIMIT, prop.getReqCheckMinuteLimit() + "");
        config.put(Const.REQ_VALIDATE_MINUTE_LIMIT, prop.getReqVerifyMinuteLimit() + "");

        config.put(Const.CAPTCHA_FONT_SIZE, prop.getFontSize() + "");
        config.put(Const.CAPTCHA_FONT_STYLE, prop.getFontStyle() + "");
        config.put(Const.CAPTCHA_WORD_COUNT, prop.getClickWordCount() + "");

        if ((StringUtils.isNotBlank(prop.getJigsaw()) && prop.getJigsaw().startsWith("classpath:"))
                || (StringUtils.isNotBlank(prop.getPicClick()) && prop.getPicClick().startsWith("classpath:"))) {
            //自定义resources目录下初始化底图
            config.put(Const.CAPTCHA_INIT_ORIGINAL, "true");
            CaptchaConfigurationResolver.initializeBaseMap(prop.getJigsaw(), prop.getPicClick());
        }
        return CaptchaServiceFactory.getInstance(config);
    }

    /**
     * 配置文件解析器
     * @author eden
     * @date 2023年9月11日 下午9:31:03
     */
    private static class CaptchaConfigurationResolver {

        private static Logger logger = LoggerFactory.getLogger(CaptchaConfigurationResolver.class);

        private static void initializeBaseMap(String jigsaw, String picClick) {
            ImageUtils.cacheBootImage(getResourcesImagesFile(jigsaw + "/original/*.png"),
                    getResourcesImagesFile(jigsaw + "/slidingBlock/*.png"),
                    getResourcesImagesFile(picClick + "/*.png"));
        }

        private static Map<String, String> getResourcesImagesFile(String path) {
            Map<String, String> imgMap = new HashMap<>();
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            try {
                Resource[] resources = resolver.getResources(path);
                for (Resource resource : resources) {
                    byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
                    String string = Base64.getEncoder().encodeToString(bytes);
                    String filename = resource.getFilename();
                    imgMap.put(filename, string);
                }
            } catch (Exception e) {
                logger.error("getResourcesImagesFile" ,e);
            }
            return imgMap;
        }
    }

    /**
     * 公共的人机验证请求
     * 可能有多个应用使用: 比如 小程序、APP、IOS、商户端、推派等等
     */
    @RestController
    @RequestMapping("/{module}/dynamic/captcha")
    public static class DynamicCaptchaController {

        @Autowired
        private CaptchaService captchaService;

        /**
         * 获取人机验证码
         * @param request
         * @return
         */
        @PostMapping("/get")
        public ResponseModel get(@RequestBody CaptchaVO data, HttpServletRequest request) {
            assert request.getRemoteHost() != null;
            data.setBrowserInfo(getRemoteId(request));
            return captchaService.get(data);
        }

        /**
         * 校验人机验证码
         * @param data
         * @param request
         * @return
         */
        @PostMapping("/check")
        public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
            data.setBrowserInfo(getRemoteId(request));
            return captchaService.check(data);
        }

        private String getRemoteId(HttpServletRequest request) {
            String xfwd = request.getHeader("X-Forwarded-For");
            String ip = getRemoteIpFromXfwd(xfwd);
            String ua = request.getHeader("user-agent");
            if (StringUtils.isNotBlank(ip)) {
                return ip + ua;
            }
            return request.getRemoteAddr() + ua;
        }

        private String getRemoteIpFromXfwd(String xfwd) {
            if (StringUtils.isNotBlank(xfwd)) {
                String[] ipList = xfwd.split(",");
                return StringUtils.trim(ipList[0]);
            }
            return null;
        }
    }
}
