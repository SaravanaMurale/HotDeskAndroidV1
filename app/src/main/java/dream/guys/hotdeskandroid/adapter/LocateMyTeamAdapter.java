package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;

public class LocateMyTeamAdapter extends RecyclerView.Adapter<LocateMyTeamAdapter.LocateMyTeamViewHolder> {

    Context context;
    List<String> stringName;



    public LocateMyTeamAdapter(Context context, List<String> stringName) {
        this.context=context;
        this.stringName=stringName;
    }

    @NonNull
    @Override
    public LocateMyTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_locate_myteam_adaper, parent, false);
        return  new LocateMyTeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocateMyTeamViewHolder holder, int position) {

        holder.locateMyTeamName.setText(stringName.get(position));

    }

    @Override
    public int getItemCount() {
        return stringName.size();
    }

    class LocateMyTeamViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.locateMyTeamProfile)
        CircleImageView myTeamProfileImage;
        @BindView(R.id.locateMyTeamName)
        TextView locateMyTeamName;
        @BindView(R.id.locateMyTeamDeskName)
        TextView locateMyTeamDeskName;
        @BindView(R.id.locateMyTeamCheckInTime)
        TextView locateMyTeamCheckInTime;
        @BindView(R.id.locateMyTeamCheckOutTime)
        TextView locateMyTeamCheckOutTime;

        public LocateMyTeamViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
