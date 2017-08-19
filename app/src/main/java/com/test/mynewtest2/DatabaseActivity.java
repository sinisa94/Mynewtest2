package com.test.mynewtest2;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DatabaseActivity extends AppCompatActivity {
    private TextView uname_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        uname_txt = (TextView) findViewById(R.id.username_txt);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uname_txt.setText(getString(R.string.firebase_status_fmt, user.getEmail()));

        } else {
            uname_txt.setText(null);
        }
    }
}
