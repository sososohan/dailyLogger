package edu.ucsb.cs.cs185.dailylogger;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin on 6/3/2015.
 */
public class EditActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "DEBUG";
    private TextView labelView;
    private Spinner categorySpinnerView;
    private EditText editTextFrom;
    private EditText editTextTo;
    private String label;
    private HashMap<String, Integer> categoryNameMap = createNameMap();
    private String categoryName;

    private String startTime;
    private String endTime;
    private Intent mainIntent;

    private String[] timeFilled;

    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_activity_fragment);
        mainIntent = getIntent();

        timeFilled =  mainIntent.getStringArrayExtra(MainActivity.KEY_TIME_FILLED);

        // gets activity info from MainActivity
        timeFilled = mainIntent.getStringArrayExtra(MainActivity.KEY_TIME_FILLED);

        label = mainIntent.getStringExtra(MainActivity.KEY_LABEL);
//        category = mainIntent.getParcelableExtra(MainActivity.KEY_CATEGORY);
        categoryName = mainIntent.getStringExtra(MainActivity.KEY_CATEGORY);
        startTime = mainIntent.getStringExtra(MainActivity.KEY_START_TIME);
        endTime = mainIntent.getStringExtra(MainActivity.KEY_END_TIME);

        // initialize label edittext view
        labelView = (EditText) findViewById(R.id.activity_label);
        if( label != null){ labelView.setText(label); }

        // initialize category spinner view
        categorySpinnerView = (Spinner) findViewById(R.id.category_label);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinnerView.setAdapter(adapter);

        if( categoryName != null){
            Log.d("asdf", Integer.toString(categoryNameMap.get(categoryName)));
            categorySpinnerView.setSelection(categoryNameMap.get(categoryName));
        }

        // initialize FromTimePicker
        editTextFrom = (EditText) findViewById(R.id.time_begin);
        SetTime fromTime = new SetTime(editTextFrom, this, startTime);
        // initialize ToTimePicker
        editTextTo = (EditText) findViewById(R.id.time_end);
        SetTime toTime = new SetTime(editTextTo, this, endTime);

    }


    public void confirmChanges(View view){

        label = labelView.getText().toString();
        categoryName = categorySpinnerView.getSelectedItem().toString();
        // SEND RESULTS BACK TO MAIN INTENT
        Intent output = new Intent();
        output.putExtra(MainActivity.KEY_LABEL, label);
        output.putExtra(MainActivity.KEY_CATEGORY, categoryName);
        output.putExtra(MainActivity.KEY_START_TIME, editTextFrom.getText().toString());
        output.putExtra(MainActivity.KEY_END_TIME, editTextTo.getText().toString());
        setResult(RESULT_OK, output);
        finish();
    }

//    public void deleteActivity(View view){

    public void deleteActivity(){
        Intent output = new Intent();
        categoryName = categorySpinnerView.getSelectedItem().toString();

        output.putExtra(MainActivity.KEY_LABEL, labelView.getText().toString());
        output.putExtra(MainActivity.KEY_CATEGORY, categoryName);
        output.putExtra(MainActivity.KEY_START_TIME, editTextFrom.getText().toString());
        output.putExtra(MainActivity.KEY_END_TIME, editTextTo.getText().toString());
        setResult(MainActivity.RESULT_DELETE, output);
        finish();
    }

    public void cancel(View view){
        setResult(RESULT_CANCELED);
        finish();
    }

    public boolean isTimeFree(TimeX start, TimeX end, String label){
        for(int i = start.toIndex(); i < end.toIndex(); i++){
            if(timeFilled[i] != null && ! timeFilled[i].equals(label)){
                Log.d(DEBUG_TAG, "label, _ : " + label + ", " + timeFilled[i]);
                return false;
            }
        }
        return true;
    }

    public void timeAlreadyFilledAlert(){
        new AlertDialog.Builder(this)
                .setTitle("Time Slot Interference")
                .setMessage("One or more time slots already filled! Change the time!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void deleteConfirmationAlert(View view){
        new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("This action cannot be undone!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteActivity();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss
                    }
                })
                .setIcon(R.drawable.ic_announcement_black_24dp)
                .show();
    }

    public HashMap<String, Integer> createNameMap(){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("WORK", 0);
        map.put("REST", 1);
        map.put("FUN", 2);
        map.put("FITNESS", 3);
        map.put("FOOD", 4);
        map.put("OTHER", 5);
        return map;
    }


}

