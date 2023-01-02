package dream.guys.hotdeskandroid.ui.login;

import static dream.guys.hotdeskandroid.utils.Utils.getAppKeysPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getLoginScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getResetPasswordPageScreencreenData;

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
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.ForgotPasswordRequest;
import dream.guys.hotdeskandroid.model.request.GetTokenRequest;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
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
                if (validateLoginDetails(etCompanyName.getText().toString().trim(),etEmail.getText().toString().trim())){
                    requestPasswordRest();
                }/*else {
                    Toast.makeText(ForgotPasswordActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

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