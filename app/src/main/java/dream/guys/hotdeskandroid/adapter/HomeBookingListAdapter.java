package dream.guys.hotdeskandroid.adapter;

import static dream.guys.hotdeskandroid.utils.MyApp.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.controllers.OtherBookingController;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.ui.home.HomeFragment;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;

public class HomeBookingListAdapter extends RecyclerView.Adapter<HomeBookingListAdapter.HomeBookingListViewHolder> {

    private static final String TAG = "HomeBookingListAdapter";
    Context context;
    Activity activity;
    HomeFragment fragment;
    ArrayList<BookingListResponse.DayGroup> list;

    public OnCheckInClickable onCheckInClickable;
    HashMap<Integer,String> map;

    boolean pastCheck = false;
    public HomeBookingListAdapter(Context context, OnCheckInClickable onCheckInClickable, ArrayList<BookingListResponse.DayGroup> recyclerModelArrayList, FragmentActivity activity, HomeFragment homeFragment, HashMap<Integer,String> map) {
        this.context = context;
        this.onCheckInClickable =  onCheckInClickable;
        this.list = recyclerModelArrayList;
        this.activity =activity;
        this.fragment = homeFragment;
        this.map=map;
    }


    public interface  OnCheckInClickable{
        public void onCheckInDeskClick(BookingListResponse.DayGroup.CalendarEntry calendarEntriesModel, String click, Date date,int position);
        public void onCheckInMeetingRoomClick(BookingListResponse.DayGroup.MeetingBooking meetingEntriesModel, String click, Date date,int position);
        public void onCheckInCarParkingClick(BookingListResponse.DayGroup.CarParkBooking carParkingEntriesModel, String click, Date date,int position);

        public void onLocationIconClick(int parentLocationId, int meetingRoomId, String desk);
    }

