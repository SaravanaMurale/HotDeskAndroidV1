package dream.guys.hotdeskandroid.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.CovidQuestionsResponse;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.ui.wellbeing.NotificationsListActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;

public class AdapterNotificationList extends RecyclerView.Adapter<AdapterNotificationList.viewHolder> {

    Context context;

    ArrayList<IncomingRequestResponse.Result> notiList;
    String page;
    int cIncoming,cOutGoing;
    AccRejInterface accRejInterface;

    int cBoxPos = -1;
    int oldPos = -1;

    public AdapterNotificationList(Context context,ArrayList<IncomingRequestResponse.Result>
            notiList,String page,int cIncoming,int cOutGoing) {
        this.context = context;
        this.notiList = notiList;
        this.page = page;
        this.cIncoming = cIncoming;
        this.cOutGoing = cOutGoing;
    }
    public AdapterNotificationList(Context context,ArrayList<IncomingRequestResponse.Result>
            notiList,String page,int cIncoming,int cOutGoing,AccRejInterface accRejInterface) {
        this.context = context;
        this.notiList = notiList;
        this.page = page;
        this.cIncoming = cIncoming;
        this.cOutGoing = cOutGoing;
        this.accRejInterface = accRejInterface;
    }

    public ArrayList<IncomingRequestResponse.Result> getList(){

       ArrayList<IncomingRequestResponse.Result> manageList = new ArrayList<>();
       manageList=(ArrayList<IncomingRequestResponse.Result>)notiList.stream().filter(IncomingRequestResponse.Result::isCheckBoxStatus).collect(Collectors.toList());

        return manageList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_old_req_list, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        
        int pos = holder.getAbsoluteAdapterPosition();

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

        if (page.equalsIgnoreCase("Manage")){
            if (cBoxPos>=0 && cBoxPos == pos){
                notiList.get(cBoxPos).setCheckBoxStatus(true);
            }else {
                notiList.get(pos).setCheckBoxStatus(false);
            }
        }

        holder.card_view.setVisibility(View.VISIBLE);
        if (notiList.get(pos).getIncoming().equalsIgnoreCase("outgoing") &&
        notiList.get(pos).getStatus().equals(0)){
            holder.btn_layout.setVisibility(View.GONE);
            if (notiList.get(pos).isTitle()){
                holder.hdr_lay.setVisibility(View.VISIBLE);
            }else {
                holder.hdr_lay.setVisibility(View.GONE);
            }

            holder.txt_title.setText("Outgoing ");
            holder.txt_title_count.setText("(" + String.valueOf(cOutGoing) + ")");
        }else {
            if (notiList.get(pos).getIncoming().equalsIgnoreCase("incoming") &&
                    notiList.get(pos).getStatus().equals(0)){

                if (notiList.get(pos).isTitle()){
                    holder.hdr_lay.setVisibility(View.VISIBLE);
                }else {
                    holder.hdr_lay.setVisibility(View.GONE);
                }

                if (page.equalsIgnoreCase("Manage")){
                    holder.txt_title.setText("Pending ");
                }else {
                    holder.txt_title.setText("Incoming ");
                }
                holder.btn_layout.setVisibility(View.VISIBLE);
                holder.txt_title_count.setText("(" + String.valueOf(cIncoming) + ")");
            }else {
                //holder.txt_title.setVisibility(View.GONE);

                if (page.equalsIgnoreCase("Manage")) {
                    holder.card_view.setVisibility(View.GONE);
                }else {
                    if (notiList.get(pos).isTitle()){
                        holder.hdr_lay.setVisibility(View.VISIBLE);
                    }else {
                        holder.hdr_lay.setVisibility(View.GONE);
                    }

                    if (oldPos==-1){
                        oldPos = pos;
                    }

                    if (oldPos == pos){
                        holder.txt_title.setVisibility(View.VISIBLE);
                    }else {
                        holder.txt_title.setVisibility(View.GONE);
                    }

                    holder.hdr_lay.setVisibility(View.VISIBLE);
                    holder.txt_title.setText("Old");
                    holder.txt_title_count.setVisibility(View.GONE);
                    holder.btn_layout.setVisibility(View.GONE);
                    //holder.txt_title_count.setText("(" + String.valueOf(cIncoming) + ")");
                }

            }
        }

        holder.tvUserName.setText(notiList.get(position).getRequesterName());
        holder.tvUserTeam.setText(notiList.get(position).getRequesterTeam());
//        holder.txt_date.setText(Utils.dateWithDayString(Utils.splitDate(notiList.get(position).getRequestedDate())));
        holder.txt_date.setText(Utils.dateWithDayString(Utils.splitDate(notiList.get(position).getDate())));
        holder.CheckInTime.setText(Utils.splitTime(notiList.get(position).getFrom()));
        holder.CheckOutTime.setText(Utils.splitTime(notiList.get(position).getTo()));

        if (page.equalsIgnoreCase("Manage")){
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.btnRemove.setVisibility(View.GONE);
            holder.btn_layout.setVisibility(View.GONE);
        }else {
            holder.checkBox.setVisibility(View.GONE);
            holder.btnRemove.setVisibility(View.GONE);
            //holder.btn_layout.setVisibility(View.VISIBLE);
        }

        switch (notiList.get(position).getEntityType()){
            case 3:
                holder.imgEntity.setImageDrawable(context.getDrawable(R.drawable.chair));
                if (notiList.get(position).getDeskCode().equalsIgnoreCase("")||notiList.get(position).getDeskCode().isEmpty()){

                } else {
                    holder.tvDesk.setText(notiList.get(position).getDeskCode());
                }
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

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (notiList.get(pos).getEntityType()==3) {
                    int id = notiList.get(pos).getId();
                    int reqTeamId = notiList.get(pos).getRequestedTeamDeskId();
                    int entity = notiList.get(pos).getEntityType();

                    if (accRejInterface!=null){
                        accRejInterface.clickEvents(id,reqTeamId,entity, AppConstants.ACCEPT);
                    }
                } else if (notiList.get(pos).getEntityType()==5){
                    int id = notiList.get(pos).getId();
//                    int reqTeamId = notiList.get(pos).getRequestedTeamDeskId();
                    int entity = notiList.get(pos).getEntityType();

                    if (accRejInterface!=null){
                        accRejInterface.clickEvents(id,0,entity, AppConstants.ACCEPT);
                    }
                }else if (notiList.get(pos).getEntityType()==4){
                    int id = notiList.get(pos).getId();
//                    int reqTeamId = notiList.get(pos).getRequestedTeamDeskId();
                    int entity = notiList.get(pos).getEntityType();

                    if (accRejInterface!=null){
                        accRejInterface.clickEvents(id,0,entity, AppConstants.ACCEPT);
                    }
                }
            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (notiList.get(pos).getEntityType()==3){

                    int id = notiList.get(pos).getId();
                    int reqTeamId = notiList.get(pos).getRequestedTeamDeskId();
                    int entity = notiList.get(pos).getEntityType();

                    if (accRejInterface!=null){
                        accRejInterface.clickEvents(id,reqTeamId,entity, AppConstants.REJECT);
                    }

                }else if (notiList.get(pos).getEntityType()==5){
                    int id = notiList.get(pos).getId();
//                    int reqTeamId = notiList.get(pos).getRequestedTeamDeskId();
                    int entity = notiList.get(pos).getEntityType();

                    if (accRejInterface!=null){
                        accRejInterface.clickEvents(id,0,entity, AppConstants.REJECT);
                    }
                } else if (notiList.get(pos).getEntityType()==4){
                    int id = notiList.get(pos).getId();
//                    int reqTeamId = notiList.get(pos).getRequestedTeamDeskId();
                    int entity = notiList.get(pos).getEntityType();

                    if (accRejInterface!=null){
                        accRejInterface.clickEvents(id,0,entity, AppConstants.REJECT);
                    }
                }

            }
        });

        holder.checkBox.setChecked(notiList.get(pos).isCheckBoxStatus());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isChecked = holder.checkBox.isChecked();

                cBoxPos = pos;
                notiList.get(pos).setCheckBoxStatus(isChecked);
                notifyDataSetChanged();

            }
        });


        if (SessionHandler.getInstance().get(context,AppConstants.ROLE)!=null &&
                SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.Administrator)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.FacilityManager)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.TeamManager)
                ||SessionHandler.getInstance().get(context,AppConstants.ROLE).equalsIgnoreCase(AppConstants.MeetingManager)){

        }else {
            holder.profile_layout.setVisibility(View.GONE);
            holder.line_view.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return notiList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        //Entity type = 5 -> car parking , 4 -> meeting , 3 -> desk , 1-> people

        TextView tvUserName,tvUserTeam,txt_date,CheckInTime,CheckOutTime,tvDesk,
                tvAddress,txt_title,txt_title_count;
        AppCompatButton btnRemove,btnAccept,btnReject;
        CheckBox checkBox;
        RelativeLayout hdr_lay,rel_status,profile_layout;
        CardView card_view;
        LinearLayout btn_layout;
        ImageView imgEntity;
        View line_view;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            hdr_lay = itemView.findViewById(R.id.hdr_lay);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserTeam = itemView.findViewById(R.id.tvUserTeam);
            txt_date = itemView.findViewById(R.id.txt_date);
            CheckInTime = itemView.findViewById(R.id.CheckInTime);
            CheckOutTime = itemView.findViewById(R.id.CheckOutTime);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            checkBox = itemView.findViewById(R.id.checkBox);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_title_count = itemView.findViewById(R.id.txt_title_count);
            card_view = itemView.findViewById(R.id.card_view);
            btn_layout = itemView.findViewById(R.id.btn_layout);
            btnAccept = itemView.findViewById(R.id.btnSubmit);
            btnReject = itemView.findViewById(R.id.btnReject);
            rel_status = itemView.findViewById(R.id.rel_status);
            imgEntity = itemView.findViewById(R.id.ivMeeting);
            tvDesk = itemView.findViewById(R.id.tvDesk);
            profile_layout = itemView.findViewById(R.id.profile_layout);
            line_view = itemView.findViewById(R.id.line_view);

        }
    }

    public interface AccRejInterface{
        public void clickEvents(int id,int reqTeamID,int Entity,String Action);
    }

}
