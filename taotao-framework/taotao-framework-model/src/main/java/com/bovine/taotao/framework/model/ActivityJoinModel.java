package com.bovine.taotao.framework.model;

import com.bovine.taotao.common.core.ModelAndView;
import lombok.Getter;
import lombok.Setter;

/**
 * 参与活动入参数据
 * @author eden
 * @date 2023/9/24 10:46:46
 */
@Getter
@Setter
public class ActivityJoinModel extends ModelAndView<Long> {

    /**
     * 省名称
     */
    private String pname;

    /**
     * 城市名称
     */
    private String cname;

    /**
     * 地区名称
     */
    private String areaname;
}
