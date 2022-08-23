package dream.guys.hotdeskandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.WellbeingConfigResponse;

public class WellbeingCommonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "PersonalHelpAdapter";
    Activity mActivity;
    Context mContext;
    public List<WellbeingConfigResponse.Link> itemsData = new ArrayList<>();


    public WellbeingCommonAdapter(Context mContext, List<WellbeingConfigResponse.Link> links) {
        this.mContext = mContext;
        this.itemsData = links;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;



            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_view, parent, false);
            return new PersonalViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        PersonalViewHolder personalViewHolder = (PersonalViewHolder) viewHolder;
        if(!itemsData.get(position).getName().isEmpty()){
            personalViewHolder.tv_name.setVisibility(View.VISIBLE);
            personalViewHolder.tv_name.setText(itemsData.get(position).getName());
        }
        personalViewHolder.textView.setText(itemsData.get(position).getUrl());

        Log.d(TAG, "onBindViewHolder: "+itemsData.get(position).getUrl());

    }


    @Override
    public int getItemCount()
    {
        return itemsData.size();
    }

    static class PersonalViewHolder extends RecyclerView.ViewHolder {

        TextView textView, tv_name;

        public PersonalViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            textView = itemView.findViewById(R.id.tv_url);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}