package edu.ucsb.cs.cs185.dailylogger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = "DEBUG";
    public static final int RESULT_DELETE = 2; //-1,0,1 taken by RESULT_OK, RESULT_CANCELED, RESULT_FIRST_USER
    public static final int EDIT_INTENT = 1;
    public static final String KEY_TIME_FILLED = "timeFilled";
    public static final String KEY_CATEGORY_LIST = "categories";
    public static final String KEY_LABEL = "label";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_START_TIME = "startTime";
    public static final String KEY_END_TIME = "endTime";
    private String[] timeFilled;
    private float mScaleFactor = 1.f;
    private ScaleGestureDetector scaleDetector;
    private ListView listView;
    private ScheduleAdapter scheduleAdapter;

//    private ArrayList<Category> categoryList;
//    private ArrayList<ActivityData> activityList;
//    private String currentActivity;

    private HashMap<String, ActivityData> activityMap;
//    ArrayList<ActivityData> allActivities;

    HashMap<Date, MyDate> dates = new HashMap<Date, MyDate>();
    Date currentDate = new Date();
    MyDate currentMyDate;
    DecimalFormat percent = new DecimalFormat("#0.#");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scaleDetector = new ScaleGestureDetector(this, new ScaleListener());

        loadActivity();
    }

    public void loadActivity() {
        setContentView(R.layout.activity_daily_logger);
        getSupportActionBar().hide();

        Button percentButton = (Button) findViewById(R.id.log);
        percentButton.setBackgroundColor(Color.parseColor("#293e6a"));

        TextView dateTitle = (TextView)findViewById(R.id.dateTitle);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        dateTitle.setText(sdf.format(currentDate));
        dateTitle.setTextColor(Color.WHITE);
        // Initializes category list w/ 4 default categories
//        categoryList = Category.defaultCategories();

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.schedule_list_view);
        // Creates adapter for the listView
        scheduleAdapter = new ScheduleAdapter(this);
        // Assign adapter to ListView
        listView.setAdapter(scheduleAdapter);
        listView.setSelection(32);

        // PERCENT HARD CODE
//        currentMyDate = new MyDate(currentDate);

        //PREFILLED ACTIVITY TO TEST ON---------------------------------------------------------------------------------------
//        currentDate = new Date();
//        Calendar cal = Calendar.getInstance().getInstance();
//        cal.set(2015, 6, 7);
//        currentDate = cal.getTime();
//
//        currentMyDate = new MyDate(currentDate);
//        currentMyDate.categories = preFillCategoryData();
//
//        dates.put(currentDate, currentMyDate);
        //-------------------------------------------------------------------------------------------------------------------

        // init timeFilled
//        timeFilled = new boolean[96];

//        ArrayList<ActivityData> aList = new ArrayList<>(activityMap.values());

        activityMap = new HashMap<String, ActivityData>();
        if (dates.containsKey(currentDate)) {
            for (String catName : dates.get(currentDate).categories.keySet()) {
                Category currentCat = dates.get(currentDate).categories.get(catName);
                for (String activityName : currentCat.activities.keySet()) {
                    ActivityData aData = currentCat.activities.get(activityName);
                    aData.category = currentCat;
                    activityMap.put(activityName, currentCat.activities.get(activityName));
                }
            }
        }

        timeFilled = generateTimeFilled(activityMap);

//        activityList = preConceivedData();
//        ArrayList<ActivityData> arrayList = new ArrayList<>(activityMap.values());

        //possible delete
        scheduleAdapter.updateActivityList(activityMap);
        scheduleAdapter.notifyDataSetChanged();


        final Activity mainActivity = this;
        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent editIntent = getInformation(mainActivity, view, position);
                mainActivity.startActivityForResult(editIntent, EDIT_INTENT);
            }

        });

