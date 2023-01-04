package com.skylightapp.Bookings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.skylightapp.Bookings.TaxiBoookingSheetListener;
import com.skylightapp.Bookings.TaxiTrip;
import com.skylightapp.Bookings.TaxiTripListener;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;


public class TripBottomSheetDialog extends Fragment {
    private TaxiTrip trip;
    AppCompatImageView imgIcon;

    private TaxiTripListener tripListener;
    private AppCompatTextView txtLocItem,txtLocTitle,driverName,driverGender,driverAge,driverCarType,driverVehicle,driverVehiclePlate;

    public TripBottomSheetDialog(TaxiTrip trip, TaxiBoookingSheetListener tripListener) {
        super();
        this.trip = trip;
        //this.tripListener = tripListener;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.taxi_trip_sheet, container, false);
        driverName = rootView.findViewById(R.id.driver_Taxi_Name);
        driverGender = rootView.findViewById(R.id.taxi_driverGender);
        driverAge = rootView.findViewById(R.id.taxi_driverAge);
        driverCarType = rootView.findViewById(R.id.driverCarType);
        driverVehicle = rootView.findViewById(R.id.driverVehicle);
        driverVehiclePlate = rootView.findViewById(R.id.driverVehiclePlate);

        imgIcon = rootView.findViewById(R.id.locationItemIcon);
        txtLocTitle = rootView.findViewById(R.id.locationItemTitle);
        txtLocItem = rootView.findViewById(R.id.locationItem);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUi();
    }

    public void setupUi() {
        setDriverData();
        setLocationData();
        //tripListener.invoke(TaxiTripListener.START_TRIP);
    }

    @SuppressLint("StringFormatInvalid")
    private void setDriverData() {
        /*driverName.setText(

                Html.fromHtml(
                        getString(
                                R.string.driver_name,
                                trip.getDriver().getDriverName() + " " + trip.getDriver().getDriverSurname()
                        ), Html.FROM_HTML_MODE_LEGACY
                )
        );
        driverGender.setText(
                Html.fromHtml(
                        getString(
                                R.string.driver_gender,
                                Gender.getGenderText(trip.getDriver().getDriverGender())
                        ), Html.FROM_HTML_MODE_LEGACY
                )
        );
        driverAge.setText(
                Html.fromHtml(
                        getString(R.string.driver_age, trip.getDriver().getDriverAge().toString()),
                        Html.FROM_HTML_MODE_LEGACY
                )
        );
        driverCarType.setText(
                Html.fromHtml(
                        getString(
                                R.string.driver_car_type,
                                CarTypes.getCarType(trip.getDriver().getDriverCarType()).getCar().getTitle().toString()
                        ), Html.FROM_HTML_MODE_LEGACY)
        );
        driverVehicle.setText(
                Html.fromHtml(
                        getString(R.string.driver_car, trip.getDriver().getDriverCarModel()),
                        Html.FROM_HTML_MODE_LEGACY
                )
        );
        driverVehiclePlate.setText(
                Html.fromHtml(
                        getString(R.string.driver_car_plate, trip.getDriver().getDriverCarPlate()),
                        Html.FROM_HTML_MODE_LEGACY
                )
        );*/
    }

    private void setLocationData() {
        imgIcon.setImageResource(R.drawable.location__start);
        txtLocTitle.setText(getString(R.string.pick_up));
        //txtLocItem.setText(trip.getRoute().paths.get(0).startAddress);
        imgIcon.setImageResource(R.drawable.location__end);
        txtLocTitle.setText(getString(R.string.drop_off));
        //txtLocItem.setText(trip.getRoute().paths.get(0).endAddress);

        /*startLocation.locationItemIcon.setImageResource(R.drawable.location__start);
        startLocation.locationItemTitle.setText(getString(R.string.pick_up));
        startLocation.locationItem.setText(trip.getRoute().paths.get(0).startAddress);
        endLocation.locationItemIcon.setImageResource(R.drawable.location__end);
        endLocation.locationItemTitle.setText(getString(R.string.drop_off));
        endLocation.locationItem.setText(trip.getRoute().paths.get(0).endAddress);*/
    }

    public void setStepData(int index) {
        /*if (index < trip.getRoute().paths.get(0).steps.size()) {
            TaxiStep step = trip.getRoute().paths.get(0).steps.get(index);
            stepInstruction.setText(step.instruction);
            stepRoadName.setText(step.roadName);
            stepDistanceTime.setText(step.distanceText + " - " + step.durationText);
            stepActionIcon.setImageResource(RouteAction.getIconByAction(step.action));*/
        }

}
