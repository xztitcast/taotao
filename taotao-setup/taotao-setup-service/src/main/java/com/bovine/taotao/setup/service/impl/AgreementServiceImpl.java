package com.bovine.taotao.setup.service.impl;

import java.io.Serializable;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.setup.entity.Agreement;
import com.bovine.taotao.setup.i.service.AgreementService;
import com.bovine.taotao.setup.mapper.AgreementMapper;

/**
 * 协议配置业务实现类
 * @author eden
 * @date 2023年2月12日 下午2:50:57
 */
@Service("tissueConfigService")
@DubboService(interfaceClass = AgreementService.class)
public class AgreementServiceImpl extends ServiceImpl<AgreementMapper, Agreement> implements IService<Agreement>, AgreementService {

	@Override
	public Agreement getEntity(Integer id) {
		return this.getById(id);
	}

	@Override
	@Transactional
	public Agreement saveEntity(Agreement t) {
		this.save(t);
		return t;
	}

	@Override
	@Transactional
	public boolean updateEntity(Agreement t) {
		return this.updateById(t);
	}

	@Override
	public Agreement getAgreementInfo(Long tisid) {
		LambdaQueryWrapper<Agreement> query = Wrappers.lambdaQuery(Agreement.class).eq(Agreement::getTisid, tisid);
		return this.getOne(query);
	}

}
