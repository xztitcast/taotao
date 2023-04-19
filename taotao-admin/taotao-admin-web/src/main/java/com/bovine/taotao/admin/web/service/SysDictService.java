package com.bovine.taotao.admin.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bovine.taotao.admin.web.entity.SysDict;
import com.bovine.taotao.admin.web.model.DictModel;
import com.bovine.taotao.common.core.P;

/**
 * 数据字典业务接口
 * @author eden
 * @date 2022/10/30 11:08:08
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 获取字典列表
     * @param form
     * @return
     */
    P<SysDict> getDictList(DictModel form);
}
