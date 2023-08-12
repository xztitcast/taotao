package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.entity.Agreement;
import com.bovine.taotao.setup.i.service.AgreementService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 后台管理系统协议控制器
 * @author eden
 * @date 2023年2月12日 下午3:02:27
 */
@RestController
@RequestMapping("/sys/agreement")
public class SysAgreementController {
	
	@DubboReference
	private AgreementService agreementService;

	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:agreement:info')")
	public R info(@PathVariable Integer id) {
		Agreement entity = agreementService.getEntity(id);
		return R.ok(entity);
	}

	/**
	 * 根据机构id查询单条数据
	 * @param tisid
	 * @return
	 */
	@GetMapping("/getInfo/{tisid}")
	@PreAuthorize("hasAuthority('sys:agreement:info')")
	public R getInfo(@PathVariable Long tisid) {
		Agreement entity = agreementService.getAgreementInfo(tisid);
		return R.ok(entity);
	}


	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@Log("保存协议数据")
	@Fill(FillType.INSERT)
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:agreement:save')")
	public R save(@RequestBody Agreement entity) {
		entity = agreementService.saveEntity(entity);
		if(entity.getId() == null) {
			return R.error();
		}
		return R.ok();
	}
	
	/**
	 * 更新
	 * @param entity
	 * @return
	 */
	@Log("修改协议数据")
	@Fill(FillType.UPDATE)
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:agreement:update')")
	public R update(@RequestBody Agreement entity) {
		boolean update = agreementService.updateEntity(entity);
		if(update) {
			return R.ok();
		}
		return R.error();
	}
}
