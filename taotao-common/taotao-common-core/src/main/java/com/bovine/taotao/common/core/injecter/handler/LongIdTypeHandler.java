package com.bovine.taotao.common.core.injecter.handler;

/**
 * Long 类型ID转换器
 * @author eden
 * @date 2023/9/23 16:57:57
 */
public class LongIdTypeHandler implements IdTypeHandler<Long> {
    @Override
    public Long transform(String parameter) {
        return Long.valueOf(parameter);
    }
}
