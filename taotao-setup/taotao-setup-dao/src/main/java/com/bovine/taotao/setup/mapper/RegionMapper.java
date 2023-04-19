package com.bovine.taotao.setup.mapper;

import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bovine.taotao.setup.entity.Region;

/**
 * 全国行政区持久化Mapper接口
 * @author eden
 * @date 2023年2月18日 下午2:32:16
 */
public interface RegionMapper extends BaseMapper<Region> {

	/**
	 * 关联查询行政区
	 * @param paramMap (省、市、区)名称
	 * @return
	 */
	Region selectRegionByNames(Map<String, Object> paramMap);
	
	/**
	 * 关联查询行政区
	 * @param paramMap (省、市、区)ID
	 * @return
	 */
	Region selectRegionByIds(Map<String, Object> paramMap);
}
