package edu.ucsb.cs.cs185.dailylogger;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Khalid on 6/6/2015.
 */
public class ActivityData implements Serializable{

    String label;
    ArrayList<TimeContainer> times;
    int minutes;
    Category category;

    public ActivityData(String label, TimeContainer time) {
        this.label = label;
        this.times = new ArrayList<TimeContainer>();
        times.add(time);
        this.minutes = time.getMinutes();
    }

    public ActivityData(){
    }

}









//package edu.ucsb.cs.cs185.dailylogger;


//import android.text.format.Time;
//import android.util.Log;
//
//import java.util.ArrayList;

/**
 * Created by Kevin on 5/31/2015.
 */
//public class ActivityData {
//    static int counter = 0;
//    private String label;
//    private Category category;
//    private ArrayList<TimeContainer> times;
//
//    public ArrayList<TimeContainer> getTimes() {return times;}
//    public void setTimes(ArrayList<TimeContainer> times) {this.times = times;}
//    public String getLabel() { return label;}
//    public void setLabel(String label) {this.label = label;}
//    public Category getCategory() {return category;}
//    public void setCategory(Category category) {this .category = category;}
//
//    public ActivityData(String label, Category category, TimeX timeStart, TimeX timeEnd){
//        this.label = label;
//        this.category = category;
//    }

//    public ActivityData(ActivityData a){
//        label = a.getLabel();
//        category = new Category(a.getCategory());
//        ArrayList<TimeContainer> cList = new ArrayList<>();
//        for( TimeContainer con : cList ){
//            TimeX start = con.start;
//            TimeX end = con.end;
//            TimeContainer tmp = new TimeContainer(new TimeX(start), new TimeX(end));
//            cList.add(tmp);
//        }
//        times = cList;
//    }

//    public ActivityData(){
//    }
//
//}
