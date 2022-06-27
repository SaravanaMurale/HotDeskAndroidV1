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

public class ShowCountryAdapter extends RecyclerView.Adapter<ShowCountryAdapter.ShowCountryViewHolder> {

    Context context;
    List<String> countryList;


    interface  OnSelectListener{

        public void onSelect();
    }

    public ShowCountryAdapter(Context context, List<String> countryList) {

        this.context=context;
        this.countryList=countryList;

    }

    @NonNull
    @Override
    public ShowCountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_show_country_adapter, parent, false);
        return new ShowCountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowCountryViewHolder holder, int position) {

        holder.tvCountryName.setText(countryList.get(position));

        holder.tvCountryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countryList.get(position);

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
