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

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.language.LanguagePOJO;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.controllers.OtherBookingController;
import dream.guys.hotdeskandroid.utils.Utils;

public class OtherBookingAdapter extends RecyclerView.Adapter<OtherBookingAdapter.ViewHolder> {

    private Context context;
    private int selectedIcon;
    private List<BookingForEditResponse.Bookings> bookingsList;
    private OtherBookingController otherBookingController;
    private LanguagePOJO.AppKeys appKeysPage;

    public OtherBookingAdapter(Context context, int selectedIcon, List<BookingForEditResponse.Bookings> bookings,
                               OtherBookingController otherBookingController) {
        this.context = context;
        appKeysPage = Utils.getAppKeysPageScreenData(context);
        this.selectedIcon = selectedIcon;
        this.bookingsList = bookings;
        this.otherBookingController = otherBookingController;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_other_booking_item,
                parent, false));
    }

    /*4 == 9 - remote - WFH
         5 == 18 - sick leave
         6 ==  6 - holiday
         7 == 8 - training*/

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            if (selectedIcon == 4) {
                holder.bookTypeImage.setBackgroundResource(R.drawable.home);
                holder.tvBookType.setText(appKeysPage.getRemote());
            } else if (selectedIcon == 5) {
                holder.bookTypeImage.setBackgroundResource(R.drawable.sick_plus);
                holder.tvBookType.setText(appKeysPage.getSick());
            } else if (selectedIcon == 6) {
                holder.bookTypeImage.setBackgroundResource(R.drawable.plane);
                holder.tvBookType.setText(appKeysPage.getHoliday());
            } else if (selectedIcon == 7) {
                holder.bookTypeImage.setBackgroundResource(R.drawable.training_book);
                holder.tvBookType.setText(appKeysPage.getTraining());
            }

            holder.tvCheckInTime.setText(Utils.splitTime(bookingsList.get(position).getFrom()));
            holder.tvCheckOutTime.setText(Utils.splitTime(bookingsList.get(position).getMyto()));

            holder.editTextEdit.setOnClickListener(view -> {
                otherBookingController.openNewBookingSheet("Edit",
                        Utils.splitTime(bookingsList.get(position).getFrom()),
                        Utils.splitTime(bookingsList.get(position).getMyto()),
                        bookingsList.get(position).getId());
            });

            holder.editDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    otherBookingController.callDeleteBooking(bookingsList.get(holder.getAdapterPosition()).getId());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return bookingsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookTypeImage;
        TextView tvBookType;
        TextView tvCheckInTime;
        TextView tvCheckOutTime;
        TextView editDelete;
        TextView editTextEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTypeImage = itemView.findViewById(R.id.bookTypeImage);
            tvBookType = itemView.findViewById(R.id.tvBookType);
            tvCheckInTime = itemView.findViewById(R.id.tvCheckInTime);
            tvCheckOutTime = itemView.findViewById(R.id.tvCheckOutTime);
            editDelete = itemView.findViewById(R.id.editDelete);
            editTextEdit = itemView.findViewById(R.id.editTextEdit);

            editDelete.setText(appKeysPage.getDelete());
            editTextEdit.setText(appKeysPage.getEdit());

        }
    }
}
