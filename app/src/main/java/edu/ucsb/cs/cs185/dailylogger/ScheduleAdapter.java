package edu.ucsb.cs.cs185.dailylogger;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Kevin on 5/31/2015.
 */
public class ScheduleAdapter extends ArrayAdapter<TimeBlock> {
    public String DEBUG_TAG = "DEBUG";
    private final Context context;
    private final TimeBlock[] timeBlocks;
    private final float HEIGHT_MIN = 60;
    private final float HEIGHT_MAX = 100;
    private int height;
    //    private ArrayList<ActivityData> activityList;
    public HashMap<String, ActivityData> activityMap;

    public ScheduleAdapter(Context context) {
        super(context, -1);
        this.context = context;
        this.timeBlocks = generateTimeBlocks();
        this.height = 80;
        // sets times to the arrayadapter
        this.addAll(timeBlocks);
    }

    public void scale(float scale){
        setHeight( (int)(60*scale) );
        notifyDataSetChanged();
    }

    public void updateActivityList(HashMap<String, ActivityData> activityMap) {
        // Updates the activity list to be displayed
        ArrayList<ActivityData> activityList = new ArrayList<>(activityMap.values());
        for(TimeBlock tm : timeBlocks){
            tm.setActivityData(null);
        }

//        this.activityList = activityList;
        this.activityMap = activityMap;

        for (ActivityData activityData : activityList) {
            for (TimeContainer container : activityData.times) {
                TimeX start = container.start;
                TimeX end = container.end;
//                Log.d(DEBUG_TAG, "" + start.toString() + ", " + end.toString());
                for (TimeX iter = new TimeX(start); !iter.equals(end); iter.nextFifteenMin()) {
                    timeBlocks[iter.toIndex()].setActivityData(activityData);
                }
            }
        }
    }

//    public ArrayList<ActivityData> getActivityList() {return activityList;}
//
//    public void updateActivityList(ArrayList<ActivityData> activityList) {
//        // Updates the activity list to be displayed
//
//        for(TimeBlock tm : timeBlocks){
//            tm.setActivityData(null);
//        }
//
//        this.activityList = activityList;
//        for (ActivityData activityData : activityList) {
//            for (TimeContainer container : activityData.getTimes()) {
//                TimeX start = container.start;
//                TimeX end = container.end;
////                Log.d(DEBUG_TAG, "" + start.toString() + ", " + end.toString());
//                for (TimeX iter = new TimeX(start); !iter.equals(end); iter.nextFifteenMin()) {
//                    timeBlocks[iter.toIndex()].setActivityData(activityData);
//                }
//            }
//        }
//    }

    public void setHeight(int height){
        this.height = Math.max(40, Math.min(height, 200));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView timeTextView = (TextView) rowView.findViewById(R.id.timeX);
        TextView meridianTextView = (TextView) rowView.findViewById(R.id.meridian);

        TimeBlock timeBlock = timeBlocks[position];
        TimeX timeX = timeBlock.getTimeX();
        // Sets the time
        timeTextView.setText(timeX.timeString());
        meridianTextView.setText(timeX.meridianString());

        // adjusts the height
        ViewGroup.LayoutParams params = rowView.getLayoutParams();
        params.height = this.height;
        rowView.setLayoutParams(params);
        rowView.requestLayout();

        ActivityData activityData = timeBlock.getActivityData();

        TextView labelView = (TextView) rowView.findViewById(R.id.activity_label);
        TextView categoryView = (TextView) rowView.findViewById(R.id.category_label);
        LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.activity_layout);

        if(activityData != null){
            Category category = activityData.category;
            // goes through each timecontainer and sets the views
            for(TimeContainer container : activityData.times) {
                TimeX start = container.start;
                TimeX end = container.end;

                if (timeX.between(start, end)) {
                    if (start.equals(timeX)) {
                        // If start time, then set label
                        labelView.setText(activityData.label);
                        float scale = this.height / (float) 6;

                        if(this.height > 123){
                            linearLayout.setOrientation(LinearLayout.VERTICAL);
                        }
                        Log.d(DEBUG_TAG, "hieght: " + this.height);
                        Log.d(DEBUG_TAG, "hieght: " + scale);
                        labelView.setTextSize(Math.max(10, Math.min(20,scale * 20 / 13)));
                        // Set category name
                        categoryView.setText(category.name);
                        categoryView.setTextSize((float) Math.max(10, Math.min(12,scale*0.83)));
                    }
                    // Set color
                    linearLayout.setBackgroundColor(Color.parseColor(MainActivity.getColor(category.name)));

                }
            }
        }
        else{
            labelView.setText("");
            categoryView.setText("");
            linearLayout.setBackgroundColor(0x00000000);

        }

        return rowView;
    }

    static private TimeBlock[] generateTimeBlocks(){
        // Helper function: Generates time for the Schedule List
        List<TimeBlock> timeIntervals = new ArrayList<TimeBlock>();
        timeIntervals.add(new TimeBlock(new TimeX(12, 0, TimeX.Meridian.AM)));
        timeIntervals.add(new TimeBlock(new TimeX(12, 15, TimeX.Meridian.AM)));
        timeIntervals.add(new TimeBlock(new TimeX(12, 30, TimeX.Meridian.AM)));
        timeIntervals.add(new TimeBlock(new TimeX(12, 45, TimeX.Meridian.AM)));

        for(int i = 1; i < 12; i++){
            for(int j = 0; j < 4; j++){
                TimeBlock t = new TimeBlock(new TimeX(i, j*15, TimeX.Meridian.AM));
                timeIntervals.add(t);
            }
        }
        timeIntervals.add(new TimeBlock(new TimeX(12, 0, TimeX.Meridian.PM)));
        timeIntervals.add(new TimeBlock(new TimeX(12, 15, TimeX.Meridian.PM)));
        timeIntervals.add(new TimeBlock(new TimeX(12, 30, TimeX.Meridian.PM)));
        timeIntervals.add(new TimeBlock(new TimeX(12, 45, TimeX.Meridian.PM)));

        for(int i = 1; i < 12; i++){
            for(int j = 0; j < 4; j++){
                TimeBlock t = new TimeBlock(new TimeX(i, j*15, TimeX.Meridian.PM));
                timeIntervals.add(t);
            }
        }
        return timeIntervals.toArray(new TimeBlock[timeIntervals.size()]);
    }

}