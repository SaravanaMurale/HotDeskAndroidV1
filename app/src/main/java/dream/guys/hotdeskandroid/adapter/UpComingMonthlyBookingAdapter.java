package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.DAOUpcomingBooking;
import dream.guys.hotdeskandroid.model.response.TeamMembersResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;

public class UpComingMonthlyBookingAdapter extends RecyclerView.Adapter<UpComingMonthlyBookingAdapter.viewHolder> {

    Context context;
    ArrayList<DAOUpcomingBooking.PersonDayViewEntry.CalendarEntry> list;
    String sample = "";

    public UpComingMonthlyBookingAdapter(Context context, ArrayList<DAOUpcomingBooking.PersonDayViewEntry.CalendarEntry> recyclerModelArrayList, String sample) {
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

        /*try{
            holder.bookingAddress.setText(new StringBuilder()
                    .append("")
                    .append(list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getfLoorName())
                    .append(" - ").append(list.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getBuildingName()).toString()
            );
        }catch (Exception e){

        }*/

        if (list.get(position).getBooking()!=null){
            holder.bookingDeskName.setText(list.get(position).getBooking().getDeskCode());
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
