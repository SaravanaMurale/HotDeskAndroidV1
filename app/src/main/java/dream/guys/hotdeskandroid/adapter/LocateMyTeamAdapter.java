package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.utils.Utils;

public class LocateMyTeamAdapter extends RecyclerView.Adapter<LocateMyTeamAdapter.LocateMyTeamViewHolder> {

    Context context;
    List<DAOTeamMember> daoTeamMemberList;
    ShowMyTeamLocationClickable showMyTeamLocationClickable;




    public interface ShowMyTeamLocationClickable{
        public void showMyTeamLocation(ArrayList<DAOTeamMember.DayGroup> dayGroups);
    }



    public LocateMyTeamAdapter(Context context, List<DAOTeamMember> stringName, ShowMyTeamLocationClickable showMyTeamLocationClickable) {
        this.context=context;
        this.daoTeamMemberList=stringName;
        this.showMyTeamLocationClickable=showMyTeamLocationClickable;


    }

    @NonNull
    @Override
    public LocateMyTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_locate_myteam_adaper, parent, false);
        return  new LocateMyTeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocateMyTeamViewHolder holder, int position) {

        holder.locateMyTeamName.setText(daoTeamMemberList.get(position).getFirstName()+" "+daoTeamMemberList.get(position).getLastName());


        if(daoTeamMemberList.get(position).getDayGroups().isEmpty() || daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().size()==0){
            holder.locateMyTeamDeskName.setText("No Booking Avaliable");
            holder.locateMyTeamCheckInTime.setVisibility(View.INVISIBLE);
            holder.locateMyTeamCheckOutTime.setVisibility(View.INVISIBLE);
            holder.locateMyTeamLocation.setVisibility(View.INVISIBLE);
            holder.locateMyTeamNameCheckInIcon.setVisibility(View.INVISIBLE);
            holder.locateMyTeamCheckOutIcon.setVisibility(View.INVISIBLE);
        }else if(!daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().isEmpty() &&daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().size()>0 ){


            for (int i = 0; i <daoTeamMemberList.get(position).getDayGroups().get(0).getCalendarEntries().size() ; i++) {

                    holder.locateMyTeamNameCheckInIcon.setVisibility(View.VISIBLE);
                    holder.locateMyTeamCheckOutIcon.setVisibility(View.VISIBLE);
                    holder.locateMyTeamCheckInTime.setVisibility(View.VISIBLE);
                    holder.locateMyTeamCheckOutTime.setVisibility(View.VISIBLE);

                    if(daoTeamMemberList.get(position).getDayGroups().get(0).getCalendarEntries().get(i).getBooking()!=null) {
                        holder.locateMyTeamDeskName.setText(daoTeamMemberList.get(position).getDayGroups().get(0).getCalendarEntries().get(i).getBooking().getDeskCode());
                        holder.locateMyTeamCheckInTime.setText(Utils.splitTime(daoTeamMemberList.get(position).getDayGroups().get(0).getCalendarEntries().get(i).getFrom()));
                        holder.locateMyTeamCheckOutTime.setText(Utils.splitTime(daoTeamMemberList.get(position).getDayGroups().get(0).getCalendarEntries().get(i).getMyto()));
                    }
                }

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showMyTeamLocationClickable.showMyTeamLocation(175,273,bottomSheetDialog,bottomSheetBehavior);
                showMyTeamLocationClickable.showMyTeamLocation(daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups());

            }
        });


    }

    @Override
    public int getItemCount() {
        return daoTeamMemberList.size();
    }

    class LocateMyTeamViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.locateMyTeamProfile)
        CircleImageView myTeamProfileImage;
        @BindView(R.id.locateMyTeamName)
        TextView locateMyTeamName;
        @BindView(R.id.locateMyTeamDeskName)
        TextView locateMyTeamDeskName;
        @BindView(R.id.locateMyTeamCheckInTime)
        TextView locateMyTeamCheckInTime;
        @BindView(R.id.locateMyTeamCheckOutTime)
        TextView locateMyTeamCheckOutTime;
        @BindView(R.id.locateMyTeamLocation)
        ImageView locateMyTeamLocation;
        @BindView(R.id.locateMyTeamNameCheckInIcon)
        ImageView locateMyTeamNameCheckInIcon;
        @BindView(R.id.locateMyTeamCheckOutIcon)
        ImageView locateMyTeamCheckOutIcon;

        public LocateMyTeamViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
