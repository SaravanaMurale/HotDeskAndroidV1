package com.brickendon.hdplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.response.DAOActiveLocation;
import com.brickendon.hdplus.utils.AppConstants;

public class DefaultLocationAdapter extends RecyclerView.Adapter<DefaultLocationAdapter.viewHolder> {

    Context context;
    ArrayList<DAOActiveLocation> activeLocationArrayList;
    DefaultLocationInterface locationInterface;
    String sFrom;

    public DefaultLocationAdapter(Context context, ArrayList<DAOActiveLocation> activeLocationArrayList,
                                  DefaultLocationInterface locationInterface,String sFrom) {
        this.context = context;
        this.activeLocationArrayList = activeLocationArrayList;
        this.locationInterface = locationInterface;
        this.sFrom = sFrom;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_default_location, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        int pos = holder.getAbsoluteAdapterPosition();

        switch (activeLocationArrayList.get(position).getLevel()) {
            case 1:
                setMargins(holder,0);
                setDisable(holder,position);
                break;
            case 2:
                setMargins(holder,40);
                setDisable(holder,position);
                break;
            case 3:
                setMargins(holder,100);
                setDisable(holder,position);
                break;
            case 4:
                setMargins(holder,150);
                if (sFrom.equalsIgnoreCase(AppConstants.DefaultLocation)){
                    setEnable(holder,position);

                   /* if (!activeLocationArrayList.get(position).getLocationType().equals(26)){
                        setEnable(holder,position);
                    }else {
                        setDisable(holder,position);
                    }*/


                }else {
                    if (activeLocationArrayList.get(position).getLocationType().equals(26)){
                        setEnable(holder,position);
                    }else {
                        setDisable(holder,position);
                    }
                }
                break;

        }

        holder.txt_title.setText(activeLocationArrayList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.txt_title.isEnabled()){
                    locationInterface.clickEvent(pos,holder.txt_title.getText().toString(),activeLocationArrayList.get(holder.getAbsoluteAdapterPosition()).getId());
                }else {

                }
            }
        });

    }

    private void setEnable(viewHolder holder,int pos) {
        holder.txt_title.setEnabled(true);
        holder.txt_title.setTextColor(context.getResources().getColor(R.color.figmaBlack, context.getTheme()));
    }
    private void setDisable(viewHolder holder,int pos) {
        holder.txt_title.setEnabled(false);
        holder.txt_title.setTextColor(context.getResources().getColor(R.color.grey, context.getTheme()));
    }

    public void setMargins(viewHolder holder,int margin) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(margin,0,0,0);
        holder.txt_title.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return activeLocationArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView txt_title;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.txt_title);

        }
    }

    public interface DefaultLocationInterface{
        public void clickEvent(int position,String floorName, int locationId);
    }

}
