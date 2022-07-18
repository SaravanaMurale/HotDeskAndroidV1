package dream.guys.hotdeskandroid.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.identity.client.AcquireTokenParameters;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IMultipleAccountPublicClientApplication;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.Prompt;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalClientException;
import com.microsoft.identity.client.exception.MsalException;
import com.microsoft.identity.client.exception.MsalServiceException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.GetTokenRequest;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.ui.login.sso.B2CConfiguration;
import dream.guys.hotdeskandroid.ui.login.sso.B2CUser;
import dream.guys.hotdeskandroid.ui.login.sso.WebViewActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
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
    String TAG="Zure SSo";

    Dialog dialog;

    @BindView(R.id.etCompanyName)
    EditText etCompanyName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.tvForgetPassword)
    TextView tvForgotPassword;

    @BindView(R.id.btnSignInLogin)
    Button signIn;
    @BindView(R.id.ivWindows)
    ImageView ivWindows;

    private List<B2CUser> users;

    /* Azure AD Variables */
    private IMultipleAccountPublicClientApplication b2cApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        dialog = new Dialog(LoginActivity.this);

        // Creates a PublicClientApplication object with res/raw/auth_config_single_account.json
        PublicClientApplication.createMultipleAccountPublicClientApplication(this,
                R.raw.auth_config_b2c,
                new IPublicClientApplication.IMultipleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(IMultipleAccountPublicClientApplication application) {
                        b2cApp = application;
                        loadAccounts();
                    }

                    @Override
                    public void onError(MsalException exception) {
                        displayError(exception);
//                        removeAccountButton.setEnabled(false);
//                        runUserFlowButton.setEnabled(false);
//                        acquireTokenSilentButton.setEnabled(false);
                    }
                });

        ivWindows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                startActivity(intent);
            */
                if (b2cApp == null) {
                    return;
                }

                /**
                 * Runs user flow interactively.
                 * <p>
                 * Once the user finishes with the flow, you will also receive an access token containing the claims for the scope you passed in (see B2CConfiguration.getScopes()),
                 * which you can subsequently use to obtain your resources.
                 */
                List<Pair<String, String>> extraQueryParameters = new ArrayList<>();
                extraQueryParameters.add( new Pair<String, String>("domain_hint", "google.com"));
                extraQueryParameters.add( new Pair<String, String>("response_type", "code+id_token"));
                extraQueryParameters.add( new Pair<String, String>("response_mode", "query"));
                extraQueryParameters.add( new Pair<String, String>("scope", "openid%20offline_access&state=12345"));

                AcquireTokenParameters parameters = new AcquireTokenParameters.Builder()
                        .startAuthorizationFromActivity(LoginActivity.this)
                        .withAuthorizationQueryStringParameters(extraQueryParameters)
                        // More settings here
                        .build();

                b2cApp.acquireToken(parameters);
