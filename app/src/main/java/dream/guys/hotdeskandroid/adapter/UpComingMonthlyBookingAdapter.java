package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.DAOUpcomingBooking;
import dream.guys.hotdeskandroid.model.response.DAOUpcomingBookingNew;
import dream.guys.hotdeskandroid.model.response.TeamMembersResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;

public class UpComingMonthlyBookingAdapter extends RecyclerView.Adapter<UpComingMonthlyBookingAdapter.viewHolder> {

    Context context;
//    ArrayList<DAOUpcomingBooking.PersonDayViewEntry.CalendarEntry> list;
    ArrayList<DAOUpcomingBookingNew> list;
    String sample = "";

    public UpComingMonthlyBookingAdapter(Context context, ArrayList<DAOUpcomingBookingNew> recyclerModelArrayList, String sample) {
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

        try{
            if (list.get(position).getDeskLocation()!=null
                    && Utils.checkStringParms(list.get(position).getDeskLocation().getFloorID())>0){
                holder.bookingAddress.setText(new StringBuilder()
                        .append("")
                        .append(Utils.checkStringParms(list.get(position).getDeskLocation().getfLoorName()))
                        .append(" - ").append(list.get(position).getDeskLocation().getBuildingName()).toString()
                );

                holder.bookingIvLocation.setVisibility(View.VISIBLE);
            } else if (list.get(position).getRequestedDeskLocation()!=null
                    && Utils.checkStringParms(list.get(position).getRequestedDeskLocation().getFloorID())>0){
                holder.bookingAddress.setText(new StringBuilder()
                        .append("")
                        .append(Utils.checkStringParms(list.get(position).getRequestedDeskLocation().getfLoorName()))
                        .append(" - ").append(list.get(position).getRequestedDeskLocation().getBuildingName()).toString()
                );

                holder.bookingIvLocation.setVisibility(View.VISIBLE);
            } else {
                holder.bookingAddress.setText("");
                holder.bookingIvLocation.setVisibility(View.GONE);
            }

        }catch (Exception e){

        }

        if (list.get(position).getRequestedDeskCode() != null){
            holder.bookingDeskName.setText(Utils.checkStringParms(list.get(position).getRequestedDeskCode()));
        } else {
            holder.bookingDeskName.setText(Utils.checkStringParms(list.get(position).getDeskCode()));
        }

        holder.bookingCheckInTime.setText(Utils.splitTime(list.get(position).getFrom()));
        holder.bookingCheckOutTime.setText(Utils.splitTime(list.get(position).getTo()));
        holder.tvBookingWorkingRemote.setText(Utils.MonthAndDateString(Utils.splitDate(list.get(position).getDate())));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView bookingCheckInTime,bookingCheckOutTime,bookingDeskName,bookingAddress,tvBookingWorkingRemote;
        ImageView bookingIvLocation;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            bookingCheckInTime = itemView.findViewById(R.id.bookingCheckInTime);
            bookingCheckOutTime = itemView.findViewById(R.id.bookingCheckOutTime);
            bookingDeskName = itemView.findViewById(R.id.bookingDeskName);
            bookingAddress = itemView.findViewById(R.id.bookingAddress);
            tvBookingWorkingRemote = itemView.findViewById(R.id.tvBookingWorkingRemote);
            bookingIvLocation = itemView.findViewById(R.id.bookingIvLocation);

        }
    }

}
