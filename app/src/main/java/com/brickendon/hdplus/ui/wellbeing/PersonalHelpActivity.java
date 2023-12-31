package com.brickendon.hdplus.ui.wellbeing;

import static com.brickendon.hdplus.utils.Utils.getAppKeysPageScreenData;
import static com.brickendon.hdplus.utils.Utils.getGlobalScreenData;
import static com.brickendon.hdplus.utils.Utils.getPersonalPagesScreenData;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.adapter.PersonalHelpAdapter;
import com.brickendon.hdplus.model.language.LanguagePOJO;
import com.brickendon.hdplus.model.request.PersonalHelpRequest;
import com.brickendon.hdplus.model.response.WellbeingConfigResponse;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalHelpActivity extends AppCompatActivity {
    private static final String TAG = "PersonalHelpActivity";

    TextView tvContent,btn_cancel;
    RecyclerView rvPersonal;
    Button btn_send;
    ImageView ReportBack;
    EditText tvDescription;
    CheckBox cb_anonymous;

    LinearLayoutManager mLayoutManager;
    PersonalHelpAdapter personalHelpAdapter;

    List<WellbeingConfigResponse.Link> linksArray = new ArrayList<>();

    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.PersonalHelp personalHelp;
    LanguagePOJO.Global global;
    TextView mTitle,tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        Intent intent=getIntent();

        tvContent = findViewById(R.id.tvContent);
        rvPersonal = findViewById(R.id.rvPersonal);
        btn_send = findViewById(R.id.btn_send);
        btn_cancel = findViewById(R.id.btn_cancel);
        ReportBack = findViewById(R.id.ReportBack);
        tvDescription = findViewById(R.id.tvDescription);
        cb_anonymous = findViewById(R.id.cb_anonymous);

        mTitle = findViewById(R.id.profile_edit);
        tvHeader = findViewById(R.id.tvHeader);

        setLanguage();

        linksArray= (List<WellbeingConfigResponse.Link>) intent.getSerializableExtra(AppConstants.PERSONAL_LINKS);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvPersonal.setLayoutManager(mLayoutManager);
        personalHelpAdapter = new PersonalHelpAdapter(this, linksArray);
        rvPersonal.setAdapter(personalHelpAdapter);

//        Log.d(TAG, "onCreate: "+linksArray.get(0).getUrl());

        tvContent.setText(intent.getStringExtra(AppConstants.PERSONAL_CONTENT));

        btn_send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(tvDescription.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Description",Toast.LENGTH_LONG).show();
                } else {
                    postPersonalHelp();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        ReportBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    private void postPersonalHelp()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        PersonalHelpRequest personalHelpRequest = new PersonalHelpRequest();
        personalHelpRequest.setDescription(tvDescription.getText().toString());
        personalHelpRequest.setAnonymous(cb_anonymous.isChecked());
        Call<Void> call = apiService.postPersonalHelp(personalHelpRequest);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.d(TAG, "onResponse: ");
                Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Not Updated",Toast.LENGTH_LONG).show();
                finish();
                Log.d(TAG, "onFailure: "+call.request());
                t.printStackTrace();
            }
        });



    }

    private void setLanguage() {
        appKeysPage = getAppKeysPageScreenData(this);
        personalHelp = getPersonalPagesScreenData(this);
        global = getGlobalScreenData(this);

        mTitle.setText(personalHelp.getTitle());
        tvHeader.setText(personalHelp.getDescribeInfo());
        cb_anonymous.setText(global.getAnonymous());
        btn_send.setText(appKeysPage.getSubmit());
        btn_cancel.setText(appKeysPage.getCancel());

    }

}