package com.bovine.taotao.setup.web.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.utils.BeanCopierUtil;
import com.bovine.taotao.setup.entity.Mine;
import com.bovine.taotao.setup.i.service.MineAreaService;
import com.bovine.taotao.setup.i.service.MineService;
import com.bovine.taotao.setup.view.MineView;

/**
 * 前端交互 我的(个人中心)控制器
 * @author eden
 * @date 2023年3月13日 下午4:05:57
 */
@RestController
@RequestMapping("/setup/mine")
public class MineController {
	
	@DubboReference
	private MineService mineService;

	@DubboReference
	private MineAreaService mineAreaService;
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info")
	public R info(@RequestHeader Long tisid) {
		Mine entity = mineService.getMine(tisid);
		if(entity == null) {
			return R.error();
		}
		MineView mv = new MineView();
		BeanCopierUtil.copyProperties(entity, mv);
		mv.setViewList(mineAreaService.getMineAreaList(entity.getId()));
		return R.ok(mv);
	}
}
