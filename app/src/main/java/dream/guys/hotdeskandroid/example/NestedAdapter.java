package dream.guys.hotdeskandroid.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;

import dream.guys.hotdeskandroid.R;


public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {

    private ArrayList<ValuesPOJO> mList;

    public NestedAdapter(ArrayList<ValuesPOJO> mList){
        this.mList = mList;
    }

    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item , parent , false);
        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.mTv.setText(mList.get(position).getValues());

        holder.checkBox.setChecked(mList.get(position).isChecked());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder{
        private TextView mTv;
        CheckBox checkBox;
        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.nestedItemTv);
            checkBox = itemView.findViewById(R.id.check_box);
        }
    }

    public void updateList(boolean b){

        /*if (b){
            Collections.replaceAll(mList.,)
            notifyDataSetChanged();
        }else {
            Collections.replaceAll(mList,)
            notifyDataSetChanged();
        }*/

    }

}
