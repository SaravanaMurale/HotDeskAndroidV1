package dream.guys.hotdeskandroid.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.EditDefaultAssetAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityEditProfileBinding;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.DefaultAssetResponse;
import dream.guys.hotdeskandroid.model.response.ProfilePicResponse;
import dream.guys.hotdeskandroid.model.response.TeamDeskResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.ui.wellbeing.NotificationActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements EditDefaultAssetAdapter.OnDefaultAssetSelectable {

    ActivityEditProfileBinding binding;
    UserDetailsResponse profileData; //= new UserDetailsResponse();
    private BottomSheetDialog attachChooser;
    public static final int REQUEST_IMAGE = 100;

    String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_profile);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getProfilePicture();

        Gson gson = new Gson();
        String json = SessionHandler.getInstance().get(EditProfileActivity.this, AppConstants.LOGIN_RESPONSE);
        if (json!=null){
            profileData = gson.fromJson(json, UserDetailsResponse.class);
        }


        binding.tvUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        binding.profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.profileUpdate.setVisibility(View.VISIBLE);
                binding.profileEdit.setVisibility(View.GONE);
                makeEnable();
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

                String name = binding.editName.getText().toString();
                String editDisplayName = binding.editDisplayName.getText().toString();
                String phone = binding.tvEditPhone.getText().toString();
                String email = binding.tvEditEmail.getText().toString();
                String teams = binding.tvEditTeams.getText().toString();
                String startTime = binding.editStartTime.getText().toString();
                String endTime = binding.editEndTime.getText().toString();
                String vehicleNum = binding.editVehicleNum.getText().toString();

                if (isValidate(phone,email)){
                    //profileData.setFullName(name);
                    profileData.setFullName(editDisplayName);
                    profileData.setPhoneNumber(phone);
                    profileData.setEmail(email);
                    profileData.getCurrentTeam().setCurrentTeamName(teams);
                    profileData.setWorkHoursFrom("2000-01-01T" + startTime + ":00.000Z");
                    profileData.setWorkHoursTo("2000-01-01T" + endTime + ":00.000Z");
                    profileData.setVehicleRegNumber(vehicleNum);

                    callProfileUpdate();
                }

            }
        });


        binding.editStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.bottomSheetTimePickerInBooking(EditProfileActivity.this, EditProfileActivity.this, binding.editStartTime
                        , "Start", "");
            }
        });

        binding.editEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.bottomSheetTimePickerInBooking(EditProfileActivity.this, EditProfileActivity.this, binding.editEndTime,
                        "End", "");
                //Utils.bottomSheetTimePicker(getContext(),getActivity(),startTime,"Start Time","");
            }
        });



        if (profileData!=null) {

            binding.editName.setText(profileData.getFullName());
            binding.editDisplayName.setText(profileData.getFullName());
            if (profileData.getCurrentTeam()!=null){
                binding.tvEditTeams.setText(profileData.getCurrentTeam().getCurrentTeamName());
            }
            binding.tvEditPhone.setText(profileData.getPhoneNumber());
            binding.tvEditEmail.setText(profileData.getEmail());
            if (profileData.getDefaultLocation()!=null){
                binding.editDefaultLocaton.setText(profileData.getDefaultLocation().getName());
            }

            binding.editStartTime.setText(Utils.splitTime(profileData.getWorkHoursFrom()));
            binding.editEndTime.setText(Utils.splitTime(profileData.getWorkHoursTo()));
            binding.editVehicleNum.setText(profileData.getVehicleRegNumber());
            binding.tvEditTel.setText(profileData.getDeskPhoneNumber());

        }

        binding.editDefaultLocaton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(EditProfileActivity.this,EditProfileActivity.class);
                startActivity(intent);*/
            }
        });

    }

    private boolean isValidate(String phone,String email) {

        if (phone.isEmpty() || phone.length()<=6){
            Toast.makeText(EditProfileActivity.this, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }else if (email.isEmpty() || !(Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches())){
            Toast.makeText(EditProfileActivity.this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }

    }

    private void getDeskName() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TeamDeskResponse>> call = apiService.getDeskListInEdit();
        call.enqueue(new Callback<List<TeamDeskResponse>>() {
            @Override
            public void onResponse(Call<List<TeamDeskResponse>> call, Response<List<TeamDeskResponse>> response) {

                List<TeamDeskResponse> teamDeskResponseList =response.body();

                callDeskBottomSheetDialogToSelectDeskCode(teamDeskResponseList);
            }

            @Override
            public void onFailure(Call<List<TeamDeskResponse>> call, Throwable t) {

            }
        });



    }

    private void doRemoveProfilePicture() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.removeProfilePicture();
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

               Utils.toastMessage(EditProfileActivity.this,"Image Removed Successfully");

                Glide.with(EditProfileActivity.this).load(R.drawable.mygirl)
                        .into(binding.ivEditPrifle);

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }


    private void getProfilePicture() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProfilePicResponse> call = apiService.getProfilePicture();
        call.enqueue(new Callback<ProfilePicResponse>() {
            @Override
            public void onResponse(Call<ProfilePicResponse> call, Response<ProfilePicResponse> response) {

                ProfilePicResponse profilePicResponse=response.body();

                System.out.println("Base64Image"+profilePicResponse.getImage());

                if(profilePicResponse.getImage()!=null &&
                        !profilePicResponse.getImage().equalsIgnoreCase("") &&
                        !profilePicResponse.getImage().isEmpty() ) {
                    String base64String = profilePicResponse.getImage();
                    String base64Image = base64String.split(",")[1];
                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    binding.ivEditPrifle.setImageBitmap(decodedByte);
                }

            }

            @Override
            public void onFailure(Call<ProfilePicResponse> call, Throwable t) {

            }
        });
    }

    private void selectImage() {
        attachChooser = new BottomSheetDialog(EditProfileActivity.this);
        attachChooser.setContentView((EditProfileActivity.this).getLayoutInflater().inflate(R.layout.popup_add_attach_options,
                new LinearLayout(EditProfileActivity.this)));
        attachChooser.show();
        //TODO: lang
        LinearLayout btnStartCamera = (LinearLayout) attachChooser.findViewById(R.id.btn_from_camera);
        LinearLayout btnStartFileBrowser = (LinearLayout) attachChooser.findViewById(R.id.btn_from_local);
        btnStartCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachChooser.dismiss();
                if (Utils.checkCameraPermission(EditProfileActivity.this))
                    cameraIntent();
            }
        });
        btnStartFileBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attachChooser.dismiss();
                if (Utils.checkCameraPermission(EditProfileActivity.this))
                    galleryIntent();
            }
        });
    }

    private void cameraIntent() {
        Intent intent = new Intent(EditProfileActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void galleryIntent() {
        Intent intent = new Intent(EditProfileActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                // loading profile image from local cache
                //loadProfile(uri.toString());
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = null;
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    getStringImage(bitmap);
                    String bast64InString= String.valueOf("data:image/png;base64," + encodedImage);
                    //base64Img = RequestBody.create(MediaType.parse("text/plain"), "data:image/png;base64," + encodedImage);

                    //New...
                    BitmapFactory.Options options = Utils.getImageSize(uri);
                    if (options.outWidth>=250 && options.outHeight>=250){
                        updateProfilePicture(bast64InString);
                    }else {
                        Utils.toastMessage(EditProfileActivity.this,"Please Select Image more than 250X250 ");
                    }


                    System.out.println();
                    Glide.with(this).load(uri.toString())
                            .into(binding.ivEditPrifle);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        return encodedImage;
    }

    private void updateProfilePicture(String bast64InString) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        ProfilePicResponse profilePicResponse=new ProfilePicResponse();
        profilePicResponse.setImage(bast64InString);

        Call<BaseResponse> call = apiService.updateProfilePicture(profilePicResponse);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                getProfilePicture();

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });



    }


    private void callProfileUpdate() {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.updateSetting(profileData);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                binding.profileUpdate.setVisibility(View.GONE);
                binding.profileEdit.setVisibility(View.VISIBLE);
                updateProfileValue();
                makeDisable();

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                //Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                updateProfileValue();
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                binding.profileUpdate.setVisibility(View.GONE);
                binding.profileEdit.setVisibility(View.VISIBLE);
                makeDisable();

            }
        });

    }

    public void updateProfileValue(){
        Gson gson = new Gson();
        String json = gson.toJson(profileData);
        SessionHandler.getInstance().save(EditProfileActivity.this,AppConstants.LOGIN_RESPONSE,json);
//        Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
    }

    private void makeEnable() {
        binding.editName.setEnabled(true);
        binding.editDisplayName.setEnabled(true);
        binding.editStartTime.setEnabled(true);
        binding.editEndTime.setEnabled(true);
        binding.editDefaultLocaton.setEnabled(true);

        binding.editDeskChange.setEnabled(true);
        binding.editParkChange.setEnabled(true);
        binding.editRoomChange.setEnabled(true);
        binding.tvEditEmail.setEnabled(true);
        binding.tvEditPhone.setEnabled(true);
        binding.tvEditTeams.setEnabled(true);
        binding.editVehicleNum.setEnabled(true);
        binding.tvEditTel.setEnabled(true);

        if (profileData!=null && profileData.getHighestRole()!=null &&
                profileData.getHighestRole().equalsIgnoreCase("Administrator")){
            binding.editDisplayName.setEnabled(true);
            binding.editName.setEnabled(false);
        }else {
            binding.editDisplayName.setEnabled(false);
            binding.editName.setEnabled(false);
        }

        binding.editDeskChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeskName();
            }
        });

        binding.editRoomChange.setTextColor(getResources().getColor(R.color.figmaBlue,getTheme()));
        binding.editDeskChange.setTextColor(getResources().getColor(R.color.figmaBlue,getTheme()));
        binding.editParkChange.setTextColor(getResources().getColor(R.color.figmaBlue,getTheme()));
        binding.editStartTime.setTextColor(getResources().getColor(R.color.figmaBlue,getTheme()));
        binding.editEndTime.setTextColor(getResources().getColor(R.color.figmaBlue,getTheme()));
        binding.changeCountry.setTextColor(getResources().getColor(R.color.figmaBlue,getTheme()));

    }
    private void makeDisable() {
        binding.editName.setEnabled(false);
        binding.editDisplayName.setEnabled(false);
        binding.editStartTime.setEnabled(false);
        binding.editEndTime.setEnabled(false);
        binding.editDefaultLocaton.setEnabled(false);

        binding.editDeskChange.setEnabled(false);
        binding.editParkChange.setEnabled(false);
        binding.editRoomChange.setEnabled(false);
        binding.tvEditEmail.setEnabled(false);
        binding.tvEditPhone.setEnabled(false);
        binding.tvEditTeams.setEnabled(false);
        binding.editVehicleNum.setEnabled(false);
        binding.tvEditTel.setEnabled(false);

        binding.editRoomChange.setTextColor(getResources().getColor(R.color.grey,getTheme()));
        binding.editDeskChange.setTextColor(getResources().getColor(R.color.grey,getTheme()));
        binding.editParkChange.setTextColor(getResources().getColor(R.color.grey,getTheme()));
        binding.editStartTime.setTextColor(getResources().getColor(R.color.grey,getTheme()));
        binding.editEndTime.setTextColor(getResources().getColor(R.color.grey,getTheme()));
        binding.changeCountry.setTextColor(getResources().getColor(R.color.grey,getTheme()));

    }

    private void callDeskBottomSheetDialogToSelectDeskCode(List<TeamDeskResponse> teamDeskResponseList) {

        RecyclerView rvDeskRecycler;
        EditDefaultAssetAdapter editDefaultAssetAdapter;
        TextView bsRepeatBack;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk,
                new RelativeLayout(this))));

        rvDeskRecycler = bottomSheetDialog.findViewById(R.id.desk_list_select_recycler);
        bsRepeatBack = bottomSheetDialog.findViewById(R.id.bsDeskBack);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);

        bsRepeatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });



        List<DefaultAssetResponse> defaultAssetResponseList=new ArrayList<>();
        for (int i = 0; i <teamDeskResponseList.size() ; i++) {
            DefaultAssetResponse defaultAssetResponse=new DefaultAssetResponse(teamDeskResponseList.get(i).getId(),teamDeskResponseList.get(i).getDeskId(),teamDeskResponseList.get(i).getTeamId(),teamDeskResponseList.get(i).getDesk().getCode());
            defaultAssetResponseList.add(defaultAssetResponse);

        }

        editDefaultAssetAdapter = new EditDefaultAssetAdapter(EditProfileActivity.this, defaultAssetResponseList,bottomSheetDialog,this);
        rvDeskRecycler.setAdapter(editDefaultAssetAdapter);

        bottomSheetDialog.show();

    }


    @Override
    public void onDefaultAssetSelect(int deskId, String code) {
        binding.editDesk.setText(code);
    }
}