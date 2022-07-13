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
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.ui.home.HomeFragment;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.Utils;

public class HomeBookingListAdapter extends RecyclerView.Adapter<HomeBookingListAdapter.HomeBookingListViewHolder> {

    Context context;
    Activity activity;
    HomeFragment fragment;
    ArrayList<BookingListResponse.DayGroup> list;

    public OnCheckInClickable onCheckInClickable;


    public HomeBookingListAdapter(Context context, OnCheckInClickable onCheckInClickable, ArrayList<BookingListResponse.DayGroup> recyclerModelArrayList, FragmentActivity activity, HomeFragment homeFragment) {
        this.context = context;
        this.onCheckInClickable =  onCheckInClickable;
        this.list = recyclerModelArrayList;
        this.activity =activity;
        this.fragment = homeFragment;
    }


    public interface  OnCheckInClickable{
        public void onCheckInDeskClick(BookingListResponse.DayGroup.CalendarEntry calendarEntriesModel, String click, Date date,int position);
        public void onCheckInMeetingRoomClick(BookingListResponse.DayGroup.MeetingBooking meetingEntriesModel, String click, Date date,int position);
        public void onCheckInCarParkingClick(BookingListResponse.DayGroup.CarParkBooking carParkingEntriesModel, String click, Date date,int position);
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
//            System.out.println("DayInTextAndNumber"+Utils.getDayAndDateFromDateFormat(list.get(position).getDate()));
            holder.today_date.setText(""+Utils.getDayAndDateFromDateFormat(list.get(position).getDate()));
            holder.lineLayout.setVisibility(View.GONE);
        }
        else {
            holder.dateLayout.setVisibility(View.GONE);
            holder.lineLayout.setVisibility(View.VISIBLE);
        }

        if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate()) == 1){
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
            holder.bookingIvEdit.setVisibility(View.GONE);
            System.out.println("date check Balaaaa"+list.get(position).getDate()+" : "+Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate()));
        } else if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
            holder.today_date.setText("Today");
        }

        if (list.get(position).getCalDeskStatus() ==1 &&
                list.get(position).getCalendarEntriesModel()
                        .getUsageTypeAbbreviation().equalsIgnoreCase("IO")){
            holder.rlBookingRemoteBlock.setVisibility(View.GONE);
            holder.rlInOffice.setVisibility(View.VISIBLE);
            //Desk Booking
            Glide.with(context)
                    .load(R.drawable.chair)
                    .placeholder(R.drawable.chair)
                    .into(holder.bookingImage);
            // TODO: 27-06-2022
            String name = String.valueOf(list.get(position).getCalendarEntriesModel().getBooking().getId());
            holder.bookingDeskName.setText(list.get(position).getCalendarEntriesModel().getBooking().getDeskCode());
            holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()));
            holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));


/*
        Below Switch case Logic
            If status id ==3 i will show check in
            If status id ==2 i will show check out
            If status id ==1 i will remove  checkIn and checkOut
*/
            switch (list.get(position).getCalendarEntriesModel().getBooking().getStatus().getId()){
                case 3:
                    if (Utils.getCurrentDate()
                            .equalsIgnoreCase(Utils.getYearMonthDateFormat(list.get(position).getDate()))
                            &&
                            Utils.compareTimeIfCheckInEnable(
                            Utils.getCurrentTime(),
                            Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            ))
                        holder.bookingBtnCheckIn.setVisibility(View.VISIBLE);
                    else
                        holder.bookingBtnCheckIn.setVisibility(View.GONE);

                    holder.bookingBtnCheckOut.setVisibility(View.GONE);
                    break;
                case 2:
                    if (Utils.getCurrentDate()
                            .equalsIgnoreCase(Utils.getYearMonthDateFormat(list.get(position).getDate())) &&
                            Utils.compareTimeIfCheckInEnable(
                                    Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()),
                                    Utils.getCurrentTime()
                            ))
                        holder.bookingBtnCheckOut.setVisibility(View.VISIBLE);
                    else
                        holder.bookingBtnCheckOut.setVisibility(View.GONE);

                    holder.bookingBtnCheckIn.setVisibility(View.GONE);
