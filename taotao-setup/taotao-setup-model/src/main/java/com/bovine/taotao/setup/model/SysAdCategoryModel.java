package com.bovine.taotao.setup.model;

import java.io.Serial;

import com.bovine.taotao.common.core.BaseModel;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 查询列表数据 的查询条件
 * @author: lujianxiong
 * @date: 2023/3/9 18:32
 */
@Setter
@Getter
public class SysAdCategoryModel extends BaseModel {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 类目名称
     */
    private String name;

}
