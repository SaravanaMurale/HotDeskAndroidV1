package dream.guys.hotdeskandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.FloorListModel;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.ui.teams.TeamsFragment;
import dream.guys.hotdeskandroid.utils.AppConstants;

public class TeamsFloorListAdapter extends RecyclerView.Adapter<TeamsFloorListAdapter.viewHolder>{
    Context context;
    ArrayList<FloorListModel> floorListModels;
    TeamsContactsAdapter.OnProfileClickable onProfileClickable;
    FloorListener floorListener;

    public TeamsFloorListAdapter(Context context, ArrayList<FloorListModel> teamMembersList, FloorListener floorListener, TeamsContactsAdapter.OnProfileClickable OnProfileClickable) {
        this.context = context;
        this.floorListModels = teamMembersList;
        this.onProfileClickable = OnProfileClickable;
        this.floorListener = floorListener;
    }
    public interface FloorListener{
        void floorListenerClick(int floorId, int deskId, String desk);
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
        System.out.println("test bala"+floorListModels.get(position).getDeskAvailability());
        holder.tvAvailableCount.setText(floorListModels.get(position).getDeskAvailability());

        LinearLayoutManager linearLayout = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL,false);
        holder.recyclerViewFloor.addItemDecoration(new TeamsFragment.OverlapDecoration());
        holder.recyclerViewFloor.setHasFixedSize(true);

        holder.recyclerViewFloor.setLayoutManager(linearLayout);

        TeamsContactsAdapter floorAdapter = new TeamsContactsAdapter(context,
                floorListModels.get(position).getDaoTeamMembers(),
                onProfileClickable);
        holder.recyclerViewFloor.setAdapter(floorAdapter);

        holder.floorLocationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i < floorListModels.get(holder.getAbsoluteAdapterPosition()).getDaoTeamMembers().size(); i++){

                }
                floorListener.floorListenerClick(
                        floorListModels.get(holder.getAbsoluteAdapterPosition()).getFloorId(),
                        floorListModels.get(holder.getAbsoluteAdapterPosition()).getDaoTeamMembers()
                                .get(0).getDayGroups()
                                .get(0).getCalendarEntries().get(0).getBooking().getDeskId(),
                        AppConstants.DESK
                        );
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
