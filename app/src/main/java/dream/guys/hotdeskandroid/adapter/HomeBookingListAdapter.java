package dream.guys.hotdeskandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dream.guys.hotdeskandroid.R;

public class HomeBookingListAdapter extends RecyclerView.Adapter<HomeBookingListAdapter.HomeBookingListViewHolder> {


    @NonNull
    @Override
    public HomeBookingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_list_recycler_layout, parent, false);
        return new HomeBookingListViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull HomeBookingListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class HomeBookingListViewHolder extends RecyclerView.ViewHolder{

        public HomeBookingListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
