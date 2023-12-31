package com.brickendon.hdplus.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.response.BookingForEditResponse;
import com.brickendon.hdplus.model.response.UserAllowedMeetingResponse;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;

public class RoomListRecyclerAdapter extends RecyclerView.Adapter<RoomListRecyclerAdapter.ViewHolder> implements Filterable {
    // HomeFragment homeFragment;
    Context context;
    Activity activity;
    List<UserAllowedMeetingResponse> rooms;
    List<UserAllowedMeetingResponse> getAllRooms;
    List<UserAllowedMeetingResponse> roomsAll;
    public OnSelectSelected onSelectSelected;
    BottomSheetDialog bottomSheetDialog;
    String roomName;
    List<BookingForEditResponse.TeamDeskAvailabilities> deskListAll;
    public RoomListRecyclerAdapter(Context context, OnSelectSelected onSelectSelected, FragmentActivity activity, List<UserAllowedMeetingResponse> bookingForEditResponse, Context context1,
                                   BottomSheetDialog bottomSheetDialog,List<UserAllowedMeetingResponse> allMeetingRoomList, String roomName) {
        // this.homeFragment=homeFragment;
        this.context = context;
        this.onSelectSelected = onSelectSelected;
        this.activity = activity;
        this.rooms=bookingForEditResponse;
        this.roomsAll = new ArrayList<>(bookingForEditResponse);
        this.bottomSheetDialog=bottomSheetDialog;
        this.getAllRooms =allMeetingRoomList;
        this.roomName =roomName;
    }
    public interface OnSelectSelected{
        public void onSelectRoom(int deskId, String deskName, String location,
                                 List<UserAllowedMeetingResponse.Amenity> amenityList,int capacityCount,String availabilityString);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_room_list_recycler_layout, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<UserAllowedMeetingResponse> filteredList=new ArrayList<>();

