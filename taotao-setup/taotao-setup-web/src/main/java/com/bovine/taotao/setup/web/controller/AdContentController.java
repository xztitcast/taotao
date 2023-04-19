package com.bovine.taotao.setup.web.controller;

import java.util.List;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovine.taotao.common.core.R;
import com.bovine.taotao.setup.entity.AdContent;
import com.bovine.taotao.setup.i.service.AdContentService;
import com.bovine.taotao.setup.model.AdContentModel;

/**
 * 前端(小程序、APP) 广告位控制器
 * @author eden
 * @date 2023年3月1日 上午11:08:54
 */
@Validated
@RestController
@RequestMapping("/setup/ad/content")
public class AdContentController {
	
	@DubboReference
	private AdContentService adContentService;

	/**
	 * 根据广告位id 或 机构号和广告类型
	 * 查询广告信息
	 * @return
	 */
	@GetMapping("/list")
	public R list(@RequestHeader Long tisid,
				  AdContentModel model) {
		model.setTisid(tisid);
		List<AdContent> list = adContentService.getAdContentList(model);
		return R.ok(list);
	}
}
