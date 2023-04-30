package com.osamaelkassaby.oe_univ;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.URL;

public class Video_player extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        Uri uri = null;

         String url = getIntent().getStringExtra("path");
        VideoView videoView = findViewById(R.id.videoView);

        MediaController mediaController = new MediaController(Video_player.this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);


        uri = Uri.parse(url);


        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }
}