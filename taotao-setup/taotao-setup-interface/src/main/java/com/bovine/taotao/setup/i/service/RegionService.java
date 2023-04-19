package com.bovine.taotao.setup.i.service;

import java.util.List;

import com.bovine.taotao.setup.entity.Region;

/**
 * 全国行政区Service业务接口
 * @author eden
 * @date 2023年2月18日 下午2:33:53
 */
public interface RegionService {
	
	/**
	 * 获取行政区树
	 * @return
	 */
	List<Region> getRegionTreeList();
	
	
	/**
	 * 获取行政区
	 * @param pname 省
	 * @param cname 市
	 * @param areaname 区
	 * @return
	 */
	Region getRegionIds(String pname, String cname, String areaname);
	
	/**
	 * 获取行政区
	 * @param pid 省
	 * @param cid 市
	 * @param areaId 区
	 * @return
	 */
	Region getRegionNames(String pid, String cid, String areaId);

	/**
	 * 根据行政区名称和父级行政区名称 获取行政区
	 * @param cname 行政区名称
	 * @param pname 父级行政区名称
	 * @return
	 */
	Region getRegionByCnameAndPname(String cname,String pname);
}
