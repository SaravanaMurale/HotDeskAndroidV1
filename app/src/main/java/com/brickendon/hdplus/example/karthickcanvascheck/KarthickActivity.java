package com.brickendon.hdplus.example.karthickcanvascheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import com.brickendon.hdplus.R;

public class KarthickActivity extends AppCompatActivity {

    Button btn1,btn2;

    RelativeLayout addCanvasLayout;

    List<KarthickPoint> karthickPointList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karthick);

        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        addCanvasLayout=(RelativeLayout) findViewById(R.id.addCanvasLayout);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                KarthickPoint karthickPoint1=new KarthickPoint(158,95);
                karthickPointList.add(karthickPoint1);
                KarthickPoint karthickPoint2=new KarthickPoint(576,100);
                karthickPointList.add(karthickPoint2);
                KarthickPoint karthickPoint3=new KarthickPoint(800,280);
                karthickPointList.add(karthickPoint3);
                KarthickPoint karthickPoint4=new KarthickPoint(490,470);
                karthickPointList.add(karthickPoint4);
                KarthickPoint karthickPoint5=new KarthickPoint(130,430);
                karthickPointList.add(karthickPoint5);


                    KarthickCanvasDraw karthickCanvasDraw=new KarthickCanvasDraw(KarthickActivity.this,karthickPointList);
                    addCanvasLayout.addView(karthickCanvasDraw);



            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KarthickPoint karthickPoint1=new KarthickPoint(300,400);
                karthickPointList.add(karthickPoint1);
                KarthickPoint karthickPoint2=new KarthickPoint(500,600);
                karthickPointList.add(karthickPoint2);
                KarthickPoint karthickPoint3=new KarthickPoint(800,280);
                karthickPointList.add(karthickPoint3);
                KarthickPoint karthickPoint4=new KarthickPoint(490,470);
                karthickPointList.add(karthickPoint4);
                KarthickPoint karthickPoint5=new KarthickPoint(130,430);
                karthickPointList.add(karthickPoint5);

                KarthickCanvasDraw karthickCanvasDraw=new KarthickCanvasDraw(KarthickActivity.this,karthickPointList);
                addCanvasLayout.addView(karthickCanvasDraw);
            }
        });


        System.out.println("One");
        System.out.println("Two");
        System.out.println("Five");
        System.out.println("Six");
        System.out.println("Three");
        System.out.println("Four");
        System.out.println("Nine");
        System.out.println("Ten");
        System.out.println("Seven");
        System.out.println("Eight");



    }
}