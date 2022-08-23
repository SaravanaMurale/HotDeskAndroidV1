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

        //holder.tv_address.setText(notiList.get(position).getP);

    }

    @Override
    public int getItemCount() {
        return notiList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tv_desk_room_name,tv_address;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tv_desk_room_name = itemView.findViewById(R.id.tv_desk_room_name);
            tv_address = itemView.findViewById(R.id.tv_address);

        }
    }

}
