package dream.guys.hotdeskandroid.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;

public class AdapterNotificationList extends RecyclerView.Adapter<AdapterNotificationList.viewHolder> {

    Context context;
    ArrayList<IncomingRequestResponse.Result> notiList;

    public AdapterNotificationList(Context context,ArrayList<IncomingRequestResponse.Result> notiList) {
        this.context = context;
        this.notiList = notiList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_old_req_list, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.tvUserName.setText(notiList.get(position).getRequesterName());
        holder.tvUserTeam.setText(notiList.get(position).getRequesterTeam());
        holder.txt_date.setText(notiList.get(position).getRequestedDate());
        holder.CheckInTime.setText(notiList.get(position).getFromUtc());
        holder.CheckOutTime.setText(notiList.get(position).getToUtc());

    }

    @Override
    public int getItemCount() {
        return notiList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        //Entity type = 5 -> car parking , 4 -> meeting , 3 -> desk , 1-> people

        TextView tvUserName,tvUserTeam,txt_date,CheckInTime,CheckOutTime;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserTeam = itemView.findViewById(R.id.tvUserTeam);
            txt_date = itemView.findViewById(R.id.txt_date);
            CheckInTime = itemView.findViewById(R.id.CheckInTime);
            CheckOutTime = itemView.findViewById(R.id.CheckOutTime);

        }
    }

}
