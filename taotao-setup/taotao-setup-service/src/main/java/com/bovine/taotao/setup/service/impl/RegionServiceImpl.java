package com.bovine.taotao.setup.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.setup.entity.Region;
import com.bovine.taotao.setup.i.service.RegionService;
import com.bovine.taotao.setup.mapper.RegionMapper;

/**
 * 全国行政区Service业务逻辑实现类
 * @author eden
 * @date 2023年2月18日 下午2:34:07
 */
@Service("regionService")
@DubboService(interfaceClass = RegionService.class)
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IService<Region>, RegionService {
	
	@Override
	public List<Region> getRegionTreeList() {
		LambdaQueryWrapper<Region> query = Wrappers.lambdaQuery(Region.class).eq(Region::getStatus, 1);
		return this.list(query);
	}

	@Override
	public Region getRegionIds(String pname, String cname, String areaname) {
		return this.baseMapper.selectRegionByNames(Map.of("pname", pname, "cname", cname, "areaname", areaname));
	}

	@Override
	public Region getRegionNames(String pid, String cid, String areaId) {
		return this.baseMapper.selectRegionByIds(Map.of("pid", pid, "cid", cid, "areaId", areaId));
	}

	@Override
	public Region getRegionByCnameAndPname(String cname, String pname) {
		LambdaQueryWrapper<Region>  query = Wrappers.lambdaQuery(Region.class)
							.eq(StringUtils.isNotEmpty(cname),Region::getCname,cname)
							.eq(StringUtils.isNotEmpty(pname),Region::getPname,pname);
		return this.getOne(query);
	}


}
