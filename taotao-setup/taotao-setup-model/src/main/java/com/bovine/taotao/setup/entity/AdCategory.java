package com.bovine.taotao.setup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 广告类目实体类
 * 类目属于内容中的一个分类
 * @author eden
 * @date 2023年2月11日 下午4:17:25
 */
@Getter
@Setter
@TableName(value = "tb_ad_category")
public class AdCategory extends CreateEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Integer id;
	
	/**
	 * 广告类目名称
	 */
	private String name;
	
	/**
	 * 广告类目标签
	 */
	private String label;
}
