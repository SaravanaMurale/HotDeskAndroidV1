package com.brickendon.hdplus.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brickendon.hdplus.BuildConfig;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.brickendon.hdplus.MainActivity;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.adapter.EditDefaultAssetAdapter;
import com.brickendon.hdplus.databinding.ActivityEditProfileBinding;
import com.brickendon.hdplus.model.language.LanguagePOJO;
import com.brickendon.hdplus.model.response.BaseResponse;
import com.brickendon.hdplus.model.response.BookingListResponse;
import com.brickendon.hdplus.model.response.DAOActiveLocation;
import com.brickendon.hdplus.model.response.DAOCountryList;
import com.brickendon.hdplus.model.response.DefaultAssetResponse;
import com.brickendon.hdplus.model.response.ProfilePicResponse;
import com.brickendon.hdplus.model.response.ProfileResponse;
import com.brickendon.hdplus.model.response.TeamDeskResponse;
import com.brickendon.hdplus.model.response.UserDetailsResponse;
import com.brickendon.hdplus.ui.settings.CountryListActivity;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements EditDefaultAssetAdapter.OnDefaultAssetSelectable {

    ActivityEditProfileBinding binding;
    UserDetailsResponse profileData; //= new UserDetailsResponse();
    private BottomSheetDialog attachChooser;
    public static final int REQUEST_IMAGE = 100;

    String encodedImage;
    ArrayList<DAOCountryList> countryList = new ArrayList<>();

    //ForLanguage
    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata;
    LanguagePOJO.Global global;

    int defaultLocationIdForCalendar=0;
    int floorParentID = 0, cityPlaceID = 0, cityPlaceParentID = 0, cityID = 0, cityParentID = 0, locationID = 0, locationParentID = 0,
            floorPositon;
    int carFloorParentID;

    String CountryName = null;
    String CityName = null;
    String buildingName = null;
    String floorName = null;
    String finFloorName="";
    String fullPathLocation = null;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String currentPhotoPath;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage";
    Bitmap thumbnail;
    ByteArrayOutputStream bytes;
    String encodeImage;

    //New-M
    String notifyStartTimeChange = "";
    String notifyEndTimeChange = "";
    private static final int PERMISSION_REQUEST_CODE = 1;
    EditDefaultAssetAdapter editDefaultAssetAdapter;
    String[] items = new String[]{"Take Photo", "Gallery","Remove photo",
            "Cancel"};



    //New...
    ArrayList<DAOActiveLocation> activeLocationArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_profile);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setLanguage();

        //getUserDetailsUsingToken( SessionHandler.getInstance().get(EditProfileActivity.this, AppConstants.USERTOKEN));

        getProfilePicture();
        loadHomeList();

        Gson gson = new Gson();
        String json = SessionHandler.getInstance().get(EditProfileActivity.this, AppConstants.LOGIN_RESPONSE);
        if (json != null) {
            profileData = gson.fromJson(json, UserDetailsResponse.class);
        }

        getCountry();
        LocalDate today = LocalDate.now();
        // Go backward to get Monday
        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        // Go forward to get Sunday
        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }

        System.out.println("Today: " + today);
        System.out.println("Monday of the Week: " + monday);
        System.out.println("Sunday of the Week: " + sunday);

        binding.weekStartEnd.setText(Utils.showBottomSheetDate("" + monday) +
                " - " +
                Utils.showBottomSheetDate("" + sunday));

        binding.tvUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkCameraPermission(EditProfileActivity.this))
                    selectImage();


