package com.brickendon.hdplus.adapter;

import android.content.Context;
import android.content.Intent;
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

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.response.IncomingRequestResponse;
import com.brickendon.hdplus.ui.wellbeing.NotificationsListActivity;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.Utils;

public class AdapterUserNotify extends RecyclerView.Adapter<AdapterUserNotify.viewHolder> {

    Context context;
    ArrayList<IncomingRequestResponse.Result> notiList;
    int count = 0;

    public AdapterUserNotify(Context context, ArrayList<IncomingRequestResponse.Result> notificationList, int count) {
        this.context = context;
        this.count = count;
        notiList = notificationList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_notify, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.tvUserName.setText(notiList.get(position).getRequesterName());
        holder.tvUserTeam.setText(notiList.get(position).getRequesterTeam());
//        holder.txt_date.setText(Utils.dateWithDayString(Utils.splitDate(notiList.get(position).getRequestedDate())));
        holder.txt_date.setText(Utils.dateWithDayString(Utils.splitDate(notiList.get(position).getDate())));
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
            holder.txt_status.setVisibility(View.GONE);

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

        switch (notiList.get(position).getStatus()){
            case 0:
                holder.txt_status.setText("Pending");
                holder.txt_status.setTextColor(context.getResources().getColor(R.color.figmaLiteGreen, context.getTheme()));
                break;
            case 1:
                holder.txt_status.setText("Approved");
                holder.txt_status.setTextColor(context.getResources().getColor(R.color.figmaLiteGreen, context.getTheme()));
                break;
            case 2:
                holder.txt_status.setText("Rejected");
                holder.txt_status.setTextColor(context.getResources().getColor(R.color.figma_red, context.getTheme()));
                break;
            case 3:
                holder.txt_status.setText("Expired");
                holder.txt_status.setTextColor(context.getResources().getColor(R.color.line_gray, context.getTheme()));
                break;

        }

        //holder.req_lay
        holder.req_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<IncomingRequestResponse.Result> notyManageList = new ArrayList<>();
                int cIncoming = 0;

                IncomingRequestResponse.Result result = new IncomingRequestResponse.Result(0);

                cIncoming = Collections.frequency(notiList, result); //incoming.size();

                Intent intent = new Intent(context, NotificationsListActivity.class);
                intent.putExtra(AppConstants.SHOWNOTIFICATION,notiList);
                intent.putExtra(AppConstants.INCOMING,cIncoming);
                context.startActivity(intent);
            }
        });

        switch (notiList.get(position).getEntityType()){
            case 3:
                holder.imgEntity.setImageDrawable(context.getDrawable(R.drawable.chair));
                holder.tvDesk.setText(notiList.get(position).getDeskCode());
                holder.tvAddress.setText(Utils.checkStringParms(notiList.get(position).getDeskLocation()));
                break;
            case 4:
                holder.imgEntity.setImageDrawable(context.getDrawable(R.drawable.room));
                holder.tvDesk.setText(notiList.get(position).getMeetingRoomName());
                holder.tvAddress.setText(notiList.get(position).getMeetingRoomName());
                break;
            case 5:
                holder.imgEntity.setImageDrawable(context.getDrawable(R.drawable.car));
                holder.tvDesk.setText(String.valueOf(notiList.get(position).getParkingSlotCode()));
                holder.tvAddress.setText(Utils.checkStringParms(notiList.get(position).getLocationName()));
                break;
        }

        if (position == 1 && notiList.get(0).getStatus() == 0) {
            holder.hdr_lay.setVisibility(View.VISIBLE);
        }else if(notiList.get(0).getStatus() != 0 && position==0 ) {
            holder.hdr_lay.setVisibility(View.VISIBLE);
        }else {
            holder.hdr_lay.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return notiList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView txt_date,CheckInTime,CheckOutTime,tvUserName,tvUserTeam,txt_count,tvDesk,tvAddress,txt_status;
        CardView cardBookingNotify,cardCovidNotify;
        RelativeLayout pending_count_lay,date_time_lay,req_lay,rel_status;
        ImageView imgEntity;
        RelativeLayout hdr_lay;

        public viewHolder(@NonNull View itemView) {
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
            txt_status = itemView.findViewById(R.id.txt_status);
            hdr_lay = itemView.findViewById(R.id.hdr_lay);

        }
    }

}
