package com.brickendon.hdplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.response.LocateCountryRespose;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.SessionHandler;

public class ShowCountryAdapter extends RecyclerView.Adapter<ShowCountryAdapter.ShowCountryViewHolder> implements Filterable {

    Context context;
    List<LocateCountryRespose> countryList;
    OnSelectListener onSelectListener;
    String identifier="";


    //Search
    List<LocateCountryRespose> countryListAll;

    int pos = -1;
    int sPos = -1;

    boolean showBuildingList;

    public interface  OnSelectListener{
        public void onSelect(LocateCountryRespose locateCountryRespose, String identifier);
    }

    public ShowCountryAdapter(Context context, List<LocateCountryRespose> countryList, OnSelectListener onSelectListener, String identifier,boolean showBuildingStatus) {
        this.context=context;
        this.countryList=countryList;
        this.onSelectListener=onSelectListener;
        this.identifier=identifier;
        this.countryListAll=new ArrayList<>(countryList);
        this.showBuildingList=showBuildingStatus;


    }

    @NonNull
    @Override
    public ShowCountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_country_adapter, parent, false);
        return new ShowCountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowCountryViewHolder holder, int position) {

        if(!showBuildingList) {

            holder.floorBlock.setVisibility(View.GONE);
            holder.itemCardView.setVisibility(View.VISIBLE);

            holder.tvCountryName.setText(countryList.get(position).getName());

            holder.itemCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onSelectListener.onSelect(countryList.get(holder.getAbsoluteAdapterPosition()), identifier);

                }
            });

        }else {

            //UI chaging like floor
            holder.floorBlock.setVisibility(View.VISIBLE);
            holder.itemCardView.setVisibility(View.GONE);

            holder.locateFloorNames.setText(countryList.get(position).getName());

            holder.floorBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pos=holder.getAbsoluteAdapterPosition();
                    notifyDataSetChanged();

                    //onSelectListener.onSelect(countryList.get(holder.getAbsoluteAdapterPosition()), identifier);
                }
            });

            if (pos == position) {

                if (pos >= sPos && sPos == pos) {

                    //SessionHandler.getInstance().remove(context, AppConstants.FLOOR_POSITION);
                    //SessionHandler.getInstance().saveBoolean(context,AppConstants.FLOOR_SELECTED_STATUS,false);
                    holder.ivLocateFloors.setImageDrawable(context.getDrawable(R.drawable.floor_disable));
                    sPos = -1;
                } else {
                    holder.ivLocateFloors.setImageDrawable(context.getDrawable(R.drawable.floor_enable));
                    //SessionHandler.getInstance().saveBoolean(context,AppConstants.FLOOR_SELECTED_STATUS,true);

                    sPos=pos;

                    onSelectListener.onSelect(countryList.get(holder.getAbsoluteAdapterPosition()), identifier);
                }

            } else {

                holder.ivLocateFloors.setImageDrawable(context.getDrawable(R.drawable.floor_disable));
                //sPos = -1;
            }


        }



    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<LocateCountryRespose> filteredList=new ArrayList<>();

            if(constraint==null || constraint.toString().isEmpty() || constraint.length()==0){
                filteredList.addAll(countryListAll);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (LocateCountryRespose locateCountryRespose:countryListAll){

                    if(locateCountryRespose.getName().toLowerCase().contains(filterPattern)){

                        filteredList.add(locateCountryRespose);

                    }

                }

            }

            FilterResults results=new FilterResults();
            results.values=filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            countryList.clear();
            countryList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };



    public class ShowCountryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.itemCardView)
        CardView itemCardView;
        @BindView(R.id.tvCountryName)
        TextView tvCountryName;

        @BindView(R.id.floorBlocks)
        RelativeLayout floorBlock;
        @BindView(R.id.ivLocateFloors)
        ImageView ivLocateFloors;
        @BindView(R.id.locateFloorNames)
        TextView locateFloorNames;

        public ShowCountryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
