package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.LanguageListResponse;
import dream.guys.hotdeskandroid.ui.login.LoginActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;

public class LanguageListAdapter extends RecyclerView.Adapter<LanguageListAdapter.LanguageListViewHolder> {

    Context context;
    List<LanguageListResponse.Multilanguages.ExistingLanguages> languageListResponseList;
    OnLanguageSelect onLanguageSelect;
    int pos =-1;


    public interface OnLanguageSelect{

        public void onLanguageSelect(String key);
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

        /*String imageName = languageListResponseList.get(position).getImagesrc();
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(imageName, "drawable", context.getPackageName());
        Drawable drawable = ResourcesCompat.getDrawable(resources,resourceId,context.getTheme());// resources.getDrawable(resourceId);
        holder.languageFlag.setImageDrawable(drawable);*/

        //Glide.with(context).load(drawable).into(holder.languageFlag);
        holder.languageCountryName.setText(languageListResponseList.get(position).getText());

        holder.languageAdapterBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = holder.getAbsoluteAdapterPosition();
                notifyDataSetChanged();

            }
        });

        if (pos == holder.getAbsoluteAdapterPosition()){
            holder.languageSelectTick.setVisibility(View.VISIBLE);

            SessionHandler.getInstance().save(context, AppConstants.LANGUAGE_KEY,languageListResponseList.get(position).getName());
            SessionHandler.getInstance().save(context, AppConstants.LANGUAGE,languageListResponseList.get(position).getText());

            onLanguageSelect.onLanguageSelect(languageListResponseList.get(position).getName());
        }else {
            holder.languageSelectTick.setVisibility(View.GONE);
        }

        //New..
        if (SessionHandler.getInstance().get(context, AppConstants.LANGUAGE_KEY)!=null){
          String s =  SessionHandler.getInstance().get(context, AppConstants.LANGUAGE_KEY);

          if (s.equalsIgnoreCase(languageListResponseList.get(position).getName())){
              holder.languageSelectTick.setVisibility(View.VISIBLE);
          }else {
              holder.languageSelectTick.setVisibility(View.GONE);
          }

        }

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
