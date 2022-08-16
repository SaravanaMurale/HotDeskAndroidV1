package dream.guys.hotdeskandroid.ui.wellbeing;

import static dream.guys.hotdeskandroid.utils.MyApp.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Binder;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.ActivityNotificationBinding;

public class CreateNoticeActivity extends AppCompatActivity {

    ActivityNotificationBinding binding;

    @BindView(R.id.createNoticeBlock)
    CardView createNoticeBlock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notice);

        //binding=ActivityNotificationBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        ButterKnife.bind(this);

        createNoticeBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCreateNotieBottomSheet();
            }
        });



    }

    private void callCreateNotieBottomSheet() {
        BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(CreateNoticeActivity.this, R.style.AppBottomSheetDialogTheme);
        locateCheckInBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_create_notice_bottomsheet,
                new RelativeLayout(CreateNoticeActivity.this)));


        locateCheckInBottomSheet.show();
    }
}