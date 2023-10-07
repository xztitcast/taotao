package com.bovine.taotao.setup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 广告与内容关联实体类
 * @author eden
 * @date 2023年2月25日 下午4:53:58
 */
@Getter
@Setter
@TableName(value = "tb_ad_join_content")
public class AdJoinContent extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 广告ID
	 */
	private Long adId;
	
	/**
	 * 广告内容ID
	 */
	private Long contentId;
	
	/**
	 * 排序
	 */
	private Integer sorted;
	
	public AdJoinContent() {
		
	}
	
	public AdJoinContent(Long adId, Long contentId, Integer sorted) {
		this.adId = adId;
		this.contentId = contentId;
		this.sorted = sorted;
	}
}
