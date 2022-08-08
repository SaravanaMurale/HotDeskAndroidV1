package dream.guys.hotdeskandroid.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.ImageResponse;
import dream.guys.hotdeskandroid.model.response.ProfilePicResponse;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {


    @BindView(R.id.tvUpdateImage)
    TextView  tvUpdateImage;
    @BindView(R.id.ivEditPrifle)
    CircleImageView circleImageView;
    private BottomSheetDialog attachChooser;
    public static final int REQUEST_IMAGE = 100;

    String encodedImage;
    RequestBody base64Img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);


        getProfilePicture();

        tvUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();

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
                String base64String = profilePicResponse.getImage();
                String base64Image = base64String.split(",")[1];
                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                circleImageView.setImageBitmap(decodedByte);

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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    getStringImage(bitmap);
                    String bast64InString= String.valueOf("data:image/png;base64," + encodedImage);
                    //base64Img = RequestBody.create(MediaType.parse("text/plain"), "data:image/png;base64," + encodedImage);
                    updateProfilePicture(bast64InString);
                    System.out.println();
                    Glide.with(this).load(uri.toString())
                            .into(circleImageView);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        return encodedImage;
    }
}