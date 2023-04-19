package com.bovine.taotao.setup.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.TissueEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 模块配置实体类(兼容小程序与APP)
 * @author eden
 * @date 2023年2月14日 下午6:33:30
 */
@Getter
@Setter
@TableName(value = "tb_module")
public class Module extends TissueEntity<Long> implements Serializable {

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
     * 状态 0:无效 1:有效
     */
    private Integer status;
    
    /**
     * 跳转类型 1:内部链接 2:外部小程序 3:H5
     */
    private Integer jumptype;
    
    /**
     * 跳转外部小程序appid
     */
    private String jumpappid;
    
    /**
     * 排序(升序规则 开始0)
     */
    private Integer sorted;
    
    /**
     * 是否可分享(0:是 1:否)
     */
    private Integer shared;
}
