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
import dream.guys.hotdeskandroid.R;


public class BookingDetailFragment extends Fragment {

    @BindView(R.id.btnCheckInNow)
    Button btnCheckInNow;

    Dialog dialog;


    public BookingDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_booking_detail, container, false);

        dialog=new Dialog(getContext());

        btnCheckInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                openCheckoutDialog();

            }
        });

        return view;
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