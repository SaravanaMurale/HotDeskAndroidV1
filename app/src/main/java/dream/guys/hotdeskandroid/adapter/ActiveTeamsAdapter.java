package dream.guys.hotdeskandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.MainActivity;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.response.ActiveTeamsResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.ui.book.BookFragment;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;

public class ActiveTeamsAdapter extends RecyclerView.Adapter<ActiveTeamsAdapter.Viewholder> {
    int typeId;
    Context context;
    Activity activity;
    BookFragment fragment;
    List<ActiveTeamsResponse> activeTeamsList;
    public OnActiveTeamsSelected onActiveTeamsSelected;
    BottomSheetDialog bottomSheetDialog;
    EditBookingDetails editBookingDetails;
    String newEditStatus;
    public ActiveTeamsAdapter(Context context, OnActiveTeamsSelected onSelectSelected, FragmentActivity activity, List<ActiveTeamsResponse> bookingForEditResponse, BookFragment context1, BottomSheetDialog bottomSheetDialog,
                              int typeId,EditBookingDetails editBookingDetails,String newEditStatus) {
        this.fragment=context1;
        this.context = context;
        this.onActiveTeamsSelected =onSelectSelected;
        this.activity = activity;
        this.activeTeamsList = bookingForEditResponse;
        this.bottomSheetDialog=bottomSheetDialog;
        this.typeId = typeId;
        this.editBookingDetails = editBookingDetails;
        this.newEditStatus= newEditStatus;
    }
    public interface OnActiveTeamsSelected{
        public void onActiveTeamsSelected(int teamId,String teamName,int typeId,EditBookingDetails editBookingDetails,String newEditStatus);

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_team_recycler_layout, parent, false);
        return new Viewholder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        if (activeTeamsList.get(position).getId()
                == SessionHandler.getInstance().getInt(context, AppConstants.TEAM_ID)){
            holder.teamName.setText(activeTeamsList.get(position).getName()+" (Default)");
        } else {
            holder.teamName.setText(activeTeamsList.get(position).getName());
        }

//        if (((MainActivity) activity).selectedTeamId == activeTeamsList.get(position).getId()){
        if (fragment.selectedTeamId == activeTeamsList.get(position).getId()){
            holder.tick.setVisibility(View.VISIBLE);
        } else {
            holder.tick.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActiveTeamsSelected.onActiveTeamsSelected(
                        activeTeamsList.get(holder.getAbsoluteAdapterPosition()).getId(),
                        activeTeamsList.get(holder.getAbsoluteAdapterPosition()).getName(),
                        typeId,
                        editBookingDetails,
                        newEditStatus
                        );
//                if (((MainActivity) activity).deskListBottomSheet!=null)
                if (fragment.deskListBottomSheet!=null)
                    fragment.deskListBottomSheet.dismiss();
                bottomSheetDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return activeTeamsList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.tick)
        ImageView tick;
        @BindView(R.id.tv_team_name)
        TextView teamName;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
