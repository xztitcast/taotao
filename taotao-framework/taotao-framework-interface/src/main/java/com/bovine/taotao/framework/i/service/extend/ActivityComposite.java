package com.bovine.taotao.framework.i.service.extend;

/**
 * 活动组合接口
 * 组合模式(模拟拦截器思想)
 * @author eden
 * @date 2023/9/23 22:10:10
 */
public interface ActivityComposite {

    /**
     * 添加节点
     * @param activityComposite
     */
    default void add(ActivityComposite activityComposite){}

    /**
     * 删除节点
     * @param activityComposite
     * @return
     */
    default boolean remove(ActivityComposite activityComposite){return false;}

    /**
     * 获取子节点
     * @param index
     * @return
     */
    default ActivityComposite getChild(int index){return null;}

    /**
     * 执行组件
     * @param request
     * @param response
     * @return
     */
    boolean doProcess(ActivityCompositeRequest request, ActivityCompositeResponse response);
}
