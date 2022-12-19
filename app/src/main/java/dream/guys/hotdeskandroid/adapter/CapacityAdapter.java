package dream.guys.hotdeskandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.ui.book.BookFragment;

public class CapacityAdapter extends RecyclerView.Adapter<CapacityAdapter.ViewHolder> {
    BookFragment fragment;
    ArrayList<String> capacityList;
    String selectedItem;

    public CapacityAdapter(BookFragment fragment,
                           ArrayList<String> capacityList, String selectedItem) {
        this.fragment = fragment;
        this.capacityList = capacityList;
        this.selectedItem = selectedItem;
    }

    @NonNull
    @Override
    public CapacityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_capacity_item,
                parent, false));
    }
    //   bottom_sheet_capacity_item.xml

    @Override
    public void onBindViewHolder(@NonNull CapacityAdapter.ViewHolder holder, int position) {

        try {
            holder.capacityItem.setText(capacityList.get(position)+ " +");

            if (selectedItem.equalsIgnoreCase(capacityList.get(position)))
                holder.ivCheck.setVisibility(View.VISIBLE);
            else
                holder.ivCheck.setVisibility(View.GONE);


            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.dismissCapacityDialog(holder.capacityItem.getText().toString());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return capacityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView capacityItem;
        ImageView ivCheck;
        RelativeLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.itemLayout);
            capacityItem = itemView.findViewById(R.id.capacityItem);
            ivCheck = itemView.findViewById(R.id.ivCheck);
        }
    }
}
