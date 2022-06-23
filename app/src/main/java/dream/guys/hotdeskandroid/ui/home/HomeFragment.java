package dream.guys.hotdeskandroid.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentHomeBinding;
import dream.guys.hotdeskandroid.utils.Utils;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        text= root.findViewById(R.id.text_home);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePicker(getContext(),getActivity(),"Start","30th date");
            }
        });
        return root;
    }
}