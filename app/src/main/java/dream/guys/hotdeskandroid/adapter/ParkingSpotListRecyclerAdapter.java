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
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.ParkingSpotModel;

public class ParkingSpotListRecyclerAdapter extends RecyclerView.Adapter<ParkingSpotListRecyclerAdapter.Viewholder> {
    // HomeFragment homeFragment;
    Context context;
    Activity activity;
    List<ParkingSpotModel> parkingSpots;
    public OnSelectSelected onSelectSelected;
    BottomSheetDialog bottomSheetDialog;


    public ParkingSpotListRecyclerAdapter(Context context, OnSelectSelected onSelectSelected, FragmentActivity activity, List<ParkingSpotModel> bookingForEditResponse, Context context1, BottomSheetDialog bottomSheetDialog) {
        // this.homeFragment=homeFragment;
        this.context = context;
        this.onSelectSelected = onSelectSelected;
        this.activity = activity;
        this.parkingSpots=bookingForEditResponse;
        this.bottomSheetDialog=bottomSheetDialog;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_room_list_recycler_layout, parent, false);
        return new ParkingSpotListRecyclerAdapter.Viewholder(itemView);
    }
    public interface OnSelectSelected{
        public void onSelectParking(int deskId,String deskName, String location);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.parking_name.setText(""+parkingSpots.get(position).getCode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onSelectSelected.onSelectParking(parkingSpots.get(holder.getAbsoluteAdapterPosition()).getId(),
                        parkingSpots.get(holder.getAbsoluteAdapterPosition()).getCode(),parkingSpots.get(holder.getAbsoluteAdapterPosition()).getLocation().getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return parkingSpots.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_desk_name)
        TextView parking_name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
