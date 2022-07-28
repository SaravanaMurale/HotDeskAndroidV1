package dream.guys.hotdeskandroid.ui.login.pin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.CreatePinRequest;
import dream.guys.hotdeskandroid.model.response.CheckPinLoginResponse;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.ui.login.LoginActivity;
import dream.guys.hotdeskandroid.ui.login.SignInActivity;
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

public class LoginPinActivity extends AppCompatActivity {
    @BindView(R.id.tv_back_to_home)
    TextView tvBack;
    @BindView(R.id.etPin)
    OtpTextView etPin;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    Dialog dialog;
    String pin="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);
        ButterKnife.bind(this);
        dialog= new Dialog(LoginPinActivity.this);

        etPin.setOtpListener(new OTPListener()
        {
            @Override
            public void onInteractionListener()
            {

            }

            @Override
            public void onOTPComplete(String otp)
            {
                pin = otp;
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pin.isEmpty() || pin.equalsIgnoreCase("")){
                    Utils.toastMessage(LoginPinActivity.this,"Enter Pin");
                }else if (pin.length()<6){
                    Utils.toastMessage(LoginPinActivity.this,"Pin should be 6 digits");
                    etPin.setOTP("");
                }else {
                    checkPin();
                }
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginPinActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkPin() {
        if (Utils.isNetworkAvailable(this)) {
            dialog= ProgressDialog.showProgressBar(LoginPinActivity.this);
//                GetTokenRequest getTokenRequest = new GetTokenRequest(companyName, email, password);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            CreatePinRequest createPinRequest = new CreatePinRequest();
            createPinRequest.setPin(pin);
            createPinRequest.setUserId(""+SessionHandler.getInstance().getInt(this,AppConstants.USER_ID));
            Call<GetTokenResponse> call = apiService.checkPinLogin(createPinRequest);
            call.enqueue(new Callback<GetTokenResponse>() {
                @Override
                public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {
                    if(response.code()==200){
                        ProgressDialog.dismisProgressBar(LoginPinActivity.this,dialog);
                        GetTokenResponse getTokenResponse = response.body();
                        if (getTokenResponse != null) {
                            //Save token
                            SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.USERTOKEN, getTokenResponse.getToken());
                            SessionHandler.getInstance().saveBoolean(LoginPinActivity.this, AppConstants.LOGIN_CHECK,true);
                            Intent intent=new Intent(LoginPinActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else if(response.code() == 401){
                        ProgressDialog.dismisProgressBar(LoginPinActivity.this,dialog);
                        Utils.toastMessage(LoginPinActivity.this, "Invalid Pin "+response.code());

                    }else {
                        ProgressDialog.dismisProgressBar(LoginPinActivity.this,dialog);
                        Utils.toastMessage(LoginPinActivity.this, "Invalid Pin "+response.code());
                    }

                }

                @Override
                public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(LoginPinActivity.this,dialog);
                    Intent intent=new Intent(LoginPinActivity.this,LoginPinActivity.class);
                    startActivity(intent);
                    finish();

                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }

    @Override
    public void onBackPressed() {

    }
}