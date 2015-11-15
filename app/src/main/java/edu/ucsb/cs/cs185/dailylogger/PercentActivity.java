package edu.ucsb.cs.cs185.dailylogger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Khalid on 6/6/2015.
 */
public class PercentActivity extends ActionBarActivity {

    Date currentDate;
    MyDate currentMyDate;
    DecimalFormat percent = new DecimalFormat("#0.#");
    HashMap<Date, MyDate> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Button percentButton = (Button) findViewById(R.id.amount);
//        percentButton.setBackgroundColor(Color.parseColor("#293e6a"));
//
        dates = (HashMap<Date, MyDate>) getIntent().getExtras().getSerializable("dateList");

        currentDate = new Date(getIntent().getLongExtra("currentDate", -1));
//


//        // HARD CODE ACTION - PLZ REMOVE
//        Category c1 = new Category("WORK");
//        Category c2 = new Category("FUN");
//        Category c3 = new Category("REST");
//        Category c4 = new Category("OTHER");
//        Category c5 = new Category("FOOD");
//        Category c6 = new Category("FITNESS");
//
//        c1.totalTime = 1;
//        c2.totalTime = 20;
//        c3.totalTime = 10;
//        c4.totalTime = 70;
//        c5.totalTime = 10;
//        c6.totalTime = 20;
//
//        TimeContainer a1Time = new TimeContainer(new TimeX(3, 15, TimeX.Meridian.AM), new TimeX(4, 15, TimeX.Meridian.AM));
//        TimeContainer a1Time2 = new TimeContainer(new TimeX(3, 45, TimeX.Meridian.AM), new TimeX(4, 45, TimeX.Meridian.AM));
//        ActivityData a1 = new ActivityData("Feed Cat", a1Time);
//        a1.times.add(a1Time2);
//        c4.activities.put(a1.label, a1);
//
//        TimeContainer a2Time = new TimeContainer(new TimeX(10, 15, TimeX.Meridian.PM), new TimeX(10, 45, TimeX.Meridian.PM));
//        ActivityData a2 = new ActivityData("Take Out Trash", a2Time);
//        c4.activities.put(a2.label, a2);
//
//        TimeContainer a3Time = new TimeContainer(new TimeX(5, 45, TimeX.Meridian.PM), new TimeX(6, 0, TimeX.Meridian.PM));
//        ActivityData a3 = new ActivityData("Really Long Other Category Type Thing LOLol ol", a3Time);
//        c4.activities.put(a3.label, a3);
//
//        currentMyDate = new MyDate(currentDate);
//        currentMyDate.totalTime = 131;
//        currentMyDate.categories.put("WORK", c1);
//        currentMyDate.categories.put("FUN", c2);
//        currentMyDate.categories.put("REST", c3);
//        currentMyDate.categories.put("OTHER", c4);
//        currentMyDate.categories.put("FOOD", c5);
//        currentMyDate.categories.put("FITNESS", c6);
//        dates.put(currentDate, currentMyDate);
//
//        //////////////////////////////
//
//        TextView dateTitle = (TextView)findViewById(R.id.dateTitle);
//        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
//        dateTitle.setText(sdf.format(currentDate));
//        dateTitle.setTextColor(Color.WHITE);
//
//        for (String key : currentMyDate.categories.keySet()) {
//            LinearLayout layout = (LinearLayout) findViewById(R.id.percent_content);
//
//            float weight = (float) currentMyDate.categories.get(key).totalTime / currentMyDate.totalTime;
//
//            Button b = new Button(this);
//            float weightPercent = weight * 100;
//            String formatted = percent.format(weightPercent);
//            b.setText(key + "   " + (Float.parseFloat(formatted)) + " %");
//            if (weight < 0.08)
//                weight = 0.08f;
//            b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                                                            0, weight));
//
//            b.setPadding(0,0,0,0);
//            b.setBackgroundColor(Color.parseColor(MainActivity.getColor(key)));
//            // HARDCODE SPACES TO JUSTIFY PERCENTAGES
////            b.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//            b.setOnClickListener(new Button.OnClickListener() {
//                public void onClick(View v) {
//                    DialogFragment dialog = new CategoryDialog();
//                    dialog.show(getFragmentManager(), "categoryDialog");
//
//                }
//            });
//            layout.addView(b);
//        }

