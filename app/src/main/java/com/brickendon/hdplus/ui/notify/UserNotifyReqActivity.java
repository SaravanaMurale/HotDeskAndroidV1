package com.brickendon.hdplus.ui.notify;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.brickendon.hdplus.databinding.ActivityUserNotifyReqBinding;


public class UserNotifyReqActivity extends AppCompatActivity {

    ActivityUserNotifyReqBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_notify_req);
        binding = com.brickendon.hdplus.databinding.ActivityUserNotifyReqBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}