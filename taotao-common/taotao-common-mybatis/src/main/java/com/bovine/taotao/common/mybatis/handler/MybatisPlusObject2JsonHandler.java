package com.bovine.taotao.common.mybatis.handler;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;

/**
 * 数据库对象转JSON
 * @author eden
 * @date 2023/9/16 20:34:34
 */
public class MybatisPlusObject2JsonHandler extends AbstractJsonTypeHandler<ObjectTypeHandler> {

    @Override
    protected ObjectTypeHandler parse(String json) {
        return JSON.parseObject(json, ObjectTypeHandler.class);
    }

    @Override
    protected String toJson(ObjectTypeHandler obj) {
        return JSON.toJSONString(obj);
    }
}
