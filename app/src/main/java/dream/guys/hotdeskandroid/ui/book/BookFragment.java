package dream.guys.hotdeskandroid.ui.book;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dream.guys.hotdeskandroid.databinding.FragmentBookBinding;

public class BookFragment extends Fragment {
    FragmentBookBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}