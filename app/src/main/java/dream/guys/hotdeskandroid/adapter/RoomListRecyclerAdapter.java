package dream.guys.hotdeskandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.ParkingSpotModel;
import dream.guys.hotdeskandroid.model.response.UserAllowedMeetingResponse;

public class RoomListRecyclerAdapter extends RecyclerView.Adapter<RoomListRecyclerAdapter.ViewHolder> {
    // HomeFragment homeFragment;
    Context context;
    Activity activity;
    List<UserAllowedMeetingResponse> rooms;
    public OnSelectSelected onSelectSelected;
    BottomSheetDialog bottomSheetDialog;

    public RoomListRecyclerAdapter(Context context, OnSelectSelected onSelectSelected, FragmentActivity activity, List<UserAllowedMeetingResponse> bookingForEditResponse, Context context1, BottomSheetDialog bottomSheetDialog) {
        // this.homeFragment=homeFragment;
        this.context = context;
        this.onSelectSelected = onSelectSelected;
        this.activity = activity;
        this.rooms=bookingForEditResponse;
        this.bottomSheetDialog=bottomSheetDialog;
    }
    public interface OnSelectSelected{
        public void onSelectRoom(int deskId, String deskName, String location,
                                 List<UserAllowedMeetingResponse.Amenity> amenityList,int capacityCount);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_room_list_recycler_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.room_name.setText(""+rooms.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onSelectSelected.onSelectRoom(rooms.get(holder.getAbsoluteAdapterPosition()).getId(),
                        rooms.get(holder.getAbsoluteAdapterPosition()).getName(),
                        rooms.get(holder.getAbsoluteAdapterPosition()).getLocationMeeting().getName(),
                        rooms.get(holder.getAbsoluteAdapterPosition()).getAmenities(),
                        rooms.get(holder.getAbsoluteAdapterPosition()).getNoOfPeople()
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
