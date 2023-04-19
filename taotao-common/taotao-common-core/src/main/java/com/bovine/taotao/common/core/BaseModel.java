package com.bovine.taotao.common.core;

import java.io.Serializable;

/**
 * 基础查询条件参数(映射前端form表单提交)
 * 定义分页、排序条件
 * 所有请求分页条件对象都需要集成该类
 * @author eden
 * @date 2022/10/22 22:43:43
 */
public class BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
     * 分页页码
     */
    protected Integer pageNum;

    /**
     * 分页页量
     */
    protected Integer pageSize;

    /**
     * 排序 ASC = true DESC = false
     */
    protected Boolean order;

    /**
     * 需要排序字段
     */
    protected String orderField;

    public BaseModel(){
        super();
    }

    public BaseModel(Integer pageNum,Integer pageSize){
        this();
        this.pageNum=pageNum;
        this.pageSize=pageSize;
    }

	public Integer getPageNum() {
        return pageNum == null ? 1 : pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize == null ? 20 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getOrder() {
        return order == null ? true : order;
    }

    public void setOrder(Boolean order) {
        this.order = order;
    }

    public String getOrderField() {
        return orderField == null || orderField.trim().length() == 0 ? "created" : orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }
}