//                (new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                TODO CORRECT: check and putExtra: label, category, time_begin, time_end
//                Intent editIntent = new Intent(mainActivity, EditActivity.class);
//
//                editIntent.putExtra(KEY_TIME_FILLED, timeFilled);
//
//                editIntent.putExtra(KEY_LABEL, "Working");
//                editIntent.putExtra(KEY_CATEGORY, "Work");
//                editIntent.putExtra(KEY_START_TIME, "8:00AM");
//                editIntent.putExtra(KEY_END_TIME, "8:30AM");
//
//
//
//                editIntent.putParcelableArrayListExtra(KEY_CATEGORY_LIST, categoryList);
//                mainActivity.startActivityForResult(editIntent, EDIT_INTENT);
//            }
//
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        if(requestCode == EDIT_INTENT && resultCode == RESULT_OK && data != null){
//            //TODO: Get back values from editActivity intent
//
//        }
//        else if(requestCode == EDIT_INTENT && resultCode == RESULT_CANCELED ){
//            //TODO: Delete activity
//        }
        if (requestCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == EDIT_INTENT && data != null) {
            String label = data.getStringExtra(KEY_LABEL);
//            categoryList = data.getParcelableArrayListExtra(KEY_CATEGORY_LIST);
//            Category category = data.getParcelableExtra(KEY_CATEGORY);

            String catName = data.getStringExtra(KEY_CATEGORY);
            catName = catName.toUpperCase();
            String startTime = data.getStringExtra(KEY_START_TIME);
            String endTime = data.getStringExtra(KEY_END_TIME);

//            categoryMap = generateCategoryMap(categoryList);
//            if (label == null || category == null) {
//                return;
//            }

            if (label.isEmpty() || catName.isEmpty()) {
                return;
            }


            // DOES CURRENT DATE EXIST IN DATES
            currentDate.setHours(1);
            currentDate.setMinutes(1);
            if (!dates.containsKey(currentDate)) {
                currentMyDate = new MyDate(currentDate);
                dates.put(currentDate, currentMyDate);
            }
            else {
                currentMyDate = dates.get(currentDate);
            }

            Category newCat = new Category(catName);

            // DOES CURRENT DATE CONTAIN CATEGORY
            if (!currentMyDate.categories.containsKey(catName)) {
                currentMyDate.categories.put(catName, newCat);

            } // else do nothing



            TimeContainer activityTime = new TimeContainer(new TimeX(startTime), new TimeX(endTime));
            int minutes = activityTime.getMinutes();

            ActivityData newActivity = new ActivityData(label, new TimeContainer(new TimeX(startTime), new TimeX(endTime)));
            newActivity.category = newCat;

            // DOES CURRENT DATE CONTAIN ACTIVITY DATA
            if (!currentMyDate.categories.get(catName).activities.containsKey(label)) {
                currentMyDate.categories.get(catName).activities.put(label, newActivity);
//                allActivities.add(newActivity);
            }
            else {
                if (resultCode != RESULT_DELETE) {
                    currentMyDate.categories.get(catName).activities.get(label).times.add(activityTime);
                }
                currentMyDate.categories.get(catName).activities.get(label).minutes += minutes;
                for (ActivityData activity : activityMap.values()) {
                    if (activity.label == label && resultCode != RESULT_DELETE) {
                        activity.times.add(activityTime);
                    }
                }
            }


//            ActivityData activityData = new ActivityData();
//            activityData.setLabel(label);
//            activityData.setCategory(category);

//            TimeX start = new TimeX(startTime);
//            TimeX end = new TimeX(endTime);
//            TimeContainer container = new TimeContainer(start, end);
//            ArrayList<TimeContainer> aList = new ArrayList<TimeContainer>();
//            aList.add(container);
//            activityData.setTimes(aList);

            // increment total times
            if (resultCode != RESULT_DELETE) {
                currentMyDate.totalTime += minutes;
                currentMyDate.categories.get(catName).totalTime += minutes;
            }


            if (resultCode == RESULT_OK) {
                updateActivityMap(newActivity, activityTime);
            } else if ( resultCode == RESULT_DELETE) {
                updateActivityMapDelete(newActivity);
            }
            // update timeFilled
//            ArrayList<ActivityData> arrayList = new ArrayList<>(activityMap.values());
            timeFilled = generateTimeFilled(activityMap);

            // update activityList
            scheduleAdapter.updateActivityList(activityMap);
            scheduleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_daily_logger, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        // Lets scaleDetector listen to all the touch events
        scaleDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.70f, Math.min(mScaleFactor, 4.30f));
            scheduleAdapter.scale(mScaleFactor);
            return true;
        }
    }

    //--------------------------------------------------------------------------------------------
    public HashMap<String, ActivityData> updateActivityMapDelete(ActivityData aData) {
        TimeContainer c = aData.times.get(0);

        // Delete from dates
        ActivityData toDelete = dates.get(currentDate).categories.get(aData.category.name).activities.get(aData.label);
        if (toDelete.times.size() < 2) {
            dates.get(currentDate).categories.get(aData.category.name).activities.remove(aData.label);
        }
        else {
            dates.get(currentDate).categories.get(aData.category.name).activities.get(aData.label).times.remove(c);
        }
        dates.get(currentDate).categories.get(aData.category.name).totalTime -= c.getMinutes();
        dates.get(currentDate).totalTime -= c.getMinutes();

        if (dates.get(currentDate).categories.get(aData.category.name).totalTime == 0) {
            dates.get(currentDate).categories.remove(aData.category.name);
        }

        if (dates.get(currentDate).totalTime == 0) {
            dates.remove(currentDate);
        }


        ActivityData  activityData = activityMap.get(aData.label);
        ArrayList<TimeContainer> timeList = activityData.times;
        Iterator<TimeContainer> iter = timeList.iterator();
        while(iter.hasNext()){
            TimeContainer tmp = (TimeContainer) iter.next();
            if(tmp.start.equals(c.start)){
                iter.remove();
            }
        }
        return activityMap;
    }

    public void updateActivityMap(ActivityData aData, TimeContainer timeContainer) {
        HashMap<String, ActivityData> newMap = new HashMap<>();
        TimeContainer c = aData.times.get(0);
        TimeX start = c.start;
        TimeX end = c.end;

        ArrayList<ActivityData> deletedList = new ArrayList<>();

        Iterator it = activityMap.entrySet().iterator();

        // Goes through each activity and their time containers
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();
            ActivityData activityData = (ActivityData) pair.getValue();

            ActivityData tmp = new ActivityData();
            tmp.label = activityData.label;
            tmp.category = new Category(activityData.category);

            // PHYSICALLY EDIT DATES

            ArrayList<TimeContainer> cList = new ArrayList<>();
            // Checks if any times conflict, if they do, then do not add it the new activity list
            for (TimeContainer container : activityData.times) {
                TimeX s = container.start;
                TimeX e = container.end;
                boolean include = true;
                TimeContainer tmpContainer = new TimeContainer(new TimeX(s), new TimeX(e));
                // if any timeslot is between the new activity time slot, then remove the timecontainer from its Times
                for (TimeX iter = new TimeX(s); !iter.equals(e); iter.nextFifteenMin()) {
                    if(iter.between(start, end)){
                        include = false;
                    }
                }
                if(include){
                    cList.add(tmpContainer);
                } else {
                    deletedList.add(activityData);

//                    // dates stuff
//                    dates.get(currentDate).categories.get(activityData.category.name).activities.get(activityData.label).minutes -= container.getMinutes();
//                    dates.get(currentDate).categories.get(activityData.category.name).totalTime -= container.getMinutes();
//                    dates.get(currentDate).totalTime -= container.getMinutes();
//
//                    dates.get(currentDate).categories.get(activityData.category.name).activities.get(activityData.label).times.remove(container);
//                    if (dates.get(currentDate).categories.get(activityData.category.name).activities.get(activityData.label).minutes == 0) {
//                        dates.get(currentDate).categories.get(activityData.category.name).activities.remove(activityData.label);
//                    }
//                    if (dates.get(currentDate).categories.get(activityData.category.name).totalTime == 0) {
//                        dates.get(currentDate).categories.remove(activityData.category.name);
//                    }
//                    if (dates.get(currentDate).totalTime == 0) {
//                        dates.remove(currentDate);
//                    }

                }
            }
            tmp.times = cList;
            // if time slots not empty, then put into new map
            if( ! tmp.times.isEmpty() ){
                newMap.put(tmp.label, tmp);
            }
        }
        // insert the new activity which only has 1 times
        String label = aData.label;
        if(newMap.containsKey(label)) {
            newMap.get(label).times
                    .add(aData.times.get(0));
        }
        else{
            newMap.put(label, aData);
        }


        it = newMap.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ActivityData activityData = (ActivityData) pair.getValue();
            String k = (String) pair.getKey();
            ActivityData a = (ActivityData) pair.getValue();
