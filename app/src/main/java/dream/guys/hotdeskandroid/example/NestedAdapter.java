package dream.guys.hotdeskandroid.example;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;

import dream.guys.hotdeskandroid.R;


public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {

    private ArrayList<ValuesPOJO> mList;
    int pos=0;

    public NestedAdapter(ArrayList<ValuesPOJO> mList){
        this.mList = mList;
    }

    public NestedAdapter(ArrayList<ValuesPOJO> list,int pos) {
        this.mList = list;
        this.pos = pos;
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

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

               // Log.d("POS", String.valueOf(pos));

                ItemAdapter.mList.get(pos).getNestedList().get(holder.getAdapterPosition()).setChecked(b);
                //itemAdapter.notifyDataSetChanged();

            }
        });

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
