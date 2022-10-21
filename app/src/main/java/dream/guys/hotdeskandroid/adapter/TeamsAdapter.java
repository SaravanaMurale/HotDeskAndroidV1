package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;

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
        System.out.println("bala check teams out");
        if (teamMembersList.get(position).getProfileImage()!=null
                && !teamMembersList.get(position).getProfileImage().equalsIgnoreCase("")){
            String cleanImage = teamMembersList.get(position).getProfileImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,","");
            byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.teamMemberImage.setImageBitmap(decodedByte);
        } else {
            Glide.with(context).load(R.drawable.avatar)
                    .into(holder.teamMemberImage);
        }
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
        CircleImageView teamMemberImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            mTxtName = itemView.findViewById(R.id.bookingDeskName);
            teamMemberImage = itemView.findViewById(R.id.bookingIvIcon);

        }
    }

    public interface TeamMemberInterface {

        void clickEvent(DAOTeamMember daoTeamMember);
    }

}
