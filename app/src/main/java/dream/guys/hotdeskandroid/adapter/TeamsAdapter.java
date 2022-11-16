package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.viewHolder> {

    Context context;
    ArrayList<DAOTeamMember> teamMembersList;
    TeamMemberInterface teamMemberInterface;

    public TeamsAdapter(Context context, ArrayList<DAOTeamMember> teamMembersList,TeamMemberInterface teamMemberInterface) {
        this.context = context;
        this.teamMembersList = teamMembersList;
        this.teamMemberInterface = teamMemberInterface;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View imageLayout = LayoutInflater.from(context).inflate(R.layout.adapter_teams, parent, false);
        return new viewHolder(imageLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        String fName = teamMembersList.get(position).getFirstName();
        String lName = teamMembersList.get(position).getLastName();

        holder.mTxtName.setText(fName + " " + lName);
        if (teamMembersList.get(position).getDayGroups().size()>0
                && teamMembersList.get(position).getDayGroups().get(0).getCalendarEntries().size()>0
                && teamMembersList.get(position).getDayGroups().get(0)
                .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                        .getCalendarEntries().size()-1).getBooking()!=null
                && teamMembersList.get(position).getDayGroups().get(0)
                .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                        .getCalendarEntries().size()-1).getBooking().getLocationBuildingFloor()!=null
        ){

            holder.mbookingCheckInTime.setVisibility(View.VISIBLE);
            holder.mbookingCheckOutTime.setVisibility(View.VISIBLE);
            holder.ivCheckIn.setVisibility(View.VISIBLE);
            holder.ivCheckOut.setVisibility(View.VISIBLE);

            holder.mbookingAddress.setText(""+teamMembersList.get(position).getDayGroups().get(0)
                    .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                            .getCalendarEntries().size()-1).getBooking().getLocationBuildingFloor().getfLoorName()+"-"
                    +teamMembersList.get(position).getDayGroups().get(0)
                    .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                            .getCalendarEntries().size()-1).getBooking().getLocationBuildingFloor().getBuildingName());

            holder.mbookingCheckInTime.setText(Utils.splitTime(teamMembersList.get(position).getDayGroups().get(0)
                    .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                            .getCalendarEntries().size()-1).getMyto()));

            holder.mbookingCheckOutTime.setText(Utils.splitTime(teamMembersList.get(position).getDayGroups().get(0)
                    .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                            .getCalendarEntries().size()-1).getFrom()));
        } else {
            holder.mbookingAddress.setText("");
            holder.mbookingCheckInTime.setVisibility(View.GONE);
            holder.mbookingCheckOutTime.setVisibility(View.GONE);
            holder.ivCheckIn.setVisibility(View.GONE);
            holder.ivCheckOut.setVisibility(View.GONE);
        }

        System.out.println("bala check teams out");
        if (teamMembersList.get(position).getProfileImage()!=null
                && !teamMembersList.get(position).getProfileImage().equalsIgnoreCase("")){
            String cleanImage = teamMembersList.get(position).getProfileImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,","");
            byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.teamMemberImage.setImageBitmap(decodedByte);
        } else {
            Glide.with(context).load(R.drawable.avatar)
                    .into(holder.teamMemberImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamMemberInterface.clickEvent(teamMembersList.get(holder.getAbsoluteAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return teamMembersList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView mTxtName, mbookingAddress,mbookingCheckOutTime,mbookingCheckInTime;
        ImageView ivCheckOut,ivCheckIn;
        CircleImageView teamMemberImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            ivCheckOut = itemView.findViewById(R.id.bookingCheckOutIcon);
            ivCheckIn = itemView.findViewById(R.id.bookingCheckInIcon);
            mTxtName = itemView.findViewById(R.id.bookingDeskName);
            mbookingCheckInTime = itemView.findViewById(R.id.bookingCheckInTime);
            mbookingCheckOutTime = itemView.findViewById(R.id.bookingCheckOutTime);
            mTxtName = itemView.findViewById(R.id.bookingDeskName);
            mbookingAddress = itemView.findViewById(R.id.bookingAddress);
            teamMemberImage = itemView.findViewById(R.id.bookingIvIcon);

        }
    }

    public interface TeamMemberInterface {

        void clickEvent(DAOTeamMember daoTeamMember);
    }

}
