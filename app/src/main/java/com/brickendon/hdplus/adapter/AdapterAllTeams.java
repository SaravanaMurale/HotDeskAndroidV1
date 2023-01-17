package com.brickendon.hdplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.response.TeamsResponse;

public class AdapterAllTeams extends RecyclerView.Adapter<AdapterAllTeams.viewholder> {

    Context context;
    List<TeamsResponse> teamsResponseList;

    public AdapterAllTeams(Context context, List<TeamsResponse> teamsResponseList) {
        this.context = context;
        this.teamsResponseList = teamsResponseList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_all_teams, parent, false);
        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.mTxtTeamName.setText(teamsResponseList.get(holder.getAbsoluteAdapterPosition()).getName());
    }

    @Override
    public int getItemCount() {
        return teamsResponseList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView mTxtTeamName;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            mTxtTeamName = itemView.findViewById(R.id.languageCountryName);

        }
    }

}
