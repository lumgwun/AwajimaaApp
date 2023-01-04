package com.skylightapp.Bookings;

public enum TripStatus {
    UNKNOWN_TRIP_STATUS {
        @Override
        public TripStatus getNextStatus() {
            return this;
        }

        @Override
        public int getCode() {
            return UNKNOWN_CODE;
        }
    },
    NEW {
        @Override
        public TripStatus getNextStatus() {
            return ENROUTE_TO_PICKUP;
        }

        @Override
        public int getCode() {
            return NEW_TRIP_CODE;
        }
    },
    ENROUTE_TO_PICKUP {
        @Override
        public TripStatus getNextStatus() {
            return ARRIVED_AT_PICKUP;
        }

        @Override
        public int getCode() {
            return ENROUTE_TO_PICKUP_CODE;
        }
    },
    ARRIVED_AT_PICKUP {
        @Override
        public TripStatus getNextStatus() {
            return ENROUTE_TO_DROPOFF;
        }

        @Override
        public int getCode() {
            return ARRIVED_AT_PICKUP_CODE;
        }
    },
    ENROUTE_TO_DROPOFF {
        @Override
        public TripStatus getNextStatus() {
            return COMPLETE;
        }

        @Override
        public int getCode() {
            return ENROUTE_TO_DROPOFF_CODE;
        }
    },
    COMPLETE {
        @Override
        public TripStatus getNextStatus() {
            return this;
        }

        @Override
        public int getCode() {
            return COMPLETE_CODE;
        }
    },
    CANCELED {
        @Override
        public TripStatus getNextStatus() {
            return this;
        }

        @Override
        public int getCode() {
            return CANCELED_CODE;
        }
    };


    private static final int UNKNOWN_CODE = 0;
    private static final int NEW_TRIP_CODE = 1;
    private static final int ENROUTE_TO_PICKUP_CODE = 2;
    private static final int ARRIVED_AT_PICKUP_CODE = 3;
    private static final int ENROUTE_TO_DROPOFF_CODE = 4;
    private static final int COMPLETE_CODE = 5;
    private static final int CANCELED_CODE = 6;

    public abstract TripStatus getNextStatus();

    public abstract int getCode();


    public static int parse(String status) {
        TripStatus statusEnum = valueOf(status);
        return statusEnum.getCode();
    }
}
