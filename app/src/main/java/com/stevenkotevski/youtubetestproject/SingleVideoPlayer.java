package com.stevenkotevski.youtubetestproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleVideoPlayer extends YouTubeFailureRecoveryActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playerview_demo);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo("wKJ9KzGQq0w");




            // my shit
            String str = "https://www.youtube.com/watch?v=QpLve6zOFQY";
            String strNew = "";
            if(str.contains("https://youtu.be/"))
            {
                strNew = str.replace("https://youtu.be/", ""); // strNew should now be video ID.
            }
            else if(str.contains("https://www.youtube.com/watch?v="))
            {
                strNew = str.replace("https://www.youtube.com/watch?v=", ""); // strNew should now be video ID.
            }
            else
            {
                strNew = str;
            }
            System.out.println(strNew);

        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}
