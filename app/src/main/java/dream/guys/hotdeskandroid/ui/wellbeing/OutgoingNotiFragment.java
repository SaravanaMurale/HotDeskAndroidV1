package dream.guys.hotdeskandroid.ui.wellbeing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;

public class OutgoingNotiFragment extends Fragment {

    ArrayList<IncomingRequestResponse.Result> notiList;

    public OutgoingNotiFragment(ArrayList<IncomingRequestResponse.Result> notiList) {
        this.notiList = notiList;
    }

    public OutgoingNotiFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
