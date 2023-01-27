package com.brickendon.hdplus.ui.login;

import static com.brickendon.hdplus.utils.Utils.getAppKeysPageScreenData;
import static com.brickendon.hdplus.utils.Utils.getLoginScreenData;
import static com.brickendon.hdplus.utils.Utils.getResetPasswordPageScreencreenData;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.brickendon.hdplus.MainActivity;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.language.LanguagePOJO;
import com.brickendon.hdplus.model.request.GetTokenRequest;
import com.brickendon.hdplus.model.response.DAOActiveLocation;
import com.brickendon.hdplus.model.response.GetTokenResponse;
import com.brickendon.hdplus.model.response.TypeOfLoginResponse;
import com.brickendon.hdplus.model.response.UserDetailsResponse;
import com.brickendon.hdplus.ui.login.sso.B2CConfiguration;
import com.brickendon.hdplus.ui.login.sso.B2CUser;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.FirebaseNotificationService;
import com.brickendon.hdplus.utils.ProgressDialog;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    String TAG = "Zure SSo";

    Dialog dialog;

    @BindView(R.id.etTenantName)
    EditText etTenantName;
    @BindView(R.id.etCompanyName)
    EditText etCompanyName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.tvForgetPassword)
    TextView tvForgotPassword;
    @BindView(R.id.backToLogin)
    TextView tvBackToLogin;

    @BindView(R.id.btnSignInLogin)
    Button signIn;
    @BindView(R.id.btnLoginSso)
    Button signInSso;
    @BindView(R.id.ivWindows)
    ImageView ivWindows;

    @BindView(R.id.btnSsoLogin)
    Button btnLoginWithSso;

    @BindView(R.id.tenant_layout)
    RelativeLayout tenantLayout;
    @BindView(R.id.login_layout)
    RelativeLayout loginLayout;

    private List<B2CUser> users;
    IAccount IaccUser;
    /* Azure AD Variables */
    private IMultipleAccountPublicClientApplication b2cApp;
    String tentantName = "";

    //New...
    ArrayList<DAOActiveLocation> activeLocationArrayList = new ArrayList<>();
    int defaultLocationIdForCalendar=0,floorParentID = 0, cityPlaceID = 0, cityPlaceParentID = 0, cityID = 0, cityParentID = 0, locationID = 0, locationParentID = 0,
            floorPositon;

    String CountryName = null;
    String CityName = null;
    String buildingName = null;
    String floorName = null;
    String fullPathLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        dialog = new Dialog(LoginActivity.this);

        setLanguage();


        // Creates a PublicClientApplication object with res/raw/auth_config_single_account.json
        PublicClientApplication.createMultipleAccountPublicClientApplication(this,
                R.raw.auth_config_b2c,
                new IPublicClientApplication.IMultipleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(IMultipleAccountPublicClientApplication application) {
                        b2cApp = application;
                        System.out.println("sso check oncreate");
                        loadAccounts();
                    }

                    @Override
                    public void onError(MsalException exception) {
                        System.out.println("sso bala check" + exception.getMessage());
                        displayError(exception);
                    }
                });
        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenantLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);
            }
        });

        btnLoginWithSso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                startActivity(intent);
            */
                loginLayout.setVisibility(View.GONE);
                tenantLayout.setVisibility(View.VISIBLE);
