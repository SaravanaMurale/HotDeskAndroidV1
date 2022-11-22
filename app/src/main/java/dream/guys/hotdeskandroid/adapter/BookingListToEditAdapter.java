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
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.utils.Utils;

public class BookingListToEditAdapter extends RecyclerView.Adapter<BookingListToEditAdapter.BookingListToEditViewHolder> {

    Context context;
    List<BookingForEditResponse.Bookings> bookingsListToEdit;
    List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities;
    OnEditClickable onEditClickable;
    String code;

    //For Language
    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage ;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata ;
    LanguagePOJO.Global global;


    public interface OnEditClickable{
        public void onEditClick(BookingForEditResponse.Bookings bookings, String code, List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities);
        public void ondeskDeleteClick(BookingForEditResponse.Bookings bookings, String code, List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities);
    }


    public BookingListToEditAdapter(Context context,
                                    List<BookingForEditResponse.Bookings> bookingsListToEdit,
                                    OnEditClickable onEditClickable, String code, List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities) {

        this.context=context;
        this.bookingsListToEdit=bookingsListToEdit;
        this.onEditClickable=onEditClickable;
        this.code=code;
        this.teamDeskAvailabilities=teamDeskAvailabilities;

    }

    @NonNull
    @Override
    public BookingListToEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_booking_edit_adapter, parent, false);
        return new BookingListToEditViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingListToEditViewHolder holder, int position) {
        holder.editCheckInIcon.setVisibility(View.GONE);
        holder.editCheckOutIcon.setVisibility(View.GONE);
        holder.editCheckInTime.setVisibility(View.GONE);
        holder.editCheckOutTime.setVisibility(View.GONE);


        if (bookingsListToEdit.get(position).getStatus().getTimeStatus()
                .equalsIgnoreCase("future")){
            holder.editDelete.setVisibility(View.VISIBLE);
            holder.editTextEdit.setVisibility(View.VISIBLE);
        } else if (bookingsListToEdit.get(position).getStatus().getTimeStatus()
                .equalsIgnoreCase("ongoing")) {
            holder.editDelete.setVisibility(View.GONE);
            holder.editTextEdit.setVisibility(View.VISIBLE);
        } else if (bookingsListToEdit.get(position).getStatus().getTimeStatus()
                .equalsIgnoreCase("past")) {
            holder.editDelete.setVisibility(View.GONE);
            holder.editTextEdit.setVisibility(View.GONE);
        }

        if (bookingsListToEdit.get(position).getStatus().getBookingStatus()
                .equalsIgnoreCase("none"))
            holder.editDelete.setVisibility(View.VISIBLE);


        if(code.equals("3")){
            holder.editBookingImage.setImageDrawable(context.getDrawable(R.drawable.chair));
        }else if(code.equals("4")){
            holder.editBookingImage.setImageDrawable(context.getDrawable(R.drawable.room));
        }else if(code.equals("5")){
            holder.editBookingImage.setImageDrawable(context.getDrawable(R.drawable.car));
        }

        switch (bookingsListToEdit.get(position).getUsageTypeId()){
            case 7:
                holder.editCode.setText("Request for Desk");
                for (int i=0;i<teamDeskAvailabilities.size();i++){
                    if (bookingsListToEdit.get(position).getRequestedTeamDeskId() == teamDeskAvailabilities.get(i).getTeamDeskId()){
                        System.out.println("teamdeskIF"+teamDeskAvailabilities.get(i).getDeskCode());
                        holder.editCode.setText(teamDeskAvailabilities.get(i).getDeskCode()
                                +"Request for Desk");
                    }
                }
                makeVisible(holder,position);
                break;
            case 9:
                holder.editCode.setText("Working from home");
                holder.editCheckInTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getFrom()));
                holder.editCheckOutTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getMyto()));
                holder.editCheckInTime.setVisibility(View.VISIBLE);
                holder.editCheckOutTime.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.editCode.setText("Working in alternative office");
                holder.editCheckInTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getFrom()));
                holder.editCheckOutTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getMyto()));
                holder.editCheckInTime.setVisibility(View.VISIBLE);
                holder.editCheckOutTime.setVisibility(View.VISIBLE);
                break;
            case 5:
                holder.editCode.setText("Not assigned to team");
                holder.editCheckInTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getFrom()));
                holder.editCheckOutTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getMyto()));
                holder.editCheckInTime.setVisibility(View.VISIBLE);
                holder.editCheckOutTime.setVisibility(View.VISIBLE);
                break;
            case 8:
                holder.editCode.setText("Training");
                holder.editCheckInTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getFrom()));
                holder.editCheckOutTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getMyto()));
                holder.editCheckInTime.setVisibility(View.VISIBLE);
                holder.editCheckOutTime.setVisibility(View.VISIBLE);
                break;
            case 6:
                holder.editCode.setText("Out of office");
                holder.editCheckInTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getFrom()));
                holder.editCheckOutTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getMyto()));
                holder.editCheckInTime.setVisibility(View.VISIBLE);
                holder.editCheckOutTime.setVisibility(View.VISIBLE);
                break;
            case 18:
                holder.editCode.setText("Sick Leave");
                holder.editCheckInTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getFrom()));
                holder.editCheckOutTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getMyto()));
                holder.editCheckInTime.setVisibility(View.VISIBLE);
                holder.editCheckOutTime.setVisibility(View.VISIBLE);

                break;
            default:



                /*holder.editCheckInIcon.setVisibility(View.VISIBLE);
                holder.editCheckOutIcon.setVisibility(View.VISIBLE);
                holder.editCheckInTime.setVisibility(View.VISIBLE);
                holder.editCheckOutTime.setVisibility(View.VISIBLE);

                holder.editCode.setText(bookingsListToEdit.get(position).getDeskCode());
                holder.editCheckInTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getFrom()));
                holder.editCheckOutTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getMyto()));*/
                holder.editCode.setText(bookingsListToEdit.get(position).getDeskCode());
                makeVisible(holder,position);

                break;

        }


