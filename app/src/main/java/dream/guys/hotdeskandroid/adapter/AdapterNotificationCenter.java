package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.ui.notify.NotificationRequestActivity;
import dream.guys.hotdeskandroid.ui.wellbeing.NotificationsListActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.Utils;

public class AdapterNotificationCenter extends RecyclerView.Adapter<AdapterNotificationCenter.viewholder> {

    Context context;
    ArrayList<IncomingRequestResponse.Result> notiList;
    int count = 0;

    ArrayList<IncomingRequestResponse.Result> incoming;
    ArrayList<IncomingRequestResponse.Result> outgoing;

    public AdapterNotificationCenter(Context context, ArrayList<IncomingRequestResponse.Result>
            notiList,int count,ArrayList<IncomingRequestResponse.Result> incoming, ArrayList<IncomingRequestResponse.Result> outgoing) {
        this.context = context;
        this.notiList = notiList;
        this.count = count;

        this.incoming = incoming;
        this.outgoing = outgoing;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_notify_center, parent, false);
        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.tvUserName.setText(notiList.get(position).getRequesterName());
        holder.tvUserTeam.setText(notiList.get(position).getRequesterTeam());
        holder.txt_date.setText(Utils.dateWithDayString(Utils.splitDate(notiList.get(position).getRequestedDate())));
        holder.CheckInTime.setText(Utils.splitTime(notiList.get(position).getFrom()));
        holder.CheckOutTime.setText(Utils.splitTime(notiList.get(position).getTo()));

        switch (notiList.get(position).getStatus()){
            case 0:
                holder.rel_status.setBackgroundColor(context.getResources().getColor(R.color.figma_byrequest, context.getTheme()));
                break;
            case 1:
                holder.rel_status.setBackgroundColor(context.getResources().getColor(R.color.figmaLiteGreen, context.getTheme()));
                break;
            case 2:
                holder.rel_status.setBackgroundColor(context.getResources().getColor(R.color.figma_unavaliable, context.getTheme()));
                break;
            case 3:
                holder.rel_status.setBackgroundColor(context.getResources().getColor(R.color.line_gray, context.getTheme()));
                break;
            /*case 4:
                holder.rel_status.setBackgroundColor(context.getResources().getColor(R.color.figma_byrequest, context.getTheme()));
                break;
            case 5:
                holder.rel_status.setBackgroundColor(context.getResources().getColor(R.color.figma_byrequest, context.getTheme()));
                break;*/
        }

        if (notiList.get(position).getStatus() == 0) {

            holder.txt_count.setText("+" + String.valueOf(count) + " more");
            holder.date_time_lay.setVisibility(View.GONE);
            holder.pending_count_lay.setVisibility(View.VISIBLE);

            if (position<1){
                holder.cardBookingNotify.setVisibility(View.VISIBLE);
            }else {
                holder.cardBookingNotify.setVisibility(View.GONE);
            }

        }else {
            holder.cardBookingNotify.setVisibility(View.VISIBLE);
            holder.date_time_lay.setVisibility(View.VISIBLE);
            holder.pending_count_lay.setVisibility(View.GONE);
        }

        //holder.req_lay
        holder.req_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<IncomingRequestResponse.Result> notyManageList = new ArrayList<>();
                int cIncoming = 0,cOutGoing = 0;

                if (outgoing!=null && outgoing.size()>0){
                    outgoing.get(0).setTitle(true);
                    notyManageList.addAll(outgoing);

                    IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);
                    cOutGoing = Collections.frequency(outgoing, result); //outgoing.size();
                }

                if (incoming!=null && incoming.size()>0){
                    incoming.get(0).setTitle(true);
                    notyManageList.addAll(incoming);

                    IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);

                    cIncoming = Collections.frequency(incoming, result); //incoming.size();
                }

                Intent intent = new Intent(context, NotificationsListActivity.class);
                intent.putExtra(AppConstants.SHOWNOTIFICATION,notyManageList);
                intent.putExtra("IncomingList",incoming);
                intent.putExtra("OutGoingList",outgoing);
                intent.putExtra(AppConstants.OUTGOING,cOutGoing);
                intent.putExtra(AppConstants.INCOMING,cIncoming);
                context.startActivity(intent);
            }
        });

        switch (notiList.get(position).getEntityType()){
            case 3:
                holder.imgEntity.setImageDrawable(context.getDrawable(R.drawable.chair));
                holder.tvDesk.setText(notiList.get(position).getDeskTeam());
                holder.tvAddress.setText(notiList.get(position).getDeskTeam());
                break;
            case 4:
                holder.imgEntity.setImageDrawable(context.getDrawable(R.drawable.room));
                holder.tvDesk.setText(notiList.get(position).getMeetingRoomName());
                holder.tvAddress.setText(notiList.get(position).getMeetingRoomName());
                break;
            case 5:
                holder.imgEntity.setImageDrawable(context.getDrawable(R.drawable.car));
                holder.tvDesk.setText(String.valueOf(notiList.get(position).getParkingSlotCode()));
                holder.tvAddress.setText(notiList.get(position).getLocationName());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return notiList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView txt_date,CheckInTime,CheckOutTime,tvUserName,tvUserTeam,txt_count,tvDesk,tvAddress;
        CardView cardBookingNotify,cardCovidNotify;
        RelativeLayout pending_count_lay,date_time_lay,req_lay,rel_status;
        ImageView imgEntity;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            txt_date = itemView.findViewById(R.id.txt_date);
            CheckInTime = itemView.findViewById(R.id.CheckInTime);
            CheckOutTime = itemView.findViewById(R.id.CheckOutTime);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserTeam = itemView.findViewById(R.id.tvUserTeam);
            txt_count = itemView.findViewById(R.id.txt_count);
            cardBookingNotify = itemView.findViewById(R.id.card_booking_notify);
            cardCovidNotify = itemView.findViewById(R.id.card_covid_notify);
            pending_count_lay = itemView.findViewById(R.id.pending_count_lay);
            date_time_lay = itemView.findViewById(R.id.date_time_lay);
            req_lay = itemView.findViewById(R.id.req_lay);
            rel_status = itemView.findViewById(R.id.rel_status);
            tvDesk = itemView.findViewById(R.id.tvDesk);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            imgEntity = itemView.findViewById(R.id.ivMeeting);

        }
    }

}
