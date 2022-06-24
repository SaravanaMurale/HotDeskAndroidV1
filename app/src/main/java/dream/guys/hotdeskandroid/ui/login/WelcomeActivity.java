package dream.guys.hotdeskandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SessionHandler.getInstance().saveBoolean(WelcomeActivity.this, AppConstants.WELCOME_VIEWED_STATUS,true);
                Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        },1000);
    }
}