//        holder.editCode.setText(bookingsListToEdit.get(position).getDeskCode());
//        holder.editCheckInTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getFrom()));
//        holder.editCheckOutTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getMyto()));

        holder.editTextEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditClickable.onEditClick(bookingsListToEdit.get(holder.getAbsoluteAdapterPosition()) ,code,teamDeskAvailabilities);

            }
        });
        holder.editDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onEditClickable.ondeskDeleteClick(bookingsListToEdit.get(holder.getAbsoluteAdapterPosition()),code,teamDeskAvailabilities);


            }
        });



    }

    private void makeVisible(BookingListToEditViewHolder holder, int position) {

        if (bookingsListToEdit.get(position).getStatus()!=null &&
                bookingsListToEdit.get(position).getStatus().getTimeStatus().equalsIgnoreCase("PAST")) {
            holder.editTextEdit.setVisibility(View.GONE);
            holder.editDelete.setVisibility(View.GONE);
        }else if (bookingsListToEdit.get(position).getStatus()!=null &&
                bookingsListToEdit.get(position).getStatus().getTimeStatus().equalsIgnoreCase("ONGOING")) {
            holder.editTextEdit.setVisibility(View.VISIBLE);
            holder.editDelete.setVisibility(View.GONE);
        }

        holder.editCheckInIcon.setVisibility(View.VISIBLE);
        holder.editCheckOutIcon.setVisibility(View.VISIBLE);
        holder.editCheckInTime.setVisibility(View.VISIBLE);
        holder.editCheckOutTime.setVisibility(View.VISIBLE);

        //holder.editCode.setText(bookingsListToEdit.get(position).getDeskCode());
        holder.editCheckInTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getFrom()));
        holder.editCheckOutTime.setText(Utils.splitTime(bookingsListToEdit.get(position).getMyto()));

    }

    @Override
    public int getItemCount() {
        return bookingsListToEdit.size();
    }

    public class BookingListToEditViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.editBookingImage)
        ImageView editBookingImage;
        @BindView(R.id.editCheckInIcon)
        ImageView editCheckInIcon;
        @BindView(R.id.editCheckOutIcon)
        ImageView editCheckOutIcon;
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


        public BookingListToEditViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


                logoinPage = Utils.getLoginScreenData(context);
                appKeysPage = Utils.getAppKeysPageScreenData(context);
                resetPage = Utils.getResetPasswordPageScreencreenData(context);
                actionOverLays = Utils.getActionOverLaysPageScreenData(context);
                bookindata = Utils.getBookingPageScreenData(context);
                global=Utils.getGlobalScreenData(context);

            editTextEdit.setText(global.getEdit());
            editDelete.setText(global.getDelete());




        }
    }

}
