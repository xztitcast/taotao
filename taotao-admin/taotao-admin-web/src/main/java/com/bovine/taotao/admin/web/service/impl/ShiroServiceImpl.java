package com.bovine.taotao.admin.web.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bovine.taotao.admin.web.entity.SysMenu;
import com.bovine.taotao.admin.web.entity.SysUser;
import com.bovine.taotao.admin.web.mapper.SysMenuMapper;
import com.bovine.taotao.admin.web.mapper.SysRoleMapper;
import com.bovine.taotao.admin.web.service.ShiroService;
import com.bovine.taotao.common.core.Constant;
import com.bovine.taotao.common.core.Constant.RedisKey;

/**
 * shiro业务接口实现类
 * 使用redis缓存存储用户信息
 * @author eden
 * @date 2018年7月23日 上午8:39:01
 */
@Service("shiroService")
public class ShiroServiceImpl implements ShiroService {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public String createToken(SysUser sysUser) {
		String token = UUID.randomUUID().toString().replace("-", "");
		redisTemplate.opsForValue().set(RedisKey.SYS_SESSION_ID_STR_KEY + token, JSON.toJSONString(sysUser), 12, TimeUnit.HOURS);
		return token;
	}

	@Override
	public SysUser getByToken(String token) {
		String value = redisTemplate.opsForValue().get(RedisKey.SYS_SESSION_ID_STR_KEY + token);
		return JSON.parseObject(value, SysUser.class);
	}

	@Override
	public boolean remove(String token) {
		return redisTemplate.delete(RedisKey.SYS_SESSION_ID_STR_KEY + token);
	}
	
	@Override
    public Set<String> getUserPermissions(long userId) {
		List<String> permsList;
		if(userId == Constant.Sys.SUPER_ADMIN) {
			List<SysMenu> list = sysMenuMapper.selectList(new QueryWrapper<>());
			permsList = list.stream().map(m -> m.getPerms()).collect(Collectors.toList());
		}else {
			permsList = sysRoleMapper.selectAllPerms(userId);
		}
		return permsList.stream().filter(p -> StringUtils.isNotBlank(p)).map(p -> Arrays.asList(p.trim().split(","))).collect(HashSet::new, Set::addAll, Set::addAll);
    }

}
