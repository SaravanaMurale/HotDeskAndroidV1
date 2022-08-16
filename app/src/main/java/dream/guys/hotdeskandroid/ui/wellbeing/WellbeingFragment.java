package dream.guys.hotdeskandroid.ui.wellbeing;

import static dream.guys.hotdeskandroid.utils.Utils.getWellBeingScreenData;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import dream.guys.hotdeskandroid.LanguageListActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentWellbeingBinding;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.ui.home.EditProfileActivity;
import dream.guys.hotdeskandroid.ui.login.pin.CreatePinActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;

public class WellbeingFragment extends Fragment {
    FragmentWellbeingBinding binding;


    @BindView(R.id.viewProfileBlock)
    CardView viewProfileBlock;

    @BindView(R.id.btnResetPin)
    RelativeLayout btnResetPin;
    @BindView(R.id.btnLogout)
    RelativeLayout btnLogout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWellbeingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        binding.healthTipsBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),FireWardensActivity.class);
                intent.putExtra("WELL_BEING_KEY","HEALTH_TIPS");
                startActivity(intent);
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

        binding.mentalHealthBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),FireWardensActivity.class);
                intent.putExtra("WELL_BEING_KEY","MENTAL");
                startActivity(intent);
            }
        });
        
        binding.noticesBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),CreateNoticeActivity.class);
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
                Intent intent=new Intent(getActivity(),TrainingActivity.class);
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


        return root;
    }

    public void setLanguage() {

        LanguagePOJO.WellBeing wellBeingPage = getWellBeingScreenData(getActivity());
        
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