package com.test.mynewtest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.mynewtest2.models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.R.id.list;

public class ListActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    public List<String> itemsList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private ListView listView;
    private TextView textView4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.listview);
        textView4 = (TextView) findViewById(R.id.textView4);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        listView.setAdapter(adapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = mDatabase.child("posts");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (final DataSnapshot DataSnapshot : snapshot.getChildren()) {
                    //Getting the data from snapshot
                    Post post = DataSnapshot.getValue(Post.class);

                    //Adding it to a string
                    String items = "Title: " + post.title + "\nBody: " + post.body + "\nAuthor: " + post.author +"\n\n";

                    String title = post.title;
                    String body = post.body;
                    String author = post.author;
                    final String uid = post.uid;

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            Object o = listView.getItemAtPosition(position);
                            textView4.setText("title: "+ o  + "pos " + position);
                        }
                    });
                    itemsList.add(items);

                    if(itemsList.size() == 1)
                    {
                        adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,itemsList);
                        listView.setAdapter(adapter);
                    }
                    else if(itemsList.size() > 1)
                    {
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}