package com.brickendon.hdplus.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.SessionHandler;

public class ProfileActivity extends AppCompatActivity {
    @BindView(R.id.profile_back)
    ImageView backIcon;
    @BindView(R.id.user_profile_pic)
    CircleImageView profileImage;
    @BindView(R.id.profileUserName)
    TextView profileUserName;
    @BindView(R.id.profileTeamName)
    TextView profileTeamName;
    @BindView(R.id.tv_team)
    TextView tvTeam;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.view_teams)
    TextView viewTeams;
    @BindView(R.id.cv_email)
    CardView cvEmail;
    @BindView(R.id.cv_phone)
    CardView cvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        uiInit();
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ViewTeamsActivity.class);
                startActivity(intent);
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvEmail.getText().toString().equalsIgnoreCase("email")){
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+tvEmail.getText()));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "email_subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "email_body");
                    startActivity(intent);
                }
            }
        });
        cvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvPhone.getText().toString().equalsIgnoreCase("phone")){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+tvPhone.getText()));
                    startActivity(intent);
                }
            }
        });
    }

    private void uiInit() {
        profileUserName.setText(SessionHandler.getInstance().get(this, AppConstants.USERNAME));
        profileTeamName.setText(SessionHandler.getInstance().get(this, AppConstants.CURRENT_TEAM));
        if (SessionHandler.getInstance().get(this, AppConstants.EMAIL)!=null &&!SessionHandler.getInstance().get(this, AppConstants.EMAIL).isEmpty()
                &&
                !SessionHandler.getInstance().get(this, AppConstants.EMAIL).equalsIgnoreCase(""))
            tvEmail.setText(SessionHandler.getInstance().get(this, AppConstants.EMAIL));
        if (SessionHandler.getInstance().get(this, AppConstants.PHONE_NUMBER) != null &&!SessionHandler.getInstance().get(this, AppConstants.PHONE_NUMBER).isEmpty()
                && !SessionHandler.getInstance().get(this, AppConstants.PHONE_NUMBER).equalsIgnoreCase(""))
            tvPhone.setText(SessionHandler.getInstance().get(this, AppConstants.PHONE_NUMBER));
        tvTeam.setText(SessionHandler.getInstance().get(this, AppConstants.CURRENT_TEAM));
        if (SessionHandler.getInstance().get(this, AppConstants.USERIMAGE)!=null){
            byte[] decodedString = Base64.decode(
                    SessionHandler.getInstance().get(this, AppConstants.USERIMAGE),
                    Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImage.setImageBitmap(decodedByte);
        }
    }

/*

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
    private File getImageFile() {
        String imageFileName = "JPEG_" + System.currentTimeMillis() + "_";
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM
                ), "Camera"
        );
        File file = null;
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

    private void galleryIntent() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, SELECT_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Utils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals(commonStrings.getTxt_take_photo().getName()))
                        selectImage();
                    else if (userChoosenTask.equals(commonStrings.getTxt_choose_from_gallery().getName()))
                        selectImage();
                } else {
                }
                break;
        }
    }

*/

}