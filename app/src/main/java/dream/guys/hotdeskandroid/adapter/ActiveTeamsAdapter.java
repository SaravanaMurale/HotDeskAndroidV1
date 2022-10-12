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
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.ActiveTeamsResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.ui.book.BookFragment;

public class ActiveTeamsAdapter extends RecyclerView.Adapter<ActiveTeamsAdapter.Viewholder> {
    Context context;
    Activity activity;
    BookFragment fragment;
    List<ActiveTeamsResponse> activeTeamsList;
    public OnActiveTeamsSelected onActiveTeamsSelected;
    BottomSheetDialog bottomSheetDialog;

    public ActiveTeamsAdapter(Context context, OnActiveTeamsSelected onSelectSelected, FragmentActivity activity, List<ActiveTeamsResponse> bookingForEditResponse, BookFragment context1, BottomSheetDialog bottomSheetDialog) {
        this.fragment=context1;
        this.context = context;
        this.onActiveTeamsSelected =onSelectSelected;
        this.activity = activity;
        this.activeTeamsList = bookingForEditResponse;
        this.bottomSheetDialog=bottomSheetDialog;

    }
    public interface OnActiveTeamsSelected{
        public void onActiveTeamsSelected(int deskId,String deskName);

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_team_recycler_layout, parent, false);
        return new Viewholder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

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
