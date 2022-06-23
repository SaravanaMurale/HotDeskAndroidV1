package dream.guys.hotdeskandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.GetTokenRequest;
import dream.guys.hotdeskandroid.model.GetTokenResponse;
import dream.guys.hotdeskandroid.model.UserDetailsResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

                String companyName=etCompanyName.getText().toString();
                String email=etEmail.getText().toString();
                String password=etPassword.getText().toString();

                //Validate Input User Details
                //validateLoginDetails(companyName,email,password);

                doLogin(companyName,email,password);

            }
        });
    }

    private void doLogin(String companyName, String email, String password) {

        if (Utils.isNetworkAvailable(this)) {
            GetTokenRequest getTokenRequest=new GetTokenRequest(companyName,email,password);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GetTokenResponse> call = apiService.getLoginToken(getTokenRequest);
            call.enqueue(new Callback<GetTokenResponse>() {
                @Override
                public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {

                    GetTokenResponse getTokenResponse=response.body();

                    if(getTokenResponse!=null){
                        SessionHandler.getInstance().save(LoginActivity.this, AppConstants.USERTOKEN,getTokenResponse.getToken());

                        Log.e("LoginActivity",getTokenResponse.getToken());
                        Log.e("LoginActivity",getTokenResponse.getExpiration());

                        System.out.println("ReceivedToken"+ getTokenResponse.getToken());
                        System.out.println("ReceivedExpiration"+ getTokenResponse.getExpiration());

                        getUserDetailsUsingToken(getTokenResponse.getToken());
                    }else {
                        Utils.toastMessage(LoginActivity.this,"You have entered wrong username or password");
                    }



                }

                @Override
                public void onFailure(Call<GetTokenResponse> call, Throwable t) {

                    Utils.toastMessage(LoginActivity.this,"You have entered wrong username or password");

                }
            });


        }else {
            Utils.toastMessage(this, "Please Enable Internet");
        }


    }

    private void getUserDetailsUsingToken(String token) {

        if (Utils.isNetworkAvailable(this)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UserDetailsResponse> call = apiService.getLoginUserDetails();
            call.enqueue(new Callback<UserDetailsResponse>() {
                @Override
                public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                    UserDetailsResponse userDetailsResponse=response.body();

                    if(userDetailsResponse!=null){
                        System.out.println("UserDetails"+userDetailsResponse.getFirstName()+" "+userDetailsResponse.getEmail());
                        launchHomeActivity();
                    }else {
                        Utils.toastMessage(LoginActivity.this,"User Details Not Found");
                    }




                }

                @Override
                public void onFailure(Call<UserDetailsResponse> call, Throwable t) {

                }
            });
        }else {
            Utils.toastMessage(this, "Please Enable Internet");
        }




    }

    private void launchHomeActivity() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }
}