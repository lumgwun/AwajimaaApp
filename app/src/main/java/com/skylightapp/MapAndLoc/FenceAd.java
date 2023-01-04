package com.skylightapp.MapAndLoc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.AwajimaLog;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FenceAd extends RecyclerView.Adapter<FenceAd.EventViewHolder> {
    private OnFenceEventClickListener listener;
    private ArrayList<FenceEvent> fenceEventArrayList;
    private Context mcontext;
    private FenceEvent fenceEvent;
    private @NonNull
    AppController app;
    String text;

    private static final String CLASSTAG =
            " " + FenceAd.class.getSimpleName () + " ";

    class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final String CLASSTAG =
                " " + FenceAd.class.getSimpleName () + "." +
                        FenceAd.EventViewHolder.class.getSimpleName () + " ";

        AppCompatTextView eventTimeView,eventTypeView,eventFenceView, txtEventID,txtStatus,txtEmergType,txtEmergID;


        EventViewHolder (View view)
        {
            super (view);
            Log.v (AwajimaLog.LOGTAG, CLASSTAG + "Constructor called");
            eventTimeView   = view.findViewById (R.id.event_Geotime);
            eventFenceView  = view.findViewById (R.id.event_Geofence);
            eventTypeView   = view.findViewById (R.id.event_Geotype);
            txtEventID = view.findViewById (R.id.device_idG);
            txtEmergType   = view.findViewById (R.id.type_emergGeo);
            txtEmergID   = view.findViewById (R.id.emerg_event_reportID);
            txtStatus   = view.findViewById (R.id.event_status_emerg);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            listener.onListItemClick(clickedPosition);

        }
    }
    FenceAd(@NotNull AppController theApp)
    {
        app = theApp;
        events = app.getEvents ();
        timeFormatter =
                new SimpleDateFormat ("dd-MMM HH:mm", Locale.getDefault ());
    }
    public FenceAd(Context context, ArrayList<FenceEvent> fenceEvents) {
        this.fenceEventArrayList = fenceEvents;
        this.mcontext = context;
    }
    public interface OnFenceEventClickListener {
        void onEventClicked(FenceEvent fenceEvent);
        void onListItemClick(int index);
    }


    private @NonNull
    List<FenceEvent> events;
    private SimpleDateFormat timeFormatter;


    @Override
    public int getItemCount ()
    {
        int result = events.size ();
        Log.v ( AwajimaLog.LOGTAG,
                CLASSTAG + "getItemCount () returns " + result );
        return result;
    }


    @Override
    public void onAttachedToRecyclerView (@NotNull RecyclerView view)
    {
        super.onAttachedToRecyclerView (view);
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onAttachedToRecyclerView called");
    }

    @Override
    public void onBindViewHolder (@NonNull FenceAd.EventViewHolder holder, int position) {
        fenceEvent = fenceEventArrayList.get(position);
        if(fenceEvent !=null){
            holder.txtEventID.setText (MessageFormat.format("GeoFence ID:{0}", fenceEvent.getEventID()));
            holder.txtEmergType.setText (MessageFormat.format("Type{0}", fenceEvent.getFenceEmergType()));
            holder.txtEmergID.setText (MessageFormat.format("Emerg. Report ID{0}", fenceEvent.getFenceEventEmergID()));
            holder.txtStatus.setText (MessageFormat.format("Status :{0}", fenceEvent.getFenceEventEmergStatus()));
            holder.eventFenceView.setText (fenceEvent.getFence ());
             text = timeFormatter.format (fenceEvent.getTimestamp ());
            text = app.getTransitionString (app.getResources (), fenceEvent.getEventID());

        }

        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onBindViewHolder called");
        //FenceEvent event = events.get (position);
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "Binding event: " + position + " " + fenceEvent);

        holder.eventTimeView.setText (text);
        holder.eventTypeView.setText (text);
    }

    @NonNull
    @Override
    public FenceAd.EventViewHolder onCreateViewHolder (@NonNull ViewGroup parent,
                                                       int viewType ) {
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onCreateViewHolder called");
        View view = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.fence_event, parent, false);
        return new FenceAd.EventViewHolder(view);
    }


    public void update () {
        timeFormatter =
                new SimpleDateFormat("dd-MMM HH:mm", Locale.getDefault());
        List<FenceEvent> appEvents = app.getEvents();
        int numEvents = appEvents.size();
        int eventsSize = events.size();
        if (numEvents > eventsSize) {
            events.addAll(eventsSize, appEvents);
        }
        if ((events.size() != numEvents)) {
            throw new AssertionError("Event adding failed");
        }
    }
}
