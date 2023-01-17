package com.brickendon.hdplus.ui.wellbeing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.databinding.ActivityNotificationBinding;
import com.brickendon.hdplus.model.language.LanguagePOJO;
import com.brickendon.hdplus.model.response.BaseResponse;
import com.brickendon.hdplus.model.response.UserDetailsResponse;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;
    UserDetailsResponse profileData; //= new UserDetailsResponse();
    int notificationType = 0;
    Gson gson = new Gson();

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
        //setContentView(R.layout.activity_notification);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setLanguage();

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String json = SessionHandler.getInstance().get(NotificationActivity.this, AppConstants.LOGIN_RESPONSE);
        if (json!=null){
            profileData = gson.fromJson(json, UserDetailsResponse.class);
            notificationType = profileData.getNotificationType();
            setSwitch(notificationType);
        }

        binding.bookingPushSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean push = binding.bookingPushSwitch.isChecked();
                boolean mail = binding.bookingMailSwitch.isChecked();

                checkSwitches(push,mail);

            }
        });
        binding.bookingMailSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean push = binding.bookingPushSwitch.isChecked();
                boolean mail = binding.bookingMailSwitch.isChecked();

                checkSwitches(push,mail);
            }
        });

        binding.checkEmailSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean push = binding.checkPushSwitch.isChecked();
                boolean mail = binding.checkEmailSwitch.isChecked();

                checkSwitches(push,mail);
            }
        });
        binding.checkPushSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean push = binding.checkPushSwitch.isChecked();
                boolean mail = binding.checkEmailSwitch.isChecked();

                checkSwitches(push,mail);
            }
        });

        binding.covidPushSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean push = binding.covidPushSwitch.isChecked();
                boolean mail = binding.covidMailSwitch.isChecked();

                checkSwitches(push,mail);
            }
        });
        binding.covidMailSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean push = binding.covidPushSwitch.isChecked();
                boolean mail = binding.covidMailSwitch.isChecked();

                checkSwitches(push,mail);
            }
        });


    }

    private void checkSwitches(boolean pushIsChecked, boolean mailIsChecked) {

        if (pushIsChecked && !mailIsChecked){
            callNotificationUpdate(2);
        }else if (!pushIsChecked && mailIsChecked){
            callNotificationUpdate(1);
        }else if (pushIsChecked && mailIsChecked){
            callNotificationUpdate(3);
        }else {
            callNotificationUpdate(0);
        }

    }

    private void callNotificationUpdate(int notify) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.updateNotifications(notify);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                if (response.body()!=null && response.code()==200){
                    setSwitch(notify);
                    profileData.setNotificationType(notify);
                    String json = gson.toJson(profileData);
                    SessionHandler.getInstance().save(NotificationActivity.this,AppConstants.LOGIN_RESPONSE,json);
                    Toast.makeText(NotificationActivity.this, "Notification updated", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(NotificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                String json = gson.toJson(profileData);
                SessionHandler.getInstance().save(NotificationActivity.this,AppConstants.LOGIN_RESPONSE,json);
                setSwitch(profileData.getNotificationType());

            }
        });

    }

    private void setSwitch(int notify) {
        switch (notify){
            case 0:
                changeSwitch(false,false);
                break;
            case 1:
                changeSwitch(false,true);
                break;
            case 2:
                changeSwitch(true,false);
                break;
            case 3:
                changeSwitch(true,true);
                break;
        }
    }

    private void changeSwitch(boolean push,boolean email) {

        binding.covidPushSwitch.setChecked(push);
        binding.checkPushSwitch.setChecked(push);
        binding.bookingPushSwitch.setChecked(push);

        binding.bookingMailSwitch.setChecked(email);
        binding.covidMailSwitch.setChecked(email);
        binding.checkEmailSwitch.setChecked(email);

    }

    public void setLanguage(){

        logoinPage = Utils.getLoginScreenData(NotificationActivity.this);
        appKeysPage = Utils.getAppKeysPageScreenData(NotificationActivity.this);
        resetPage = Utils.getResetPasswordPageScreencreenData(NotificationActivity.this);
        actionOverLays = Utils.getActionOverLaysPageScreenData(NotificationActivity.this);
        bookindata = Utils.getBookingPageScreenData(NotificationActivity.this);
        global=Utils.getGlobalScreenData(NotificationActivity.this);

        binding.tvTitle.setText(appKeysPage.getNotifications());
        binding.tvTitleCheckIn.setText(appKeysPage.getCheckInAndCheckOut());
        binding.tvContent.setText(appKeysPage.getCheckInNotificationtitle());
        binding.tvRest.setText(appKeysPage.getPush());
        binding.checkInEmail.setText(appKeysPage.getEmail());
        binding.tvBookTitle.setText(appKeysPage.getBookingChangeAlerts());
        binding.tvBookContent.setText(appKeysPage.getAcceptedOrRejectedTitle());
        binding.bookingPushTv.setText(appKeysPage.getPush());
        binding.bookingEmailTv.setText(appKeysPage.getEmail());
        binding.tvCovidTitle.setText(appKeysPage.getCovidReminderTitle());
        binding.covidPush.setText(appKeysPage.getPush());
        binding.covidEmail.setText(appKeysPage.getEmail());

    }

}