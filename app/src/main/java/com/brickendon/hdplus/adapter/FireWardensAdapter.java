package com.brickendon.hdplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.response.FirstAidResponse;

public class FireWardensAdapter extends RecyclerView.Adapter<FireWardensAdapter.FireWandensViewHolder> {


    Context context;
    List<FirstAidResponse.Persons> firstAidResponseList;
    int description;

    ViewPersonDetailCliclable viewPersonDetailCliclable;

    public interface ViewPersonDetailCliclable{
        public void  viewPersonClick(FirstAidResponse.Persons persons);
        public void  loadLocateFromFireWardens();
    }

    public FireWardensAdapter(Context context, List<FirstAidResponse.Persons> firstAidResponseList,
                              int description,ViewPersonDetailCliclable viewPersonDetailCliclable) {

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

        if(description == 4)
            Glide.with(context)
                .load(R.drawable.fire)
                .placeholder(R.drawable.fire)
                .into(holder.firewardensImage);
        else
            Glide.with(context)
                    .load(R.drawable.plus)
                    .placeholder(R.drawable.plus)
                    .into(holder.firewardensImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPersonDetailCliclable.viewPersonClick(firstAidResponseList.get(holder.getAbsoluteAdapterPosition()));

            }
        });


        holder.locateMyTeamLocationFireWarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstAidResponseList.get(holder.getAbsoluteAdapterPosition());

                viewPersonDetailCliclable.loadLocateFromFireWardens();
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

        @BindView(R.id.locateMyTeamLocation)
        ImageView locateMyTeamLocationFireWarden;


        public FireWandensViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
