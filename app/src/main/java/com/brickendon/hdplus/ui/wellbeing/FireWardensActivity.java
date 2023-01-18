package com.brickendon.hdplus.ui.wellbeing;

import static com.brickendon.hdplus.utils.Utils.getAppKeysPageScreenData;
import static com.brickendon.hdplus.utils.Utils.getCurrentDate;
import static com.brickendon.hdplus.utils.Utils.getWellBeingScreenData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.adapter.FireWardensAdapter;
import com.brickendon.hdplus.databinding.ActivityFireWardensBinding;
import com.brickendon.hdplus.model.language.LanguagePOJO;
import com.brickendon.hdplus.model.response.DAOTeamMember;
import com.brickendon.hdplus.model.response.FirstAidResponse;
import com.brickendon.hdplus.ui.teams.ShowProfileActivity;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.ExtendedDataHolder;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FireWardensActivity extends AppCompatActivity implements FireWardensAdapter.ViewPersonDetailCliclable {

    ActivityFireWardensBinding binding;

    LanguagePOJO.WellBeing wellBeingPage;
    LanguagePOJO.AppKeys appKeysPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_fire_wardens);
        binding = ActivityFireWardensBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setLanguage();


        Intent intent = getIntent();
        final String receivedActivity = intent.getStringExtra("WELL_BEING_KEY");

        //Set Title Here
        if (receivedActivity.equals("HEALTH_TIPS")) {
            //binding.activityTitle.setText("Health tips");
            //binding.headFire.setText("Health tips");
            binding.activityTitle.setText(appKeysPage.getHealthTips());
            binding.headFire.setText(appKeysPage.getHealthTips());
            binding.tvReportAnHazzard.setVisibility(View.GONE);
            binding.tvEvacuation.setVisibility(View.GONE);
        } else if (receivedActivity.equals("FIRE")) {
            //binding.activityTitle.setText("Fire wardens");
            /*binding.headFire.setText("Fire wardens");
            binding.titleFire.setText("Fire wardens");*/

            //binding.headFire.setText(appKeysPage.getFireWarden());

            binding.subTitleFire.setText(dateFormat());
            getFirstAidPersonsDetails(4);
        } else if (receivedActivity.equals("FIRST_AID")) {
            //binding.activityTitle.setText("First aid");
            binding.activityTitle.setText(appKeysPage.getFirstAid());
            Glide.with(this)
                    .load(R.drawable.plus)
                    .into(binding.profileUpdate);

            binding.tvEvacuation.setText("First aid guide");
            //binding.headFire.setText("First Aid");
            // binding.headFire.setText(appKeysPage.getFirstAid());
            binding.headFire.setText("Your First aiders");
           // binding.titleFire.setText("Adminss");
            binding.subTitleFire.setText(dateFormat());
            getFirstAidPersonsDetails(5);

        } else if (receivedActivity.equals("MENTAL")) {
            //binding.activityTitle.setText("Mental health");
            //binding.headFire.setText("Mental health");
            //binding.tvReportAnHazzard.setText("Report an issue");

            binding.activityTitle.setText(appKeysPage.getMentalHealth());
            binding.headFire.setText(appKeysPage.getMentalHealth());
            binding.tvReportAnHazzard.setText(appKeysPage.getReportAnIssue());

            binding.tvEvacuation.setText("Stress relief tips");
        }


        binding.fireBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void getFirstAidPersonsDetails(int description) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<FirstAidResponse>> call = apiService.getFirstAidResponse();
        call.enqueue(new Callback<List<FirstAidResponse>>() {
            @Override
            public void onResponse(Call<List<FirstAidResponse>> call, Response<List<FirstAidResponse>> response) {

                List<FirstAidResponse> firstAidResponseList = response.body();

                List<FirstAidResponse.Persons> personsList = new ArrayList<>();


                for (int i = 0; i < firstAidResponseList.size(); i++) {
                    if (firstAidResponseList.get(i).getPersonsList().size() > 0) {

                        if (firstAidResponseList.get(i).getType() == description) {
                            for (int j = 0; j < firstAidResponseList.get(i).getPersonsList().size(); j++) {
                                personsList.add(firstAidResponseList.get(i).getPersonsList().get(j));
                                System.out.println("FirstAidFullName" + firstAidResponseList.get(i).getPersonsList().get(j).getFullName());
                            }
                        }
                    }
                }

                callSetAdapter(personsList, description);
            }

            @Override
            public void onFailure(Call<List<FirstAidResponse>> call, Throwable t) {

            }
        });

    }

    private void callSetAdapter(List<FirstAidResponse.Persons> personsList, int description) {
        FireWardensAdapter fireWardensAdapter = new FireWardensAdapter(FireWardensActivity.this, personsList, description, this);
        binding.rvFireWardens.setAdapter(fireWardensAdapter);
    }

    @Override
    public void viewPersonClick(FirstAidResponse.Persons persons) {


        Calendar startDate = Calendar.getInstance();

        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String currendate = String.valueOf(year + "-" + (month + 1) + "-" + day);

        try {
            currendate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new SimpleDateFormat("yyyy-M-d").parse(currendate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DAOTeamMember daoTeamMember = new DAOTeamMember();
        daoTeamMember.setFirstName(persons.getFullName());
        daoTeamMember.setUserId(persons.getId());


        Intent intent = new Intent(FireWardensActivity.this, ShowProfileActivity.class);
        String personJsonString = new Gson().toJson(daoTeamMember);
        ExtendedDataHolder extras = ExtendedDataHolder.getInstance();
        extras.putExtra(AppConstants.USER_CURRENT_STATUS, personJsonString);
        //  intent.putExtra(AppConstants.USER_CURRENT_STATUS,daoTeamMember);
        intent.putExtra("DATE", currendate);
        startActivity(intent);

    }

    @Override
    public void loadLocateFromFireWardens() {

    }

    private void setLanguage() {
        wellBeingPage = getWellBeingScreenData(this);
        appKeysPage = getAppKeysPageScreenData(this);

    }

    private String dateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(getCurrentDate());
            sdf.applyPattern("EEE, dd MMM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(d);
    }
}