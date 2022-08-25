package dream.guys.hotdeskandroid.ui.wellbeing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.CovidCertificationAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityCovidCertificationBinding;
import dream.guys.hotdeskandroid.model.request.CovidAnswerRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.CovidQuestionsResponse;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidCertificationActivity extends AppCompatActivity {

    ActivityCovidCertificationBinding binding;
    CovidCertificationAdapter covidCertificationAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_covid_certification);

        //https://dev-api.hotdeskplus.com/api/wellness/CovidSelfCertificationdQuestions?language=en

        binding=ActivityCovidCertificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getCovidRelatedQuestions();

        binding.covidCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.covidSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CovidAnswerRequest covidAnswerRequest=new CovidAnswerRequest();
                covidAnswerRequest.setCovidQuestionsResponseList(covidCertificationAdapter.getList());
                covidAnswerRequest.setResult(1);
                covidAnswerRequest.setCovidCertificationTransportMode(1);

                submitCovidAnswer(covidAnswerRequest);
            }
        });

    }

    private void submitCovidAnswer(CovidAnswerRequest covidAnswerRequest) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.submitCovidAnswer(covidAnswerRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                BaseResponse baseResponse=response.body();
                System.out.println();

                System.out.println("CovidResultCode"+baseResponse.getResultCode());

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }

    private void getCovidRelatedQuestions() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CovidQuestionsResponse>> call = apiService.getCovidQuestions("en");
        call.enqueue(new Callback<List<CovidQuestionsResponse>>() {
            @Override
            public void onResponse(Call<List<CovidQuestionsResponse>> call, Response<List<CovidQuestionsResponse>> response) {

                List<CovidQuestionsResponse> covidQuestionsResponseList=response.body();


                covidCertificationAdapter=new CovidCertificationAdapter(CovidCertificationActivity.this,covidQuestionsResponseList);
                binding.rvCovidCertificate.setAdapter(covidCertificationAdapter);


            }

            @Override
            public void onFailure(Call<List<CovidQuestionsResponse>> call, Throwable t) {

            }
        });

    }
}