//            a.getLabel();

//            Log.d(DEBUG_TAG, "Key:" + k + " -- " + a.getLabel() + "," + a.getCategory().getName() + ",     " + a.getTimes().toString());
        }

        for (ActivityData activity : deletedList) {
            for (TimeContainer sameTask : activity.times) {
                if (sameTask.collision(timeContainer)) {
                    dates.get(currentDate).categories.get(activity.category.name).activities.get(activity.label).times.remove(sameTask);
                }
            }
            dates.get(currentDate).categories.get(activity.category.name).activities.get(activity.label).times.remove(timeContainer);
            if (dates.get(currentDate).categories.get(activity.category.name).activities.get(activity.label).times.size() == 0) {
                dates.get(currentDate).categories.get(activity.category.name).activities.remove(activity.label);
            }
            dates.get(currentDate).categories.get(activity.category.name).totalTime -= timeContainer.getMinutes();
            dates.get(currentDate).totalTime -= timeContainer.getMinutes();

            if (dates.get(currentDate).categories.get(activity.category.name).totalTime <= 0) {
                dates.get(currentDate).categories.remove(activity.category.name);
            }
        }

        activityMap = newMap;
    }

    public Intent getInformation(Activity mainActivity, View view, int position){
        Intent editIntent = new Intent(mainActivity, EditActivity.class);
        TextView timeView = (TextView) view.findViewById(R.id.timeX);

        editIntent.putExtra(KEY_TIME_FILLED, timeFilled);

        if(timeFilled[position] != null) {
            String label = timeFilled[position];

            editIntent.putExtra(KEY_LABEL, label);

            ActivityData activityData = activityMap.get(label);
            Category category = activityData.category;

//            editIntent.putExtra(KEY_CATEGORY, (Parcelable)category);    //Parcelable or serializable? unsure
            editIntent.putExtra(KEY_CATEGORY, category.name);

            TimeX time = TimeX.indexToTime(position);
            TimeContainer timeContainer = getCorrectTimeContainer(time, activityData);
            TimeX start = timeContainer.start;
            TimeX end = timeContainer.end;

            editIntent.putExtra(KEY_START_TIME, start.toString());
            editIntent.putExtra(KEY_END_TIME, end.toString());
        }
        else{
            TextView meridianView = (TextView) view.findViewById(R.id.meridian);
            String time = timeView.getText().toString() + meridianView.getText().toString();
            editIntent.putExtra(KEY_START_TIME, time);
            TimeX endTime = TimeX.indexToTime(position+1);
            editIntent.putExtra(KEY_END_TIME, endTime.toString());
        }
        editIntent.putExtra(KEY_TIME_FILLED, timeFilled);
//        editIntent.putParcelableArrayListExtra(KEY_CATEGORY_LIST, categoryList);
        return editIntent;
    }

