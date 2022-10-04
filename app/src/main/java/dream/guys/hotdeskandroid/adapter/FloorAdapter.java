package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.ui.book.BookFragment;
import dream.guys.hotdeskandroid.ui.locate.LocateFragment;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;

public class FloorAdapter extends RecyclerView.Adapter<FloorAdapter.FloorAdapterViewHolder> {

    Context context;
    List<LocateCountryRespose> locateCountryResposeList;
    String identifier;

    int pos = -1;
    int sPos = -1;
    boolean selectedPosition = false;

    public FloorAdapter(Context context, List<LocateCountryRespose> locateCountryResposeList, LocateFragment locateFragment, String identifier) {
        this.context=context;
        this.locateCountryResposeList=locateCountryResposeList;
        this.identifier=identifier;

    }
    public FloorAdapter(Context context, List<LocateCountryRespose> locateCountryResposeList, BookFragment locateFragment, String identifier) {
        this.context=context;
        this.locateCountryResposeList=locateCountryResposeList;
        this.identifier=identifier;

    }

    @NonNull
    @Override
    public FloorAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_floor_adapter, parent, false);
        return new FloorAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FloorAdapterViewHolder holder, int position) {

        holder.floorName.setText(locateCountryResposeList.get(position).getName());

        holder.floorBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionHandler.getInstance().saveInt(context, AppConstants.FLOOR_POSITION,holder.getAbsoluteAdapterPosition());
                SessionHandler.getInstance().saveInt(context, AppConstants.LOCATION_ID,locateCountryResposeList.get(holder.getAbsoluteAdapterPosition()).getLocateCountryId());
                System.out.println("FloorPositionSaved");
                SessionHandler.getInstance().saveInt(context, AppConstants.FLOOR_POSITION, holder.getAbsoluteAdapterPosition());
                SessionHandler.getInstance().saveBoolean(context,AppConstants.FLOOR_SELECTED_STATUS,true);
                pos = holder.getAbsoluteAdapterPosition();
                notifyDataSetChanged();

            }
        });

        if (pos == position) {
            //locateCountryResposeList.get(holder.getAbsoluteAdapterPosition());
            //SessionHandler.getInstance().saveInt(context, AppConstants.FLOOR_POSITION,holder.getAbsoluteAdapterPosition());

            if (pos >= sPos && sPos == pos) {
                SessionHandler.getInstance().remove(context, AppConstants.FLOOR_POSITION);
                SessionHandler.getInstance().saveBoolean(context,AppConstants.FLOOR_SELECTED_STATUS,false);
                holder.ivFloor.setImageDrawable(context.getDrawable(R.drawable.floor_disable));
                sPos = -1;
            } else {

                holder.ivFloor.setImageDrawable(context.getDrawable(R.drawable.floor_enable));
                SessionHandler.getInstance().saveBoolean(context,AppConstants.FLOOR_SELECTED_STATUS,true);
                SessionHandler.getInstance().saveInt(context, AppConstants.FLOOR_POSITION, holder.getAbsoluteAdapterPosition());
                SessionHandler.getInstance().saveInt(context, AppConstants.LOCATION_ID,locateCountryResposeList.get(holder.getAbsoluteAdapterPosition()).getLocateCountryId());
                sPos=pos;
            }

        } else {
            //locateCountryResposeList.get(holder.getAbsoluteAdapterPosition());
            //SessionHandler.getInstance().saveInt(context, AppConstants.NEW_FLOOR_POSITION,holder.getAbsoluteAdapterPosition());
            SessionHandler.getInstance().remove(context, AppConstants.FLOOR_POSITION);
            SessionHandler.getInstance().saveBoolean(context,AppConstants.FLOOR_SELECTED_STATUS,false);
            holder.ivFloor.setImageDrawable(context.getDrawable(R.drawable.floor_disable));
        }
    }

    @Override
    public int getItemCount() {
        return locateCountryResposeList.size();
    }

    public class FloorAdapterViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.floorBlock)
        RelativeLayout floorBlock;
        @BindView(R.id.ivLocateFloor)
        ImageView ivFloor;
        @BindView(R.id.locateFloorName)
        TextView floorName;

        public FloorAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
