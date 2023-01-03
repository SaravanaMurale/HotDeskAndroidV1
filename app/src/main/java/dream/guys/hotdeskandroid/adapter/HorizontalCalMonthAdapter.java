package dream.guys.hotdeskandroid.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.HorizontalCalendarModel;
import dream.guys.hotdeskandroid.ui.teams.TeamsFragment;
import dream.guys.hotdeskandroid.utils.Utils;

public class HorizontalCalMonthAdapter extends RecyclerView.Adapter<HorizontalCalMonthAdapter.ViewHolder> {
    Activity activityContext;
    TeamsFragment fragment;
    ArrayList<HorizontalCalendarModel> horizontalCalendarModels;
    int oldSelectedPos=0,newSelectedPos=0;
    CalendarSelectedInterface selectedInterface;
    public HorizontalCalMonthAdapter(ArrayList<HorizontalCalendarModel> horizontalCalendarModels,
                                     Activity activityContext, TeamsFragment teamsFragment, CalendarSelectedInterface calendarSelectedInterface) {
        this.horizontalCalendarModels = horizontalCalendarModels;
        this.activityContext = activityContext;
        this.fragment = teamsFragment;
        this.selectedInterface = calendarSelectedInterface;
    }

    public interface CalendarSelectedInterface{
        public void calendarSelectedDate(String date,int oldSelectedPos,int newSelectedPos);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cal_month_item, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.day.setText(Utils.checkStringParms(horizontalCalendarModels.get(position).getYear()));

        if (horizontalCalendarModels.get(position).isSelected()){
            holder.month.setText(Utils.checkStringParms(horizontalCalendarModels.get(position).getMonth()));
            oldSelectedPos = holder.getAbsoluteAdapterPosition();
            updateSelectedColor(holder);
        } else {
            holder.month.setText(Utils.checkStringParms(horizontalCalendarModels.get(position).getShortMonth()));
            updateNotSelectedColor(holder);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newSelectedPos = holder.getAbsoluteAdapterPosition();
                selectedInterface.calendarSelectedDate(horizontalCalendarModels.get(holder.getAbsoluteAdapterPosition()).getDate(),
                        oldSelectedPos,newSelectedPos);
            }
        });
    }

    private void updateSelectedColor(ViewHolder holder) {
        holder.dateLayout.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figmaBlue));
        holder.day.setTextColor(ContextCompat.getColorStateList(activityContext,R.color.white));
        holder.month.setTextColor(ContextCompat.getColorStateList(activityContext,R.color.white));
    }

    private void updateNotSelectedColor(ViewHolder holder) {
        holder.dateLayout.setBackgroundTintList(ContextCompat.getColorStateList(activityContext,R.color.figmaBackground));
        holder.day.setTextColor(ContextCompat.getColorStateList(activityContext,R.color.figmaBlack));
        holder.month.setTextColor(ContextCompat.getColorStateList(activityContext,R.color.figmaBlack));
    }

    @Override
    public int getItemCount() {
        return horizontalCalendarModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.top_layout)
        LinearLayout dateLayout;
        @BindView(R.id.month)
        TextView month;
        @BindView(R.id.day)
        TextView day;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}

