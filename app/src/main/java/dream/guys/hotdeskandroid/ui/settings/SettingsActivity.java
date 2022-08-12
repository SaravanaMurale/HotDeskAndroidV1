package dream.guys.hotdeskandroid.ui.settings;

import static dream.guys.hotdeskandroid.utils.Utils.getWellBeingScreenData;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import dream.guys.hotdeskandroid.LanguageListActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.ActivitySettingsBinding;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.ui.home.EditProfileActivity;
import dream.guys.hotdeskandroid.ui.login.pin.CreatePinActivity;
import dream.guys.hotdeskandroid.ui.wellbeing.NotificationActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        context = SettingsActivity.this;

        //New...
        setLanguage();

        if (SessionHandler.getInstance().get(context, AppConstants.LANGUAGE_KEY)!=null){
            binding.txtLang.setText(SessionHandler.getInstance().get(context, AppConstants.LANGUAGE_KEY));
        }

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SessionHandler.getInstance().removeAll(getContext());
                SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK,false);
                Utils.finishAllActivity(context);

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

                Intent intent=new Intent(context, EditProfileActivity.class);
                startActivity(intent);

            }
        });

        binding.welBeingLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, LanguageListActivity.class);
                startActivity(intent);

            }
        });

        binding.notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, NotificationActivity.class);
                startActivity(intent);
            }
        });

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void setLanguage() {

        LanguagePOJO.WellBeing wellBeingPage = getWellBeingScreenData(this);

        if (wellBeingPage!=null) {

            binding.tvContact.setText(wellBeingPage.getDefault());
            binding.tvOrganization.setText(wellBeingPage.getChangeOrganization());
            binding.tvDarkMode.setText(wellBeingPage.getDarkMode());
            binding.tvPhone.setText(wellBeingPage.getWhatsNew());
            binding.tvDesk.setText(wellBeingPage.getDesks());
            binding.tvPreference.setText(wellBeingPage.getPreference());
            binding.tvLang.setText(wellBeingPage.getLanguage());
            binding.tvNoti.setText(wellBeingPage.getNotifications());
            binding.tvApp.setText(wellBeingPage.getApp());
            binding.tvPin.setText(wellBeingPage.getSetUpPin());
            binding.tvBio.setText(wellBeingPage.getSetUpBiometric());
            binding.tvReset.setText(wellBeingPage.getResetPassword());
            binding.tvReport.setText(wellBeingPage.getHelpTroubleShoot());
            binding.tvHelp.setText(wellBeingPage.getAbout());
            binding.tvLogout.setText(wellBeingPage.getLogOut());
            binding.tvSecurity.setText(wellBeingPage.getSecurity());

        }

    }

    private void checkPinPopUp() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.20);
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
                Intent intent = new Intent(context, CreatePinActivity.class);
                context.startActivity(intent);
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