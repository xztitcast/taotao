package com.bovine.taotao.framework.service.extend;

import com.bovine.taotao.framework.i.service.extend.ActivityComposite;
import com.bovine.taotao.framework.i.service.extend.ActivityCompositeRequest;
import com.bovine.taotao.framework.i.service.extend.ActivityCompositeResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合管理器
 * @author eden
 * @date 2023/9/23 22:14:14
 */
public class ActivityManagerComposite implements ActivityComposite {

    private String name;

    private Integer level;

    public ActivityManagerComposite(String name, Integer level){
        this.name = name;
        this.level = level;
    }

    private List<ActivityComposite> activityCompositeList = new ArrayList<>();

    @Override
    public void add(ActivityComposite activityComposite) {
        this.activityCompositeList.add(activityComposite);
    }

    @Override
    public boolean remove(ActivityComposite activityComposite) {
        return this.activityCompositeList.remove(activityComposite);
    }

    @Override
    public ActivityComposite getChild(int index) {
        return this.activityCompositeList.get(index);
    }

    @Override
    public boolean doProcess(ActivityCompositeRequest request, ActivityCompositeResponse response) {
        boolean done = true;
        for(int i = request.getSignal(); i < this.activityCompositeList.size(); i++) {
            ActivityComposite activityComposite = this.activityCompositeList.get(i);
            done = activityComposite.doProcess(request, response);
            if(!done) break;
        }
        return done;
    }
}
