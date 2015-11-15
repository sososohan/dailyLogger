package edu.ucsb.cs.cs185.dailylogger;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.crypto.spec.DESedeKeySpec;

/**
 * Created by Kevin on 6/3/2015.
 */
class SetTime implements View.OnFocusChangeListener, View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    private String time;
    private Context context;
    private int hour;
    private int minute;

    public SetTime(EditText editText, Context ctx, String time){
        this.editText = editText;
        this.editText.setOnClickListener(this);
        this.editText.setOnFocusChangeListener(this);
        this.time = time;
        this.context = ctx;

        int i = time.indexOf("M");
        editText.setText(time.substring(0, i - 1) + " " + time.substring(i - 1, i + 1) + "  ");
        this.hour = TimeX.parseHour24(time);
        this.minute = TimeX.parseMinute(time);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            new CustomTimePickerDialog(context, this, hour, minute, false).show();
        }
    }

    @Override
    public void onClick(View v) {

        new CustomTimePickerDialog(context, this, hour, minute, false).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String meridian;
        this.hour = hourOfDay;
        this.minute = minute;

        if(hourOfDay < 12){
            meridian = "AM";
        }
        else{
            hourOfDay -= 12;
            meridian = "PM";
        }
        if (hourOfDay == 0) hourOfDay = 12;

        this.editText.setText( String.format("%d:%02d %s", hourOfDay, this.minute, meridian));
    }

    //--------------------------------------------------------------------------------
    // Inner-class custom time picker dialog
    // Makes Minute options: 00, 15, 30, 45
    //
    protected class CustomTimePickerDialog extends TimePickerDialog {
        private final static int MINUTE_INTERVAL = 15;
        private TimePicker timePicker;
        private final OnTimeSetListener callback;

        public CustomTimePickerDialog(Context context, OnTimeSetListener callBack,
                                      int hourOfDay, int minute, boolean is24HourView) {
            super(context, TimePickerDialog.THEME_HOLO_LIGHT, callBack, hourOfDay, minute / MINUTE_INTERVAL,
                    is24HourView);
            this.callback = callBack;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (callback != null && timePicker != null) {
                timePicker.clearFocus();
                callback.onTimeSet(timePicker, timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute() * MINUTE_INTERVAL);
            }
        }

        @Override
        protected void onStop() {
        }

        @Override
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            try {
                Class<?> classForid = Class.forName("com.android.internal.R$id");
                Field timePickerField = classForid.getField("timePicker");
                this.timePicker = (TimePicker) findViewById(timePickerField
                        .getInt(null));
                Field field = classForid.getField("minute");

                NumberPicker mMinuteSpinner = (NumberPicker) timePicker
                        .findViewById(field.getInt(null));
                mMinuteSpinner.setMinValue(0);
                mMinuteSpinner.setMaxValue((60 / MINUTE_INTERVAL) - 1);
                List<String> displayedValues = new ArrayList<String>();
                for (int i = 0; i < 60; i += MINUTE_INTERVAL) {
                    displayedValues.add(String.format("%02d", i));
                }
                mMinuteSpinner.setDisplayedValues(displayedValues
                        .toArray(new String[0]));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
