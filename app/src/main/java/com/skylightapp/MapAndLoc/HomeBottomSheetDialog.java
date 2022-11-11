package com.skylightapp.MapAndLoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Bookings.TaxiBoookingSheetListener;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeBottomSheetDialog extends Fragment {


    @NotNull
    ArrayList<CarTypes> cars;
    @NotNull
    CarTypes selectedCar;
    TaxiBoookingSheetListener onItemClickListener;
    private AppCompatButton btnCallTaxi;
    private RecyclerView recyclSuggest,recylCar;
    private AppCompatImageView search,priceImg,distanceImg,imgDest,addressStartImg;
    private LinearLayout layoutSearchForTaxis;
    private FrameLayout frameLAddressAndS;
    private ConstraintLayout addressLayout;
    private AppCompatTextView txtTaxiPrice,txtTaxiDistance,taxiArrivalTime,txtTaxiInfo,txtDestAddress,addressStart;

    public HomeBottomSheetDialog(@NotNull TaxiBoookingSheetListener onItemClickListener) {
        super();
        this.onItemClickListener = onItemClickListener;
        //this.cars = kotlin.collections.CollectionsKt.arrayListOf(CarTypes.CLASSIC, CarTypes.CABIN, CarTypes.PREMIUM);
        this.selectedCar = CarTypes.CLASSIC;
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView= inflater.inflate(R.layout.taxi_bs_home, container, false);
        /*driverName = rootView.findViewById(R.id.driver_Taxi_Name);
        driverGender = rootView.findViewById(R.id.taxi_driverGender);
        driverAge = rootView.findViewById(R.id.taxi_driverAge);
        driverCarType = rootView.findViewById(R.id.driverCarType);
        driverVehicle = rootView.findViewById(R.id.driverVehicle);
        driverVehiclePlate = rootView.findViewById(R.id.driverVehiclePlate);

        imgIcon = rootView.findViewById(R.id.locationItemIcon);
        txtLocTitle = rootView.findViewById(R.id.locationItemTitle);
        txtLocItem = rootView.findViewById(R.id.locationItem);*/


        btnCallTaxi = rootView.findViewById(R.id.callTaxi);
        recyclSuggest = rootView.findViewById(R.id.suggesRecy);
        search = rootView.findViewById(R.id.searchIcon_Address);
        priceImg = rootView.findViewById(R.id.price_imageView);
        txtTaxiPrice = rootView.findViewById(R.id.taxi_price);
        txtTaxiDistance = rootView.findViewById(R.id.taxi_distance);

        distanceImg = rootView.findViewById(R.id.distance_imageView);
        taxiArrivalTime = rootView.findViewById(R.id.taxi_arrived_time);
        txtTaxiInfo = rootView.findViewById(R.id.taxi_info);
        txtDestAddress = rootView.findViewById(R.id.address_destination);

        imgDest = rootView.findViewById(R.id.destination_imageView);
        addressStart = rootView.findViewById(R.id.address_start);
        addressStartImg = rootView.findViewById(R.id.start_imageView);

        frameLAddressAndS = rootView.findViewById(R.id.search_and_address_layout);
        layoutSearchForTaxis = rootView.findViewById(R.id.searchLayout_Taxi);
        addressLayout = rootView.findViewById(R.id.address_layout);


        recylCar = rootView.findViewById(R.id.carsRecy);





        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* searchLayout.setOnClickListener(view1 ->
                HomeBottomSheetDialog.this.onItemClickListener.invoke(BottomSheetButtonTypes.FIRST_DESTINATION)
        );
        addressStart.setOnClickListener(view1 ->
                HomeBottomSheetDialog.this.onItemClickListener.invoke(BottomSheetButtonTypes.HOME)
        );
        addressDestination.setOnClickListener(view1 ->
                HomeBottomSheetDialog.this.onItemClickListener.invoke(BottomSheetButtonTypes.DESTINATION)
        );
        callTaxi.setOnClickListener(view1 ->
                HomeBottomSheetDialog.this.onItemClickListener.invoke(BottomSheetButtonTypes.CALL_TAXI)
        );*/
    }

    public void showAddressLayout(Boolean value) {
        if (Boolean.TRUE.equals(value)) {
            addressLayout.setVisibility(View.VISIBLE);
            //searchLayout.setVisibility(View.GONE);
        } else {
            addressLayout.setVisibility(View.GONE);
            //searchLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setStartingAddress(String address) {
        addressStart.setText(address);
    }

    public void setDestinationAddress(String address) {
        //addressDestination.setText(address);
    }

    public void setTaxiInformationMessage(String message) {
        //taxiInfo.setText(message);
    }

    public void setTaxiArrivedTime(String time) {
       // taxiArrivedTime.setText(time);
    }

    public void setTaxiPrice(String price) {
        //taxiPrice.setText("$" + price);
    }

    public void setTaxiDistance(String distance) {
        //taxiDistance.setText(distance);
    }

    public void setSelectedCarType(CarTypes carType) {
        selectedCar = carType;
        setCars(cars);
    }

    public HomeBottomSheetDialog setCars(List<CarTypes> cars) {
        /*if (!cars.isEmpty()) {
            this.cars = (ArrayList<CarTypes>) cars;
            cars.setVisibility(View.VISIBLE);
            cars.setLayoutManager(new GridLayoutManager(this.getContext(), 1, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false));
            cars.setAdapter(new BottomSheetCarsAdapter(
                    cars, selectedCar, (IClickListener<CarTypes>) car -> {
                selectedCar = car;
                HomeBottomSheetDialog.this.onItemClickListener.invoke(BottomSheetButtonTypes.CAR_TYPE_SELECTED);
            }
            ));
        }*/
        return this;
    }
}
