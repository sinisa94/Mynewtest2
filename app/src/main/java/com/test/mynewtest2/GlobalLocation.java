package com.test.mynewtest2;

public class GlobalLocation {
        public double current_longitude;
        public double current_latitude;

        GlobalLocation(){
            // Default constructor required for calls to DataSnapshot.getValue(Pins.class)
        }

        public GlobalLocation(String uid,double current_longitude, double current_latitude){
            this.current_longitude = current_longitude;
            this.current_latitude = current_latitude;
        }

}
