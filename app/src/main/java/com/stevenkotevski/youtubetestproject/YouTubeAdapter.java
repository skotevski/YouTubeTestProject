package com.stevenkotevski.youtubetestproject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The purpose of this class is to essentially just handle everything with the YouTube videos.
 * It handles adding them into the RecyclerView, and it handles the dynamically changing ArrayList
 * of YouTube Videos. This class also handles the initialization and playing of the YouTube videos as well.
 */

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.VideoInfoHolder> {

    //sample video ids to put into the ArrayList:
    ArrayList<String> VideoID = new ArrayList<>(Arrays.asList("vKSA_idPZkc", "9m_K2Yg7wGQ", "5_-NKRVn7IQ"));
    Context ctx;

    public YouTubeAdapter(Context context) {
        this.ctx = context; // fetching the current Context
    }



    public void addVideo(String url){
        // Before we add the video to the arraylist, we must parse the link to be the videoID:

        if(url.contains("https://youtu.be/")) //The url begins with this form when copying through the YouTube app in a phone
        {
            url = url.replace("https://youtu.be/", ""); // deleting that part of the url
        }
        else if(url.contains("https://www.youtube.com/watch?v="))   // The url begins with this form when copying through a computer
        {
            url = url.replace("https://www.youtube.com/watch?v=", ""); // Deleting that part
        }
        else // The ID is normal
        {
            url = url; // If it doesn't begin with either, then we assume that the videoID is okay, and we add it as is.
        }
        VideoID.add(url);
    }




    // This method copies the contents of the VideoID ArrayList into the ArrayList entered as a parameter.
    // The purpose of it is to be able to fetch and save the values of this arraylist later.
    public void copyListContents(ArrayList<String> arrList)
    {
        arrList = (ArrayList<String>)VideoID.clone();
    }

    // This method is able to set and change the VideoID list into the ArrayList entered as a parameter
    // The purpose is so that when the saved arraylist is fetched, we can update the arraylist in the adapter through here.
    public void setListContents(ArrayList<String> arrList)
    {
        VideoID = arrList;
    }


    //Override methods:

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_list_item, parent, false);
        return new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {


        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        holder.youTubeThumbnailView.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(VideoID.get(position));
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                // Wasn't able to figure out a way to handle failure
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return VideoID.size();
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
        }

        @Override
        public void onClick(View v) {

            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, DeveloperKey.DEVELOPER_KEY, VideoID.get(getLayoutPosition()));
            ctx.startActivity(intent); // Actually playing the YouTube Video
        }
    }
}