package dream.guys.hotdeskandroid.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements Filterable {

    public static ArrayList<DataModel> mList;
    private ArrayList<ValuesPOJO> list = new ArrayList<>();
    NestedAdapter adapter;
    selectItemInterface selectItemInterface;

    //For Filter
    public static ArrayList<DataModel> mListAll;

    public ArrayList<DataModel> getUpdatedList(){
        return mList;
    }

    public ItemAdapter() {
    }

    public ItemAdapter(ArrayList<DataModel> mList){
        this.mList  = mList;
        notifyDataSetChanged();
    }

    public ItemAdapter(ArrayList<DataModel> mList,selectItemInterface selectItemInterface){
        this.mList  = mList;
        notifyDataSetChanged();
        this.selectItemInterface = selectItemInterface;
        this.mListAll=new ArrayList<>(mList);

    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_item , parent , false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        //if(position!=0) {

        DataModel model = mList.get(holder.getAdapterPosition());
        holder.mTextView.setText(model.getItemText());

        //boolean isExpandable = model.isExpandable();
        //holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (model.isChecked()) {
            mList.get(position).setChecked(true);
            holder.checkBox.setChecked(true);
        } else {
            mList.get(position).setChecked(false);
            holder.checkBox.setChecked(false);
        }

            /*if (isExpandable) {
                holder.mArrowImage.setImageResource(R.drawable.minus_1px);
                holder.expandableLayout.setVisibility(View.VISIBLE);
            } else {
                holder.mArrowImage.setImageResource(R.drawable.plus_1px);
                holder.expandableLayout.setVisibility(View.GONE);
            }
*/
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    mList.get(holder.getAdapterPosition()).setChecked(true);
                    list = new ArrayList<>();

                    for (int i = 0; i < mList.get(holder.getAdapterPosition()).getNestedList().size(); i++) {

                        mList.get(holder.getAdapterPosition()).getNestedList().get(i).setChecked(true);
                        //list.set(i,mList.get(holder.getAdapterPosition()).getNestedList().get(i));

                        list = mList.get(holder.getAdapterPosition()).getNestedList();

                    }
                    setValueToadapter(holder, holder.getAbsoluteAdapterPosition());
                } else {
                    mList.get(holder.getAdapterPosition()).setChecked(false);
                    list = new ArrayList<>();

                    for (int i = 0; i < mList.get(holder.getAdapterPosition()).getNestedList().size(); i++) {

                        mList.get(holder.getAdapterPosition()).getNestedList().get(i).setChecked(false);
                        //list.set(i,mList.get(holder.getAdapterPosition()).getNestedList().get(i));

                        list = mList.get(holder.getAdapterPosition()).getNestedList();

                    }
                    setValueToadapter(holder, holder.getAbsoluteAdapterPosition());
                }
            }
        });

        //New...
        list = mList.get(holder.getAdapterPosition()).getNestedList();

        setValueToadapter(holder, holder.getAbsoluteAdapterPosition());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //model.setExpandable(!model.isExpandable());
                mList.get(holder.getAdapterPosition()).setExpandable(!model.isExpandable());
                list = mList.get(holder.getAdapterPosition()).getNestedList(); //model.getNestedList();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        /*}else {
            holder.linearLayout.setVisibility(View.GONE);
        }*/
    }

    private void setValueToadapter(ItemViewHolder holder,int pos) {


        adapter = new NestedAdapter(list,pos,ItemAdapter.this);
        holder.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.nestedRecyclerView.setHasFixedSize(true);
        holder.nestedRecyclerView.setAdapter(adapter);

        boolean isExpandable = mList.get(pos).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable) {
            holder.mArrowImage.setImageResource(R.drawable.minus_1px);
            holder.expandableLayout.setVisibility(View.VISIBLE);
        } else {
            holder.mArrowImage.setImageResource(R.drawable.plus_1px);
            holder.expandableLayout.setVisibility(View.GONE);
        }

        //New...
        if (selectItemInterface!=null){
            selectItemInterface.clickCount(mList,pos);
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void clearAll(){
        mList.clear();
        mListAll.clear();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<DataModel> filteredList=new ArrayList<>();


            if(constraint==null || constraint.toString().isEmpty() || constraint.length()==0){
                filteredList.addAll(mListAll);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (DataModel daoTeamMember:mListAll){
                    ArrayList<ValuesPOJO> nestedList2 = new ArrayList<>();

                    for (int i = 0; i < list.size(); i++) {

                        if(list.get(i).getValues().toLowerCase().contains(filterPattern)){
                            nestedList2.add(list.get(i));

                        }
                    }
                    filteredList.add(new DataModel(nestedList2,mList.get(0).getItemText()));
                }

            }

            FilterResults results=new FilterResults();
            results.values=filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mList.clear();
            mList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

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


    //New...
    public interface selectItemInterface {
        public void clickCount(ArrayList<DataModel> mList,int pos);
    }


}
