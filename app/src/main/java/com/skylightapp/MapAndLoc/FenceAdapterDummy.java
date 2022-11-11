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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FenceAdapterDummy extends RecyclerView.Adapter<FenceAdapterDummy.EventViewHolder> {

    private static final String CLASSTAG =
            " " + FenceAdapterDummy.class.getSimpleName () + " ";

    class EventViewHolder extends RecyclerView.ViewHolder {
        private final String CLASSTAG =
                " " + FenceAdapterDummy.class.getSimpleName () + "." +
                        EventViewHolder.class.getSimpleName () + " ";

        AppCompatTextView eventTimeView,eventTypeView,eventFenceView;


        EventViewHolder (View view)
        {
            super (view);
            Log.v (AwajimaLog.LOGTAG, CLASSTAG + "Constructor called");
            eventTimeView   = view.findViewById (R.id.event_time);
            eventFenceView  = view.findViewById (R.id.event_fence);
            eventTypeView   = view.findViewById (R.id.event_type);
        }

    }

    private @NonNull
    List<FenceEvent> events;
    private SimpleDateFormat timeFormatter;


    private @NonNull
    AppController app;

    @Override
    public int getItemCount ()
    {
        int result = events.size ();
        Log.v ( AwajimaLog.LOGTAG,
                CLASSTAG + "getItemCount () returns " + result );
        return result;
    }

    FenceAdapterDummy(@NotNull AppController theApp)
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
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "Binding event: " + i + " " + event);
        String text = timeFormatter.format (event.getTimestamp ());
        holder.eventTimeView.setText (text);
        holder.eventFenceView.setText (event.getFence ());
        text = app.getTransitionString (app.getResources (), event.getEventID());
        holder.eventTypeView.setText (text);
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
  int numEvents = appEvents.size ();
  int eventsSize = events.size ();
  if (numEvents > eventsSize) {
    events.addAll (eventsSize, appEvents);
  }
  if ((events.size () != numEvents))
  { throw new AssertionError ("Event adding failed"); }
}

}
