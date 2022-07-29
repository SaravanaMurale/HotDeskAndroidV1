package dream.guys.hotdeskandroid.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;

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
    }

    private void uiInit() {
        profileUserName.setText(SessionHandler.getInstance().get(this, AppConstants.USERNAME));
        profileTeamName.setText(SessionHandler.getInstance().get(this, AppConstants.CURRENT_TEAM));
        if (!SessionHandler.getInstance().get(this, AppConstants.EMAIL).isEmpty()&&SessionHandler.getInstance().get(this, AppConstants.EMAIL).equalsIgnoreCase(""))
            tvEmail.setText(SessionHandler.getInstance().get(this, AppConstants.EMAIL));
        if (!SessionHandler.getInstance().get(this, AppConstants.PHONE_NUMBER).isEmpty()
                && SessionHandler.getInstance().get(this, AppConstants.PHONE_NUMBER).equalsIgnoreCase(""))
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
}