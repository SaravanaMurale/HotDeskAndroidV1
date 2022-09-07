package dream.guys.hotdeskandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.List;

import dream.guys.hotdeskandroid.utils.AppConstants;

public class DeepLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);
        deepLinking();
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }
    private void deepLinking() {
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        String data1= appLinkData.getQueryParameter("typekey"); // you will get the value "value1" from application 1
        String data2= appLinkData.getQueryParameter("id");
        System.out.println("deep chevk"+data1+"     "+data2);
        if(appLinkData != null) {

            AppConstants.FIRSTREFERAL = true;

            List<String> params = appLinkData.getPathSegments();

            AppConstants.REFERALID = params.get(params.size() - 1);
            AppConstants.REFERALCODEE = params.get(params.size() - 2);

            System.out.println("Referal id =" + AppConstants.REFERALID + " Referall Code = " + AppConstants.REFERALCODEE);
//            Toast.makeText(this, "Referal id =" + AppConstants.REFERALID + " Referall Code = " + AppConstants.REFERALCODEE, Toast.LENGTH_LONG).show();
        }

    }

}