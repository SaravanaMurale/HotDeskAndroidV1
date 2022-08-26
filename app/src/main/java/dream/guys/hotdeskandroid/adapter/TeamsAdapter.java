package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.viewHolder> {

    Context context;
    ArrayList<DAOTeamMember> teamMembersList;
    TeamMemberInterface teamMemberInterface;

    public TeamsAdapter(Context context, ArrayList<DAOTeamMember> teamMembersList,TeamMemberInterface teamMemberInterface) {
        this.context = context;
        this.teamMembersList = teamMembersList;
        this.teamMemberInterface = teamMemberInterface;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View imageLayout = LayoutInflater.from(context).inflate(R.layout.adapter_teams, parent, false);
        return new viewHolder(imageLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        String fName = teamMembersList.get(position).getFirstName();
        String lName = teamMembersList.get(position).getLastName();

        holder.mTxtName.setText(fName + " " + lName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamMemberInterface.clickEvent(teamMembersList.get(holder.getAbsoluteAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return teamMembersList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView mTxtName;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtName = itemView.findViewById(R.id.bookingDeskName);

        }
    }

    public interface TeamMemberInterface {

        void clickEvent(DAOTeamMember daoTeamMember);
    }

}
