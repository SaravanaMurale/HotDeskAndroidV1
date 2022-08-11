package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.DefaultAssetResponse;

public class EditDefaultAssetAdapter  extends RecyclerView.Adapter<EditDefaultAssetAdapter.EditDefaultAssetViewHolder> {

    Context context;
    List<DefaultAssetResponse> defaultAssetResponseList;
    BottomSheetDialog bottomSheetDialog;
    OnDefaultAssetSelectable onDefaultAssetSelectable;

    public interface OnDefaultAssetSelectable{


        public void onDefaultAssetSelect(int deskId, String code);

    }

    public EditDefaultAssetAdapter(Context context, List<DefaultAssetResponse> defaultAssetResponseList, BottomSheetDialog bottomSheetDialog, OnDefaultAssetSelectable onDefaultAssetSelectable) {

        this.context=context;
        this.defaultAssetResponseList=defaultAssetResponseList;
        this.bottomSheetDialog=bottomSheetDialog;
        this.onDefaultAssetSelectable=onDefaultAssetSelectable;

    }

    @NonNull
    @Override
    public EditDefaultAssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_room_list_recycler_layout, parent, false);
        return new EditDefaultAssetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EditDefaultAssetViewHolder holder, int position) {
        holder.desk_name.setText(defaultAssetResponseList.get(position).getCode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                onDefaultAssetSelectable.onDefaultAssetSelect(defaultAssetResponseList.get(holder.getAbsoluteAdapterPosition()).getDeskId(),
                        defaultAssetResponseList.get(holder.getAbsoluteAdapterPosition()).getCode());
            }
        });
    }

    @Override
    public int getItemCount() {
        return defaultAssetResponseList.size();
    }

    public class EditDefaultAssetViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_desk_name)
        TextView desk_name;
        public EditDefaultAssetViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
