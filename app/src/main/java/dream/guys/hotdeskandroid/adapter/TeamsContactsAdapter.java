package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;

public class TeamsContactsAdapter extends RecyclerView.Adapter<TeamsContactsAdapter.viewHolder> {

    Context context;
    ArrayList<DAOTeamMember> teamMembersList;

    public TeamsContactsAdapter(Context context,ArrayList<DAOTeamMember> teamMembersList) {
        this.context = context;
        this.teamMembersList = teamMembersList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View imageLayout = LayoutInflater.from(context).inflate(R.layout.adapter_team_contacts, parent, false);
        return new viewHolder(imageLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        if (position > 4) {
            if (position == 5){
                holder.relative.setVisibility(View.VISIBLE);
                holder.tvCount.setText(" + " + String.valueOf(teamMembersList.size()-6));
            }else {
                holder.profile_image.setVisibility(View.GONE);
                holder.relative.setVisibility(View.GONE);
            }

        }else {
            holder.relative.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return teamMembersList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tvCount;
        RelativeLayout relative;
        ImageView profile_image;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tvCount = itemView.findViewById(R.id.tvCount);
            relative = itemView.findViewById(R.id.relative);
            profile_image = itemView.findViewById(R.id.profile_image);

        }
    }

}
