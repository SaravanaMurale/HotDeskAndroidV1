package com.brickendon.hdplus.ui.wellbeing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.databinding.ActivityLeaveBinding;

public class LeaveActivity extends AppCompatActivity {

    ActivityLeaveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_leave);

        binding=ActivityLeaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.reqTimeOffBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callRequestTimeOffBottomSheet();
            }
        });
    }

    private void callRequestTimeOffBottomSheet() {
        TextView reqTimeOffSelect;
        BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(LeaveActivity.this, R.style.AppBottomSheetDialogTheme);
        locateCheckInBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.layout_request_timeoff_bottomsheet,
                new RelativeLayout(LeaveActivity.this)));

        reqTimeOffSelect=locateCheckInBottomSheet.findViewById(R.id.reqTimeOffSelect);

        reqTimeOffSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRequestTimeSelectType();
            }
        });


        locateCheckInBottomSheet.show();
    }

    private void callRequestTimeSelectType() {

        BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(LeaveActivity.this, R.style.AppBottomSheetDialogTheme);
        locateCheckInBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.layout_timeoff_tyep_bottomsheet,
                new RelativeLayout(LeaveActivity.this)));

        locateCheckInBottomSheet.show();

    }
}