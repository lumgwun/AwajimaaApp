package com.skylightapp.MapAndLoc;

import android.graphics.Color;

import com.skylightapp.R;

public enum CarTypes {
    CLASSIC,
    CABIN,
    PREMIUM;


    public static CarTypes getCarType(Integer value) {
        CarTypes carTypes;
        switch (value) {
            case 1:
                carTypes = CarTypes.CLASSIC;
                break;
            case 2:
                carTypes = CarTypes.CABIN;
                break;
            case 3:
                carTypes = CarTypes.PREMIUM;
                break;
            default:
                carTypes = CarTypes.CLASSIC;
        }
        return carTypes;
    }

    public Car getCar() {
        Car car;

        switch (this) {
            case CLASSIC:
                car = new Car(
                        R.drawable.ic_orange_car,
                        R.string.premium,
                        Color.YELLOW,
                        3.50
                );
                break;
            case CABIN:
                car = new Car(
                        R.drawable.ic_taxi,
                        R.string.classic,
                        Color.GRAY,
                        4.50
                );
                break;
            case PREMIUM:
                car = new Car(
                        R.drawable.taxi2,
                        R.string.gold,
                        Color.BLACK,
                        5.50
                );
                break;
            default:
                car = new Car(
                        R.drawable.ic_taxi33,
                        R.string.vip_car,
                        Color.YELLOW,
                        3.50
                );
                break;
        }
        return car;

    }
}
