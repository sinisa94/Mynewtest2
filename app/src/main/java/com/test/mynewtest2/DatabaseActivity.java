package com.test.mynewtest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.mynewtest2.models.Post;
import com.test.mynewtest2.models.User;

import java.util.HashMap;
import java.util.Map;

public class DatabaseActivity extends BaseActivity {
    private TextView uname_txt;
    private DatabaseReference mDatabase;
    private EditText name_text_input,body_text_input;
    private Button add_btn;
    private static final String REQUIRED = "Required";
    private static final String TAG = "DatabaseActivity";

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        uname_txt = (TextView) findViewById(R.id.username_txt);
        add_btn = (Button) findViewById(R.id.button_add);
        name_text_input = (EditText) findViewById(R.id.name_txt_input);
        body_text_input = (EditText) findViewById(R.id.body_txt_input);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPost();
            }
        });
    }

    private void submitPost() {
        final String item_name = name_text_input.getText().toString();
        final String item_body = body_text_input.getText().toString();
       // test purp only mDatabase.setValue(name_text_input);
        // Title is required
        if (TextUtils.isEmpty(item_name)) {
            name_text_input.setError(REQUIRED);
            return;
        }
        // Body is required
        if (TextUtils.isEmpty(item_body)) {
            body_text_input.setError(REQUIRED);
            return;
        }

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value

                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(DatabaseActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            uname_txt.setText(user.username);
                            writeNewPost(userId, user.username, item_name, item_body);
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
        name_text_input.setEnabled(enabled);
        body_text_input.setEnabled(enabled);
        if (enabled) {
            add_btn.setVisibility(View.VISIBLE);
        } else {
            add_btn.setVisibility(View.GONE);
        }
    }

    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        MainActivity.getLocVal glv = new MainActivity.getLocVal();
        double longitude = glv.longitude;
        double latitude = glv.latitude;
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