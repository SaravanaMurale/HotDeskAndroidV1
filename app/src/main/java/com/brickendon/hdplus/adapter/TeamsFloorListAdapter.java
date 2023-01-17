package com.brickendon.hdplus.adapter;

import static com.brickendon.hdplus.utils.MyApp.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.FloorListModel;
import com.brickendon.hdplus.ui.teams.TeamsFragment;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.SessionHandler;

public class TeamsFloorListAdapter extends RecyclerView.Adapter<TeamsFloorListAdapter.viewHolder>{
    Context context;
    ArrayList<FloorListModel> floorListModels;
    TeamsContactsAdapter.OnProfileClickable onProfileClickable;
    FloorListener floorListener;
    TeamsFragment fragment;

    public TeamsFloorListAdapter(Context context, ArrayList<FloorListModel> teamMembersList, FloorListener floorListener, TeamsContactsAdapter.OnProfileClickable OnProfileClickable, Fragment fragment) {
        this.context = context;
        this.floorListModels = teamMembersList;
        this.onProfileClickable = OnProfileClickable;
        this.floorListener = floorListener;
        this.fragment=(TeamsFragment) fragment;
    }
    public interface FloorListener{
        void floorListenerClick(int floorId, int deskId, String desk);
        void updateAdapterList(TeamsContactsAdapter teamsContactsAdapter);
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View floorImageLayout = LayoutInflater.from(context).inflate(R.layout.floor_layout_recycler, parent, false);
        return new TeamsFloorListAdapter.viewHolder(floorImageLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.floorName.setText(floorListModels.get(position).getFloorName());
        holder.tvAvailableCount.setText(floorListModels.get(position).getDeskAvailability());

        if (fragment.expandStatus) {
            holder.recyclerViewFloor.setLayoutManager(new LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false));
            holder.tvAvailableCount.setVisibility(View.GONE);
        }
        else {
            holder.recyclerViewFloor.setLayoutManager(new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false));
            holder.tvAvailableCount.setVisibility(View.VISIBLE);
        }
        holder.recyclerViewFloor.addItemDecoration(new TeamsFragment.OverlapDecoration());
        holder.recyclerViewFloor.setHasFixedSize(true);


        TeamsContactsAdapter floorAdapter = new TeamsContactsAdapter(context,
                floorListModels.get(position).getDaoTeamMembers(),
                floorListModels.get(position).getDaoTeamMembersNew(),
                onProfileClickable,fragment);
        holder.recyclerViewFloor.setAdapter(floorAdapter);
        floorListener.updateAdapterList(floorAdapter);

        holder.floorLocationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int pos=0;
                    loop:
                    for(int i=0; i < floorListModels.get(holder.getAbsoluteAdapterPosition()).getDaoTeamMembers().get(0).getDayGroups().get(0).getCalendarEntries().size(); i++){
                        if (floorListModels.get(holder.getAbsoluteAdapterPosition()).getDaoTeamMembers()
                                .get(0).getDayGroups()
                                .get(0).getCalendarEntries().get(i).getBooking().getLocationBuildingFloor() !=null){
                            pos = i;
                            break loop;
                        }

                    }

                    String floorName=floorListModels.get(holder.getAbsoluteAdapterPosition()).getDaoTeamMembers()
                            .get(0).getDayGroups()
                            .get(0).getCalendarEntries().get(pos).getBooking().getLocationBuildingFloor().getBuildingName()+","
                            +floorListModels.get(holder.getAbsoluteAdapterPosition()).getDaoTeamMembers()
                            .get(0).getDayGroups()
                            .get(0).getCalendarEntries().get(pos).getBooking().getLocationBuildingFloor().getfLoorName();


                    SessionHandler.getInstance().save(getContext(),AppConstants.FULLPATHLOCATION,floorName);



                    floorListener.floorListenerClick(
                            floorListModels.get(holder.getAbsoluteAdapterPosition()).getFloorId(),
                            floorListModels.get(holder.getAbsoluteAdapterPosition()).getDaoTeamMembers()
                                    .get(0).getDayGroups()
                                    .get(0).getCalendarEntries().get(0).getBooking().getDeskId(),
                            AppConstants.DESK
                    );

                } catch (Exception e){

                }


                //floorListModels.get(position).getDaoTeamMembers().get(position).getDayGroups().get(position).get

            }
        });

    }

    @Override
    public int getItemCount() {
        return floorListModels.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView floorName,tvAvailableCount;
        ImageView floorLocationImage;
        RecyclerView recyclerViewFloor;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            floorName= itemView.findViewById(R.id.tvFloorName);
            tvAvailableCount= itemView.findViewById(R.id.tvAvailableCount);
            floorLocationImage= itemView.findViewById(R.id.ivLocation);
            recyclerViewFloor = itemView.findViewById(R.id.recyclerView);
        }
    }
}
