package dream.guys.hotdeskandroid.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.CreatePinRequest;
import dream.guys.hotdeskandroid.model.request.GetTokenRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.CheckPinLoginResponse;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.ui.login.pin.CreatePinActivity;
import dream.guys.hotdeskandroid.ui.login.pin.LoginPinActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.MyApp;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends AppCompatActivity {


    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;


    @BindView(R.id.signInRoot)
    RelativeLayout signInRoot;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.btnPinSignIn)
    Button btnPinSignIn;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);
        dialog= new Dialog(this);
        if (getIntent().getExtras() != null &&
                getIntent().getExtras().getBoolean("qr_deep_link")){
            Utils.showCustomAlertDialog(this, "Please Login then scan the Qr Code for booking.");
        }
        boolean tokenStatus =
                SessionHandler.getInstance().getBoolean(
                        MyApp.getContext(),
                        AppConstants.TOKEN_EXPIRY_STATUS);

        try {
            android.content.pm.PackageInfo info = getPackageManager().getPackageInfo(
                    "dream.guys.hotdeskandroid",
                    android.content.pm.PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                android.util.Log.d("KeyHash", "KeyHash:" + android.util.Base64.encodeToString(md.digest(),
                        android.util.Base64.DEFAULT));

            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {

        } catch (java.security.NoSuchAlgorithmException e) {

        }
//        checkForPinLogin();

        //Already loggedin user
        System.out.println("login chec"+SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.PIN_SETUP_DONE));
//        System.out.println("login chec"+SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.AppConstants.LOGIN_CHECK));
        if(tokenStatus &&!SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.LOGIN_CHECK)
                && SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.PIN_SETUP_DONE)){
            btnPinSignIn.setVisibility(View.VISIBLE);
            //If token expired enable fingerprint
//            enableBioMetricAccessHere();
        } else if (!SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.LOGIN_CHECK)
                && SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.PIN_SETUP_DONE)){
            btnPinSignIn.setVisibility(View.VISIBLE);
            /*
            Intent intent=new Intent(SignInActivity.this, LoginPinActivity.class);
            startActivity(intent);
            finish();*/
        }else {
            btnPinSignIn.setVisibility(View.GONE);

            //NormalFlow
            int userId=SessionHandler.getInstance().getInt(SignInActivity.this, AppConstants.USER_ID);
            if(SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.LOGIN_CHECK)){
                launchHomeActivity();
            }else {
                visibleSignInButton();
            }
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkForPinLogin();
                Intent intent=new Intent(SignInActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnPinSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkForPinLogin();
                Intent intent=new Intent(SignInActivity.this, LoginPinActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void checkForPinLogin() {
        if (Utils.isNetworkAvailable(this)) {
            dialog = ProgressDialog.showProgressBar(SignInActivity.this);
//                GetTokenRequest getTokenRequest = new GetTokenRequest(companyName, email, password);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            CreatePinRequest createPinRequest = new CreatePinRequest();
            createPinRequest.setTenantName(SessionHandler.getInstance().get(this, AppConstants.COMPANY_NAME));
            createPinRequest.setUserId(""+SessionHandler.getInstance().getInt(this,AppConstants.USER_ID));
            Call<CheckPinLoginResponse> call = apiService.checkPinLoginAvailable(createPinRequest);
            call.enqueue(new Callback<CheckPinLoginResponse>() {
                @Override
                public void onResponse(Call<CheckPinLoginResponse> call, Response<CheckPinLoginResponse> response) {
                    if(response.code()==200){
                        //ProgressDialog.dismisProgressBar(SignInActivity.this,dialog);
                        if (response.body().isHasPinSetup()){
//                            btnPinSignIn.setVisibility(View.VISIBLE);
//                            btnSignIn.setVisibility(View.VISIBLE);
                            /*Intent intent=new Intent(SignInActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();*/
                        }else {
//                            btnSignIn.setVisibility(View.VISIBLE);
//                            btnPinSignIn.setVisibility(View.GONE);
/*
                            Intent intent=new Intent(SignInActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();*/
                        }

                    }else if(response.code() == 401){
                        ProgressDialog.dismisProgressBar(SignInActivity.this,dialog);
                    }
                    else {
                        ProgressDialog.dismisProgressBar(SignInActivity.this,dialog);
//                        Utils.toastMessage(SignInActivity.this, "Error updating Pin. Please enter again");
                        Intent intent=new Intent(SignInActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<CheckPinLoginResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(SignInActivity.this,dialog);
                    Intent intent=new Intent(SignInActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            });


        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }

    }

    @SuppressLint("ResourceAsColor")
    private void enableBioMetricAccessHere() {

        //signInRoot.setBackgroundColor(R.color.biometric_bg);

        BiometricManager biometricManager= BiometricManager.from(this);

        switch (biometricManager.canAuthenticate()){

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "No HardWare Found", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Not Working", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "No FingerPrint Assigned", Toast.LENGTH_SHORT).show();
                break;
        }

        Executor executor= ContextCompat.getMainExecutor(this);

        biometricPrompt=new BiometricPrompt(SignInActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                sendSavedLoginCredentialToServer();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo=new BiometricPrompt.PromptInfo.Builder().setTitle("HotDesk").setDescription("Use Fingerprint To Login").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);

    }

    private void sendSavedLoginCredentialToServer() {

        if (Utils.isNetworkAvailable(this)) {
            String companyName=SessionHandler.getInstance().get(SignInActivity.this,AppConstants.COMPANY_NAME);
            String email=SessionHandler.getInstance().get(SignInActivity.this,AppConstants.EMAIL);
            String password=SessionHandler.getInstance().get(SignInActivity.this,AppConstants.PASSWORD);

            GetTokenRequest getTokenRequest = new GetTokenRequest(companyName, email, password);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GetTokenResponse> call = apiService.getLoginToken(getTokenRequest);
            call.enqueue(new Callback<GetTokenResponse>() {
                @Override
                public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {

                    GetTokenResponse getTokenResponse = response.body();

                    if (getTokenResponse != null) {
                        //Save token
                        SessionHandler.getInstance().save(SignInActivity.this, AppConstants.USERTOKEN, getTokenResponse.getToken());

                        //getUserDetailsUsingToken(getTokenResponse.getToken());

                        //Token is updated so Set Token Expiry Status false here
                        SessionHandler.getInstance().saveBoolean(SignInActivity.this,AppConstants.TOKEN_EXPIRY_STATUS,false);
                        launchHomeActivity();

                    } else {
                        Utils.toastMessage(SignInActivity.this, "CantFetch");
                    }
                }

                @Override
                public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                    System.out.println("WrongDataReceived"+t.getMessage().toString());
                    Utils.toastMessage(SignInActivity.this, "You have entered wrong username or password");
                }
            });
        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }

    private void visibleSignInButton() {
        btnSignIn.setVisibility(View.VISIBLE);
    }

    private void launchHomeActivity() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}