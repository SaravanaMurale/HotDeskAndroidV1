package com.brickendon.hdplus.example;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.brickendon.hdplus.R;

public class PointDrawCanvasActivity extends AppCompatActivity {

    PointF pointA=new PointF(800,280);
    PointF pointB=new PointF(490,470);

    LineView lineView;
    RelativeLayout relativeLayout;

    MyCanvasDraw myCanvasDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_draw_canvas);

        relativeLayout=(RelativeLayout)findViewById(R.id.drawLineLayout);

        /*lineView=new LineView(this);
        lineView.setPointA(pointA);
        lineView.setPointB(pointB);
        lineView.draw();*/

        //myCanvasDraw=new MyCanvasDraw(getApplicationContext(), pointList);
        //relativeLayout.addView(myCanvasDraw);




       /* for (int i = 0; i <5 ; i++) {

            for (int j = 0; j <1 ; j++) {
                PointF pointA=new PointF(158,95);
            }

        }*/



       // relativeLayout.addView(lineView);

    }
}