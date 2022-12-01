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
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;
import dream.guys.hotdeskandroid.utils.Utils;

public class MeetingListToEditAdapter extends RecyclerView.Adapter<MeetingListToEditAdapter.MeetingListViewHolder> {

    Context context;
    List<MeetingListToEditResponse> meetingListToEditResponseList;
    OnMeetingEditClickable onMeetingEditClickable;

    //For Language
    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage ;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata ;
    LanguagePOJO.Global global;

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
    public MeetingListViewHolder

    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_booking_edit_adapter, parent, false);
        return new MeetingListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingListViewHolder holder, int position) {
        if (meetingListToEditResponseList.get(position).getStatus().getTimeStatus()
                .equalsIgnoreCase("future")){
            holder.editDelete.setVisibility(View.VISIBLE);
            holder.editTextEdit.setVisibility(View.VISIBLE);
        } else if (meetingListToEditResponseList.get(position).getStatus().getTimeStatus()
                .equalsIgnoreCase("ongoing")) {
            holder.editDelete.setVisibility(View.VISIBLE);
            holder.editTextEdit.setVisibility(View.VISIBLE);

            if (meetingListToEditResponseList.get(position)
                    .getStatus().getBookingStatus()
                    .equalsIgnoreCase("None")) {
                holder.editDelete.setVisibility(View.VISIBLE);
            } else {
                holder.editDelete.setVisibility(View.GONE);
            }
        } else if (meetingListToEditResponseList.get(position).getStatus().getTimeStatus()
                .equalsIgnoreCase("past")) {
            holder.editDelete.setVisibility(View.GONE);
            holder.editTextEdit.setVisibility(View.GONE);
        }

        holder.editCode.setText((meetingListToEditResponseList.get(position).getMeetingRoomName()));
        holder.editCheckInTime.setText(Utils.splitTime(meetingListToEditResponseList.get(position).getFrom()));
        holder.editCheckOutTime.setText(Utils.splitTime(meetingListToEditResponseList.get(position).getTo()));
        holder.editBookingImage.setImageDrawable(context.getDrawable(R.drawable.room));
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

                logoinPage = Utils.getLoginScreenData(context);
                appKeysPage = Utils.getAppKeysPageScreenData(context);
                resetPage = Utils.getResetPasswordPageScreencreenData(context);
                actionOverLays = Utils.getActionOverLaysPageScreenData(context);
                bookindata = Utils.getBookingPageScreenData(context);
                global=Utils.getGlobalScreenData(context);

            editDelete.setText(global.getDelete());
            editTextEdit.setText(global.getEdit());



        }
    }
}
