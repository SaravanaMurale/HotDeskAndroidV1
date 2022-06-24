package dream.guys.hotdeskandroid.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;


public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        int userId=SessionHandler.getInstance().getInt(SignInActivity.this, AppConstants.USER_ID);

        if(userId>0){
            launchHomeActivity();
        }else if(userId==0) {
            btnSignIn.setVisibility(View.VISIBLE);
        }




        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent=new Intent(SignInActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();



            }
        });

    }

    private void launchHomeActivity() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);

    }
}