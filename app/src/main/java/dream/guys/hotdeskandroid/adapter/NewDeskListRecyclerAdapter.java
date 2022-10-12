package dream.guys.hotdeskandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.ui.book.BookFragment;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;

public class NewDeskListRecyclerAdapter extends RecyclerView.Adapter<NewDeskListRecyclerAdapter.viewholder> {
    Context context;
    Activity activity;
    BookFragment fragment;
    List<BookingForEditResponse.TeamDeskAvailabilities> deskList;
    public OnChangeSelected onChangeSelected;
    BottomSheetDialog bottomSheetDialog;

    public NewDeskListRecyclerAdapter(Context context, OnChangeSelected onSelectSelected, FragmentActivity activity, List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponse, BookFragment context1, BottomSheetDialog bottomSheetDialog) {
         this.fragment=context1;
        this.context = context;
        this.onChangeSelected =onSelectSelected;
        this.activity = activity;
        this.deskList = bookingForEditResponse;
        this.bottomSheetDialog=bottomSheetDialog;

    }
    public interface OnChangeSelected{
        public void onChangeDesk(int deskId,String deskName);

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_desk_room_list_recycler_layout, parent, false);
        return new viewholder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
//        fragment.selectedTeamId
        holder.desk_name.setText(deskList.get(position).getDeskCode());

        if (deskList.get(position).getTeamId() != SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID)
                && fragment.selectedTeamAutoApproveStatus != 2){
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.select.setVisibility(View.VISIBLE);

            holder.deskStatus.setText("Available For Request");
            holder.deskIconStatus.setColorFilter(ContextCompat.getColor(context, R.color.figma_orange),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.select.setVisibility(View.VISIBLE);

            holder.deskStatus.setText("Available");
            holder.deskIconStatus.setColorFilter(ContextCompat.getColor(context, R.color.figmaLiteGreen),
                    android.graphics.PorterDuff.Mode.MULTIPLY);

        }

        if (deskList.get(position).isBookedByUser()){
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.select.setVisibility(View.VISIBLE);
            holder.deskStatus.setText("Booked by me");
            holder.deskIconStatus.setColorFilter(ContextCompat.getColor(context, R.color.figmaBlue),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        if (deskList.get(position).isBookedByElse()){
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
            holder.select.setVisibility(View.GONE);
            holder.deskStatus.setText("Booked by Other");
            holder.deskIconStatus.setColorFilter(ContextCompat.getColor(context, R.color.figmaGrey),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        }


    }

    @Override
    public int getItemCount() {
        return deskList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.card)
        CardView card;
        @BindView(R.id.tv_desk_name)
        TextView desk_name;
        @BindView(R.id.tv_select)
        TextView select;
        @BindView(R.id.desk_status)
        TextView deskStatus;
        @BindView(R.id.desk_icon_status)
        ImageView deskIconStatus;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