        loadActivity();
    }

    public void loadActivity() {
        setContentView(R.layout.activity_percent);
        getSupportActionBar().hide();

        Button percentButton = (Button) findViewById(R.id.amount);
        percentButton.setBackgroundColor(Color.parseColor("#293e6a"));

//        currentDate.setTime(getIntent().getLongExtra("currentDate", -1));
          currentMyDate = dates.get(currentDate);

        if (dates.containsKey(currentMyDate)) {
            TextView percentNoData = (TextView) findViewById(R.id.percent_nodata);
            percentNoData.setVisibility(View.INVISIBLE);
        }
        else {
            TextView percentNoData = (TextView) findViewById(R.id.percent_nodata);
            percentNoData.setVisibility(View.VISIBLE);
        }

//        HashMap<Date, MyDate> dates = (HashMap<Date, MyDate>) getIntent().getExtras().getSerializable("dateList");

//        currentDate.setTime(getIntent().getLongExtra("currentDate", -1));
//        currentMyDate = dates.get(currentDate);

        // HARD CODE ACTION - PLZ REMOVE
//        CategoryA c1 = new CategoryA("WORK");
//        CategoryA c2 = new CategoryA("FUN");
//        CategoryA c3 = new CategoryA("REST");
//        CategoryA c4 = new CategoryA("OTHER");
//        CategoryA c5 = new CategoryA("FOOD");
//        CategoryA c6 = new CategoryA("FITNESS");
//
//        c1.totalTime = 1;
//        c2.totalTime = 20;
//        c3.totalTime = 10;
//        c4.totalTime = 70;
//        c5.totalTime = 10;
//        c6.totalTime = 20;
//
//        currentMyDate = new MyDate(currentDate);
//        currentMyDate.totalTime = 131;
//        currentMyDate.categories.put("WORK", c1);
//        currentMyDate.categories.put("FUN", c2);
//        currentMyDate.categories.put("REST", c3);
//        currentMyDate.categories.put("OTHER", c4);
//        currentMyDate.categories.put("FOOD", c5);
//        currentMyDate.categories.put("FITNESS", c6);
//        dates.put(currentDate, currentMyDate);

        //////////////////////////////

        TextView dateTitle = (TextView)findViewById(R.id.dateTitle);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        dateTitle.setText(sdf.format(currentDate));
        dateTitle.setTextColor(Color.WHITE);


        if (dates.containsKey(currentDate)) {
            for (String key : currentMyDate.categories.keySet()) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.percent_content);

                float weight = (float) currentMyDate.categories.get(key).totalTime / currentMyDate.totalTime;

                Button b = new Button(this);
                float weightPercent = weight * 100;
                String formatted = percent.format(weightPercent);
                b.setText(key + "   " + (Float.parseFloat(formatted)) + " %");
                if (weight < 0.08)
                    weight = 0.08f;
                b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        0, weight));

                b.setPadding(0, 0, 0, 0);
                b.setBackgroundColor(Color.parseColor(MainActivity.getColor(key)));
                // HARDCODE SPACES TO JUSTIFY PERCENTAGES
//            b.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

                final String passKey = key;
                b.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
