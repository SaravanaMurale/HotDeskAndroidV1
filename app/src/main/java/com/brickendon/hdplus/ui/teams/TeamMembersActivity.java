package com.brickendon.hdplus.ui.teams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.brickendon.hdplus.adapter.AdapterTeamMembers;
import com.brickendon.hdplus.databinding.ActivityTeamMembersBinding;
import com.brickendon.hdplus.model.response.TeamMembersResponse;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamMembersActivity extends AppCompatActivity {

    ActivityTeamMembersBinding binding;
    AdapterTeamMembers adapterTeamMembers;
    List<TeamMembersResponse> teamMembersResponses = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_team_members);
        binding = ActivityTeamMembersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = TeamMembersActivity.this;

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent intent = getIntent();

        try {

            if (intent!=null){

                String date = intent.getStringExtra("DATE");
                int teamID  = intent.getIntExtra(AppConstants.TEAM_ID,0);

                callTeamMemberStatus(date,teamID);
            }

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void callTeamMemberStatus(String date,int teamID) {
        if (Utils.isNetworkAvailable(context)) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<TeamMembersResponse>> call = apiService.getTeamMembers(date,teamID);
            call.enqueue(new Callback<List<TeamMembersResponse>>() {
                @Override
                public void onResponse(Call<List<TeamMembersResponse>> call, Response<List<TeamMembersResponse>> response) {
//                    Toast.makeText(MainActivity.this, "on res", Toast.LENGTH_SHORT).show();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                    if(response.code()==200){

                        teamMembersResponses = response.body();
                        if (teamMembersResponses!=null &&
                                teamMembersResponses.size()>0){

                            setAdapter();

                        }

                    }else if(response.code()==401){
                        //Handle if token got expired
                        Utils.tokenExpiryAlert(context,"");

                    } else {
                        Log.d("Search", "onResponse: else");
                        Utils.toastShortMessage(TeamMembersActivity.this,"Api Issue Code: "+response.code());
                    }

                }
                @Override
                public void onFailure(Call<List<TeamMembersResponse>> call, Throwable t) {
//                    Toast.makeText(context, "on fail", Toast.LENGTH_SHORT).show();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    Utils.toastShortMessage(TeamMembersActivity.this,"Response Failure: "+t.getMessage());
                    Log.d("Search", "onResponse: fail"+t.getMessage());
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }

    private void setAdapter() {
        adapterTeamMembers = new AdapterTeamMembers(context,teamMembersResponses);
        binding.teamMemberRecyclerview.setAdapter(adapterTeamMembers);
    }

}