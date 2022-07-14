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

import dream.guys.hotdeskandroid.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    public static ArrayList<DataModel> mList;
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

        DataModel model = mList.get(holder.getAdapterPosition());
        holder.mTextView.setText(model.getItemText());

        boolean isExpandable = model.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (model.isChecked()){
            holder.checkBox.setChecked(true);
        }else {
            holder.checkBox.setChecked(false);
        }

        if (isExpandable){
            holder.mArrowImage.setImageResource(R.drawable.minus_1px);
        }else{
            holder.mArrowImage.setImageResource(R.drawable.plus_1px);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    mList.get(holder.getAdapterPosition()).setChecked(true);
                    list = new ArrayList<>();

                    for (int i=0;i<mList.get(holder.getAdapterPosition()).getNestedList().size();i++){

                        mList.get(holder.getAdapterPosition()).getNestedList().get(i).setChecked(true);
                        //list.set(i,mList.get(holder.getAdapterPosition()).getNestedList().get(i));

                        list = mList.get(holder.getAdapterPosition()).getNestedList();

                    }
                    setValueToadapter(holder,holder.getAbsoluteAdapterPosition());
                }else {
                    mList.get(holder.getAdapterPosition()).setChecked(false);
                    list = new ArrayList<>();

                    for (int i=0;i<mList.get(holder.getAdapterPosition()).getNestedList().size();i++){

                        mList.get(holder.getAdapterPosition()).getNestedList().get(i).setChecked(false);
                        //list.set(i,mList.get(holder.getAdapterPosition()).getNestedList().get(i));

                        list = mList.get(holder.getAdapterPosition()).getNestedList();

                    }
                    setValueToadapter(holder,holder.getAbsoluteAdapterPosition());
                }
            }
        });

        setValueToadapter(holder,holder.getAbsoluteAdapterPosition());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //model.setExpandable(!model.isExpandable());
                mList.get(holder.getAdapterPosition()).setExpandable(!model.isExpandable());
                list = mList.get(holder.getAdapterPosition()).getNestedList(); //model.getNestedList();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    private void setValueToadapter(ItemViewHolder holder,int pos) {


        adapter = new NestedAdapter(list,pos);
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
