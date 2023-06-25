package com.bovine.taotao.admin.web.filter;

import com.bovine.taotao.common.core.S;
import com.bovine.taotao.common.core.exception.custom.TaotaoServiceException;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * XSS过滤处理
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletRequest request;

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        //非json类型，直接返回
        if(!checkContentTypeIsJson()){
            return super.getInputStream();
        }

        //为空，直接返回
        String json = IOUtils.toString(super.getInputStream(), StandardCharsets.UTF_8);
        if (StringUtils.isBlank(json)) {
            return super.getInputStream();
        }

        //xss过滤
        json = xssEncode(json);
        final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return bis.read();
            }
        };
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (StringUtils.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = xssEncode(parameters[i]);
        }
        return parameters;
    }

    @Override
    public Map<String,String[]> getParameterMap() {
        Map<String,String[]> map = new LinkedHashMap<>();
        Map<String,String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = xssEncode(values[i]);
            }
            map.put(key, values);
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(xssEncode(name));
        if (StringUtils.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    private String xssEncode(String input) {
        return XssFilterWrapper.filter(input);
    }

    /**
     * 获取最原始的request
     */
    public HttpServletRequest getRequest() {
        return this.request;
    }

    /**
     * 获取最原始的request
     */
    public static HttpServletRequest getRequest(HttpServletRequest request) {
        if (request instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) request).getRequest();
        }

        return request;
    }
    /**
     * 判断是否是json请求，以前缀的方式
     */
    private boolean checkContentTypeIsJson() {
        String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
        return StringUtils.startsWithIgnoreCase(header, MediaType.APPLICATION_JSON_VALUE);
    }

    private static class XssFilterWrapper extends Safelist {

        private static final String SQL_REG = "\\b(and|or)\\b.{1,6}?(=|>|<|\\bin\\b|\\blike\\b)|\\/\\*.+?\\*\\/|<\\s*script\\b|\\bEXEC\\b|UNION.+?SELECT|UPDATE.+?SET|INSERT\\s+INTO.+?VALUES|(SELECT|DELETE).+?FROM|(CREATE|ALTER|DROP|TRUNCATE)\\s+(TABLE|DATABASE)";

        /**
         * XSS过滤
         */
        public static String filter(String html){
            String clean = Jsoup.clean(html, xssWhitelist());
            Pattern sqlPattern = Pattern.compile(SQL_REG, Pattern.CASE_INSENSITIVE);
            if(sqlPattern.matcher(clean.toLowerCase()).find()) {
                log.warn("请求存在SQL注入: {}", html);
                throw new TaotaoServiceException(S.SYSTEM_UNAUTHORIZED.getValue(), S.SYSTEM_UNAUTHORIZED.getMessage());
            }
            return clean;
        }

        /**
         * XSS过滤白名单
         */
        private static Safelist xssWhitelist(){
            return new Safelist()
                    //支持的标签
                    .addTags("a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", "div", "dl",
                            "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", "small",
                            "strike", "strong","sub", "sup", "table", "tbody", "td","tfoot", "th", "thead", "tr", "u","ul",
                            "embed","object","param","span")

                    //支持的标签属性
                    .addAttributes("a", "href", "class", "style", "target", "rel", "nofollow")
                    .addAttributes("blockquote", "cite")
                    .addAttributes("code", "class", "style")
                    .addAttributes("col", "span", "width")
                    .addAttributes("colgroup", "span", "width")
                    .addAttributes("img", "align", "alt", "height", "src", "title", "width", "class", "style")
                    .addAttributes("ol", "start", "type")
                    .addAttributes("q", "cite")
                    .addAttributes("table", "summary", "width", "class", "style")
                    .addAttributes("tr", "abbr", "axis", "colspan", "rowspan", "width", "style")
                    .addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width", "style")
                    .addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope","width", "style")
                    .addAttributes("ul", "type", "style")
                    .addAttributes("pre", "class", "style")
                    .addAttributes("div", "class", "id", "style")
                    .addAttributes("embed", "src", "wmode", "flashvars", "pluginspage", "allowFullScreen", "allowfullscreen",
                            "quality", "width", "height", "align", "allowScriptAccess", "allowscriptaccess", "allownetworking", "type")
                    .addAttributes("object", "type", "id", "name", "data", "width", "height", "style", "classid", "codebase")
                    .addAttributes("param", "name", "value")
                    .addAttributes("span", "class", "style")

                    //标签属性对应的协议
                    .addProtocols("a", "href", "ftp", "http", "https", "mailto")
                    .addProtocols("img", "src", "http", "https")
                    .addProtocols("blockquote", "cite", "http", "https")
                    .addProtocols("cite", "cite", "http", "https")
                    .addProtocols("q", "cite", "http", "https")
                    .addProtocols("embed", "src", "http", "https");
        }
    }
}