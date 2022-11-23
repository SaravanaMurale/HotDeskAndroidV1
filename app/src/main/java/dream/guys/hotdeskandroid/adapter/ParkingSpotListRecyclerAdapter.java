package dream.guys.hotdeskandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

public class ParkingSpotListRecyclerAdapter extends RecyclerView.Adapter<ParkingSpotListRecyclerAdapter.Viewholder> implements Filterable {
    // HomeFragment homeFragment;
    Context context;
    Activity activity;
    List<ParkingSpotModel> parkingSpots;
    List<ParkingSpotModel> parkingSpotsAll;
    public OnSelectSelected onSelectSelected;
    BottomSheetDialog bottomSheetDialog;


    public ParkingSpotListRecyclerAdapter(Context context, OnSelectSelected onSelectSelected, FragmentActivity activity, List<ParkingSpotModel> bookingForEditResponse, Context context1, BottomSheetDialog bottomSheetDialog) {
        // this.homeFragment=homeFragment;
        this.context = context;
        this.onSelectSelected = onSelectSelected;
        this.activity = activity;
        this.parkingSpots=bookingForEditResponse;
        this.parkingSpotsAll=new ArrayList<>(bookingForEditResponse);
        this.bottomSheetDialog=bottomSheetDialog;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_room_list_recycler_layout, parent, false);
        return new ParkingSpotListRecyclerAdapter.Viewholder(itemView);
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ParkingSpotModel> filteredList=new ArrayList<>();

            if(constraint==null || constraint.toString().isEmpty() || constraint.length()==0 || constraint==""){
                filteredList.addAll(parkingSpotsAll);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (ParkingSpotModel dskl:parkingSpotsAll){

                    if(dskl.getCode().toLowerCase().contains(filterPattern)){

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

            parkingSpots.clear();
            parkingSpots.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    @Override
    public Filter getFilter() {
        return filter;
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
