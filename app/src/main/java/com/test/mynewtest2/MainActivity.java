package com.test.mynewtest2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btn_login, btn_db, btn_items, btn_newpost, btn_logout, btn_map, btn_test, btn_test2;
    private TextView curr_usr, test_txt;
    private String test;
    private EditText edit_txt;

public static class getLocVal {
    public static double longitude, latitude;
    double[] getVals(double x, double y) {
        double[] longlat = new double[2];
        longlat[0] = x;
        longlat[1] = y;
        longitude = x;
        latitude = y;
        return longlat;
    }
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        btn_login = (Button) findViewById(R.id.button_login);
        btn_logout = (Button) findViewById(R.id.button_logout);
        btn_db = (Button) findViewById(R.id.button_database);
        btn_items = (Button) findViewById(R.id.items_button);
        btn_test = (Button) findViewById(R.id.test_button);
        btn_map = (Button) findViewById(R.id.button_map);
        btn_newpost = (Button) findViewById(R.id.newpost_button);
        curr_usr = (TextView) findViewById(R.id.currentuser_txt);
        test_txt = (TextView) findViewById(R.id.test_text);
        btn_test2 = (Button) findViewById(R.id.button122);
        edit_txt = (EditText) findViewById(R.id.editText);
      final  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        /*if (user != null) {
            btn_login.setText("SignOut");
            curr_usr.setText(getString(R.string.firebase_status_fmt,user.getEmail()));
        }*/
        btn_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });
        btn_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_test2.setText("gCL");
                BaseActivity.getCurrentLocation gCL = new BaseActivity.getCurrentLocation();
                test_txt.setText(Double.toString(gCL.longitude) + "\n\n" + Double.toString(gCL.latitude));
            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_map.setText("MapsActivity");
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TabsActivity.class));
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
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        btn_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null ){
                    startActivity(new Intent(MainActivity.this, ItemsActivity.class));
                }
                else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });

    }


}