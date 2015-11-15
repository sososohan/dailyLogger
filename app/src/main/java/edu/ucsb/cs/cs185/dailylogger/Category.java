package edu.ucsb.cs.cs185.dailylogger;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kevin on 6/2/2015.
 */
public class Category implements Parcelable, Serializable{
    // parcel keys
    private static final String KEY_NAME = "name";
    private static final String KEY_COLOR = "age";

//    private String name;
//    private int color;
    String name;
    int totalTime;
    HashMap<String, ActivityData> activities;

    public Category(String name) {
        this.name = name;
        this.totalTime = 0;
        this.activities = new HashMap<String, ActivityData>();
    }

//    public Category(String name, int color){
//        this.label = name;
//        this.color = color;
//    }
    public Category(Category c){
        this.name = c.name;
//        this.color = c.getColor();
    }
//    public static String getKeyName() {return KEY_NAME;}
//    public static String getKeyColor() {return KEY_COLOR;}
//    public String getName() {return name;}
//    public void setName(String name) {this.name = name;}
//    public int getColor() {return color;}
//    public void setColor(int color) {this.color = color;}

    // Generates default categories
//    public static HashMap<String, Category> defaultCategories(){
//        HashMap<String, Category> map = new HashMap<>();
//
//        map.put("Work", new Category("Work", Color.YELLOW));
//        map.put("Sleep/Rest", new Category("Sleep/Rest", Color.BLUE));
//        map.put("Personal", new Category("Personal", Color.GREEN));
//        map.put("On The Move", new Category("On The Move", Color.RED));
//        return map;
//    }

    public static String[] namesListToArray(List<Category> categories){
        List<String> names = new ArrayList<String>();
        for (Category c : categories) {
            names.add(c.name);
        }
        return names.toArray(new String[names.size()]);
    }


    /* Necessary Parcelable Methods */
    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.name);
//        dest.writeInt(this.color);
    }

    public Category(Parcel parcel){
        this.name = parcel.readString();
//        this.color = parcel.readInt();
    }


    /**
     * Creator required for class implementing the parcelable interface.
     */
    public static final Creator<Category> CREATOR =
            new Creator<Category>() {
                @Override
                public Category createFromParcel(Parcel source) {
                    return new Category(source);
                }

                @Override
                public Category[] newArray(int size) {
                    return new Category[size];
                }

            };

}
