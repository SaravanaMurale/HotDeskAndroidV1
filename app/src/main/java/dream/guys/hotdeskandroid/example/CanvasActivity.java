package dream.guys.hotdeskandroid.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import dream.guys.hotdeskandroid.R;

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