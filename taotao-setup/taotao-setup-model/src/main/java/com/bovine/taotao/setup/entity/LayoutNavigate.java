package com.bovine.taotao.setup.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 小程序导航按钮实体类
 * @author eden
 * @date 2023年2月10日 下午2:17:59
 */
@Getter
@Setter
@TableName(value = "tb_layout_navigate")
public class LayoutNavigate extends BaseEntity<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     *导航名
     */
    private String navigateName;
    /**
     * 导航选中图片
     */
    private String selectIcon;

    /**
     * 导航未选中默认图片
     */
    private String defaultIcon;

    /**
     * 页面路径
     */
    private String pagePath;

    /**
     * 是否特殊:1:非特殊 2:特殊(扫一扫)
     */
    private Integer special;

    /**
     * 页面参数
     */
    private String pageParameters;
    
    /**
     * 升序规则
     */
    private Integer sorted;
    
    /**
     * 布局ID(关联tb_layout表id)
     */
    private Integer layoutId;

}
