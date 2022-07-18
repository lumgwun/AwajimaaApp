package com.skylightapp.RealEstate;

import com.skylightapp.R;

public class PropertiesDetails {
    private PropertiesDetails() {
    }

    public static final Properties[] PROPERTIES = {
            new Properties(R.string.residential,
                    R.string.residential_description,
                    ResPropAct.class),
            new Properties(R.string.Estate,
                    R.string.estate_descript,
                    EstatePropAct.class),
            new Properties(R.string.eventCenter,
                    R.string.eventCenter_description,
                    EventCenterListAct.class),
            new Properties(R.string.officeProp,
                    R.string.officeProp_description,
                    OfficePropAct.class),
            /*new Properties(R.string.hall,
                    R.string.hall_description,
                    HallAct.class),*/

    };
}
