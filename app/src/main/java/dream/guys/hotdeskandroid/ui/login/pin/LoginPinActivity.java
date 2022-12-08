package dream.guys.hotdeskandroid.ui.login.pin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.CreatePinRequest;
import dream.guys.hotdeskandroid.model.response.CheckPinLoginResponse;
import dream.guys.hotdeskandroid.model.response.DAOActiveLocation;
import dream.guys.hotdeskandroid.model.response.GetTokenResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.ui.login.LoginActivity;
import dream.guys.hotdeskandroid.ui.login.SignInActivity;
import dream.guys.hotdeskandroid.ui.login.WelcomeActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.FirebaseNotificationService;
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

    ArrayList<DAOActiveLocation> activeLocationArrayList = new ArrayList<>();
    int floorParentID = 0, cityPlaceID = 0, cityPlaceParentID = 0, cityID = 0, cityParentID = 0, locationID = 0, locationParentID = 0,
            floorPositon;

    String CountryName = null;
    String CityName = null;
    String buildingName = null;
    String floorName = null;
    String fullPathLocation = null;

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
                }else if (pin.length() < 6) {
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
                Intent intent=new Intent(LoginPinActivity.this, LoginPinActivity.class);
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
                            getUserDetailsUsingToken(SessionHandler.getInstance().get(LoginPinActivity.this,AppConstants.USERTOKEN));
/*

                            //New M
                            SessionHandler.getInstance().saveBoolean(LoginPinActivity.this,AppConstants.PIN_SETUP_DONE,true);

                            Intent intent=new Intent(LoginPinActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();*/
                        }
                    } else if(response.code() == 401){
                        ProgressDialog.dismisProgressBar(LoginPinActivity.this,dialog);
                        Utils.toastMessage(LoginPinActivity.this, "Invalid Pin ");

                    }else {
                        ProgressDialog.dismisProgressBar(LoginPinActivity.this,dialog);
                        Utils.toastMessage(LoginPinActivity.this, "Invalid Pin ");
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

    private void getUserDetailsUsingToken(String token) {

        if (Utils.isNetworkAvailable(this)) {
            dialog = ProgressDialog.showProgressBar(LoginPinActivity.this);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UserDetailsResponse> call = apiService.getLoginUserDetails();
            call.enqueue(new Callback<UserDetailsResponse>() {
                @Override
                public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                    if (response.code() == 200) {
                        UserDetailsResponse userDetailsResponse = response.body();

                        if (userDetailsResponse != null) {
                            //Save User credential for Biometric access
//                            String companyName = etCompanyName.getText().toString();
//                            String email = etEmail.getText().toString();
//                            String password = etPassword.getText().toString();


//                            sendFCMToken();


                            userDetailsResponse.getFullName();


                            SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.USERNAME, Utils.checkStringParms(userDetailsResponse.getFullName()));
//                            SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.COMPANY_NAME, Utils.checkStringParms(companyName));
                            if (userDetailsResponse.getCurrentTeam() != null) {
                                SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.CURRENT_TEAM, Utils.checkStringParms(userDetailsResponse.getCurrentTeam().getCurrentTeamName()));
                                SessionHandler.getInstance().saveInt(LoginPinActivity.this, AppConstants.TEAM_ID, Utils.checkStringParms(userDetailsResponse.getCurrentTeam().getCurrentTeamId()));
                            }
                            SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.PHONE_NUMBER, Utils.checkStringParms(userDetailsResponse.getPhoneNumber()));
//                            SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.EMAIL, Utils.checkStringParms(email));
//                            SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.PASSWORD, Utils.checkStringParms(email));
                            SessionHandler.getInstance().saveBoolean(LoginPinActivity.this, AppConstants.USER_DETAILS_SAVED_STATUS, true);
                            SessionHandler.getInstance().saveInt(LoginPinActivity.this, AppConstants.TEAMMEMBERSHIP_ID, Utils.checkStringParms(userDetailsResponse.getTeamMembershipId()));

                            SessionHandler.getInstance().saveBoolean(LoginPinActivity.this, AppConstants.PIN_SETUP_DONE, userDetailsResponse.isHasPinSetup());
                            //Log.d(TAG, "onResponse: "+userDetailsResponse.getDefaultLocation().getParentLocationId());
                            if (userDetailsResponse.getDefaultCarParkLocation() != null) {
                                SessionHandler.getInstance().saveInt(LoginPinActivity.this, AppConstants.DEFAULT_CAR_PARK_LOCATION_ID,
                                        Utils.checkStringParms(userDetailsResponse.getDefaultCarParkLocation().getId()));
                            }

                            if (userDetailsResponse.getCurrentTeam() != null) {
                                SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.CURRENT_TEAM, userDetailsResponse.getCurrentTeam().getCurrentTeamName());
                                SessionHandler.getInstance().saveInt(LoginPinActivity.this, AppConstants.TEAM_ID, userDetailsResponse.getCurrentTeam().getCurrentTeamId());
                            }

                            //Vehicle Number
                            if(userDetailsResponse.getVehicleRegNumber()!=null){
                                SessionHandler.getInstance().save(LoginPinActivity.this,AppConstants.VEHICLE_NUMBER,userDetailsResponse.getVehicleRegNumber());
                            }

                            if (userDetailsResponse.getDefaultLocation() != null) {
                                SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.DEFAULT_TIME_ZONE_ID, Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getTimeZoneId()));
                                SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.DEFAULT_LOCATION_NAME, Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getName()));
                                SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.DEFAULT_LOCATION_ID, String.valueOf(Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getParentLocationId())));
                                SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.DEFAULT_LOCATION_UNIQUE_ID, String.valueOf(Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getId())));
                            }

                            //Save UserId
                            SessionHandler.getInstance().saveInt(LoginPinActivity.this, AppConstants.USER_ID, Utils.checkStringParms(userDetailsResponse.getId()));
                            SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.ROLE, Utils.checkStringParms(userDetailsResponse.getHighestRole()));

                            ProgressDialog.dismisProgressBar(LoginPinActivity.this, dialog);

                            //New...
                            Gson gson = new Gson();
                            String json = gson.toJson(userDetailsResponse);
                            SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.LOGIN_RESPONSE, json);

                            //Check welcome screen viewed status
                            //boolean welcomeViewStatus=SessionHandler.getInstance().getBoolean(LoginPinActivity.this,AppConstants.WELCOME_VIEWED_STATUS);

                            //New...
                            if (userDetailsResponse.getDefaultLocation() != null) {
                                setLocate(userDetailsResponse.getDefaultLocation());
                            } else {
                                launchWelcomeActivity();
                            }

                        } else {
                            ProgressDialog.dismisProgressBar(LoginPinActivity.this, dialog);
                            Utils.toastMessage(LoginPinActivity.this, "User Details Not Found");
                        }

                    } else if (response.code() == 401) {
                        ProgressDialog.dismisProgressBar(LoginPinActivity.this, dialog);
                        Utils.toastMessage(LoginPinActivity.this, "Please Try Again");
                    }

                }

                @Override
                public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                    ProgressDialog.dismisProgressBar(LoginPinActivity.this, dialog);
                }
            });
        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }

    private void sendFCMToken() {
        String tokenInSharedPreference = SessionHandler.getInstance().get(getApplicationContext(), AppConstants.SAVETOKEN);
        if (tokenInSharedPreference != null) {
            FirebaseNotificationService.saveTokenInserver(tokenInSharedPreference);
        }
    }

    private void setLocate(UserDetailsResponse.DefaultLocation defaultLocation) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DAOActiveLocation>> call = apiService.getActiveLocations();
        call.enqueue(new Callback<ArrayList<DAOActiveLocation>>() {
            @Override
            public void onResponse(Call<ArrayList<DAOActiveLocation>> call, Response<ArrayList<DAOActiveLocation>> response) {

                if (response.body() != null && response.code() == 200 && response.body().size() > 0) {

                    activeLocationArrayList = new ArrayList<>();
                    activeLocationArrayList = response.body();

                    floorParentID = defaultLocation.getParentLocationId();
                    Integer id = defaultLocation.getId();
                    floorName = defaultLocation.getName();

                    activeLocationArrayList = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(val -> val.getParentLocationId() != null).collect(Collectors.toList());

                    ArrayList<DAOActiveLocation> selectFloors = new ArrayList<>();
                    selectFloors = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(val -> val.getParentLocationId() == floorParentID).collect(Collectors.toList());

                    for (int i = 0; i < selectFloors.size(); i++) {

                        if (id.equals(selectFloors.get(i).getId())) {
                            floorPositon = i;
                            break;
                        }
                    }

                    SessionHandler.getInstance().saveInt(LoginPinActivity.this, AppConstants.PARENT_ID_CHECK, floorParentID);
                    SessionHandler.getInstance().saveInt(LoginPinActivity.this, AppConstants.FLOOR_POSITION_CHECK, floorPositon);

                    //New...
                    SessionHandler.getInstance().saveInt(LoginPinActivity.this, AppConstants.PARENT_ID, floorParentID);
                    SessionHandler.getInstance().saveInt(LoginPinActivity.this, AppConstants.FLOOR_POSITION, floorPositon);

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


                    //To load Default location
                    SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.COUNTRY_NAME_CHECK, CountryName);
                    SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.BUILDING_CHECK, buildingName);
                    SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.FLOOR_CHECK, floorName);
                    SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.FULLPATHLOCATION_CHECK, fullPathLocation);


                    SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.COUNTRY_NAME, CountryName);
                    SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.BUILDING, buildingName);
                    SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.FLOOR, floorName);
                    SessionHandler.getInstance().save(LoginPinActivity.this, AppConstants.FULLPATHLOCATION, fullPathLocation);

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
    private void launchWelcomeActivity() {
        Intent intent = new Intent(LoginPinActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

}