package dream.guys.hotdeskandroid.ui.wellbeing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentWellbeingBinding;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;

public class WellbeingFragment extends Fragment {
    FragmentWellbeingBinding binding;

    @BindView(R.id.btnLogout)
    Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWellbeingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionHandler.getInstance().removeAll(getContext());

                Utils.finishAllActivity(getContext());

            }
        });



        return root;
    }
}