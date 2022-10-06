package dream.guys.hotdeskandroid.ui.wellbeing;

import static dream.guys.hotdeskandroid.utils.Utils.getAppKeysPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getWellBeingScreenData;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.LanguageListActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentWellbeingBinding;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.response.WellbeingConfigResponse;
import dream.guys.hotdeskandroid.ui.home.EditProfileActivity;
import dream.guys.hotdeskandroid.ui.login.pin.CreatePinActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.MyApp;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WellbeingFragment extends Fragment {

    private static final String TAG = "WellbeingFragment";
    FragmentWellbeingBinding binding;


    @BindView(R.id.viewProfileBlock)
    CardView viewProfileBlock;

    @BindView(R.id.btnResetPin)
    RelativeLayout btnResetPin;
    @BindView(R.id.btnLogout)
    RelativeLayout btnLogout;

    String type="",personalData= "", benefits="", events="", health="", notice="", rewards="",healthtips="",menu="";
    List<WellbeingConfigResponse.Link> linksArrayPersonal = new ArrayList<>();
    List<WellbeingConfigResponse.Link> linksArrayBenefits = new ArrayList<>();
    List<WellbeingConfigResponse.Link> linksArrayEvents = new ArrayList<>();
    List<WellbeingConfigResponse.Link> linksArrayHealth = new ArrayList<>();
    List<WellbeingConfigResponse.Link> linksArrayNotice = new ArrayList<>();
    List<WellbeingConfigResponse.Link> linksArrayRewards = new ArrayList<>();
    List<WellbeingConfigResponse.Link> linksArrayHealthTips = new ArrayList<>();

    List<WellbeingConfigResponse.Link> linksArrayMenu = new ArrayList<>();

    LanguagePOJO.AppKeys appKeysPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWellbeingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getWellBeingConfigData();

        binding.healthTipsBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WellbeingCommonActivity.class);
                //intent.putExtra(AppConstants.WELLBEING_TYPE, "Health Tips");
                intent.putExtra(AppConstants.WELLBEING_TYPE, binding.tvHealthTips.getText());
                intent.putExtra(AppConstants.PERSONAL_CONTENT, healthtips);
                Log.d(TAG, "onClick: "+linksArrayHealthTips.size());
                intent.putExtra(AppConstants.PERSONAL_LINKS, (Serializable) linksArrayHealthTips);
                startActivity(intent);
                /*
                Intent intent=new Intent(getActivity(),FireWardensActivity.class);
                intent.putExtra("WELL_BEING_KEY","HEALTH_TIPS");
                startActivity(intent);*/
            }
        });

        binding.fireWardensBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),FireWardensActivity.class);
                intent.putExtra("WELL_BEING_KEY","FIRE");
                startActivity(intent);
            }
        });

        binding.firstAidBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),FireWardensActivity.class);
                intent.putExtra("WELL_BEING_KEY","FIRST_AID");
                startActivity(intent);
            }
        });

        binding.workspaceSurveyBlock.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getActivity(),WorkspaceSurveyActivity.class);
                startActivity(intent);
            }
        });
        binding.mentalHealthBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Intent intent=new Intent(getActivity(),TrainingActivity.class);*/
                Intent intent=new Intent(getActivity(), WellbeingCommonActivity.class);
                //intent.putExtra(AppConstants.WELLBEING_TYPE, "Mental Health");
                intent.putExtra(AppConstants.WELLBEING_TYPE, binding.mentalHealth.getText());
                intent.putExtra(AppConstants.PERSONAL_CONTENT, health);
                Log.d(TAG, "onClick: "+linksArrayPersonal.size());
                intent.putExtra(AppConstants.PERSONAL_LINKS, (Serializable) linksArrayHealth);
                startActivity(intent);
            }
        });

