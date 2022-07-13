package dream.guys.hotdeskandroid.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dream.guys.hotdeskandroid.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<DataModel> mList;
    private ArrayList<ValuesPOJO> list = new ArrayList<>();
    NestedAdapter adapter;

    public ItemAdapter(ArrayList<DataModel> mList){
        this.mList  = mList;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_item , parent , false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        DataModel model = mList.get(position);
        holder.mTextView.setText(model.getItemText());

        boolean isExpandable = model.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable){
            holder.mArrowImage.setImageResource(R.drawable.minus);
        }else{
            holder.mArrowImage.setImageResource(R.drawable.plus_1px);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mList.get(position).setChecked(true);

                    for (int i=0;i<mList.get(position).getNestedList().size();i++){

                        model.getNestedList().get(i).setChecked(true);
                        list.set(i,model.getNestedList().get(i));

                    }
                    setValueToadapter(holder);
                }else {
                    mList.get(position).setChecked(false);

                    for (int i=0;i<mList.get(position).getNestedList().size();i++){

                        model.getNestedList().get(i).setChecked(false);
                        list.set(i,model.getNestedList().get(i));

                    }
                    setValueToadapter(holder);
                }
            }
        });

        setValueToadapter(holder);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setExpandable(!model.isExpandable());
                list = model.getNestedList();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    private void setValueToadapter(ItemViewHolder holder) {

        adapter = new NestedAdapter(list);
        holder.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.nestedRecyclerView.setHasFixedSize(true);
        holder.nestedRecyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;
        private RelativeLayout expandableLayout;
        private TextView mTextView;
        private ImageView mArrowImage;
        private RecyclerView nestedRecyclerView;
        CheckBox checkBox;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            mTextView = itemView.findViewById(R.id.itemTv);
            mArrowImage = itemView.findViewById(R.id.arro_imageview);
            nestedRecyclerView = itemView.findViewById(R.id.child_rv);
            checkBox = itemView.findViewById(R.id.check_box);
        }
    }
}
