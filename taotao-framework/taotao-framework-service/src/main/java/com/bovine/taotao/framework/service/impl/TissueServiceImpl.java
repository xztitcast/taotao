package com.bovine.taotao.framework.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.framework.entity.Tissue;
import com.bovine.taotao.framework.i.service.TissueService;
import com.bovine.taotao.framework.mapper.TissueMapper;

/**
 * 机构信息Service业务逻辑实现类
 * @author eden
 * @date 2023年7月1日 下午10:44:13
 */
@Service("tissueService")
@DubboService(interfaceClass = TissueService.class)
public class TissueServiceImpl extends ServiceImpl<TissueMapper, Tissue> implements IService<Tissue>, TissueService {

}
