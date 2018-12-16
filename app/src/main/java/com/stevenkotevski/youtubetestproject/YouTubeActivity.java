package com.stevenkotevski.youtubetestproject;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * The purpose of this class is to act as the driver for the YouTube Video Mini-App.
 * In order to have the data of the ArrayList saved when exiting out of the app, I used
 * SharedPreferences and created a saveData() and loadData() function for saving and loading
 * videoID's into the list.
 * The decision to use a RecyclerView was so that all of the videos can be displayed neatly
 * and I wouldn't have to worry about initializing all of them at once, instead they only can be
 * initialized and played when clicked on.
 *
 */

public class YouTubeActivity extends Activity {

    ArrayList<String> arrList = new ArrayList<>();
    // this ArrayList is used with the VideoID ArrayList constantly, to fetch and send the videoID's
    // whenever the app is opened or closed



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.youtube_recycler_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        // Using LinearLayoutManager to handle the layout of the RecyclerView:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        final YouTubeAdapter adapter = new YouTubeAdapter(YouTubeActivity.this);
        recyclerView.setAdapter(adapter);

        // Obtaining data from the last time when the app was saved,
        // and setting the ArrayList in the adapter class to that.
        loadData();
        adapter.setListContents(arrList);

        // Button used to add a video to the VideoID ArrayList
        Button addVideo = (Button) findViewById(R.id.addVideo);

        // EditText which has the URL of the video that the user is going to add.
        final EditText userURL = (EditText) findViewById(R.id.videoURL);


        // When add is clicked, it parses the EditText into a String and calls the addVideo method of the adapter class
        addVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String url = userURL.getText().toString(); //gets you the contents of edit text
                adapter.addVideo(url);
                adapter.copyListContents(arrList);
            }
        });


        // Must call the save button to save the data of the ArrayList
        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    // Using SharedPreferences and Gson, it saves the arraylist as String version of a json representation of the ArrayList
    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrList);
        editor.putString("task list", json);
        editor.apply();
    }

    // Fetching the data from the sharedPreferences created by saveData
    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        arrList = gson.fromJson(json, type);

        // If saveData was never called, then it would be null, so it must create a new ArrayList
        if(arrList == null){
            arrList = new ArrayList<>();
        }
    }

}

