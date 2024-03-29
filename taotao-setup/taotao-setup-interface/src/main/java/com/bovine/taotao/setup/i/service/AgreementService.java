package com.bovine.taotao.setup.i.service;

import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.setup.entity.Agreement;

/**
 * 协议配置业务接口
 * @author eden
 * @date 2023年2月12日 下午2:51:08
 */
public interface AgreementService extends FrameworkService<Agreement, Integer> {

	/**
	 * 根据机构号获取机构协议配置
	 * @param tisid
	 * @return
	 */
	Agreement getAgreementInfo(Long tisid);

}
