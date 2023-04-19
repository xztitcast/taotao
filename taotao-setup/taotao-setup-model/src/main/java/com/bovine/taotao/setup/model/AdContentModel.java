package com.bovine.taotao.setup.model;

import java.io.Serial;
import java.io.Serializable;

import com.bovine.taotao.common.core.serialize.LongIdKeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 小程序交互广告请求参数
 * @author: lujianxiong
 * @date: 2023/3/16 14:39
 */
@Setter
@Getter
public class AdContentModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 机构号
     */
    private Long tisid;

    /**
     * 广告类型
     */
    private String adType;

    /**
     * 广告位id
     */
    @JsonDeserialize(using = LongIdKeyDeserializer.class)
    private Long adId;

}
