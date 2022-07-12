package dream.guys.hotdeskandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        dialog = new Dialog(ForgotPasswordActivity.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLoginDetails(etCompanyName.getText().toString(),etEmail.getText().toString())){
                    requestPasswordRest();
                }else {
                    Toast.makeText(ForgotPasswordActivity.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void requestPasswordRest() {
            if (Utils.isNetworkAvailable(this)) {

                dialog= ProgressDialog.showProgressBar(ForgotPasswordActivity.this);
//                GetTokenRequest getTokenRequest = new GetTokenRequest(companyName, email, password);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<GetTokenResponse> call = apiService.requestPasswordReset(etCompanyName.getText().toString(),
                        etEmail.getText().toString());
                call.enqueue(new Callback<GetTokenResponse>() {
                    @Override
                    public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {
                        if(response.code()==200){
                            ProgressDialog.dismisProgressBar(ForgotPasswordActivity.this,dialog);
                            Utils.toastMessage(ForgotPasswordActivity.this, "Success");
                            finish();
                        }else if(response.code()==401){
                            ProgressDialog.dismisProgressBar(ForgotPasswordActivity.this,dialog);
                            Utils.toastMessage(ForgotPasswordActivity.this, "Wrong userName or password");
                        }
                        else {
                            ProgressDialog.dismisProgressBar(ForgotPasswordActivity.this,dialog);
                            Utils.toastMessage(ForgotPasswordActivity.this, "Failure");

                        }
                    }

                    @Override
                    public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                        ProgressDialog.dismisProgressBar(ForgotPasswordActivity.this,dialog);
                        Utils.toastMessage(ForgotPasswordActivity.this, "You have entered wrong username or password");

                    }
                });


            } else {
                Utils.toastMessage(this, "Please Enable Internet");
            }
            

    }

    private boolean validateLoginDetails(String companyName, String email) {

        boolean userDetailStatus = false;

        if (Utils.isValiedText(companyName)) {
            if (Utils.isValidEmail(email)) {
                userDetailStatus=true;
            } else {
                Utils.toastMessage(ForgotPasswordActivity.this, "Pls Enter Valid Email");
            }
        } else {
            Utils.toastMessage(ForgotPasswordActivity.this, "Pls Enter Valid CompanyName");
        }

        return userDetailStatus;

    }

}