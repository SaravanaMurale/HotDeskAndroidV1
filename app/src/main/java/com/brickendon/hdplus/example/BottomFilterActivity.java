package com.brickendon.hdplus.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.brickendon.hdplus.R;

public class BottomFilterActivity extends AppCompatActivity {

    Button bottmSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_filter);

        bottmSheet=findViewById(R.id.bottmSheet);

        bottmSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callFilterBottomSheet();

            }
        });
    }

    private void callFilterBottomSheet() {



    }
}