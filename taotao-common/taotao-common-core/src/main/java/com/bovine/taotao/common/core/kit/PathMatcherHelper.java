package com.bovine.taotao.common.core.kit;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Set;

/**
 * 路径匹配工具
 * @author eden
 * @date 2023/7/4 21:31:31
 */
public final class PathMatcherHelper {

    private static PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 路由匹配
     * @param patterns 路由匹配符集合
     * @param path 被匹配的路由
     * @return 是否匹配成功
     */
    public static boolean isMatch(Set<String> patterns, String path) {
        for (String pattern : patterns) {
            if (isMatch(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 路由匹配
     * @param pattern 路由匹配符
     * @param path 被匹配的路由
     * @return 是否匹配成功
     */
    public static boolean isMatch(String pattern, String path) {
        return pathMatcher.match(pattern, path);
    }
}
