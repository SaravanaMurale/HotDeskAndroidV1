package dream.guys.hotdeskandroid.ui.locate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentHomeBinding;
import dream.guys.hotdeskandroid.databinding.FragmentLocateBinding;

public class LocateFragment extends Fragment {
    FragmentLocateBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}