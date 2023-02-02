package com.brickendon.hdplus.ui.home;

import static com.brickendon.hdplus.utils.Utils.getCurrentDate;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.brickendon.hdplus.MainActivity;
import com.brickendon.hdplus.R;
import com.brickendon.hdplus.databinding.FragmentChangeScheduleBinding;
import com.brickendon.hdplus.databinding.FragmentQrBinding;
import com.brickendon.hdplus.model.request.BookingsRequest;
import com.brickendon.hdplus.model.request.LocateDeskDeleteRequest;
import com.brickendon.hdplus.model.response.BaseResponse;
import com.brickendon.hdplus.model.response.BookingForEditResponse;
import com.brickendon.hdplus.model.response.BookingListResponse;
import com.brickendon.hdplus.ui.settings.SettingsActivity;
import com.brickendon.hdplus.utils.AppConstants;
import com.brickendon.hdplus.utils.SessionHandler;
import com.brickendon.hdplus.utils.Utils;
import com.brickendon.hdplus.webservice.ApiClient;
import com.brickendon.hdplus.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeScheduleFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentChangeScheduleBinding fragmentChangeScheduleBinding;
    BookingListResponse bookingListResponse;
    int teamId=0,teamMembershipId=0,selectedDeskId=0;
    HashMap<String, Date> daysListHash = new HashMap<>();
    String[] days = new String[7];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentChangeScheduleBinding = fragmentChangeScheduleBinding.inflate(inflater, container, false);
        View root = fragmentChangeScheduleBinding.getRoot();
        fragmentChangeScheduleBinding.homeUserName.setText(
                SessionHandler.getInstance().get(getContext(), AppConstants.USERNAME));
        fragmentChangeScheduleBinding.homeTeamName.setText(SessionHandler.getInstance().get(getContext(),AppConstants.CURRENT_TEAM));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Inflate the layout for this fragment
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat dayFormat = new SimpleDateFormat("EEE");
        DateFormat dateFormat = new SimpleDateFormat("dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String day="";
        String date="";
        daysListHash.clear();
        for (int i = 0; i < 7; i++) {
            System.out.println("days check bala"+dayFormat.format(calendar.getTime()));
            switch (dayFormat.format(calendar.getTime())){
                case "Mon":
                    fragmentChangeScheduleBinding.tvMondayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    if (Utils.compareTwoDate(calendar.getTime(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvMondayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.mRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.mSick.setEnabled(false);
                        fragmentChangeScheduleBinding.mOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvMondayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.mRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.mSick.setEnabled(true);
                        fragmentChangeScheduleBinding.mOutOffice.setEnabled(true);
                    }

                    break;
                case "Tue":
                    fragmentChangeScheduleBinding.tvTuesdayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    if (Utils.compareTwoDate(calendar.getTime(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvTuesdayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.tRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.tSick.setEnabled(false);
                        fragmentChangeScheduleBinding.tOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvTuesdayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.tRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.tSick.setEnabled(true);
                        fragmentChangeScheduleBinding.tOutOffice.setEnabled(true);
                    }

                    break;
                case "Wed":
                    fragmentChangeScheduleBinding.tvWednesdayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    if (Utils.compareTwoDate(calendar.getTime(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvWednesdayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.wRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.wSick.setEnabled(false);
                        fragmentChangeScheduleBinding.wOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvWednesdayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.wRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.wSick.setEnabled(true);
                        fragmentChangeScheduleBinding.wOutOffice.setEnabled(true);
                    }


                    break;
                case "Thu":
                    fragmentChangeScheduleBinding.tvThursdayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    if (Utils.compareTwoDate(calendar.getTime(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvThursdayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.thRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.thSick.setEnabled(false);
                        fragmentChangeScheduleBinding.thOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvThursdayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.thRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.thSick.setEnabled(true);
                        fragmentChangeScheduleBinding.thOutOffice.setEnabled(true);
                    }


                    break;
                case "Fri":
                    fragmentChangeScheduleBinding.tvFridayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    if (Utils.compareTwoDate(calendar.getTime(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvFridayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.fRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.fSick.setEnabled(false);
                        fragmentChangeScheduleBinding.fOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvFridayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.fRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.fSick.setEnabled(true);
                        fragmentChangeScheduleBinding.fOutOffice.setEnabled(true);
                    }
                    break;
                case "Sat":
                    fragmentChangeScheduleBinding.tvSaturdayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    if (Utils.compareTwoDate(calendar.getTime(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvSaturdayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.sRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.sSick.setEnabled(false);
                        fragmentChangeScheduleBinding.sOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvSaturdayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.sRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.sSick.setEnabled(false);
                        fragmentChangeScheduleBinding.sOutOffice.setEnabled(false);
                    }


                    break;
                case "Sun":
                    fragmentChangeScheduleBinding.tvSundayDate.setText(dayFormat.format(calendar.getTime())
                            +" \n"+dateFormat.format(calendar.getTime()));
                    if (Utils.compareTwoDate(calendar.getTime(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvSundayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.suRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.suSick.setEnabled(false);
                        fragmentChangeScheduleBinding.suOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvSundayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.suRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.suSick.setEnabled(false);
                        fragmentChangeScheduleBinding.suOutOffice.setEnabled(false);
                    }

                    break;
                default:
            }
            daysListHash.put(dayFormat.format(calendar.getTime()), calendar.getTime());
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        loadHomeList();
        if(SessionHandler.getInstance().get(getContext(),AppConstants.TENANTIMAGE)!=null){
            String cleanImage = SessionHandler.getInstance().get(getContext(),AppConstants.TENANTIMAGE);
            byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            fragmentChangeScheduleBinding.tentantImageView.setImageBitmap(decodedByte);
        }
        if(SessionHandler.getInstance().get(getContext(),AppConstants.USERIMAGE)!=null){
            String cleanImage = SessionHandler.getInstance().get(getContext(),AppConstants.USERIMAGE);
            byte[] decodedString = Base64.decode(cleanImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            fragmentChangeScheduleBinding.userProfilePic.setImageBitmap(decodedByte);
        }

        fragmentChangeScheduleBinding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController= Navigation.findNavController(view);
                navController.navigate(R.id.navigation_home);
            }
        });
        fragmentChangeScheduleBinding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController= Navigation.findNavController(view);
                navController.navigate(R.id.navigation_home);
            }
        });
        fragmentChangeScheduleBinding.searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showSearch();
            }
        });
        fragmentChangeScheduleBinding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(),ProfileActivity.class);
                getActivity().startActivity(intent);*/
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                getActivity().startActivity(intent);
            }
        });

/*
        fragmentChangeScheduleBinding.mondayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radio = group.findViewById(checkedId);
                int idx = group.indexOfChild(radio);
                RadioButton r = (RadioButton) group.getChildAt(idx);

                if (bookingListResponse !=null && !r.isChecked()){
                    checkSwitchState("Mon",checkedId,group);
                }
            }
        });
        fragmentChangeScheduleBinding.tuesdayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radio = group.findViewById(checkedId);
                int idx = group.indexOfChild(radio);
                RadioButton r = (RadioButton) group.getChildAt(idx);

                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Tue",checkedId,group);
                }
            }
        });
        fragmentChangeScheduleBinding.wednesdayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radio = group.findViewById(checkedId);
                int idx = group.indexOfChild(radio);
                RadioButton r = (RadioButton) group.getChildAt(idx);

                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Wed",checkedId,group);
                }
            }
        });
        fragmentChangeScheduleBinding.thursdayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radio = group.findViewById(checkedId);
                int idx = group.indexOfChild(radio);
                RadioButton r = (RadioButton) group.getChildAt(idx);

                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Thu",checkedId,group);
                }
            }
        });
        fragmentChangeScheduleBinding.fridayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radio = group.findViewById(checkedId);
                int idx = group.indexOfChild(radio);
                RadioButton r = (RadioButton) group.getChildAt(idx);

                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Fri",checkedId,group);
                }
            }
        });
        fragmentChangeScheduleBinding.saturdayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radio = group.findViewById(checkedId);
                int idx = group.indexOfChild(radio);
                RadioButton r = (RadioButton) group.getChildAt(idx);

                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Sat",checkedId,group);
                }
            }
        });
        fragmentChangeScheduleBinding.sundayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radio = group.findViewById(checkedId);
                int idx = group.indexOfChild(radio);
                RadioButton r = (RadioButton) group.getChildAt(idx);

                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Sun",checkedId,group);
                }
            }
        });*/

    }

    /*private void checkSwitchState(String day, int checkedId, RadioGroup radioGroup) {
        boolean outerCheck = false;
        for (int i=0; i<bookingListResponse.getDayGroups().size();i++){
            if (!(Utils.compareTwoDate(bookingListResponse.getDayGroups().get(i).getDate(),
                    Utils.getCurrentDate()) == 1) &&
                    Utils.getDayFromDate(bookingListResponse.getDayGroups().get(i).getDate())
                            .equalsIgnoreCase(day) &&
                    bookingListResponse.getDayGroups().get(i).getCalendarEntries() !=null &&
                    bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0
            ){
                if(Utils.compareTwoDate(bookingListResponse.getDayGroups().get(i).getDate(),
                        Utils.getCurrentDate()) == 2){
                    if (radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.mRemote.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.tRemote.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.wRemote.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.thRemote.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.fRemote.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.sRemote.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.suRemote.getId()){
                        callBookingForEdit(9);
                    } else if (radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.mOutOffice.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.tOutOffice.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.wOutOffice.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.thOutOffice.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.fOutOffice.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.sOutOffice.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.suOutOffice.getId()){
                        callBookingForEdit(6);
                    } else if (radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.mSick.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.tSick.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.wSick.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.thSick.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.fSick.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.sSick.getId()
                            || radioGroup.getCheckedRadioButtonId() ==
                            fragmentChangeScheduleBinding.suSick.getId()){
                        callBookingForEdit(18);
                    }
                }else {

                    switch (day){
                        case "Mon":
                            boolean checkInOffice = false;
                            for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().get(x).getUsageTypeAbbreviation()
                                        .equalsIgnoreCase("IO")){
                                    checkInOffice = true;
                                }
                            }
                            if (checkInOffice){
                                statusPopUp(bookingListResponse.getDayGroups().get(i).getDate(),
                                        checkedId, radioGroup,day);
                            } else {
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0){
                                    if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suRemote.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                9);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suOutOffice.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                6);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suSick.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                18);
                                    }
                                }
                            }
                            outerCheck = true;
                            break;
                        case "Tue":
                            boolean tcheckInOffice = false;
                            for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().get(x).getUsageTypeAbbreviation()
                                        .equalsIgnoreCase("IO")){
                                    tcheckInOffice = true;
                                }
                            }
                            if (tcheckInOffice){
                                statusPopUp(bookingListResponse.getDayGroups().get(i).getDate(),
                                        checkedId, radioGroup,day);
                            }  else {
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0){
                                    if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suRemote.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                9);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suOutOffice.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                6);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suSick.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                18);
                                    }
                                }
                            }

                            outerCheck = true;
                            break;
                        case "Wed":
                            boolean wcheckInOffice = false;
                            for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().get(x).getUsageTypeAbbreviation()
                                        .equalsIgnoreCase("IO")){
                                    wcheckInOffice = true;
                                }
                            }
                            if (wcheckInOffice){
                                statusPopUp(bookingListResponse.getDayGroups().get(i).getDate(),
                                        checkedId, radioGroup,day);
                            }  else {
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0){
                                    if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suRemote.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                9);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suOutOffice.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                6);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suSick.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                18);
                                    }
                                }
                            }

                            outerCheck = true;
                            break;
                        case "Thu":
                            boolean thcheckInOffice = false;
                            for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().get(x).getUsageTypeAbbreviation()
                                        .equalsIgnoreCase("IO")){
                                    thcheckInOffice = true;
                                }
                            }
                            if (thcheckInOffice){
                                statusPopUp(bookingListResponse.getDayGroups().get(i).getDate(),
                                        checkedId, radioGroup,day);
                            }  else {
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0){
                                    if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suRemote.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                9);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suOutOffice.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                6);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suSick.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                18);
                                    }
                                }
                            }

                            outerCheck = true;
                            break;
                        case "Fri":
                            boolean fcheckInOffice = false;
                            for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().get(x).getUsageTypeAbbreviation()
                                        .equalsIgnoreCase("IO")){
                                    fcheckInOffice = true;
                                }
                            }
                            if (fcheckInOffice){
                                statusPopUp(bookingListResponse.getDayGroups().get(i).getDate(),
                                        checkedId, radioGroup,day);
                            }  else {
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0){
                                    if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suRemote.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                9);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suOutOffice.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                6);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suSick.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                18);
                                    }
                                }
                            }

                            outerCheck = true;
                            break;
                        case "Sat":
                            boolean scheckInOffice = false;
                            for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().get(x).getUsageTypeAbbreviation()
                                        .equalsIgnoreCase("IO")){
                                    scheckInOffice = true;
                                }
                            }
                            if (scheckInOffice){
                                statusPopUp(bookingListResponse.getDayGroups().get(i).getDate(),
                                        checkedId, radioGroup,day);
                            }  else {
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0){
                                    if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suRemote.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                9);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suOutOffice.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                6);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suSick.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                18);
                                    }
                                }
                            }

                            outerCheck = true;
                            break;
                        case "Sun":
                            boolean sucheckInOffice = false;
                            for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().get(x).getUsageTypeAbbreviation()
                                        .equalsIgnoreCase("IO")){
                                    sucheckInOffice = true;
                                }
                            }
                            if (sucheckInOffice){
                                statusPopUp(bookingListResponse.getDayGroups().get(i).getDate(),
                                        checkedId, radioGroup,day);
                            }  else {
                                if (bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0){
                                    if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sRemote.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suRemote.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                9);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sOutOffice.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suOutOffice.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                6);
                                    } else if (radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.mSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.tSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.wSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.thSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.fSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.sSick.getId()
                                            || radioGroup.getCheckedRadioButtonId() ==
                                            fragmentChangeScheduleBinding.suSick.getId()){
                                        callFulldayBooking(bookingListResponse.getDayGroups().get(i).getDate(),
                                                18);
                                    }
                                }
                            }

                            outerCheck = true;
                            break;
                        default:
                    }
                }
            }
        }
        if (!outerCheck) {
            if (radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.mRemote.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.tRemote.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.wRemote.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.thRemote.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.fRemote.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.sRemote.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.suRemote.getId()){
                callFulldayBooking(daysListHash.get(day),
                        9);
            } else if (radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.mOutOffice.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.tOutOffice.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.wOutOffice.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.thOutOffice.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.fOutOffice.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.sOutOffice.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.suOutOffice.getId()){
                callFulldayBooking(daysListHash.get(day),
                        6);
            } else if (radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.mSick.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.tSick.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.wSick.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.thSick.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.fSick.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.sSick.getId()
                    || radioGroup.getCheckedRadioButtonId() ==
                    fragmentChangeScheduleBinding.suSick.getId()){
                callFulldayBooking(daysListHash.get(day),
                        18);
            }


        }

    }
*/
    private void callBookingForEdit(int usageTypeId) {
        if (Utils.isNetworkAvailable(getActivity())) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BookingForEditResponse> call = apiService.getBookingsForEdit(SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAM_ID),
                    SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID),
                    Utils.getCurrentDate(),Utils.getCurrentDate());
            call.enqueue(new Callback<BookingForEditResponse>() {
                @Override
                public void onResponse(Call<BookingForEditResponse> call, Response<BookingForEditResponse> response) {
                    if (response.code()==200) {
                        BookingForEditResponse bookingForEditResponse = response.body();
                        LocateDeskDeleteRequest deskDeleteRequest = new LocateDeskDeleteRequest();
                        deskDeleteRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
                        deskDeleteRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));
                        List<LocateDeskDeleteRequest.ChangeSets> changeSetsList = new ArrayList<>();
                        deskDeleteRequest.setChangesetsList(changeSetsList);
                        List<Integer> integerList = new ArrayList<>();
                        for (int i=0; i<bookingForEditResponse.getBookings().size();i++){
                            if (bookingForEditResponse.getBookings().get(i)
                                    .getStatus().getTimeStatus().equalsIgnoreCase("future")){
                                integerList.add(bookingForEditResponse.getBookings().get(i).getId());
                            }
                        }

                        deskDeleteRequest.setDeleteIdsList(integerList);

                        deleteDesks(deskDeleteRequest, usageTypeId);
                    }else if(response.code()==401){
                        Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);

                    }
                }

                @Override
                public void onFailure(Call<BookingForEditResponse> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    private void deleteDesks(LocateDeskDeleteRequest deskDeleteRequest, int usageTypeId) {
        if (Utils.isNetworkAvailable(getActivity())) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BaseResponse> call = apiService.doDeleteDeskBooking(deskDeleteRequest);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.code()==200){
                        makebookingWithUsageType(usageTypeId);
                    } else {
                        makebookingWithUsageType(usageTypeId);
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    makebookingWithUsageType(usageTypeId);
                }
            });


        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }


    }

    private void makebookingWithUsageType(int usageTypeId) {
        JsonObject jsonOuterObject = new JsonObject();
        JsonObject jsonInnerObject = new JsonObject();
        JsonObject jsonChangesObject = new JsonObject();
        JsonArray jsonChangesetArray = new JsonArray();
        JsonArray jsonDeletedIdsArray = new JsonArray();
        jsonInnerObject.addProperty("id",0);
        jsonInnerObject.addProperty("date",""+Utils.getCurrentDate() + "T" + "00:00:00.000" + "Z");
        jsonOuterObject.addProperty("teamId", SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
        jsonOuterObject.addProperty("teamMembershipId", SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

        BookingsRequest bookingsRequest = new BookingsRequest();
        ArrayList<BookingsRequest.ChangeSets> list =new ArrayList<>();
        ArrayList<Integer> list1 =new ArrayList<>();

        BookingsRequest.ChangeSets changeSets = new BookingsRequest.ChangeSets();
        changeSets.setDate(""+Utils.getCurrentDate() + "T" + "00:00:00.000" + "Z");
        jsonChangesObject.addProperty("usageTypeId", usageTypeId);
        jsonChangesObject.addProperty("timeZoneId", SessionHandler.getInstance().get(getContext(),AppConstants.DEFAULT_TIME_ZONE_ID));
        jsonChangesObject.addProperty("from", getCurrentDate() + "" + "T" + Utils.getCurrentTime() + ":" + "00" + "." + "000" + "Z");
        jsonChangesObject.addProperty("to",getCurrentDate() + "" + "T" + Utils.addHoursToSelectedTime(Utils.getCurrentTime(),2)
                + ":" + "00" + "." + "000" + "Z");

        jsonInnerObject.add("changes",jsonChangesObject);
        jsonChangesetArray.add(jsonInnerObject);

        jsonOuterObject.add("changesets", jsonChangesetArray);
        jsonOuterObject.add("deletedIds", jsonDeletedIdsArray);

        /*LocateDeskBookingRequest locateDeskBookingRequest = new LocateDeskBookingRequest();
        locateDeskBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
        locateDeskBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

        List<LocateDeskBookingRequest.ChangeSets> changeSetsList = new ArrayList<>();
        LocateDeskBookingRequest.ChangeSets changeSets = locateDeskBookingRequest.new ChangeSets();
        changeSets.setChangeSetId(0);
        changeSets.setChangeSetDate(Utils.getCurrentDate() + "T" + "00:00:00.000" + "Z");

        LocateDeskBookingRequest.ChangeSets.Changes changes = changeSets.new Changes();
        changes.setUsageTypeId(usageTypeId);

        changes.setTimeZoneId(SessionHandler.getInstance().get(getContext(),AppConstants.DEFAULT_TIME_ZONE_ID));

        changeSets.setChanges(changes);
        changeSetsList.add(changeSets);

        locateDeskBookingRequest.setChangeSets(changeSetsList);

        List<LocateDeskBookingRequest.DeleteIds> deleteIdsList = new ArrayList<>();
        locateDeskBookingRequest.setDeletedIds(deleteIdsList);
*/

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.bookingBookings(jsonOuterObject);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }

    private void statusPopUp(Date date, int checkedId, RadioGroup radioGroup,String day) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.20);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_pin_pop_up);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView text = dialog.findViewById(R.id.tv_err_msg);
        text.setText("There is an existing In office booking. \n Do you want to cancel to the selected booking type?");
        TextView dialogButton = dialog.findViewById(R.id.tv_ok);
        TextView dialogButtonCancel = dialog.findViewById(R.id.tv_cancel);
        dialogButton.setBackgroundColor(getResources().getColor(R.color.figma_red));
        dialogButtonCancel.setBackgroundColor(getResources().getColor(R.color.figmaGrey));
