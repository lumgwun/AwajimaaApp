package com.skylightapp.MarketPlaceTransport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LocationSelecAct extends AppCompatActivity implements PlaceSelectionListener {
    private List<Places> myPlacesList=new ArrayList<>( );
    private RecyclerView placesRecyclerView;
    private PlacesAdapter mPlacesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_location_selec);
        setTitle("Place Selection");

        //Class Place Adapter
        mPlacesAdapter=new PlacesAdapter( myPlacesList );

        //Recyclar view for Places
        placesRecyclerView=(RecyclerView) findViewById( R.id.placesRecyclerView) ;

        //RecyclerView Animation..
        //placesRecyclerView.setHasFixedSize( true );
        RecyclerView.LayoutManager mlayoutManager=new LinearLayoutManager( getApplicationContext() );
        placesRecyclerView.setLayoutManager( mlayoutManager );
        placesRecyclerView.setItemAnimator( new DefaultItemAnimator() );
        placesRecyclerView.setAdapter( mPlacesAdapter );

        placesData();


    }

    private void placesData(){
        Places place=new Places( "Cyberia Smart Homes","Cyberjaya","10.2KM" );
        myPlacesList.add(place);

        place=new Places( "Cyberia Smart Homes,Block A1","Cyberjaya,Selangor","5.2KM" );
        myPlacesList.add(place);


    }

    @Override
    public void onError(Status status) {

    }

    @Override
    public void onPlaceSelected(@NonNull @NotNull com.google.android.libraries.places.api.model.Place place) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}