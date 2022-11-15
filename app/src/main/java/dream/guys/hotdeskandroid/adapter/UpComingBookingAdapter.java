package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.TeamMembersResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;

public class UpComingBookingAdapter extends RecyclerView.Adapter<UpComingBookingAdapter.viewHolder> {

    Context context;
    ArrayList<TeamMembersResponse.DayGroup> list;
    String sample = "";

    public UpComingBookingAdapter(Context context, ArrayList<TeamMembersResponse.DayGroup> recyclerModelArrayList,String sample) {
        this.context = context;
        this.list = recyclerModelArrayList;
        this.sample = sample;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View imageLayout = LayoutInflater.from(context).inflate(R.layout.adapter_upcoming_booking, parent, false);
        return new viewHolder(imageLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        //holder.rlBookingRemoteBlock.setVisibility(View.GONE);
        //holder.rlInOffice.setVisibility(View.VISIBLE);

        //conditon is to display date and line accordingly
        if (list.get(position).isDateStatus()){
            //holder.dateLayout.setVisibility(View.VISIBLE);
            System.out.println("DateFormatPrintHere"+list.get(position).getDate());
//            System.out.println("DayInTextAndNumber"+Utils.getDayAndDateFromDateFormat(list.get(position).getDate()));
            //holder.today_date.setText(""+Utils.getDayAndDateFromDateFormat(list.get(position).getDate()));
            //holder.lineLayout.setVisibility(View.GONE);
        }
        else {
            //holder.dateLayout.setVisibility(View.GONE);
            //holder.lineLayout.setVisibility(View.VISIBLE);
        }

        //condition displays today string instead od date and disable previous date
        /*if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate()) == 1){
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
            holder.bookingIvEdit.setVisibility(View.GONE);
            System.out.println("date check Balaaaa"+list.get(position).getDate()+" : "+Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate()));
        } else if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
            holder.today_date.setText("Today");
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.bookingIvEdit.setVisibility(View.VISIBLE);
        }else {
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.bookingIvEdit.setVisibility(View.VISIBLE);
        }*/

        if (list.get(position).getCalDeskStatus() ==1 &&
                list.get(position).getCalendarEntriesModel()
                        .getUsageTypeAbbreviation().equalsIgnoreCase("IO")){
            if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Booked");
            }

            //holder.rlBookingRemoteBlock.setVisibility(View.GONE);
            //holder.rlInOffice.setVisibility(View.VISIBLE);
            try{
                holder.bookingAddress.setText(new StringBuilder()
                        .append("")
                        .append(list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getfLoorName())
                        .append(" - ").append(list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getBuildingName()).toString()
                );
            }catch (Exception e){

            }

            //Desk Booking
            /*Glide.with(context)
                    .load(R.drawable.chair)
                    .placeholder(R.drawable.chair)
                    .into(holder.bookingImage);*/

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
                    Date dNow = new Date( ); // Instantiate a Date object
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dNow);
                    cal.add(Calendar.MINUTE, 5);
                    dNow = cal.getTime();
                    SimpleDateFormat f24hours=new SimpleDateFormat("HH:mm");

                    if (Utils.getCurrentDate()
                            .equalsIgnoreCase(Utils.getYearMonthDateFormat(list.get(position).getDate()))
                            &&
                            Utils.compareTimeIfCheckInEnable(
                                    f24hours.format(dNow),
                                    Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            )){
                        //holder.greenLine.setVisibility(View.GONE);
                        //holder.bookingBtnCheckIn.setVisibility(View.VISIBLE);
                        /*if(fragment.qrEnabled)
                            holder.bookingBtnCheckIn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.scan_2px, 0);
                        else
                            holder.bookingBtnCheckIn.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);*/
                    }
                    else
                        //holder.bookingBtnCheckIn.setVisibility(View.GONE);

                    //holder.bookingBtnCheckOut.setVisibility(View.GONE);
                    break;
                case 2:
                    //show green line if its falls between from and to time of today
                    if (Utils.getCurrentDate()
                            .equalsIgnoreCase(Utils.getYearMonthDateFormat(list.get(position).getDate())) &&
                            Utils.compareTimeIfCheckInEnable(
                                    Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()),
                                    Utils.getCurrentTime()
                            )){
                        /*holder.greenLine.setVisibility(View.VISIBLE);
                        holder.bookingBtnCheckOut.setVisibility(View.VISIBLE);*/
                    }
                    else{
                        /*holder.greenLine.setVisibility(View.GONE);
                        holder.bookingBtnCheckOut.setVisibility(View.GONE);*/
                    }

