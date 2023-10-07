package com.bovine.taotao.framework.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bovine.taotao.common.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 机构信息实体类
 * @author eden
 * @date 2023年7月1日 下午10:37:35
 */
@Getter
@Setter
public class Tissue extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

	/**
	 * 机构全称
	 */
    private String name;

    /**
     * 机构编号
     */
    private String serials;

    /**
     * 机构简称
     */
    private String des;

    /**
     * logo图片
     */
	private String logos;

	/**
	 * 机构服务电话(类似400)
	 */
    private String phones;
    
    /**
     * 负责人姓名
     */
    private String tname;
    
    /**
     * 负责人电话
     */
    private String tphone;
    
    /**
     * 省编码
     */
    private String pid;
    
    /**
     * 省名称
     */
    private String pname;
    
    /**
     * 市编码
     */
    private String cid;
    
    /**
     * 市名称
     */
    private String cname;
    
    /**
     * 区编码
     */
    private String areaId;
    
    /**
     * 区名称
     */
    private String areaname;
    
    /**
     * 机构详细地址
     */
    private String address;
    
    /**
     * 签约时间
     */
    private Date signtime;
    
    /**
     * 到期时间
     */
    private Date expiretime;
    
    @TableField(exist = false)
    private List<Long> userIdList;
}
