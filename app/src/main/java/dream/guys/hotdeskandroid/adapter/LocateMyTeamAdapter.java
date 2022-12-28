package dream.guys.hotdeskandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.model.response.DAOTeamMember;
import dream.guys.hotdeskandroid.utils.Utils;

public class LocateMyTeamAdapter extends RecyclerView.Adapter<LocateMyTeamAdapter.LocateMyTeamViewHolder> implements Filterable {

    Context context;
    List<DAOTeamMember> daoTeamMemberList;
    ShowMyTeamLocationClickable showMyTeamLocationClickable;

    //ForFilter
    List<DAOTeamMember> daoTeamMemberListAll;



    public interface ShowMyTeamLocationClickable {
        public void showMyTeamLocation(DAOTeamMember.DayGroup.CalendarEntry dayGroups, String name, boolean ifFirstAidStatus, boolean fireStatus, String profileImage);

        public void loadMyTeamLocation(int id, int floorID);
    }


    public LocateMyTeamAdapter(Context context, List<DAOTeamMember> stringName, ShowMyTeamLocationClickable showMyTeamLocationClickable) {
        this.context = context;
        this.daoTeamMemberList = stringName;
        this.showMyTeamLocationClickable = showMyTeamLocationClickable;
        this.daoTeamMemberListAll = new ArrayList<>(daoTeamMemberList);


    }

