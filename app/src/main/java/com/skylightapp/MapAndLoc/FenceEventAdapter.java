package com.skylightapp.MapAndLoc;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.AwajimaLog;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FenceEventAdapter extends RecyclerView.Adapter<FenceEventAdapter.EventViewHolder> {
    private ArrayList<FenceEvent> fenceArrayList;
    private Context mcontext;
    int resources;
    private @NonNull
    List<FenceEvent> events;
    private SimpleDateFormat timeFormatter;
    private int numEvents;
    private int eventsSize,emergID;
    private EmergencyReport emergencyReport;
    private String report,reportType,town,status,state;


    private @NonNull
    AppController app;

    public FenceEventAdapter(ArrayList<FenceEvent> recyclerDataArrayList, Context mcontext) {
        this.fenceArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public FenceEventAdapter(Context context, int resources, ArrayList<FenceEvent> fences) {
        this.fenceArrayList = fences;
        this.mcontext = context;
        this.resources = resources;

    }

    private static final String CLASSTAG =
            " " + FenceEventAdapter.class.getSimpleName () + " ";
    String text;

    class EventViewHolder extends RecyclerView.ViewHolder {
        private final String CLASSTAG =
                " " + FenceEventAdapter.class.getSimpleName () + "." +
                        EventViewHolder.class.getSimpleName () + " ";

        AppCompatTextView eventTimeView,eventTypeView,eventFenceView,txtEmergID,txtEmergReport,txtEmergRTown,txtEmergRState,txtEmergRStatus;
        BezelImageView icon;


        EventViewHolder (View view)
        {
            super (view);
            Log.v (AwajimaLog.LOGTAG, CLASSTAG + "Constructor called");
            eventTimeView   = view.findViewById (R.id.event_time);
            eventFenceView  = view.findViewById (R.id.event_fence);
            eventTypeView   = view.findViewById (R.id.event_type);
            icon   = view.findViewById (R.id.img_eventF);
            txtEmergID   = view.findViewById (R.id.event_emerg_ID);
            txtEmergReport   = view.findViewById (R.id.f_event_report);
            txtEmergRTown   = view.findViewById (R.id.f_event_Town);
            txtEmergRState   = view.findViewById (R.id.f_stateEmerg);
            txtEmergRStatus   = view.findViewById (R.id.f_Emerg_Status);
        }

    }



    @Override
    public int getItemCount ()
    {
        int result = events.size ();
        Log.v ( AwajimaLog.LOGTAG,
                CLASSTAG + "getItemCount () returns " + result );
        return result;
    }
    public interface OnFenceClickListener {
        void onFenceClicked(Fence fence);
        void onListItemClick(int index);
    }

    FenceEventAdapter(@NotNull AppController theApp)
    {
        app = theApp;
        events = app.getEvents ();
        timeFormatter =
                new SimpleDateFormat ("dd-MMM HH:mm", Locale.getDefault ());
    }

    @Override
    public void onAttachedToRecyclerView (@NotNull RecyclerView view)
    {
        super.onAttachedToRecyclerView (view);
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onAttachedToRecyclerView called");
    }

    @Override
    public void onBindViewHolder (@NonNull EventViewHolder holder, int i) {
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onBindViewHolder called");
        FenceEvent event = events.get (i);
        if(event !=null){
            emergencyReport=event.getFenceEmergReport();
        }
        if(emergencyReport !=null){
            report =emergencyReport.getEmergReport();
            reportType=emergencyReport.getEmergRType();
            town=emergencyReport.getEmergRTown();
            status=emergencyReport.getEmergRStatus();
            emergID=emergencyReport.getEmergReportID();
            state=emergencyReport.getEmergRState();
        }
        if(event !=null){
            Log.v (AwajimaLog.LOGTAG, CLASSTAG + "Binding event: " + i + " " + event);
            text = timeFormatter.format (event.getTimestamp ());
            holder.eventFenceView.setText (event.getFence ());
            text = app.getTransitionString (app.getResources (), event.getEventID());
            holder.txtEmergRTown.setText (MessageFormat.format("Report Town{0}", town));
            holder.txtEmergReport.setText (MessageFormat.format("Report{0}", report));
            holder.txtEmergRStatus.setText (MessageFormat.format("Status{0}", status));
            holder.txtEmergRState.setText (MessageFormat.format("State{0}", state));
            holder.txtEmergID.setText (MessageFormat.format("Emerg ID:{0}", emergID+"/"+reportType));

        }
        if(text !=null){
            holder.eventTimeView.setText (text);
            holder.eventTypeView.setText (text);

        }


    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder ( @NonNull ViewGroup parent,
                                                int viewType )
    {
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onCreateViewHolder called");
        View view = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.view_fence_event, parent, false);
        return new EventViewHolder (view);
    }
    public void update () {
        timeFormatter =
                new SimpleDateFormat ("dd-MMM HH:mm", Locale.getDefault ());
        List<FenceEvent> appEvents = app.getEvents ();
        if(appEvents !=null){
            numEvents = appEvents.size ();

        }
        if(events !=null){
            eventsSize = events.size ();

        }


        if (numEvents > eventsSize) {
            events.addAll (eventsSize, appEvents);
        }
        if ((events.size () != numEvents)) {
            throw new AssertionError ("Event adding failed");
        }

    }

}
