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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.Utils;

public class HomeBookingListAdapter extends RecyclerView.Adapter<HomeBookingListAdapter.HomeBookingListViewHolder> {

    Context context;
    Activity activity;
    ArrayList<BookingListResponse.DayGroup> list;

    public OnCheckInClickable onCheckInClickable;


    public HomeBookingListAdapter(Context context, OnCheckInClickable onCheckInClickable, ArrayList<BookingListResponse.DayGroup> recyclerModelArrayList, FragmentActivity activity) {
        this.context = context;
        this.onCheckInClickable =  onCheckInClickable;
        this.list = recyclerModelArrayList;
        this.activity =activity;
    }


    public interface  OnCheckInClickable{
        public void onCheckInDeskClick(BookingListResponse.DayGroup.CalendarEntry calendarEntriesModel, String click);
        public void onCheckInMeetingRoomClick(BookingListResponse.DayGroup.MeetingBooking meetingEntriesModel, String click);
        public void onCheckInCarParkingClick(BookingListResponse.DayGroup.CarParkBooking carParkingEntriesModel, String click);
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


            System.out.println("DateFormatPrintHere"+list.get(position).getDate());
            System.out.println("DayInTextAndNumber"+Utils.getDayAndDateFromDateFormat(list.get(position).getDate()));

            holder.today_date.setText(""+Utils.getDayAndDateFromDateFormat(list.get(position).getDate()));
            holder.lineLayout.setVisibility(View.GONE);
        }
        else {
            holder.dateLayout.setVisibility(View.GONE);
            holder.lineLayout.setVisibility(View.VISIBLE);
        }

        if (list.get(position).getCalDeskStatus() ==1 && list.get(position).getCalendarEntriesModel().getUsageTypeAbbreviation().equalsIgnoreCase("IO")){
            //Desk Booking
            Glide.with(context)
                    .load(R.drawable.chair)
                    .placeholder(R.drawable.chair)
                    .into(holder.bookingImage);
            // TODO: 27-06-2022
            String name = String.valueOf(list.get(position).getCalendarEntriesModel().getBooking().getId());
            holder.bookingDeskName.setText(list.get(position).getCalendarEntriesModel().getUsageTypeName());
            holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getFromUTC()));
            holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getToUTC()));
        } else if (list.get(position).getCalDeskStatus() == 2){
            //Meeting Room
            Glide.with(context)
                    .load(R.drawable.room)
                    .placeholder(R.drawable.room)
                    .into(holder.bookingImage);

            holder.bookingDeskName.setText(""+list.get(position).getMeetingBookingsModel().getMeetingRoomName());
            holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getMeetingBookingsModel().getFromUtc()));
            holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getMeetingBookingsModel().getToUtc()));
        } else if (list.get(position).getCalDeskStatus() == 3){
            //Car Parking
            Glide.with(context)
                    .load(R.drawable.car)
                    .placeholder(R.drawable.car)
                    .into(holder.bookingImage);

            holder.bookingDeskName.setText(""+list.get(position).getCarParkBookingsModel().getParkingSlotCode());
            holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getCarParkBookingsModel().getFromUtc()));
            holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getCarParkBookingsModel().getToUtc()));
        }

        if (list.get(position).getCalendarEntriesModel() != null && !list.get(position).getCalendarEntriesModel().getUsageTypeAbbreviation().equalsIgnoreCase("IO")){
            holder.rlBookingRemoteBlock.setVisibility(View.VISIBLE);
            holder.rlInOffice.setVisibility(View.GONE);
        }

        //CheckIn Button Click
        holder.bookingBtnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.get(position).getCalendarEntriesModel()!=null){
                    String clickedStatus="CHECKIN";
                onCheckInClickable.onCheckInDeskClick(list.get(position).getCalendarEntriesModel(), AppConstants.CHECKIN);
                    Toast.makeText(context, "DESK CLICKED", Toast.LENGTH_SHORT).show();
                }else if(list.get(position).getMeetingBookingsModel()!=null){
                    String clickedStatus="CHECKIN";
                    onCheckInClickable.onCheckInMeetingRoomClick(list.get(position).getMeetingBookingsModel(),AppConstants.CHECKIN);
                    Toast.makeText(context, "ROOM CLICKED", Toast.LENGTH_SHORT).show();
                }else if(list.get(position).getCarParkBookingsModel()!=null){
                    String clickedStatus="CHECKIN";
                    onCheckInClickable.onCheckInCarParkingClick(list.get(position).getCarParkBookingsModel(),AppConstants.CHECKIN);
                    Toast.makeText(context, "CAR CLICKED", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.bookingIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.get(position).getCalendarEntriesModel()!=null){
                    String clickedStatus="EDIT";
                    onCheckInClickable.onCheckInDeskClick(list.get(position).getCalendarEntriesModel(), AppConstants.EDIT);
                    Toast.makeText(context, "DESK CLICKED", Toast.LENGTH_SHORT).show();
                }else if(list.get(position).getMeetingBookingsModel()!=null){
                    String clickedStatus="EDIT";
                    onCheckInClickable.onCheckInMeetingRoomClick(list.get(position).getMeetingBookingsModel(),AppConstants.EDIT);
                    Toast.makeText(context, "ROOM CLICKED", Toast.LENGTH_SHORT).show();
                }else if(list.get(position).getCarParkBookingsModel()!=null){
                    String clickedStatus="EDIT";
                    onCheckInClickable.onCheckInCarParkingClick(list.get(position).getCarParkBookingsModel(),AppConstants.EDIT);
                    Toast.makeText(context, "CAR CLICKED", Toast.LENGTH_SHORT).show();
                }

            }
        });





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

        @BindView(R.id.today_date)
        TextView today_date;

        @BindView(R.id.rlLineLayout)
        RelativeLayout lineLayout;
        @BindView(R.id.rlInOffice)
        RelativeLayout rlInOffice;
        @BindView(R.id.bookingWorkingRemoteBlock)
        RelativeLayout rlBookingRemoteBlock;

        @BindView(R.id.bookingIvEdit)
        ImageView bookingIvEdit;


        public HomeBookingListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

}
