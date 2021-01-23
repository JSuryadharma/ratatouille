package com.example.ratatouille.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ratatouille.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ZoomImage extends AppCompatActivity {

    private ImageView zoomBackButton;
    private PhotoView zoomedImage;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);

        zoomBackButton = findViewById(R.id.zoomBackButton);
        zoomedImage = findViewById(R.id.zoomPhoto);
        context = this;

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Glide.with(context).load(url).into(zoomedImage);

        zoomBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}