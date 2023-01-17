package com.brickendon.hdplus.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.brickendon.hdplus.R;

public class MutipleImageLoadActivity extends AppCompatActivity {

    LinearLayout addLinearLayout;
    ImageView dessAva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutiple_image_load);

        addLinearLayout=(LinearLayout)findViewById(R.id.addLinearLayout);
        dessAva=(ImageView) findViewById(R.id.dessAva);


        for (int i = 0; i <2 ; i++) {
            addImageView(i);
        }


      /*  for (int i = 0; i <3 ; i++) {
            addView(i);
        }*/


    }

    private void addImageView(int i) {

        if (i == 1){
            RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout.leftMargin = 200;
        relativeLayout.topMargin = 300;
        dessAva.setLayoutParams(relativeLayout);
    }

        if(i==2){
            RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeLayout.leftMargin = 50;
            relativeLayout.topMargin = 100;
            dessAva.setLayoutParams(relativeLayout);
        }

    }

    private void addView(int i) {

        //x---->

        //y
        //  |
        //  |
        //  |

        View deskView = getLayoutInflater().inflate(R.layout.layout_item_desk, null, false);
        ImageView iv1=deskView.findViewById(R.id.ivDesk);

        if(i==0) {

            iv1.setX(5);
            iv1.setY(10);
           iv1.setPadding(10,10,10,10);

            //addLinearLayout.addView(deskView);

        }


        if(i==1) {
           // ImageView iv1=deskView.findViewById(R.id.deskkk);
            iv1.setX(100);
            iv1.setY(50);
            iv1.setPadding(20,20,20,20);

            //addLinearLayout.addView(deskView);

        }

        if(i==2) {
           // ImageView iv1=deskView.findViewById(R.id.deskkk);
            iv1.setX(200);
            iv1.setY(30);

           iv1.setPadding(20,20,20,20);

            //addLinearLayout.addView(deskView);

        }

        addLinearLayout.addView(deskView);



    }
}