//        dialogButtonCancel.setTextColor(getResources().getColor(R.color.figmaBlack));
        dialogButtonCancel.setText("Cancel");
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("radio group check id"+checkedId);
                System.out.println("radio group check butt id"+radioGroup.getCheckedRadioButtonId());
                switch (day){
                    case "Mon":
                        if (radioGroup.getCheckedRadioButtonId() ==
                                fragmentChangeScheduleBinding.mRemote.getId()){
                            callClearApi(date, radioGroup,9);
                        } else if (radioGroup.getCheckedRadioButtonId() ==
                                fragmentChangeScheduleBinding.mOutOffice.getId()){
                            callClearApi(date, radioGroup,6);
                        } else if (radioGroup.getCheckedRadioButtonId() ==
                                fragmentChangeScheduleBinding.mSick.getId()){
                            callClearApi(date, radioGroup,18);
                        }
                        break;
                    case "Tue":
                        if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.tRemote.getId()){
                            callClearApi(date, radioGroup,9);
                        } else if (radioGroup.getCheckedRadioButtonId() ==
                                fragmentChangeScheduleBinding.tOutOffice.getId()){
                            callClearApi(date, radioGroup,6);
                        } else if (radioGroup.getCheckedRadioButtonId() ==
                                fragmentChangeScheduleBinding.tSick.getId()){
                            callClearApi(date, radioGroup,18);
                        }
                        break;
                    case "Wed":
                        if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.wRemote.getId()){
                            callClearApi(date, radioGroup,9);
                        } else if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.wOutOffice.getId()){
                            callClearApi(date, radioGroup,6);
                        } else if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.wSick.getId()){
                            callClearApi(date, radioGroup,18);
                        }
                        break;
                    case "Thu":
                        if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.thRemote.getId()){
                            callClearApi(date, radioGroup,9);
                        } else if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.thOutOffice.getId()){
                            callClearApi(date, radioGroup,6);
                        } else if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.thSick.getId()){
                            callClearApi(date, radioGroup,18);
                        }
                        break;
                    case "Fri":
                        if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.fRemote.getId()){
                            callClearApi(date, radioGroup,9);
                        } else if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.fOutOffice.getId()){
                            callClearApi(date, radioGroup,6);
                        } else if (radioGroup.getCheckedRadioButtonId() ==
                                fragmentChangeScheduleBinding.fSick.getId()){
                            callClearApi(date, radioGroup,18);
                        }
                        break;
                    case "Sat":
                        if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.sRemote.getId()){
                            callClearApi(date, radioGroup,9);
                        } else if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.sOutOffice.getId()){
                            callClearApi(date, radioGroup,6);
                        } else if (radioGroup.getCheckedRadioButtonId() ==
                                fragmentChangeScheduleBinding.sSick.getId()){
                            callClearApi(date, radioGroup,18);
                        }
                        break;
                    case "Sun":
                        if (radioGroup.getCheckedRadioButtonId() == fragmentChangeScheduleBinding.suRemote.getId()){
                            callClearApi(date, radioGroup,9);
                        } else if (radioGroup.getCheckedRadioButtonId() ==
                                fragmentChangeScheduleBinding.suOutOffice.getId()){
                            callClearApi(date, radioGroup,6);
                        } else if (radioGroup.getCheckedRadioButtonId() ==
                                fragmentChangeScheduleBinding.suSick.getId()){
                            callClearApi(date, radioGroup,18);
                        }
                        break;
                    default:
                }

