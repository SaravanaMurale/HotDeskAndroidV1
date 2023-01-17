package com.brickendon.hdplus.ui.wellbeing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import com.brickendon.hdplus.model.response.IncomingRequestResponse;

public class InComingNotiFragment extends Fragment {

    ArrayList<IncomingRequestResponse.Result> notiList;

    public InComingNotiFragment() {
    }

    public InComingNotiFragment(ArrayList<IncomingRequestResponse.Result> notiList) {
        this.notiList = notiList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
