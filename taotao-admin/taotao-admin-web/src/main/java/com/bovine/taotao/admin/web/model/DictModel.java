package com.bovine.taotao.admin.web.model;

import java.io.Serializable;

import com.bovine.taotao.common.core.BaseModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 字典Form表单实体类
 * @author eden
 * @date 2022年10月30日 下午4:48:04
 */
@Getter
@Setter
public class DictModel extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String key;
}
