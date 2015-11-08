package com.example.david.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ViewPhotosActivity extends AppCompatActivity {

    RecyclerView photo_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);

        photo_list = (RecyclerView)findViewById(R.id.photo_list);

        // Extract the event data
        Intent event_intent = getIntent();
        Event event = event_intent.getParcelableExtra("Event Data");

        // Setup the recycler view
        PhotoAdapter adapter = new PhotoAdapter(event.photos);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        photo_list.setAdapter(adapter);
        photo_list.setLayoutManager(manager);
    }

}
