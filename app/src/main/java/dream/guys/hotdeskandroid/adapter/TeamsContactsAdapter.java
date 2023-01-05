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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.TeamsMemberListDataModel;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.ui.teams.TeamsFragment;
import dream.guys.hotdeskandroid.utils.Utils;

public class TeamsContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    Context context;
    ArrayList<DAOTeamMember> teamMembersList;
    ArrayList<TeamsMemberListDataModel> teamMembersListNew;
    ArrayList<TeamsMemberListDataModel> teamMembersListAll;
    ArrayList<TeamsMemberListDataModel> teamMembersListAllNew;
    OnProfileClickable onProfileClickable;
    TeamsFragment fragment;
    HashMap<Integer, Boolean> fireWarden;
    HashMap<Integer, Boolean> firstAid;

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<TeamsMemberListDataModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.toString().isEmpty() || constraint.length() == 0 || constraint == "") {
                filteredList.addAll(teamMembersListAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (TeamsMemberListDataModel dskl : teamMembersListAll) {

                    if (dskl.getFirstName().toLowerCase().contains(filterPattern)
                            || dskl.getFirstName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(dskl);

                    }

                }

            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            teamMembersListNew.clear();
            teamMembersListNew.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    public interface OnProfileClickable {
        public void onProfileClick(DAOTeamMember daoTeamMember);

        void clickEvent(DAOTeamMember daoTeamMember);
    }

    public TeamsContactsAdapter(Context context, ArrayList<DAOTeamMember> teamMembersList,ArrayList<TeamsMemberListDataModel> teamMembersListNew, OnProfileClickable onProfileClickable, Fragment fragment) {
        this.context = context;
        this.teamMembersList = teamMembersList;
        this.teamMembersListNew = teamMembersListNew;
        this.teamMembersListAll = new ArrayList<>(teamMembersListNew);
        this.onProfileClickable = onProfileClickable;
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
                viewHolder holder = (viewHolder) viewholder;
                if (position > 4) {
                    if (position == 5) {
                        holder.relative.setVisibility(View.VISIBLE);
                        holder.tvCount.setText(" + " + String.valueOf(teamMembersListNew.size() - 6));
                    } else {
                        holder.profile_image.setVisibility(View.GONE);
                        holder.relative.setVisibility(View.GONE);
                    }

                } else {
                    holder.profile_image.setVisibility(View.VISIBLE);
                    holder.relative.setVisibility(View.GONE);
                }

                if (teamMembersListNew.get(position).getProfileImage() != null
                        && !teamMembersListNew.get(position).getProfileImage().equalsIgnoreCase("")) {
                    String cleanImage = teamMembersListNew.get(position).getProfileImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "");
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
//                Toast.makeText(context, "sds"+teamMembersListNew.get(holder.getAbsoluteAdapterPosition()).getDayGroups().size(), Toast.LENGTH_SHORT).show();
                        onProfileClickable.onProfileClick(teamMembersListNew.get(holder.getAbsoluteAdapterPosition()).getDaoTeamMember());
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
                viewHolderList holderList = (viewHolderList) viewholder;

                String fName = teamMembersListNew.get(position).getFirstName();
                String lName = teamMembersListNew.get(position).getLastName();
                if (fragment.firewardenList != null) {
                    if (fragment.firewardenList.containsKey(teamMembersListNew.get(position).getUserId())) {
                        holderList.fireWardenIcon.setVisibility(View.VISIBLE);
                    } else {
                        holderList.fireWardenIcon.setVisibility(View.GONE);
                    }
                } else {
                    holderList.fireWardenIcon.setVisibility(View.GONE);
                }

                if (fragment.firstAidList != null) {
                    if (fragment.firstAidList.containsKey(teamMembersListNew.get(position).getUserId())) {
                        holderList.firstAidWardenIcon.setVisibility(View.VISIBLE);
                    } else {
                        holderList.firstAidWardenIcon.setVisibility(View.GONE);
                    }

                } else {
                    holderList.firstAidWardenIcon.setVisibility(View.GONE);
                }

                holderList.mTxtName.setText(fName + " " + lName);
                if (teamMembersListNew.get(position).getCalendarEntriesModel()!=null
                        && teamMembersListNew.get(position).getCalendarEntriesModel().getBooking() != null) {

                    /*holderList.mbookingCheckInTime.setVisibility(View.VISIBLE);
                    holderList.mbookingCheckOutTime.setVisibility(View.VISIBLE);
                    holderList.ivCheckIn.setVisibility(View.VISIBLE);
                    holderList.ivCheckOut.setVisibility(View.VISIBLE);*/
                    if (teamMembersListNew.get(position).getCalendarEntriesModel().getBooking().getLocationBuildingFloor() != null) {
                        holderList.mbookingAddress.setVisibility(View.VISIBLE);

                        holderList.mbookingAddress.setText("" + teamMembersListNew.get(position).getCalendarEntriesModel()
                                .getBooking().getLocationBuildingFloor().getBuildingName() + "-"
                                + teamMembersListNew.get(position).getCalendarEntriesModel()
                                .getBooking().getLocationBuildingFloor().getfLoorName());
                    }

                   /* holderList.mbookingCheckInTime.setText(Utils.splitTime(teamMembersListNew.get(position).getDayGroups().get(0)
                            .getCalendarEntries().get(teamMembersListNew.get(position).getDayGroups().get(0)
                                    .getCalendarEntries().size()-1).getFrom()));

                    holderList.mbookingCheckOutTime.setText(Utils.splitTime(teamMembersListNew.get(position).getDayGroups().get(0)
                            .getCalendarEntries().get(teamMembersListNew.get(position).getDayGroups().get(0)
                                    .getCalendarEntries().size()-1).getMyto()));*/
                } else {
                    holderList.mbookingAddress.setText("");
                    holderList.mbookingAddress.setVisibility(View.GONE);
                   /* holderList.timeLayout.setVisibility(View.GONE);
                    holderList.mbookingCheckInTime.setVisibility(View.GONE);
                    holderList.mbookingCheckOutTime.setVisibility(View.GONE);
                    holderList.ivCheckIn.setVisibility(View.GONE);
                    holderList.ivCheckOut.setVisibility(View.GONE);*/
                }

                if (teamMembersListNew.get(position).getCalendarEntriesModel()!=null) {
                    holderList.timeLayout.setVisibility(View.VISIBLE);
                    holderList.mbookingCheckInTime.setVisibility(View.VISIBLE);
                    holderList.mbookingCheckOutTime.setVisibility(View.VISIBLE);
                    holderList.ivCheckIn.setVisibility(View.VISIBLE);
                    holderList.ivCheckOut.setVisibility(View.VISIBLE);

                    try{

                        if (teamMembersListNew.get(position).getCalendarEntriesModel().getFrom() != null)
                            holderList.mbookingCheckInTime.setText(Utils.splitTime(teamMembersListNew.get(position).getCalendarEntriesModel().getFrom()));

                        if (teamMembersListNew.get(position).getCalendarEntriesModel() != null)
                            holderList.mbookingCheckOutTime.setText(Utils.splitTime(teamMembersListNew.get(position).getCalendarEntriesModel().getMyto()));
                    } catch (Exception e){

                    }

//                    holderList.mbookingCheckOutTime.setText(Utils.splitTime(teamMembersListNew.get(position).getDayGroups().get(0)
//                            .getCalendarEntries().get(teamMembersListNew.get(position).getDayGroups().get(0)
//                                    .getCalendarEntries().size() - 1).getMyto()));
                } else {
                    holderList.timeLayout.setVisibility(View.GONE);
                    holderList.mbookingCheckInTime.setVisibility(View.GONE);
                    holderList.mbookingCheckOutTime.setVisibility(View.GONE);
                    holderList.ivCheckIn.setVisibility(View.GONE);
                    holderList.ivCheckOut.setVisibility(View.GONE);
                }

                System.out.println("bala check teams out");
                if (teamMembersListNew.get(position).getProfileImage() != null
                        && !teamMembersListNew.get(position).getProfileImage().equalsIgnoreCase("")) {
                    String cleanImage = teamMembersListNew.get(position).getProfileImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "");
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
                        onProfileClickable.clickEvent(teamMembersListNew.get(holderList.getAbsoluteAdapterPosition()).getDaoTeamMember());
                    }
                });

                break;
        }
        if (!fragment.expandStatus) {

        } else {

        }

    }

    @Override
    public int getItemCount() {
        return teamMembersListNew.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

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

    public class viewHolderList extends RecyclerView.ViewHolder {

        TextView mTxtName, mbookingAddress, mbookingCheckOutTime, mbookingCheckInTime;
        ImageView ivCheckOut, ivCheckIn, fireWardenIcon, firstAidWardenIcon;
        CircleImageView teamMemberImage;
        LinearLayout timeLayout;

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
            timeLayout = itemView.findViewById(R.id.timeLayout);

        }
    }


}
