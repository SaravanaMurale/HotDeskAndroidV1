package com.brickendon.hdplus.ui.login.pin;

import static com.brickendon.hdplus.utils.Utils.getSettingsPageScreenData;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.language.LanguagePOJO;
import com.brickendon.hdplus.model.request.CreatePinRequest;
import com.brickendon.hdplus.model.response.BaseResponse;
import com.brickendon.hdplus.model.response.GetTokenResponse;
import com.brickendon.hdplus.model.response.UserDetailsResponse;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.ProgressDialog;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePinActivity extends AppCompatActivity {
    @BindView(R.id.etConfirmPin)
    OtpTextView confirmPin;
    @BindView(R.id.etNewPin)
    OtpTextView newPin;
    @BindView(R.id.btnSubmit)
    Button btnSubmiot;
    @BindView(R.id.tv_back_to_home)
    TextView tvBack;
    Dialog dialog;
    String pin="", conPin="";

    //New...
    @BindView(R.id.profile_back)
    ImageView profile_back;
    @BindView(R.id.old_pin_layout)
    LinearLayout old_pin_layout;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.etOldPin)
    OtpTextView etOldPin;
    String oldPin = "";
    boolean isPinSetup;

    @BindView(R.id.tvConfirmPin)
    TextView tvConfirmPin;
    @BindView(R.id.tvNewPin)
    TextView tvNewPin;
    @BindView(R.id.tvRest)
    TextView tvRest;
    @BindView(R.id.tvOldPin)
    TextView tvOldPin;

    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata;
    LanguagePOJO.Global global;
    LanguagePOJO.Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);
        ButterKnife.bind(this);

        dialog = new Dialog(CreatePinActivity.this);
        setLanguage();
        newPin.setOtpListener(new OTPListener()
        {
            @Override
            public void onInteractionListener()
            {
                //Toast.makeText(getApplicationContext(),"selected",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onOTPComplete(String otp)
            {
                pin = otp;
//                Toast.makeText(getApplicationContext(),otp,Toast.LENGTH_LONG).show();
            }
        });

        confirmPin.setOtpListener(new OTPListener()
        {
            @Override
            public void onInteractionListener()
            {

            }

            @Override
            public void onOTPComplete(String otp)
            {
                conPin = otp;
//                Toast.makeText(getApplicationContext(),otp,Toast.LENGTH_LONG).show();
            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        profile_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSubmiot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPinSetup){
                    if (threePinValidations(oldPin,pin,conPin)){
                        callOldPincheck(oldPin);
                    }else {
//                        Utils.toastMessage(getApplicationContext(),"Please enter Old Pin");
                        etOldPin.setOTP("");
                        newPin.setOTP("");
                        confirmPin.setOTP("");
                    }
                }else {
                    if (validateLoginDetails(pin,conPin)){
                        createPinLogin();
                    }else {
                        newPin.setOTP("");
                        confirmPin.setOTP("");
                    }
                }

               /* if (validateLoginDetails(newPin.getText().toString(),confirmPin.getText().toString())){
                    createPinLogin();
                }*/
            }
        });

        //New...
        etOldPin.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                oldPin = otp;
            }
        });

        getUserDetailsUsingToken();


    }
    public void setLanguage() {

        LanguagePOJO.Settings wellBeingPage = getSettingsPageScreenData(this);
        logoinPage = Utils.getLoginScreenData(this);
        appKeysPage = Utils.getAppKeysPageScreenData(this);
        resetPage = Utils.getResetPasswordPageScreencreenData(this);
        actionOverLays = Utils.getActionOverLaysPageScreenData(this);
        bookindata = Utils.getBookingPageScreenData(this);
        global = Utils.getGlobalScreenData(this);
        settings = Utils.getSettingsPageScreenData(this);
        try {
            tvRest.setText(settings.getSetUpPinEditPinDescription());
            tvOldPin.setText(appKeysPage.getEnterYourOldPin());
            tvNewPin.setText(appKeysPage.getEnterYourNewPin());
        } catch (Exception e){

        }

    }

    private boolean threePinValidations(String oldPin, String nwPin, String conPin) {
        boolean userDetailStatus = false;

        if (nwPin.equalsIgnoreCase("") || nwPin.isEmpty()
                || conPin.equalsIgnoreCase("") || conPin.isEmpty()
                || oldPin.equalsIgnoreCase("") || oldPin.isEmpty() ){
            Utils.toastMessage(getApplicationContext(),"Please enter Pin");
            userDetailStatus = false;
        }else if (nwPin.length() < 6 || conPin.length() < 6 || oldPin.length() < 6 ){
            Utils.toastMessage(getApplicationContext(),"Pin Should be 6 digits");
            userDetailStatus = false;
        }else if (!nwPin.equalsIgnoreCase(conPin)){
            Utils.toastMessage(getApplicationContext(),"Pin Mismatch");
            userDetailStatus = false;
        }else {
            userDetailStatus = true;
        }

        return userDetailStatus;
    }

    private void callOldPincheck(String oldPin) {

        if (Utils.isNetworkAvailable(this)) {
            dialog= ProgressDialog.showProgressBar(CreatePinActivity.this);
//                GetTokenRequest getTokenRequest = new GetTokenRequest(companyName, email, password);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            CreatePinRequest createPinRequest = new CreatePinRequest();
            createPinRequest.setPin(oldPin);
            createPinRequest.setUserId(""+SessionHandler.getInstance().getInt(this,AppConstants.USER_ID));
            Call<GetTokenResponse> call = apiService.checkPinLogin(createPinRequest);
            call.enqueue(new Callback<GetTokenResponse>() {
                @Override
                public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {
                    if(response.code()==200){
                        ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
                        createPinLogin();

                        /*GetTokenResponse getTokenResponse = response.body();
                        if (getTokenResponse != null) {
                            //Save token
                            SessionHandler.getInstance().save(CreatePinActivity.this, AppConstants.USERTOKEN, getTokenResponse.getToken());
                            SessionHandler.getInstance().saveBoolean(CreatePinActivity.this, AppConstants.LOGIN_CHECK,true);

                        }*/
                    } else if(response.code() == 401){
                        ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
                        Utils.toastMessage(CreatePinActivity.this, "Invalid Pin "+response.code());

                    }else {
                        ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
                        Utils.toastMessage(CreatePinActivity.this, "Invalid Pin "+response.code());
                    }

                }

                @Override
                public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
                    Intent intent=new Intent(CreatePinActivity.this,LoginPinActivity.class);
                    startActivity(intent);
                    finish();

                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
        
    }

    private void createPinLogin() {
        if (Utils.isNetworkAvailable(this)) {

            dialog= ProgressDialog.showProgressBar(CreatePinActivity.this);
//                GetTokenRequest getTokenRequest = new GetTokenRequest(companyName, email, password);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            CreatePinRequest createPinRequest = new CreatePinRequest();
            createPinRequest.setTenantName(SessionHandler.getInstance().get(this, AppConstants.COMPANY_NAME));
            createPinRequest.setUsername(SessionHandler.getInstance().get(this,AppConstants.EMAIL));
            createPinRequest.setNewPin(pin.trim());
            createPinRequest.setConfirmNewPin(conPin.trim());
            Call<BaseResponse> call = apiService.createPin(createPinRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(response.code()==200){
                        ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
                        switch (response.body().getResultCode()){
                            case "PINS_NOT_MATCH":
                                Utils.toastMessage(CreatePinActivity.this,"pins does not match");
                                break;
                            case "INVALID_LENGTH":
                                Utils.toastMessage(CreatePinActivity.this,"Invalid Length");
                                break;
                            case "OK":
                                Utils.toastMessage(CreatePinActivity.this,"Pin Setup Successfull");
                                SessionHandler.getInstance().saveBoolean(CreatePinActivity.this,AppConstants.PIN_SETUP_DONE,true);

                                SessionHandler.getInstance().saveBoolean(CreatePinActivity.this,AppConstants.PIN_ACTIVE_STATUS_AFTER_LOGOUT,true);

                                finish();
                                break;
                        }

                    }else if(response.code() == 401){
                        ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
                        Utils.toastMessage(CreatePinActivity.this, "Token Expired");
                    }
                    else {
                        ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
                        Utils.toastMessage(CreatePinActivity.this, "Error updating Pin. Please enter again");
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
//                    Utils.toastMessage(CreatePinActivity.this, "Api Failed");

                }
            });


        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }


    }

    private boolean validateLoginDetails(String nwPin, String conPin) {
        boolean userDetailStatus = false;

        if (nwPin.equalsIgnoreCase("") || nwPin.isEmpty()
                || conPin.equalsIgnoreCase("") || conPin.isEmpty() ){
            Utils.toastMessage(getApplicationContext(),"Please enter Pin");
            userDetailStatus = false;
        }else if (nwPin.length() < 6 || conPin.length() < 6){
            Utils.toastMessage(getApplicationContext(),"Pin Should be 6 digits");
            userDetailStatus = false;
        }else if (!nwPin.equalsIgnoreCase(conPin)){
            Utils.toastMessage(getApplicationContext(),"Pin Mismatch");
            userDetailStatus = false;
        }else {
            userDetailStatus = true;
        }

        return userDetailStatus;

    }

    @Override
    public void onBackPressed() {

    }
    
    //New...

    private void getUserDetailsUsingToken() {

        if (Utils.isNetworkAvailable(this)) {
            dialog=ProgressDialog.showProgressBar(CreatePinActivity.this);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UserDetailsResponse> call = apiService.getLoginUserDetails();
            call.enqueue(new Callback<UserDetailsResponse>() {
                @Override
                public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                    ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
                    if(response.code()==200){
                        UserDetailsResponse userDetailsResponse = response.body();

                        if (userDetailsResponse != null) {

                            isPinSetup = userDetailsResponse.isHasPinSetup();

                            if (isPinSetup){
                                old_pin_layout.setVisibility(View.VISIBLE);

                            }else {
                                old_pin_layout.setVisibility(View.GONE);
                                tvTitle.setText("");
                                tvNewPin.setText("Enter your pin");
                                tvConfirmPin.setText("Re-enter your pin");

                            }

                        } else {
                            ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
                            Utils.toastMessage(CreatePinActivity.this, "User Details Not Found");
                        }

                    }else if(response.code()==401){
                        ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
                        Utils.toastMessage(CreatePinActivity.this, "Please Try Again");
                    }

                }

                @Override
                public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(CreatePinActivity.this,dialog);
                }
            });
        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }


    }
    
}