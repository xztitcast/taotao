package com.bovine.taotao.admin.web.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bovine.taotao.admin.web.entity.SysDevice;
import com.bovine.taotao.admin.web.mapper.SysDeviceMapper;
import com.bovine.taotao.admin.web.modelAndView.model.DeviceModel;
import com.bovine.taotao.admin.web.service.SysDeviceService;
import com.bovine.taotao.common.core.P;

/**
 * 系统设备授权Service接口实现类
 * @author eden
 * @date 2023年2月22日 上午11:47:39
 */
@Service("sysDeviceService")
public class SysDeviceServiceImpl extends ServiceImpl<SysDeviceMapper, SysDevice> implements SysDeviceService {

	@Override
	public P<SysDevice> getSysDeviceList(DeviceModel dm) {
		IPage<SysDevice> page = new Page<>(dm.getPageNum(), dm.getPageSize());
		QueryWrapper<SysDevice> wrapper = new QueryWrapper<>();
		wrapper.eq(StringUtils.isNotBlank(dm.getName()), "name", dm.getName()).orderBy(true, dm.getOrder(), dm.getOrderField());
		page(page, wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}
	
}
