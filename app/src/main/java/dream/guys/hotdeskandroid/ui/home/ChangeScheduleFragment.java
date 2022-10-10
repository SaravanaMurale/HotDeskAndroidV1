package dream.guys.hotdeskandroid.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.databinding.FragmentChangeScheduleBinding;
import dream.guys.hotdeskandroid.databinding.FragmentQrBinding;
import dream.guys.hotdeskandroid.model.response.BookingListResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeScheduleFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentChangeScheduleBinding fragmentChangeScheduleBinding;

    int teamId=0,teamMembershipId=0,selectedDeskId=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        inflater.inflate(R.layout.fragment_change_schedule, container, false)
        fragmentChangeScheduleBinding = fragmentChangeScheduleBinding.inflate(inflater, container, false);
        View root = fragmentChangeScheduleBinding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inflate the layout for this fragment
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat dayFormat = new SimpleDateFormat("EEE");
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String day="";
        String date="";
        String[] days = new String[7];
        for (int i = 0; i < 7; i++)
        {
            System.out.println("days check bala"+dayFormat.format(calendar.getTime()));
            switch (dayFormat.format(calendar.getTime())){
                case "Mon":
                    if (Utils.compareTwoDate(calendar.getTime(),Utils.getCurrentDate())!=1){
                        fragmentChangeScheduleBinding.mondayGroup.setEnabled(false);
                        fragmentChangeScheduleBinding.tvMondayDate.setTextColor(getResources().getColor(R.color.figmaBlue));
                    } else {
                        fragmentChangeScheduleBinding.tvMondayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                    }
                    fragmentChangeScheduleBinding.tvMondayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    break;
                case "Tue":
                    fragmentChangeScheduleBinding.tvTuesdayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));

                    break;
                case "Wed":
                    fragmentChangeScheduleBinding.tvWednesdayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    break;
                case "Thu":
                    fragmentChangeScheduleBinding.tvThursdayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    break;
                case "Fri":
                    fragmentChangeScheduleBinding.tvFridayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    break;
                case "Sat":
                    fragmentChangeScheduleBinding.tvSaturdayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    break;
                case "Sun":
                    fragmentChangeScheduleBinding.tvSundayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    break;
                default:
            }
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void loadHomeList(){
        if (Utils.isNetworkAvailable(getActivity())) {
//            dialog= ProgressDialog.showProgressBar(getContext());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BookingListResponse> call = apiService.getUserMyWorkDetails(Utils.getCurrentDate(),true);
//            Call<BookingListResponse> call = apiService.getUserMyWorkDetails("2022-07-18",true);
            //Call<BookingListResponse> call = apiService.getUserMyWorkDetails("2022-07-04",true);
            call.enqueue(new Callback<BookingListResponse>() {
                @Override
                public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                    if(response.code()==200) {
                        BookingListResponse bookingListResponse  =response.body();
                        teamId = bookingListResponse.getTeamId();
                        teamMembershipId = bookingListResponse.getTeamMembershipId();
//                        createRecyclerList(bookingListResponse);
//                        loadDeskList();

//                        ProgressDialog.dismisProgressBar(getContext(),dialog);
//                        Log.d(TAG, "onResponse: if");

                    }else if(response.code()==401){
                        //Handle if token got expired
//                        ProgressDialog.dismisProgressBar(getContext(),dialog);
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
//                        Utils.finishAllActivity(getContext());
//                        redirectToBioMetricAccess();

                    }
                }
                @Override
                public void onFailure(Call<BookingListResponse> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

}