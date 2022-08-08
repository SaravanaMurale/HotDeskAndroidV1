package dream.guys.hotdeskandroid.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityEditProfileBinding;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    UserDetailsResponse profileData; //= new UserDetailsResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_profile);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Gson gson = new Gson();
        String json = SessionHandler.getInstance().get(EditProfileActivity.this, AppConstants.LOGIN_RESPONSE);
        if (json!=null){
            profileData = gson.fromJson(json, UserDetailsResponse.class);
        }

        binding.profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.profileUpdate.setVisibility(View.VISIBLE);
                binding.profileEdit.setVisibility(View.GONE);
                makeEnable();
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

                profileData.setFullName(name);
                profileData.setPhoneNumber(phone);
                profileData.setEmail(email);
                profileData.getCurrentTeam().setCurrentTeamName(teams);
                profileData.setWorkHoursFrom("2000-01-01T" + startTime + ":00.000Z");
                profileData.setWorkHoursTo("2000-01-01T" + endTime + ":00.000Z");

                callProfileUpdate();

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

        binding.editRoomChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDeskBottomSheetDialogToSelectDeskCode();
            }
        });

        if (profileData!=null) {

            binding.editName.setText(profileData.getFullName());
            binding.tvEditTeams.setText(profileData.getCurrentTeam().getCurrentTeamName());
            binding.tvEditPhone.setText(profileData.getPhoneNumber());
            binding.tvEditEmail.setText(profileData.getEmail());
            binding.editDefaultLocaton.setText(profileData.getDefaultLocation().getName());
            binding.editStartTime.setText(Utils.splitTime(profileData.getWorkHoursFrom()));
            binding.editEndTime.setText(Utils.splitTime(profileData.getWorkHoursTo()));

        }

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
                makeDisable();

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }
        });

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

        if (profileData!=null && profileData.getHighestRole()!=null &&
                profileData.getHighestRole().equalsIgnoreCase("Administrator")){
            binding.editDisplayName.setEnabled(true);
        }else {
            binding.editDisplayName.setEnabled(false);
        }

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

    }

    private void callDeskBottomSheetDialogToSelectDeskCode() {

        RecyclerView rvDeskRecycler;
        DeskListRecyclerAdapter deskListRecyclerAdapter;
        TextView bsRepeatBack;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk,
                new RelativeLayout(this))));

        rvDeskRecycler = bottomSheetDialog.findViewById(R.id.desk_list_select_recycler);
        bsRepeatBack = bottomSheetDialog.findViewById(R.id.bsDeskBack);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);

        /*deskListRecyclerAdapter = new DeskListRecyclerAdapter(getContext(), this, getActivity(), teamDeskAvailabilities, getContext(), bottomSheetDialog);
        rvDeskRecycler.setAdapter(deskListRecyclerAdapter);*/

        bottomSheetDialog.show();

    }

}