/*
        binding.mentalHealthBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),FireWardensActivity.class);
                intent.putExtra("WELL_BEING_KEY","MENTAL");
                startActivity(intent);
            }
        });
*/

        binding.noticesBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(getActivity(),CreateNoticeActivity.class);
                startActivity(intent);*/
                Intent intent=new Intent(getActivity(), WellbeingCommonActivity.class);
                //intent.putExtra(AppConstants.WELLBEING_TYPE, "Notice");
                intent.putExtra(AppConstants.WELLBEING_TYPE, binding.tvNoti.getText());
                intent.putExtra(AppConstants.PERSONAL_CONTENT, notice);
                Log.d(TAG, "onClick: "+linksArrayPersonal.size());
                intent.putExtra(AppConstants.PERSONAL_LINKS, (Serializable) linksArrayNotice);
                startActivity(intent);
            }
        });

        binding.rewardsBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(getActivity(),CreateNoticeActivity.class);
                startActivity(intent);*/
                Intent intent=new Intent(getActivity(), WellbeingCommonActivity.class);
                //intent.putExtra(AppConstants.WELLBEING_TYPE, "Rewards");
                intent.putExtra(AppConstants.WELLBEING_TYPE, binding.tvRewards.getText());
                intent.putExtra(AppConstants.PERSONAL_CONTENT, rewards);
                Log.d(TAG, "onClick: "+linksArrayPersonal.size());
                intent.putExtra(AppConstants.PERSONAL_LINKS, (Serializable) linksArrayRewards);
                startActivity(intent);
            }
        });

        binding.personalBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PersonalHelpActivity.class);
                intent.putExtra(AppConstants.PERSONAL_CONTENT, personalData);
                Log.d(TAG, "onClick: "+linksArrayPersonal.size());
                intent.putExtra(AppConstants.PERSONAL_LINKS, (Serializable) linksArrayPersonal);
                startActivity(intent);
            }
        });

        binding.workspaceBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WorkspaceAssessmentActivity.class);
                startActivity(intent);
            }
        });

        binding.reportAnIssueBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ReportAnIssueActivity.class);
                startActivity(intent);
            }
        });

        binding.traininBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(getActivity(),TrainingActivity.class);*/
                Intent intent=new Intent(getActivity(), WellbeingCommonActivity.class);
                //intent.putExtra(AppConstants.WELLBEING_TYPE, "Benefits");
                intent.putExtra(AppConstants.WELLBEING_TYPE, binding.txtBenefits.getText());
                intent.putExtra(AppConstants.PERSONAL_CONTENT, benefits);
                Log.d(TAG, "onClick: "+linksArrayPersonal.size());
                intent.putExtra(AppConstants.PERSONAL_LINKS, (Serializable) linksArrayBenefits);
                startActivity(intent);
            }
        });

        binding.eventBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Intent intent=new Intent(getActivity(),TrainingActivity.class);*/
                Intent intent=new Intent(getActivity(), WellbeingCommonActivity.class);
                //intent.putExtra(AppConstants.WELLBEING_TYPE, "Events");
                intent.putExtra(AppConstants.WELLBEING_TYPE, binding.tvEvents.getText());
                intent.putExtra(AppConstants.PERSONAL_CONTENT, events);
                Log.d(TAG, "onClick: "+linksArrayPersonal.size());
                intent.putExtra(AppConstants.PERSONAL_LINKS, (Serializable) linksArrayEvents);
                startActivity(intent);
            }
        });

        binding.healthBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Intent intent=new Intent(getActivity(),TrainingActivity.class);*/
                Intent intent=new Intent(getActivity(), WellbeingCommonActivity.class);
                //intent.putExtra(AppConstants.WELLBEING_TYPE, "Health Eating");
                intent.putExtra(AppConstants.WELLBEING_TYPE, binding.txtHealtheat.getText());
                intent.putExtra(AppConstants.PERSONAL_CONTENT, health);
                Log.d(TAG, "onClick: "+linksArrayPersonal.size());
                intent.putExtra(AppConstants.PERSONAL_LINKS, (Serializable) linksArrayHealth);
                startActivity(intent);
            }
        });

        binding.leaveBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),LeaveActivity.class);
                startActivity(intent);

            }
        });

        binding.covidCertifcatetBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),CovidCertificationActivity.class);
                startActivity(intent);
            }
        });



        //New...
        setLanguage();

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SessionHandler.getInstance().removeAll(getContext());
                SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                Utils.finishAllActivity(getContext());

            }
        });
        binding.btnResetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPinPopUp();

            }
        });

        binding.viewProfileBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);

            }
        });

        binding.welBeingLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getContext(), LanguageListActivity.class);
                startActivity(intent);

            }
        });

        binding.notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        binding.menuBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), WellbeingCommonActivity.class);
                intent.putExtra(AppConstants.WELLBEING_TYPE, binding.txtMenu.getText());
                intent.putExtra(AppConstants.PERSONAL_CONTENT, menu);
                Log.d(TAG, "onClick: "+linksArrayMenu.size());
                intent.putExtra(AppConstants.PERSONAL_LINKS, (Serializable) linksArrayMenu);
                startActivity(intent);
            }
        });


        return root;
    }

    private void getWellBeingConfigData()
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<WellbeingConfigResponse>> call = apiService.getWellbeingSectionConfig();
        call.enqueue(new Callback<List<WellbeingConfigResponse>>() {
            @Override
            public void onResponse(Call<List<WellbeingConfigResponse>> call, Response<List<WellbeingConfigResponse>> response) {

                if (response.body() != null) {

                    /*Training - type 1
                    Events = type 2
                    Health tips = type 3
                    firewarden - 4
                    firstaid - 5
                    mental health - 6
                    Menu - 7
                    Notices - 8
                    Personnel help - 9
                    Repot issue - 10
                    Rewards - 11
                    Workspace survey - 12
                    Workspace assessment - 13*/

                    setEnableDisable(response.body());

                benefits = response.body().get(0).getDescription();
                events = response.body().get(1).getDescription();
                healthtips = response.body().get(2).getDescription();
                health = response.body().get(5).getDescription();
                notice = response.body().get(7).getDescription();
                rewards = response.body().get(10).getDescription();

                personalData = response.body().get(8).getDescription();
                menu = response.body().get(6).getDescription();

//                Toast.makeText(getActivity(), ""+response.body().size(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < response.body().get(0).getLinks().size(); i++) {
                    Log.d(TAG, "onResponse: " + response.body().get(0).getLinks().size());
                    linksArrayBenefits.add(response.body().get(0).getLinks().get(i));
                }

                for (int i = 0; i < response.body().get(1).getLinks().size(); i++) {
                    Log.d(TAG, "onResponse: " + response.body().get(1).getLinks().size());
                    linksArrayEvents.add(response.body().get(1).getLinks().get(i));
                }

                for (int i = 0; i < response.body().get(2).getLinks().size(); i++) {
                    Log.d(TAG, "onResponse: " + response.body().get(2).getLinks().size());
                    linksArrayHealthTips.add(response.body().get(2).getLinks().get(i));
                }


                for (int i = 0; i < response.body().get(5).getLinks().size(); i++) {
                    Log.d(TAG, "onResponse: " + response.body().get(5).getLinks().size());
                    linksArrayHealth.add(response.body().get(5).getLinks().get(i));
                }

                for (int i = 0; i < response.body().get(6).getLinks().size(); i++) {
                    Log.d(TAG, "onResponse: " + response.body().get(6).getLinks().size());
                    linksArrayMenu.add(response.body().get(6).getLinks().get(i));
                }

                for (int i = 0; i < response.body().get(7).getLinks().size(); i++) {
                    Log.d(TAG, "onResponse: " + response.body().get(7).getLinks().size());
                    linksArrayNotice.add(response.body().get(7).getLinks().get(i));
                }

                for (int i = 0; i < response.body().get(10).getLinks().size(); i++) {
                    Log.d(TAG, "onResponse: " + response.body().get(10).getLinks().size());
                    linksArrayRewards.add(response.body().get(10).getLinks().get(i));
                }

                for (int i = 0; i < response.body().get(8).getLinks().size(); i++) {
                    Log.d(TAG, "onResponse: " + response.body().get(8).getLinks().size());
                    linksArrayPersonal.add(response.body().get(8).getLinks().get(i));
                }


                }

            }

            @Override
            public void onFailure(Call<List<WellbeingConfigResponse>> call, Throwable t)
            {
                System.out.println("on failure weill" + t.getMessage());
//                Toast.makeText(getActivity(), "onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setEnableDisable(List<WellbeingConfigResponse> body) {

        if (!(body.get(0).isActive())){
            binding.traininBlock.setEnabled(false);
            binding.tvTraining.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgTraining.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(1).isActive())){
            binding.eventBlock.setEnabled(false);
            binding.tvEvents.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgEvents.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(2).isActive())){
            binding.healthTipsBlock.setEnabled(false);
            binding.tvHealthTips.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgHealth.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(3).isActive())){
            binding.fireWardensBlock.setEnabled(false);
            binding.firewar.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgFWar.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(4).isActive())){
            binding.firstAidBlock.setEnabled(false);
            binding.fireaid.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgFAid.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(5).isActive())){
            binding.mentalHealthBlock.setEnabled(false);
            binding.mentalHealth.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgMHealth.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(6).isActive())){
            binding.menuBlock.setEnabled(false);
            binding.tvMenu.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgMenu.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(7).isActive())){
            binding.noticesBlock.setEnabled(false);
            binding.tvNotification.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgNotice.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(8).isActive())){
            binding.personalBlock.setEnabled(false);
            binding.tvPersonal.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgPersonal.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(9).isActive())){
            binding.reportAnIssueBlock.setEnabled(false);
            binding.tvReportIssue.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgReportIssue.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(10).isActive())){
            binding.rewardsBlock.setEnabled(false);
            binding.tvRewards.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgRewards.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(11).isActive())){
            binding.workspaceSurveyBlock.setEnabled(false);
            binding.tvWorkspaceSurvey.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgWorkspaceSurvey.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (!(body.get(12).isActive())){
            binding.workspaceBlock.setEnabled(false);
            binding.tvWorkspace.setTextColor(getResources().getColor(R.color.grey, getActivity().getTheme()));
            binding.imgWorkAss.setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey),android.graphics.PorterDuff.Mode.SRC_IN);
        }

    }

    public void setLanguage() {

        LanguagePOJO.WellBeing wellBeingPage = getWellBeingScreenData(getActivity());
        appKeysPage = getAppKeysPageScreenData(getActivity());
        
        if (wellBeingPage!=null) {
            
            binding.profileBack.setText("");
            binding.tvContact.setText(wellBeingPage.getDefault());
            binding.tvEmail.setText(wellBeingPage.getWorkSchedule());
            binding.tvTeams.setText(wellBeingPage.getWorkHours());
            binding.tvPhone.setText(wellBeingPage.getLocation());
            binding.tvDesk.setText(wellBeingPage.getDesks());
            binding.tvPreference.setText(wellBeingPage.getPreference());
            binding.tvLang.setText(wellBeingPage.getLanguage());
            binding.tvNoti.setText(wellBeingPage.getNotifications());
            binding.tvApp.setText(wellBeingPage.getApp());
            binding.tvPin.setText(wellBeingPage.getSetUpPin());
            binding.tvBio.setText(wellBeingPage.getSetUpBiometric());
            binding.tvReset.setText(wellBeingPage.getResetPassword());
            binding.tvReport.setText(wellBeingPage.getReportAnIssue());
            binding.tvHelp.setText(wellBeingPage.getHelp());
            binding.tvLogout.setText(wellBeingPage.getLogOut());

            //New...
            binding.title.setText(wellBeingPage.getTabTitle());
            binding.txtHealthsaf.setText(appKeysPage.getHealthAndSafety());
            binding.tvHealthTips.setText(appKeysPage.getHealthTips());
            binding.firewar.setText(appKeysPage.getFireWarden());
            binding.fireaid.setText(appKeysPage.getFirstAid());
            binding.mentalHealth.setText(appKeysPage.getMentalHealth());
            binding.txtHr.setText(appKeysPage.getHumanResources());
            binding.tvLeave.setText(appKeysPage.getLeave());
            binding.tvReportIssue.setText(appKeysPage.getReportAnIssue());
            binding.txtPlaces.setText(appKeysPage.getPlaces());
            binding.txtBenefits.setText(appKeysPage.getBenefits());
            binding.tvTraining.setText(appKeysPage.getTraining());
            binding.txtEvents.setText(appKeysPage.getEvents());
            binding.tvEvents.setText(appKeysPage.getEvents());
            binding.txtHealtheat.setText(appKeysPage.getHealthEating());
            binding.tvHealth.setText(appKeysPage.getHealthEating());
            binding.txtNotices.setText(appKeysPage.getNotices());
            binding.tvNotification.setText(appKeysPage.getNotices());
            binding.txtCovid.setText(appKeysPage.getCovid());
            binding.tvCovid.setText(wellBeingPage.getCovidCertification());
            binding.txtPHelp.setText(appKeysPage.getPersonalHelp());
            binding.tvPersonal.setText(appKeysPage.getPersonalHelp());
            binding.txtRewards.setText(wellBeingPage.getRewards());
            binding.tvRewards.setText(wellBeingPage.getRewards());
            binding.txtWork.setText(wellBeingPage.getWorkPlaceAssessment());
            binding.tvWorkspace.setText(wellBeingPage.getWorkPlaceAssessment());
            binding.txtWorksur.setText(appKeysPage.getWorkSpaceSurvey());
            binding.tvWorkspaceSurvey.setText(appKeysPage.getWorkSpaceSurvey());

        }

    }

    private void checkPinPopUp() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.20);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_pin_pop_up);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView text = dialog.findViewById(R.id.tv_err_msg);
        text.setText("The option to login using a pin is now available. \n To enable please select continue");
        TextView dialogButton = dialog.findViewById(R.id.tv_ok);
        TextView dialogButtonCancel = dialog.findViewById(R.id.tv_cancel);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreatePinActivity.class);
                getActivity().startActivity(intent);
                dialog.dismiss();
            }
        });
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

}