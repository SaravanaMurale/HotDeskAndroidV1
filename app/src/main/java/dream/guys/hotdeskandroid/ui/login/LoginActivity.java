package dream.guys.hotdeskandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.GetTokenRequest;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etCompanyName)
    EditText etCompanyName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btnSignIn)
    Button signIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String companyName = etCompanyName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                //Validate Input User Details
                if(validateLoginDetails(companyName, email, password)){
                    doLogin(companyName,email,password);
                }

            }
        });
    }



    //GetToken
    private void doLogin(String companyName, String email, String password) {

        if (Utils.isNetworkAvailable(this)) {
            GetTokenRequest getTokenRequest = new GetTokenRequest(companyName, email, password);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GetTokenResponse> call = apiService.getLoginToken(getTokenRequest);
            call.enqueue(new Callback<GetTokenResponse>() {
                @Override
                public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {

                    if(response.code()==200){
                        GetTokenResponse getTokenResponse = response.body();
                        if (getTokenResponse != null) {
                            //Save token
                            SessionHandler.getInstance().save(LoginActivity.this, AppConstants.USERTOKEN, getTokenResponse.getToken());

                            //System.out.println("ReceivedToken" + getTokenResponse.getToken());
                            //System.out.println("ReceivedExpiration" + getTokenResponse.getExpiration());
                            //String token=getTokenResponse.getExpiration();

                            //GetUser Details Using Token
                            getUserDetailsUsingToken(getTokenResponse.getToken());
                        } else {
                            Utils.toastMessage(LoginActivity.this, "No Token Found");
                        }
                    }else if(response.code()==401){
                        Utils.toastMessage(LoginActivity.this, "Wrong userName or password");
                    }
                }

                @Override
                public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                    Utils.toastMessage(LoginActivity.this, "You have entered wrong username or password");

                }
            });


        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }


    }

    //By Passing Token Get User Details
    private void getUserDetailsUsingToken(String token) {

        if (Utils.isNetworkAvailable(this)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UserDetailsResponse> call = apiService.getLoginUserDetails();
            call.enqueue(new Callback<UserDetailsResponse>() {
                @Override
                public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                    if(response.code()==200){
                        UserDetailsResponse userDetailsResponse = response.body();

                        if (userDetailsResponse != null) {
                            //Save User credential for Biometric access
                            String companyName = etCompanyName.getText().toString();
                            String email = etEmail.getText().toString();
                            String password = etPassword.getText().toString();


                            userDetailsResponse.getFullName();

                            SessionHandler.getInstance().save(LoginActivity.this,AppConstants.USERNAME,userDetailsResponse.getFullName());
                            SessionHandler.getInstance().save(LoginActivity.this,AppConstants.COMPANY_NAME,companyName);
                            SessionHandler.getInstance().save(LoginActivity.this,AppConstants.EMAIL,email);
                            SessionHandler.getInstance().save(LoginActivity.this,AppConstants.PASSWORD,password);
                            SessionHandler.getInstance().saveBoolean(LoginActivity.this,AppConstants.USER_DETAILS_SAVED_STATUS,true);

                            //Save UserId
                            SessionHandler.getInstance().saveInt(LoginActivity.this,AppConstants.USER_ID,userDetailsResponse.getId());

                            //Check welcome screen viewed status
                            //boolean welcomeViewStatus=SessionHandler.getInstance().getBoolean(LoginActivity.this,AppConstants.WELCOME_VIEWED_STATUS);
                            launchWelcomeActivity();

                        } else {
                            Utils.toastMessage(LoginActivity.this, "User Details Not Found");
                        }

                    }else if(response.code()==401){
                        Utils.toastMessage(LoginActivity.this, "Please Try Again");
                    }

                }

                @Override
                public void onFailure(Call<UserDetailsResponse> call, Throwable t) {

                }
            });
        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }


    }


    private boolean validateLoginDetails(String companyName, String email, String password) {

        boolean userDetailStatus = false;

        if (Utils.isValiedText(companyName)) {
            if (Utils.isValidEmail(email)) {
                if (Utils.isValiedText(password)) {
                    userDetailStatus=true;
                } else {
                    Utils.toastMessage(LoginActivity.this, "Pls Enter Valid Password");
                }
            } else {
                Utils.toastMessage(LoginActivity.this, "Pls Enter Valid Email");
            }
        } else {
            Utils.toastMessage(LoginActivity.this, "Pls Enter Valid CompanyName");
        }


        return userDetailStatus;

    }

    private void launchHomeActivity() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void launchWelcomeActivity() {

        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();

    }
}