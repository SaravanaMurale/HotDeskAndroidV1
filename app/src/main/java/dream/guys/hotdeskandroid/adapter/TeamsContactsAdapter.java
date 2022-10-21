package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.ui.home.EditProfileActivity;
import dream.guys.hotdeskandroid.ui.teams.TeamsFragment;

public class TeamsContactsAdapter extends RecyclerView.Adapter<TeamsContactsAdapter.viewHolder> {

    Context context;
    ArrayList<DAOTeamMember> teamMembersList;
    OnProfileClickable  onProfileClickable;

    public interface OnProfileClickable{
        public void onProfileClick(DAOTeamMember daoTeamMember);
    }

    public TeamsContactsAdapter(Context context, ArrayList<DAOTeamMember> teamMembersList, OnProfileClickable  onProfileClickable) {
        this.context = context;
        this.teamMembersList = teamMembersList;
        this.onProfileClickable=onProfileClickable;
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

        if (teamMembersList.get(position).getProfileImage()!=null
                && !teamMembersList.get(position).getProfileImage().equalsIgnoreCase("")){
            String cleanImage = teamMembersList.get(position).getProfileImage().replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,","");
            byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.profile_image.setImageBitmap(decodedByte);
        } else {
            Glide.with(context).load(R.drawable.avatar)
                    .into(holder.profile_image);
        }

        holder.profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "sds"+teamMembersList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().size(), Toast.LENGTH_SHORT).show();
                onProfileClickable.onProfileClick(teamMembersList.get(holder.getAbsoluteAdapterPosition()));
            }
        });
        holder.tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileClickable.onProfileClick(null);
            }
        });

    }

    @Override
    public int getItemCount() {
        return teamMembersList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView tvCount;
        RelativeLayout relative;
        ImageView profile_image;
        CircleImageView circleImageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tvCount = itemView.findViewById(R.id.tvCount);
            relative = itemView.findViewById(R.id.relative);
            profile_image = itemView.findViewById(R.id.profile_image);


        }
    }

}
