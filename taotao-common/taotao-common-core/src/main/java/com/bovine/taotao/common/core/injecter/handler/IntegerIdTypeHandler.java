package com.bovine.taotao.common.core.injecter.handler;

/**
 * Integer 类型ID转换器
 * @author eden
 * @date 2023/9/23 16:59:59
 */
public class IntegerIdTypeHandler implements IdTypeHandler<Integer> {
    @Override
    public Integer transform(String parameter) {
        return Integer.valueOf(parameter);
    }
}
