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
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.response.CarParkListToEditResponse;
import dream.guys.hotdeskandroid.utils.Utils;

public class CarListToEditAdapterBooking extends RecyclerView.Adapter<CarListToEditAdapterBooking.CarListToEditViewHolder>  {


    LanguagePOJO.Login logoinPage;
    LanguagePOJO.AppKeys appKeysPage;
    LanguagePOJO.ResetPassword resetPage ;
    LanguagePOJO.ActionOverLays actionOverLays;
    LanguagePOJO.Booking bookindata ;
    LanguagePOJO.Global global;
    LanguagePOJO.MeetingRooms meetingRoomsLanguage;

    Context context;
    List<CarParkListToEditResponse> carParkBookings;
    public CarEditClickableBooking carEditClickable;
    private String code;


    public  interface CarEditClickableBooking{
        public void onCarEditClicks(CarParkListToEditResponse carParkBooking);
        public void onCarDeleteClicks(CarParkListToEditResponse carParkBooking);
    }


    public CarListToEditAdapterBooking(Context context,
                                       List<CarParkListToEditResponse> carParkBookings,
                                       CarEditClickableBooking carEditClickable, String code) {
        this.context=context;
        this.carParkBookings=carParkBookings;
        this.carEditClickable=carEditClickable;
        this.code=code;
    }

    @NonNull
    @Override
    public CarListToEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //dialog_locate_car_park_edit_booking_bottomsheet
        //layout_booking_edit_adapter

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_booking_edit_adapter, parent, false);
        return new CarListToEditViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarListToEditViewHolder holder, int position) {
        if (carParkBookings.get(position).getStatus().getTimeStatus()
                .equalsIgnoreCase("future")){
            holder.editDelete.setVisibility(View.VISIBLE);
            holder.editTextEdit.setVisibility(View.VISIBLE);
            holder.rlBottonm.setVisibility(View.VISIBLE);
        } else if (carParkBookings.get(position).getStatus().getTimeStatus()
                .equalsIgnoreCase("ongoing")) {
            holder.editDelete.setVisibility(View.VISIBLE);
            holder.editTextEdit.setVisibility(View.VISIBLE);
            holder.rlBottonm.setVisibility(View.VISIBLE);

            if (carParkBookings.get(position).getStatus().getBookingStatus()
                    .equalsIgnoreCase("None")) {
                holder.editDelete.setVisibility(View.VISIBLE);
            } else {
                holder.editDelete.setVisibility(View.GONE);
            }
        } else if (carParkBookings.get(position).getStatus().getTimeStatus()
                .equalsIgnoreCase("past")) {
            holder.editDelete.setVisibility(View.GONE);
            holder.editTextEdit.setVisibility(View.GONE);
            holder.rlBottonm.setVisibility(View.GONE);
            
        }
        if (carParkBookings.get(position).getStatus().getBookingType()!=null
                && carParkBookings.get(position).getStatus().getBookingType().equalsIgnoreCase("req")) {
            holder.editDelete.setVisibility(View.GONE);
            holder.editTextEdit.setVisibility(View.GONE);
            holder.rlBottonm.setVisibility(View.GONE);
        }


        if(code.equals("5")){
            holder.editBookingImage.setImageDrawable(context.getDrawable(R.drawable.car));
        }

        holder.editCode.setText(carParkBookings.get(position).getParkingSlotName());
        holder.editCheckInTime.setText(Utils.splitTime(carParkBookings.get(position).getFrom()));
        holder.editCheckOutTime.setText(Utils.splitTime(carParkBookings.get(position).getMyto()));

        holder.editTextEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carEditClickable.onCarEditClicks(carParkBookings.get(holder.getAbsoluteAdapterPosition()));
            }
        });
        holder.editDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carEditClickable.onCarDeleteClicks(carParkBookings.get(holder.getAbsoluteAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return carParkBookings.size();
    }

    public class CarListToEditViewHolder extends RecyclerView.ViewHolder{

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
        @BindView(R.id.bottom)
        RelativeLayout rlBottonm;


        public CarListToEditViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


            logoinPage = Utils.getLoginScreenData(context);
            appKeysPage = Utils.getAppKeysPageScreenData(context);
            resetPage = Utils.getResetPasswordPageScreencreenData(context);
            actionOverLays = Utils.getActionOverLaysPageScreenData(context);
            bookindata = Utils.getBookingPageScreenData(context);
            global=Utils.getGlobalScreenData(context);
            meetingRoomsLanguage=Utils.getMeetingRoomsPageScreenData(context);

            editDelete.setText(appKeysPage.getDelete());
            editTextEdit.setText(appKeysPage.getEdit());

        }
    }


}
