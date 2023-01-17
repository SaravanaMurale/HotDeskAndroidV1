package com.brickendon.hdplus.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.adapter.AdapterCountry;
import com.brickendon.hdplus.databinding.ActivityCountryListBinding;
import com.brickendon.hdplus.model.response.DAOCountryList;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryListActivity extends AppCompatActivity implements AdapterCountry.CountryInterface{

    ActivityCountryListBinding binding;
    Context context;
    AdapterCountry adapterCountry;
    ArrayList<DAOCountryList> countryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_country_list);

        binding = ActivityCountryListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = CountryListActivity.this;

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getCountry();

    }

    private void getCountry() {
        binding.locateProgressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DAOCountryList>> call = apiService.getCountryList();
        call.enqueue(new Callback<ArrayList<DAOCountryList>>() {
            @Override
            public void onResponse(Call<ArrayList<DAOCountryList>> call, Response<ArrayList<DAOCountryList>> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                if (response.body()!=null){

                    if (response.code() == 200) {

                        countryList = new ArrayList<>();
                        countryList = response.body();
                        setAdapter();

                    }else if(response.code() == 401){
                        Utils.showCustomTokenExpiredDialog(CountryListActivity.this,"Token Expired");
                        SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK,false);

                    }else {
                        Toast.makeText(context, "No Response", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<ArrayList<DAOCountryList>> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }
        });

    }

    public void setAdapter() {
        adapterCountry = new AdapterCountry(context,countryList,this);
        binding.countryRecyclerview.setAdapter(adapterCountry);
    }

    @Override
    public void clickEvent(String name, int id) {
        SessionHandler.getInstance().save(context,AppConstants.SELECTED_COUNTRY,name);
        SessionHandler.getInstance().saveInt(context,AppConstants.SELECTED_COUNTRY_ID,id);
        finish();
    }
}