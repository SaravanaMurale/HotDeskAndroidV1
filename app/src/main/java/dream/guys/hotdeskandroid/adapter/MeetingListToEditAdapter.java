package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;
import dream.guys.hotdeskandroid.utils.Utils;

public class MeetingListToEditAdapter extends RecyclerView.Adapter<MeetingListToEditAdapter.MeetingListViewHolder> {

    Context context;
    List<MeetingListToEditResponse> meetingListToEditResponseList;
    OnMeetingEditClickable onMeetingEditClickable;
    public interface OnMeetingEditClickable{
        public void onMeetingEditClick(MeetingListToEditResponse meetingListToEditResponse);
        public void onMeetingDeleteClick(MeetingListToEditResponse meetingListToEditResponse);

    }

    public MeetingListToEditAdapter(Context context, List<MeetingListToEditResponse> meetingListToEditResponseList,OnMeetingEditClickable onMeetingEditClickable) {


        this.context=context;
        this.meetingListToEditResponseList=meetingListToEditResponseList;
        this.onMeetingEditClickable=onMeetingEditClickable;

    }

    @NonNull
    @Override
    public MeetingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_booking_edit_adapter, parent, false);
        return new MeetingListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingListViewHolder holder, int position) {

        holder.editCode.setText((meetingListToEditResponseList.get(position).getMeetingRoomName()));
        holder.editCheckInTime.setText(Utils.splitTime(meetingListToEditResponseList.get(position).getFrom()));
        holder.editCheckOutTime.setText(Utils.splitTime(meetingListToEditResponseList.get(position).getTo()));

        holder.editTextEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onMeetingEditClickable.onMeetingEditClick(meetingListToEditResponseList.get(holder.getAbsoluteAdapterPosition()));

            }
        });
        holder.editDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onMeetingEditClickable.onMeetingDeleteClick(meetingListToEditResponseList.get(holder.getAbsoluteAdapterPosition()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return meetingListToEditResponseList.size();
    }

    public class MeetingListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.editBookingImage)
        ImageView editBookingImage;
        @BindView(R.id.editCode)
        TextView editCode;
        @BindView(R.id.editCheckOutTime)
        TextView editCheckOutTime;
        @BindView(R.id.editCheckInTime)
        TextView editCheckInTime;
        @BindView(R.id.editDelete)
        TextView editDelete;
        @BindView(R.id.editTextEdit)
        TextView editTextEdit;


        public MeetingListViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