//                callClearApi(date, radioGroup);
                dialog.dismiss();
            }
        });
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View radio = radioGroup.findViewById(checkedId);
                int idx = radioGroup.indexOfChild(radio);
                RadioButton r = (RadioButton) radioGroup.getChildAt(idx);
                r.setChecked(false);
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    private void callFulldayBooking(Date date, int usageTypeId) {
        if (Utils.isNetworkAvailable(getActivity())) {
            fragmentChangeScheduleBinding.progrssBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("date",""+Utils.getYearMonthDateFormat(date)+"T00:00:00.000Z");
            jsonObject.addProperty("teamId",SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAM_ID));
            jsonObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAMMEMBERSHIP_ID));
            jsonObject.addProperty("userId",SessionHandler.getInstance().getInt(getContext(),AppConstants.USER_ID));
            jsonObject.addProperty("usageTypeId", usageTypeId);

            Call<BaseResponse> call = apiService.fullDayBooking(jsonObject);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(response.code()==200) {
                        Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();

                    } else if(response.code()==401){
                        //Handle if token got expired
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                    } else {
                        Toast.makeText(getContext(), "Not Updated", Toast.LENGTH_SHORT).show();
                    }
                    fragmentChangeScheduleBinding.progrssBar.setVisibility(View.GONE);
                }
                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    fragmentChangeScheduleBinding.progrssBar.setVisibility(View.GONE);
                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }
    private void callClearApi(Date date, RadioGroup radioGroup, int usageTypeId) {
        if (Utils.isNetworkAvailable(getActivity())) {
            fragmentChangeScheduleBinding.progrssBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("date",""+Utils.getYearMonthDateFormat(date)+"T00:00:00.000Z");
            jsonObject.addProperty("teamId",SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAM_ID));
            jsonObject.addProperty("teamMembershipId",SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAMMEMBERSHIP_ID));

            Call<BaseResponse> call = apiService.clearBooking(jsonObject);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if(response.code()==200) {
                        callFulldayBooking(date,usageTypeId);
                    }else if(response.code()==500){
                        callFulldayBooking(date,usageTypeId);
                    }else if(response.code()==401){
                        //Handle if token got expired
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
                    }
                }
                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {

                }
            });

        } else {
            Utils.toastMessage(getActivity(), "Please Enable Internet");
        }
    }

    private void loadHomeList(){
        if (Utils.isNetworkAvailable(getActivity())) {
            fragmentChangeScheduleBinding.progrssBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<BookingListResponse> call = apiService.getUserMyWorkDetails(Utils.getCurrentDate(),true);
            call.enqueue(new Callback<BookingListResponse>() {
                @Override
                public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                    if(response.code()==200) {
                        bookingListResponse  =response.body();
                        teamId = bookingListResponse.getTeamId();
                        teamMembershipId = bookingListResponse.getTeamMembershipId();
                        fragmentChangeScheduleBinding.progrssBar.setVisibility(View.GONE);
//                        calculateSchedule(bookingListResponse);
                    }else if(response.code()==401){
                        //Handle if token got expired
                        SessionHandler.getInstance().saveBoolean(getActivity(), AppConstants.LOGIN_CHECK,false);
                        Utils.showCustomTokenExpiredDialog(getActivity(),"Token Expired");
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

    /*private void calculateSchedule(BookingListResponse bookingListResponse) {
        fragmentChangeScheduleBinding.mondayGroup.setOnCheckedChangeListener(null);
        fragmentChangeScheduleBinding.tuesdayGroup.setOnCheckedChangeListener(null);
        fragmentChangeScheduleBinding.wednesdayGroup.setOnCheckedChangeListener(null);
        fragmentChangeScheduleBinding.thursdayGroup.setOnCheckedChangeListener(null);
        fragmentChangeScheduleBinding.fridayGroup.setOnCheckedChangeListener(null);
        fragmentChangeScheduleBinding.saturdayGroup.setOnCheckedChangeListener(null);
        fragmentChangeScheduleBinding.sundayGroup.setOnCheckedChangeListener(null);
        for(int i=0; i<bookingListResponse.getDayGroups().size(); i++){
            switch (Utils.getDayFromDate(bookingListResponse.getDayGroups().get(i).getDate())){
                case "Mon":
                    String date = Utils.getDayFromDate(bookingListResponse.getDayGroups().get(i).getDate())
                            +"\n"+ Utils.getDateFromDate(bookingListResponse.getDayGroups().get(i).getDate());
                    date.replace(","," \n");
                    fragmentChangeScheduleBinding.tvMondayDate.setText(date);
                    if (Utils.compareTwoDate(bookingListResponse.getDayGroups().get(i).getDate(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvMondayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.mRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.mSick.setEnabled(false);
                        fragmentChangeScheduleBinding.mOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvMondayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.mRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.mSick.setEnabled(true);
                        fragmentChangeScheduleBinding.mOutOffice.setEnabled(true);
                    }

                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()!=null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0
                    ){
                        for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")){
                                fragmentChangeScheduleBinding.mRemote.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")){
                                fragmentChangeScheduleBinding.mOutOffice.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")){
                                fragmentChangeScheduleBinding.mSick.setChecked(true);
                            } else {

                            }
                        }
                    }

                    break;
                case "Tue":
                    String tdate =Utils.getDayFromDate(bookingListResponse.getDayGroups().get(i).getDate())
                            +"\n"+ Utils.getDateFromDate(bookingListResponse.getDayGroups().get(i).getDate());
                    tdate.replace(","," \n");
                    fragmentChangeScheduleBinding.tvTuesdayDate.setText(tdate);
                    if (Utils.compareTwoDate(bookingListResponse.getDayGroups().get(i).getDate(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvTuesdayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.tRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.tSick.setEnabled(false);
                        fragmentChangeScheduleBinding.tOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvTuesdayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.tRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.tSick.setEnabled(true);
                        fragmentChangeScheduleBinding.tOutOffice.setEnabled(true);
                    }

                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()!=null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0
                    ){
                        for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")){
                                fragmentChangeScheduleBinding.tRemote.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")){
                                fragmentChangeScheduleBinding.tOutOffice.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")){
                                fragmentChangeScheduleBinding.tSick.setChecked(true);
                            } else {

                            }
                        }
                    }

                    break;
                case "Wed":
                    String wdate = Utils.getDayFromDate(bookingListResponse.getDayGroups().get(i).getDate())
                            +"\n"+ Utils.getDateFromDate(bookingListResponse.getDayGroups().get(i).getDate());
                    wdate.replace(","," \n");
                    fragmentChangeScheduleBinding.tvWednesdayDate.setText(wdate);
                    if (Utils.compareTwoDate(bookingListResponse.getDayGroups().get(i).getDate(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvWednesdayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.wRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.wSick.setEnabled(false);
                        fragmentChangeScheduleBinding.wOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvWednesdayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.wRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.wSick.setEnabled(true);
                        fragmentChangeScheduleBinding.wOutOffice.setEnabled(true);
                    }

                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()!=null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0
                    ){
                        for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")){
                                fragmentChangeScheduleBinding.wRemote.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")){
                                fragmentChangeScheduleBinding.wOutOffice.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")){
                                fragmentChangeScheduleBinding.wSick.setChecked(true);
                            } else {

                            }
                        }
                    }

                    break;
                case "Thu":
                    String thdate = Utils.getDayFromDate(bookingListResponse.getDayGroups().get(i).getDate())
                            +"\n"+ Utils.getDateFromDate(bookingListResponse.getDayGroups().get(i).getDate());
                    thdate.replace(","," \n");
                    fragmentChangeScheduleBinding.tvThursdayDate.setText(thdate);
                    if (Utils.compareTwoDate(bookingListResponse.getDayGroups().get(i).getDate(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvThursdayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.thRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.thSick.setEnabled(false);
                        fragmentChangeScheduleBinding.thOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvThursdayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.thRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.thSick.setEnabled(true);
                        fragmentChangeScheduleBinding.thOutOffice.setEnabled(true);
                    }

                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()!=null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0
                    ){
                        for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")){
                                fragmentChangeScheduleBinding.thRemote.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")){
                                fragmentChangeScheduleBinding.thOutOffice.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")){
                                fragmentChangeScheduleBinding.thSick.setChecked(true);
                            } else {

                            }
                        }
                    }
                    break;
                case "Fri":
                    String fdate = Utils.getDayFromDate(bookingListResponse.getDayGroups().get(i).getDate())
                            +"\n"+ Utils.getDateFromDate(bookingListResponse.getDayGroups().get(i).getDate());
                    fdate.replace(","," \n");
                    fragmentChangeScheduleBinding.tvFridayDate.setText(fdate);
                    if (Utils.compareTwoDate(bookingListResponse.getDayGroups().get(i).getDate(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvFridayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.fRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.fSick.setEnabled(false);
                        fragmentChangeScheduleBinding.fOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvFridayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.fRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.fSick.setEnabled(true);
                        fragmentChangeScheduleBinding.fOutOffice.setEnabled(true);
                    }

                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()!=null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0
                    ){
                        for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")){
                                fragmentChangeScheduleBinding.fRemote.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")){
                                fragmentChangeScheduleBinding.fOutOffice.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")){
                                fragmentChangeScheduleBinding.fSick.setChecked(true);
                            } else {

                            }
                        }
                    }
                    break;
                case "Sat":
                    String sdate = Utils.getDayFromDate(bookingListResponse.getDayGroups().get(i).getDate())
                            +"\n"+ Utils.getDateFromDate(bookingListResponse.getDayGroups().get(i).getDate());
                    sdate.replace(","," \n");
                    fragmentChangeScheduleBinding.tvSaturdayDate.setText(sdate);
                    if (Utils.compareTwoDate(bookingListResponse.getDayGroups().get(i).getDate(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvSaturdayDate.setTextColor(getResources().getColor(R.color.figmaGrey));
                        fragmentChangeScheduleBinding.sRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.sSick.setEnabled(false);
                        fragmentChangeScheduleBinding.sOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvSaturdayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.sRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.sSick.setEnabled(true);
                        fragmentChangeScheduleBinding.sOutOffice.setEnabled(true);
                    }

                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()!=null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0
                    ){
                        for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")){
                                fragmentChangeScheduleBinding.sRemote.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")){
                                fragmentChangeScheduleBinding.sOutOffice.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")){
                                fragmentChangeScheduleBinding.sSick.setChecked(true);
                            } else {

                            }
                        }
                    }
                    break;
                case "Sun":
                    String sudate = Utils.getDayFromDate(bookingListResponse.getDayGroups().get(i).getDate())
                            +"\n"+ Utils.getDateFromDate(bookingListResponse.getDayGroups().get(i).getDate());
                    sudate.replace(","," \n");
                    fragmentChangeScheduleBinding.tvSundayDate.setText(sudate);
                    if (Utils.compareTwoDate(bookingListResponse.getDayGroups().get(i).getDate(),
                            Utils.getCurrentDate()) == 1){
                        fragmentChangeScheduleBinding.tvSundayDate.setTextColor(getResources().getColor(R.color.figmaBgGrey));
                        fragmentChangeScheduleBinding.suRemote.setEnabled(false);
                        fragmentChangeScheduleBinding.suSick.setEnabled(false);
                        fragmentChangeScheduleBinding.suOutOffice.setEnabled(false);
                    } else {
                        fragmentChangeScheduleBinding.tvSundayDate.setTextColor(getResources().getColor(R.color.figmaBlack));
                        fragmentChangeScheduleBinding.suRemote.setEnabled(true);
                        fragmentChangeScheduleBinding.suSick.setEnabled(true);
                        fragmentChangeScheduleBinding.suOutOffice.setEnabled(true);
                    }

                    if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()!=null
                            && bookingListResponse.getDayGroups().get(i).getCalendarEntries().size()>0
                    ){
                        for (int x=0; x < bookingListResponse.getDayGroups().get(i).getCalendarEntries().size(); x++){
                            if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("WFH")){
                                fragmentChangeScheduleBinding.suRemote.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("OO")){
                                fragmentChangeScheduleBinding.suOutOffice.setChecked(true);
                            }else if (bookingListResponse.getDayGroups().get(i).getCalendarEntries()
                                    .get(x).getUsageTypeAbbreviation().equalsIgnoreCase("SL")){
                                fragmentChangeScheduleBinding.suSick.setChecked(true);
                            } else {

                            }
                        }
                    }

                    break;
                default:
            }
        }
        fragmentChangeScheduleBinding.mondayGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        fragmentChangeScheduleBinding.tuesdayGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        fragmentChangeScheduleBinding.wednesdayGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        fragmentChangeScheduleBinding.thursdayGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        fragmentChangeScheduleBinding.fridayGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        fragmentChangeScheduleBinding.saturdayGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        fragmentChangeScheduleBinding.sundayGroup.setOnCheckedChangeListener(this::onCheckedChanged);
    }
*/

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        View radio = group.findViewById(checkedId);
        int idx = group.indexOfChild(radio);
        RadioButton r = (RadioButton) group.getChildAt(idx);
/*
        switch (group.getId()){
            case R.id.monday_group:
                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Mon",checkedId,group);
                }
                break;
            case R.id.tuesday_group:
                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Tue",checkedId,group);
                }
                break;
            case R.id.wednesday_group:
                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Wed",checkedId,group);
                }
                break;
            case R.id.thursday_group:
                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Thu",checkedId,group);
                }
                break;
            case R.id.friday_group:
                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Fri",checkedId,group);
                }
                break;
            case R.id.saturday_group:
                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Sat",checkedId,group);
                }
                break;
            case R.id.sunday_group:
                if (bookingListResponse !=null && r.isChecked()){
                    checkSwitchState("Sun",checkedId,group);
                }
                break;
            default:

        }*/
    }
}