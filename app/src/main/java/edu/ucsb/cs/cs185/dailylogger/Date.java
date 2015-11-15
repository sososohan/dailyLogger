package edu.ucsb.cs.cs185.dailylogger;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Kevin on 6/6/2015.
 */
public class Date {

    public ArrayList<Category> categories;
    int totalTime;

    public Date(){
    }

    public Date(ArrayList<Category> aList){
        this.categories = aList;
    }


}
