package dream.guys.hotdeskandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;

public class FireWardensAdapter extends RecyclerView.Adapter<FireWardensAdapter.FireWandensViewHolder> {

    @NonNull
    @Override
    public FireWandensViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_firewardens_adapter, parent, false);
        return  new FireWandensViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FireWandensViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class FireWandensViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.firewardensProfilePic)
        CircleImageView firewardensProfilePic;
        @BindView(R.id.firewandensName)
        TextView firewandensName;
        @BindView(R.id.firewardensImage)
        ImageView firewardensImage;
        @BindView(R.id.firwardensStatus)
        TextView firwardensStatus;
        @BindView(R.id.firwardensAddress)
        TextView firwardensAddress;

        public FireWandensViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
