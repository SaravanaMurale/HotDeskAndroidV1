package dream.guys.hotdeskandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Action;
import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;

public class HomeBookingListAdapter extends RecyclerView.Adapter<HomeBookingListAdapter.HomeBookingListViewHolder> {

    Context context;
    Activity activity;
    ArrayList<BookingListResponse.DayGroup> list;

    public HomeBookingListAdapter(Context context, FragmentActivity activity, ArrayList<BookingListResponse.DayGroup> recyclerModelArrayList) {
        this.context= context;
        this.activity= activity;
        this.list = recyclerModelArrayList;

    }

    @NonNull
    @Override
    public HomeBookingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_list_recycler_layout, parent, false);
        return new HomeBookingListViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull HomeBookingListViewHolder holder, int position) {
        if (list.get(position).isDateStatus()){
            holder.dateLayout.setVisibility(View.VISIBLE);
            holder.lineLayout.setVisibility(View.GONE);
        }
        else {
            holder.dateLayout.setVisibility(View.GONE);
            holder.lineLayout.setVisibility(View.VISIBLE);
        }

        if (list.get(position).getCalDeskStatus() ==1 && list.get(position).getCalendarEntriesModel().getUsageTypeAbbreviation().equalsIgnoreCase("IO")){
            Glide.with(context)
                    .load(R.drawable.chair)
                    .placeholder(R.drawable.chair)
                    .into(holder.bookingImage);
            // TODO: 27-06-2022
            System.out.println("check bava"+position);
            String name = String.valueOf(list.get(position).getCalendarEntriesModel().getBooking().getId());
            holder.bookingDeskName.setText(" "+name);

        } else if (list.get(position).getCalDeskStatus() == 2){
            Glide.with(context)
                    .load(R.drawable.room)
                    .placeholder(R.drawable.room)
                    .into(holder.bookingImage);

            holder.bookingDeskName.setText(""+list.get(position).getMeetingBookingsModel().getMeetingRoomName());
        } else if (list.get(position).getCalDeskStatus() == 3){
            Glide.with(context)
                    .load(R.drawable.car)
                    .placeholder(R.drawable.car)
                    .into(holder.bookingImage);

            holder.bookingDeskName.setText(""+list.get(position).getCarParkBookingsModel().getParkingSlotCode());
        }

        if (list.get(position).getCalendarEntriesModel() != null && !list.get(position).getCalendarEntriesModel().getUsageTypeAbbreviation().equalsIgnoreCase("IO")){
            holder.rlBookingRemoteBlock.setVisibility(View.VISIBLE);
            holder.rlInOffice.setVisibility(View.GONE);
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HomeBookingListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.bookingDeskName)
        TextView bookingDeskName;
        @BindView(R.id.bookingAddress)
        TextView bookingAddress;
        @BindView(R.id.bookingCheckInTime)
        TextView bookingCheckInTime;
        @BindView(R.id.bookingCheckOutTime)
        TextView bookingCheckOutTime;
        @BindView(R.id.bookingBtnCheckIn)
        Button bookingBtnCheckIn;
        @BindView(R.id.bookingIvIcon)
        ImageView bookingImage;

        @BindView(R.id.rlDateLayout)
        RelativeLayout dateLayout;
        @BindView(R.id.rlLineLayout)
        RelativeLayout lineLayout;
        @BindView(R.id.rlInOffice)
        RelativeLayout rlInOffice;
        @BindView(R.id.bookingWorkingRemoteBlock)
        RelativeLayout rlBookingRemoteBlock;


        public HomeBookingListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
