package com.bovine.taotao.common.mybatis.handler;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;

import java.util.List;

/**
 * 数组库数组转JSON
 * @author eden
 * @date 2023/9/16 20:30:30
 */
public class MybatisPlusList2JsonHandler extends AbstractJsonTypeHandler<List<String>> {
    @Override
    protected List<String> parse(String json) {
        return JSON.parseArray(json, String.class);
    }

    @Override
    protected String toJson(List<String> obj) {
        return JSON.toJSONString(obj);
    }
}
