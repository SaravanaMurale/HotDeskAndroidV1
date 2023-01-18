package com.brickendon.hdplus.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.datasource.ExpandHeader;
import com.brickendon.hdplus.datasource.ExpandItem;
import com.brickendon.hdplus.datasource.TeamsExpandListModel;
import com.brickendon.hdplus.model.TeamsMemberListDataModel;
import com.brickendon.hdplus.model.response.DAOTeamMember;
import com.brickendon.hdplus.ui.teams.TeamsFragment;
import com.brickendon.hdplus.utils.Utils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamsExpandedNewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = -1;
    Context context;
    ArrayList<DAOTeamMember> teamMembersList;
    ArrayList<TeamsMemberListDataModel> teamMembersListNew;
    ArrayList<TeamsMemberListDataModel> teamMembersListAll;
    ArrayList<TeamsMemberListDataModel> teamMembersListAllNew;
    OnProfileClickable onProfileClickable;
    TeamsFragment fragment;
    HashMap<Integer, Boolean> fireWarden;
    HashMap<Integer, Boolean> firstAid;
    int size=50;

    ArrayList<TeamsExpandListModel> list;

    public TeamsExpandedNewAdapter(Context context, ArrayList<TeamsExpandListModel> teamMembersListNew,
                                   OnProfileClickable onProfileClickable, Fragment fragment) {

        this.context = context;
        this.list = teamMembersListNew;
//        this.teamMembersListAll = new ArrayList<>(teamMembersListNew);
        this.onProfileClickable = onProfileClickable;
        this.fragment = (TeamsFragment) fragment;
        if (teamMembersListNew.size()>50)
            this.size=50;
        else
            this.size=teamMembersListNew.size();
    }

    public interface OnProfileClickable {
        public void onExpandedProfileClick(DAOTeamMember daoTeamMember);

        void clickEvent(DAOTeamMember daoTeamMember);
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType==TYPE_HEADER){
            View v = inflater.inflate(R.layout.teams_expand_head,parent,false);
            return new VHHeader(v);
        } else {
            View v = inflater.inflate(R.layout.adapter_teams,parent,false);
            return new VHItem(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHHeader){
            final VHHeader vhHeader = (VHHeader) holder;
            final ExpandHeader expandHeader = (ExpandHeader) list.get(position);
            vhHeader.headerName.setText(""+expandHeader.getFloorNamae());

        } else if (holder instanceof  VHItem){
            final VHItem vhItemHolder = (VHItem) holder;
            final ExpandItem expandItem = (ExpandItem) list.get(position);
            TeamsMemberListDataModel listDataModel =expandItem.getTeamsMemberListDataModel();

            Log.d("TeamsCont", "onBindViewHolder: "+position);
            String fName = listDataModel.getFirstName();
            String lName = listDataModel.getLastName();
            if (fragment.firewardenList != null) {
                if (fragment.firewardenList.containsKey(listDataModel.getUserId())) {
                    vhItemHolder.fireWardenIcon.setVisibility(View.VISIBLE);
                } else {
                    vhItemHolder.fireWardenIcon.setVisibility(View.GONE);
                }
            } else {
                vhItemHolder.fireWardenIcon.setVisibility(View.GONE);
            }

            if (fragment.firstAidList != null) {
                if (fragment.firstAidList.containsKey(listDataModel.getUserId())) {
                    vhItemHolder.firstAidWardenIcon.setVisibility(View.VISIBLE);
                } else {
                    vhItemHolder.firstAidWardenIcon.setVisibility(View.GONE);
                }

            } else {
                vhItemHolder.firstAidWardenIcon.setVisibility(View.GONE);
            }

            vhItemHolder.mTxtName.setText(fName + " " + lName);
            if (listDataModel.getCalendarEntriesModel()!=null
                    && listDataModel.getCalendarEntriesModel().getBooking() != null) {

                    /*vhItemHolder.mbookingCheckInTime.setVisibility(View.VISIBLE);
                    vhItemHolder.mbookingCheckOutTime.setVisibility(View.VISIBLE);
                    vhItemHolder.ivCheckIn.setVisibility(View.VISIBLE);
                    vhItemHolder.ivCheckOut.setVisibility(View.VISIBLE);*/
                if (listDataModel.getCalendarEntriesModel().getBooking().getLocationBuildingFloor() != null) {
                    vhItemHolder.mbookingAddress.setVisibility(View.VISIBLE);

                    vhItemHolder.mbookingAddress.setText("" + listDataModel.getCalendarEntriesModel()
                            .getBooking().getLocationBuildingFloor().getBuildingName() + "-"
                            + listDataModel.getCalendarEntriesModel()
                            .getBooking().getLocationBuildingFloor().getfLoorName());
                }

                   /* vhItemHolder.mbookingCheckInTime.setText(Utils.splitTime(listDataModel.getDayGroups().get(0)
                            .getCalendarEntries().get(listDataModel.getDayGroups().get(0)
                                    .getCalendarEntries().size()-1).getFrom()));

                    vhItemHolder.mbookingCheckOutTime.setText(Utils.splitTime(listDataModel.getDayGroups().get(0)
                            .getCalendarEntries().get(listDataModel.getDayGroups().get(0)
                                    .getCalendarEntries().size()-1).getMyto()));*/
            } else {
                vhItemHolder.mbookingAddress.setText("");
                vhItemHolder.mbookingAddress.setVisibility(View.GONE);
                    /*
                        vhItemHolder.timeLayout.setVisibility(View.GONE);
                        vhItemHolder.mbookingCheckInTime.setVisibility(View.GONE);
                        vhItemHolder.mbookingCheckOutTime.setVisibility(View.GONE);
                        vhItemHolder.ivCheckIn.setVisibility(View.GONE);
                        vhItemHolder.ivCheckOut.setVisibility(View.GONE);
                    */
            }

            if (listDataModel.getCalendarEntriesModel()!=null) {
                vhItemHolder.timeLayout.setVisibility(View.VISIBLE);
                vhItemHolder.mbookingCheckInTime.setVisibility(View.VISIBLE);
                vhItemHolder.mbookingCheckOutTime.setVisibility(View.VISIBLE);
                vhItemHolder.ivCheckIn.setVisibility(View.VISIBLE);
                vhItemHolder.ivCheckOut.setVisibility(View.VISIBLE);

                try{

                    if (listDataModel.getCalendarEntriesModel().getFrom() != null)
                        vhItemHolder.mbookingCheckInTime.setText(Utils.splitTime(listDataModel.getCalendarEntriesModel().getFrom()));

                    if (listDataModel.getCalendarEntriesModel() != null)
                        vhItemHolder.mbookingCheckOutTime.setText(Utils.splitTime(listDataModel.getCalendarEntriesModel().getMyto()));
                } catch (Exception e){

                }

//                    vhItemHolder.mbookingCheckOutTime.setText(Utils.splitTime(listDataModel.getDayGroups().get(0)
//                            .getCalendarEntries().get(listDataModel.getDayGroups().get(0)
//                                    .getCalendarEntries().size() - 1).getMyto()));
            } else {
                vhItemHolder.timeLayout.setVisibility(View.GONE);
                vhItemHolder.mbookingCheckInTime.setVisibility(View.GONE);
                vhItemHolder.mbookingCheckOutTime.setVisibility(View.GONE);
                vhItemHolder.ivCheckIn.setVisibility(View.GONE);
                vhItemHolder.ivCheckOut.setVisibility(View.GONE);
            }

            try {
                if (listDataModel.getProfileImage() != null
                        && !listDataModel.getProfileImage().equalsIgnoreCase("")) {
                    String cleanImage = listDataModel.getProfileImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,", "");
                    byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    vhItemHolder.teamMemberImage.setImageBitmap(decodedByte);
                } else {
                    Glide.with(context).load(R.drawable.avatar)
                            .placeholder(R.drawable.avatar)
                            .into(vhItemHolder.teamMemberImage);
                }
            }catch (Exception e){

            }
            vhItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onProfileClickable.clickEvent(teamMembersListNew.get(holder.getAbsoluteAdapterPosition()).getDaoTeamMember());
                    } catch (Exception e){

                    }
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return position;
    }

    private boolean isPositionHeader(int position){
        return list.get(position) instanceof ExpandHeader;
    }

    class VHHeader extends RecyclerView.ViewHolder{
        TextView headerName;
        public VHHeader(@NonNull View itemView) {
            super(itemView);
            headerName = itemView.findViewById(R.id.tvHeaderName);
        }
    }
    class VHItem extends RecyclerView.ViewHolder{
        TextView mTxtName, mbookingAddress, mbookingCheckOutTime, mbookingCheckInTime;
        ImageView ivCheckOut, ivCheckIn, fireWardenIcon, firstAidWardenIcon;
        CircleImageView teamMemberImage;
        LinearLayout timeLayout;

        public VHItem(@NonNull View itemView) {
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
