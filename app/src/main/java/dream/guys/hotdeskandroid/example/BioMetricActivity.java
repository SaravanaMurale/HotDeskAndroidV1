package dream.guys.hotdeskandroid.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.Executor;

import dream.guys.hotdeskandroid.R;

public class BioMetricActivity extends AppCompatActivity {

    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_metric);

        BiometricManager biometricManager=BiometricManager.from(this);

        switch (biometricManager.canAuthenticate()){

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "No HardWare Found", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Not Working", Toast.LENGTH_SHORT).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "No FingerPrint Assigned", Toast.LENGTH_SHORT).show();
                break;


                /*Started Here*/
        }


        Executor executor= ContextCompat.getMainExecutor(this);

        biometricPrompt=new BiometricPrompt(BioMetricActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Toast.makeText(BioMetricActivity.this, "BioMetric Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo=new BiometricPrompt.PromptInfo.Builder().setTitle("HotDesk")
                .setDescription("Use Fingerprint To Login").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);


    }


}