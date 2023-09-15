package com.bovine.taotao.admin.web.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.admin.web.entity.SysDict;
import com.bovine.taotao.admin.web.modelAndView.model.DictModel;
import com.bovine.taotao.admin.web.service.SysDictService;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;

/**
 * 后台管理字典控制器
 * @author eden
 * @date 2022年10月16日下午7:59:13
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {

	@Autowired
	private SysDictService sysDictService;
	
	/**
	 * 字典列表数据
	 * @param form 前端提交form表单数据
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize(value = "hasAuthority('sys:dict:list')")
	public R list(DictModel form) {
		P<SysDict> p = sysDictService.getDictList(form);
		return R.ok(p);
	}

	/**
	 * 单配置信息
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize(value = "hasAuthority('sys:dict:info')")
	public R info(@PathVariable("id") Long id){
		SysDict dict = sysDictService.getById(id);
		return R.ok(dict);
	}

	/**
	 * 保存配置
	 */
	@Log("保存字典数据")
	@PostMapping("/save")
	@PreAuthorize(value = "hasAuthority('sys:dict:save')")
	public R save(@RequestBody SysDict dict){
		boolean save = sysDictService.save(dict);
		if(save) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改配置
	 */
	@Log("修改字典数据")
	@PostMapping("/update")
	@PreAuthorize(value = "hasAuthority('sys:dict:update')")
	public R update(@RequestBody SysDict dict){
		boolean update = sysDictService.updateById(dict);
		if(update) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除配置
	 */
	@Log("删除字典数据")
	@DeleteMapping ("/delete")
	@PreAuthorize(value = "hasAuthority('sys:dict:delete')")
	public R delete(@RequestBody Long[] ids){
		boolean remove = sysDictService.removeByIds(Arrays.asList(ids));
		if(remove) {
			return R.ok();
		}
		return R.error();
	}
	
	
}