/*

                AcquireTokenParameters parameters = new AcquireTokenParameters.Builder()
                        .startAuthorizationFromActivity(LoginActivity.this)
                        .withAuthorizationQueryStringParameters(extraQueryParameters)
                        .fromAuthority(B2CConfiguration.getAuthorityFromPolicyName("B2C_1A_signup_signin_Multitenant"))
//                        .fromAuthority(B2CConfiguration.getAuthorityFromPolicyName("B2C_1A_signup_signin_Multitenant"policyListSpinner.getSelectedItem().toString()))
                        .withScopes(B2CConfiguration.getScopes())
                        .withPrompt(Prompt.LOGIN)
                        .withCallback(getAuthInteractiveCallback())
                        .build();

                b2cApp.acquireToken(parameters);
*/

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String companyName = etCompanyName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                //Validate Input User Details
                if(validateLoginDetails(companyName, email, password)){
                    doLogin(companyName,email,password);
                }

            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Callback used for interactive request.
     * If succeeds we use the access token to call the Microsoft Graph.
     * Does not check cache.
     */
    private AuthenticationCallback getAuthInteractiveCallback() {
        return new AuthenticationCallback() {

            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                /* Successfully got a token, use it to call a protected resource - MSGraph */
                Log.d(TAG, "Successfully authenticated");

                /* display result info */
                displayResult(authenticationResult);

                /* Reload account asynchronously to get the up-to-date list. */
                loadAccounts();
            }

            @Override
            public void onError(MsalException exception) {
                final String B2C_PASSWORD_CHANGE = "AADB2C90118";
                if (exception.getMessage().contains(B2C_PASSWORD_CHANGE)) {
                    Log.d(TAG, "onError: The user clicks the 'Forgot Password' link in a sign-up or sign-in user flow.\\n\" +\n" +
                            "                            \"Your application needs to handle this error code by running a specific user flow that resets the password.");
                    return;
                }

                /* Failed to acquireToken */
                Log.d(TAG, "Authentication failed: " + exception.toString());
                displayError(exception);

                if (exception instanceof MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception instanceof MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                }
            }

            @Override
            public void onCancel() {
                /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.");
            }
        };
    }

    private void loadAccounts() {
        if (b2cApp == null) {
            return;
        }

        b2cApp.getAccounts(new IPublicClientApplication.LoadAccountsCallback() {
            @Override
            public void onTaskCompleted(final List<IAccount> result) {
                users = B2CUser.getB2CUsersFromAccountList(result);
                updateUI(users);
            }

            @Override
            public void onError(MsalException exception) {
                displayError(exception);
            }
        });
    }
    /**
     * Display the graph response
     */
    private void displayResult(@NonNull final IAuthenticationResult result) {
        final String output =
                "Access Token :" + result.getAccessToken() + "\n" +
                        "Scope : " + result.getScope() + "\n" +
                        "Expiry : " + result.getExpiresOn() + "\n" +
                        "Tenant ID : " + result.getTenantId() + "\n";

        Log.d(TAG, "displayResult: "+output);
    }


    /**
     * Display the error message
     */
    private void displayError(@NonNull final Exception exception) {
        Log.d(TAG, "displayError: "+exception.toString());
    }

    /**
     * Updates UI based on the obtained user list.
     */
    private void updateUI(final List<B2CUser> users) {
        if (users.size() != 0) {
//            removeAccountButton.setEnabled(true);
//            acquireTokenSilentButton.setEnabled(true);
        } else {
//            removeAccountButton.setEnabled(false);
//            acquireTokenSilentButton.setEnabled(false);
        }

    }

    //GetToken
    private void doLogin(String companyName, String email, String password) {

        if (Utils.isNetworkAvailable(this)) {

            dialog=ProgressDialog.showProgressBar(LoginActivity.this);
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

                            ProgressDialog.dismisProgressBar(LoginActivity.this,dialog);

                            //GetUser Details Using Token

                            SessionHandler.getInstance().saveBoolean(LoginActivity.this, AppConstants.LOGIN_CHECK,true);
                            getUserDetailsUsingToken(getTokenResponse.getToken());
                        } else {
                            ProgressDialog.dismisProgressBar(LoginActivity.this,dialog);
                            Utils.toastMessage(LoginActivity.this, "No Token Found");
                        }
                    }else if(response.code()==401){
                        ProgressDialog.dismisProgressBar(LoginActivity.this,dialog);
                        Utils.toastMessage(LoginActivity.this, "Wrong userName or password");
                    }else if(response.code()==403){
                        Intent intent = new Intent(getApplicationContext(),GDPRActivity.class);
                        intent.putExtra("tenantName",companyName);
                        intent.putExtra("userName",email);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(LoginActivity.this,dialog);
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
            dialog=ProgressDialog.showProgressBar(LoginActivity.this);
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
                            SessionHandler.getInstance().save(LoginActivity.this,AppConstants.CURRENT_TEAM,userDetailsResponse.getCurrentTeam().getCurrentTeamName());
                            SessionHandler.getInstance().save(LoginActivity.this,AppConstants.EMAIL,email);
                            SessionHandler.getInstance().save(LoginActivity.this,AppConstants.PASSWORD,password);
                            SessionHandler.getInstance().saveBoolean(LoginActivity.this,AppConstants.USER_DETAILS_SAVED_STATUS,true);
                            SessionHandler.getInstance().saveInt(LoginActivity.this,AppConstants.TEAMMEMBERSHIP_ID,userDetailsResponse.getTeamMembershipId());
                            SessionHandler.getInstance().saveInt(LoginActivity.this,AppConstants.TEAM_ID,userDetailsResponse.getCurrentTeam().getCurrentTeamId());
                            SessionHandler.getInstance().saveBoolean(LoginActivity.this,AppConstants.PIN_SETUP_DONE,userDetailsResponse.isHasPinSetup());
                            System.out.println("login chec"+SessionHandler.getInstance().getBoolean(LoginActivity.this,AppConstants.PIN_SETUP_DONE));
                            System.out.println("login chec respos"+userDetailsResponse.isHasPinSetup());

                            //Save UserId
                            SessionHandler.getInstance().saveInt(LoginActivity.this,AppConstants.USER_ID,userDetailsResponse.getId());

                            ProgressDialog.dismisProgressBar(LoginActivity.this,dialog);

                            //Check welcome screen viewed status
                            //boolean welcomeViewStatus=SessionHandler.getInstance().getBoolean(LoginActivity.this,AppConstants.WELCOME_VIEWED_STATUS);
                            launchWelcomeActivity();

                        } else {
                            ProgressDialog.dismisProgressBar(LoginActivity.this,dialog);
                            Utils.toastMessage(LoginActivity.this, "User Details Not Found");
                        }

                    }else if(response.code()==401){
                        ProgressDialog.dismisProgressBar(LoginActivity.this,dialog);
                        Utils.toastMessage(LoginActivity.this, "Please Try Again");
                    }

                }

                @Override
                public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(LoginActivity.this,dialog);
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

    @Override
    public void onBackPressed() {

    }
}