package dream.guys.hotdeskandroid.ui.notify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import dream.guys.hotdeskandroid.adapter.AdapterNotificationCenter;
import dream.guys.hotdeskandroid.databinding.ActivityNotificationCenterBinding;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationCenterActivity extends AppCompatActivity {

    ActivityNotificationCenterBinding binding;
    AdapterNotificationCenter adapterNotificationList;
    Context context;
    ArrayList<IncomingRequestResponse.Result> notiList;
    ArrayList<IncomingRequestResponse.Result> outGoingNotificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification_center);
        binding = ActivityNotificationCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = NotificationCenterActivity.this;

        uiInit();

    }

    private void uiInit() {

        Intent intent = getIntent();

        if (intent!=null) {

            //notiList = (ArrayList<IncomingRequestResponse.Result>) intent.getSerializableExtra(AppConstants.SHOWNOTIFICATION);

            adapterNotificationList = new AdapterNotificationCenter(context,notiList);
            binding.notificationRecyclerview.setAdapter(adapterNotificationList);

        }


    }


    private void loadNotification() {

        if (Utils.isNetworkAvailable(context)) {

            callIncomingNotification();
            callOutGoingNotification();

        } else {
            Utils.toastMessage(context, "Please Enable Internet");
        }
    }

    private void callOutGoingNotification() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<IncomingRequestResponse> call = apiService.getOutgoingRequest(true);
        call.enqueue(new Callback<IncomingRequestResponse>() {
            @Override
            public void onResponse(Call<IncomingRequestResponse> call, Response<IncomingRequestResponse> response) {
                if(response.code()==200){
                    outGoingNotificationList = new ArrayList<>();
                    if (response.body()!=null && response.body().getResults()!=null){
                        outGoingNotificationList.addAll(response.body().getResults());
                            /*loo :
                            for (int i=0;i<notiList.size();i++){
                                if (notiList.get(i).getStatus()==0){
                                    SessionHandler.getInstance().saveBoolean(context, AppConstants.SHOWNOTIFICATION,true);
                                    break loo;
                                }
                                SessionHandler.getInstance().saveBoolean(context, AppConstants.SHOWNOTIFICATION,false);
                            }*/
                    }
                }
            }
            @Override
            public void onFailure(Call<IncomingRequestResponse> call, Throwable t) {

            }
        });
    }

    private void callIncomingNotification() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<IncomingRequestResponse> call = apiService.getIncomingRequest(true);
        call.enqueue(new Callback<IncomingRequestResponse>() {
            @Override
            public void onResponse(Call<IncomingRequestResponse> call, Response<IncomingRequestResponse> response) {
                if(response.code()==200){
                    notiList = new ArrayList<>();
                    if (response.body().getResults()!=null){
                        notiList.addAll(response.body().getResults());
                            /*loo :
                            for (int i=0;i<notiList.size();i++){
                                if (notiList.get(i).getStatus()==0){
                                    SessionHandler.getInstance().saveBoolean(context, AppConstants.SHOWNOTIFICATION,true);
                                    break loo;
                                }
                                SessionHandler.getInstance().saveBoolean(context, AppConstants.SHOWNOTIFICATION,false);
                            }*/
                    }
                }else if(response.code()==401){
                    Utils.showCustomTokenExpiredDialog(NotificationCenterActivity.this,"Token Expired");
                    SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK,false);
//                        Utils.finishAllActivity(getContext());
                }
            }
            @Override
            public void onFailure(Call<IncomingRequestResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadNotification();

    }
}
