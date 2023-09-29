package com.bovine.taotao.setup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 文章类目实体类
 * @author eden
 * @date 2023年2月22日 下午8:30:14
 */
@Getter
@Setter
@TableName("tb_classroom_category")
public class ClassroomCategory extends CreateEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 类目名称
	 */
	private String name;
}
