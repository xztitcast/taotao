package com.bovine.taotao.common.core.injecter.handler;

/**
 * String 类型ID转换器
 * @author eden
 * @date 2023/9/23 16:58:58
 */
public class StringIdTypeHandler implements IdTypeHandler<String> {
    @Override
    public String transform(String parameter) {
        return parameter;
    }
}
