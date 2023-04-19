package com.bovine.taotao.setup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bovine.taotao.setup.entity.AdContent;
import com.bovine.taotao.setup.model.AdContentModel;

/**
 * 广告内容Mapper接口
 * @author eden
 * @date 2023年2月11日 下午4:23:48
 */
public interface AdContentMapper extends BaseMapper<AdContent> {

	/**
	 * 查询广告内容列表
	 * @return
	 */
	List<AdContent> selectAdContentList(@Param("model") AdContentModel model);

}
