package com.bovine.taotao.setup.service.controller;

import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.utils.BeanCopierUtil;
import com.bovine.taotao.setup.entity.Layout;
import com.bovine.taotao.setup.entity.LayoutNavigate;
import com.bovine.taotao.setup.i.service.LayoutNavigateService;
import com.bovine.taotao.setup.i.service.LayoutService;
import com.bovine.taotao.setup.view.NavigateTabBarView;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 前端 布局导航控制器
 * @author eden
 * @date 2023年2月14日 下午1:44:17
 */
@RestController
@RequestMapping("/setup/layout")
public class LayoutController {

	@DubboReference
	private LayoutService layoutService;
	
	@DubboReference
	private LayoutNavigateService layoutnavigateService;

	/**
	 * 获取指定类型布局数据
	 * 调用此接口需要有加密验签，否则接口暴露之后会被其他人调用(后续把加密规则补上)
	 * @param tisid
	 * @param type 类型1:小程序 2:安卓 3:IOS
	 * @return
	 */
	@GetMapping("/tabBar")
	public R tabBar(@RequestHeader Long tisid, @RequestParam(value = "type", defaultValue = "1") Integer type) {
		Layout entity = layoutService.getLayout(tisid, type);
		if(entity == null) {
			return R.error("机构ID不存在!");
		}
		NavigateTabBarView nv = new NavigateTabBarView();
		BeanCopierUtil.copyProperties(entity, nv);
		List<LayoutNavigate> list = layoutnavigateService.getLayoutNavigateList(entity.getId());
		nv.setList(list.stream().map(icon -> {
			if (StringUtils.isNotBlank(icon.getPageParameters())) {
                icon.setPagePath(icon.getPagePath()+"?"+icon.getPageParameters());
            }
			return NavigateTabBarView.TabBarList.builder()
					.iconPath(icon.getDefaultIcon())
					.selectedIconPath(icon.getSelectIcon())
					.text(icon.getNavigateName())
					.pagePath(icon.getPagePath())
					.special(icon.getSpecial())
					.build();
		}).collect(Collectors.toList()));
		return R.ok(nv);
	}
}
