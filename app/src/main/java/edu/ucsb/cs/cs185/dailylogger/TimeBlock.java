package edu.ucsb.cs.cs185.dailylogger;

/**
 * Created by Kevin on 6/2/2015.
 */
public class TimeBlock {
    private TimeX timeX;
    private ActivityData activityData;

    public TimeBlock(TimeX timeX, ActivityData activityData){
        this.timeX = timeX;
        this.activityData = activityData;
    }

    public TimeBlock(TimeX timeX){
        this.timeX = timeX;
        this.activityData = null;
    }

    public TimeX getTimeX() {return timeX;}
    public void setTimeX(TimeX timeX) {this.timeX = timeX;}
    public ActivityData getActivityData() {return activityData;}
    public void setActivityData(ActivityData activityData) {this.activityData = activityData;}


}
