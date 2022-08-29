package dream.guys.hotdeskandroid.ui.wellbeing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.adapter.FireWardensAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityFireWardensBinding;
import dream.guys.hotdeskandroid.model.response.FirstAidResponse;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            binding.headFire.setText("Health tips");
            binding.tvReportAnHazzard.setVisibility(View.GONE);
            binding.tvEvacuation.setVisibility(View.GONE);
        }
        else if(receivedActivity.equals("FIRE")){
            //binding.activityTitle.setText("Fire wardens");
            binding.headFire.setText("Fire wardens");
            binding.titleFire.setText("Fire wardens");
            binding.subTitleFire.setText("Persons");
            getFirstAidPersonsDetails("Firewardenss");
        }else if(receivedActivity.equals("FIRST_AID")){
            binding.activityTitle.setText("First aid");
            binding.tvEvacuation.setText("First aid guide");
            binding.headFire.setText("First Aid");
            binding.titleFire.setText("Adminss");
            binding.subTitleFire.setText("Persons");
            getFirstAidPersonsDetails("Adminss");
            
        }else if(receivedActivity.equals("MENTAL")) {
            binding.activityTitle.setText("Mental health");
            binding.headFire.setText("Mental health");
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

    private void getFirstAidPersonsDetails(String description) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<FirstAidResponse>> call = apiService.getFirstAidResponse();
        call.enqueue(new Callback<List<FirstAidResponse>>() {
            @Override
            public void onResponse(Call<List<FirstAidResponse>> call, Response<List<FirstAidResponse>> response) {

                List<FirstAidResponse> firstAidResponseList=response.body();

                List<FirstAidResponse.Persons> personsList=new ArrayList<>();


                    for (int i = 0; i < firstAidResponseList.size(); i++) {
                        if (firstAidResponseList.get(i).getPersonsList().size()>0) {

                            if(firstAidResponseList.get(i).getDescription().equals(description)){
                                for (int j = 0; j <firstAidResponseList.get(i).getPersonsList().size() ; j++) {
                                    personsList.add(firstAidResponseList.get(i).getPersonsList().get(j));
                                    System.out.println("FirstAidFullName" + firstAidResponseList.get(i).getPersonsList().get(j).getFullName());
                                }
                            }



                    } }

                for (int i = 0; i <personsList.size() ; i++) {
                    System.out.println("OnlyPersonList"+personsList.get(i).getFullName());
                }



                FireWardensAdapter fireWardensAdapter=new FireWardensAdapter(FireWardensActivity.this,personsList,description);
                binding.rvFireWardens.setAdapter(fireWardensAdapter);

            }

            @Override
            public void onFailure(Call<List<FirstAidResponse>> call, Throwable t) {

            }
        });

    }
}