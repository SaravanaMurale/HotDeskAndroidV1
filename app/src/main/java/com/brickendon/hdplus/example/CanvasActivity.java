package com.brickendon.hdplus.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.brickendon.hdplus.R;

public class CanvasActivity extends AppCompatActivity{

    ImageView imageView;
    float x=0,y=0 ;
    float dx,dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);


    }

}