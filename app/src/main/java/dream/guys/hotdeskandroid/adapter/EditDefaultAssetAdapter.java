package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.DefaultAssetResponse;

public class EditDefaultAssetAdapter extends RecyclerView.Adapter<EditDefaultAssetAdapter
        .EditDefaultAssetViewHolder> implements Filterable {

    Context context;
    List<DefaultAssetResponse> defaultAssetResponseList;
    List<DefaultAssetResponse> tempList;
    BottomSheetDialog bottomSheetDialog;
    OnDefaultAssetSelectable onDefaultAssetSelectable;
    private String selectedItem;

    public interface OnDefaultAssetSelectable {


        public void onDefaultAssetSelect(int deskId, String code);

    }

    public EditDefaultAssetAdapter(Context context, List<DefaultAssetResponse> defaultAssetResponseList, BottomSheetDialog bottomSheetDialog, OnDefaultAssetSelectable onDefaultAssetSelectable, String selectedItem) {

        this.context = context;
        this.defaultAssetResponseList = defaultAssetResponseList;
        this.tempList = defaultAssetResponseList;
        this.bottomSheetDialog = bottomSheetDialog;
        this.onDefaultAssetSelectable = onDefaultAssetSelectable;
        this.selectedItem = selectedItem;

    }

    @NonNull
    @Override
    public EditDefaultAssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_room_list_recycler_layout, parent, false);
        return new EditDefaultAssetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EditDefaultAssetViewHolder holder, int position) {
        holder.desk_name.setText(tempList.get(position).getCode());

        if (selectedItem != null && !selectedItem.isEmpty()) {
            if (selectedItem.equalsIgnoreCase(tempList.get(position).getCode())) {
                holder.tvSelect.setText("Selected");
                holder.tvSelect.setTextColor(context.getResources().getColor(R.color.figmaBlack));
            } else {
                holder.tvSelect.setText("Select");
                holder.tvSelect.setTextColor(context.getResources().getColor(R.color.figmaBlueText));
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onDefaultAssetSelectable.onDefaultAssetSelect(tempList.get(holder.getAbsoluteAdapterPosition()).getDeskId(),
                        tempList.get(holder.getAbsoluteAdapterPosition()).getCode());
            }
        });

        holder.locationLayout.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return tempList.size();
    }

    public class EditDefaultAssetViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_desk_name)
        TextView desk_name;
        @BindView(R.id.tv_select)
        TextView tvSelect;
        LinearLayout locationLayout;

        public EditDefaultAssetViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            locationLayout = itemView.findViewById(R.id.locationLayout);

        }
    }

    @Override
    public Filter getFilter() {
        return new DeskFilter();
    }

    private class DeskFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = defaultAssetResponseList;
                results.count = defaultAssetResponseList.size();

            } else {
                List<DefaultAssetResponse> fRecords = new ArrayList<>();

                for (DefaultAssetResponse s : defaultAssetResponseList) {
                    if (s.getCode().toLowerCase().trim().
                            contains(constraint.toString().toLowerCase().trim())) {
                        fRecords.add(s);
                    }
                }
                results.values = fRecords;
                results.count = fRecords.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            tempList = (List<DefaultAssetResponse>) results.values;
            notifyDataSetChanged();
        }
    }
}
