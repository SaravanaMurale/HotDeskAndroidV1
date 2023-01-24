package com.brickendon.hdplus.ui.login;

import static com.brickendon.hdplus.utils.MyApp.getContext;
import static com.brickendon.hdplus.utils.Utils.getAppKeysPageScreenData;
import static com.brickendon.hdplus.utils.Utils.getLoginScreenData;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.brickendon.hdplus.MainActivity;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.language.LanguagePOJO;
import com.brickendon.hdplus.model.request.CreatePinRequest;
import com.brickendon.hdplus.model.request.GetTokenRequest;
import com.brickendon.hdplus.model.response.BookingListResponse;
import com.brickendon.hdplus.model.response.CheckPinLoginResponse;
import com.brickendon.hdplus.model.response.GetTokenResponse;
import com.brickendon.hdplus.ui.login.pin.LoginPinActivity;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.MyApp;
import com.brickendon.hdplus.utils.ProgressDialog;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
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

    MyApp myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);
        dialog = new Dialog(this);

        setLang();

/*
        setNightMode(this,
                SessionHandler.getInstance()
                        .getBoolean(SignInActivity.this, AppConstants.DARK_MODE_CHECK));
                        */
        //String tokenInSharedPreference=SessionHandler.getInstance().get(getApplicationContext(),AppConstants.SAVETOKEN);
        //System.out.println("SharedPreferenceToken "+tokenInSharedPreference);

        //FirebaseNotificationService firebaseNotificationService=new FirebaseNotificationService();
        //firebaseNotificationService.saveTokenInserver(tokenInSharedPreference);


        if (getIntent().getExtras() != null &&
                getIntent().getExtras().getBoolean("qr_deep_link")) {
            Utils.toastShortMessage(this, "Please Login then scan the Qr Code for booking.");
        }
        boolean tokenStatus =
                SessionHandler.getInstance().getBoolean(
                        getContext(),
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
        System.out.println("login chec" + SessionHandler.getInstance().getBoolean(SignInActivity.this, AppConstants.PIN_SETUP_DONE));
//

        //Bala Flow
        /*if(tokenStatus &&!SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.LOGIN_CHECK)
        System.out.println("login chec"+SessionHandler.getInstance().getBoolean(SignInActivity.this, AppConstants.PIN_SETUP_DONE));
//        System.out.println("login chec"+SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.AppConstants.LOGIN_CHECK));
        if(tokenStatus
                &&!SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.LOGIN_CHECK)
                && SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.PIN_SETUP_DONE)){
            btnPinSignIn.setVisibility(View.VISIBLE);
        } else if (!SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.LOGIN_CHECK)
                && SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.PIN_SETUP_DONE)){
            btnPinSignIn.setVisibility(View.VISIBLE);

            Intent intent=new Intent(SignInActivity.this, LoginPinActivity.class);
            startActivity(intent);
            finish();
        }else {
            btnPinSignIn.setVisibility(View.GONE);

            //NormalFlow
            int userId=SessionHandler.getInstance().getInt(SignInActivity.this, AppConstants.USER_ID);
            if(SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.LOGIN_CHECK)){
                launchHomeActivity();
            }else {
                visibleSignInButton();
            }
        }*/
        //End Bala Flow

        boolean logicCheck = SessionHandler.getInstance().getBoolean(SignInActivity.this, AppConstants.LOGIN_CHECK);
        boolean pinActiveStatusAfterLogout = SessionHandler.getInstance().getBoolean(SignInActivity.this, AppConstants.PIN_ACTIVE_STATUS_AFTER_LOGOUT);

        if (!logicCheck && !pinActiveStatusAfterLogout) {
            //FirstTime Login
            btnPinSignIn.setVisibility(View.INVISIBLE);
        } else if (logicCheck && pinActiveStatusAfterLogout) {
            btnSignIn.setVisibility(View.INVISIBLE);
            btnPinSignIn.setVisibility(View.INVISIBLE);
            launchHomeActivity();
        } else if (logicCheck && !pinActiveStatusAfterLogout) {
            btnPinSignIn.setVisibility(View.GONE);
            launchHomeActivity();
        } else if (!logicCheck && pinActiveStatusAfterLogout) {
            btnPinSignIn.setVisibility(View.VISIBLE);
            visibleSignInButton();
        } else {
            btnPinSignIn.setVisibility(View.VISIBLE);
        }
        /*else {
            btnPinSignIn.setVisibility(View.GONE);

            //NormalFlow
            int userId=SessionHandler.getInstance().getInt(SignInActivity.this, AppConstants.USER_ID);
            if(SessionHandler.getInstance().getBoolean(SignInActivity.this,AppConstants.LOGIN_CHECK)){
                launchHomeActivity();
            }else {
                visibleSignInButton();
            }
        }*/


        //New My
        /*int userId = SessionHandler.getInstance().getInt(SignInActivity.this, AppConstants.USER_ID);
        boolean pinActiveStatusAfterLogout = SessionHandler.getInstance().getBoolean(SignInActivity.this, AppConstants.PIN_ACTIVE_STATUS_AFTER_LOGOUT);

        if (userId > 0 && pinActiveStatusAfterLogout) {
            btnPinSignIn.setVisibility(View.VISIBLE);
            visibleSignInButton();
        }

        if( userId>0 && SessionHandler.getInstance().getBoolean(SignInActivity.this, AppConstants.LOGIN_CHECK)){
            Intent intent=new Intent(SignInActivity.this, LoginPinActivity.class);
            startActivity(intent);
            finish();
        }else if (!SessionHandler.getInstance().getBoolean(SignInActivity.this, AppConstants.LOGIN_CHECK)) {
            launchHomeActivity();
        }*/


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkForPinLogin();
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnPinSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checkForPinLogin();
                Intent intent = new Intent(SignInActivity.this, LoginPinActivity.class);
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
            createPinRequest.setUserId("" + SessionHandler.getInstance().getInt(this, AppConstants.USER_ID));
            Call<CheckPinLoginResponse> call = apiService.checkPinLoginAvailable(createPinRequest);
            call.enqueue(new Callback<CheckPinLoginResponse>() {
                @Override
                public void onResponse(Call<CheckPinLoginResponse> call, Response<CheckPinLoginResponse> response) {
                    if (response.code() == 200) {
                        //ProgressDialog.dismisProgressBar(SignInActivity.this,dialog);
                        if (response.body().isHasPinSetup()) {
//                            btnPinSignIn.setVisibility(View.VISIBLE);
//                            btnSignIn.setVisibility(View.VISIBLE);
                            /*Intent intent=new Intent(SignInActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();*/
                        } else {
//                            btnSignIn.setVisibility(View.VISIBLE);
//                            btnPinSignIn.setVisibility(View.GONE);
/*
                            Intent intent=new Intent(SignInActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();*/
                        }

                    } else if (response.code() == 401) {
                        ProgressDialog.dismisProgressBar(SignInActivity.this, dialog);
                    } else {
                        ProgressDialog.dismisProgressBar(SignInActivity.this, dialog);
//                        Utils.toastMessage(SignInActivity.this, "Error updating Pin. Please enter again");
                        Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<CheckPinLoginResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(SignInActivity.this, dialog);
                    Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
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

        BiometricManager biometricManager = BiometricManager.from(this);

        switch (biometricManager.canAuthenticate()) {

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

        Executor executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(SignInActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
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

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("HybridHero").setDescription("Use Fingerprint To Login").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);

    }

    private void sendSavedLoginCredentialToServer() {

        if (Utils.isNetworkAvailable(this)) {
            String companyName = SessionHandler.getInstance().get(SignInActivity.this, AppConstants.COMPANY_NAME);
            String email = SessionHandler.getInstance().get(SignInActivity.this, AppConstants.EMAIL);
            String password = SessionHandler.getInstance().get(SignInActivity.this, AppConstants.PASSWORD);

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
                        SessionHandler.getInstance().saveBoolean(SignInActivity.this, AppConstants.TOKEN_EXPIRY_STATUS, false);
                        launchHomeActivity();

                    } else {
                        Utils.toastMessage(SignInActivity.this, "CantFetch");
                    }
                }

                @Override
                public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                    System.out.println("WrongDataReceived" + t.getMessage().toString());
//                    Utils.toastMessage(SignInActivity.this, "You have entered the incorrect password. Please try again.");
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

        if (Utils.isNetworkAvailable(this)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BookingListResponse> call = apiService.getUserMyWorkDetails(Utils.getCurrentDate(), true);
            call.enqueue(new Callback<BookingListResponse>() {
                @Override
                public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                    try {
                        if (response.code() == 200) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 1000);
                        } else {
                            Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<BookingListResponse> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(SignInActivity.this, "Please Enable Internet");
        }


    }

    @Override
    public void onBackPressed() {

    }

    public void setLang() {
        String key = SessionHandler.getInstance().get(SignInActivity.this, AppConstants.LANGUAGE_KEY); //= key+".json";

        //if (key == null) {
        if (key == null || key.isEmpty()) {
            SessionHandler.getInstance().save(SignInActivity.this, AppConstants.LANGUAGE_KEY, "en");
            SessionHandler.getInstance().save(SignInActivity.this, AppConstants.LANGUAGE, "English");
            key = SessionHandler.getInstance().get(SignInActivity.this, AppConstants.LANGUAGE_KEY);

        }

        String json = LoadJsonFromAsset(key + ".json");
        Gson gson = new Gson();
        Type listUserType = new TypeToken<LanguagePOJO>() {
        }.getType();
        LanguagePOJO langPOJO = gson.fromJson(json, listUserType);
        Utils.setLangInPref(langPOJO, SignInActivity.this);

        LanguagePOJO.Login logoinPage = getLoginScreenData(this);
        LanguagePOJO.AppKeys appKeysPage = getAppKeysPageScreenData(this);

        btnSignIn.setText(appKeysPage.getSignIn());
        btnPinSignIn.setText(appKeysPage.getSigninwithpin());

    }

    private String LoadJsonFromAsset(String fileName) {

        String json = null;
        try {
            InputStream is = this.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}