//                    CropImage.activity()
//                            .setGuidelines(CropImageView.Guidelines.ON)
//                            .start(EditProfileActivity.this);

            }
        });
        binding.profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.profileUpdate.setVisibility(View.VISIBLE);
                binding.profileEdit.setVisibility(View.GONE);
                //makeEnable();

            }
        });


        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.removeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doRemoveProfilePicture();

            }
        });

        binding.profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                validateData();


            }
        });


        //StartTime API call
        binding.editStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //notifyStartTime

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!notifyStartTimeChange.isEmpty()) {
                    notifyStartTimeChange = "";
                    validateData();
                }

            }
        });

        binding.editStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyStartTimeChange = "start";
                Utils.bottomSheetTimePicker24Hrs(EditProfileActivity.this, EditProfileActivity.this, binding.editStartTime, "Start", "",false);
            }
        });


        //EndTimeAPIcall


        binding.editEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyEndTimeChange = "End";
                Utils.bottomSheetTimePicker24Hrs(EditProfileActivity.this, EditProfileActivity.this, binding.editEndTime, "End", "",false);
            }
        });

        binding.editEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!notifyEndTimeChange.isEmpty()) {
                    notifyEndTimeChange = "";
                    validateData();
                }

            }
        });


        //Vehicle Number Change
        binding.editVehicleNum.setImeOptions(EditorInfo.IME_ACTION_DONE);
        binding.editVehicleNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validateData();
                    return true;
                }
                return false;
            }
        });


        //Email Change
        binding.tvEditEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validateData();
                    return true;
                }
                return false;
            }
        });

        //Teams
        binding.tvEditTeams.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validateData();
                    return true;
                }
                return false;
            }
        });

        //Mobile Number
        binding.tvEditPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validateData();
                    return true;
                }
                return false;
            }
        });

        //Telephone Number
        binding.tvEditTel.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validateData();
                    return true;
                }
                return false;
            }
        });


        binding.editDeskChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int teamId = SessionHandler.getInstance().getInt(EditProfileActivity.this, AppConstants.TEAM_ID);
                if (teamId <= 0) {
                    Utils.toastMessage(EditProfileActivity.this, "Not assigned to a team.Please contact administrator!");
                }else{
                    if (binding.editDesk.getText().toString() != null)
                        getDeskName(binding.editDesk.getText().toString());
                }


            }
        });


        basedOnRoleEnableDisable();


        binding.editName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validateData();
                    return true;
                }

                return false;
            }
        });


        binding.ivEditSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("loadFrag", "changeSchedule");
                startActivity(intent);
            }
        });


        if (profileData != null) {

            binding.editName.setText(profileData.getFullName());
            binding.editDisplayName.setText(profileData.getFullName());
           /* if (profileData.getCurrentTeam() != null) {
                binding.tvEditTeams.setText(profileData.getCurrentTeam().getCurrentTeamName());
            }*/

            if (profileData.getPhoneNumber() != null) {
                binding.tvEditPhone.setText(profileData.getPhoneNumber());
            }
             if (profileData.getEmail() != null) {
                 binding.tvEditEmail.setText(profileData.getEmail());
            }

            binding.editStartTime.setText(Utils.splitTime(profileData.getWorkHoursFrom()));
            binding.editEndTime.setText(Utils.splitTime(profileData.getWorkHoursTo()));
            binding.editVehicleNum.setText(profileData.getVehicleRegNumber());
            binding.tvEditTel.setText(profileData.getDeskPhoneNumber());

            if (profileData.getDefaultLocation() != null) {
                binding.editDefaultLocaton.setText(profileData.getDefaultLocation().getName());
                floorParentID = profileData.getDefaultLocation().getParentLocationId();
            }

            if (profileData.getPreferredDesk() != null) {
                binding.editDesk.setText(profileData.getPreferredDesk().getCode());
            }
            if (profileData.getDefaultCarParkLocation() != null) {
                binding.editPark.setText(profileData.getDefaultCarParkLocation().getName());
                carFloorParentID = profileData.getDefaultCarParkLocation().getParentLocationId();
            }


        }

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            ArrayList<DAOActiveLocation> finalLocationArrayList = new ArrayList<>();
                            ArrayList<DAOActiveLocation> cityPlaceFloorArrayList = new ArrayList<>();

                            Intent intent = result.getData();

                            if (intent != null) {

                                if (intent.getStringExtra("sFrom") != null) {

                                    String from = intent.getStringExtra("sFrom");
                                    int position = intent.getIntExtra("Position", 0);
                                    floorName = intent.getStringExtra("floorName");

                                    finalLocationArrayList = (ArrayList<DAOActiveLocation>) intent.getSerializableExtra("List");
                                    cityPlaceFloorArrayList = (ArrayList<DAOActiveLocation>) intent.getSerializableExtra("FloorList");

                                    floorParentID = finalLocationArrayList.get(position).getParentLocationId();
                                    Integer id = finalLocationArrayList.get(position).getId();
                                    locationID = finalLocationArrayList.get(position).getId();


                                    if (from.equalsIgnoreCase(AppConstants.DefaultLocation)) {

                                        UserDetailsResponse.DefaultLocation defaultLocation = new UserDetailsResponse.DefaultLocation();

                                        ArrayList<DAOActiveLocation> selectFloors = new ArrayList<>();
                                        selectFloors = (ArrayList<DAOActiveLocation>) cityPlaceFloorArrayList.stream()
                                                .filter(val -> val.getParentLocationId() == floorParentID).collect(Collectors.toList());

                                        for (int i = 0; i < selectFloors.size(); i++) {

                                            if (id.equals(selectFloors.get(i).getId())) {
                                                floorPositon = i;
                                                finFloorName=selectFloors.get(i).getName();
                                                break;
                                            }
                                        }

                                        defaultLocation.setId(finalLocationArrayList.get(position).getId());
                                        defaultLocation.setName(finalLocationArrayList.get(position).getName());
                                        defaultLocation.setDescription(finalLocationArrayList.get(position).getDescription());
                                        defaultLocation.setLeafLocation(finalLocationArrayList.get(position).getIsLeafLocation());
                                        defaultLocation.setLocationType(finalLocationArrayList.get(position).getLocationType());
                                        defaultLocation.setActive(finalLocationArrayList.get(position).getIsActive());
                                        defaultLocation.setTimeZoneId(finalLocationArrayList.get(position).getTimeZoneId());
                                        defaultLocation.setParentLocationId(floorParentID);

                                        profileData.setDefaultLocation(defaultLocation);

                                        ArrayList<DAOActiveLocation> buildingPlace = new ArrayList<>();
                                        ArrayList<DAOActiveLocation> cityList = new ArrayList<>();
                                        ArrayList<DAOActiveLocation> location = new ArrayList<>();

                                        binding.editDefaultLocaton.setText(floorName);


                                        buildingPlace.addAll(finalLocationArrayList.stream().filter(val -> val.getId() == floorParentID).collect(Collectors.toList()));

                                        if (buildingPlace.size() > 0) {
                                            cityPlaceID = buildingPlace.get(0).getId();
                                            cityPlaceParentID = buildingPlace.get(0).getParentLocationId();
                                            buildingName = buildingPlace.get(0).getName();
                                        }

                                        cityList.addAll(finalLocationArrayList.stream().filter(val -> val.getId() == cityPlaceParentID).collect(Collectors.toList()));

                                        if (cityList.size() > 0) {
                                            cityID = cityList.get(0).getId();
                                            cityParentID = cityList.get(0).getParentLocationId();
                                            CityName = cityList.get(0).getName();
                                        }

                                        location.addAll(finalLocationArrayList.stream().filter(val -> val.getId() == cityParentID).collect(Collectors.toList()));

                                        if (location.size() > 0) {
                                            locationParentID = location.get(0).getParentLocationId();
                                            CountryName = location.get(0).getName();
                                        }

                                        validateData();

                                    } else {

                                        //Car Parking...
                                        binding.editPark.setText(floorName);

                                        UserDetailsResponse.DefaultCarParkLocation carPark = new UserDetailsResponse.DefaultCarParkLocation();
                                        carPark.setId(finalLocationArrayList.get(position).getId());
                                        carPark.setName(finalLocationArrayList.get(position).getName());
                                        carPark.setDescription(finalLocationArrayList.get(position).getDescription());
                                        carPark.setLeafLocation(finalLocationArrayList.get(position).getIsLeafLocation());
                                        carPark.setLocationType(finalLocationArrayList.get(position).getLocationType());
                                        carPark.setActive(finalLocationArrayList.get(position).getIsActive());
                                        carPark.setTimeZoneId(finalLocationArrayList.get(position).getTimeZoneId());
                                        carPark.setParentLocationId(floorParentID);

                                        //profileData.setDefaultCarParkLocation(carPark);

                                        //validateData();

                                    }
                                }

                            }
                        }

                    }
                });

        binding.editDefaultLocaton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EditProfileActivity.this, DefaultLocationActivity.class);
                intent.putExtra(AppConstants.FROM, AppConstants.DefaultLocation);
                resultLauncher.launch(intent);
                //startActivity(intent);
            }
        });

        binding.changeCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfileActivity.this, CountryListActivity.class);
                startActivity(intent);
            }
        });

        //New...
        //makeDisable();
        binding.editParkChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int teamId = SessionHandler.getInstance().getInt(EditProfileActivity.this, AppConstants.TEAM_ID);
                if (teamId <= 0) {
                    Utils.toastMessage(EditProfileActivity.this, "Not assigned to a team.Please contact administrator!");
                }else {
                    Intent intent = new Intent(EditProfileActivity.this, DefaultLocationActivity.class);
                    intent.putExtra(AppConstants.FROM, AppConstants.ParkingLocation);
                    resultLauncher.launch(intent);
                    //startActivity(intent);
                }
            }
        });


        binding.ivEditEmailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (profileData.getEmail() != null && !profileData.getEmail().isEmpty())
                    Utils.openMail(EditProfileActivity.this, profileData.getEmail());*/
            }
        });

        binding.tvEditPhoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (profileData.getPhoneNumber() != null && !profileData.getPhoneNumber().isEmpty())
                    Utils.openDial(EditProfileActivity.this, profileData.getPhoneNumber());*/
            }
        });

        binding.ivEditEmailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (profileData.getDeskPhoneNumber() != null && !profileData.getDeskPhoneNumber().isEmpty())
                    Utils.openMail(EditProfileActivity.this, profileData.getDeskPhoneNumber());*/
            }
        });

    }

    //By Passing Token Get User Details
    private void getUserDetailsUsingToken(String token) {

        if (Utils.isNetworkAvailable(this)) {
            //dialog = ProgressDialog.showProgressBar(EditProfileActivity.this);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UserDetailsResponse> call = apiService.getLoginUserDetails();
            call.enqueue(new Callback<UserDetailsResponse>() {
                @Override
                public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {

                    if (response.code() == 200) {
                        UserDetailsResponse userDetailsResponse = response.body();

                        if (userDetailsResponse != null) {
                            //Save User credential for Biometric access


                            //SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.USERNAME, Utils.checkStringParms(userDetailsResponse.getFullName()));
                            //SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.COMPANY_NAME, Utils.checkStringParms(companyName));
                            /*if (userDetailsResponse.getCurrentTeam() != null) {
                                SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.CURRENT_TEAM, Utils.checkStringParms(userDetailsResponse.getCurrentTeam().getCurrentTeamName()));
                                SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.TEAM_ID, Utils.checkStringParms(userDetailsResponse.getCurrentTeam().getCurrentTeamId()));
                            }*/
                            //SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.PHONE_NUMBER, Utils.checkStringParms(userDetailsResponse.getPhoneNumber()));
                            //SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.EMAIL, Utils.checkStringParms(email));
                            //SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.PASSWORD, Utils.checkStringParms(email));
                            //SessionHandler.getInstance().saveBoolean(EditProfileActivity.this, AppConstants.USER_DETAILS_SAVED_STATUS, true);
                            //SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.TEAMMEMBERSHIP_ID, Utils.checkStringParms(userDetailsResponse.getTeamMembershipId()));

                            //SessionHandler.getInstance().saveBoolean(EditProfileActivity.this, AppConstants.PIN_SETUP_DONE, userDetailsResponse.isHasPinSetup());

                            /*if(userDetailsResponse.isHasPinSetup()){
                                SessionHandler.getInstance().saveBoolean(EditProfileActivity.this,AppConstants.PIN_ACTIVE_STATUS_AFTER_LOGOUT,true);
                            }*/

                            //Log.d(TAG, "onResponse: "+userDetailsResponse.getDefaultLocation().getParentLocationId());
                            if (userDetailsResponse.getDefaultCarParkLocation() != null) {
                                SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.DEFAULT_CAR_PARK_LOCATION_ID,
                                        Utils.checkStringParms(userDetailsResponse.getDefaultCarParkLocation().getId()));
                            }

                            /*if (userDetailsResponse.getCurrentTeam() != null) {
                                SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.CURRENT_TEAM, userDetailsResponse.getCurrentTeam().getCurrentTeamName());
                                SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.TEAM_ID, userDetailsResponse.getCurrentTeam().getCurrentTeamId());
                            }*/

                            //Vehicle Number
                            if(userDetailsResponse.getVehicleRegNumber()!=null){
                                SessionHandler.getInstance().save(EditProfileActivity.this,AppConstants.VEHICLE_NUMBER,userDetailsResponse.getVehicleRegNumber());
                            }

                            if (userDetailsResponse.getDefaultLocation() != null) {
                                SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.DEFAULT_TIME_ZONE_ID, Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getTimeZoneId()));
                                SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.DEFAULT_LOCATION_NAME, Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getName()));
                                SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.DEFAULT_LOCATION_ID, String.valueOf(Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getParentLocationId())));
                                SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.DEFAULT_LOCATION_UNIQUE_ID, String.valueOf(Utils.checkStringParms(userDetailsResponse.getDefaultLocation().getId())));
                            }

                            //Save UserId
                            //SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.USER_ID, Utils.checkStringParms(userDetailsResponse.getId()));
                            //SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.ROLE, Utils.checkStringParms(userDetailsResponse.getHighestRole()));

                            //ProgressDialog.dismisProgressBar(EditProfileActivity.this, dialog);

                            //New...
                            Gson gson = new Gson();
                            String json = gson.toJson(userDetailsResponse);
                            SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.LOGIN_RESPONSE, json);

                            //Check welcome screen viewed status
                            //boolean welcomeViewStatus=SessionHandler.getInstance().getBoolean(EditProfileActivity.this,AppConstants.WELCOME_VIEWED_STATUS);

                            //New...
                            if (userDetailsResponse.getDefaultLocation() != null) {
                                setLocate(userDetailsResponse.getDefaultLocation());
                            } else {
                                //launchWelcomeActivity();
                            }


                            String jsons = SessionHandler.getInstance().get(EditProfileActivity.this, AppConstants.LOGIN_RESPONSE);
                            if (jsons != null) {
                                profileData = gson.fromJson(json, UserDetailsResponse.class);
                            }

                        } else {
                            //ProgressDialog.dismisProgressBar(EditProfileActivity.this, dialog);
                            Utils.toastMessage(EditProfileActivity.this, "User Details Not Found");
                        }

                    } else if (response.code() == 401) {
                        //ProgressDialog.dismisProgressBar(EditProfileActivity.this, dialog);
                        Utils.toastMessage(EditProfileActivity.this, "Please Try Again");
                    }

                }

                @Override
                public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                    //ProgressDialog.dismisProgressBar(EditProfileActivity.this, dialog);
                }
            });
        } else {
            Utils.toastMessage(this, "Please Enable Internet");
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

                    floorParentID = defaultLocation.getParentLocationId();
                    defaultLocationIdForCalendar = defaultLocation.getId();
                    Integer id = defaultLocation.getId();
                    floorName = defaultLocation.getName();

                    activeLocationArrayList = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(val -> val.getParentLocationId() != null).collect(Collectors.toList());

                    ArrayList<DAOActiveLocation> selectFloors = new ArrayList<>();
                    selectFloors = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(val -> val.getParentLocationId() == floorParentID).collect(Collectors.toList());
                    String finFloorName="";

                    for (int i = 0; i < selectFloors.size(); i++) {

                        if (id.equals(selectFloors.get(i).getId())) {
                            floorPositon = i;
                            finFloorName=selectFloors.get(i).getName();
                            break;
                        }
                    }

                    //please add this for calendar default locations #Bala
                    SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.LOCATION_ID, defaultLocationIdForCalendar);
                    SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.PARENT_ID_CHECK, floorParentID);
                    SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.FLOOR_POSITION_CHECK, floorPositon);

                    //New...
                    SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.PARENT_ID, floorParentID);
                    SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.FLOOR_POSITION, floorPositon);

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
                    SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.COUNTRY_NAME_CHECK, CountryName);
                    SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.BUILDING_CHECK, buildingName);

                    SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.FLOOR_CHECK, buildingName);
                    SessionHandler.getInstance().save(EditProfileActivity.this,AppConstants.FINAL_FLOOR_CHECK,finFloorName);

                    SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.FULLPATHLOCATION_CHECK, fullPathLocation);



                    SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.COUNTRY_NAME, CountryName);
                    SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.BUILDING, buildingName);

                    SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.FLOOR, buildingName);
                    SessionHandler.getInstance().save(EditProfileActivity.this,AppConstants.FINAL_FLOOR,finFloorName);

                    SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.FULLPATHLOCATION, fullPathLocation);

                    //launchWelcomeActivity();

                } else {
                    //launchWelcomeActivity();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<DAOActiveLocation>> call, Throwable t) {
                //launchWelcomeActivity();
            }
        });

    }

    private void validateData() {

        String name = binding.editName.getText().toString();
        String editDisplayName = binding.editDisplayName.getText().toString();
        String phone = binding.tvEditPhone.getText().toString();
        String deskPhone = binding.tvEditTel.getText().toString();
        String email = binding.tvEditEmail.getText().toString();
        String teams ="";
        if (profileData.getCurrentTeam() != null) {
            teams = profileData.getCurrentTeam().getCurrentTeamName();
        }

        String startTime = binding.editStartTime.getText().toString();
        String endTime = binding.editEndTime.getText().toString();
        String vehicleNum = binding.editVehicleNum.getText().toString();

        //if (isValidate(phone,email) && floorParentID!=0){
        profileData.setFullName(name);
        //profileData.setFullName(editDisplayName);
        profileData.setPhoneNumber(phone);
        profileData.setDeskPhoneNumber(deskPhone);
        profileData.setEmail(email);
        if(profileData.getCurrentTeam()!= null)
        profileData.getCurrentTeam().setCurrentTeamName(teams);
        profileData.setWorkHoursFrom("2000-01-01T" + startTime + ":00.000Z");
        profileData.setWorkHoursTo("2000-01-01T" + endTime + ":00.000Z");
        profileData.setVehicleRegNumber(vehicleNum);

        callProfileUpdate();
        // }


    }

    private void loadHomeList() {
        if (Utils.isNetworkAvailable(this)) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BookingListResponse> call = apiService.getUserMyWorkDetails(Utils.getCurrentDate(), true);
            call.enqueue(new Callback<BookingListResponse>() {
                @Override
                public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                    if (response.code() == 200) {
                        BookingListResponse bookingListResponse = response.body();
                        //calculateSchedule(bookingListResponse);
                    } else if (response.code() == 401) {
                        //Handle if token got expired
                        SessionHandler.getInstance().saveBoolean(EditProfileActivity.this, AppConstants.LOGIN_CHECK, false);
                        Utils.showCustomTokenExpiredDialog(EditProfileActivity.this, "Token Expired");
                    }
                }

                @Override
                public void onFailure(Call<BookingListResponse> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(EditProfileActivity.this, "Please Enable Internet");
        }
    }

    /*private void calculateSchedule(BookingListResponse bookingListResponse) {
        for (int i = 0; i < bookingListResponse.getDayGroups().size(); i++) {
            switch (Utils.getDayFromDate(bookingListResponse.getDayGroups().get(i).getDate())) {
                case "Mon":
                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries() != null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size() > 0
                    ) {
                        for (int x = 0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++) {
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")) {
                                Glide.with(this)
                                        .load(R.drawable.remote_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivMonday);

                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")) {
                                Glide.with(this)
                                        .load(R.drawable.out_of_office_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivMonday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")) {
                                Glide.with(this)
                                        .load(R.drawable.sick_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivMonday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("IO")) {
                                Glide.with(this)
                                        .load(R.drawable.building_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivMonday);
                            } else {
                                Glide.with(this)
                                        .load(R.drawable.info_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivMonday);
                            }
                        }
                    }

                    break;
                case "Tue":

                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries() != null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size() > 0
                    ) {
                        for (int x = 0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++) {
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")) {
                                Glide.with(this)
                                        .load(R.drawable.remote_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivTuesday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")) {
                                Glide.with(this)
                                        .load(R.drawable.out_of_office_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivTuesday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")) {
                                Glide.with(this)
                                        .load(R.drawable.sick_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivTuesday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("IO")) {
                                Glide.with(this)
                                        .load(R.drawable.building_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivTuesday);
                            } else {
                                Glide.with(this)
                                        .load(R.drawable.info_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivTuesday);
                            }
                        }
                    }

                    break;
                case "Wed":

                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries() != null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size() > 0
                    ) {
                        for (int x = 0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++) {
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")) {
                                Glide.with(this)
                                        .load(R.drawable.remote_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivWednesday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")) {
                                Glide.with(this)
                                        .load(R.drawable.out_of_office_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivWednesday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")) {
                                Glide.with(this)
                                        .load(R.drawable.sick_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivWednesday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("IO")) {
                                Glide.with(this)
                                        .load(R.drawable.building_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivWednesday);
                            } else {
                                Glide.with(this)
                                        .load(R.drawable.info_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivTuesday);
                            }
                        }
                    }

                    break;
                case "Thu":

                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries() != null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size() > 0
                    ) {
                        for (int x = 0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++) {
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")) {
                                Glide.with(this)
                                        .load(R.drawable.remote_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivThursday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")) {
                                Glide.with(this)
                                        .load(R.drawable.out_of_office_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivThursday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")) {
                                Glide.with(this)
                                        .load(R.drawable.sick_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivThursday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("IO")) {
                                Glide.with(this)
                                        .load(R.drawable.building_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivThursday);
                            } else {
                                Glide.with(this)
                                        .load(R.drawable.info_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivTuesday);
                            }
                        }
                    }
                    break;
                case "Fri":

                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries() != null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size() > 0
                    ) {
                        for (int x = 0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++) {
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")) {
                                Glide.with(this)
                                        .load(R.drawable.remote_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivFriday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")) {
                                Glide.with(this)
                                        .load(R.drawable.out_of_office_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivFriday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")) {
                                Glide.with(this)
                                        .load(R.drawable.sick_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivFriday);
                            } else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("IO")) {
                                Glide.with(this)
                                        .load(R.drawable.building_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivFriday);
                            } else {
                                Glide.with(this)
                                        .load(R.drawable.info_circle)
                                        .placeholder(R.drawable.info_circle)
                                        .into(binding.ivTuesday);
                            }
                        }
                    }
                    break;
                case "Sat":
                    Glide.with(this)
                            .load(R.drawable.out_of_office_circle)
                            .placeholder(R.drawable.out_of_office_circle)
                            .into(binding.ivSaturday);
                    binding.tvSaturday.setTextColor(getResources().getColor(R.color.figmaGrey));
                    binding.ivSaturday.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.figmaGrey)));
                    break;
                case "Sun":
                    Glide.with(this)
                            .load(R.drawable.out_of_office_circle)
                            .placeholder(R.drawable.out_of_office_circle)
                            .into(binding.ivSunday);

                    binding.ivSunday.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.figmaGrey)));
                    binding.tvSunday.setTextColor(getResources().getColor(R.color.figmaGrey));
                    break;
                default:
            }
        }
    }
*/
    private boolean isValidate(String phone, String email) {

        if (phone.isEmpty() || phone.length() <= 6) {
            Toast.makeText(EditProfileActivity.this, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (email.isEmpty() || !(Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches())) {
            Toast.makeText(EditProfileActivity.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    private void getDeskName(String selectedItem) {

        if (Utils.isNetworkAvailable(EditProfileActivity.this)) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<TeamDeskResponse>> call = apiService.getDeskListInEdit();
            call.enqueue(new Callback<List<TeamDeskResponse>>() {
                @Override
                public void onResponse(Call<List<TeamDeskResponse>> call, Response<List<TeamDeskResponse>> response) {

                    List<TeamDeskResponse> teamDeskResponseList = response.body();

                /*for (int i = 0; i <teamDeskResponseList.size() ; i++) {

                    System.out.println("DeskDetails "+teamDeskResponseList.get(i).getDesk()+" "+teamDeskResponseList.get(i).getDeskId()+" "+teamDeskResponseList.get(i).getTeamId()+" "+teamDeskResponseList.get(i).getId());

                }*/

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                    callDeskBottomSheetDialogToSelectDeskCode(teamDeskResponseList, selectedItem);
                }

                @Override
                public void onFailure(Call<List<TeamDeskResponse>> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(EditProfileActivity.this, getResources().getString(R.string.enable_internet));
        }


    }

    private void doRemoveProfilePicture() {

        if (Utils.isNetworkAvailable(EditProfileActivity.this)) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            ProfilePicResponse profilePicResponse = new ProfilePicResponse();
            profilePicResponse.setImage("");
            Call<BaseResponse> call = apiService.removeProfilePicture(profilePicResponse);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    Utils.toastMessage(EditProfileActivity.this, "Image Removed Successfully");

                    Glide.with(EditProfileActivity.this).load(R.drawable.avatar)
                            .into(binding.ivEditPrifle);

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    getProfilePicture();
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(EditProfileActivity.this, getResources().getString(R.string.enable_internet));
        }

    }


    private void getProfilePicture() {

        if (Utils.isNetworkAvailable(EditProfileActivity.this)) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ProfilePicResponse> call = apiService.getProfilePicture();
            call.enqueue(new Callback<ProfilePicResponse>() {
                @Override
                public void onResponse(Call<ProfilePicResponse> call, Response<ProfilePicResponse> response) {

                    if (response.body() != null) {
                        ProfilePicResponse profilePicResponse = response.body();

                        System.out.println("Base64Image" + profilePicResponse.getImage());

                        if (profilePicResponse.getImage() != null &&
                                !profilePicResponse.getImage().equalsIgnoreCase("") &&
                                !profilePicResponse.getImage().isEmpty()) {
                            String base64String = profilePicResponse.getImage();
                            String base64Image = base64String.split(",")[1];
                            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            binding.ivEditPrifle.setImageBitmap(decodedByte);

                            items = new String[]{"Take Photo", "Gallery","Remove photo",
                                    "Cancel"};
                        }else{

                            Glide.with(EditProfileActivity.this).load(R.drawable.avatar)
                                    .into(binding.ivEditPrifle);
                            List<String> list = new ArrayList<String>(Arrays.asList(items));
                            list.remove("Remove photo");
                            items = list.toArray(new String[0]);
                        }
                    }


                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<ProfilePicResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(EditProfileActivity.this, getResources().getString(R.string.enable_internet));
        }
    }

    /*private void selectImage() {
        attachChooser = new BottomSheetDialog(EditProfileActivity.this);
        attachChooser.setContentView((EditProfileActivity.this).getLayoutInflater().inflate(R.layout.popup_add_attach_options,
                new LinearLayout(EditProfileActivity.this)));
        attachChooser.show();
        LinearLayout btnStartCamera = (LinearLayout) attachChooser.findViewById(R.id.btn_from_camera);
        LinearLayout btnStartFileBrowser = (LinearLayout) attachChooser.findViewById(R.id.btn_from_local);
        btnStartCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachChooser.dismiss();
                if (Utils.checkCameraPermission(EditProfileActivity.this))
                    //cameraIntent();
                    CropImage.activity().start(EditProfileActivity.this);
            }
        });
        btnStartFileBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachChooser.dismiss();
                if (Utils.checkPermission(EditProfileActivity.this))
                    //galleryIntent();
                    CropImage.activity().start(EditProfileActivity.this);
            }
        });
    }
*/

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        return encodedImage;
    }

    private void updateProfilePicture(String bast64InString) {

        if (Utils.isNetworkAvailable(EditProfileActivity.this)) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            ProfilePicResponse profilePicResponse = new ProfilePicResponse();
            profilePicResponse.setImage(bast64InString);

            Call<BaseResponse> call = apiService.updateProfilePicture(profilePicResponse);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(EditProfileActivity.this,"Profile updated", Toast.LENGTH_SHORT).show();
                    getProfilePicture();

                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                }
            });

        } else {
            Utils.toastMessage(EditProfileActivity.this, getResources().getString(R.string.enable_internet));
        }


    }


    private void callProfileUpdate() {
        if (Utils.isNetworkAvailable(EditProfileActivity.this)) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ProfileResponse> call = apiService.updateSetting(profileData);
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                    //ProgressDialog.dismisProgressBar(getContext(),dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    binding.profileUpdate.setVisibility(View.GONE);
                    binding.profileEdit.setVisibility(View.GONE);

                    Toast.makeText(EditProfileActivity.this,"Profile updated", Toast.LENGTH_SHORT).show();
                    updateProfileValue();
                    closeKeyboard();
                    //makeDisable();

                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    Toast.makeText(EditProfileActivity.this,"Profile updated", Toast.LENGTH_SHORT).show();
                    closeKeyboard();
                    updateProfileValue();
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    binding.profileUpdate.setVisibility(View.GONE);
                    binding.profileEdit.setVisibility(View.GONE);
                    //makeDisable();

                }
            });

        } else {
            Utils.toastMessage(EditProfileActivity.this, getResources().getString(R.string.enable_internet));
        }

    }

    private void closeKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {

        }


    }

    public void updateProfileValue() {
        Gson gson = new Gson();
        String json = gson.toJson(profileData);
        SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.LOGIN_RESPONSE, json);
