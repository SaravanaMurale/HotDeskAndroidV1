package dream.guys.hotdeskandroid.ui.locate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentHomeBinding;
import dream.guys.hotdeskandroid.databinding.FragmentLocateBinding;
import dream.guys.hotdeskandroid.utils.Utils;

public class LocateFragment extends Fragment {
    FragmentLocateBinding binding;

    TextView locateText,title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        locateText= root.findViewById(R.id.locate_Text);
        locateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePicker(getContext(),getActivity(),"End","ckasnckan");
            }
        });
        return root;
    }
}