package com.brickendon.hdplus.ui.login;

import static com.brickendon.hdplus.utils.MyApp.getContext;
import static com.brickendon.hdplus.utils.Utils.getAppKeysPageScreenData;
import static com.brickendon.hdplus.utils.Utils.getResetPasswordPageScreencreenData;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.language.LanguagePOJO;
import com.brickendon.hdplus.model.request.ForgotPasswordRequest;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.ProgressDialog;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    Dialog dialog;
    @BindView(R.id.btnSubmit)
    Button submit;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etCompanyName)
    EditText etCompanyName;
    @BindView(R.id.tv_back_to_sign_in)
    TextView gobacktoSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        dialog = new Dialog(ForgotPasswordActivity.this);

        LanguagePOJO.AppKeys appKeysPage = getAppKeysPageScreenData(this);
        LanguagePOJO.ResetPassword resetPage = getResetPasswordPageScreencreenData(this);

        etCompanyName.setHint(resetPage.getCompany());
        etEmail.setHint(appKeysPage.getEmailAddress());
        gobacktoSignIn.setText(appKeysPage.getGoBackToSignIn());



        gobacktoSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoginRegion(etCompanyName.getText().toString().trim().toLowerCase(),false);
                if (validateLoginDetails(etCompanyName.getText().toString().trim(),etEmail.getText().toString().trim())){
                    requestPasswordRest();
                }/*else {
                    Toast.makeText(ForgotPasswordActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    public void getLoginRegion(String tenantName, boolean isSSO) {
        if (Utils.isNetworkAvailable(getContext())) {
            AppConstants.BASE_URL_DYNAMIC = AppConstants.BASE_URL;

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<String>> call = apiService.getLoginRegion(tenantName);
            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    if (response.code() == 200) {
                        List<String> regionList = response.body();
                        String baseUrl="";
                        if (regionList!=null){
                            if (regionList.size()>0){
                                baseUrl= regionList.get(regionList.size()-1);
                                setBaseUrl(baseUrl,isSSO);
                            } else {
                                SessionHandler.getInstance().save(getContext(),
                                        AppConstants.BASE_URL_DYNAMIC,AppConstants.BASE_URL);

                            }
                        } else {
                            SessionHandler.getInstance().save(getContext(),
                                    AppConstants.BASE_URL_DYNAMIC_SESSION,AppConstants.BASE_URL);

                        }
                    } else {
                        SessionHandler.getInstance().save(getContext(),
                                AppConstants.BASE_URL_DYNAMIC_SESSION,AppConstants.BASE_URL);

                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    SessionHandler.getInstance().save(getContext(),
                            AppConstants.BASE_URL_DYNAMIC_SESSION,AppConstants.BASE_URL);

                }
            });
        } else {
            Utils.toastMessage(getContext(), "Please Enable Internet");
        }
    }

    private void setBaseUrl(String baseUrl, boolean isSSO) {
        switch (baseUrl) {
            case "DEV":
                AppConstants.BASE_URL_DYNAMIC ="https://dev-api.hybridhero.com/";
                SessionHandler.getInstance().save(getContext(),
                        AppConstants.BASE_URL_DYNAMIC_SESSION,"https://dev-api.hybridhero.com/");
                break;
            case "PROD":
                AppConstants.BASE_URL_DYNAMIC ="https://api.hybridhero.com/";
                SessionHandler.getInstance().save(getContext(),
                        AppConstants.BASE_URL_DYNAMIC_SESSION,"https://api.hybridhero.com/");
                break;
            case "PREPROD":
                AppConstants.BASE_URL_DYNAMIC = "https://preprod-api.hotdeskplus.com/";
                SessionHandler.getInstance().save(getContext(),
                        AppConstants.BASE_URL_DYNAMIC_SESSION,"https://preprod-api.hotdeskplus.com/");
                break;
            case "PRODAUSTRALIA":
                AppConstants.BASE_URL_DYNAMIC = "https://api.hybridhero.com.au/";
                SessionHandler.getInstance().save(getContext(),
                        AppConstants.BASE_URL_DYNAMIC_SESSION,"https://api.hybridhero.com.au/");
                break;
            default:
        }

        if (validateLoginDetails(etCompanyName.getText().toString().trim(),
                etEmail.getText().toString().trim())){
            requestPasswordRest();
        }
    }

    private void requestPasswordRest() {
            if (Utils.isNetworkAvailable(this)) {

                dialog= ProgressDialog.showProgressBar(ForgotPasswordActivity.this);
//                GetTokenRequest getTokenRequest = new GetTokenRequest(companyName, email, password);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
                forgotPasswordRequest.setTenantName(etCompanyName.getText().toString().trim());
                forgotPasswordRequest.setUserName(etEmail.getText().toString().trim());
                SessionHandler.getInstance().remove(ForgotPasswordActivity.this,AppConstants.USERTOKEN);
                Call<Boolean> call = apiService.requestPasswordReset(forgotPasswordRequest);
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.code()==200 && response.body()){
                            ProgressDialog.dismisProgressBar(ForgotPasswordActivity.this,dialog);
                            Utils.toastMessage(
                                    ForgotPasswordActivity.this, "You will receive an email shortly with the instructions on how to reset your password.");
                            finish();
                        }
                        else {
                            ProgressDialog.dismisProgressBar(ForgotPasswordActivity.this,dialog);
                            Utils.toastMessage(ForgotPasswordActivity.this, "Email or company is invalid");
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        ProgressDialog.dismisProgressBar(ForgotPasswordActivity.this,dialog);
                        Utils.toastMessage(ForgotPasswordActivity.this, "Email or company is invalid");

                    }
                });


            } else {
                Utils.toastMessage(this, "Please Enable Internet");
            }
            

    }

    private boolean validateLoginDetails(String companyName, String email) {

        boolean userDetailStatus = false;

        if (Utils.isValiedCompanyName(companyName)) {
            if (Utils.isValidEmail(email)) {
                userDetailStatus=true;
            } else {
               Utils.toastMessage(ForgotPasswordActivity.this, "Please enter a valid email address.");
               // etEmail.setError("Please enter a valid email address.");
            }
        } else {
           // etCompanyName.setError("Please enter a valid company name.");
           Utils.toastMessage(ForgotPasswordActivity.this, "Please enter a valid company name.");
        }

        return userDetailStatus;

    }

    @Override
    public void onBackPressed() {

    }
}