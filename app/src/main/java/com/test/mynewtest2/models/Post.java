package com.test.mynewtest2.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.test.mynewtest2.MainActivity;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Post {

    public String uid;
    public String author;
    public String title;
    public String body;

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();
    public double latitude;
    public double longitude;
    public String pinName;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String body, double latitude, double longitude, String pinName) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pinName = pinName;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("latitude",latitude);
        result.put("longitude",longitude);
        result.put("pinname",pinName);
        result.put("starCount", starCount);
        result.put("stars", stars);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]