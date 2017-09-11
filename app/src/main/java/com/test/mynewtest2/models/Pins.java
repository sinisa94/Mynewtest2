package com.test.mynewtest2.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Sinisa on 28.08.2017..
 */
// [START post_class]
@IgnoreExtraProperties
public class Pins {
    public String uid;
    public double longitude;
    public double latitude;

    Pins(){
        // Default constructor required for calls to DataSnapshot.getValue(Pins.class)
    }

    public Pins(String uid,double longitude, double latitude){
        this.uid = uid;
        this.longitude = longitude;
        this.latitude = latitude;
    }

}
