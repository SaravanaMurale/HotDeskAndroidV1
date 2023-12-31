package com.brickendon.hdplus.ui.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

import com.brickendon.hdplus.adapter.AdapterNotificationCenter;
import com.brickendon.hdplus.databinding.ActivityNotificationCenterBinding;
import com.brickendon.hdplus.model.response.IncomingRequestResponse;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationCenterActivity extends AppCompatActivity {

    ActivityNotificationCenterBinding binding;
    AdapterNotificationCenter adapterNotificationList;
    Context context;
    ArrayList<IncomingRequestResponse.Result> notiList;
    ArrayList<IncomingRequestResponse.Result> outGoingNotificationList;
    ArrayList<IncomingRequestResponse.Result> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification_center);
        binding = ActivityNotificationCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = NotificationCenterActivity.this;

        uiInit();

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void uiInit() {

        Intent intent = getIntent();

        if (intent!=null) {

            //notiList = (ArrayList<IncomingRequestResponse.Result>) intent.getSerializableExtra(AppConstants.SHOWNOTIFICATION);

        }


    }


    private void loadNotification() {

        if (Utils.isNetworkAvailable(context)) {
            notificationList = new ArrayList<>();
            callIncomingNotification();//247
//            callOutGoingNotification();//9

        } else {
            Utils.toastMessage(context, "Please Enable Internet");
        }
    }

    private void callOutGoingNotification() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<IncomingRequestResponse> call = apiService.getOutgoingRequest(true);
        Call<IncomingRequestResponse> call = apiService.getOutgoingRequest(true,true);
        call.enqueue(new Callback<IncomingRequestResponse>() {
            @Override
            public void onResponse(Call<IncomingRequestResponse> call, Response<IncomingRequestResponse> response) {
                if(response.code()==200){
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    outGoingNotificationList = new ArrayList<>();
                    if (response.body()!=null && response.body().getResults()!=null){

                        outGoingNotificationList.addAll(response.body().getResults());

                        outGoingNotificationList.replaceAll(val ->{
                            val.setIncoming("Pending");
                            return val;
                        });

                        //outGoingNotificationList.sort(Comparator.comparing(IncomingRequestResponse.Result::getStatus));

                        //notificationList.addAll(outGoingNotificationList);
                        setAdapter();

                    }else {
                        setAdapter();
                    }
                }
            }
            @Override
            public void onFailure(Call<IncomingRequestResponse> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        
        // do not remove this line sorting is done because we call 2 api and merge them so sorting is needed
//        notificationList.sort(Comparator.comparing(IncomingRequestResponse.Result::getStatus));

        int count = 0;
        //if (outGoingNotificationList!=null && outGoingNotificationList.size()>0) {
         if (notificationList!=null && notificationList.size()>0) {
            IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);
            count = Collections.frequency(notificationList, result);
        }



        adapterNotificationList = new AdapterNotificationCenter(context,notificationList,
                count,notiList,outGoingNotificationList);
        binding.notificationRecyclerview.setAdapter(adapterNotificationList);

    }

    private void callIncomingNotification() {

        binding.locateProgressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<IncomingRequestResponse> call = apiService.getIncomingRequest(true);
        Call<IncomingRequestResponse> call = apiService.getIncomingRequest(true,true);
        call.enqueue(new Callback<IncomingRequestResponse>() {
            @Override
            public void onResponse(Call<IncomingRequestResponse> call, Response<IncomingRequestResponse> response) {
                if(response.code()==200){
                    notiList = new ArrayList<>();
                    if (response.body()!=null && response.body().getResults()!=null){
                        notiList.addAll(response.body().getResults());
                            loo :
                            for (int i=0;i<notiList.size();i++){
                                if (notiList.get(i).getStatus()==0){
                                    SessionHandler.getInstance().saveBoolean(context,
                                            AppConstants.SHOWNOTIFICATION,true);
                                    binding.notiIcon.setVisibility(View.VISIBLE);
                                    break loo;
                                }
                                SessionHandler.getInstance().saveBoolean(context,
                                        AppConstants.SHOWNOTIFICATION,false);
                            }
                        notiList.replaceAll(val ->{
                            val.setIncoming("incoming");
                            return val;
                        });
                      //  notiList.sort(Comparator.comparing(IncomingRequestResponse.Result::getStatus));

                        notificationList.addAll(notiList);

                        callOutGoingNotification();

                    }else {
                        callOutGoingNotification();
                    }
                }else if(response.code()==401){
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    Utils.showCustomTokenExpiredDialog(NotificationCenterActivity.this,"Token Expired");
                    SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK,false);
//                        Utils.finishAllActivity(getContext());
                    callOutGoingNotification();
                } else {
                    callOutGoingNotification();
                }
            }
            @Override
            public void onFailure(Call<IncomingRequestResponse> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                callOutGoingNotification();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadNotification();

    }
}
