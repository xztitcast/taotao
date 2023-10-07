package com.bovine.taotao.setup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bovine.taotao.common.mybatis.entity.CreateEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 小程序导航全局配置实体类
 * @author eden
 * @date 2023年2月10日 下午2:17:42
 */
@Setter
@Getter
@TableName(value = "tb_layout")
public class Layout extends CreateEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * tab 上的文字默认颜色,仅支持十六进制颜色
     */
    private String color;

    /**
     * tab 上的文字选中时的颜色,仅支持十六进制颜色
     */
    private String selectedColor;

    /**
     * tab 的背景色,仅支持十六进制颜色
     */
    private String backgroundColor;

    /**
     * tabbar 上边框的颜色, 仅支持 black / white
     * 从系统迁移过来目前发现该字段没有使用
     */
    private String borderStyle = "black";
    
    /**
     * 类型1:小程序 2:安卓 3:IOS
     */
    private Integer type;
    
    /**
     * 状态0:无效 1:有效
     */
    private Integer status;

}