//    public String[] generateTimeFilled(ArrayList<ActivityData> allActivities){
//        // returns String array of size 96 with labels corresponding to the timeslots their in.
//        String[] timeFilled = new String[96];
//        for(ActivityData activity : allActivities){
//            for( TimeContainer timeSlot : activity.times){
//                for(int i = timeSlot.start.toIndex(); i < timeSlot.end.toIndex(); i++){
//                    timeFilled[i] = activity.label;
//                }
//            }
//        }
//        return timeFilled;
//    }

    public String[] generateTimeFilled(HashMap<String, ActivityData> activityMap){
        ArrayList<ActivityData> aList = new ArrayList<>(activityMap.values());
        // returns String array of size 96 with labels corresponding to the timeslots their in.
        String[] timeFilled = new String[96];
        for(ActivityData activityData : aList){
            for( TimeContainer timeSlot : activityData.times){
                for(int i = timeSlot.start.toIndex(); i < timeSlot.end.toIndex(); i++){
                    timeFilled[i] = activityData.label;
                }
            }
        }
        return timeFilled;
    }

    public TimeContainer getCorrectTimeContainer(TimeX time, ActivityData activityData){
        // Finds the correct TimeContainer for the activity, given a timeX
        for( TimeContainer timeContainer : activityData.times){
            if(time.between(timeContainer.start, timeContainer.end)){
                return timeContainer;
            }
        }
        return null;
    }

    public static HashMap<String, Category> generateCategoryMap(ArrayList<Category> categoryList){
        HashMap<String, Category> categoryMap = new HashMap<>();
        for( Category c : categoryList ){
            categoryMap.put(c.name, new Category(c));
        }
        return categoryMap;

    }

    HashMap<String, Category> preFillCategoryData(){
        HashMap<String, Category> newMap = new HashMap<>();
        // Work activities
        Category category = new Category("WORK");
        //Help Desk
        String label = "Help Desk";
        TimeContainer container = new TimeContainer(10, 0, TimeX.Meridian.AM, 12, 30, TimeX.Meridian.PM);
        ActivityData aData = new ActivityData(label, container);
        category.activities.put(label, aData);
        //Internship
        label = "Internship";
        container = new TimeContainer(2, 0, TimeX.Meridian.PM, 5, 0, TimeX.Meridian.PM);
        aData = new ActivityData(label, container);
        category.activities.put("Internship", aData);
        //add it to the map
        newMap.put(category.name, category);

        //Fitnes Activites
        category = new Category("FITNESS");
        //walk the fucking dog
        label = "Walk The Dog";
        container = new TimeContainer(8, 15, TimeX.Meridian.AM, 8, 45, TimeX.Meridian.AM);
        aData = new ActivityData(label, container);
        category.activities.put(label, aData);
        //add it to the map
        newMap.put(category.name, category);

//         //Debug code
//        Iterator it = newMap.entrySet().iterator();
//        while(it.hasNext()) {
//            Map.Entry pair = (Map.Entry) it.next();
//            Category c = (Category) pair.getValue();
//            Log.d(DEBUG_TAG, c.name);
//            for(ActivityData a : c.activities.values()){
//                Log.d(DEBUG_TAG, a.getLabel() + " " + a.getTimes().toString());
//            }
//        }

        return newMap;
    }

