package dream.guys.hotdeskandroid.ui.book;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentBookingDetailBinding;
import dream.guys.hotdeskandroid.databinding.FragmentHomeBinding;


public class BookingDetailFragment extends Fragment {

    FragmentBookingDetailBinding fragmentBookingDetailBinding;

    @BindView(R.id.bookingDetailDeskName)
    TextView bookingDetailDeskName;
    @BindView(R.id.bookingDetailAddress)
    TextView bookingDetailAddress;
    @BindView(R.id.bookingDetailCheckInTime)
    TextView bookingDetailCheckInTime;
    @BindView(R.id.bookingCheckOutTime)
    TextView bookingCheckOutTime;

    @BindView(R.id.btnCheckInNow)
    Button btnCheckInNow;

    Dialog dialog;

    String bookName,bookAdddress,bookChecInTime,bookCheckOutTime;


    public BookingDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View view=inflater.inflate(R.layout.fragment_booking_detail, container, false);
        fragmentBookingDetailBinding = FragmentBookingDetailBinding.inflate(inflater, container, false);
        View root = fragmentBookingDetailBinding.getRoot();

        dialog=new Dialog(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
             bookName = bundle.getString("BOOK_NAME", null);
            bookAdddress = bundle.getString("BOOK_ADDRESS", null);
            bookChecInTime = bundle.getString("CHECK_IN_TIME", null);
            bookCheckOutTime = bundle.getString("CHECK_OUT_TIME", null);
        }

        fragmentBookingDetailBinding.bookingDetailDeskName.setText(bookName);
        //fragmentBookingDetailBinding.bookingDetailAddress.setText("");
        fragmentBookingDetailBinding.bookingDetailCheckInTime.setText(bookChecInTime);
        fragmentBookingDetailBinding.bookingCheckOutTime.setText(bookCheckOutTime);


        fragmentBookingDetailBinding. btnCheckInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                openCheckoutDialog();

            }
        });

        return root;
    }

    private void openCheckoutDialog() {

        dialog.setContentView(R.layout.layout_checkout_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView checkDialogClose=dialog.findViewById(R.id.checkDialogClose);

        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        dialog.show();
    }
}