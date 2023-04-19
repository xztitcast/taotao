package com.bovine.taotao.admin.web.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.admin.web.entity.SysRole;
import com.bovine.taotao.admin.web.mapper.SysRoleMapper;
import com.bovine.taotao.admin.web.model.RoleModel;
import com.bovine.taotao.admin.web.service.SysRoleMenuService;
import com.bovine.taotao.admin.web.service.SysRoleService;
import com.bovine.taotao.admin.web.service.SysUserRoleService;
import com.bovine.taotao.common.core.Constant;
import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.S;
import com.bovine.taotao.common.core.exception.custom.SysServiceException;

/**
 * 系统角色业务接口实现类
 * @author eden
 * @date 2018年10月23日 上午9:33:55
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
	
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	@Transactional
	public boolean save(SysRole entity) {
		boolean save = super.save(entity);
		if(save) {
			//检查权限
			checkPrems(entity);
			sysRoleMenuService.saveOrUpdate(entity.getId(), entity.getMenuIdList());
		}
		return save;
	}

	@Override
	@Transactional
	public boolean updateById(SysRole entity) {
		boolean update = super.updateById(entity);
		if(update) {
			//检查权限
			checkPrems(entity);
			sysRoleMenuService.saveOrUpdate(entity.getId(), entity.getMenuIdList());
		}
		return update;
	}

	@Override
	@Transactional
	public void deleteBatch(Long... roleIds) {
		this.removeByIds(Arrays.asList(roleIds));
		sysRoleMenuService.deleteBatch(roleIds);
		sysUserRoleService.deleteBatch(roleIds);
	}

	@Override
	public List<Long> getRoleIdList(Long creator) {
		List<SysRole> list = this.listByMap(Map.of("creator", creator));
		return list.stream().map(SysRole::getId).collect(Collectors.toList());
	}

	@Override
	public P<SysRole> getSysRoleList(RoleModel rm, long creator) {
		IPage<SysRole> page = new Page<>(rm.getPageNum(), rm.getPageSize());
		QueryWrapper<SysRole> warpper = new QueryWrapper<>();
		warpper.eq(StringUtils.isNotBlank(rm.getName()), "name", rm.getName())
				.eq(creator != Constant.Sys.SUPER_ADMIN, "creator", creator)
				.orderBy(true, rm.getOrder(), rm.getOrderField());
		page(page, warpper);
		return new P<>(page.getTotal(), page.getRecords());
	}
	
	private void checkPrems(SysRole role) {
		if(role.getCreator() == Constant.Sys.SUPER_ADMIN) {
			return;
		}
		List<Long> menuIdList = this.baseMapper.selectAllMenuId(role.getCreator());
		if(!menuIdList.containsAll(role.getMenuIdList())) {
			throw new SysServiceException(S.USER_ADDROLE_NOTPERMISSIONS_ERROR);
		}
	}
}
