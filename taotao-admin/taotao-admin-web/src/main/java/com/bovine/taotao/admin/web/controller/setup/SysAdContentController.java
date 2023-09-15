package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.admin.web.modelAndView.view.AdPreviewView;
import com.bovine.taotao.admin.web.modelAndView.view.BaseSelectorView;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.entity.AdContent;
import com.bovine.taotao.setup.i.service.AdContentService;
import com.bovine.taotao.setup.model.AdContentModel;
import com.bovine.taotao.setup.model.SysAdContentModel;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台管理广告内容配置
 * @author eden
 * @date 2023年2月11日 下午4:49:50
 */
@RestController
@RequestMapping("/sys/ad/content")
public class SysAdContentController {

	@DubboReference
	private AdContentService adContentService;

	/**
	 * 查询列表数据
	 * @param bm
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:ad:content:list')")
	public R list(SysAdContentModel bm) {
		P<AdContent> p = adContentService.getBaseList(bm);
		return R.ok(p);
	}
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:ad:content:info')")
	public R info(@PathVariable Long id) {
		AdContent entity = adContentService.getEntity(id);
		return R.ok(entity);
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@Log("保存广告内容数据")
	@Fill(FillType.INSERT)
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:ad:content:save')")
	public R save(@RequestBody AdContent entity) {
		entity = adContentService.saveEntity(entity);
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
	@Log("修改广告内容数据")
	@Fill(FillType.UPDATE)
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:ad:content:update')")
	public R update(@RequestBody AdContent entity) {
		boolean update = adContentService.updateEntity(entity);
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
	@Log("删除广告内容数据")
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('sys:ad:content:delete')")
	public R delete(@RequestBody Long[] ids) {
		boolean delete = adContentService.delete(Arrays.asList(ids));
		if(delete) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 获取选择器
	 * @return
	 */
	@GetMapping("/selector")
	@PreAuthorize("hasAuthority('sys:ad:content:info')")
	public R selector() {
		List<BaseSelectorView<Long>> list = adContentService.getAdContentList().stream().map(a -> new BaseSelectorView<>(a.getName(), a.getId())).collect(Collectors.toList());
		return R.ok(list);
	}
	
	/**
	 * 查询广告位预览内容
	 * @param adId 广告ID
	 * @return
	 */
	@GetMapping("/preview/{adId}")
	@PreAuthorize("hasAuthority('sys:ad:content:list')")
	public R preview(@PathVariable Long adId) {
		AdContentModel model = new AdContentModel();
		model.setAdId(adId);
		List<AdPreviewView> list = adContentService.getAdContentList(model).stream().map(a -> new AdPreviewView(a.getId(), a.getPics())).collect(Collectors.toList());
		return R.ok(list);
	}
	 
}
