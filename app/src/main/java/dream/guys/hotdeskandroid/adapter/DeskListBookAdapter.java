package dream.guys.hotdeskandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.controllers.EditDeskController;
import dream.guys.hotdeskandroid.model.request.EditBookingDetails;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;

public class DeskListBookAdapter extends RecyclerView.Adapter<DeskListBookAdapter.viewholder> implements Filterable {
    Context context;
    Activity activity;
    EditDeskController fragment;
    List<BookingForEditResponse.TeamDeskAvailabilities> deskList;
    List<BookingForEditResponse.TeamDeskAvailabilities> deskListAll;
    public OnChangeSelected onChangeSelected;
    BottomSheetDialog bottomSheetDialog;
    EditBookingDetails editBookingDetails;
    int typeId;
    String newEditStatus;
    public DeskListBookAdapter(Context context, OnChangeSelected onSelectSelected, Activity activity,
                                      List<BookingForEditResponse.TeamDeskAvailabilities> bookingForEditResponse,
                                      EditDeskController context1, BottomSheetDialog bottomSheetDialog,
                                      int typeId, EditBookingDetails editBookingDetails,String newEditStatus) {
        this.fragment=context1;
        this.context = context;
        this.onChangeSelected =onSelectSelected;
        this.activity = activity;
        this.deskList = bookingForEditResponse;
        this.deskListAll = new ArrayList<>(bookingForEditResponse);
        this.bottomSheetDialog=bottomSheetDialog;
        this.typeId = typeId;
        this.editBookingDetails = editBookingDetails;
        this.newEditStatus=newEditStatus;
    }
    public interface OnChangeSelected{
        public void onChangeDesk(BookingForEditResponse.TeamDeskAvailabilities deskList,int deskId, String deskName, String request,
                                 String timeZone,int typeId,
                                 EditBookingDetails editBookingDetails,String newEditStatus,int teamId);

    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_desk_room_list_recycler_layout, parent, false);
        return new viewholder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
//        fragment.selectedTeamId
        holder.desk_name.setText(deskList.get(position).getDeskCode());
        holder.tvLocationAddress.setText(Utils.checkStringParms(deskList.get(position).getLocationDetails().getBuildingName())+
                ", "+
                Utils.checkStringParms(deskList.get(position).getLocationDetails().getfLoorName()));
        holder.tvDescription.setText(Utils.checkStringParms(deskList.get(position).getDescription()));

        if (deskList.get(position).getTeamId() != SessionHandler.getInstance()
                .getInt(context, AppConstants.TEAM_ID)
                && deskList.get(position).getAutomaticApprovalStatus() == 3){
            holder.card.setBackgroundColor(ContextCompat.getColor(activity,R.color.white));
            holder.select.setVisibility(View.GONE);
//            System.out.println("chec stats adapter "+fragment.selectedTeamAutoApproveStatus);

            holder.deskStatus.setText("Not Available For Request");
            holder.deskIconStatus.setColorFilter(context.getColor(R.color.figma_red));
        } else if (deskList.get(position).getTeamId() != SessionHandler.getInstance()
                .getInt(context, AppConstants.TEAM_ID)
                && deskList.get(position).getAutomaticApprovalStatus() != 2) {
            holder.card.setBackgroundColor(ContextCompat.getColor(activity,R.color.white));
            holder.select.setVisibility(View.VISIBLE);
//            System.out.println("chec stats adapter "+fragment.selectedTeamAutoApproveStatus);

            holder.deskStatus.setText("Available For Request");
            holder.deskIconStatus.setColorFilter(context.getColor(R.color.figma_orange));
        } else {
            holder.card.setBackgroundColor(ContextCompat.getColor(activity,R.color.white));
            holder.select.setVisibility(View.VISIBLE);

            holder.deskStatus.setText("Available");
            holder.deskIconStatus.setColorFilter(context.getColor(R.color.figmaLiteGreen));
        }

