package dream.guys.hotdeskandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dream.guys.hotdeskandroid.R;

public class LocateMyTeamAdapter extends RecyclerView.Adapter<LocateMyTeamAdapter.LocateMyTeamViewHolder> {


    @NonNull
    @Override
    public LocateMyTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_locate_myteam_adaper, parent, false);
        return  new LocateMyTeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocateMyTeamViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class LocateMyTeamViewHolder extends RecyclerView.ViewHolder{

        public LocateMyTeamViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
