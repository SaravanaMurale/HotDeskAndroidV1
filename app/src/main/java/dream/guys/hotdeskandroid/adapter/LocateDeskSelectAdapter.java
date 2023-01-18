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
import dream.guys.hotdeskandroid.model.request.DeskStatusChangeAndSelect;

public class LocateDeskSelectAdapter extends RecyclerView.Adapter<LocateDeskSelectAdapter.LocateDeskSelectViewHolder>  {


    Context context;
    List<DeskStatusChangeAndSelect> deskStatusChangeAndSelectList;
    OnDeskSelectListener onDeskSelectListener;


    public interface  OnDeskSelectListener{

        public void onDeskSelectAndChange(DeskStatusChangeAndSelect deskStatusChangeAndSelect);

    }


    public LocateDeskSelectAdapter(Context context, List<DeskStatusChangeAndSelect> deskStatusChangeAndSelectList,OnDeskSelectListener onDeskSelectListener) {

        this.context=context;
        this.deskStatusChangeAndSelectList=deskStatusChangeAndSelectList;
        this.onDeskSelectListener=onDeskSelectListener;

    }

    @NonNull
    @Override
    public LocateDeskSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_desk_select_adapter, parent, false);
        return new LocateDeskSelectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocateDeskSelectViewHolder holder, int position) {


        holder.deskLocation.setVisibility(View.GONE);
        holder.locateCapacityBlockInDesk.setVisibility(View.GONE);
        holder.chipGroup.setVisibility(View.GONE);
        holder.tvDescriptionInChangeBlocks.setVisibility(View.GONE);

        holder.deskName.setText(deskStatusChangeAndSelectList.get(position).getDeskName());

        if(deskStatusChangeAndSelectList.get(position).getStatus()==0){
            holder.statusText.setText("Unavaliable");
            holder.user_status.setImageDrawable(context.getDrawable(R.drawable.status_unavaliable));
        }else if(deskStatusChangeAndSelectList.get(position).getStatus()==1){
            holder.statusText.setText("Aavaliable");
            holder.user_status.setImageDrawable(context.getDrawable(R.drawable.green_dot));
        }else if(deskStatusChangeAndSelectList.get(position).getStatus()==2){
            holder.statusText.setText("BookedByMe");
            holder.user_status.setImageDrawable(context.getDrawable(R.drawable.status_booked_byme));
        }else if(deskStatusChangeAndSelectList.get(position).getStatus()==3) {
            holder.statusText.setText("BookedByOther");
            holder.user_status.setImageDrawable(context.getDrawable(R.drawable.status_booked));
        }else if(deskStatusChangeAndSelectList.get(position).getStatus()==4) {
            holder.statusText.setText("Request");
            holder.user_status.setImageDrawable(context.getDrawable(R.drawable.byrequest));
        }

        holder.selectDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeskSelectListener.onDeskSelectAndChange(deskStatusChangeAndSelectList.get(holder.getAbsoluteAdapterPosition()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return deskStatusChangeAndSelectList.size();
    }

    public class LocateDeskSelectViewHolder extends RecyclerView.ViewHolder{

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

        public LocateDeskSelectViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