    @NonNull
    @Override
    public HomeBookingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_list_recycler_layout, parent, false);
        return new HomeBookingListViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HomeBookingListViewHolder holder, int position) {
        holder.rlBookingRemoteBlock.setVisibility(View.GONE);
        holder.rlInOffice.setVisibility(View.VISIBLE);
        //conditon is to display date and line accordingly
        if (list.get(position).isDateStatus()) {
            holder.dateLayout.setVisibility(View.VISIBLE);
//            System.out.println("DateFormatPrintHere"+list.get(position).getDate());
//            System.out.println("DayInTextAndNumber"+Utils.getDayAndDateFromDateFormat(list.get(position).getDate()));
            try {
                String day = Utils.getDayAndDateFromDateFormat(list.get(position).getDate()).split(" ")[0];
                String dateNum = Utils.getDayAndDateFromDateFormat(list.get(position).getDate()).split(" ")[1];
                holder.today_date.setText(""+day+"\n"+dateNum);
                holder.lineLayout.setVisibility(View.GONE);
            }catch (Exception e){

            }
        } else {
            holder.dateLayout.setVisibility(View.GONE);
            holder.lineLayout.setVisibility(View.VISIBLE);
        }

        //        System.out.println("check date of today "+list.size());
        //condition displays today string instead od date and disable previous date
        if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate()) == 1) {
        /*    if (!fragment.showPastStatus){
                holder.allLayout.setVisibility(View.GONE);
                holder.allLinearLayout.setVisibility(View.GONE);
            }
            else{
                holder.allLayout.setVisibility(View.VISIBLE);
                holder.allLinearLayout.setVisibility(View.VISIBLE);
            }
*/
            holder.tv_past_event.setVisibility(View.GONE);
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));

            holder.bookingIvEdit.setVisibility(View.GONE);
            holder.bookingIvLocation.setVisibility(View.GONE);
            holder.tv_change.setVisibility(View.GONE);
            disableColorUpdate(holder);

        } else if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2) {
//            System.out.println("check date of today present "+list.get(position).getDate());
            if (!fragment.showPastStatus && !pastCheck)
                holder.tv_past_event.setVisibility(View.VISIBLE);
            else
                holder.tv_past_event.setVisibility(View.GONE);

            pastCheck = true;
            holder.today_date.setText("Today");
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            enableColorUpdate(holder);
            holder.bookingIvEdit.setVisibility(View.VISIBLE);
            holder.tv_change.setVisibility(View.VISIBLE);
            holder.bookingIvLocation.setVisibility(View.VISIBLE);

        }else {
            System.out.println("check date of today future "+list.get(position).getDate());
            holder.tv_past_event.setVisibility(View.GONE);
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            enableColorUpdate(holder);
            holder.bookingIvEdit.setVisibility(View.VISIBLE);
            holder.tv_change.setVisibility(View.VISIBLE);
            holder.bookingIvLocation.setVisibility(View.VISIBLE);
        }


        if (list.get(position).getCalDeskStatus() ==1 &&
                list.get(position).getCalendarEntriesModel()
                        .getUsageTypeAbbreviation().equalsIgnoreCase("IO")){
            if (Utils.compareTwoDate(list.get(position).getDate(), Utils.getCurrentDate())==2){
                SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Booked");
            }

            holder.rlBookingRemoteBlock.setVisibility(View.GONE);
            holder.rlInOffice.setVisibility(View.VISIBLE);
            try{
                holder.bookingAddress.setText(new StringBuilder()
                        .append("")
                        .append(list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getBuildingName())
                        .append(" - ").append(list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getfLoorName()).toString()
                );
            }catch (Exception e){

            }

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

            try{
                if(Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==1
                        || (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2
                        &&
                        !Utils.compareTimeIfCheckInEnable(
                                Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()),
                                Utils.getCurrentTime()))){
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
                    disableColorUpdate(holder);
                    holder.bookingIvEdit.setVisibility(View.GONE);
                    holder.bookingIvLocation.setVisibility(View.GONE);
                    holder.tv_change.setVisibility(View.GONE);
                } else {
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    enableColorUpdate(holder);
                    holder.bookingIvEdit.setVisibility(View.VISIBLE);
                    holder.bookingIvLocation.setVisibility(View.VISIBLE);
                    holder.tv_change.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){

            }

/*
        Below Switch case Logic
            If status id ==3 i will show check in
            If status id ==2 i will show check out
            If status id ==1 i will remove  checkIn and checkOut
*/
            switch (list.get(position).getCalendarEntriesModel().getBooking().getStatus().getId()){
                case 3:
                    Date dNow = new Date( ); // Instantiate a Date object
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dNow);
                    if (fragment.earlyCheckInTime>0)
                        cal.add(Calendar.MINUTE, fragment.earlyCheckInTime);
                    else
                        cal.add(Calendar.MINUTE, 2);
                    dNow = cal.getTime();
                    SimpleDateFormat f24hours=new SimpleDateFormat("HH:mm");


                    if (Utils.getCurrentDate()
                            .equalsIgnoreCase(Utils.getYearMonthDateFormat(list.get(position).getDate()))
                            &&
                            Utils.compareTimeIfCheckInEnable(
                                    f24hours.format(dNow),
                                    Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            )){
                        holder.greenLine.setVisibility(View.GONE);
                        holder.bookingBtnCheckIn.setVisibility(View.VISIBLE);
                        if(fragment.qrEnabled)
                            holder.bookingBtnCheckIn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.scan_2px, 0);
                        else
                            holder.bookingBtnCheckIn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                    else
                        holder.bookingBtnCheckIn.setVisibility(View.GONE);


                    if (fragment.expiryCheckInTime>0){
                        if (Utils.getCurrentDate()
                                .equalsIgnoreCase(Utils.getYearMonthDateFormat(list.get(position).getDate()))
                                &&
                                Utils.compareTimeIfCheckInEnable(
                                        Utils.getCurrentTime(),
                                        Utils.selectedTimeWithExtraMins(
                                                Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()),
                                                fragment.expiryCheckInTime)
                                )){
                            holder.bookingBtnCheckIn.setVisibility(View.GONE);
                        }else {
//                            holder.bookingBtnCheckIn.setVisibility(View.VISIBLE);
                        }

                    }
                    holder.bookingBtnCheckOut.setVisibility(View.GONE);
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    break;
                case 2:
                    //show green line if its falls between from and to time of today
                    if (Utils.getCurrentDate()
                            .equalsIgnoreCase(Utils.getYearMonthDateFormat(list.get(position).getDate())) &&
                            Utils.compareTimeIfCheckInEnable(
                                    Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()),
                                    Utils.getCurrentTime()
                            )){
                        holder.greenLine.setVisibility(View.VISIBLE);
                        holder.bookingBtnCheckOut.setVisibility(View.VISIBLE);
                    }
                    else{
                        holder.greenLine.setVisibility(View.GONE);
                        holder.bookingBtnCheckOut.setVisibility(View.GONE);
                    }

                    holder.bookingBtnCheckIn.setVisibility(View.GONE);
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
//                    holder.bookingBtnCheckOut.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    holder.bookingBtnCheckIn.setVisibility(View.GONE);
                    holder.bookingBtnCheckOut.setVisibility(View.GONE);
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
                    break;
                default:
            }
        } else if (list.get(position).getCalDeskStatus() ==1 &&
                !list.get(position).getCalendarEntriesModel()
                .getUsageTypeAbbreviation().equalsIgnoreCase("IO")) {
            holder.rlBookingRemoteBlock.setVisibility(View.VISIBLE);
            holder.rlInOffice.setVisibility(View.GONE);
            holder.bookingBtnCheckIn.setVisibility(View.GONE);
            holder.bookingBtnCheckOut.setVisibility(View.GONE);
            try{
                if(Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==1
                        || (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2
                        &&
                        !Utils.compareTimeIfCheckInEnable(
                                Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()),
                                Utils.getCurrentTime()))){
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
                    disableColorUpdate(holder);
                    holder.bookingIvEdit.setVisibility(View.GONE);
                    holder.bookingIvLocation.setVisibility(View.GONE);
                    holder.tv_change.setVisibility(View.GONE);

                } else {
                    enableColorUpdate(holder);
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    holder.bookingIvEdit.setVisibility(View.VISIBLE);
                    holder.bookingIvLocation.setVisibility(View.VISIBLE);
                    holder.tv_change.setVisibility(View.VISIBLE);

                }
            }catch (Exception e){
                Log.d(TAG, "onBindViewHolder: "+e.getMessage());
            }
