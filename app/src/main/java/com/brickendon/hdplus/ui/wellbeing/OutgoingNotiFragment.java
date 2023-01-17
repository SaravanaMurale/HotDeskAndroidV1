package com.brickendon.hdplus.ui.wellbeing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import com.brickendon.hdplus.adapter.AdapterNotificationList;
import com.brickendon.hdplus.databinding.FragmentRequestTabBinding;
import com.brickendon.hdplus.databinding.FragmentWellbeingBinding;
import com.brickendon.hdplus.model.response.IncomingRequestResponse;

public class OutgoingNotiFragment extends Fragment {

    ArrayList<IncomingRequestResponse.Result> notiList;
    AdapterNotificationList adapterNotificationList;

    FragmentRequestTabBinding binding;

    public OutgoingNotiFragment(ArrayList<IncomingRequestResponse.Result> notiList) {
        this.notiList = notiList;
    }

    public OutgoingNotiFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRequestTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //adapterNotificationList = new AdapterNotificationList(getActivity(),notiList,"");
        //binding.recyclerview.setAdapter(adapterNotificationList);

        return root;
    }
}