//                tenantNamePopUp();

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
                if (validateLoginDetails(companyName, email, password)) {
                    doLogin(companyName, email, password);
                }

            }
        });
        signInSso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etTenantName.getText().toString().equalsIgnoreCase("") && !etTenantName.getText().toString().isEmpty()) {
                    tentantName = etTenantName.getText().toString();

//                    SessionHandler.getInstance().remove(MyApp.getContext(), AppConstants.USERTOKEN);
                    /*List<Pair<String, String>> extraQueryParameters = new ArrayList<>();
//                    extraQueryParameters.add( new Pair<String, String>("domain_hint", "google.com"));
                    extraQueryParameters.add( new Pair<String, String>("domain_hint", "hdplusdev"));
//                            extraQueryParameters.add( new Pair<String, String>("domain_hint",
//                                    typeOfLoginResponse.getMobileIdentityProvider()));


                    AcquireTokenParameters parameters = new AcquireTokenParameters.Builder()
                            .startAuthorizationFromActivity(LoginActivity.this)
//                            .fromAuthority(B2CConfiguration
//                                    .getAuthorityFromPolicyName("B2C_1A_signup_signin_Multitenant"))
                            .withScopes(B2CConfiguration.getScopes())
                            .withPrompt(Prompt.SELECT_ACCOUNT)
//                                    .withLoginHint(typeOfLoginResponse.getMobileIdentityProvider())
                            .withAuthorizationQueryStringParameters(extraQueryParameters)
                            .withCallback(getAuthInteractiveCallback())
                            .build();

                    b2cApp.acquireToken(parameters);*/

                    checkSsoEnabled();
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter a valid company name.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkSsoEnabled() {
        if (Utils.isNetworkAvailable(this)) {
            if(LoginActivity.this!=null &&!LoginActivity.this.isFinishing())
                dialog = ProgressDialog.showProgressBar(LoginActivity.this);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            SessionHandler.getInstance().remove(LoginActivity.this, AppConstants.USERTOKEN);
            JsonObject jsonObject = new JsonObject();
            if (!tentantName.equalsIgnoreCase(""))
                jsonObject.addProperty("tenantName", tentantName);
            Call<TypeOfLoginResponse> call = apiService.typeOfLogin(jsonObject);
            call.enqueue(new Callback<TypeOfLoginResponse>() {
                @Override
                public void onResponse(Call<TypeOfLoginResponse> call, Response<TypeOfLoginResponse> response) {
                    TypeOfLoginResponse typeOfLoginResponse = response.body();
                    if (response.code() == 200 && typeOfLoginResponse != null) {
                        if (typeOfLoginResponse.getTypeOfLogin() == 1) {
                            SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.TYPE_OF_LOGIN, typeOfLoginResponse.getTypeOfLogin());
                            ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);
                            Utils.toastShortMessage(LoginActivity.this, "SSO Login has not been set up, please contact Admin to Setup");
                        } else {
                            ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);

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
//                            extraQueryParameters.add( new Pair<String, String>("domain_hint", "google.com"));
                            extraQueryParameters.add(new Pair<String, String>("domain_hint",
                                    typeOfLoginResponse.getMobileIdentityProvider()));
                            System.out.println("domain_hint" + typeOfLoginResponse.getMobileIdentityProvider());

                            /*extraQueryParameters.add( new Pair<String, String>("domain_hint",
                                    "okta.com"));*/

                            AcquireTokenParameters parameters = new AcquireTokenParameters.Builder()
                                    .startAuthorizationFromActivity(LoginActivity.this)
                                    .fromAuthority(B2CConfiguration
                                            .getAuthorityFromPolicyName("b2c_1a_signup_signin_multitenant"))
                                    .withScopes(B2CConfiguration.getScopes())
                                    .withPrompt(Prompt.SELECT_ACCOUNT)
                                    .withAuthorizationQueryStringParameters(extraQueryParameters)
                                    .withCallback(getAuthInteractiveCallback())
                                    .build();

                            b2cApp.acquireToken(parameters);
                        }
                    } else if (response.code() == 403) {
                        ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);

                    } else {
                        ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);
                        Utils.toastShortMessage(LoginActivity.this, "SSO Login is not setup for this email contact admin.");
                    }
                }

                @Override
                public void onFailure(Call<TypeOfLoginResponse> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
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
                try{
                /* Successfully got a token, use it to call a protected resource - MSGraph */
                Log.d(TAG, "Successfully authenticated");
                System.out.println("bala sso" + authenticationResult.getAccount());
                /* display result info */
                IaccUser = authenticationResult.getAccount();

                displayResult(authenticationResult);

                /* Reload account asynchronously to get the up-to-date list. */
                loadAccounts();
                if(dialog!= null && dialog.isShowing())
                 dialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(MsalException exception) {
                try {
                    if(dialog!= null && dialog.isShowing())
                        dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "SSO AUth Failed", Toast.LENGTH_SHORT).show();
                    System.out.println("bala sso error" + exception.getMessage());
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
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancel() {
                /* User canceled the authentication */
                System.out.println("bala sso cancel");
                Log.d(TAG, "User cancelled login.");
            }
        };
    }

    private AuthenticationCallback getSignOutAuthInteractiveCallback() {
        return new AuthenticationCallback() {

            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                /* Successfully got a token, use it to call a protected resource - MSGraph */
                Log.d(TAG, "Successfully authenticated");
                System.out.println("bala sso" + authenticationResult.getAccount());
                /* display result info */
                IaccUser = authenticationResult.getAccount();

                displayResult(authenticationResult);

                /* Reload account asynchronously to get the up-to-date list. */
                loadAccounts();
                signOutAccounts(null);
               // dialog.dismiss();

            }

            @Override
            public void onError(MsalException exception) {
              //  dialog.dismiss();
                Toast.makeText(LoginActivity.this, "SSO AUth Failed", Toast.LENGTH_SHORT).show();
                System.out.println("bala sso error" + exception.getMessage());
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
                System.out.println("bala sso cancel");
                Log.d(TAG, "User cancelled login.");
            }
        };
    }

    private void signOutAccounts(IAccount account) {
        if (b2cApp == null) {
            return;
        }

        if (users.size() > 0) {
            final B2CUser selectedUser = users.get(0);
            selectedUser.signOutAsync(b2cApp,
                    new IMultipleAccountPublicClientApplication.RemoveAccountCallback() {
                        @Override
                        public void onRemoved() {
                            System.out.println("sso sign out if");
                            loadAccounts();
                        }

                        @Override
                        public void onError(@NonNull MsalException exception) {
                            System.out.println("sso sign out else");
                            displayError(exception);
                        }
                    });
        } else {
            System.out.println("sso sigout user not available");
        }

    }

    private void loadAccounts() {
        if (b2cApp == null) {
            return;
        }

        b2cApp.getAccounts(new IPublicClientApplication.LoadAccountsCallback() {
            @Override
            public void onTaskCompleted(final List<IAccount> result) {
//                Toast.makeText(LoginActivity.this, "b2c acc check"+result.size(), Toast.LENGTH_SHORT).show();
                users = B2CUser.getB2CUsersFromAccountList(result);
                System.out.println("sso check userList" + users.size());
//                updateUI(users);
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
                        "Tenant ID : " + result.getTenantId() + "\n" +
                        "email : " + result.getAccount().getUsername() + "\n";
        if (result.getAccessToken() != null)
            SessionHandler.getInstance().save(LoginActivity.this, AppConstants.USERTOKEN, result.getAccessToken());
        callTokenExachange(result.getAccount().getUsername());
        signOutAccounts(result.getAccount());
        Log.d(TAG, "displayResult: " + output);
    }

    private void callTokenExachange(String email) {
        if (Utils.isNetworkAvailable(this)) {
            if(LoginActivity.this!=null && !LoginActivity.this.isFinishing())
                dialog = ProgressDialog.showProgressBar(LoginActivity.this);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JsonObject jsonObject = new JsonObject();
            if (!tentantName.equalsIgnoreCase(""))
                jsonObject.addProperty("tenantName", tentantName);
            Call<GetTokenResponse> call = apiService.tokenExchange(jsonObject);
            call.enqueue(new Callback<GetTokenResponse>() {
                @Override
                public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {
                    if (response.code() == 200) {
                        GetTokenResponse getTokenResponse = response.body();
                        if (getTokenResponse != null) {
                            //Save token
                            SessionHandler.getInstance().save(LoginActivity.this, AppConstants.USERTOKEN, getTokenResponse.getToken());
                            SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.TYPE_OF_LOGIN, 2);

                            //GetUser Details Using Token
                            SessionHandler.getInstance().saveBoolean(LoginActivity.this, AppConstants.LOGIN_CHECK, true);
                            getUserDetailsUsingToken(getTokenResponse.getToken());
                        } else {
                            Utils.toastMessage(LoginActivity.this, "No Token Found");
                        }
                    } else if (response.code() == 403) {
                        Intent intent = new Intent(getApplicationContext(), GDPRActivity.class);
                        intent.putExtra("tenantName", tentantName);
                        intent.putExtra("userName", email);
                        startActivity(intent);
                    } else {
                        signOutAccounts(null);
                        Utils.toastShortMessage(LoginActivity.this, "SSO Login is not setup for this email contact admin.");
                    }
                }

                @Override
                public void onFailure(Call<GetTokenResponse> call, Throwable t) {

                }
            });
            ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);
        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }

    /**
     * Display the error message
     */
    private void displayError(@NonNull final Exception exception) {
        Log.d(TAG, "displayError: " + exception.toString());
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
            if(LoginActivity.this!=null &&!LoginActivity.this.isFinishing())
                dialog = ProgressDialog.showProgressBar(LoginActivity.this);
            SessionHandler.getInstance().remove(LoginActivity.this, AppConstants.USERTOKEN);
            GetTokenRequest getTokenRequest = new GetTokenRequest(companyName, email, password);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GetTokenResponse> call = apiService.getLoginToken(getTokenRequest);
            call.enqueue(new Callback<GetTokenResponse>() {
                @Override
                public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {

                    if (response.code() == 200) {
                        GetTokenResponse getTokenResponse = response.body();
                        if (getTokenResponse != null) {
                            //Save token
                            SessionHandler.getInstance().save(LoginActivity.this, AppConstants.USERTOKEN, getTokenResponse.getToken());

                            ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);

                            //GetUser Details Using Token

                            SessionHandler.getInstance().saveBoolean(LoginActivity.this, AppConstants.LOGIN_CHECK, true);
                            getUserDetailsUsingToken(getTokenResponse.getToken());
                        } else {
                            ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);
                            Utils.toastMessage(LoginActivity.this, "You have entered an incorrect username or password.  Please try again.");
//                            etEmail.requestFocus();
//                            etEmail.setError("You have entered the incorrect password. Please try again.");

                        }
                    } else if (response.code() == 401) {
                        ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);
                        Utils.toastMessage(LoginActivity.this, "You have entered an incorrect username or password.  Please try again.");
//                        etEmail.requestFocus();
//                        etEmail.setError("You have entered the incorrect password. Please try again.");
                    } else if (response.code() == 403) {
                        Intent intent = new Intent(getApplicationContext(), GDPRActivity.class);
                        intent.putExtra("tenantName", companyName);
                        intent.putExtra("userName", email);
                        startActivity(intent);
                    } else {
                        ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);
                        Utils.toastMessage(LoginActivity.this, "You have entered an incorrect username or password.  Please try again.");
                    }
                }

                @Override
                public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);

                }
            });


        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }

    //By Passing Token Get User Details
    private void getUserDetailsUsingToken(String token) {

        if (Utils.isNetworkAvailable(this)) {
            if(LoginActivity.this!=null &&!LoginActivity.this.isFinishing())
                dialog = ProgressDialog.showProgressBar(LoginActivity.this);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UserDetailsResponse> call = apiService.getLoginUserDetails();
            call.enqueue(new Callback<UserDetailsResponse>() {
                @Override
                public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                    if (response.code() == 200) {
                        UserDetailsResponse userDetailsResponse = response.body();

                        if (userDetailsResponse != null) {
                            //Save User credential for Biometric access
                            String companyName = etCompanyName.getText().toString();
                            String email = etEmail.getText().toString();
                            String password = etPassword.getText().toString();


                            sendFCMToken();


                            userDetailsResponse.getFullName();


                            SessionHandler.getInstance().save(LoginActivity.this, AppConstants.USERNAME, Utils.checkStringParms(userDetailsResponse.getFullName()));
                            SessionHandler.getInstance().save(LoginActivity.this, AppConstants.COMPANY_NAME, Utils.checkStringParms(companyName));
                            if (userDetailsResponse.getCurrentTeam() != null) {
                                SessionHandler.getInstance().save(LoginActivity.this, AppConstants.CURRENT_TEAM, Utils.checkStringParms(userDetailsResponse.getCurrentTeam().getCurrentTeamName()));
                                SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.TEAM_ID, Utils.checkStringParms(userDetailsResponse.getCurrentTeam().getCurrentTeamId()));
                            }
                            SessionHandler.getInstance().save(LoginActivity.this, AppConstants.PHONE_NUMBER, Utils.checkStringParms(userDetailsResponse.getPhoneNumber()));
                            SessionHandler.getInstance().save(LoginActivity.this, AppConstants.EMAIL, Utils.checkStringParms(email));
                            SessionHandler.getInstance().save(LoginActivity.this, AppConstants.PASSWORD, Utils.checkStringParms(email));
                            SessionHandler.getInstance().saveBoolean(LoginActivity.this, AppConstants.USER_DETAILS_SAVED_STATUS, true);
                            SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.TEAMMEMBERSHIP_ID, Utils.checkStringParms(userDetailsResponse.getTeamMembershipId()));

                            SessionHandler.getInstance().saveBoolean(LoginActivity.this, AppConstants.PIN_SETUP_DONE,
                                    userDetailsResponse.isHasPinSetup());

                            if(userDetailsResponse.isHasPinSetup()){
                                SessionHandler.getInstance().saveBoolean(LoginActivity.this,AppConstants.PIN_ACTIVE_STATUS_AFTER_LOGOUT,true);
                            }

                            //Log.d(TAG, "onResponse: "+userDetailsResponse.getDefaultLocation().getParentLocationId());
                            if (userDetailsResponse.getDefaultCarParkLocation() != null) {
                                SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.DEFAULT_CAR_PARK_LOCATION_ID,
                                        Utils.checkStringParms(userDetailsResponse.getDefaultCarParkLocation().getId()));
                            }

                            if (userDetailsResponse.getCurrentTeam() != null) {
                                SessionHandler.getInstance().save(LoginActivity.this, AppConstants.CURRENT_TEAM, userDetailsResponse.getCurrentTeam().getCurrentTeamName());
                                SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.TEAM_ID, userDetailsResponse.getCurrentTeam().getCurrentTeamId());
                            }

                            //Vehicle Number
                            if(userDetailsResponse.getVehicleRegNumber()!=null){
                                SessionHandler.getInstance().save(LoginActivity.this,AppConstants.VEHICLE_NUMBER,userDetailsResponse.getVehicleRegNumber());
                            }

                            if (userDetailsResponse.getDefaultLocation() != null) {
                                SessionHandler.getInstance().save(LoginActivity.this, AppConstants.DEFAULT_TIME_ZONE_ID, Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getTimeZoneId()));
                                SessionHandler.getInstance().save(LoginActivity.this, AppConstants.DEFAULT_LOCATION_NAME, Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getName()));
                                SessionHandler.getInstance().save(LoginActivity.this, AppConstants.DEFAULT_LOCATION_ID, String.valueOf(Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getParentLocationId())));
                                SessionHandler.getInstance().save(LoginActivity.this, AppConstants.DEFAULT_LOCATION_UNIQUE_ID, String.valueOf(Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getId())));
                            }

                            //Save UserId
                            SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.USER_ID, Utils.checkStringParms(userDetailsResponse.getId()));
                            SessionHandler.getInstance().save(LoginActivity.this, AppConstants.ROLE, Utils.checkStringParms(userDetailsResponse.getHighestRole()));

                            ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);

                            //New...
                            Gson gson = new Gson();
                            String json = gson.toJson(userDetailsResponse);
                            SessionHandler.getInstance().save(LoginActivity.this, AppConstants.LOGIN_RESPONSE, json);

                            //Check welcome screen viewed status
                            //boolean welcomeViewStatus=SessionHandler.getInstance().getBoolean(LoginActivity.this,AppConstants.WELCOME_VIEWED_STATUS);

                            //New...
                            if (userDetailsResponse.getDefaultLocation() != null) {
                                setLocate(userDetailsResponse.getDefaultLocation());
                            } else {
                                launchWelcomeActivity();
                            }

                        } else {
                            ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);
                            Utils.toastMessage(LoginActivity.this, "User Details Not Found");
                        }

                    } else if (response.code() == 401) {
                        ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);
                        Utils.toastMessage(LoginActivity.this, "Please Try Again");
                    }

                }

                @Override
                public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(LoginActivity.this, dialog);
                }
            });
        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }


    }


    private void sendFCMToken() {
        String tokenInSharedPreference = SessionHandler.getInstance().get(getApplicationContext(), AppConstants.SAVETOKEN);
        System.out.println("SharedPreferenceToken"+tokenInSharedPreference);
        Log.d(TAG, "SharedPreferenceToken"+tokenInSharedPreference);
        if (tokenInSharedPreference != null) {
            //System.out.println("tokenInSharedPreferenceInLoginActivity "+tokenInSharedPreference);
            FirebaseNotificationService.saveTokenInserver(tokenInSharedPreference);
        }
    }

    private boolean validateLoginDetails(String companyName, String email, String password) {
        boolean userDetailStatus = false;
        if (Utils.isValiedCompanyName(companyName)) {
            if (Utils.isValidEmail(email)) {
                if (Utils.isValiedText(password)) {
                    if (Utils.isValidPassword(password)) {
                        userDetailStatus = true;
                    } else {
                        etPassword.requestFocus();
                        etPassword.setError("You have entered the incorrect password. Please try again.");
                    }
                } else {
                    Utils.toastMessage(LoginActivity.this, "Please enter a valid password.");
                    //etPassword.setError("Please enter a valid password.");
                    etPassword.requestFocus();
                }
            } else {
               Utils.toastMessage(LoginActivity.this, "Please enter a valid email address.");
                etEmail.requestFocus();
                //etEmail.setError("Please enter a valid email address.");
            }
        } else {
           Utils.toastMessage(LoginActivity.this, "Please enter a valid company name.");
            etCompanyName.requestFocus();
            //etCompanyName.setError("Please enter a valid company name.");
        }


        return userDetailStatus;

    }

    private void launchHomeActivity() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void launchWelcomeActivity() {
//        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }

    public void setLanguage() {

        LanguagePOJO.Login logoinPage = getLoginScreenData(this);
        LanguagePOJO.AppKeys appKeysPage = getAppKeysPageScreenData(this);
        LanguagePOJO.ResetPassword resetPage = getResetPasswordPageScreencreenData(this);

        if (logoinPage != null) {

            //etTenantName.setHint(appKeysPage.getTenantName());
           etCompanyName.setHint(resetPage.getCompany());
            etEmail.setHint(appKeysPage.getEmailAddress());
            etPassword.setHint(appKeysPage.getPassword());
            tvForgotPassword.setText(appKeysPage.getForgotPassword() + "?");
            signIn.setText(appKeysPage.getSignIn());
            btnLoginWithSso.setText(appKeysPage.getSignInWithSso());
            //tvBackToLogin.setText(appKeysPage.getGoBackToSignIn());
            //signInSso.setText(abackToLoginppKeysPage.getSignInWithSso());
            //tvSignInWith.setText(appKeysPage.getOrSignInWith());
        }

    }

    //New...
    private void setLocate(UserDetailsResponse.DefaultLocation defaultLocation) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DAOActiveLocation>> call = apiService.getActiveLocations();
        call.enqueue(new Callback<ArrayList<DAOActiveLocation>>() {
            @Override
            public void onResponse(Call<ArrayList<DAOActiveLocation>> call, Response<ArrayList<DAOActiveLocation>> response) {

                if (response.body() != null && response.code() == 200 && response.body().size() > 0) {

                    activeLocationArrayList = new ArrayList<>();
                    activeLocationArrayList = response.body();

                    //parentLocationId
                    floorParentID = defaultLocation.getParentLocationId();

                    defaultLocationIdForCalendar = defaultLocation.getId();
                    //Default Location Id
                    Integer id = defaultLocation.getId();
                    floorName = defaultLocation.getName();

                    activeLocationArrayList = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(val -> val.getParentLocationId() != null).collect(Collectors.toList());

                    ArrayList<DAOActiveLocation> selectFloors = new ArrayList<>();
                    selectFloors = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(val -> val.getParentLocationId() == floorParentID).collect(Collectors.toList());
                    String finFloorName="";


                    ArrayList<DAOActiveLocation> getSupportZoneList = new ArrayList<>();
                    getSupportZoneList = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(m -> m.getId() == floorParentID).collect(Collectors.toList());

                    if(getSupportZoneList!=null && getSupportZoneList.size()>0) {

                        SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.SUPPORT_ZONE_ID, getSupportZoneList.get(0).getParentLocationId());

                    }

                    if(selectFloors!=null && selectFloors.size()>0) {

                        for (int i = 0; i < selectFloors.size(); i++) {

                            if (id.equals(selectFloors.get(i).getId())) {
                                floorPositon = i;
                                finFloorName = selectFloors.get(i).getName();
                                break;
                            }
                        }

                    }

                    //please add this for calendar default locations #Bala
                    SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.LOCATION_ID, defaultLocationIdForCalendar);
                    SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.PARENT_ID_CHECK, floorParentID);
                    SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.FLOOR_POSITION_CHECK, floorPositon);

                    //New...
                    SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.PARENT_ID, floorParentID);
                    SessionHandler.getInstance().saveInt(LoginActivity.this, AppConstants.FLOOR_POSITION, floorPositon);

                    ArrayList<DAOActiveLocation> buildingPlace = new ArrayList<>();
                    ArrayList<DAOActiveLocation> cityList = new ArrayList<>();
                    ArrayList<DAOActiveLocation> location = new ArrayList<>();

                    buildingPlace.addAll(activeLocationArrayList.stream().filter(val -> val.getId() == floorParentID).collect(Collectors.toList()));

                    if (buildingPlace.size() > 0) {
                        cityPlaceID = buildingPlace.get(0).getId();
                        cityPlaceParentID = buildingPlace.get(0).getParentLocationId();
                        buildingName = buildingPlace.get(0).getName();
                    }

                    cityList.addAll(activeLocationArrayList.stream().filter(val -> val.getId() == cityPlaceParentID).collect(Collectors.toList()));

                    if (cityList.size() > 0) {
                        cityID = cityList.get(0).getId();
                        cityParentID = cityList.get(0).getParentLocationId();
                        CityName = cityList.get(0).getName();
                    }

                    location.addAll(activeLocationArrayList.stream().filter(val -> val.getId() == cityParentID).collect(Collectors.toList()));

                    if (location.size() > 0) {
                        locationID = location.get(0).getId();
                        locationParentID = location.get(0).getParentLocationId();
                        CountryName = location.get(0).getName();
                    }

                    //System.out.println("LoginLocation "+buildingName+" "+CityName+" "+CountryName+" "+finFloorName);


                    //To load Default location

                    //Safety Purpose Block
                    //SessionHandler.getInstance().save(LoginActivity.this, AppConstants.FLOOR_CHECK, defaultLocation.getName());
                    //SessionHandler.getInstance().save(LoginActivity.this, AppConstants.FLOOR, defaultLocation.getName());
                    //End Safety Purpose Block

                    SessionHandler.getInstance().save(LoginActivity.this, AppConstants.COUNTRY_NAME_CHECK, CountryName);

                    if(CityName!=null) {
                        SessionHandler.getInstance().save(LoginActivity.this, AppConstants.BUILDING_CHECK, CityName);
                    }
                    if(buildingName!=null) {
                        SessionHandler.getInstance().save(LoginActivity.this, AppConstants.FLOOR_CHECK, buildingName);
                    }

                    SessionHandler.getInstance().save(LoginActivity.this,AppConstants.FINAL_FLOOR_CHECK,finFloorName);
                    SessionHandler.getInstance().save(LoginActivity.this, AppConstants.FULLPATHLOCATION_CHECK, fullPathLocation);



                    SessionHandler.getInstance().save(LoginActivity.this, AppConstants.COUNTRY_NAME, CountryName);

                    if(CityName!=null) {
                        SessionHandler.getInstance().save(LoginActivity.this, AppConstants.BUILDING, CityName);
                    }
                    if(buildingName!=null) {
                        SessionHandler.getInstance().save(LoginActivity.this, AppConstants.FLOOR, buildingName);
                    }

                    SessionHandler.getInstance().save(LoginActivity.this,AppConstants.FINAL_FLOOR,finFloorName);

                    SessionHandler.getInstance().save(LoginActivity.this, AppConstants.FULLPATHLOCATION, fullPathLocation);

                    launchWelcomeActivity();

                } else {
                    launchWelcomeActivity();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<DAOActiveLocation>> call, Throwable t) {
                launchWelcomeActivity();
            }
        });

    }


}