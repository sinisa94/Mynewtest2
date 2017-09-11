package com.test.mynewtest2;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.test.mynewtest2.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;
    private FirebaseAuth mAuth;

    String[] items = new String[] {"Login","Logout","etc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mainListView = (ListView) findViewById( R.id.mainListView );
        mAuth = FirebaseAuth.getInstance();
        ArrayList<String> itemsList = new ArrayList<String>();
        itemsList.addAll( Arrays.asList(items) );
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsList);
        mainListView.setAdapter( listAdapter );

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch(position) {
                    case 0:
                        if(mAuth.getCurrentUser() == null){
                        startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                        }
                        else {
                            Snackbar.make(view, "You're already logged in", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        return;
                    case 1:
                        if (mAuth.getCurrentUser() != null) {
                            mAuth.signOut();
                            Snackbar.make(view, "You have successfully signed out", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                            else {
                            Snackbar.make(view, "You're not logged in", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        return;
                    case 2:
                        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                        return;
                }
            }
        });
    }
}
