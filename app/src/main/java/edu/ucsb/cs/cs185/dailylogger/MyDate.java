package edu.ucsb.cs.cs185.dailylogger;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Khalid on 6/6/2015.
 */
public class MyDate implements Serializable {

    Date date;
    SimpleDateFormat dateFormat;
    HashMap<String, Category> categories;
    int totalTime;

    public MyDate(Date date) {
        this.date = date;
        this.categories = new HashMap<String, Category>();
        this.dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        this.totalTime = 0;
    }

}
