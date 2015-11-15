package edu.ucsb.cs.cs185.dailylogger;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kevin on 6/8/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
//    private String[] mDataset;
      ArrayList<MyDate> mDataset;
        HashMap<java.util.Date, MyDate> dates;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(HashMap<java.util.Date, MyDate> myDates, ArrayList<MyDate> myDataset) {
        mDataset = myDataset;
        dates = myDates;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView tView = (TextView) holder.mView.findViewById(R.id.card_text);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
//        //        dateTitle.setText(sdf.format(currentDate));
//        for (java.util.Date date : dates.keySet()) {
//            String entry = sdf.format(dates.get(date).date) + "                          "
//                    +  PercentActivity.dialogTotalTime(dates.get(date).totalTime);
//            myDataSet.add(entry);
        String entry = sdf.format(mDataset.get(position).date) + "                          "
                + PercentActivity.dialogTotalTime(mDataset.get(position).totalTime);
        tView.setText(entry);
        final int myDatePosition = position;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dateLog = new Intent(v.getContext(), LogActivity.class);
                dateLog.putExtra("currentDate", mDataset.get(myDatePosition).date.getTime());
                dateLog.putExtra("dateList", dates);
                v.getContext().startActivity(dateLog);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}