package dream.guys.hotdeskandroid.ui.wellbeing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.CovidCertificationAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityCovidCertificationBinding;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.CovidAnswerRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.CovidQuestionsResponse;
import dream.guys.hotdeskandroid.ui.teams.ShowProfileActivity;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CovidCertificationActivity extends AppCompatActivity {

    ActivityCovidCertificationBinding binding;
    CovidCertificationAdapter covidCertificationAdapter;
    RadioGroup covidRadioGroup;
    RadioButton radioTransportButton;

    Dialog dialog;


    //ForLanguage
    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage ;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata ;
    LanguagePOJO.Global global;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_covid_certification);

        //https://dev-api.hotdeskplus.com/api/wellness/CovidSelfCertificationdQuestions?language=en

        dialog = new Dialog(CovidCertificationActivity.this);

        binding=ActivityCovidCertificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ReportBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setLanguage();


        getCovidRelatedQuestions();

        binding.covidCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        covidRadioGroup=(RadioGroup)findViewById(R.id.covidRadioGroup);


        binding.covidSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isNetworkAvailable(CovidCertificationActivity.this)) {

                int selectedId=covidRadioGroup.getCheckedRadioButtonId();
                radioTransportButton=(RadioButton)findViewById(selectedId);

                System.out.println("SelectedTransport"+radioTransportButton.getText().toString());
                String transPortMode=radioTransportButton.getText().toString();

                int transportMode=0;

                if(transPortMode.equalsIgnoreCase("Car")){
                    transportMode=1;
                }else if(transPortMode.equalsIgnoreCase("Public Transport")){
                    transportMode=2;
                }else if(transPortMode.equalsIgnoreCase("Taxi Uber")){
                    transportMode=3;
                }else if(transPortMode.equalsIgnoreCase("Bike")){
                    transportMode=4;
                }else if(transPortMode.equalsIgnoreCase("Walk")){
                    transportMode=5;
                }else if(transPortMode.equalsIgnoreCase("Work from home")){
                    transportMode=6;
                }


                //"localDate": "2022-09-06T00:00:00.000Z"
                CovidAnswerRequest covidAnswerRequest=new CovidAnswerRequest();
                covidAnswerRequest.setCovidQuestionsResponseList(covidCertificationAdapter.getList());


                covidAnswerRequest.setLocalDate(Utils.getCurrentDate()+"T00:00:00.000Z");
                covidAnswerRequest.setTemplate("");

                covidAnswerRequest.setResult(1);

                //If any answer is set to trye then result should be 2 else 1
                for (int i = 0; i <covidCertificationAdapter.getList().size() ; i++) {

                    if(covidCertificationAdapter.getList().get(i).isAnswer()){
                        covidAnswerRequest.setResult(2);
                    }

                }

                covidAnswerRequest.setCovidCertificationTransportMode(transportMode);
                submitCovidAnswer(covidAnswerRequest);

                }else {
                    Utils.toastMessage(CovidCertificationActivity.this, getResources().getString(R.string.enable_internet));
                }
            }
        });

    }

    private void submitCovidAnswer(CovidAnswerRequest covidAnswerRequest) {
        dialog= ProgressDialog.showProgressBar(CovidCertificationActivity.this);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Void> call = apiService.submitCovidAnswer(covidAnswerRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                dialog.dismiss();

                if(response.code()==200){
                    Toast.makeText(CovidCertificationActivity.this, "Successfull Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void getCovidRelatedQuestions() {

        if (Utils.isNetworkAvailable(CovidCertificationActivity.this)) {

        dialog= ProgressDialog.showProgressBar(CovidCertificationActivity.this);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CovidQuestionsResponse>> call = apiService.getCovidQuestions("en");
        call.enqueue(new Callback<List<CovidQuestionsResponse>>() {
            @Override
            public void onResponse(Call<List<CovidQuestionsResponse>> call, Response<List<CovidQuestionsResponse>> response) {

                List<CovidQuestionsResponse> covidQuestionsResponseList=response.body();


                covidCertificationAdapter=new CovidCertificationAdapter(CovidCertificationActivity.this,covidQuestionsResponseList);
                binding.rvCovidCertificate.setAdapter(covidCertificationAdapter);

                dialog.dismiss();


            }

            @Override
            public void onFailure(Call<List<CovidQuestionsResponse>> call, Throwable t) {
                dialog.dismiss();
            }
        });

        }else {
            Utils.toastMessage(CovidCertificationActivity.this, getResources().getString(R.string.enable_internet));
        }

    }

    public void setLanguage(){

        logoinPage = Utils.getLoginScreenData(CovidCertificationActivity.this);
        appKeysPage = Utils.getAppKeysPageScreenData(CovidCertificationActivity.this);
        resetPage = Utils.getResetPasswordPageScreencreenData(CovidCertificationActivity.this);
        actionOverLays = Utils.getActionOverLaysPageScreenData(CovidCertificationActivity.this);
        bookindata = Utils.getBookingPageScreenData(CovidCertificationActivity.this);
        global=Utils.getGlobalScreenData(CovidCertificationActivity.this);

        binding.covidSubmit.setText(appKeysPage.getSubmit());
        binding.covidCancel.setText(appKeysPage.getCancel());
        binding.indicateMode.setText(appKeysPage.getCovidTransportMode());




    }
}