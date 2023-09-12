package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.entity.MineArea;
import com.bovine.taotao.setup.i.service.MineAreaService;
import com.bovine.taotao.setup.view.MineAreaView;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台管理系统 "我的"页面区域控制器
 * @author eden
 * @date 2023年3月13日 下午4:28:00
 */
@RestController
@RequestMapping("/sys/mine/area")
public class SysMineAreaController {
	
	@DubboReference
	private MineAreaService mineAreaService;

	/**
	 * 查询我的页面区域数据
	 * @param mineId
	 * @return
	 */
	@GetMapping("/select/{mineId}")
	@PreAuthorize("hasAuthority('sys:mine:area:list')")
	public R select(@PathVariable Integer mineId) {
		List<MineAreaView> list = mineAreaService.getMineAreaList(mineId);
		return R.ok(list);
	}
	
	/**
	 * 新增
	 * @param entity
	 * @return
	 */
	@Log("保存我的页面区域数据")
	@Fill(FillType.INSERT)
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:mine:area:save')")
	public R save(@RequestBody MineArea entity) {
		entity = mineAreaService.saveEntity(entity);
		if(entity.getId() == null) {
			return R.error();
		}
		return R.ok();
	}
	
	/**
	 * 修改
	 * @param entity
	 * @return
	 */
	@Log("修改我的页面区域数据")
	@Fill(FillType.INSERT)
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:mine:area:update')")
	public R update(@RequestBody MineArea entity) {
		boolean update = mineAreaService.updateEntity(entity);
		if(!update){
			return R.error();
		}
		return R.ok();
	}
	
}