//        Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();

        //please add this for calendar default locations #Bala
        if(locationID > 0)
            SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.LOCATION_ID, locationID);
        if (floorParentID > 0)
            SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.PARENT_ID_CHECK, floorParentID);
        if (floorPositon > 0)
            SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.FLOOR_POSITION_CHECK, floorPositon);

        //New...
        if (floorParentID > 0)
            SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.PARENT_ID, floorParentID);
        if (floorPositon > 0)
            SessionHandler.getInstance().saveInt(EditProfileActivity.this, AppConstants.FLOOR_POSITION, floorPositon);

        Log.d("floorParentID", String.valueOf(floorParentID));
        Log.d("floorPositon", String.valueOf(floorPositon));


        //To load Default location
        if (CountryName!=null &&!CountryName.equalsIgnoreCase("") && !CountryName.isEmpty())
            SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.COUNTRY_NAME_CHECK, CountryName);
        if (buildingName!=null &&!buildingName.equalsIgnoreCase("") && !buildingName.isEmpty())
            SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.BUILDING_CHECK, buildingName);
        if (buildingName!=null &&!buildingName.equalsIgnoreCase("") && !buildingName.isEmpty())
            SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.FLOOR_CHECK, buildingName);
        if (finFloorName!=null &&!finFloorName.equalsIgnoreCase("") && !finFloorName.isEmpty())
            SessionHandler.getInstance().save(EditProfileActivity.this,AppConstants.FINAL_FLOOR_CHECK,finFloorName);
        if (fullPathLocation!=null &&!fullPathLocation.equalsIgnoreCase("") && !fullPathLocation.isEmpty())
            SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.FULLPATHLOCATION_CHECK, fullPathLocation);

        if (CountryName!=null &&!CountryName.equalsIgnoreCase("") && !CountryName.isEmpty())
            SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.COUNTRY_NAME, CountryName);
        if (buildingName!=null &&!buildingName.equalsIgnoreCase("") && !buildingName.isEmpty())
            SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.BUILDING, buildingName);
        if (buildingName!=null &&!buildingName.equalsIgnoreCase("") && !buildingName.isEmpty())
            SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.FLOOR, buildingName);
        if (finFloorName!=null &&!finFloorName.equalsIgnoreCase("") && !finFloorName.isEmpty())
            SessionHandler.getInstance().save(EditProfileActivity.this,AppConstants.FINAL_FLOOR,finFloorName);
        if (fullPathLocation!=null &&!fullPathLocation.equalsIgnoreCase("") && !fullPathLocation.isEmpty())
            SessionHandler.getInstance().save(EditProfileActivity.this, AppConstants.FULLPATHLOCATION, fullPathLocation);

    }

    private void makeEnable() {
        binding.editName.setEnabled(false);
        binding.editDisplayName.setEnabled(true);
        binding.editStartTime.setEnabled(true);
        binding.editEndTime.setEnabled(true);
        binding.editDefaultLocaton.setEnabled(true);

        //binding.editDeskChange.setEnabled(true);
        binding.editParkChange.setEnabled(true);
        binding.editRoomChange.setEnabled(true);
        binding.tvEditEmail.setEnabled(false);
        binding.tvEditPhone.setEnabled(true);
        binding.tvEditTeams.setEnabled(false);
        binding.editVehicleNum.setEnabled(true);
        binding.tvEditTel.setEnabled(true);
        binding.changeCountry.setEnabled(true);

        //basedOnRoleEnableDisable();


        binding.editRoomChange.setTextColor(getResources().getColor(R.color.figmaBlueText, getTheme()));
        //binding.editDeskChange.setTextColor(getResources().getColor(R.color.figmaBlue,getTheme()));
        binding.editParkChange.setTextColor(getResources().getColor(R.color.figmaBlueText, getTheme()));
        binding.editStartTime.setTextColor(getResources().getColor(R.color.figmaBlueText, getTheme()));
        binding.editEndTime.setTextColor(getResources().getColor(R.color.figmaBlueText, getTheme()));
        binding.changeCountry.setTextColor(getResources().getColor(R.color.figmaBlueText, getTheme()));

    }

    private void basedOnRoleEnableDisable() {


        if (profileData != null && profileData.getHighestRole() != null) {

            if (profileData.getHighestRole().equalsIgnoreCase(AppConstants.Administrator)
                    || profileData.getHighestRole().equalsIgnoreCase(AppConstants.FacilityManager)
                    || profileData.getHighestRole().equalsIgnoreCase(AppConstants.TeamManager)
                    || profileData.getHighestRole().equalsIgnoreCase(AppConstants.Manager)
                    || profileData.getHighestRole().equalsIgnoreCase(AppConstants.MeetingManager)) {

                binding.editDisplayName.setEnabled(false);
                binding.editName.setEnabled(false);


            } else {
                binding.editDisplayName.setEnabled(false);
                binding.editName.setEnabled(false);
            }

        } else {
            binding.editDisplayName.setEnabled(false);
            binding.editName.setEnabled(false);
        }

    }

    private void makeDisable() {
        binding.editName.setEnabled(false);
        binding.editDisplayName.setEnabled(false);
        binding.editStartTime.setEnabled(false);
        binding.editEndTime.setEnabled(false);
        binding.editDefaultLocaton.setEnabled(false);

        //binding.editDeskChange.setEnabled(false);
        binding.editParkChange.setEnabled(false);
        binding.editRoomChange.setEnabled(false);
        binding.tvEditEmail.setEnabled(false);
        binding.tvEditPhone.setEnabled(false);
        binding.tvEditTeams.setEnabled(false);
        binding.editVehicleNum.setEnabled(false);
        binding.tvEditTel.setEnabled(false);
        binding.changeCountry.setEnabled(false);

        binding.editRoomChange.setTextColor(getResources().getColor(R.color.grey, getTheme()));
        //binding.editDeskChange.setTextColor(getResources().getColor(R.color.grey,getTheme()));
        binding.editParkChange.setTextColor(getResources().getColor(R.color.grey, getTheme()));
        binding.editStartTime.setTextColor(getResources().getColor(R.color.grey, getTheme()));
        binding.editEndTime.setTextColor(getResources().getColor(R.color.grey, getTheme()));
        binding.changeCountry.setTextColor(getResources().getColor(R.color.grey, getTheme()));

    }

    private void callDeskBottomSheetDialogToSelectDeskCode(List<TeamDeskResponse> teamDeskResponseList, String selectedItem) {

        RecyclerView rvDeskRecycler;

        TextView bsRepeatBack;
        EditText bsGeneralSearch;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk,
                new RelativeLayout(this))));

        rvDeskRecycler = bottomSheetDialog.findViewById(R.id.desk_list_select_recycler);
        bsRepeatBack = bottomSheetDialog.findViewById(R.id.bsDeskBack);
        bsGeneralSearch = bottomSheetDialog.findViewById(R.id.bsGeneralSearch);
        bottomSheetDialog.findViewById(R.id.filter_layout).setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);

        bsRepeatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bsGeneralSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editDefaultAssetAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        List<DefaultAssetResponse> defaultAssetResponseList = new ArrayList<>();
        if(teamDeskResponseList != null) {
            for (int i = 0; i < teamDeskResponseList.size(); i++) {
                DefaultAssetResponse defaultAssetResponse = new DefaultAssetResponse(teamDeskResponseList.get(i).getId(),
                        teamDeskResponseList.get(i).getDeskId(), teamDeskResponseList.get(i).getTeamId(),
                        teamDeskResponseList.get(i).getDesk().getCode());
                defaultAssetResponseList.add(defaultAssetResponse);

            }

            editDefaultAssetAdapter = new EditDefaultAssetAdapter(EditProfileActivity.this, defaultAssetResponseList,
                    bottomSheetDialog, this, selectedItem);
            rvDeskRecycler.setAdapter(editDefaultAssetAdapter);
        }
        bottomSheetDialog.show();

    }


    @Override
    public void onDefaultAssetSelect(int deskId, String code) {
        binding.editDesk.setText(code);

        System.out.println("SlectedCodeAndId " + code + " " + deskId);

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        UserDetailsResponse.PreferredDesk preferredDesk = userDetailsResponse.new PreferredDesk();
        preferredDesk.setId(deskId);
        preferredDesk.setCode(code);
        preferredDesk.setDescription("");

        profileData.setPreferredDesk(preferredDesk);

        //profileData.getPreferredDesk().setId(deskId);
        //profileData.getPreferredDesk().setCode(code);
        validateData();
        //profileData.getPreferredDesk().setDescription("");

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (SessionHandler.getInstance().get(EditProfileActivity.this, AppConstants.SELECTED_COUNTRY) != null
                && !SessionHandler.getInstance().get(EditProfileActivity.this, AppConstants.SELECTED_COUNTRY).isEmpty()) {

            binding.editCountry.setText(SessionHandler.getInstance().get(EditProfileActivity.this, AppConstants.SELECTED_COUNTRY));

            int id = SessionHandler.getInstance().getInt(EditProfileActivity.this, AppConstants.SELECTED_COUNTRY_ID);
            profileData.setCountryId(id);

            SessionHandler.getInstance().remove(EditProfileActivity.this, AppConstants.SELECTED_COUNTRY);
            SessionHandler.getInstance().remove(EditProfileActivity.this, AppConstants.SELECTED_COUNTRY_ID);

            validateData();
        }

    }

    private void getCountry() {
        if (Utils.isNetworkAvailable(EditProfileActivity.this)) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ArrayList<DAOCountryList>> call = apiService.getCountryList();
            call.enqueue(new Callback<ArrayList<DAOCountryList>>() {
                @Override
                public void onResponse(Call<ArrayList<DAOCountryList>> call, Response<ArrayList<DAOCountryList>> response) {

                    //binding.locateProgressBar.setVisibility(View.INVISIBLE);

                    if (response.body() != null) {

                        if (response.code() == 200) {

                            countryList = new ArrayList<>();
                            countryList = response.body();

                            if (profileData != null && countryList.size() > 0) {

                                for (int i = 0; i < countryList.size(); i++) {
                                    if (profileData.getCountryId() == countryList.get(i).getId()) {
                                        binding.editCountry.setText(countryList.get(i).getName());
                                        break;
                                    }
                                }

                            }

                        } else if (response.code() == 401) {
                            Utils.showCustomTokenExpiredDialog(EditProfileActivity.this, "Token Expired");
                            SessionHandler.getInstance().saveBoolean(EditProfileActivity.this, AppConstants.LOGIN_CHECK, false);

                        } else {
                            //Toast.makeText(EditProfileActivity.this, "No Response", Toast.LENGTH_SHORT).show();
                        }

                    }

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);


                }

                @Override
                public void onFailure(Call<ArrayList<DAOCountryList>> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
            });

        } else {
            Utils.toastMessage(EditProfileActivity.this, getResources().getString(R.string.enable_internet));
        }

    }

    public void setLanguage() {

        logoinPage = Utils.getLoginScreenData(EditProfileActivity.this);
        appKeysPage = Utils.getAppKeysPageScreenData(EditProfileActivity.this);
        resetPage = Utils.getResetPasswordPageScreencreenData(EditProfileActivity.this);
        actionOverLays = Utils.getActionOverLaysPageScreenData(EditProfileActivity.this);
        bookindata = Utils.getBookingPageScreenData(EditProfileActivity.this);
        global = Utils.getGlobalScreenData(EditProfileActivity.this);

        binding.tvTitle.setText(appKeysPage.getProfile());
        binding.tvUpdateImage.setText(appKeysPage.getUploadPhoto());
        binding.editProfileNameTv.setText(appKeysPage.getName());
        binding.EditProfileTvDisplayName.setText(appKeysPage.getDisplayName());
        binding.editProfileScheduleTv.setText(appKeysPage.getSchedule());
        binding.editProfileDefaultWorkingHoursTv.setText(appKeysPage.getDefaultWorkingHours());
        binding.tvEditStartTime.setText(appKeysPage.getStart());
        binding.tvEditEndTime.setText(appKeysPage.getEnd());
        binding.editProfileDefaultLocationTv.setText(appKeysPage.getDefaultLocation());
        binding.editProfileDefaultAssetTv.setText(appKeysPage.getDefaultAssets());
        binding.editProfileWorkSpaceTv.setText(appKeysPage.getWorkSpace());
        binding.editProfileParkingTv.setText(appKeysPage.getParking());
        binding.editProfileRoomTv.setText(appKeysPage.getRoom());
        binding.editProfileCountryTv.setText(appKeysPage.getCountry());
        binding.editProfileVehicleNumberTv.setText(appKeysPage.getVehicleNumber());
        //binding.editDeskChange.setText(appKeysPage.getChange());
        binding.editParkChange.setText(appKeysPage.getChange());
        binding.editRoomChange.setText(appKeysPage.getChange());
        binding.tvEditContact.setText(appKeysPage.getContact());
        binding.editProfileHybridHereTv.setText(appKeysPage.getHybridHero());


    }


    //New...

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        *//*if (requestCode == RC_LOAD_IMG_BROWSER) {
            onSelectFromGalleryResult(data);
        } else if (requestCode == RC_LOAD_IMG_CAMERA) {
            onCaptureImageResult(data);
        }*//*