        if (deskList.get(position).isBookedByElse()) {

            if(Utils.compareTwoDatesandTime(Utils.getCurrentDate()+"T"+Utils.getCurrentTime()+":00Z",
                    deskList.get(position).getAvailableTimeSlots()
                            .get(deskList.get(position).getAvailableTimeSlots().size() - 1)
                            .getFrom())==1){
                holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.figmaBgGrey));
                holder.select.setVisibility(View.GONE);
                holder.deskStatus.setText("Booked by Other");
                holder.deskIconStatus.setColorFilter(context.getColor(R.color.figmaGrey));

            } else if (deskList.get(position).getTeamId() != SessionHandler.getInstance()
                    .getInt(context, AppConstants.TEAM_ID)
                    && deskList.get(position).getAutomaticApprovalStatus() != 2){
                holder.card.setBackgroundColor(ContextCompat.getColor(activity,R.color.white));
                holder.select.setVisibility(View.VISIBLE);

                holder.deskStatus.setText("Available For Request");
                holder.deskIconStatus.setColorFilter(context.getColor(R.color.figma_orange));
            } else {
                holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                holder.select.setVisibility(View.VISIBLE);

                holder.deskStatus.setText("Available");
                holder.deskIconStatus.setColorFilter(context.getColor(R.color.figmaLiteGreen));
            }

        }

        if (deskList.get(position).isBookedByUser()) {
            holder.card.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.select.setVisibility(View.VISIBLE);
            holder.deskStatus.setText("Booked by me");

            holder.deskIconStatus.setColorFilter(context.getColor(R.color.figmaBlue));
        }
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.deskStatus.getText().toString().equalsIgnoreCase("Available For Request")){
                    onChangeSelected.onChangeDesk(deskList.get(holder.getAbsoluteAdapterPosition()),
                            deskList.get(holder.getAbsoluteAdapterPosition()).getTeamDeskId(),
                            deskList.get(holder.getAbsoluteAdapterPosition()).getDeskCode(),"request"
                            ,deskList.get(holder.getAbsoluteAdapterPosition()).getTimeZones().get(0).getTimeZoneId(),
                            typeId,editBookingDetails,newEditStatus,deskList.get(holder.getAbsoluteAdapterPosition()).getTeamId());
                } else if(holder.deskStatus.getText().toString().equalsIgnoreCase("Available")){
                    onChangeSelected.onChangeDesk(deskList.get(holder.getAbsoluteAdapterPosition()),
                            deskList.get(holder.getAbsoluteAdapterPosition()).getTeamDeskId(),
                            deskList.get(holder.getAbsoluteAdapterPosition()).getDeskCode(),"new"
                            , deskList.get(holder.getAbsoluteAdapterPosition()).getTimeZones().get(0).getTimeZoneId(),
                            typeId,editBookingDetails,newEditStatus,deskList.get(holder.getAbsoluteAdapterPosition()).getTeamId());
                } else {
                    if (deskList.get(holder.getAbsoluteAdapterPosition()).getTeamId() != SessionHandler.getInstance()
                            .getInt(context, AppConstants.TEAM_ID)
                            && deskList.get(position).getAutomaticApprovalStatus() != 2)
                    {
                        onChangeSelected.onChangeDesk(deskList.get(holder.getAbsoluteAdapterPosition()),
                                deskList.get(holder.getAbsoluteAdapterPosition()).getTeamDeskId(),
                                deskList.get(holder.getAbsoluteAdapterPosition()).getDeskCode(),"request"
                                , deskList.get(holder.getAbsoluteAdapterPosition()).getTimeZones().get(0).getTimeZoneId(),
                                typeId,editBookingDetails,newEditStatus,deskList.get(holder.getAbsoluteAdapterPosition()).getTeamId());
                    } else {
                        onChangeSelected.onChangeDesk(deskList.get(holder.getAbsoluteAdapterPosition()),
                                deskList.get(holder.getAbsoluteAdapterPosition()).getTeamDeskId(),
                                deskList.get(holder.getAbsoluteAdapterPosition()).getDeskCode(),"new",
                                deskList.get(holder.getAbsoluteAdapterPosition()).getTimeZones().get(0).getTimeZoneId(),
                                typeId,editBookingDetails,newEditStatus,deskList.get(holder.getAbsoluteAdapterPosition()).getTeamId());
                    }
                }
                bottomSheetDialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return deskList.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<BookingForEditResponse.TeamDeskAvailabilities> filteredList=new ArrayList<>();

            if(constraint==null || constraint.toString().isEmpty() || constraint.length()==0 || constraint==""){
                filteredList.addAll(deskListAll);
            }else {
                String filterPattern=constraint.toString().toLowerCase().trim();

                for (BookingForEditResponse.TeamDeskAvailabilities dskl:deskListAll){

                    if(dskl.getDeskCode().toLowerCase().contains(filterPattern)){

                        filteredList.add(dskl);

                    }

                }

            }

            FilterResults results=new FilterResults();
            results.values=filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            deskList.clear();
            deskList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };


    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.card)
        CardView card;
        @BindView(R.id.tv_desk_name)
        TextView desk_name;
        @BindView(R.id.tv_select)
        TextView select;
        @BindView(R.id.desk_status)
        TextView deskStatus;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_location_address)
        TextView tvLocationAddress;
        @BindView(R.id.desk_icon_status)
        ImageView deskIconStatus;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
