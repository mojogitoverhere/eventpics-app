package com.example.david.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

public class AlbumViewActivity extends AppCompatActivity {

    EditText pic_url;
    ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);

        // Actual event names for this user
        String username = "david";

        // Setup the event list
        EventAdapter event_adapter = new EventAdapter(username, getApplicationContext());
        LinearLayoutManager event_list_manager = new LinearLayoutManager(this);
        RecyclerView event_list = (RecyclerView)findViewById(R.id.event_list);
        event_list.setLayoutManager(event_list_manager);
        event_list.setAdapter(event_adapter);
        event_list.addItemDecoration(new EventAdapter.ItemDivider(AlbumViewActivity.this));
    }


}
