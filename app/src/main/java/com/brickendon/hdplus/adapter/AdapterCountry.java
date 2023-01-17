package com.brickendon.hdplus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.response.DAOCountryList;

public class AdapterCountry extends RecyclerView.Adapter<AdapterCountry.viewholder> {

    Context context;
    ArrayList<DAOCountryList> countryList;
    CountryInterface countryInterface;

    public AdapterCountry(Context context, ArrayList<DAOCountryList> countryList,CountryInterface countryInterface) {
        this.context = context;
        this.countryList = countryList;
        this.countryInterface = countryInterface;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_all_teams, parent, false);
        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        int pos = holder.getAbsoluteAdapterPosition();

        holder.mTxtCountryName.setText(countryList.get(pos).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = countryList.get(pos).getId();
                String countryName = countryList.get(pos).getName();

                countryInterface.clickEvent(countryName,id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }


    public class viewholder extends RecyclerView.ViewHolder{

        TextView mTxtCountryName;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            mTxtCountryName = itemView.findViewById(R.id.languageCountryName);

        }
    }

    public interface CountryInterface{
        public void clickEvent(String name,int id);
    }

}
