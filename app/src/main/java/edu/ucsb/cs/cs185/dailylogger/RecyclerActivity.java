package edu.ucsb.cs.cs185.dailylogger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kevin on 6/8/2015.
 */
public class RecyclerActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    java.util.Date currentDate;
    HashMap<java.util.Date, MyDate> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        getSupportActionBar().hide();

        Button percentButton = (Button) findViewById(R.id.archive);
        percentButton.setBackgroundColor(Color.parseColor("#293e6a"));

        dates = (HashMap<java.util.Date, MyDate>) getIntent().getExtras().getSerializable("dateList");
        currentDate = new java.util.Date(getIntent().getLongExtra("currentDate", -1));

        ArrayList<MyDate> myDataSet = new ArrayList<MyDate>();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
    //        dateTitle.setText(sdf.format(currentDate));
        for (java.util.Date date : dates.keySet()) {
            myDataSet.add(dates.get(date));
        }

        if (dates.size() > 0) {
            TextView nodata = (TextView) findViewById(R.id.nodata);
            nodata.setVisibility(View.INVISIBLE);
        }
        else {
            TextView nodata = (TextView) findViewById(R.id.nodata);
            nodata.setVisibility(View.VISIBLE);
        }
        // DATA SET CONTAINS THE STRING EACH TEXTVIEW WILL CONTAIN
//        String[] myDataSet = {"THIS IS A STRING1", "THIS IS THE STRING 2", "THIS IS THE STRING 3","THIS IS THE STRING 4"};

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(dates, myDataSet);
        mRecyclerView.setAdapter(mAdapter);
    }

    // FOOTER BUTTONS ONCLICK
    public void goToPercent(View view) {
        Intent intent = new Intent(this, PercentActivity.class);
        long millis = currentDate.getTime();
        intent.putExtra("currentDate", millis);
        intent.putExtra("dateList", this.dates);
        startActivity(intent);
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

    }
    /////////////////////////

}