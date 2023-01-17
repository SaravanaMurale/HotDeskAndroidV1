package com.brickendon.hdplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.request.SelectCode;

public class DeskSelectListAdapter  extends RecyclerView.Adapter<DeskSelectListAdapter.DeskSelectViewHolder> {

    Context context;
    List<SelectCode> stringList;
    OnDeskSelectClickable onDeskSelectClickable;
    BottomSheetDialog bottomSheetDialog;;
    String desk;


    public interface  OnDeskSelectClickable{
        public void onDeskSelect(int id, String code,String desk);
    }

    public DeskSelectListAdapter(Context context, List<SelectCode> stringList, OnDeskSelectClickable onDeskSelectClickable, BottomSheetDialog bottomSheetDialog, String desk) {
        this.context = context;
        this.stringList = stringList;
        this.onDeskSelectClickable = onDeskSelectClickable;
        this.bottomSheetDialog=bottomSheetDialog;
        this.desk=desk;
    }

    @NonNull
    @Override
    public DeskSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_room_list_recycler_layout, parent, false);
        return new DeskSelectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeskSelectViewHolder holder, int position) {
        holder.desk_name.setText(stringList.get(position).getCode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onDeskSelectClickable.onDeskSelect(stringList.get(holder.getAbsoluteAdapterPosition()).getId(),
                        stringList.get(holder.getAbsoluteAdapterPosition()).getCode(),desk);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class DeskSelectViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_desk_name)
        TextView desk_name;

        public DeskSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
