package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.request.CarStatusChangeAndSelect;

public class LocateCarSelectAdapter extends RecyclerView.Adapter<LocateCarSelectAdapter.LocateCarSelectViewHolder> {


    Context context;
    List<CarStatusChangeAndSelect> carStatusChangeAndSelectList;
    OnCarSelectListener onCarSelectListener;


    public LocateCarSelectAdapter(Context context, List<CarStatusChangeAndSelect> carStatusChangeAndSelectList, OnCarSelectListener onCarSelectListener) {
        this.context = context;
        this.carStatusChangeAndSelectList = carStatusChangeAndSelectList;
        this.onCarSelectListener = onCarSelectListener;
    }

    public interface OnCarSelectListener{

        public void onCarSelectAndChange(CarStatusChangeAndSelect carStatusChangeAndSelect);

    }

    @NonNull
    @Override
    public LocateCarSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_desk_select_adapter, parent, false);
        return new LocateCarSelectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocateCarSelectViewHolder holder, int position) {

        holder.deskLocation.setVisibility(View.GONE);
        holder.locateCapacityBlockInDesk.setVisibility(View.GONE);
        holder.chipGroup.setVisibility(View.GONE);
        holder.tvDescriptionInChangeBlocks.setVisibility(View.GONE);

        holder.deskName.setText(carStatusChangeAndSelectList.get(position).getCarName());

        if(carStatusChangeAndSelectList.get(position).getStatus()==0){
            holder.statusText.setText("Unavaliable");
            holder.user_status.setImageDrawable(context.getDrawable(R.drawable.status_unavaliable));
        }else if(carStatusChangeAndSelectList.get(position).getStatus()==1){
            holder.statusText.setText("Aavaliable");
            holder.user_status.setImageDrawable(context.getDrawable(R.drawable.green_dot));
        }else if(carStatusChangeAndSelectList.get(position).getStatus()==2){
            holder.statusText.setText("BookedByMe");
            holder.user_status.setImageDrawable(context.getDrawable(R.drawable.status_booked_byme));
        }else if(carStatusChangeAndSelectList.get(position).getStatus()==3) {
            holder.statusText.setText("BookedByOther");
            holder.user_status.setImageDrawable(context.getDrawable(R.drawable.status_booked));
        }else if(carStatusChangeAndSelectList.get(position).getStatus()==4) {
            holder.statusText.setText("Request");
            holder.user_status.setImageDrawable(context.getDrawable(R.drawable.byrequest));
        }

        holder.selectDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCarSelectListener.onCarSelectAndChange(carStatusChangeAndSelectList.get(holder.getAbsoluteAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return carStatusChangeAndSelectList.size();
    }

    public class LocateCarSelectViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.locate_change_desk_name)
        TextView deskName;
        @BindView(R.id.locate_change_desk_locate)
        TextView deskLocation;
        @BindView(R.id.locateCapacityBlockInDesk)
        LinearLayout locateCapacityBlockInDesk;
        @BindView(R.id.status_check_layout_change)
        LinearLayout statusLayoutChange;
        @BindView(R.id.user_status)
        ImageView user_status;
        @BindView(R.id.changeBlockStatus)
        TextView statusText;

        @BindView(R.id.tvDescriptionInChangeBlocks)
        TextView tvDescriptionInChangeBlocks;
        @BindView(R.id.locate_list_item_selected)
        ChipGroup chipGroup;

        @BindView(R.id.locate_desk_change_select)
        TextView selectDesk;

        public LocateCarSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
