package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;

public class ShowCountryAdapter extends RecyclerView.Adapter<ShowCountryAdapter.ShowCountryViewHolder> {

    Context context;
    List<LocateCountryRespose> countryList;
    OnSelectListener onSelectListener;
    String identifier="";


    public interface  OnSelectListener{

        public void onSelect(LocateCountryRespose locateCountryRespose, String identifier);
    }

    public ShowCountryAdapter(Context context, List<LocateCountryRespose> countryList, OnSelectListener onSelectListener, String identifier) {

        this.context=context;
        this.countryList=countryList;
        this.onSelectListener=onSelectListener;
        this.identifier=identifier;

    }

    @NonNull
    @Override
    public ShowCountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_country_adapter, parent, false);
        return new ShowCountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowCountryViewHolder holder, int position) {

        holder.tvCountryName.setText(countryList.get(position).getName());

        holder.tvCountryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSelectListener.onSelect( countryList.get(holder.getAbsoluteAdapterPosition()),identifier);

            }
        });

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ShowCountryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvCountryName)
        TextView tvCountryName;

        public ShowCountryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
