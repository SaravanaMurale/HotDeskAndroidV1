package dream.guys.hotdeskandroid.ui.wellbeing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentHomeBinding;
import dream.guys.hotdeskandroid.databinding.FragmentWellbeingBinding;

public class WellbeingFragment extends Fragment {
    FragmentWellbeingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWellbeingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}