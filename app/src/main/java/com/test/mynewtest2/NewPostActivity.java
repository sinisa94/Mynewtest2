package com.test.mynewtest2;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.mynewtest2.fragment.MapFragment;
import com.test.mynewtest2.models.Post;
import com.test.mynewtest2.models.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class NewPostActivity extends BaseActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;
    private TextView loc_info_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        //  Write the location name.
        //
        loc_info_txt = (TextView) findViewById(R.id.loc_info_text);
        getLocationValue glx = new getLocationValue();
        final double longitude = glx.longitude;
        final double latitude = glx.latitude;

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation( longitude,latitude, 1);

            if(addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                //loc_info_txt.setText(strReturnedAddress.toString());
                //TODO check if multiple adresses exist for same geolocation.
                loc_info_txt.setText(returnedAddress.getAddressLine(0));
            }
            else{
                loc_info_txt.setText("No Address returned!");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            loc_info_txt.setText("Canont get Address!");
        }

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]


        mTitleField = (EditText) findViewById(R.id.field_title);
        mBodyField = (EditText) findViewById(R.id.field_body);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_post);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }


    public static class getLocationValue {
        public static double longitude, latitude;
        public double[] getValues(double x, double y) {
            double[] longlat = new double[2];
            longlat[0] = x;
            longlat[1] = y;
            longitude = x;
            latitude = y;
            return longlat;
        }
    }


    private void submitPost() {
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);
                        getCurrentLocation gCL = new getCurrentLocation();
                        //MainActivity.getLocVal glv = new MainActivity.getLocVal();
                        final double longitude = gCL.longitude;
                        final double latitude = gCL.latitude;
                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewPostActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.username, title, body, longitude, latitude);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String title, String body, double longitude, double latitude) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body, longitude, latitude);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}
