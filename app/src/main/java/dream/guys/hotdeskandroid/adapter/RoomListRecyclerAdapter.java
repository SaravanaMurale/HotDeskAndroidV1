package dream.guys.hotdeskandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.ParkingSpotModel;
import dream.guys.hotdeskandroid.model.response.UserAllowedMeetingResponse;

public class RoomListRecyclerAdapter extends RecyclerView.Adapter<RoomListRecyclerAdapter.ViewHolder> implements Filterable {
    // HomeFragment homeFragment;
    Context context;
    Activity activity;
    List<UserAllowedMeetingResponse> rooms;
    List<UserAllowedMeetingResponse> roomsAll;
    public OnSelectSelected onSelectSelected;
    BottomSheetDialog bottomSheetDialog;

    List<BookingForEditResponse.TeamDeskAvailabilities> deskListAll;
    public RoomListRecyclerAdapter(Context context, OnSelectSelected onSelectSelected, FragmentActivity activity, List<UserAllowedMeetingResponse> bookingForEditResponse, Context context1, BottomSheetDialog bottomSheetDialog) {
        // this.homeFragment=homeFragment;
        this.context = context;
        this.onSelectSelected = onSelectSelected;
        this.activity = activity;
        this.rooms=bookingForEditResponse;
        this.roomsAll = new ArrayList<>(bookingForEditResponse);
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
        @BindView(R.id.desk_status)
        TextView deskStatus;
        @BindView(R.id.desk_icon_status)
        ImageView deskIconStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
