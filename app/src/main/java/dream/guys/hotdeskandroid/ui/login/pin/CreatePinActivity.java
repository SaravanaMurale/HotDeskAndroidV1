package dream.guys.hotdeskandroid.ui.login.pin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.CreatePinRequest;
import dream.guys.hotdeskandroid.model.request.ForgotPasswordRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.ui.login.ForgotPasswordActivity;
import dream.guys.hotdeskandroid.ui.login.LoginActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);
        ButterKnife.bind(this);

        dialog = new Dialog(CreatePinActivity.this);


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
        btnSubmiot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLoginDetails(pin,conPin)){
                    createPinLogin();
                }else {
                    newPin.setOTP("");
                    confirmPin.setOTP("");
                }

               /* if (validateLoginDetails(newPin.getText().toString(),confirmPin.getText().toString())){
                    createPinLogin();
                }*/
            }
        });

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
                    Utils.toastMessage(CreatePinActivity.this, "You have entered wrong username or password");

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
}