//                    DialogFragment dialog = new CategoryDialog();
//                    dialog.show(getFragmentManager(), "categoryDialog");
                        CategoryDialog dialog = CategoryDialog.newInstance(passKey, currentMyDate);
                        dialog.show(getFragmentManager(), "categoryDialog");
                    }
                });
                layout.addView(b);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    // FOOTER BUTTONS ONCLICK
    public void goToPercent(View view) {

    }

    public void goToLog(View view) {
        Intent intent = new Intent(this, LogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        long millis = currentDate.getTime();
        intent.putExtra("currentDate", millis);
        intent.putExtra("dateList", this.dates);
        startActivity(intent);
    }

    public void goToArchive(View view) {
        Intent intent = new Intent(this, RecyclerActivity.class);
        long millis = currentDate.getTime();
        intent.putExtra("currentDate", millis);
        intent.putExtra("dateList", this.dates);
        startActivity(intent);
    }
    /////////////////////////

    public static class CategoryDialog extends DialogFragment {

        public static CategoryDialog newInstance(String catName, MyDate currentMyDate) {
            CategoryDialog frag = new CategoryDialog();
            Bundle args = new Bundle();
            args.putString("catName", catName);
            args.putSerializable("currentMyDate", currentMyDate);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.percent_dialog, null);
            builder.setView(v);

            MyDate currentMyDate = (MyDate) getArguments().getSerializable("currentMyDate");
            String catName = getArguments().getString("catName");

            // Title TextViews
            LinearLayout titleLayout = (LinearLayout) v.findViewById(R.id.percent_dialog_title);
            titleLayout.setBackgroundColor(Color.parseColor(MainActivity.getColor(catName)));

            TextView catTitle = (TextView) v.findViewById(R.id.catName);
            catTitle.setText(catName);

            TextView totTime = (TextView) v.findViewById(R.id.totalTime);
            int totalMinutes = currentMyDate.categories.get(catName).totalTime;
            totTime.setText(PercentActivity.dialogTotalTime(totalMinutes));

//            TextView empty = (TextView) v.findViewById(R.id.empty);
//            empty.setVisibility(View.VISIBLE);
            // Activity TextViews
            for (ActivityData activity : currentMyDate.categories.get(catName).activities.values()) {
//                TextView empty = (TextView) v.findViewById(R.id.empty);
//                empty.setVisibility(View.GONE);
                LinearLayout bigLayout = (LinearLayout) v.findViewById(R.id.percent_dialog_data);

                LinearLayout miniLayout = new LinearLayout(getActivity());
                LinearLayout.LayoutParams miniParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                     LinearLayout.LayoutParams.WRAP_CONTENT);
                miniParams.setMargins(0, 0, 0, 15);
                miniLayout.setLayoutParams(miniParams);
                miniLayout.setOrientation(LinearLayout.HORIZONTAL);
                bigLayout.addView(miniLayout);

                TextView activityLabel = new TextView(getActivity());
                LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                      LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                                      1);
                labelParams.setMargins(48, 0, 0, 0);
                labelParams.gravity = Gravity.LEFT;
                activityLabel.setLayoutParams(labelParams);
                activityLabel.setGravity(Gravity.LEFT);
                activityLabel.setText(activity.label);
                miniLayout.addView(activityLabel);

                TextView activityTime = new TextView(getActivity());
                LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                                      LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                                      1);
                timeParams.gravity = Gravity.RIGHT;
                activityTime.setLayoutParams(timeParams);
                String timesText = "";
                for (TimeContainer time : activity.times) {
                    timesText = timesText + time.start.toString() + " - " + time.end.toString();
                    timesText = timesText + "\n";
                }
                activityTime.setText(timesText);
                miniLayout.addView(activityTime);
            }

            return builder.create();
        }

    }

    public void incrementDate(View view) {
        final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
        long t = currentDate.getTime();
        Date afterAddingOneDay = new Date(t + (1440 * ONE_MINUTE_IN_MILLIS));
        currentDate = afterAddingOneDay;
        loadActivity();
    }

    public void decrementDate(View view) {
        final long ONE_MINUTE_IN_MILLIS=60000;//millisecs
        long t = currentDate.getTime();
        Date afterSubtractingOneDay = new Date(t - (1440 * ONE_MINUTE_IN_MILLIS));
        currentDate = afterSubtractingOneDay;
        loadActivity();
    }

    public static String dialogTotalTime(int minutes) {
        int hours = minutes / 60;
        int newMinutes = minutes % 60;
        return Integer.toString(hours) + " hrs " + Integer.toString(newMinutes) + " mins";
    }
}
