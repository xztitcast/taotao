package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.SysAdModelAndView;
import com.bovine.taotao.setup.SysAdModelAndView.SysAdJoinContentModelAndView;
import com.bovine.taotao.setup.entity.Ad;
import com.bovine.taotao.setup.i.service.AdJoinContentService;
import com.bovine.taotao.setup.i.service.AdService;
import com.bovine.taotao.setup.model.SysAdModel;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台管理广告位控制器
 * @author eden
 * @date 2023年2月11日 上午11:54:23
 */
@RestController
@RequestMapping("/sys/ad")
public class SysAdController {

	@DubboReference
	private AdService adService;
	
	@DubboReference
	private AdJoinContentService adJoinContentService;

	/**
	 * 查询列表数据
	 * @param bm
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:ad:list')")
	public R list(SysAdModel bm) {
		P<Ad> p = adService.getBaseList(bm);
		return R.ok(p);
	}
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:ad:info')")
	public R info(@PathVariable Long id) {
		List<SysAdJoinContentModelAndView> list = adJoinContentService.getAdJoinContentList(id).stream().map(a -> new SysAdJoinContentModelAndView(a.getContentId(), a.getSorted())).collect(Collectors.toList());
		return R.ok(list);
	}
	
	/**
	 * 保存
	 * @param mv
	 * @return
	 */
	@Log("保存广告位数据")
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:ad:save')")
	public R save(@RequestBody SysAdModelAndView mv) {
		boolean save = adJoinContentService.saveEntity(mv);
		if(save) {
			Ad entity = new Ad();
			entity.setId(mv.getAdId());
			entity.setNums(mv.getJoinViewList().size());
			adService.updateEntity(entity);
		}
		return R.ok();
	}
	
	/**
	 * 更新
	 * @param entity
	 * @return
	 */
	@Log("修改广告位数据")
	@Fill(FillType.UPDATE)
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:ad:update')")
	public R update(@RequestBody Ad entity) {
		boolean update = adService.updateEntity(entity);
		if(update) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@Log("删除广告位数据")
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('sys:ad:delete')")
	public R delete(@RequestBody Long[] ids) {
		boolean delete = adService.delete(Arrays.asList(ids));
		if(delete) {
			return R.ok();
		}
		return R.error();
	}
	
	
}