    @NonNull
    @Override
    public LocateMyTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_locate_myteam_adaper, parent, false);
        return new LocateMyTeamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocateMyTeamViewHolder holder, int position) {

        holder.locateMyTeamName.setText(daoTeamMemberList.get(position).getFirstName() + " " + daoTeamMemberList.get(position).getLastName());


        //Enable FirstAid
        if(daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).isIfFirstAidStatus()){
            holder.locateMyTeamPlusIv.setVisibility(View.VISIBLE);
        }else {
            holder.locateMyTeamPlusIv.setVisibility(View.GONE);
        }

        //Enable FireWarnds
        if(daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).isFireStatus()){
            holder.locateMyTeamFireIv.setVisibility(View.VISIBLE);
        }else {
            holder.locateMyTeamFireIv.setVisibility(View.GONE);
        }


        //To set Image here
           if(daoTeamMemberList.get(position).getProfileImage()!=null && !daoTeamMemberList.get(position).getProfileImage().isEmpty()) {

                    System.out.println("ImageSetHere ");
                    holder.myTeamProfileImage.setVisibility(View.VISIBLE);
                    String base64String = daoTeamMemberList.get(position).getProfileImage();
                    String base64Image = base64String.split(",")[1];
                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

               Glide.with(context)
                       .load(decodedByte)
                       .placeholder(R.drawable.avatar)
                       .into(holder.myTeamProfileImage);
                }else{
               Glide.with(context)
                       .load(R.drawable.avatar)
                       .placeholder(R.drawable.avatar)
                       .into(holder.myTeamProfileImage);
           }


        if (daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups()==null || daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().size() == 0) {
            //holder.locateMyTeamDeskName.setText("No Booking Avaliable");
            holder.locateMyTeamCheckInTime.setVisibility(View.INVISIBLE);
            holder.locateMyTeamCheckOutTime.setVisibility(View.INVISIBLE);
            holder.locateMyTeamLocation.setVisibility(View.INVISIBLE);
            holder.locateMyTeamNameCheckInIcon.setVisibility(View.INVISIBLE);
            holder.locateMyTeamCheckOutIcon.setVisibility(View.INVISIBLE);
            holder.locateMyTeamDeskName.setVisibility(View.INVISIBLE);
        } else {

            if (daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().get(0).getCalendarEntriesModel() != null){
                if (daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().get(0).getCalendarEntriesModel().getBooking() != null) {
                    //setVivisbleItems(holder);

                    holder.locateMyTeamLocation.setVisibility(View.VISIBLE);
                    holder.locateMyTeamNameCheckInIcon.setVisibility(View.VISIBLE);
                    holder.locateMyTeamCheckOutIcon.setVisibility(View.VISIBLE);
                    holder.locateMyTeamCheckInTime.setVisibility(View.VISIBLE);
                    holder.locateMyTeamCheckOutTime.setVisibility(View.VISIBLE);
                    holder.locateMyTeamDeskName.setVisibility(View.VISIBLE);

                    holder.locateMyTeamDeskName.setText(daoTeamMemberList.get(position).getDayGroups().get(0).getCalendarEntriesModel().getBooking().getDeskCode());
                    holder.locateMyTeamCheckInTime.setText(Utils.splitTime(daoTeamMemberList.get(position).getDayGroups().get(0).getCalendarEntriesModel().getFrom()));
                    holder.locateMyTeamCheckOutTime.setText(Utils.splitTime(daoTeamMemberList.get(position).getDayGroups().get(0).getCalendarEntriesModel().getMyto()));

                } else if (daoTeamMemberList.get(position).getDayGroups().get(0).getCalendarEntriesModel().getBooking() == null) {


                    holder.locateMyTeamDeskName.setVisibility(View.INVISIBLE);

                    holder.locateMyTeamCheckInTime.setVisibility(View.VISIBLE);
                    holder.locateMyTeamCheckOutTime.setVisibility(View.VISIBLE);
                    holder.locateMyTeamLocation.setVisibility(View.VISIBLE);
                    holder.locateMyTeamNameCheckInIcon.setVisibility(View.VISIBLE);
                    holder.locateMyTeamCheckOutIcon.setVisibility(View.VISIBLE);

                    holder.locateMyTeamCheckInTime.setText(Utils.splitTime(daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().get(0).getCalendarEntriesModel().getFrom()));
                    holder.locateMyTeamCheckOutTime.setText(Utils.splitTime(daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().get(0).getCalendarEntriesModel().getMyto()));


                }

        }else {
                holder.locateMyTeamCheckInTime.setVisibility(View.INVISIBLE);
                holder.locateMyTeamCheckOutTime.setVisibility(View.INVISIBLE);
                holder.locateMyTeamLocation.setVisibility(View.INVISIBLE);
                holder.locateMyTeamNameCheckInIcon.setVisibility(View.INVISIBLE);
                holder.locateMyTeamCheckOutIcon.setVisibility(View.INVISIBLE);
                holder.locateMyTeamDeskName.setVisibility(View.INVISIBLE);
            }

        }

        holder.locateMyTeamLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    showMyTeamLocationClickable.loadMyTeamLocation(daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().get(0).getCalendarEntriesModel().getBooking().getLocationBuildingFloor().getFloorID(), daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().get(0).getCalendarEntriesModel().getBooking().getDeskId());
                } catch (Exception e){

                }


            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().isEmpty()) {

                    if (daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().get(0).getCalendarEntriesModel() != null) {
                        //showMyTeamLocationClickable.showMyTeamLocation(175,273,bottomSheetDialog,bottomSheetBehavior);
                        String name = daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getFirstName() + " " + daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getLastName();
                        showMyTeamLocationClickable.showMyTeamLocation(daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getDayGroups().get(0).getCalendarEntriesModel(), name,daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).isIfFirstAidStatus(),daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).isFireStatus(),daoTeamMemberList.get(holder.getAbsoluteAdapterPosition()).getProfileImage());
                    } else {
                        Utils.toastMessage(context, "No Booking Avaliable");
                    }
                } else {
                    Utils.toastMessage(context, "No Booking Avaliable");
                }
            }
        });


    }

    private void setVivisbleItems(LocateMyTeamViewHolder holder) {
        holder.locateMyTeamLocation.setVisibility(View.VISIBLE);
        holder.locateMyTeamNameCheckInIcon.setVisibility(View.VISIBLE);
        holder.locateMyTeamCheckOutIcon.setVisibility(View.VISIBLE);
        holder.locateMyTeamCheckInTime.setVisibility(View.VISIBLE);
        holder.locateMyTeamCheckOutTime.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return daoTeamMemberList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<DAOTeamMember> filteredList = new ArrayList<>();

            if (constraint == null || constraint.toString().isEmpty() || constraint.length() == 0) {
                filteredList.addAll(daoTeamMemberListAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DAOTeamMember daoTeamMember : daoTeamMemberListAll) {

                    if (daoTeamMember.getFirstName().toLowerCase().contains(filterPattern)) {

                        filteredList.add(daoTeamMember);

                    }

                }

            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            daoTeamMemberList.clear();
            daoTeamMemberList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    class LocateMyTeamViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.locateMyTeamProfiles)
        CircleImageView myTeamProfileImage;
        @BindView(R.id.locateMyTeamName)
        TextView locateMyTeamName;
        @BindView(R.id.locateMyTeamDeskName)
        TextView locateMyTeamDeskName;
        @BindView(R.id.locateMyTeamCheckInTime)
        TextView locateMyTeamCheckInTime;
        @BindView(R.id.locateMyTeamCheckOutTime)
        TextView locateMyTeamCheckOutTime;
        @BindView(R.id.locateMyTeamLocation)
        ImageView locateMyTeamLocation;
        @BindView(R.id.locateMyTeamNameCheckInIcon)
        ImageView locateMyTeamNameCheckInIcon;
        @BindView(R.id.locateMyTeamCheckOutIcon)
        ImageView locateMyTeamCheckOutIcon;

        @BindView(R.id.locateMyTeamPlusIv)
        ImageView locateMyTeamPlusIv;
        @BindView(R.id.locateMyTeamFireIv)
        ImageView locateMyTeamFireIv;

        public LocateMyTeamViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}