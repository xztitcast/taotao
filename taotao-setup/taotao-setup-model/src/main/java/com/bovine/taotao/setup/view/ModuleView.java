package com.bovine.taotao.setup.view;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 模块视图
 * @author eden
 * @date 2023年3月13日 下午6:20:59
 */
@Getter
@Setter
public class ModuleView implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

	/**
	 * 模块名称
	 */
    private String name;
    
    /**
     * 描述
     */
    private String remark;

    /**
     * icon图片地址
     */
    private String icon;

    /**
     * 链接地址
     */
    private String linkurl;
    
    /**
     * 跳转类型 1:内部链接 2:外部小程序
     */
    private Integer jumptype;
    
    /**
     * 跳转外部小程序appid
     */
    private String jumpappid;
    
    /**
     * 是否可分享(0:是 1:否)
     */
    private Integer shared;
}
