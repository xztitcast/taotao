package com.bovine.taotao.setup.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.utils.BeanCopierUtil;
import com.bovine.taotao.setup.entity.Classroom;
import com.bovine.taotao.setup.i.service.ClassroomCategoryService;
import com.bovine.taotao.setup.i.service.ClassroomService;
import com.bovine.taotao.setup.model.ArticleAndClassroomModel;
import com.bovine.taotao.setup.view.CategoryView;
import com.bovine.taotao.setup.view.ClassroomContentView;
import com.bovine.taotao.setup.view.ClassroomView;

/**
 * 前端交互 数币课堂前端控制器
 * @author eden
 * @date 2023年2月23日 下午9:18:59
 */
@RestController
@RequestMapping("/setup/classroom")
public class ClassroomController {

	@DubboReference
	private ClassroomService classroomService;
	
	@DubboReference
	private ClassroomCategoryService classroomCategoryService;

	/**
	 * 获取课堂列表
	 * @param am
	 * @return
	 */
	@PostMapping("/list")
	public R list(@RequestBody ArticleAndClassroomModel aacm) {
		P<ClassroomView> p = classroomService.getClassroomList(aacm);
		return R.ok(p);
	}
	
	/**
	 * 获取文章内容
	 * @param id
	 * @return
	 */
	@GetMapping("/content/{id}")
	public R cotent(@PathVariable Long id) {
		Classroom entity = classroomService.getEntity(id);
		ClassroomContentView ccv = new ClassroomContentView();
		if(entity != null) {
			BeanCopierUtil.copyProperties(entity, ccv);
		}
		return R.ok(ccv);
	}
	
	/**
	 * 获取文章分类
	 * @return
	 */
	@GetMapping("/category")
	public R category() {
		List<CategoryView> list = classroomCategoryService.getArticleCategoryList().stream().map(a -> new CategoryView(a.getId(), a.getName())).collect(Collectors.toList());
		return R.ok(list);
	}
}
