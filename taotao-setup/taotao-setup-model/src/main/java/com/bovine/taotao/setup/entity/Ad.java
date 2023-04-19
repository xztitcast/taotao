package com.bovine.taotao.setup.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.TissueEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 广告实体类
 * @author eden
 * @date 2023年2月11日 上午11:30:06
 */
@Getter
@Setter
@TableName(value = "tb_ad")
public class Ad extends TissueEntity<Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 导航页名称(模块页)
	 */
	private String navigateName;
	
	/**
	 * 广告类型(实际是固定编号与前端商量固定)
	 */
	private String adtype;
	
	/**
	 * 广告条数
	 */
	private Integer nums;

    /**
     * 是否切屏0否 1是
     */
    private Integer change;

    /**
     * 是否有效 0无效 1有效
     */
    private Integer status;

    /**
     * 位置(1横栏banner,2底部,3首页中部抢优惠)
     */
    private Integer position;
    
    /**
     * 排序升序规则
     */
    private Integer sorted;

}
