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

import java.text.MessageFormat;
import java.util.ArrayList;



public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.RecyclerViewHolder> {

    private ArrayList<TimeLine> timeLines;
    private Context mcontext;
    int resources;

    public TimeLineAdapter(ArrayList<TimeLine> recyclerDataArrayList, Context mcontext) {
        this.timeLines = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public TimeLineAdapter(Context context, int resources, ArrayList<TimeLine> timeLines) {
        this.timeLines = timeLines;
        this.mcontext = context;
        this.resources = resources;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_list_row_admin, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        TimeLine recyclerData = timeLines.get(position);
        //Profile profile = (Profile) timeLines.get(position);
        holder.title.setText(MessageFormat.format("Tittle{0}", recyclerData.getTimelineTittle()));
        holder.details.setText(MessageFormat.format("Details{0}", recyclerData.getTimelineDetails()));
        holder.location.setText(MessageFormat.format("Location{0}", String.valueOf(recyclerData.getTimeline_Location())));

    }
    @Override
    public int getItemCount() {
        return (null != timeLines ? timeLines.size() : 0);
    }

    // View Holder Class to handle Recycler View.
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private final BezelImageView userPicture;
        private TextView details;
        private TextView location;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.timeline_tittle);
            details = itemView.findViewById(R.id.timeline_details);
            location = itemView.findViewById(R.id.timeline_location);
            userPicture = itemView.findViewById(R.id.user_pix11);
        }
    }
}