//                    holder.bookingBtnCheckOut.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    holder.bookingBtnCheckIn.setVisibility(View.GONE);
                    holder.bookingBtnCheckOut.setVisibility(View.GONE);
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
                    break;
                default:
            }
        }
        else if (list.get(position).getCalDeskStatus() ==1 &&
                !list.get(position).getCalendarEntriesModel()
                .getUsageTypeAbbreviation().equalsIgnoreCase("IO")){
            holder.rlBookingRemoteBlock.setVisibility(View.VISIBLE);
            holder.rlInOffice.setVisibility(View.GONE);
            holder.bookingBtnCheckIn.setVisibility(View.GONE);
            holder.bookingBtnCheckOut.setVisibility(View.GONE);

            switch (list.get(position).getCalendarEntriesModel().getUsageTypeAbbreviation()){
                case "RQ":
                    holder.tvBookingWorkingRemote.setText("Request for Desk In Progress");
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("Request for Desk In Progress");
                    break;
                case "WFH":
                    holder.tvBookingWorkingRemote.setText("You're Working remotely");
                    holder.tvSubBookingWorkingRemote.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            +" - "
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("You're Working remotely");
                    break;
                case "WOO":
                    holder.tvBookingWorkingRemote.setText("You're Working in alternative office");

                    holder.tvSubBookingWorkingRemote.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            +" - "
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));

                    list.get(position).getCalendarEntriesModel().setUsageTypeName("You're Working in alternative office");

                    break;
                case "TR":
                    holder.tvBookingWorkingRemote.setText("You're in training");
                    holder.tvSubBookingWorkingRemote.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            +" - "
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));

                    list.get(position).getCalendarEntriesModel().setUsageTypeName("You're in training");
                    break;
                case "OO":
                    holder.tvBookingWorkingRemote.setText("Out of office");
                    holder.tvSubBookingWorkingRemote.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            +" - "
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));

                    list.get(position).getCalendarEntriesModel().setUsageTypeName("Out of office");

                    break;
                case "SL":
                    holder.tvBookingWorkingRemote.setText("You're on Sick Leave");
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("You're on Sick Leave");
                    break;

            }
        }else if (list.get(position).getCalDeskStatus() == 2){
            //Meeting Room
            holder.bookingBtnCheckIn.setVisibility(View.GONE);
            holder.bookingBtnCheckOut.setVisibility(View.GONE);

            Glide.with(context)
                    .load(R.drawable.room)
                    .placeholder(R.drawable.room)
                    .into(holder.bookingImage);

            holder.bookingDeskName.setText(""+list.get(position).getMeetingBookingsModel().getMeetingRoomName());
            holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getMeetingBookingsModel().getFrom()));
            holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getMeetingBookingsModel().getMyto()));

        } else if (list.get(position).getCalDeskStatus() == 3){
            //Car Parking
            holder.bookingBtnCheckIn.setVisibility(View.GONE);
            holder.bookingBtnCheckOut.setVisibility(View.GONE);

            Glide.with(context)
                    .load(R.drawable.car)
                    .placeholder(R.drawable.car)
                    .into(holder.bookingImage);

            holder.bookingDeskName.setText(""+list.get(position).getCarParkBookingsModel().getParkingSlotCode());
            holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getCarParkBookingsModel().getFrom()));
            holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getCarParkBookingsModel().getMyto()));
        }

        //CheckOut Button Click
        holder.bookingBtnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.changeCheckOut(list.get(holder.getAbsoluteAdapterPosition()),holder.getAbsoluteAdapterPosition());
            }
        });

        //CheckIn Button Click
        holder.bookingBtnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel()!=null){
                    String clickedStatus="CHECKIN";
                onCheckInClickable.onCheckInDeskClick(list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel(), AppConstants.CHECKIN, list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
//                    fragment.changeCheckIn(list.get(holder.getAbsoluteAdapterPosition()));
                    Toast.makeText(context, "DESK CLICKED", Toast.LENGTH_SHORT).show();
                }else if(list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel()!=null){
                    String clickedStatus="CHECKIN";
                    onCheckInClickable.onCheckInMeetingRoomClick(list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel(),AppConstants.CHECKIN, list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
                    //Toast.makeText(context, "ROOM CLICKED", Toast.LENGTH_SHORT).show();
                }else if(list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel()!=null){
                    String clickedStatus="CHECKIN";
                    onCheckInClickable.onCheckInCarParkingClick(list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel(),AppConstants.CHECKIN, list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
                    //Toast.makeText(context, "CAR CLICKED", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.bookingIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.get(holder.getAbsoluteAdapterPosition()).getCalDeskStatus() ==1){
                    String clickedStatus="EDIT";
                    onCheckInClickable.onCheckInDeskClick(list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel(), AppConstants.EDIT,list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
                    //Toast.makeText(context, "DESK CLICKED", Toast.LENGTH_SHORT).show();
                }else if(list.get(holder.getAbsoluteAdapterPosition()).getCalDeskStatus() ==2){
                    String clickedStatus="EDIT";
                    onCheckInClickable.onCheckInMeetingRoomClick(list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel(),AppConstants.EDIT, list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
                    //Toast.makeText(context, "ROOM CLICKED", Toast.LENGTH_SHORT).show();
                }else if(list.get(holder.getAbsoluteAdapterPosition()).getCalDeskStatus() ==3){
                    String clickedStatus="EDIT";
                    onCheckInClickable.onCheckInCarParkingClick(list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel(),AppConstants.EDIT, list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
                    //Toast.makeText(context, "CAR CLICKED", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.rlBookingRemoteBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel()!=null){
                    String clickedStatus="REMOTE";
                    onCheckInClickable.onCheckInDeskClick(list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel(), AppConstants.REMOTE,list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
                    //Toast.makeText(context, "DESK CLICKED", Toast.LENGTH_SHORT).show();
                }else if(list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel()!=null){
                    String clickedStatus="REMOTE";
                    onCheckInClickable.onCheckInMeetingRoomClick(list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel(),AppConstants.REMOTE, list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
                    //Toast.makeText(context, "ROOM CLICKED", Toast.LENGTH_SHORT).show();
                }else if(list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel()!=null){
                    String clickedStatus="REMOTE";
                    onCheckInClickable.onCheckInCarParkingClick(list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel(),AppConstants.REMOTE, list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
                    //Toast.makeText(context, "CAR CLICKED", Toast.LENGTH_SHORT).show();
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
        @BindView(R.id.bookingBtnCheckOut)
        Button bookingBtnCheckOut;
        @BindView(R.id.bookingIvIcon)
        ImageView bookingImage;
        @BindView(R.id.rlDateLayout)
        RelativeLayout dateLayout;

        @BindView(R.id.today_date)
        TextView today_date;
        @BindView(R.id.tvBookingWorkingRemote)
        TextView tvBookingWorkingRemote;
        @BindView(R.id.tvSubBookingWorkingRemote)
        TextView tvSubBookingWorkingRemote;

        @BindView(R.id.rlLineLayout)
        RelativeLayout lineLayout;
        @BindView(R.id.rlInOffice)
        RelativeLayout rlInOffice;
        @BindView(R.id.bookingWorkingRemoteBlock)
        RelativeLayout rlBookingRemoteBlock;
        @BindView(R.id.card)
        CardView card;

        @BindView(R.id.bookingIvEdit)
        ImageView bookingIvEdit;


        public HomeBookingListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

}
