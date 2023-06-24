package com.bovine.taotao.admin.web.controller;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.Valid;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovine.taotao.admin.web.annotation.Log;
import com.bovine.taotao.admin.web.entity.SysUser;
import com.bovine.taotao.admin.web.model.PasswordModel;
import com.bovine.taotao.admin.web.model.UserModel;
import com.bovine.taotao.admin.web.service.SysUserRoleService;
import com.bovine.taotao.admin.web.service.SysUserService;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.R;
import com.bovine.taotao.common.core.S;
import static com.bovine.taotao.common.core.Constant.Sys.*;

/**
 * 管理系统用户控制器
 * @author eden
 * @time 2022年7月22日 下午12:16:09
 */
@Validated
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 获取当前登陆用户创建的所有用户列表
	 * @param form
	 * @return
	 */
	@GetMapping("/list")
	@PreAuthorize(value = "hasAuthority('sys:user:list')")
	public R list(UserModel form) {
		P<SysUser> list = sysUserService.getSysUserList(form, getUserId());
		return R.ok(list);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	@PreAuthorize(value = "hasAuthority('sys:user:info')")
	public R info(){
		return R.ok(getUser());
	}
	
	/**
	 * 获取单个用户信息
	 * @param userId
	 * @return
	 */
	@GetMapping("/info/{userId}")
	@PreAuthorize(value = "hasAuthority('sys:user:info')")
	public R info(@PathVariable("userId") Long userId) {
		SysUser user = sysUserService.getById(userId);
		if(user == null) {
			return R.error(S.USER_NOTFOUND_ERROR);
		}
		List<Long> roleIdList = sysUserRoleService.getRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		return R.ok(user);
	}
	
	@Log("保存用户数据")
	@PostMapping("/save")
	@PreAuthorize(value = "hasAuthority('sys:user:save')")
	public R save(@RequestBody SysUser user) {
		String salt = RandomStringUtils.randomAlphanumeric(13);
		String password = passwordEncoder.encode(user.getPassword().concat(salt));
		user.setPassword(password);
		user.setSalt(salt);
		user.setCreator(getUserId());
		sysUserService.saveOrUpdate(user);
		return R.ok();
	}
	
	@Log("修改用户数据")
	@PostMapping("/update")
	@PreAuthorize(value = "hasAuthority('sys:user:update')")
	public R update(@RequestBody SysUser user) throws Exception{
		user.setCreator(getUserId());
		sysUserService.saveOrUpdate(user);
		return R.ok();
	}

	@Log("修改用户密码")
	@PutMapping ("/password")
	@PreAuthorize(value = "hasAuthority('sys:user:update')")
	public R password(@Valid @RequestBody PasswordModel pm){
		SysUser sysUser = sysUserService.getById(getUserId());
		if(sysUser == null) {
			return R.error(S.USER_NOTFOUND_ERROR);
		}
		String password = passwordEncoder.encode(pm.getPassword().concat(sysUser.getSalt()));
		if(!sysUser.getPassword().equals(password)) {
			return R.error(S.USER_ORIGINAL_PWD_ERROR);
		}
		String salt = RandomStringUtils.randomAlphanumeric(13);
		password = passwordEncoder.encode(pm.getNewPassword().concat(salt));
		sysUser.setPassword(password);
		sysUser.setSalt(salt);
		sysUserService.updateById(sysUser);
		return R.ok();
	}

	@Log("修改用户账户状态")
	@PostMapping("/change")
	@PreAuthorize(value = "hasAuthority('sys:user:update')")
	public R change(@RequestBody SysUser em){
		SysUser sysUser = sysUserService.getById(em.getId());
		if(sysUser == null) {
			return R.error(S.USER_NOTFOUND_ERROR);
		}
		if (getUserId() != SUPER_ADMIN) {
			List<Long> nodeUserIdList = sysUserService.getNodeUserIdList(getUserId());
			if (!nodeUserIdList.contains(em.getId())){
				return R.error(S.USER_NOTPERMISSION_ERROR);
			}
		}
		if(sysUser.getStatus() == em.getStatus().intValue()) {
			return R.error(S.USER_STATUS_PARAMTER_ERROR);
		}
		sysUserService.updateById(em);
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@Log("删除用户数据")
	@DeleteMapping("/delete")
	@PreAuthorize(value = "hasAuthority('sys:user:delete')")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, SUPER_ADMIN)){
			return R.error(S.USER_REMOVE_SUPER_ADMIN_ERROR);
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error(S.USER_CURRENT_REMOVE_ERROR);
		}
		
		sysUserService.removeByIds(Arrays.asList(userIds));
		
		return R.ok();
	}
	
	/**
	 * 获取用户Tree数据
	 * @return
	 */
	@GetMapping("/tree")
	@PreAuthorize(value = "hasAuthority('sys:user:list')")
	public R tree() {
		List<SysUser> list = sysUserService.getSysUserList(getUserId());
		SysUser root = new SysUser();
		root.setId(0L);
		root.setParentId(-1);
		root.setUsername("请关联用户");
		root.setStatus(0);
		root.setCreator(0L);
		list.add(root);
		return R.ok(list);
	}
	
}
