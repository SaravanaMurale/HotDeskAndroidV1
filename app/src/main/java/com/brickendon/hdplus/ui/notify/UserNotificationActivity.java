package com.brickendon.hdplus.ui.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

import com.brickendon.hdplus.adapter.AdapterUserNotify;
import com.brickendon.hdplus.databinding.ActivityUserNotificationBinding;
import com.brickendon.hdplus.model.response.IncomingRequestResponse;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserNotificationActivity extends AppCompatActivity {

    ActivityUserNotificationBinding binding;
    Context context;
    ArrayList<IncomingRequestResponse.Result> notiList;
    ArrayList<IncomingRequestResponse.Result> notificationList;
    AdapterUserNotify adapterUserNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_notification);
        binding = ActivityUserNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = UserNotificationActivity.this;

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        loadNotification();

    }

    private void loadNotification() {

        if (Utils.isNetworkAvailable(context)) {
            notificationList = new ArrayList<>();
            callOutgoingNotification();

        } else {
            Utils.toastMessage(context, "Please Enable Internet");
        }
    }

    private void callOutgoingNotification() {

        binding.locateProgressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<IncomingRequestResponse> call = apiService.getOutgoingRequest(true);
        Call<IncomingRequestResponse> call = apiService.getOutgoingRequest(true,true);
        call.enqueue(new Callback<IncomingRequestResponse>() {
            @Override
            public void onResponse(Call<IncomingRequestResponse> call, Response<IncomingRequestResponse> response) {
                if(response.code()==200){
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    notiList = new ArrayList<>();
                    if (response.body()!=null && response.body().getResults()!=null){
                        notiList.addAll(response.body().getResults());
                            loo :
                            for (int i=0;i<notiList.size();i++){
                                if (notiList.get(i).getStatus()==0){
                                    SessionHandler.getInstance().saveBoolean(context, AppConstants.SHOWNOTIFICATION,true);
                                    binding.notiIcon.setVisibility(View.VISIBLE);
                                    break loo;
                                }
                                SessionHandler.getInstance().saveBoolean(context, AppConstants.SHOWNOTIFICATION,false);
                            }
                        notiList.replaceAll(val ->{
                            val.setIncoming("outgoing");
                            return val;
                        });
                        //notiList.sort(Comparator.comparing(IncomingRequestResponse.Result::getStatus));

                        notificationList.addAll(notiList);

                        IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);
                        int count = Collections.frequency(notificationList, result);

                        adapterUserNotify = new AdapterUserNotify(context,notificationList,count);
                        binding.notificationRecyclerview.setAdapter(adapterUserNotify);

                    }
                }else if(response.code()==401){
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    Utils.showCustomTokenExpiredDialog(UserNotificationActivity.this,"Token Expired");
                    SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK,false);
                }
            }
            @Override
            public void onFailure(Call<IncomingRequestResponse> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}