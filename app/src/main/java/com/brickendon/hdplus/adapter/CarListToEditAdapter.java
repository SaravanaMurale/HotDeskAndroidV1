package com.brickendon.hdplus.adapter;

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
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.model.response.CarParkingForEditResponse;
import com.brickendon.hdplus.utils.Utils;

public class CarListToEditAdapter extends RecyclerView.Adapter<CarListToEditAdapter.CarListToEditViewHolder> {

    Context context;
    List<CarParkingForEditResponse.CarParkBooking> carParkBookings;
    public CarEditClickable carEditClickable;
    private String code;
    private String selectedCode;

    public  interface CarEditClickable{
        public void onCarEditClick(CarParkingForEditResponse.CarParkBooking carParkBooking, String selectedCode);
        public void onCarDeleteClick(CarParkingForEditResponse.CarParkBooking carParkBooking);
    }


    public CarListToEditAdapter(Context context, List<CarParkingForEditResponse.CarParkBooking> carParkBookings, CarEditClickable carEditClickable, String code,String selectedCode) {

        this.context=context;
        this.carParkBookings=carParkBookings;
        this.carEditClickable=carEditClickable;
        this.code=code;
        this.selectedCode=selectedCode;

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
            holder.editBookingImage.setImageDrawable(context.getDrawable(R.drawable.car_edit));
        }

        holder.editCode.setText(selectedCode);
        holder.editCheckInTime.setText(Utils.splitTime(carParkBookings.get(position).getFrom()));
        holder.editCheckOutTime.setText(Utils.splitTime(carParkBookings.get(position).getMyto()));

        holder.editTextEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carEditClickable.onCarEditClick(carParkBookings.get(holder.getAbsoluteAdapterPosition()),selectedCode);
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
