package dream.guys.hotdeskandroid.ui.wellbeing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;

public class NotificationsListActivity extends AppCompatActivity {

    ViewPager mainViewpager;
    TabLayout tabLayout;
    InComingNotiFragment inComingNotiFragment;
    OutgoingNotiFragment outgoingNotiFragment;
    PastNotiFragment pastNotiFragment;

    ArrayList<IncomingRequestResponse.Result> notiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_list);

        uiInit();


    }

    private void uiInit() {
        tabLayout = findViewById(R.id.tabs);
        mainViewpager = findViewById(R.id.viewpager);

        Intent intent = getIntent();

        if (intent!=null) {

            notiList = (ArrayList<IncomingRequestResponse.Result>) intent.getSerializableExtra(AppConstants.SHOWNOTIFICATION);

            inComingNotiFragment = new InComingNotiFragment(notiList);
            outgoingNotiFragment = new OutgoingNotiFragment(notiList);
            pastNotiFragment = new PastNotiFragment(notiList);

            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(inComingNotiFragment, "Notification");
            adapter.addFragment(outgoingNotiFragment, "Request");
            //adapter.addFragment(pastNotiFragment, "Past");

            mainViewpager.setAdapter(adapter);

            tabLayout.setupWithViewPager(mainViewpager);
            //tabLayout.addOnTabSelectedListener(this);
            mainViewpager.setCurrentItem(0);



        }


    }


}