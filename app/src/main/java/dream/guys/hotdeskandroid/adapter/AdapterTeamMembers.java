package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.TeamMembersResponse;

public class AdapterTeamMembers extends RecyclerView.Adapter<AdapterTeamMembers.viewHolder> {

    Context context;
    List<TeamMembersResponse> teamMembersResponses;

    public AdapterTeamMembers(Context context, List<TeamMembersResponse> teamMembersResponses) {
        this.context = context;
        this.teamMembersResponses = teamMembersResponses;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_all_teams, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.mTxtCountryName.setText(teamMembersResponses.get(position).getFirstName() + " " + teamMembersResponses.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return teamMembersResponses.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView mTxtCountryName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtCountryName = itemView.findViewById(R.id.languageCountryName);
        }
    }

}
