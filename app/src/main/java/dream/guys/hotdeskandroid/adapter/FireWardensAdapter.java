package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.FirstAidResponse;
import dream.guys.hotdeskandroid.ui.wellbeing.FireWardensActivity;

public class FireWardensAdapter extends RecyclerView.Adapter<FireWardensAdapter.FireWandensViewHolder> {


    Context context;
    List<FirstAidResponse.Persons> firstAidResponseList;
    String description;

    ViewPersonDetailCliclable viewPersonDetailCliclable;

    public interface ViewPersonDetailCliclable{
        public void  viewPersonClick(FirstAidResponse.Persons persons);
    }

    public FireWardensAdapter(Context context, List<FirstAidResponse.Persons> firstAidResponseList, String description,ViewPersonDetailCliclable viewPersonDetailCliclable) {

        this.context=context;
        this.firstAidResponseList=firstAidResponseList;
        this.description=description;
        this.viewPersonDetailCliclable=viewPersonDetailCliclable;

    }

    @NonNull
    @Override
    public FireWandensViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_firewardens_adapter, parent, false);
        return  new FireWandensViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FireWandensViewHolder holder, int position) {


        holder.firewandensName.setText(firstAidResponseList.get(position).getFullName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPersonDetailCliclable.viewPersonClick(firstAidResponseList.get(holder.getAbsoluteAdapterPosition()));




            }
        });



    }

    @Override
    public int getItemCount() {
        return firstAidResponseList.size();
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
