package com.example.shan.merchant.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/12 0012.
 */

public class Activity implements Serializable{
    private int activityId;
    private String activityName;
    private String activityContent;//活动内容
    private String activityStartTime;//活动开始时间
    private String activityEndTime;//活动结束时间

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public String getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(String activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime;
    }
}