//            holder.tv_change.setVisibility(View.VISIBLE);

     /*
            if (list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor()!=null)
                holder.bookingAddress.setText(new StringBuilder()
                    .append("")
                    .append(" "+list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getBuildingName())
                    .append(" - ").append(list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getfLoorName()).toString()
            );*/

            switch (list.get(position).getCalendarEntriesModel().getUsageTypeAbbreviation()){
                case "RQ":
                    holder.startTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()));

                    holder.endTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));

                    Glide.with(context)
                            .load(R.drawable.chair)
                            .placeholder(R.drawable.chair)
                            .into(holder.bookingRemoteHome);

//                    holder.tv_change.setVisibility(View.GONE);
                    holder.tvBookingWorkingRemote.setText("Request for Desk In Progress");
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("Request for Desk In Progress");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Request for Desk");
                    }
                    break;
                case "WFH":
                    holder.tvBookingWorkingRemote.setText("You’re working remotely");
                   /* holder.tvSubBookingWorkingRemote.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            +" - "
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));*/
                    holder.startTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()));

                    holder.endTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("You’re working remotely");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Working remotely");
                    }

                    Glide.with(context)
                            .load(R.drawable.home)
                            .placeholder(R.drawable.home)
                            .into(holder.bookingRemoteHome);
                    break;
                case "WOO":
                    holder.tvBookingWorkingRemote.setText("You're Working in alternative office");

                   /* holder.tvSubBookingWorkingRemote.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            +" - "
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));*/
                    holder.startTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()));

                    holder.endTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("You're Working in alternative office");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Alternative Office");
                    }
                    Glide.with(context)
                            .load(R.drawable.home)
                            .placeholder(R.drawable.home)
                            .into(holder.bookingRemoteHome);
                    break;
                case "TR":
                    holder.tvBookingWorkingRemote.setText("You're in training");
                   /* holder.tvSubBookingWorkingRemote.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            +" - "
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));*/

                    holder.startTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()));

                    holder.endTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));

                    list.get(position).getCalendarEntriesModel().setUsageTypeName("You're in training");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Training");
                    }
                    Glide.with(context)
                            .load(R.drawable.training_book)
                            .placeholder(R.drawable.training_book)
                            .into(holder.bookingRemoteHome);
                    break;
                case "OO":
                    holder.tvBookingWorkingRemote.setText("Out of office");
                    holder.startTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()));

                    holder.endTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("Out of office");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Out of Office");
                    }
                    Glide.with(context)
                            .load(R.drawable.out_of_office_plane)
                            .placeholder(R.drawable.out_of_office_plane)
                            .into(holder.bookingRemoteHome);
                    break;
                case "SL":
                    holder.tvBookingWorkingRemote.setText("You’re off sick");
                    holder.startTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()));

                    holder.endTime.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("You’re off sick");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Sick Leave");
                    }
                    Glide.with(context)
                            .load(R.drawable.sick_plus)
                            .placeholder(R.drawable.sick_plus)
                            .into(holder.bookingRemoteHome);
                    break;
                default:
                    holder.tvBookingWorkingRemote.setText(""+list.get(position)
                            .getCalendarEntriesModel().getUsageTypeName());
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("Not assigned to Team");
                    /*
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2) {
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"No Team");
                    }
                    */
                    Glide.with(context)
                            .load(R.drawable.notification_icon)
                            .placeholder(R.drawable.notification_icon)
                            .into(holder.bookingRemoteHome);
            }
        } else if (list.get(position).getCalDeskStatus() == 2) {
            //Meeting Room
            holder.rlBookingRemoteBlock.setVisibility(View.GONE);
            holder.bookingBtnCheckIn.setVisibility(View.GONE);
            holder.bookingBtnCheckOut.setVisibility(View.GONE);
            holder.bookingIvEdit.setVisibility(View.GONE);
            try{
                if(Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==1
                        || (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2
                        &&
                        !Utils.compareTimeIfCheckInEnable(
                                Utils.splitTime(list.get(position).getMeetingBookingsModel().getMyto()),
                                Utils.getCurrentTime()))){
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
                    disableColorUpdate(holder);
                    holder.bookingIvEdit.setVisibility(View.GONE);
                    holder.bookingIvLocation.setVisibility(View.GONE);
                    holder.tv_change.setVisibility(View.GONE);

                } else {
                    enableColorUpdate(holder);
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    if (list.get(position).getMeetingBookingsModel().getBookedByUserId()
                            == SessionHandler.getInstance().getInt(context,AppConstants.USER_ID)) {
                        holder.bookingIvEdit.setVisibility(View.VISIBLE);
                        Glide.with(context).load(R.drawable.ic_home_io_edit).into(holder.bookingIvEdit);
                    } else {
                        Log.d(TAG, "onBindViewHolder: "+list.get(position).getMeetingBookingsModel().getMeetingRoomName());
                        Log.d(TAG, "onBindViewHolder: "+list.get(position).getMeetingBookingsModel().getBookedByUserId());
                        Log.d(TAG, "onBindViewHolder: "+SessionHandler.getInstance().getInt(context,AppConstants.USER_ID));

                        holder.bookingIvEdit.setVisibility(View.VISIBLE);
                        Glide.with(context).load(R.drawable.ic_info_circle_blue).into(holder.bookingIvEdit);
                    }
                    holder.bookingIvLocation.setVisibility(View.VISIBLE);
                    holder.tv_change.setVisibility(View.VISIBLE);
                }
            } catch (Exception e){
                Log.d(TAG, "onBindViewHolder: "+e.getMessage());
            }
            if (map.containsKey(list.get(position).getMeetingBookingsModel().getId())){
                holder.bookingRefreshIcon.setVisibility(View.VISIBLE);
            }
            holder.bookingAddress.setText(new StringBuilder()
                    .append("")
                    .append(list.get(position).getMeetingBookingsModel().getLocationBuildingFloor().getBuildingName())
                    .append(" - ").append(list.get(position).getMeetingBookingsModel().getLocationBuildingFloor().getfLoorName()).toString()
            );
            Glide.with(context)
                    .load(R.drawable.room)
                    .placeholder(R.drawable.room)
                    .into(holder.bookingImage);

            //show green line if its falls between from and to time of today
            if(Utils.getCurrentDate()
                    .equalsIgnoreCase(Utils.getYearMonthDateFormat(list.get(position).getDate()))
                    &&
                    Utils.compareTimeIfCheckInEnable(
                            Utils.getCurrentTime(),
                            Utils.splitTime(list.get(position).getMeetingBookingsModel().getFrom())
                    ) &&
                    Utils.compareTimeIfCheckInEnable(
                            Utils.splitTime(list.get(position).getMeetingBookingsModel().getMyto()),
                            Utils.getCurrentTime())){
                holder.greenLine.setVisibility(View.VISIBLE);
            } else {
                holder.greenLine.setVisibility(View.GONE);
            }

            holder.bookingDeskName.setText(""+list.get(position).getMeetingBookingsModel().getMeetingRoomName());
            holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getMeetingBookingsModel().getFrom()));
            holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getMeetingBookingsModel().getMyto()));

        }
        else if (list.get(position).getCalDeskStatus() == 3
                && !list.get(position)
                .getCarParkBookingsModel().getRequestStatus()
                .equalsIgnoreCase("pending")){
            //Car Parking
            holder.rlBookingRemoteBlock.setVisibility(View.GONE);
            holder.bookingBtnCheckIn.setVisibility(View.GONE);
            holder.bookingBtnCheckOut.setVisibility(View.GONE);
            try{
                if(Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==1
                        || (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2
                        &&
                        !Utils.compareTimeIfCheckInEnable(
                                Utils.splitTime(list.get(position).getCarParkBookingsModel().getMyto()),
                                Utils.getCurrentTime()))){
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
                    disableColorUpdate(holder);
                    holder.bookingIvEdit.setVisibility(View.GONE);
                    holder.bookingIvLocation.setVisibility(View.GONE);
                    holder.tv_change.setVisibility(View.GONE);
                } else {
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    enableColorUpdate(holder);
                    holder.bookingIvEdit.setVisibility(View.VISIBLE);
                    holder.bookingIvLocation.setVisibility(View.VISIBLE);
                    holder.tv_change.setVisibility(View.VISIBLE);
                }
            } catch (Exception e){
                Log.d(TAG, "onBindViewHolder: "+e.getMessage());
            }
            holder.bookingAddress.setText(new StringBuilder()
                    .append("")
                    .append(list.get(position).getCarParkBookingsModel().getLocationBuildingFloor().getBuildingName())
                    .append(" - ").append(list.get(position).getCarParkBookingsModel().getLocationBuildingFloor().getfLoorName()).toString()
            );
            //show green line if its falls between from and to time of today
            if(Utils.getCurrentDate()
                    .equalsIgnoreCase(Utils.getYearMonthDateFormat(list.get(position).getDate()))
                    &&
                    Utils.compareTimeIfCheckInEnable(
                            Utils.getCurrentTime(),
                            Utils.splitTime(list.get(position).getCarParkBookingsModel().getFrom())
                    )&&
                    Utils.compareTimeIfCheckInEnable(
                    Utils.splitTime(list.get(position).getCarParkBookingsModel().getMyto()),
                    Utils.getCurrentTime()
            )){
                holder.greenLine.setVisibility(View.VISIBLE);
            }else {
                holder.greenLine.setVisibility(View.GONE);
            }

            Glide.with(context)
                    .load(R.drawable.car)
                    .placeholder(R.drawable.car)
                    .into(holder.bookingImage);

            holder.bookingDeskName.setText(""+list.get(position).getCarParkBookingsModel().getParkingSlotCode());
            holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getCarParkBookingsModel().getFrom()));
            holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getCarParkBookingsModel().getMyto()));
        }
        else if (list.get(position).getCalDeskStatus() == 3) {
            holder.rlBookingRemoteBlock.setVisibility(View.VISIBLE);
            holder.tv_change.setVisibility(View.GONE);
            holder.rlInOffice.setVisibility(View.GONE);
            holder.bookingBtnCheckIn.setVisibility(View.GONE);
            holder.bookingBtnCheckOut.setVisibility(View.GONE);
            try {
                if(Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==1
                        || (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2
                        &&
                        !Utils.compareTimeIfCheckInEnable(
                                Utils.splitTime(list.get(position).getCarParkBookingsModel().getMyto()),
                                Utils.getCurrentTime()))){
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
                    disableColorUpdate(holder);
                    holder.bookingIvEdit.setVisibility(View.GONE);
                    holder.bookingIvLocation.setVisibility(View.GONE);
                    holder.tv_change.setVisibility(View.GONE);
                } else {
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    enableColorUpdate(holder);
                    holder.bookingIvEdit.setVisibility(View.VISIBLE);
                    holder.bookingIvLocation.setVisibility(View.VISIBLE);
                    holder.tv_change.setVisibility(View.VISIBLE);
                }
            } catch (Exception e){
                Log.e(TAG, "onBindViewHolder: ", e.getCause());
            }

            holder.tvBookingWorkingRemote.setText("Request For Parking - "+list.get(position).getCarParkBookingsModel().getParkingSlotCode());
            holder.startTime.setText(""
                    +Utils.splitTime(list.get(position).getCarParkBookingsModel().getFrom()));

            holder.endTime.setText(""
                    +Utils.splitTime(list.get(position).getCarParkBookingsModel().getMyto()));

            Glide.with(context)
                    .load(R.drawable.car)
                    .placeholder(R.drawable.car)
                    .into(holder.bookingRemoteHome);
        } else {
            holder.tv_change.setVisibility(View.GONE);
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
//                    Toast.makeText(context, "DESK CLICKED", Toast.LENGTH_SHORT).show();
                }
                /*else if(list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel()!=null){
                    String clickedStatus="CHECKIN";
                    onCheckInClickable.onCheckInMeetingRoomClick(list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel(),AppConstants.CHECKIN, list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
                    //Toast.makeText(context, "ROOM CLICKED", Toast.LENGTH_SHORT).show();
                }else if(list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel()!=null){
                    String clickedStatus="CHECKIN";
                    onCheckInClickable.onCheckInCarParkingClick(list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel(),AppConstants.CHECKIN, list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
                    //Toast.makeText(context, "CAR CLICKED", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        holder.bookingIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.get(holder.getAbsoluteAdapterPosition()).getCalDeskStatus() ==1){
                    String clickedStatus="EDIT";
                    onCheckInClickable.onCheckInDeskClick(list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel(),
                            AppConstants.EDIT,list.get(holder.getAbsoluteAdapterPosition()).getDate(),holder.getAbsoluteAdapterPosition());
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

        holder.tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**/
                if (list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel() != null &&
                        (list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel()
                                .getUsageTypeAbbreviation().equalsIgnoreCase("TR") ||
                                list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel()
                                        .getUsageTypeAbbreviation().equalsIgnoreCase("SL") ||
                                list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel()
                                        .getUsageTypeAbbreviation().equalsIgnoreCase("OO") ||
                                list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel()
                                        .getUsageTypeAbbreviation().equalsIgnoreCase("WFH"))) {
                    new OtherBookingController(context,
                            list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel(),
                            list.get(holder.getAbsoluteAdapterPosition()).getDate(), "home");
                } else if(list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel() != null &&
                        (list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel()
                                .getUsageTypeAbbreviation().equalsIgnoreCase("REQ")
                                || list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel()
                                .getUsageTypeAbbreviation().equalsIgnoreCase("RQ"))){
                    if (list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel() != null) {
                        String clickedStatus = "EDIT";
                        onCheckInClickable.onCheckInDeskClick(list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel(), AppConstants.EDIT,
                                list.get(holder.getAbsoluteAdapterPosition()).getDate(), holder.getAbsoluteAdapterPosition());
                        //Toast.makeText(context, "DESK CLICKED", Toast.LENGTH_SHORT).show();
                    } else if (list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel() != null) {
                        String clickedStatus = "EDIT";
                        onCheckInClickable.onCheckInMeetingRoomClick(list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel(), AppConstants.EDIT, list.get(holder.getAbsoluteAdapterPosition()).getDate(), holder.getAbsoluteAdapterPosition());
                        //Toast.makeText(context, "ROOM CLICKED", Toast.LENGTH_SHORT).show();
                    } else if (list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel() != null) {
                        String clickedStatus = "EDIT";
                        onCheckInClickable.onCheckInCarParkingClick(list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel(), AppConstants.EDIT, list.get(holder.getAbsoluteAdapterPosition()).getDate(), holder.getAbsoluteAdapterPosition());
                        //Toast.makeText(context, "CAR CLICKED", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel() != null) {
                        String clickedStatus = "REMOTE";
                        onCheckInClickable.onCheckInDeskClick(list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel(), AppConstants.REMOTE,
                                list.get(holder.getAbsoluteAdapterPosition()).getDate(), holder.getAbsoluteAdapterPosition());
                        //Toast.makeText(context, "DESK CLICKED", Toast.LENGTH_SHORT).show();
                    } else if (list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel() != null) {
                        String clickedStatus = "REMOTE";
                        onCheckInClickable.onCheckInMeetingRoomClick(list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel(), AppConstants.REMOTE, list.get(holder.getAbsoluteAdapterPosition()).getDate(), holder.getAbsoluteAdapterPosition());
                        //Toast.makeText(context, "ROOM CLICKED", Toast.LENGTH_SHORT).show();
                    } else if (list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel() != null) {
                        String clickedStatus = "REMOTE";
                        onCheckInClickable.onCheckInCarParkingClick(list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel(), AppConstants.REMOTE, list.get(holder.getAbsoluteAdapterPosition()).getDate(), holder.getAbsoluteAdapterPosition());
                        //Toast.makeText(context, "CAR CLICKED", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //Click on Location Icon
        holder.bookingIvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel()!=null){

                    //Desk
                    if(list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel().getBooking().getLocationBuildingFloor()!=null){
                        //System.out.println("CalendarParentId "+list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getFloorID());

                        SessionHandler.getInstance().save(getContext(),AppConstants.FULLPATHLOCATION,list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getBuildingName()+","+list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getfLoorName());

                        int parentLocationId=list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getFloorID();
                        int deskId=list.get(holder.getAbsoluteAdapterPosition()).getCalendarEntriesModel().getBooking().getDeskId();
                        onCheckInClickable.onLocationIconClick(parentLocationId, deskId, AppConstants.DESK);
                    }

                }else if(list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel()!=null){

                    //Meeting
                    if(list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel().getLocationBuildingFloor()!=null){
                        //System.out.println("MeetingParentId "+list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel().getLocationBuildingFloor().getFloorID());

                        SessionHandler.getInstance().save(getContext(),AppConstants.FULLPATHLOCATION,list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel().getLocationBuildingFloor().getBuildingName()+","+list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel().getLocationBuildingFloor().getfLoorName());

                        int parentLocationId=list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel().getLocationBuildingFloor().getFloorID();
                        int meetingRoomId=list.get(holder.getAbsoluteAdapterPosition()).getMeetingBookingsModel().getMeetingRoomId();
                        onCheckInClickable.onLocationIconClick(parentLocationId,meetingRoomId, AppConstants.MEETING);

                    }

                }else if(list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel()!=null){
                    //Car
                    if(list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel().getLocationBuildingFloor()!=null){
                        //System.out.println("CarParentId "+list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel().getLocationBuildingFloor().getFloorID());

                        SessionHandler.getInstance().save(getContext(),AppConstants.FULLPATHLOCATION,list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel().getLocationBuildingFloor().getBuildingName()+","+list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel().getLocationBuildingFloor().getfLoorName());

                        int parentLocationId=list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel().getLocationBuildingFloor().getFloorID();
                        int carParkingId=list.get(holder.getAbsoluteAdapterPosition()).getCarParkBookingsModel().getParkingSlotId();
                        onCheckInClickable.onLocationIconClick(parentLocationId, carParkingId, AppConstants.CAR_PARKING);

                    }
                }

            }
        });
    }

    private void disableColorUpdate(HomeBookingListViewHolder holder) {
        holder.bookingImage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.figmaGrey)));
        holder.bookingRemoteHome.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.figmaGrey)));
        holder.remoteChecoutIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.figmaGrey)));
        holder.bookingCheckInIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.figmaGrey)));
        holder.bookingCheckOutIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.figmaGrey)));
        holder.remoteChecinIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.figmaGrey)));
        holder.tvBookingWorkingRemote.setTextColor(ContextCompat.getColor(context,R.color.figmaGrey));
        holder.bookingDeskName.setTextColor(ContextCompat.getColor(context,R.color.figmaGrey));
        holder.startTime.setTextColor(ContextCompat.getColor(context,R.color.figmaGrey));
        holder.endTime.setTextColor(ContextCompat.getColor(context,R.color.figmaGrey));
        holder.bookingCheckInTime.setTextColor(ContextCompat.getColor(context,R.color.figmaGrey));
        holder.bookingCheckOutTime.setTextColor(ContextCompat.getColor(context,R.color.figmaGrey));
    }
    private void enableColorUpdate(HomeBookingListViewHolder holder) {
        holder.bookingImage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.black)));
        holder.bookingRemoteHome.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.black)));
        holder.remoteChecoutIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.figmaBlackGrey)));
        holder.bookingCheckInIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.figmaBlackGrey)));
        holder.bookingCheckOutIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.figmaBlackGrey)));
        holder.remoteChecinIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context,R.color.figmaBlackGrey)));
        holder.tvBookingWorkingRemote.setTextColor(ContextCompat.getColor(context,R.color.black));
        holder.bookingDeskName.setTextColor(ContextCompat.getColor(context,R.color.black));
        holder.startTime.setTextColor(ContextCompat.getColor(context,R.color.black));
        holder.endTime.setTextColor(ContextCompat.getColor(context,R.color.black));
        holder.bookingCheckInTime.setTextColor(ContextCompat.getColor(context,R.color.black));
        holder.bookingCheckOutTime.setTextColor(ContextCompat.getColor(context,R.color.black));
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
        @BindView(R.id.green_line)
        TextView greenLine;
        @BindView(R.id.bookingBtnCheckIn)
        Button bookingBtnCheckIn;
        @BindView(R.id.bookingBtnCheckOut)
        Button bookingBtnCheckOut;
        @BindView(R.id.bookingIvIcon)
        ImageView bookingImage;
        @BindView(R.id.bookingRemoteHome)
        ImageView bookingRemoteHome;
        @BindView(R.id.rlDateLayout)
        RelativeLayout dateLayout;

        @BindView(R.id.tv_change)
        ImageView tv_change;
        @BindView(R.id.tv_past_event)
        LinearLayout tv_past_event;
        @BindView(R.id.today_date)
        TextView today_date;
        @BindView(R.id.tvBookingWorkingRemote)
        TextView tvBookingWorkingRemote;

        @BindView(R.id.startTime)
        TextView startTime;
        @BindView(R.id.endTime)
        TextView endTime;

        @BindView(R.id.all_layout)
        RelativeLayout allLayout;
        @BindView(R.id.all_ll_layout)
        LinearLayout allLinearLayout;
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

        @BindView(R.id.bookingRefreshIcon)
        ImageView bookingRefreshIcon;

        @BindView(R.id.bookingIvLocationIcon)
        ImageView bookingIvLocation;
        @BindView(R.id.remote_checout_icon)
        ImageView remoteChecoutIcon;
        @BindView(R.id.remote_checin_icon)
        ImageView remoteChecinIcon;
        @BindView(R.id.bookingCheckInIcon)
        ImageView bookingCheckInIcon;
        @BindView(R.id.bookingCheckOutIcon)
        ImageView bookingCheckOutIcon;


        public HomeBookingListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

}
