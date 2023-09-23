package com.bovine.taotao.common.core.injecter.handler;

/**
 * ID类型转换器
 * @author eden
 * @date 2023/9/23 16:56:56
 */
public interface IdTypeHandler<T> {

    T transform(String parameter);
}
