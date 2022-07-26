package dream.guys.hotdeskandroid.ui.book;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentBookBinding;

public class BookFragment extends Fragment {
    FragmentBookBinding binding;
    @BindView(R.id.desk_layout)
    LinearLayout deskLayout;
    @BindView(R.id.room_layout)
    LinearLayout roomLayout;
    @BindView(R.id.parking_layout)
    LinearLayout parkingLayout;
    @BindView(R.id.more_layout)
    LinearLayout moreLayout;

    @BindView(R.id.iv_desk)
    ImageView ivDesk;
    @BindView(R.id.iv_room)
    ImageView ivRoom;
    @BindView(R.id.iv_parking)
    ImageView ivParking;
    @BindView(R.id.iv_more)
    ImageView ivMore;

    @BindView(R.id.tv_desk)
    TextView tvDesk;
    @BindView(R.id.tv_room)
    TextView tvRoom;
    @BindView(R.id.tv_parking)
    TextView tvParking;
    @BindView(R.id.tv_more)
    TextView tvMore;

    int selectedicon = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.deskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabToggleViewClicked(0);
            }
        });


        return root;
    }

    @SuppressLint("ResourceAsColor")
    private void tabToggleViewClicked(int i) {
        resetLayout();
        switch (i){
            case 0:
                binding.deskLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBlue));
                binding.ivDesk.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.white));
                binding.tvDesk.setTextColor(R.color.white);
                binding.tvDesk.setVisibility(View.VISIBLE);

                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;

        }
    }

    private void resetLayout() {
        binding.deskLayout.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(),R.color.figmaBgGrey));
        binding.roomLayout.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.figmaBgGrey));
        binding.parkingLayout.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.figmaBgGrey));
        binding.moreLayout.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.figmaBgGrey));
    }
}