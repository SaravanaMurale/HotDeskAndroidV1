package dream.guys.hotdeskandroid.ui.teams;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dream.guys.hotdeskandroid.databinding.FragmentTeamsBinding;

public class TeamsFragment extends Fragment {

    FragmentTeamsBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTeamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}