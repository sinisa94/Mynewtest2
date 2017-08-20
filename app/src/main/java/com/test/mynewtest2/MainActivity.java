package com.test.mynewtest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.test.mynewtest2.models.User;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btn_login,btn_db,btn_items,btn_newpost,btn_logout;
    private TextView curr_usr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        btn_login = (Button) findViewById(R.id.button_login);
        btn_logout = (Button) findViewById(R.id.button_logout);
        btn_db = (Button) findViewById(R.id.button_database);
        btn_items = (Button) findViewById(R.id.items_button);
        btn_newpost = (Button) findViewById(R.id.newpost_button);
        curr_usr = (TextView) findViewById(R.id.currentuser_txt);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();;
        if (mAuth.getCurrentUser() != null) {
            btn_login.setText("You're allready signed in");
            curr_usr.setText(getString(R.string.firebase_status_fmt,user.getEmail()));
            }
        btn_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ItemsActivity.class));
            }
        });
        btn_newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewPostActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        btn_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DatabaseActivity.class));
            }
        });

        }




    }
