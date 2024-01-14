package com.bovine.taotao.admin.web.controller.setup;

import com.bovine.taotao.admin.web.annotation.Fill;
import com.bovine.taotao.admin.web.annotation.FillType;
import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.S;
import com.bovine.taotao.setup.entity.Classroom;
import com.bovine.taotao.setup.i.service.ClassroomService;
import com.bovine.taotao.setup.model.SysClassroomModel;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * 后台管理系统 文章控制器
 * @author eden
 * @date 2023年2月22日 下午8:45:05
 */
@RestController
@RequestMapping("/sys/classroom")
public class SysClassroomController {

	@DubboReference
	private ClassroomService classroomService;

	/**
	 * 获取物业列表
	 * @param am
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize("hasAuthority('sys:classroom:list')")
	public R list(SysClassroomModel am) {
		P<Classroom> p = classroomService.getBaseList(am);
		return R.ok(p);
	}
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	@GetMapping("/info/{id}")
	@PreAuthorize("hasAuthority('sys:classroom:info')")
	public R info(@PathVariable Long id) {
		Classroom entity = classroomService.getEntity(id);
		return R.ok(entity);
	}
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@Log("保存数币课堂数据")
	@Fill(FillType.INSERT)
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('sys:classroom:save')")
	public R save(@RequestBody Classroom entity) {
		entity = classroomService.saveEntity(entity);
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
	@Log("修改数币课堂数据")
	@Fill(FillType.UPDATE)
	@PostMapping("/update")
	@PreAuthorize("hasAuthority('sys:classroom:update')")
	public R update(@RequestBody Classroom entity) {
		boolean update = classroomService.updateEntity(entity);
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
	@Log("删除数币课堂数据")
	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('sys:classroom:delete')")
	public R delete(@RequestBody Long[] ids) {
		boolean delete = classroomService.delete(Arrays.asList(ids));
		if(delete) {
			return R.ok();
		}
		return R.error();
	}
	
	@Log("数币课堂置顶或取消置顶")
	@Fill(FillType.UPDATE)
	@PostMapping("/top")
	@PreAuthorize("hasAuthority('sys:classroom:top')")
	public R top(@RequestBody Classroom entity) {
		Classroom article = classroomService.getEntity(entity.getId());
		if(article == null) {
			return R.error(S.DATA_NOTFOUND_ERROR);
		}
		int top = entity.getTop();
		if(top < 0 && top > 2) {
			return R.error(S.PARAMTER_BOUNDS_ERROR);
		}
		if(article.getTop() == top) {
			return R.error(S.DATA_STATUS_ERROR);
		}
		entity.setTop(top);
		boolean update = classroomService.updateEntity(entity);
		if(!update) {
			return R.error();
		}
		return R.ok();
	}
	
	/**
	 * 更改状态
	 * @param entity
	 * @return
	 */
	@Log("数币课堂上架或者下架")
	@Fill(FillType.UPDATE)
	@PostMapping("/change")
	@PreAuthorize("hasAuthority('sys:classroom:change')")
	public R change(@RequestBody Classroom entity) {
		Classroom article = classroomService.getEntity(entity.getId());
		if(article == null) {
			return R.error(S.DATA_NOTFOUND_ERROR);
		}
		int status = entity.getStatus();
		if(status < 1 && status > 4) {
			return R.error(S.PARAMTER_BOUNDS_ERROR);
		}
		if(article.getStatus() == status) {
			return R.error(S.DATA_STATUS_ERROR);
		}
		if(status == 2) {
			entity.setPushTime(new Date());
		}
		entity.setStatus(status);
		boolean update = classroomService.updateEntity(entity);
		if(!update) {
			return R.error();
		}
		return R.ok();
	}
}
