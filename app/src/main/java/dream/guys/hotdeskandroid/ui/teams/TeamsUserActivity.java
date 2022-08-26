package dream.guys.hotdeskandroid.ui.teams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.adapter.AdapterAllTeams;
import dream.guys.hotdeskandroid.databinding.ActivityTeamsUserBinding;
import dream.guys.hotdeskandroid.model.response.TeamsResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsUserActivity extends AppCompatActivity {

    ActivityTeamsUserBinding binding;
    Context context;

    List<TeamsResponse> teamsResponseList = new ArrayList<>();
    AdapterAllTeams adapterAllTeams;

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

    private void getTeams() {
        binding.locateProgressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TeamsResponse>> call = apiService.getTeams();
        call.enqueue(new Callback<List<TeamsResponse>>() {
            @Override
            public void onResponse(Call<List<TeamsResponse>> call, Response<List<TeamsResponse>> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                if (response.body()!=null){

                    if (response.code() == 200) {
                        teamsResponseList = response.body();

                        if (teamsResponseList!=null && teamsResponseList.size()>0){
                            adapterAllTeams = new AdapterAllTeams(TeamsUserActivity.this,teamsResponseList);
                            binding.teamsRecyclerview.setAdapter(adapterAllTeams);
                        }

                    }else if(response.code() == 401){
                        Utils.showCustomTokenExpiredDialog(TeamsUserActivity.this,"Token Expired");
                        SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK,false);

                    }else {
                        Toast.makeText(TeamsUserActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<List<TeamsResponse>> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

}