//        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                Glide.with(EditProfileActivity.this).load(resultUri).into(binding.ivEditPrifle);

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    getStringImage(bitmap);
                    String bast64InString= String.valueOf("data:image/png;base64," + encodedImage);
                    //base64Img = RequestBody.create(MediaType.parse("text/plain"), "data:image/png;base64," + encodedImage);
                    //New...
                    BitmapFactory.Options options = Utils.getImageSize(resultUri);
                    if (options.outWidth>=250 && options.outHeight>=250){
                        updateProfilePicture(bast64InString);
                    }else {
                        Utils.toastMessage(EditProfileActivity.this,"Please Select Image more than 250X250 ");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("errot image"+result.getError());
                Toast.makeText(this, "elsed da"+ result.getError(), Toast.LENGTH_SHORT).show();
            }
        }
    }
*/
    private void selectImage() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Select Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utils.checkPermission(EditProfileActivity.this);

                if (items[item].equals("Take Photo")) {
                    if (checkPermission()) {
//                    userChoosenTask = commonStrings.getTxt_take_photo().getName();
                        if (result)
                            cameraIntent();
                    } else {
                        requestPermission();
                    }
                } else if (items[item].equals("Gallery")) {
//                    userChoosenTask = commonStrings.getTxt_choose_from_gallery().getName();
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }else if (items[item].equals("Remove photo")) {
                    dialog.dismiss();
                    doRemoveProfilePicture();
                }
            }
        });
        builder.show();
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setActivityTitle("Thumbnail Image")
//                .setCropShape(CropImageView.CropShape.RECTANGLE)
//                .setCropMenuCropButtonTitle("Done")
//                .setAllowFlipping(false)
//                .setMinCropResultSize(400, 400)
//                .setMaxCropResultSize(400, 400)
//                .setMinCropWindowSize(400, 400)
//                .start(this);
    }

    private void cameraIntent() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = getImageFile(); // 1
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) // 2
                uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID.concat(".provider"), file);
            else
                uri = Uri.fromFile(file); // 3
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); // 4
//            startActivityForResult(pictureIntent, CAMERA_ACTION_PICK_REQUEST_CODE);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void galleryIntent() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        thumbnail = null;
        try {
            thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        Uri selectedImage = data.getData();

        encodeImage = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);


        /*
        requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes.toByteArray());
        body = MultipartBody.Part.createFormData("profile_img", "image.jpg", requestFile);
        ivImage.setImageBitmap(thumbnail);
        ivImage.setImageURI(Uri.parse(data.toURI()));*/
    }

    private File getImageFile() {
        String imageFileName = "JPEG_" + System.currentTimeMillis() + "_";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "Camera"
        );
        File file = null;
        if(!storageDir.exists())
            storageDir.mkdir();

        try {
            file = File.createTempFile(
                    imageFileName, ".jpg", storageDir
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPhotoPath = "file:" + file.getAbsolutePath();
        return file;
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        bytes = new ByteArrayOutputStream();
        if (thumbnail != null) {
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            encodeImage = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
        }
//        requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes.toByteArray());
//        body = MultipartBody.Part.createFormData("profile_img", "image.jpg", requestFile);
//        ivImage.setImageBitmap(thumbnail);
    }

    private void openCropActivity(Uri sourceUri, Uri destinationUri) {
        UCrop.of(sourceUri, destinationUri)
                .withMaxResultSize(400, 400)
                .withAspectRatio(5f, 5f)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += ".jpg";
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                Uri sourceUri = data.getData(); // 1
                File file = getImageFile(); // 2
                Uri destinationUri = Uri.fromFile(file);  // 3
                openCropActivity(sourceUri, destinationUri);  // 4

                onSelectFromGalleryResult(data);
            } else if (requestCode == REQUEST_CAMERA)
                uri = Uri.parse(currentPhotoPath);
            openCropActivity(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
//                onCaptureImageResult(data);
        }
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            onCaptureImageResult(data);
            uri = UCrop.getOutput(data);
            System.out.println("print uri" + uri);
            System.out.println("print uri" + new File(uri.getPath()).getAbsolutePath());

            thumbnail = null;
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            Uri selectedImage = data.getData();
            encodeImage = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);

            Glide.with(EditProfileActivity.this).load(uri).into(binding.ivEditPrifle);

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                getStringImage(bitmap);
                String bast64InString = String.valueOf("data:image/png;base64," + encodedImage);
                //base64Img = RequestBody.create(MediaType.parse("text/plain"), "data:image/png;base64," + encodedImage);
                //New...
                BitmapFactory.Options options = Utils.getImageSize(uri);
                if (options.outWidth >= 250 && options.outHeight >= 250) {
                    updateProfilePicture(bast64InString);
                } else {
                    Utils.toastMessage(EditProfileActivity.this, "Please Select Image more than 250X250 ");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            /*
            requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes.toByteArray());
            body = MultipartBody.Part.createFormData("profile_img", "image.jpg", requestFile);
            ivImage.setImageBitmap(thumbnail);*/
//            showImage(uri);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                thumbnail = null;
                try {
                    thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                Uri selectedImage = data.getData();
                encodeImage = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
                Glide.with(EditProfileActivity.this).load(selectedImage).into(binding.ivEditPrifle);

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    getStringImage(bitmap);
                    String bast64InString = String.valueOf("data:image/png;base64," + encodedImage);
                    //base64Img = RequestBody.create(MediaType.parse("text/plain"), "data:image/png;base64," + encodedImage);
                    //New...
                    BitmapFactory.Options options = Utils.getImageSize(selectedImage);
                    if (options.outWidth >= 250 && options.outHeight >= 250) {
                        updateProfilePicture(encodedImage);
                    } else {
                        Utils.toastMessage(EditProfileActivity.this, "Please Select Image more than 250X250 ");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
                requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes.toByteArray());
                body = MultipartBody.Part.createFormData("profile_img", "image.jpg", requestFile);
                ivImage.setImageBitmap(thumbnail);*/

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == -1) {
            cameraPopUP();
        }
    }

    private void cameraPopUP() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_pin_pop_up);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView text = dialog.findViewById(R.id.tv_err_msg);
        text.setText("App require Camera Permission to Take photo");
        TextView dialogButton = dialog.findViewById(R.id.tv_ok);
        TextView dialogButtonCancel = dialog.findViewById(R.id.tv_cancel);

        dialogButton.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        });

        dialogButtonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

}