package dream.guys.hotdeskandroid.ui.wellbeing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.ActivityFireWardensBinding;

public class FireWardensActivity extends AppCompatActivity {

    ActivityFireWardensBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fire_wardens);
        binding=ActivityFireWardensBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent=getIntent();
        final String receivedActivity=intent.getStringExtra("WELL_BEING_KEY");

        //Set Title Here
        if(receivedActivity.equals("HEALTH_TIPS")){
            binding.activityTitle.setText("Health tips");
            binding.tvReportAnHazzard.setVisibility(View.GONE);
            binding.tvEvacuation.setVisibility(View.GONE);
        }
        else if(receivedActivity.equals("FIRE")){
            //binding.activityTitle.setText("Fire wardens");
        }else if(receivedActivity.equals("FIRST_AID")){
            binding.activityTitle.setText("First aid");
            binding.tvEvacuation.setText("First aid guide");
        }else if(receivedActivity.equals("MENTAL")) {
            binding.activityTitle.setText("Mental health");
            binding.tvReportAnHazzard.setText("Report an issue");
            binding.tvEvacuation.setText("Stress relief tips");
        }





        binding.fireBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
}