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

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.ui.home.HomeFragment;
import dream.guys.hotdeskandroid.utils.AppConstants;

public class DeskListRecyclerAdapter extends RecyclerView.Adapter<DeskListRecyclerAdapter.Viewholder> {
    HomeFragment homeFragment;
    Context context;
    Activity activity;
    List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponse;
    public OnSelectSelected onSelectSelected;
    BottomSheetDialog bottomSheetDialog;
    public DeskListRecyclerAdapter(Context context, OnSelectSelected onSelectSelected, FragmentActivity activity, List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponse, HomeFragment homeFragment, BottomSheetDialog bottomSheetDialog) {
        this.homeFragment=homeFragment;
        this.context = context;
        this.onSelectSelected =onSelectSelected;
        this.activity = activity;
        this.bookingForEditResponse=bookingForEditResponse;
        this.bottomSheetDialog=bottomSheetDialog;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_room_list_recycler_layout, parent, false);
        return new Viewholder(itemView);

    }
    public interface OnSelectSelected{
        public void onSelectDesk(int deskId,String deskName);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.desk_name.setText(""+bookingForEditResponse.get(position).getDeskCode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onSelectSelected.onSelectDesk(bookingForEditResponse.get(holder.getAbsoluteAdapterPosition()).getDeskId(),
                        bookingForEditResponse.get(holder.getAbsoluteAdapterPosition()).getDeskCode());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingForEditResponse.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_desk_name)
        TextView desk_name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
