package dream.guys.hotdeskandroid.ui.wellbeing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.ActivityFireWardensBinding;

public class FireWardensActivity extends AppCompatActivity {

    ActivityFireWardensBinding activityFireWardensBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fire_wardens);
        activityFireWardensBinding=ActivityFireWardensBinding.inflate(getLayoutInflater());
        setContentView(activityFireWardensBinding.getRoot());




    }
}