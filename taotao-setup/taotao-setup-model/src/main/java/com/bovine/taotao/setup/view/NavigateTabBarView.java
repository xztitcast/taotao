package com.bovine.taotao.setup.view;

import java.io.Serializable;
import java.util.List;

import com.bovine.taotao.common.core.serialize.IntegerIdKeySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 小程序tab bar视图交互数据实体类
 * @author eden
 * @date 2023年2月14日 下午2:10:52
 */
@Getter
@Setter
public class NavigateTabBarView implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 文字颜色
	 */
	private String color;
    
	/**
	 * 文字选中颜色
	 */
    private String selectedColor;
    
    /**
     * 背景颜色
     */
    private String backgroundColor;
    
    /**
     * 边框颜色
     */
    private String borderStyle;

    private List<TabBarList> list;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TabBarList {
    	
    	/**
    	 * 导航ID
    	 */
        @JsonSerialize(using = IntegerIdKeySerializer.class)
    	private Integer id;
        
    	/**
    	 * 导航页面路径
    	 */
        private String pagePath;
        
        /**
         * 导航名称
         */
        private String text;
        
        /**
         * icon默认图片路径
         */
        private String iconPath;
        
        /**
         * icon选中图片路径
         */
        private String selectedIconPath;
        
        /**
         * 是否特殊:1:非特殊 2:特殊(扫一扫)
         */
        private Integer special;
    }
}