            if(constraint==null || constraint.toString().isEmpty() || constraint.length()==0 || constraint==""){
                filteredList.addAll(roomsAll);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (UserAllowedMeetingResponse dskl:roomsAll){

                    if(dskl.getName().toLowerCase().contains(filterPattern)){

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

            rooms.clear();
            rooms.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.room_name.setText(""+rooms.get(position).getName());
        holder.capacityLayout.setVisibility(View.VISIBLE);
        holder.locationDetails.setVisibility(View.VISIBLE);
        holder.statusLayout.setVisibility(View.VISIBLE);

        if (roomName.equalsIgnoreCase(rooms.get(position).getName())){
            holder.select.setText("Selected");
            holder.select.setTextColor(ContextCompat.getColor(context,R.color.figmaBlack));
        } else {
            holder.select.setText("Select");
            holder.select.setTextColor(ContextCompat.getColor(context,R.color.figmaBlueText));
        }

        if (rooms.get(position).getDescription() != null
                && !rooms.get(position).getDescription().equalsIgnoreCase("")
                && !rooms.get(position).getDescription().isEmpty()) {
            holder.tv_description.setVisibility(View.VISIBLE);
            holder.tv_description_text.setVisibility(View.VISIBLE);
            holder.tv_description.setText(rooms.get(position).getDescription());
        } else {
            holder.tv_description.setVisibility(View.GONE);
            holder.tv_description_text.setVisibility(View.GONE);
        }

                team:
                for (int x=0; x<rooms.get(position).getTeams().size(); x++) {
                    if(rooms.get(position).getTeams().get(x).getId() ==
                            SessionHandler.getInstance().getInt(context,AppConstants.TEAM_ID)) {
                            holder.capacityNo.setText(""+Utils.checkStringParms(rooms.get(position).getNoOfPeople()));
                            if (rooms.get(position).getLocationMeeting()!=null)
                                holder.locationDetails.setText(Utils.checkStringParms(rooms.get(position).getLocationMeeting().getLocationDetails().getBuildingName())
                                +", "+Utils.checkStringParms(rooms.get(position).getLocationMeeting().getLocationDetails().getFloorName()));
                            else
                                holder.locationDetails.setVisibility(View.GONE);

                            holder.card.setBackgroundColor(ContextCompat.getColor(activity,R.color.white));
                            holder.select.setVisibility(View.VISIBLE);

                            holder.deskStatus.setText("Available");
                            holder.deskIconStatus.setColorFilter(context.getColor(R.color.figmaLiteGreen));
                            break team;

                    } else {
                        if (!rooms.get(position).isActive()
                                || rooms.get(position).getAutomaticApprovalStatus() == 3){
                            holder.card.setBackgroundColor(ContextCompat.getColor(activity,R.color.white));
                            holder.select.setVisibility(View.GONE);
                            holder.capacityNo.setText(""+rooms.get(position).getNoOfPeople());
                            if (rooms.get(position).getLocationMeeting()!=null)
                                holder.locationDetails.setText(Utils.checkStringParms(rooms.get(position).getLocationMeeting().getLocationDetails().getBuildingName())
                                        +", "+Utils.checkStringParms(rooms.get(position).getLocationMeeting().getLocationDetails().getFloorName()));
                            else
                                holder.locationDetails.setVisibility(View.GONE);

                            holder.deskStatus.setText("Not Available For Request");
                            holder.deskIconStatus.setColorFilter(context.getColor(R.color.figma_red));
                            break team;
                        } else if (rooms.get(position).getAutomaticApprovalStatus() == 2){
                            holder.capacityNo.setText(""+Utils.checkStringParms(rooms.get(position).getNoOfPeople()));
                            if (rooms.get(position).getLocationMeeting()!=null)
                                holder.locationDetails.setText(Utils.checkStringParms(rooms.get(position).getLocationMeeting().getLocationDetails().getBuildingName())
                                        +", "+Utils.checkStringParms(rooms.get(position).getLocationMeeting().getLocationDetails().getFloorName()));
                            else
                                holder.locationDetails.setVisibility(View.GONE);

                            holder.card.setBackgroundColor(ContextCompat.getColor(activity,R.color.white));
                            holder.select.setVisibility(View.VISIBLE);

                            holder.deskStatus.setText("Available");
                            holder.deskIconStatus.setColorFilter(context.getColor(R.color.figmaLiteGreen));
                            break team;
                        } else {
                            holder.capacityNo.setText(""+Utils.checkStringParms(rooms.get(position).getNoOfPeople()));
                            if (rooms.get(position).getLocationMeeting()!=null)
                                holder.locationDetails.setText(Utils.checkStringParms(rooms.get(position).getLocationMeeting().getLocationDetails().getBuildingName())
                                        +", "+Utils.checkStringParms(rooms.get(position).getLocationMeeting().getLocationDetails().getFloorName()));
                            else
                                holder.locationDetails.setVisibility(View.GONE);

                            holder.card.setBackgroundColor(ContextCompat.getColor(activity,R.color.white));
                            holder.select.setVisibility(View.VISIBLE);

                            holder.deskStatus.setText("Available For Request");
                            holder.deskIconStatus.setColorFilter(context.getColor(R.color.figma_orange));
                        }
                    }
                }
                manager:
                for (int x=0; x<rooms.get(position).getManagers().size(); x++) {
                    if(rooms.get(position).getManagers().get(x).getId() ==
                            SessionHandler.getInstance().getInt(context,AppConstants.USER_ID)) {
                            holder.capacityNo.setText(""+Utils.checkStringParms(rooms.get(position).getNoOfPeople()));
                            if (rooms.get(position).getLocationMeeting()!=null)
                                holder.locationDetails.setText(Utils.checkStringParms(rooms.get(position).getLocationMeeting().getLocationDetails().getBuildingName())
                                +", "+Utils.checkStringParms(rooms.get(position).getLocationMeeting().getLocationDetails().getFloorName()));
                            else
                                holder.locationDetails.setVisibility(View.GONE);

                            holder.card.setBackgroundColor(ContextCompat.getColor(activity,R.color.white));
                            holder.select.setVisibility(View.VISIBLE);

                            holder.deskStatus.setText("Available");
                            holder.deskIconStatus.setColorFilter(context.getColor(R.color.figmaLiteGreen));
                            break manager;

                    }
                }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onSelectSelected.onSelectRoom(rooms.get(holder.getAbsoluteAdapterPosition()).getId(),
                        rooms.get(holder.getAbsoluteAdapterPosition()).getName(),
                        rooms.get(holder.getAbsoluteAdapterPosition()).getLocationMeeting().getName(),
                        rooms.get(holder.getAbsoluteAdapterPosition()).getAmenities(),
                        rooms.get(holder.getAbsoluteAdapterPosition()).getNoOfPeople(),
                        holder.deskStatus.getText().toString()
                        );
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_desk_name)
        TextView room_name;
        @BindView(R.id.tv_description)
        TextView tv_description;
        @BindView(R.id.description_text)
        TextView tv_description_text;
        @BindView(R.id.desk_status)
        TextView deskStatus;
        @BindView(R.id.tv_select)
        TextView select;
        @BindView(R.id.tv_location_details)
        TextView locationDetails;
        @BindView(R.id.tv_capacity_no)
        TextView capacityNo;
        @BindView(R.id.capacity_layout)
        LinearLayout capacityLayout;
        @BindView(R.id.status_layout)
        LinearLayout statusLayout;
        @BindView(R.id.desk_icon_status)
        ImageView deskIconStatus;
        @BindView(R.id.card)
        LinearLayout card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
