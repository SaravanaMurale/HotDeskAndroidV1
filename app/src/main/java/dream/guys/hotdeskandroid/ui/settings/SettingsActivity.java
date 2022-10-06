package dream.guys.hotdeskandroid.ui.settings;

import static dream.guys.hotdeskandroid.utils.MyApp.getContext;
import static dream.guys.hotdeskandroid.utils.Utils.dateWithDayString;
import static dream.guys.hotdeskandroid.utils.Utils.getSettingsPageScreenData;
import static dream.guys.hotdeskandroid.utils.Utils.getWellBeingScreenData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nimbusds.jose.util.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import dream.guys.hotdeskandroid.LanguageListActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.ActivitySettingsBinding;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.request.ChangePasswordRequest;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.ProfilePicResponse;
import dream.guys.hotdeskandroid.model.response.UserDetailsResponse;
import dream.guys.hotdeskandroid.ui.home.EditProfileActivity;
import dream.guys.hotdeskandroid.ui.login.pin.CreatePinActivity;
import dream.guys.hotdeskandroid.ui.wellbeing.CovidCertificationActivity;
import dream.guys.hotdeskandroid.ui.wellbeing.NotificationActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    Context context;
    UserDetailsResponse profileData;
    Dialog dialog,proDialog;

    //For Language
    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage ;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata ;
    LanguagePOJO.Global global;
    LanguagePOJO.Settings settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        context = SettingsActivity.this;

        //New...
        setLanguage();
        if (SessionHandler.getInstance().getBoolean(SettingsActivity.this,AppConstants.DARK_MODE_CHECK))
            binding.switchDarkMode.setChecked(true);
        else
            binding.switchDarkMode.setChecked(false);
        //getProfilePicture();
        profileData = Utils.getLoginData(context);

        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            //int num = pInfo.versionCode;
            binding.helpIcon.setText("Version" + String.valueOf(version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (profileData!=null) {
            binding.tvViewProfileName.setText(profileData.getFullName());
        }

        if (SessionHandler.getInstance().get(context, AppConstants.LANGUAGE)!=null){
            binding.txtLang.setText(SessionHandler.getInstance().get(context, AppConstants.LANGUAGE));
        }


        binding.switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    setNightModeHere(SettingsActivity.this,isChecked);


            }
        });

        binding.settingSendMailBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"feedback@hybridhero.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SettingsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SessionHandler.getInstance().removeAll(getContext());
                SessionHandler.getInstance().saveBoolean(context, AppConstants.LOGIN_CHECK,false);
                Utils.finishAllActivity(context);
                SessionHandler.getInstance().remove(context,AppConstants.COUNTRY_NAME);
                SessionHandler.getInstance().remove(context,AppConstants.BUILDING);
                SessionHandler.getInstance().remove(context,AppConstants.FLOOR);
                SessionHandler.getInstance().remove(context,AppConstants.FULLPATHLOCATION);
                SessionHandler.getInstance().remove(context,AppConstants.PARENT_ID);

            }
        });
        binding.btnResetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPinPopUp();

            }
        });

        binding.passwordResetBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog=new Dialog(SettingsActivity.this);
                proDialog=new Dialog(SettingsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
                int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.20);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.layout_change_password);
                dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                EditText etCurrentPassword=dialog.findViewById(R.id.etCurrentPassword);
                EditText etNewPassword=dialog.findViewById(R.id.etNewPassword);
                EditText etConfirmPassword=dialog.findViewById(R.id.etConfirmPassword);
                TextView tv_titleChangePassword=dialog.findViewById(R.id.tv_titleChangePassword);



                Button buttonChangePassword=dialog.findViewById(R.id.btnChangePasswordSubmit);
                Button buttonChangePasswordCancel=dialog.findViewById(R.id.btnChangePasswordCancel);

                //tv_titleChangePassword.setText(appKeysPage.getChangePassword());
                buttonChangePasswordCancel.setText(appKeysPage.getCancel());
                buttonChangePassword.setText(appKeysPage.getSubmit());
                etCurrentPassword.setHint(appKeysPage.getCurrentPassword());
                etNewPassword.setHint(appKeysPage.getNewPassword());
                etConfirmPassword.setHint(appKeysPage.getConfirmPassword());




                buttonChangePasswordCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                buttonChangePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String currentPassword=etCurrentPassword.getText().toString().trim();
                        String newPassword=etNewPassword.getText().toString().trim();
                        String confirmPassword=etConfirmPassword.getText().toString().trim();
                        if(currentPassword!=null && newPassword.equalsIgnoreCase(confirmPassword)){

                            if (Utils.isNetworkAvailable(SettingsActivity.this)) {

                            proDialog= ProgressDialog.showProgressBar(SettingsActivity.this);
                            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                            ChangePasswordRequest changePasswordRequest=new ChangePasswordRequest(currentPassword,newPassword,confirmPassword);
                            Call<BaseResponse> call=apiService.changePassword(changePasswordRequest);
                            call.enqueue(new Callback<BaseResponse>() {
                                @Override
                                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                    BaseResponse baseResponse=response.body();

                                    dialog.dismiss();
                                    proDialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<BaseResponse> call, Throwable t) {
                                    dialog.dismiss();
                                    proDialog.dismiss();
                                }
                            });

                            }else {
                                Utils.toastMessage(SettingsActivity.this, getResources().getString(R.string.enable_internet));
                            }


                        }


                    }
                });

                dialog.show();



            }
        });

        binding.viewProfileBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, EditProfileActivity.class);
                startActivity(intent);

            }
        });

        binding.langLayout.setOnClickListener(new View.OnClickListener() {
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

        binding.helpAndTroubleShootBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String locationOfPdfFile=Environment.getExternalStorageDirectory() + "/" + "hotdesk"+"/Help.pdf";
                File file=new File(locationOfPdfFile);

                //If File already presest
                if(file.exists() && !file.isDirectory()) {
                    Intent intent=new Intent(SettingsActivity.this,HelpPdfViewActivity.class);
                    startActivity(intent);
                }else {
                    //Or Get the file
                    if (checkPermission()) {
                        getHelpAndPDFHandler();

                    } else {
                        requestPermission();
                    }
                }




            }
        });

        binding.whatNewBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(SettingsActivity.this,WhatsNewActiviy.class);
                startActivity(intent);

            }
        });

    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1003);
        }
    }

    private boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void getHelpAndPDFHandler() {

        if (Utils.isNetworkAvailable(this)) {

            binding.locateProgressBar.setVisibility(View.VISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiService.downloadPdf();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    File apkStorage = null;
                    File outputFile = null;
                    if (response != null) {

                        ResponseBody responseBody = response.body();
                        InputStream is = responseBody.byteStream();

                        try {
                            //Get File if SD card is present
                            if (isSDCardPresent()) {
                                apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + "hotdesk");
                                System.out.println("FileCreated");
                            } else
                                Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                            //If File is not present create directory
                            if (!apkStorage.exists()) {
                                apkStorage.mkdir();
                                System.out.println("DirectorFileCreated");
                                //Log.e(TAG, "Directory Created.");
                            }

                            outputFile = new File(apkStorage, "Help.pdf");//Create Output file in Main File

                            //Create New File if not present
                            if (!outputFile.exists()) {
                                outputFile.createNewFile();
                                //Log.e(TAG, "File Created");


                                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                                byte[] buffer = new byte[1024];//Set buffer type
                                int len1 = 0;//init length
                                while ((len1 = is.read(buffer)) != -1) {
                                    fos.write(buffer, 0, len1);//Write new file
                                }

                                //Close all connection after doing task
                                fos.close();
                                is.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            outputFile = null;

                        }

                    }


                    binding.locateProgressBar.setVisibility(View.GONE);

                    Intent intent = new Intent(SettingsActivity.this, HelpPdfViewActivity.class);
                    startActivity(intent);


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    binding.locateProgressBar.setVisibility(View.GONE);
                }
            });

        }else {
            Utils.toastMessage(this, "Please Enable Internet");
        }


    }

    public boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private void setNightModeHere(SettingsActivity settingsActivity, boolean state) {
        if (state) {
            SessionHandler.getInstance().saveBoolean(SettingsActivity.this,
                    AppConstants.DARK_MODE_CHECK,true);
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);

            //Toast.makeText(target, "Dark", Toast.LENGTH_SHORT).show();
        } else {
            SessionHandler.getInstance().saveBoolean(SettingsActivity.this,
                    AppConstants.DARK_MODE_CHECK,false);
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);

        }

    }


    private void getProfilePicture() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ProfilePicResponse> call = apiService.getProfilePicture();
        call.enqueue(new Callback<ProfilePicResponse>() {
            @Override
            public void onResponse(Call<ProfilePicResponse> call, Response<ProfilePicResponse> response) {

                ProfilePicResponse profilePicResponse=response.body();

                //System.out.println("Base64Image"+profilePicResponse.getImage());

                if(profilePicResponse.getImage()!=null &&
                        !profilePicResponse.getImage().equalsIgnoreCase("") &&
                        !profilePicResponse.getImage().isEmpty() ) {
                    String base64String = profilePicResponse.getImage();
                    String base64Image = base64String.split(",")[1];
                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    binding.ivViewPrifle.setImageBitmap(decodedByte);
                }

            }

            @Override
            public void onFailure(Call<ProfilePicResponse> call, Throwable t) {

            }
        });
    }

    public void setLanguage() {

        LanguagePOJO.Settings wellBeingPage = getSettingsPageScreenData(this);
        logoinPage = Utils.getLoginScreenData(this);
        appKeysPage = Utils.getAppKeysPageScreenData(this);
        resetPage = Utils.getResetPasswordPageScreencreenData(this);
        actionOverLays = Utils.getActionOverLaysPageScreenData(this);
        bookindata = Utils.getBookingPageScreenData(this);
        global=Utils.getGlobalScreenData(this);
        settings=Utils.getSettingsPageScreenData(this);


        binding.settingViewProfile.setText(settings.getViewProfile());

        if (wellBeingPage!=null) {

            //binding.tvContact.setText(wellBeingPage.getDefault());
            binding.tvOrganization.setText(wellBeingPage.getChangeOrganisation());
            binding.tvDarkMode.setText(wellBeingPage.getDarkMode());
            binding.tvPhone.setText(wellBeingPage.getWhatsnew());
            binding.tvDesk.setText(wellBeingPage.getFeedback());
            binding.tvPreference.setText(wellBeingPage.getPreference());
            binding.tvLang.setText(wellBeingPage.getLanguage());
            binding.tvNoti.setText(wellBeingPage.getNotifications());
            binding.tvApp.setText(wellBeingPage.getApp());
            binding.tvPin.setText(wellBeingPage.getSetUpPin());
            binding.tvBio.setText(wellBeingPage.getSetUpBiometric());
            binding.tvReset.setText(wellBeingPage.getResetPassword());
            binding.tvReport.setText(wellBeingPage.getHelp());
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

    @Override
    protected void onResume() {
        super.onResume();
        getProfilePicture();
        profileData = Utils.getLoginData(context);
        if (profileData!=null) {
            binding.tvViewProfileName.setText(profileData.getFullName());
        }
    }
}