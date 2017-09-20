package com.test.mynewtest2.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Sinisa on 28.08.2017..
 */
// [START post_class]
@IgnoreExtraProperties
public class Pins {
    public String uid;
    public double latitude;
    public double longitude;
    public String pinName;


    Pins(){
        // Default constructor required for calls to DataSnapshot.getValue(Pins.class)
    }

    public Pins(String uid, double latitude, double longitude, String pinName){
        this.uid = uid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pinName = pinName;
    }

}
