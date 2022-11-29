package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.ui.home.EditProfileActivity;
import dream.guys.hotdeskandroid.ui.teams.TeamsFragment;
import dream.guys.hotdeskandroid.utils.Utils;

public class TeamsContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    Context context;
    ArrayList<DAOTeamMember> teamMembersList;
    ArrayList<DAOTeamMember> teamMembersListAll;
    OnProfileClickable  onProfileClickable;
    TeamsFragment fragment;
    HashMap<Integer, Boolean> fireWarden;
    HashMap<Integer, Boolean> firstAid;
    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<DAOTeamMember> filteredList=new ArrayList<>();

            if(constraint==null || constraint.toString().isEmpty() || constraint.length()==0 || constraint==""){
                filteredList.addAll(teamMembersListAll);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (DAOTeamMember dskl:teamMembersListAll){

                    if(dskl.getFirstName().toLowerCase().contains(filterPattern)
                            || dskl.getFirstName().toLowerCase().contains(filterPattern)){
                        filteredList.add(dskl);

                    }

                }

            }

            FilterResults results=new FilterResults();
            results.values=filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            teamMembersList.clear();
            teamMembersList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public interface OnProfileClickable{
        public void onProfileClick(DAOTeamMember daoTeamMember);
        void clickEvent(DAOTeamMember daoTeamMember);
    }

    public TeamsContactsAdapter(Context context, ArrayList<DAOTeamMember> teamMembersList, OnProfileClickable  onProfileClickable, Fragment fragment) {
        this.context = context;
        this.teamMembersList = teamMembersList;
        this.teamMembersListAll = new ArrayList<>(teamMembersList);
        this.onProfileClickable=onProfileClickable;
        this.fragment = (TeamsFragment) fragment;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View imageLayout;
        switch (viewType) {
            case 0:
                imageLayout = LayoutInflater.from(context).inflate(R.layout.adapter_team_contacts, parent, false);
                return new viewHolder(imageLayout);
            case 1:
                imageLayout = LayoutInflater.from(context).inflate(R.layout.adapter_teams, parent, false);
                return new viewHolderList(imageLayout);

        }
        return null;
    }


    @Override
    public int getItemViewType(int position) {
        if (!fragment.expandStatus)
            return 0;
        else
            return 1;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewholder, int position) {
        switch (viewholder.getItemViewType()) {
            case 0:
                viewHolder holder  = (viewHolder) viewholder;
                if (position > 4) {
                    if (position == 5){
                        holder.relative.setVisibility(View.VISIBLE);
                        holder.tvCount.setText(" + \n" + String.valueOf(teamMembersList.size()-6));
                    }else {
                        holder.profile_image.setVisibility(View.GONE);
                        holder.relative.setVisibility(View.GONE);
                    }

                } else {
                    holder.profile_image.setVisibility(View.VISIBLE);
                    holder.relative.setVisibility(View.GONE);
                }

                if (teamMembersList.get(position).getProfileImage()!=null
                        && !teamMembersList.get(position).getProfileImage().equalsIgnoreCase("")){
                    String cleanImage = teamMembersList.get(position).getProfileImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,","");
                    byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    holder.profile_image.setImageBitmap(decodedByte);
                } else {
                    Glide.with(context).load(R.drawable.avatar)
                            .into(holder.profile_image);
                }

                holder.profile_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                Toast.makeText(context, "sds"+teamMembersList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().size(), Toast.LENGTH_SHORT).show();
                        onProfileClickable.onProfileClick(teamMembersList.get(holder.getAbsoluteAdapterPosition()));
                    }
                });
                holder.tvCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onProfileClickable.onProfileClick(null);
                    }
                });

                break;
            case 1:
                viewHolderList holderList  = (viewHolderList) viewholder;

                String fName = teamMembersList.get(position).getFirstName();
                String lName = teamMembersList.get(position).getLastName();
                if (fragment.firewardenList!=null){
                    if (fragment.firewardenList.containsKey(teamMembersList.get(position).getUserId())){
                        holderList.fireWardenIcon.setVisibility(View.VISIBLE);
                    } else {
                        holderList.fireWardenIcon.setVisibility(View.GONE);
                    }
                } else {
                    holderList.fireWardenIcon.setVisibility(View.GONE);
                }

                if (fragment.firstAidList!=null){
                    if (fragment.firstAidList.containsKey(teamMembersList.get(position).getUserId())){
                        holderList.firstAidWardenIcon.setVisibility(View.VISIBLE);
                    } else {
                        holderList.firstAidWardenIcon.setVisibility(View.GONE);
                    }

                } else {
                    holderList.firstAidWardenIcon.setVisibility(View.GONE);
                }

                holderList.mTxtName.setText(fName + " " + lName);
                if (teamMembersList.get(position).getDayGroups().size()>0
                        && teamMembersList.get(position).getDayGroups().get(0).getCalendarEntries().size()>0
                        && teamMembersList.get(position).getDayGroups().get(0)
                        .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                                .getCalendarEntries().size()-1).getBooking()!=null
                ){

                    holderList.mbookingCheckInTime.setVisibility(View.VISIBLE);
                    holderList.mbookingCheckOutTime.setVisibility(View.VISIBLE);
                    holderList.ivCheckIn.setVisibility(View.VISIBLE);
                    holderList.ivCheckOut.setVisibility(View.VISIBLE);
                    if (teamMembersList.get(position).getDayGroups().get(0)
                            .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                                    .getCalendarEntries().size()-1).getBooking().getLocationBuildingFloor()!=null) {
                        holderList.mbookingAddress.setText(""+teamMembersList.get(position).getDayGroups().get(0)
                                .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                                        .getCalendarEntries().size()-1).getBooking().getLocationBuildingFloor().getBuildingName()+"-"
                                +teamMembersList.get(position).getDayGroups().get(0)
                                .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                                        .getCalendarEntries().size()-1).getBooking().getLocationBuildingFloor().getfLoorName());
                    }

                    holderList.mbookingCheckInTime.setText(Utils.splitTime(teamMembersList.get(position).getDayGroups().get(0)
                            .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                                    .getCalendarEntries().size()-1).getFrom()));

                    holderList.mbookingCheckOutTime.setText(Utils.splitTime(teamMembersList.get(position).getDayGroups().get(0)
                            .getCalendarEntries().get(teamMembersList.get(position).getDayGroups().get(0)
                                    .getCalendarEntries().size()-1).getMyto()));
                } else {
                    holderList.mbookingAddress.setText("");
                    holderList.mbookingCheckInTime.setVisibility(View.GONE);
                    holderList.mbookingCheckOutTime.setVisibility(View.GONE);
                    holderList.ivCheckIn.setVisibility(View.GONE);
                    holderList.ivCheckOut.setVisibility(View.GONE);
                }

                System.out.println("bala check teams out");
                if (teamMembersList.get(position).getProfileImage()!=null
                        && !teamMembersList.get(position).getProfileImage().equalsIgnoreCase("")){
                    String cleanImage = teamMembersList.get(position).getProfileImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,","");
                    byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    holderList.teamMemberImage.setImageBitmap(decodedByte);
                } else {
                    Glide.with(context).load(R.drawable.avatar)
                            .into(holderList.teamMemberImage);
                }
                holderList.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onProfileClickable.clickEvent(teamMembersList.get(holderList.getAbsoluteAdapterPosition()));
                    }
                });

                break;
        }
        if (!fragment.expandStatus){

        } else {

        }

    }

    @Override
    public int getItemCount() {
        return teamMembersList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tvCount;
        RelativeLayout relative;
        ImageView profile_image;
        CircleImageView circleImageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tvCount = itemView.findViewById(R.id.tvCount);
            relative = itemView.findViewById(R.id.relative);
            profile_image = itemView.findViewById(R.id.profile_image);


        }
    }
    public class viewHolderList extends RecyclerView.ViewHolder{

        TextView mTxtName, mbookingAddress,mbookingCheckOutTime,mbookingCheckInTime;
        ImageView ivCheckOut,ivCheckIn, fireWardenIcon,firstAidWardenIcon;
        CircleImageView teamMemberImage;

        public viewHolderList(@NonNull View itemView) {
            super(itemView);

            ivCheckOut = itemView.findViewById(R.id.bookingCheckOutIcon);
            ivCheckIn = itemView.findViewById(R.id.bookingCheckInIcon);
            mTxtName = itemView.findViewById(R.id.bookingDeskName);
            mbookingCheckInTime = itemView.findViewById(R.id.bookingCheckInTime);
            mbookingCheckOutTime = itemView.findViewById(R.id.bookingCheckOutTime);
            mTxtName = itemView.findViewById(R.id.bookingDeskName);
            mbookingAddress = itemView.findViewById(R.id.bookingAddress);
            teamMemberImage = itemView.findViewById(R.id.bookingIvIcon);
            fireWardenIcon = itemView.findViewById(R.id.fireWardenIcon);
            firstAidWardenIcon = itemView.findViewById(R.id.firstAidIcon);

        }
    }


}
