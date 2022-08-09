package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.LanguageListResponse;

public class LanguageListAdapter extends RecyclerView.Adapter<LanguageListAdapter.LanguageListViewHolder> {

    Context context;
    List<LanguageListResponse.Multilanguages.ExistingLanguages> languageListResponseList;
    OnLanguageSelect onLanguageSelect;


    public interface OnLanguageSelect{

        public void onLanguageSelect();
    }

    public LanguageListAdapter(Context context, List<LanguageListResponse.Multilanguages.ExistingLanguages> languageListResponseList, OnLanguageSelect onLanguageSelect) {

        this.context=context;
        this.languageListResponseList=languageListResponseList;
        this.onLanguageSelect=onLanguageSelect;

    }

    @NonNull
    @Override
    public LanguageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_language_adapter, parent, false);
        return new LanguageListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LanguageListViewHolder holder, int position) {

        //Glide.with(context).load("").into(holder.languageFlag);
        holder.languageCountryName.setText(languageListResponseList.get(position).getText());

        holder.languageAdapterBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.languageSelectTick.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return languageListResponseList.size();
    }

    public class LanguageListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.languageAdapterBlock)
        RelativeLayout languageAdapterBlock;
        @BindView(R.id.languageFlag)
        ImageView languageFlag;
        @BindView(R.id.languageCountryName)
        TextView languageCountryName;
        @BindView(R.id.languageSelectTick)
        ImageView languageSelectTick;

        public LanguageListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
