package dream.guys.hotdeskandroid.ui.teams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dream.guys.hotdeskandroid.databinding.ActivityTeamsUserBinding;

public class TeamsUserActivity extends AppCompatActivity {

    ActivityTeamsUserBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_teams_user);

        binding = ActivityTeamsUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = TeamsUserActivity.this;

        binding.viewAllUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UpComingBookingActivity.class);
                startActivity(intent);
            }
        });

    }
}