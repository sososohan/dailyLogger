package edu.ucsb.cs.cs185.dailylogger;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Kevin on 6/5/2015.
 */
public class TimeContainer implements Serializable{
    public TimeX start;
    public TimeX end;

    public TimeContainer(TimeX start, TimeX end){
        this.start = start;
        this.end = end;
    }

    public TimeContainer(int h1, int m1, TimeX.Meridian merid1, int h2, int m2, TimeX.Meridian merid2){
        start = new TimeX(h1, m1, merid1);
        end = new TimeX(h2, m2, merid2);

    }

    @Override
    public String toString(){
        String s = start.toString() + " - " + end.toString();
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeContainer that = (TimeContainer) o;

        if (!start.equals(that.start)) return false;
        return end.equals(that.end);

    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }

    // get time between start and end
    public int getMinutes() {
        int startMinutes = 0;
        int endMinutes = 0;

        if (this.start.getMeridian() == TimeX.Meridian.PM) {
            startMinutes += 720;
        }

        if (this.start.getHour() != 12) {
            startMinutes += this.start.getHour() * 60;
        }
        startMinutes += this.start.getMinute();

        if (this.end.getMeridian() == TimeX.Meridian.PM) {
            endMinutes += 720;
        }

        if (this.end.getHour() != 12) {
            endMinutes += this.end.getHour() * 60;
        }
        endMinutes += this.end.getMinute();


        if (endMinutes < startMinutes) {
            return 1440 - startMinutes + endMinutes;
        }
        else {
            return endMinutes - startMinutes;
        }
    }

    public boolean collision(TimeContainer b) {
        boolean collides = false;

        int startMinutesA = 0;
        int endMinutesA = 0;

        if (this.start.getMeridian() == TimeX.Meridian.PM) {
            startMinutesA += 720;
        }

        if (this.start.getHour() != 12) {
            startMinutesA += this.start.getHour() * 60;
        }
        startMinutesA += this.start.getMinute();

        if (this.end.getMeridian() == TimeX.Meridian.PM) {
            endMinutesA += 720;
        }

        if (this.end.getHour() != 12) {
            endMinutesA += this.end.getHour() * 60;
        }
        endMinutesA += this.end.getMinute();

        int startMinutesB = 0;
        int endMinutesB = 0;

        if (this.start.getMeridian() == TimeX.Meridian.PM) {
            startMinutesB += 720;
        }

        if (this.start.getHour() != 12) {
            startMinutesB += this.start.getHour() * 60;
        }
        startMinutesB += this.start.getMinute();

        if (this.end.getMeridian() == TimeX.Meridian.PM) {
            endMinutesB += 720;
        }

        if (this.end.getHour() != 12) {
            endMinutesB += this.end.getHour() * 60;
        }
        endMinutesB += this.end.getMinute();

        // if exact same
        if (startMinutesA == startMinutesB && endMinutesA == endMinutesB) return false;

        // check
        if (startMinutesB < endMinutesA && endMinutesB > startMinutesA) {
            collides = true;
        }

        return collides;
    }
}
