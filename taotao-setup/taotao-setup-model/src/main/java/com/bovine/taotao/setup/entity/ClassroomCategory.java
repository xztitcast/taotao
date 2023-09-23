package com.bovine.taotao.setup.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 文章类目实体类
 * @author eden
 * @date 2023年2月22日 下午8:30:14
 */
@Getter
@Setter
@TableName("tb_classroom_category")
public class ClassroomCategory extends CreateEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 类目名称
	 */
	private String name;
}