                    //holder.bookingBtnCheckIn.setVisibility(View.GONE);
//                    holder.bookingBtnCheckOut.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    /*holder.bookingBtnCheckIn.setVisibility(View.GONE);
                    holder.bookingBtnCheckOut.setVisibility(View.GONE);
                    holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));*/
                    break;
                default:
            }
        }
        else if (list.get(position).getCalDeskStatus() ==1 &&
                !list.get(position).getCalendarEntriesModel()
                        .getUsageTypeAbbreviation().equalsIgnoreCase("IO")){
            /*holder.rlBookingRemoteBlock.setVisibility(View.VISIBLE);
            holder.rlInOffice.setVisibility(View.GONE);
            holder.bookingBtnCheckIn.setVisibility(View.GONE);
            holder.bookingBtnCheckOut.setVisibility(View.GONE);*/

     /*       if (list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor()!=null)
            holder.bookingAddress.setText(new StringBuilder()
                    .append("")
                    .append(" "+list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getBuildingName())
                    .append(" - ").append(list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getfLoorName()).toString()
            );*/
            switch (list.get(position).getCalendarEntriesModel().getUsageTypeAbbreviation()){
                case "RQ":
                    holder.tvBookingWorkingRemote.setText("Request for Desk In Progress");
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("Request for Desk In Progress");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Request for Desk");
                    }
                    break;
                case "WFH":
                    holder.tvBookingWorkingRemote.setText("You're Working remotely");
                    holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()));
                    holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));

                    /*holder.tvSubBookingWorkingRemote.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            +" - "
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("You're Working remotely");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Working remotely");
                    }*/

                    break;
                case "WOO":
                    holder.tvBookingWorkingRemote.setText("You're Working in alternative office");

                    /*holder.tvSubBookingWorkingRemote.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            +" - "
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));*/

                    holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()));
                    holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));

                    /*list.get(position).getCalendarEntriesModel().setUsageTypeName("You're Working in alternative office");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Alternative Office");
                    }*/
                    break;
                case "TR":
                    holder.tvBookingWorkingRemote.setText("You're in training");
                    holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()));
                    holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));

                    /*holder.tvSubBookingWorkingRemote.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            +" - "
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));

                    list.get(position).getCalendarEntriesModel().setUsageTypeName("You're in training");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Training");
                    }*/
                    break;
                case "OO":
                    holder.tvBookingWorkingRemote.setText("Out of office");
                    holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom()));
                    holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));

                    /*holder.tvSubBookingWorkingRemote.setText(""
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getFrom())
                            +" - "
                            +Utils.splitTime(list.get(position).getCalendarEntriesModel().getMyto()));
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("Out of office");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Out of Office");
                    }*/
                    break;
                case "SL":
                    holder.tvBookingWorkingRemote.setText("You're on Sick Leave");
                    list.get(position).getCalendarEntriesModel().setUsageTypeName("You're on Sick Leave");
                    if (Utils.compareTwoDate(list.get(position).getDate(),Utils.getCurrentDate())==2){
                        SessionHandler.getInstance().save(context,AppConstants.USER_CURRENT_STATUS,"Sick Leave");
                    }
                    break;
            }
        }
        else if (list.get(position).getCalDeskStatus() == 2){
            //Meeting Room
            //holder.rlBookingRemoteBlock.setVisibility(View.GONE);
            //holder.bookingBtnCheckIn.setVisibility(View.GONE);
            //holder.bookingBtnCheckOut.setVisibility(View.GONE);

            holder.bookingAddress.setText(new StringBuilder()
                    .append("")
                    .append(list.get(position).getMeetingBookingsModel().getLocationBuildingFloor().getBuildingName())
                    .append(" - ").append(list.get(position).getMeetingBookingsModel().getLocationBuildingFloor().getfLoorName()).toString()
            );
            /*Glide.with(context)
                    .load(R.drawable.room)
                    .placeholder(R.drawable.room)
                    .into(holder.bookingImage);*/

            //show green line if its falls between from and to time of today
            /*if(Utils.getCurrentDate()
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
            }*/

            holder.bookingDeskName.setText(""+list.get(position).getMeetingBookingsModel().getMeetingRoomName());
            holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getMeetingBookingsModel().getFrom()));
            holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getMeetingBookingsModel().getMyto()));

        }
        else if (list.get(position).getCalDeskStatus() == 3){
            //Car Parking
            /*holder.rlBookingRemoteBlock.setVisibility(View.GONE);
            holder.bookingBtnCheckIn.setVisibility(View.GONE);
            holder.bookingBtnCheckOut.setVisibility(View.GONE);*/
            holder.bookingAddress.setText(new StringBuilder()
                    .append("")
                    .append(list.get(position).getCarParkBookingsModel().getLocationBuildingFloor().getBuildingName())
                    .append(" - ").append(list.get(position).getCarParkBookingsModel().getLocationBuildingFloor().getfLoorName()).toString()
            );
            //show green line if its falls between from and to time of today
            /*if(Utils.getCurrentDate()
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
            }*/

            /*Glide.with(context)
                    .load(R.drawable.car)
                    .placeholder(R.drawable.car)
                    .into(holder.bookingImage);*/

            holder.bookingDeskName.setText(""+list.get(position).getCarParkBookingsModel().getParkingSlotCode());
            holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getCarParkBookingsModel().getFrom()));
            holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getCarParkBookingsModel().getMyto()));
        }

    }

    @Override
    public int getItemCount() {
        if (list.size()>2)
            return 2;
        else
            return list.size();
        /*
            if (sample.equalsIgnoreCase("")){
                return list.size();
            }else {
                return 2;
            }
        */
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView bookingCheckInTime,bookingCheckOutTime,bookingDeskName,bookingAddress,tvBookingWorkingRemote;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            bookingCheckInTime = itemView.findViewById(R.id.bookingCheckInTime);
            bookingCheckOutTime = itemView.findViewById(R.id.bookingCheckOutTime);
            bookingDeskName = itemView.findViewById(R.id.bookingDeskName);
            bookingAddress = itemView.findViewById(R.id.bookingAddress);
            tvBookingWorkingRemote = itemView.findViewById(R.id.tvBookingWorkingRemote);

        }
    }

}
