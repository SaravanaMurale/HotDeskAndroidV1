package dream.guys.hotdeskandroid.example.karthickcanvascheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.R;

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
                KarthickPoint karthickPoint1=new KarthickPoint(100,200);
                karthickPointList.add(karthickPoint1);
                KarthickPoint karthickPoint2=new KarthickPoint(200,300);
                karthickPointList.add(karthickPoint2);

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

                KarthickCanvasDraw karthickCanvasDraw=new KarthickCanvasDraw(KarthickActivity.this,karthickPointList);
                addCanvasLayout.addView(karthickCanvasDraw);
            }
        });


        System.out.println("One");
        System.out.println("Two");
        System.out.println("Three");
        System.out.println("Five");


    }
}