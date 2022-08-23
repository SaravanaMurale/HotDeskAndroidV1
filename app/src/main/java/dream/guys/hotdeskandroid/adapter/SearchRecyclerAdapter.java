package dream.guys.hotdeskandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.GlobalSearchResponse;

public class SearchRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    Activity activity;
    List<GlobalSearchResponse.Results> list;
    GlobalSearchOnClickable globalSearchOnClickable;

    public interface GlobalSearchOnClickable{
        public void onClickGlobalSearch(GlobalSearchResponse.Results results);
    }

    public SearchRecyclerAdapter(Context context, Activity activity, List<GlobalSearchResponse.Results> list,GlobalSearchOnClickable globalSearchOnClickable) {
        this.context = context;
        this.activity = activity;
        this.list = list;
        this.globalSearchOnClickable=globalSearchOnClickable;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case 0:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_people_recycler_layout, parent, false);
                return new ViewholderPeople(itemView);
            case 1:
                View itemView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_desk_recycler_layout, parent, false);
                return new ViewholderDesk(itemView1);
            case 2:
                View itemView2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_team_recycler_layout, parent, false);
                return new ViewholderTeams(itemView2);
            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ViewholderPeople viewholderPeople = (ViewholderPeople) holder;
                viewholderPeople.username.setText(""+list.get(position).getName());
                viewholderPeople.profileTeamName.setText(""+list.get(position).getTeam());
                break;
            case 1:
                ViewholderDesk viewholderDesk = (ViewholderDesk) holder;
                if (list.get(position).getEntityType()==3){
                    viewholderDesk.deskImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.chair));
                    viewholderDesk.deskName.setText(""+list.get(position).getCode());
                    viewholderDesk.deskAddress.setText(""+list.get(position).getFullLocationPath());
                } else if (list.get(position).getEntityType()==4) {
                    viewholderDesk.deskImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.room));
                    viewholderDesk.deskName.setText(""+list.get(position).getName());
                    viewholderDesk.deskAddress.setText(""+list.get(position).getFullLocationPath());
                } else if (list.get(position).getEntityType()==5) {
                    viewholderDesk.deskImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.car));
                }
                break;
            case 2:

                break;
            default:

                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_navigation_locate2);
                //NavController navController = Navigation.findNavController(view);
                //navController.navigate(R.id.action_navigation_home_to_navigation_locate2);
                globalSearchOnClickable.onClickGlobalSearch(list.get(holder.getAbsoluteAdapterPosition()));

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getEntityType()==1)
            return 0;
        else if (list.get(position).getEntityType()==3||list.get(position).getEntityType()==4||list.get(position).getEntityType()==5)
            return 1;
        else
            return 2;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewholderPeople extends RecyclerView.ViewHolder {
        @BindView(R.id.user_profile_pic)
        CircleImageView userProfile;
        @BindView(R.id.profileUserName)
        TextView username;
        @BindView(R.id.profileTeamName)
        TextView profileTeamName;
        public ViewholderPeople(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
    public class ViewholderDesk extends RecyclerView.ViewHolder {
        @BindView(R.id.bookingAddress)
        TextView deskAddress;
        @BindView(R.id.bookingDeskName)
        TextView deskName;
        @BindView(R.id.bookingIvIcon)
        ImageView deskImage;
        public ViewholderDesk(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
    public class ViewholderTeams extends RecyclerView.ViewHolder {
        @BindView(R.id.teams)
        TextView teams;
        public ViewholderTeams(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