//    ArrayList<ActivityData> preConceivedData(){
//        // Creates activities for the demo
//        ArrayList<ActivityData> activityList = new ArrayList<ActivityData>();
//        ActivityData work = new ActivityData();
//        work.setLabel("Work");
//        work.setCategory(categoryList.get(0));
//        work.setTimeStart(new TimeX(8, 15, TimeX.Meridian.AM));
//        work.setTimeEnd(new TimeX(11, 0, TimeX.Meridian.AM));
//        activityList.add(work);
//        return activityList;
//    }

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

    // FOOTER BUTTONS ONCLICK
    public void goToPercent(View view) {
        Intent intent = new Intent(this, PercentActivity.class);
        long millis = currentDate.getTime();
        intent.putExtra("currentDate", millis);
        intent.putExtra("dateList", this.dates);
        startActivity(intent);
    }

    public void goToLog(View view) {

    }

    public void goToArchive(View view) {
        Intent intent = new Intent(this, RecyclerActivity.class);
        long millis = currentDate.getTime();
        intent.putExtra("currentDate", millis);
        intent.putExtra("dateList", this.dates);
        startActivity(intent);
    }
    //////////////////////////

    public static String getColor(String category) {
        String color;
        switch(category) {
            case "WORK":    color = "#D07979";
                            break;
            case "REST":    color = "#9679D0";
                            break;
            case "FUN":     color = "#A6C4EA";
                            break;
            case "FITNESS": color = "#79D0A3";
                            break;
            case "FOOD":    color = "#CDD079";
                            break;
            case "OTHER":   color = "#D09C79";
                            break;
            default:        color = "#000000";
                            break;
        }
        return color;
    }
}
