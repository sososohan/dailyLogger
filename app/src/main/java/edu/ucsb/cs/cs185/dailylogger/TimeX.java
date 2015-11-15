package edu.ucsb.cs.cs185.dailylogger;

import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 6/1/2015.
 */
public class TimeX implements Serializable{
    private int hour;
    private int minute;
    private Meridian meridian;

    public enum Meridian {
        AM, PM
    }

    public TimeX(int h, int m, Meridian meridian){
        setHour(h);
        setMinute(m);
        this.meridian = meridian;
    }

    public TimeX(TimeX x){
        setHour(x.getHour());
        setMinute(x.getMinute());
        this.meridian = x.getMeridian();
    }

    public TimeX(String time){
        time = time.replace(" ", "");
        int h = Integer.parseInt(time.substring(0, time.indexOf(":")));
        int m = TimeX.parseMinute(time);

        int indexLast = time.indexOf("M");
        String merid = time.substring(indexLast - 1, indexLast + 1);
        Meridian meridian = Meridian.AM;
        if( merid.equals("PM")){ meridian = Meridian.PM; }

        this.hour = h;
        this.minute = m;
        this.meridian = meridian;

    }

    public int getHour() {return hour;}
    public void setHour(int hour) {
        // Acceptable Hours between 1 - 12
        this.hour = Math.max(1, Math.min(12, hour));
    }

    public int getMinute() {return minute;}
    public void setMinute(int minute) {
        // Acceptable Minutes: 0, 15, 30, 45
        this.minute = Math.max(0, Math.min(((minute/15)*15), 60));
    }

    public Meridian getMeridian() {return meridian;}
    public void setMeridian(Meridian meridian) {this.meridian = meridian;}

    public int toIndex(){
        // converts time into the correct index in an array of times.
        // index 0 -> 0min, 1 -> 15min, 2 -> 30min...
        int index = (int) (this.hourTo24() * 4 + ( ((float)minute/60) * 4));

        return index;
    }

    public static TimeX indexToTime(int index) {
        int h = index / 4;
        int m = (index % 4) * 15;
        Meridian meridian = Meridian.PM;
        if(index < 48) meridian = Meridian.AM;
        if(h == 0) h = 12;
        if (h > 12){ h -= 12; }
        TimeX x = new TimeX(h, m, meridian);

        return x;
    }

    public String timeString(){
        String time = String.format("%d:%02d", hour, minute);
        return time;
    }

    public String meridianString(){
        return meridian.toString();
    }

    public Meridian switchMeridian(){
        if(meridian == Meridian.AM){ return Meridian.PM; }
        return Meridian.AM;
    }

    @Override
    public String toString(){
        return timeString() + meridianString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeX timeX = (TimeX) o;

        if (hour != timeX.hour) return false;
        if (minute != timeX.minute) return false;
        return meridian == timeX.meridian;
    }

    @Override
    public int hashCode() {
        int result = hour;
        result = 31 * result + minute;
        result = 31 * result + meridian.hashCode();
        return result;
    }

    public boolean between(TimeX a, TimeX b){
        //TODO: Make correct from 11:45 PM to 12:15 AM and forward
        boolean bool = false;
        int check = this.hourTo24() * 60 + this.minute;
        int from = a.hourTo24() * 60 + a.getMinute();
        int to = b.hourTo24() * 60 + b.getMinute();
        if(from <= check && check < to){
            bool = true;
        }
        return bool;
    }

    public int hourTo24(){
        int hour = this.getHour();
        if(hour == 12) {
            if (this.meridian == Meridian.AM) return 0;
            if (this.meridian == Meridian.PM ) return 12;
            }
        if(this.meridian == Meridian.PM){
            hour += 12;
        }
        return hour;
    }

    public static int hourTo24(int hour, String meridian){
        if(hour == 12) {
            if (meridian.equals("AM")) return 0;
            if (meridian.equals("PM")) return 12;
        }
        if(meridian.equals("PM")){
            System.out.print(" " + meridian + " ");
            hour += 12;
        }
        return hour;
    }

    public void nextFifteenMin(){
        minute += 15;
        if(hour == 11 && minute == 60){
            hour = 12;
            minute = 0;
            this.meridian = switchMeridian();
            return;
        }

        if(minute == 60) {
            hour++;
            minute = 0;
            if (hour > 12) {
                hour = 1;
            }
        }

    }

    public static TimeContainer createTimeContainer(int s1, int e1, Meridian m1, int s2, int e2, Meridian m2){
        TimeX from = new TimeX(s1, e1, m1);
        TimeX to = new TimeX(s2, e2, m2);
        return new TimeContainer(from, to);
    }


    public static int parseHour24(String time){
        int h = Integer.parseInt(time.substring(0, time.indexOf(":")));

        int indexLast = time.indexOf("M");
        String meridian = time.substring(indexLast - 1, indexLast + 1);
        return hourTo24(h, meridian);
    }

    public static int parseMinute(String time){
        int minute = Integer.parseInt(time.substring(time.indexOf(":")+1, time.indexOf("M") - 1));
        return minute;
    }
//    public static int parseMin

    public static void main(String args[]){

        TimeX x = new TimeX("8:20 AM");
        System.out.println(x.toString());

//        int h = 1;
//        Meridian merid = Meridian.AM;
//        for(int i = 0; i < 24; i++){
//            for(int k = 0; k < 4; k++){
//                TimeX x = new TimeX(h, k*15, merid);
//                String y = x.toString();
//                TimeX z = new TimeX(y);
//                System.out.println(y + " : " + z.toString());
//            }
//            h++;
//            if(h == 13){
//                h = 1;
//                merid = Meridian.PM;
//            }
//        }


//        TimeX.Meridian merid = TimeX.Meridian.AM;
//        for(int i = 0; i < 96; i++) {
//            indexToTime(i);
//        }

//        int h = 1;
//        Meridian merid = Meridian.AM;
//        for(int i = 0; i < 24; i++){
//            for(int k = 0; k < 4; k++){
//                TimeX x = new TimeX(h, k*15, merid);
//
//                System.out.println(x.toString() + ": " + x.toIndex());
//            }
//            h++;
//            if(h == 13){
//                h = 1;
//                merid = Meridian.PM;
//            }
//        }


//        TimeX a = new TimeX(12, 30, Meridian.PM);
//        TimeX b = new TimeX(12, 45, Meridian.PM);
//        TimeX c = new TimeX(2, 0, Meridian.PM);
//        int k = 1;
//        Meridian merid = Meridian.AM;
//        for(int i = 0; i < 24; i++){
//            TimeX x = new TimeX(k, 0, merid);
//            System.out.println(x.toString() + ":" + k + ": " + x.hourTo24());
//            k++;
//            if(k == 13){
//                k = 1;
//                merid = Meridian.PM;
//            }
//        }

//
//        int k = 1;
//        int old = 0;
//        int next = 2;
//        Meridian merid = Meridian.AM;
//        for(int i = 0; i < 24; i++){
//            TimeX x = new TimeX(k, 0, merid);
//            TimeX oldX = new TimeX(old, 0, merid);
//            TimeX newX = new TimeX(next, 0, Meridian.AM);
////            System.out.println( x.toString() + ": " +k +": " + x.hourTo24() + "   : ");
//            System.out.println(oldX.toString() +"," + x.toString() + "," + newX.toString() + ":   " + x.between(oldX, newX));
//            old = k++;
//            next++;
//            if(k == 13) {
//                k = 1;
//                old = 0;
//                next = 2;
//                merid = Meridian.PM;
//            }
//
//        }



    }


}