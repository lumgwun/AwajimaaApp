package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;



public class TimeLineAdapterOthers extends RecyclerView.Adapter<TimeLineAdapterOthers.RecyclerViewHolder> {

    private ArrayList<TimeLine> timeLines;
    private Context mcontext;
    int resources;

    public TimeLineAdapterOthers(ArrayList<TimeLine> recyclerDataArrayList, Context mcontext) {
        this.timeLines = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public TimeLineAdapterOthers(Context context, int resources, ArrayList<TimeLine> timeLines) {
        this.timeLines = timeLines;
        this.mcontext = context;
        this.resources = resources;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_row_user, parent, false);
        return new RecyclerViewHolder(view);
    }
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public final BezelImageView userPicture;
        public TextView details;
        public TextView timeStamp;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.timeline_tittle_u);
            details = itemView.findViewById(R.id.timeline_details_u);
            timeStamp = itemView.findViewById(R.id.timeline_date);
            userPicture = itemView.findViewById(R.id.user_pix11);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {
        TimeLine recyclerData = timeLines.get(position);
        holder.title.setText(MessageFormat.format("Tittle{0}", recyclerData.getTimelineTittle()));
        holder.details.setText(MessageFormat.format("Details{0}", recyclerData.getTimelineDetails()));
        holder.timeStamp.setText(MessageFormat.format("Time Stamp{0}", String.valueOf(recyclerData.getTimestamp())));
    }

    @Override
    public int getItemCount() {
        return (null != timeLines ? timeLines.size() : 0);
    }

    // View Holder Class to handle Recycler View.


}
