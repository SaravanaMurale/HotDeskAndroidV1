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
import dream.guys.hotdeskandroid.model.response.CarParkListToEditResponse;
import dream.guys.hotdeskandroid.utils.Utils;

public class CarListToEditAdapter extends RecyclerView.Adapter<CarListToEditAdapter.CarListToEditViewHolder> {

    Context context;
    List<CarParkListToEditResponse> carParkBookings;
    public CarEditClickable carEditClickable;
    private String code;

    public   interface CarEditClickable{
        public void onCarEditClick(CarParkListToEditResponse carParkBooking);
        public void onCarDeleteClick(CarParkListToEditResponse carParkBooking);
    }


    public CarListToEditAdapter(Context context, List<CarParkListToEditResponse> carParkBookings, CarEditClickable carEditClickable, String code) {

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

        if(code.equals("5")){
            holder.editBookingImage.setImageDrawable(context.getDrawable(R.drawable.car));
        }

        holder.editCode.setText(carParkBookings.get(position).getVehicleRegNumber());
        holder.editCheckInTime.setText(Utils.splitTime(carParkBookings.get(position).getFrom()));
        holder.editCheckOutTime.setText(Utils.splitTime(carParkBookings.get(position).getMyto()));

        holder.editTextEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carEditClickable.onCarEditClick(carParkBookings.get(holder.getAbsoluteAdapterPosition()));
            }
        });
        holder.editDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carEditClickable.onCarDeleteClick(carParkBookings.get(holder.getAbsoluteAdapterPosition()));
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



        public CarListToEditViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
