package dream.guys.hotdeskandroid.ui.locate;

import static dream.guys.hotdeskandroid.utils.Utils.getCurrentDate;
import static dream.guys.hotdeskandroid.utils.Utils.getCurrentTime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.BookingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.CarListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.FloorAdapter;
import dream.guys.hotdeskandroid.adapter.LocateMyTeamAdapter;
import dream.guys.hotdeskandroid.adapter.MeetingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.ShowCountryAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentLocateBinding;
import dream.guys.hotdeskandroid.example.CanvasView;
import dream.guys.hotdeskandroid.example.DataModel;
import dream.guys.hotdeskandroid.example.ItemAdapter;
import dream.guys.hotdeskandroid.example.MyCanvasDraw;
import dream.guys.hotdeskandroid.example.ValuesPOJO;
import dream.guys.hotdeskandroid.model.request.CarParkingStatusModel;
import dream.guys.hotdeskandroid.model.request.DeskStatusModel;
import dream.guys.hotdeskandroid.model.request.LocateBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkEditRequest;
import dream.guys.hotdeskandroid.model.request.LocateDeskBookingRequest;
import dream.guys.hotdeskandroid.model.request.MeetingRoomRequest;
import dream.guys.hotdeskandroid.model.request.Point;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.CarParkAvalibilityResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingForEditResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingslotsResponse;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.model.response.DeskDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.MeetingListToEditResponse;
import dream.guys.hotdeskandroid.model.response.MeetingRoomDescriptionResponse;
import dream.guys.hotdeskandroid.model.response.TeamsResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocateFragment extends Fragment implements ShowCountryAdapter.OnSelectListener, BookingListToEditAdapter.OnEditClickable, DeskListRecyclerAdapter.OnSelectSelected, CarListToEditAdapter.CarEditClickable, MeetingListToEditAdapter.OnMeetingEditClickable {



    @BindView(R.id.locateProgressBar)
    ProgressBar progressBar;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    //BottomSheetData
    TextView country, state, street, floor, back, bsApply;
    RecyclerView rvCountry, rvState, rvStreet, rvFloor;
    ShowCountryAdapter showCountryAdapter;
    FloorAdapter floorAdapter;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout countryBlock, statBlock, streetBlock, floorBlock;

    TextView bsLocationSearch;

    RecyclerView rvMyTeam;
    LocateMyTeamAdapter locateMyTeamAdapter;


    TextView locateText, title;


    @BindView(R.id.searchLocate)
    TextView searchLocate;
    @BindView(R.id.locateCalendearView)
    TextView locateCalendearView;
    @BindView(R.id.locateStartTime)
    TextView locateStartTime;
    @BindView(R.id.locateEndTime)
    TextView locateEndTime;


    @BindView(R.id.ivLocateMyTeam)
    ImageView ivLocateMyTeam;
    @BindView(R.id.ivLocateFilter)
    ImageView ivLocateFilter;
    @BindView(R.id.ivLocateKey)
    ImageView ivLocateKey;
    @BindView(R.id.locateKeyStatusBlock)
    RelativeLayout locateKeyStatusBlock;


    @BindView(R.id.first)
    ImageView first;

    @BindView(R.id.firstLayout)
    LinearLayout firstLayout;

   /* @BindView(R.id.secondLayout)
    LinearLayout secondLayout;*/

    View deskView;
    int canvasss = 0;


    List<LocateCountryRespose> locateCountryResposeList;


    CanvasView canvasView;
    @NonNull FragmentLocateBinding binding;

    //Dialog dialog;
    int stateId = 0;

    int endTimeSelectedStats=0;

    boolean keyClickedStats = true;
    List<Point> pointList = new ArrayList<>();

    //CheckInDetails
    TextView locateDeskName, editBookingBack, editBookingContinue;
    RelativeLayout bookingDateBlock, bookingStartBlock, bookingEndBlock, bookingCommentBlock, bookingVechicleRegtBlock;
    EditText etComment, etVehicleReg;
    TextView locateCheckInDate, locateCheckInTime, locateCheckoutTime;
    int teamDeskIdForBooking = 0;
    int selectedCarParkingSlotId = 0;

    //DesAvaliablityChecking
    List<DeskAvaliabilityResponse.TeamDeskAvaliabilityList> teamDeskAvaliabilityList;
    List<TeamsResponse> teamsResponseList;

    //Edit Booking
    int selectedDeskId=0;
    TextView deskRoomName;

    //CarParkingAvalibilityChecking
    List<CarParkingslotsResponse> carParkingslots;
    List<CarParkAvalibilityResponse> carParkAvalibilityResponseList;
    List<CarParkingStatusModel> carParkingStatusModelList;
    boolean carParkingCheckingStatus=false;

    //Description
    String meetingRoomDescription=null;
    String carParkDescription=null;
    String deskDescriotion=null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dialog = new Dialog(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println("OnViewCreated");

        //Initally Load Floor Details
        initLoadFloorDetails(0);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = dream.guys.hotdeskandroid.databinding.FragmentLocateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        binding.locateStartTime.setText(getCurrentTime());
        binding.locateStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetLocateTimePickerInBooking(getContext(), getActivity(), binding.locateStartTime, "", "",1);

            }
        });

        if(endTimeSelectedStats==0){
            binding.locateEndTime.setText("23:59");
        }

        binding.locateEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), binding.locateEndTime, "", "");
                bottomSheetLocateTimePickerInBooking(getContext(), getActivity(), binding.locateEndTime, "", "",2);



            }
        });


        binding.locateCalendearView.setText(getCurrentDate());
        binding.locateCalendearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*String startDateString = "06/27/2007";
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

                Date startDate;
                try {
                    startDate = df.parse(startDateString);
                    //String newDateString = df.format(startDate);
                    System.out.println(newDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/



                /*Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                formatter = new SimpleDateFormat("E dd MMM");
                String strDate = formatter.format(date);
                binding.locateCalendearView.setText(strDate);*/

                //System.out.println("ReceivedDatePickerInLocate" + binding.locateCalendearView.getText().toString());


                //locateCalendearView.getText().toString()+"T"+"00:00:00.000"+"Z");

                //Utils.bottomSheetDatePicker(getContext(), getActivity(), "", "", binding.locateCalendearView);
                bottomSheetLocateDatePickerInBooking(getContext(), getActivity(), "", "", binding.locateCalendearView);

            }
        });

        binding.searchLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocateCountryList();

            }
        });

        binding.ivLocateMyTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callMyTeamBottomSheet();

            }
        });

        binding.ivLocateFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callLocateFilterBottomSheet();

            }
        });

        binding.ivLocateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (keyClickedStats) {
                    binding.ivLocateKey.setImageDrawable(getResources().getDrawable(R.drawable.key_icon_orange));
                    binding.locateKeyStatusBlock.setVisibility(View.VISIBLE);
                    keyClickedStats = false;

                } else {
                    binding.ivLocateKey.setImageDrawable(getResources().getDrawable(R.drawable.locate_key));
                    binding.locateKeyStatusBlock.setVisibility(View.INVISIBLE);
                    keyClickedStats = true;
                }

            }
        });


        binding.first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //callCheckInBottomSheetToBook(selctedCode, key, id);
                //System.out.println("");

            }
        });


        //Initally Load Floor Details
        //initLoadFloorDetails(0);


        return root;
    }




    public void initLoadFloorDetails(int i) {
        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
        if (parentId > 0) {
            String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
            String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);

            if (buildingName == null && floorName == null) {
                binding.searchLocate.setHint("40th Bank Street,30th Floor");
            } else {
                binding.searchLocate.setHint(buildingName + "," + floorName);
            }

            //ForCoordinate
            int subParentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.SUB_PARENT_ID);
            boolean findCoordinateStatus = true;
            //getFloorDetails(subParentId,findCoordinateStatus);

            binding.locateProgressBar.setVisibility(View.VISIBLE);
            //Used For Desk Avaliability Checking
            getAvaliableDeskDetails(null, 0);
            getTeams();

            //Used For Car Parking Avaliability Checking
           /* getCarParkingSlots(parentId);
            getCarParkingAvalibilitySlots();
            carParkAvalibilityChecking();*/

            doInitCarAvalibilityHere(parentId);



            getLocateDeskRoomCarDesign(parentId, i);



        } else {
            Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
        }
    }

    private void doInitCarAvalibilityHere(int parentId) {


        getCarParkingSlots(parentId);
        getCarParkingAvalibilitySlots();

        int dateStatus=Utils.doDateCompareHere(binding.locateCalendearView.getText().toString());


        if(dateStatus==0){
            //unavaliable

            carParkingStatusModelList.clear();
            if(carParkingslots!=null){
                for (int i = 0; i <carParkingslots.size() ; i++) {

                    CarParkingStatusModel   carParkingStatusModel=new CarParkingStatusModel(carParkingslots.get(i).getCarParkingSlotId(),carParkingslots.get(i).getCode(),0);
                    System.out.println("CarParkUnAvaliable");
                    carParkingStatusModelList.add(carParkingStatusModel);

                }
            }
            System.out.println("CarParkUnAvaliable");
        }else if(dateStatus==1){
            //todayDate
            System.out.println("CarParktodayDate");
        }else if(dateStatus==2){
            //nextdayDate
            System.out.println("CarParknextdayDate");
        }


        if(dateStatus>0){
            carParkAvalibilityChecking();
        }




    }

    private void carParkAvalibilityChecking() {

        CarParkingslotsResponse carParkingslotsResponse=null;
        CarParkingStatusModel carParkingStatusModel=null;
        CarParkAvalibilityResponse carParkAvalibilityResponse=null;
        carParkingStatusModelList=new ArrayList<>();
        carParkingStatusModelList.clear();

        if (carParkingslots!=null) {

            for (int i = 0; i < carParkingslots.size(); i++) {

                carParkingslotsResponse=carParkingslots.get(i);

                if (carParkAvalibilityResponseList!=null) {

                    for (int j = 0; j < carParkAvalibilityResponseList.size(); j++) {

                        if (carParkingslotsResponse.getCarParkingSlotId() == carParkAvalibilityResponseList.get(j).getParkingSlotAvalibilityId()) {
                            carParkAvalibilityResponse = carParkAvalibilityResponseList.get(j);
                            //carParkingStatusModel=new CarParkingStatusModel()
                            if (carParkAvalibilityResponse.isBookedByElse() == false && carParkAvalibilityResponse.isBookedByUser() == false && carParkAvalibilityResponse.isAvailable() == true && (carParkingslots.get(i).getParkingSlotAvailability() == 1 && (carParkingslots.get(i).getAssignessList().size() == 0))) {
                                carParkingStatusModel=new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(),carParkingslotsResponse.getCode(),1);
                                System.out.println("CarParkAvaliable");
                               // ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_avaliable));
                                break;
                            } else if (carParkAvalibilityResponse.isBookedByElse() == false && carParkAvalibilityResponse.isBookedByUser() == false && carParkAvalibilityResponse.isAvailable() == true && carParkingslots.get(i).getParkingSlotAvailability() == 2) {
                                carParkingStatusModel=new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(),carParkingslotsResponse.getCode(),4);
                                System.out.println("CarParkingRequest");
                                //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_request));
                                break;
                            } else if (carParkAvalibilityResponse.isBookedByElse() == false && carParkAvalibilityResponse.isBookedByUser() == false && (carParkAvalibilityResponse.isAvailable() == false || carParkingslots.get(i).getParkingSlotAvailability() == 1)) {
                                carParkingStatusModel=new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(),carParkingslotsResponse.getCode(),0);
                                System.out.println("CarParkUnAvaliable");
                                //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                                break;
                            } else if (carParkAvalibilityResponse.isBookedByElse() == true) {
                                carParkingStatusModel=new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(),carParkingslotsResponse.getCode(),3);
                                System.out.println("CarParkingBookedOther");
                                //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_booked));
                                break;
                            } else if (carParkAvalibilityResponse.isBookedByUser() == true) {
                                carParkingStatusModel=new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(),carParkingslotsResponse.getCode(),2);
                                System.out.println("BookedForMe");
                                //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_bookedbyme));
                                break;
                            } else {
                                carParkingStatusModel=new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(),carParkingslotsResponse.getCode(),0);
                                System.out.println("CarParkUnAvaliable");
                                //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                                break;
                            }

                        }

                    }

                } else {
                    System.out.println("CarParkUnAvaliable");
                    carParkingStatusModel=new CarParkingStatusModel(carParkingslotsResponse.getCarParkingSlotId(),carParkingslotsResponse.getCode(),0);
                   // ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                }

                carParkingStatusModelList.add(carParkingStatusModel);

            }

        } else {
            System.out.println("CarParkUnAvaliable");
            //deskStatusModel=new DeskStatusModel(key,id,code,0);
            //deskStatusModelList.add(deskStatusModel);
            //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
        }

    }

    private void getCarParkingAvalibilitySlots() {

        binding.locateProgressBar.setVisibility(View.VISIBLE);

        //String toDate=Utils.getCurrentDate()+"T00:00:00Z";
        String toDate=binding.locateCalendearView.getText().toString()+"T00:00:00.000Z";
        //System.out.println("ToDateCheckoing"+toDate);

        //Add min and hour
        String startTime=Utils.addMinuteWithCurrentTime(1,2);
        String fromTime=startTime+".000Z";
        String endTime=Utils.addMinuteWithCurrentTime(2,3);
        String toTime=endTime+".000Z";

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CarParkAvalibilityResponse>> call = apiService.getCarParkingSlotAvalibility(toDate,fromTime,toTime);
        call.enqueue(new Callback<List<CarParkAvalibilityResponse>>() {
            @Override
            public void onResponse(Call<List<CarParkAvalibilityResponse>> call, Response<List<CarParkAvalibilityResponse>> response) {

                carParkAvalibilityResponseList=response.body();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },2000);

               // ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onFailure(Call<List<CarParkAvalibilityResponse>> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void getCarParkingSlots(int parentId) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        //dialog = ProgressDialog.showProgressBar(getContext());
        System.out.println("CarPArkingSlotCAlled");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CarParkingslotsResponse>> call = apiService.getCarParkingSlots(parentId);
        call.enqueue(new Callback<List<CarParkingslotsResponse>>() {
            @Override
            public void onResponse(Call<List<CarParkingslotsResponse>> call, Response<List<CarParkingslotsResponse>> response) {
                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                carParkingslots=response.body();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },2000);

                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                //ProgressDialog.dismisProgressBar(getContext(), dialog);

            }

            @Override
            public void onFailure(Call<List<CarParkingslotsResponse>> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

    private void getTeams() {
        binding.locateProgressBar.setVisibility(View.VISIBLE);

        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TeamsResponse>> call = apiService.getTeams();
        call.enqueue(new Callback<List<TeamsResponse>>() {
            @Override
            public void onResponse(Call<List<TeamsResponse>> call, Response<List<TeamsResponse>> response) {

                teamsResponseList = response.body();
                //ProgressDialog.dismisProgressBar(getContext(), dialog);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },2000);

                binding.locateProgressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onFailure(Call<List<TeamsResponse>> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }
        });

    }


    private void getLocateDeskRoomCarDesign(int parentId, int id) {
        binding.locateProgressBar.setVisibility(View.VISIBLE);

        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        int floorPosition = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentId);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                locateCountryResposeList = response.body();
                int totalDeskSize = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks().size();
                System.out.println("TotalSize" + totalDeskSize);



                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                //getAvaliableDeskDetails(locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks(),0);


                //getFloorCoordinates(locateCountryResposeList.get(floorPosition).getCoordinates());


                //addDottedLine();


                List<String> valueList = new ArrayList<>();
                if (locateCountryResposeList.get(floorPosition).getItems() != null) {

                    int itemTotalSize=locateCountryResposeList.get(floorPosition).getItems().size();

                    for (String key : locateCountryResposeList.get(floorPosition).getItems().keySet()) {

                        valueList = locateCountryResposeList.get(floorPosition).getItems().get(key);

                        addView(valueList, key, floorPosition,itemTotalSize);

                    }

                } else {
                    Toast.makeText(getContext(), "No Data", Toast.LENGTH_LONG).show();
                }


                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                //ProgressDialog.dismisProgressBar(getContext(), dialog);

            }
        });
    }


    private void getFloorCoordinates(List<List<Integer>> coordinateList) {


        System.out.println("CoordinateSize" + coordinateList.size());
        //List<Point> pointList=new ArrayList<>();
        for (int i = 0; i < coordinateList.size(); i++) {

            System.out.println("CoordinateData" + i + "position" + "size " + coordinateList.get(i).size());

            Point point = new Point(coordinateList.get(i).get(0) + 40, coordinateList.get(i).get(1) + 20);
            pointList.add(point);


        }
        System.out.println("PointListSize" + pointList.size());

        //binding.secondLayout.postInvalidate();
        //binding.secondLayout.invalidate();

        if (pointList.size() > 0) {
            MyCanvasDraw myCanvasDraw = new MyCanvasDraw(getContext(), pointList);

            /*if(canvasss==1){
                myCanvasDraw.setDrawMethod();
            }*/


            //myCanvasDraw.postInvalidate();
            //myCanvasDraw.invalidate();
            //binding.secondLayout.postInvalidate();
            //binding.secondLayout.invalidate();
            //myCanvasDraw.setInvalidate();
            binding.secondLayout.addView(myCanvasDraw);

            //binding.secondLayout.onFinishTemporaryDetach();
            System.out.println("AlreadyHaveCanvashObject");

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("OnResumeCalled");
    }

    private void addDottedLine() {
 
        /* View dottView = getLayoutInflater().inflate(R.layout.layout_dotted_line, null, false);
         ImageView ivDesk = dottView.findViewById(R.id.dottedImage);
         RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
 
         relativeLayout.leftMargin =300;
         relativeLayout.topMargin = 500;
         ivDesk.setLayoutParams(relativeLayout);

         binding.firstLayout.addView(dottView);*/

        //MyCanvasDraw myCanvasDraw = new MyCanvasDraw(getContext(), pointList);
        //binding.secondLayout.addView(myCanvasDraw);


    }



    @SuppressLint("ResourceType")
    private void addView(List<String> valueList, String key, int floorPosition,int itemTotalSize) {

        System.out.println("ItemTotalSize"+itemTotalSize);
        System.out.println("ReceivedKeyInAddView" + key);
        //String startDate="2022-07-26 18:30:00";
        //String endDate="2022-07-26 23:30:00";

        String startDate=binding.locateCalendearView.getText().toString()+" "+binding.locateStartTime.getText().toString()+":00";
        String endDate=binding.locateCalendearView.getText().toString()+" "+binding.locateEndTime.getText().toString()+":00";


        System.out.println("AddViewDataPrintedHere"+binding.locateCalendearView.getText().toString()+" "+binding.locateStartTime.getText().toString()+" "+binding.locateEndTime.getText().toString());
        System.out.println("AddViewStart"+startDate);
        System.out.println("AddViewEnd"+endDate);

        //Desk Avaliablity Checking Split key to get id and code
        String[] result = key.split("_");
        int id = Integer.parseInt(result[0]);
        String code = result[1];

        deskView = getLayoutInflater().inflate(R.layout.layout_item_desk, null, false);
        ImageView ivDesk = deskView.findViewById(R.id.ivDesk);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        DeskStatusModel deskStatusModel=null;
        List<DeskStatusModel> deskStatusModelList=new ArrayList<>();


        //Desk Avaliablity Checking
        if (code.equals(AppConstants.DESK)) {

            if(teamDeskAvaliabilityList!=null){

                for (int i = 0; i < teamDeskAvaliabilityList.size(); i++) {

                    boolean wasAssigned = false;

                    if (id == teamDeskAvaliabilityList.get(i).getDeskId()) {

                        DeskAvaliabilityResponse.TeamDeskAvaliabilityList teamDeskAvaliability = teamDeskAvaliabilityList.get(i);

                        //GET TEAM ID
                        boolean getTeamId = false;
                        TeamsResponse teamsResponse = new TeamsResponse();
                        if (!getTeamId) {
                            if(teamsResponseList!=null) {
                                for (int j = 0; j < teamsResponseList.size(); j++) {
                                    if (teamDeskAvaliability.getTeamId() == teamsResponseList.get(j).getId()) {

                                        teamsResponse = teamsResponseList.get(j);
                                        getTeamId = true;
                                    }
                                }
                            }
                        }//

                        System.out.println("TotalAvaliableDeskId" + teamDeskAvaliability.getDeskId());
                        System.out.println("CurrentDateAndTimeIn24HoursFormat" + Utils.getCurrentTimeIn24HourFormat());

                        //GetCurrentDate and Offset
                        String offSetAddedDate = Utils.addingHoursToCurrentDate(teamDeskAvaliability.getCurrentTimeZoneOffset());

                        System.out.println("NewlyAddedDateWithTime" + offSetAddedDate);
                        System.out.println("NewlyAddedDateAlone" + Utils.splitGetDate(offSetAddedDate));


                        for (int j = 0; j < teamDeskAvaliability.getAvailableTimeSlotsList().size(); j++) {

                            if (!wasAssigned) {

                                DeskAvaliabilityResponse.TeamDeskAvaliabilityList.AvailableTimeSlots availableTimeSlots = teamDeskAvaliability.getAvailableTimeSlotsList().get(j);

                                System.out.println("SlotFrom" + availableTimeSlots.getFrom());
                                System.out.println("SlotTo" + availableTimeSlots.getTo());


                                int dateComparsionResult = Utils.compareTwoDates(startDate, offSetAddedDate);
                                int second = Utils.compareTwoDates(startDate, Utils.removeTandZInDate(availableTimeSlots.getFrom()));
                                int third = Utils.compareTwoDates(endDate, Utils.removeTandZInDate(availableTimeSlots.getTo()));

                                if (dateComparsionResult == 1) {
                                    System.out.println("BookingUnavaliable");
                                    ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));

                                    deskStatusModel=new DeskStatusModel(key,id,code,0);

                                } else if (teamDeskAvaliability.isPartiallyAvailable() == true && second == 2 && third == 1) {

                                    if (teamDeskAvaliability.isBookedByUser() == true) {
                                        System.out.println("BookingbookedForMe");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_bookedbyme));
                                        deskStatusModel=new DeskStatusModel(key,id,code,2);

                                    } else if (teamDeskAvaliability.isBookedByElse() == true) {
                                        System.out.println("BookingBookedOther");
                                        deskStatusModel=new DeskStatusModel(key,id,code,3);
                                    } else if (teamsResponse.getDeskCount() != 0 && teamsResponse.getAutomaticApprovalStatus() == 2) {
                                        System.out.println("BookingAvaliable");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_avaliable));
                                        deskStatusModel=new DeskStatusModel(key,id,code,1);

                                    } else if (teamDeskAvaliability.getTeamId() == SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID)) {
                                        System.out.println("BookingAvaliable");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_avaliable));
                                        deskStatusModel=new DeskStatusModel(key,id,code,1);
                                    } else {

                                        if (teamsResponse != null && teamsResponse.getAutomaticApprovalStatus() == 3) {
                                            System.out.println("BookingUnavaliable");
                                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                                            deskStatusModel=new DeskStatusModel(key,id,code,0);
                                        } else {
                                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_request));
                                            System.out.println("BookingRequest");
                                            deskStatusModel=new DeskStatusModel(key,id,code,4);
                                        }
                                    }
                                    wasAssigned = true;

                                } else {
                                    System.out.println("BookingUnavaliable");
                                    ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                                    deskStatusModel=new DeskStatusModel(key,id,code,0);
                                    if (teamDeskAvaliability.isBookedByUser() == true) {
                                        System.out.println("BookingbookedForMe");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_bookedbyme));
                                        deskStatusModel=new DeskStatusModel(key,id,code,2);
                                    } else if (teamDeskAvaliability.isBookedByElse() == true) {
                                        System.out.println("BookingBookedOther");
                                        deskStatusModel=new DeskStatusModel(key,id,code,3);
                                    }
                                }


                            }

                            deskStatusModelList.add(deskStatusModel);


                        }


                    }


                }
            }else {
                ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                deskStatusModel=new DeskStatusModel(key,id,code,0);
                deskStatusModelList.add(deskStatusModel);
            }


        }else if(code.equals(AppConstants.CAR_PARKING)){

            //CarPark Avalibility Checking

            //if(!carParkingCheckingStatus) {
                for (int i = 0; i < carParkingStatusModelList.size(); i++) {

                    if (id==carParkingStatusModelList.get(i).getId()) {

                        if(carParkingStatusModelList.get(i).getStatus()==0){
                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                        }else if(carParkingStatusModelList.get(i).getStatus()==1){
                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_avaliable));
                        }else if(carParkingStatusModelList.get(i).getStatus()==2){
                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_bookedbyme));
                        }else if(carParkingStatusModelList.get(i).getStatus()==3){
                            //ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.des));
                        }else if(carParkingStatusModelList.get(i).getStatus()==4){
                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_request));
                        }

                    }
                   // System.out.println("CarParkTesting" + carParkingStatusModelList.get(i).getKey() + " " + carParkingStatusModelList.get(i).getStatus());

                }

               // carParkingCheckingStatus=true;
            //}




        }


        //Head Facing
        for (int i = 0; i < valueList.size(); i++) {

            if (i == 2) {
                int rotateValue = Integer.parseInt(valueList.get(2));
                if (rotateValue == 1) {
                    ivDesk.setRotation(90);
                } else if (rotateValue == 2) {
                    ivDesk.setRotation(135);
                } else if (rotateValue == 3) {
                    ivDesk.setRotation(180);
                } else if (rotateValue == 4) {
                    ivDesk.setRotation(225);
                } else if (rotateValue == 5) {
                    ivDesk.setRotation(270);
                } else if (rotateValue == 6) {
                    ivDesk.setRotation(315);
                } else if (rotateValue == 7) {
                    ivDesk.setRotation(360);
                } else if (rotateValue == 8) {
                    ivDesk.setRotation(405);
                }
            }
            System.out.println("PositionValue" + valueList.get(i));

        }

        //

        //SetImageHereBased on Code
             /* String[] result = key.split("_");
              String splitValue=result[1];

             if(splitValue.equals(AppConstants.DESK)){

             }else if(splitValue.equals(AppConstants.MEETING)){

             }else if(splitValue.equals(AppConstants.CAR_PARKING)){

             }*/

        //Set Image Based on Position
        int x = Integer.parseInt(valueList.get(0));
        int y = Integer.parseInt(valueList.get(1));

        relativeLayout.leftMargin = x;
        relativeLayout.topMargin = y;
        relativeLayout.width = 80;
        relativeLayout.height = 67;
        ivDesk.setLayoutParams(relativeLayout);


        //OnClickListener
        ivDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                List<String> onClickValue = locateCountryResposeList.get(floorPosition).getItems().get(key);
                      /*for (int j = 0; j <onClickValue.size() ; j++) {
                         System.out.println("OnClickedKey"+key+"OnClickedValue"+onClickValue.get(j));
                     }*/

                //Split key to get id and code
                String[] result = key.split("_");
                int id = Integer.parseInt(result[0]);
                String code = result[1];
                String selctedCode = "";
                int meetingRoomId=0;
                String meetingRoomName="";

                //Get code based on id
                if (code.equals(AppConstants.DESK)) {
                    //Get Code For Desk
                    for (int i = 0; i < locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks().size(); i++) {

                        if (id == locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks().get(i).getDesksId()) {

                            selctedCode = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks().get(i).getDeskCode();

                            System.out.println("ClickedCodeIs" + locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks().get(i).getDeskCode());

                        }

                    }

                } else if (code.equals(AppConstants.MEETING)) {
                    //Get Code For MEETING
                    for (int i = 0; i < locateCountryResposeList.get(floorPosition).getLocationItemLayout().getMeetingRoomsList().size(); i++) {

                        if (id == locateCountryResposeList.get(floorPosition).getLocationItemLayout().getMeetingRoomsList().get(i).getMeetingRoomId()) {

                            meetingRoomName = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getMeetingRoomsList().get(i).getMeetingRoomCode();
                            meetingRoomId=locateCountryResposeList.get(floorPosition).getLocationItemLayout().getMeetingRoomsList().get(i).getMeetingRoomId();
                        }

                    }


                } else if (code.equals(AppConstants.CAR_PARKING)) {
                    //Get Code For CAR_PARKING
                    for (int i = 0; i < locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().size(); i++) {
                        if (id == locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getId()) {

                            selctedCode = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getCode();
                        }

                    }

                }

               /* if(deskStatusModelList.size()>0){
                    for (int i = 0; i <deskStatusModelList.size() ; i++) {

                        System.out.println("DESKNAMETOREQUESTTISERVER"+deskStatusModelList.get(i).getKey()+" "+deskStatusModelList.get(i).getStatus());

                    }
                }*/

                int requestTeamId=0,requestTeamDeskId=0;
                if (code.equals("3")) {
                    if(deskStatusModelList!=null){
                        for (int i = 0; i <deskStatusModelList.size() ; i++) {
                            if(key.equals(deskStatusModelList.get(i).getKey())){

                                if(deskStatusModelList.get(i).getStatus()==1){

                                    //DeskDescription
                                    getDescriptionUsingDeskId(id);


                                    //Avaliable Booking
                                    //Booking Bottom Sheet
                                    callDeskBookingnBottomSheet(selctedCode, key, id, code,requestTeamId,requestTeamDeskId);

                                }else if(deskStatusModelList.get(i).getStatus()==4){
                                    //Booking Request
                                    DeskStatusModel deskStatusModel1=deskStatusModelList.get(i);
                                    for (int j = 0; j <teamDeskAvaliabilityList.size() ; j++) {

                                        if(deskStatusModel1.getId()==teamDeskAvaliabilityList.get(j).getDeskId()){

                                            requestTeamId=teamDeskAvaliabilityList.get(j).getTeamId();
                                            requestTeamDeskId=teamDeskAvaliabilityList.get(j).getTeamDeskId();
                                            System.out.println("RequstedTeamId"+teamDeskAvaliabilityList.get(j).getTeamId());
                                            System.out.println("RequestedTeamDeskId"+teamDeskAvaliabilityList.get(j).getTeamDeskId());

                                        }

                                    }

                                    //DeskDescription
                                    getDescriptionUsingDeskId(id);

                                    //Booking Request Bottom Sheet
                                    callDeskBookingnBottomSheet(selctedCode, key, id, code,requestTeamId,requestTeamDeskId);


                                }else if(deskStatusModelList.get(i).getStatus()==2){
                                    //Booking Edit BottomSheet
                                    getBookingListToEdit(code);

                                }else if(deskStatusModelList.get(i).getStatus()==0){
                                    //Unavaliable
                                    callDeskUnavaliable(selctedCode, key, id, code,requestTeamId,requestTeamDeskId);
                                }


                            }
                        }

                    }


                }else if(code.equals("5")){

                    if(carParkingStatusModelList!=null){

                        for (int i = 0; i <carParkingStatusModelList.size() ; i++) {

                            if (id==carParkingStatusModelList.get(i).getId()) {

                                if(carParkingStatusModelList.get(i).getStatus()==0){
                                    callDeskUnavaliable(selctedCode, key, id, code,requestTeamId,requestTeamDeskId);
                                }else if(carParkingStatusModelList.get(i).getStatus()==1){

                                    //CarDescription
                                    getCarDescriptionUsingCardId(id);

                                    //CarBooking
                                    callDeskBookingnBottomSheet(selctedCode, key, id, code,requestTeamId,requestTeamDeskId);
                                }else if(carParkingStatusModelList.get(i).getStatus()==2){
                                    //EditCarParking
                                    getCarBookingEditList(id);

                                }else if(carParkingStatusModelList.get(i).getStatus()==3){

                                }else if(carParkingStatusModelList.get(i).getStatus()==4){

                                    getCarDescriptionUsingCardId(id);
                                    //CarRequestBooking
                                    callDeskBookingnBottomSheet(selctedCode, key, id, code,requestTeamId,requestTeamDeskId);
                                }else if(carParkingStatusModelList.get(i).getStatus()==0){
                                    callDeskUnavaliable(selctedCode, key, id, code,requestTeamId,requestTeamDeskId);
                                }

                            }

                        }

                    }


                } else if (code.equals("4")) {

                    System.out.println("SelectedMeetingRoomId"+meetingRoomId);

                    getMeetingRoomDescription(meetingRoomId);

                    //MeetingRoomBooking
                    callMeetingRoomBookingBottomSheet(meetingRoomId,meetingRoomName);


                    //getMeetingBookingListToEdit(meetingRoomId);
                    //MeetingRoomEditListAdapter
                   // callMeetingRoomEditListAdapterBottomSheet();
                    

                }

            }
        });

        binding.firstLayout.addView(deskView);


    }

    private void getMeetingBookingListToEdit(int meetingRoomId) {

        //String startDate=binding.locateCalendearView.getText().toString()+"T00:00:00.000Z";
        //String endDate=binding.locateCalendearView.getText().toString()+"T00:00:00.000Z";
        String startDate=binding.locateCalendearView.getText().toString();
        String endDate=binding.locateCalendearView.getText().toString();
        int[] roomid=new int[1];
        roomid[0]=meetingRoomId;
        String roomId= Arrays.toString(roomid);

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MeetingListToEditResponse>> call=apiService.getMeetingListToEdit(startDate,endDate,roomId);
        call.enqueue(new Callback<List<MeetingListToEditResponse>>() {
           @Override
           public void onResponse(Call<List<MeetingListToEditResponse>> call, Response<List<MeetingListToEditResponse>> response) {

               List<MeetingListToEditResponse> meetingListToEditResponseList  =response.body();

               callMeetingRoomEditListAdapterBottomSheet(meetingListToEditResponseList);


           }

           @Override
           public void onFailure(Call<List<MeetingListToEditResponse>> call, Throwable t) {

           }
       });
    }

    private void callMeetingRoomEditListAdapterBottomSheet(List<MeetingListToEditResponse> meetingListToEditResponseList) {
        RecyclerView rvMeeingEditList;
        TextView editClose,editDate;
        LinearLayoutManager linearLayoutManager;

        BottomSheetDialog locateMeetEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateMeetEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvMeeingEditList = locateMeetEditBottomSheet.findViewById(R.id.rvEditList);
        editClose=locateMeetEditBottomSheet.findViewById(R.id.editClose);
        editDate=locateMeetEditBottomSheet.findViewById(R.id.editDate);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMeeingEditList.setLayoutManager(linearLayoutManager);
        rvMeeingEditList.setHasFixedSize(true);

        MeetingListToEditAdapter meetingListToEditAdapter=new MeetingListToEditAdapter(getContext(),meetingListToEditResponseList,this);
        rvMeeingEditList.setAdapter(meetingListToEditAdapter);

        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateMeetEditBottomSheet.dismiss();
            }
        });

        //getMeetingListToEdit


        locateMeetEditBottomSheet.show();
    }

    private void callDeskUnavaliable(String selctedCode, String key, int id, String code, int requestTeamId, int requestTeamDeskId) {

        TextView unAvalibaleDeskName,tvUnavaliableBack;

      /*  BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateCheckInBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_unavalible_bottomsheet,
                new RelativeLayout(getContext())));*/

        BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_locate_unavalible_bottomsheet, null);
        locateCheckInBottomSheet.setContentView(view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(500);


        unAvalibaleDeskName=locateCheckInBottomSheet.findViewById(R.id.unAvalibaleDeskName);
        tvUnavaliableBack=locateCheckInBottomSheet.findViewById(R.id.tvUnavaliableBack);

        unAvalibaleDeskName.setText(selctedCode);


        tvUnavaliableBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateCheckInBottomSheet.dismiss();
            }
        });

        locateCheckInBottomSheet.show();


    }

    private void getMeetingRoomDescription(int meetingRoomId) {

        //dialog = ProgressDialog.showProgressBar(getContext());
        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MeetingRoomDescriptionResponse>  call=apiService.getMeetingRoomDescription(meetingRoomId);
        call.enqueue(new Callback<MeetingRoomDescriptionResponse>() {
            @Override
            public void onResponse(Call<MeetingRoomDescriptionResponse> call, Response<MeetingRoomDescriptionResponse> response) {
                MeetingRoomDescriptionResponse meetingRoomDescriptionResponse=response.body();
                meetingRoomDescription=meetingRoomDescriptionResponse.getDescription();
                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<MeetingRoomDescriptionResponse> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void getCarDescriptionUsingCardId(int id) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CarParkingDescriptionResponse> call=apiService.getCarParkingDescription(id);
        call.enqueue(new Callback<CarParkingDescriptionResponse>() {
            @Override
            public void onResponse(Call<CarParkingDescriptionResponse> call, Response<CarParkingDescriptionResponse> response) {

                CarParkingDescriptionResponse carParkingDescriptionResponse =response.body();
                if(carParkingDescriptionResponse!=null){
                    carParkDescription=carParkingDescriptionResponse.getDescription();
                }


                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<CarParkingDescriptionResponse> call, Throwable t) {

                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }
        });
    }


    private void getDescriptionUsingDeskId(int id) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DeskDescriptionResponse> call=apiService.getDiskDescription(id);
        call.enqueue(new Callback<DeskDescriptionResponse>() {
            @Override
            public void onResponse(Call<DeskDescriptionResponse> call, Response<DeskDescriptionResponse> response) {
                DeskDescriptionResponse deskDescriptionResponse=response.body();
                if(deskDescriptionResponse!=null) {
                    deskDescriotion = deskDescriptionResponse.getDeskDescription();
                }

                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<DeskDescriptionResponse> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void callMeetingRoomBookingBottomSheet(int meetingRoomId, String meetingRoomName) {

        TextView startRoomTime,endTRoomime,editRoomBookingContinue,editRoomBookingBack,tvMeetingRoomDescription,roomTitle;
        EditText etParticipants,etSubject,etComments;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_room_booking,
                new RelativeLayout(getContext()))));

        startRoomTime = bottomSheetDialog.findViewById(R.id.tvRoomStartTime);
        endTRoomime = bottomSheetDialog.findViewById(R.id.tvRoomEndTime);
        etParticipants=bottomSheetDialog.findViewById(R.id.etParticipants);
        etSubject=bottomSheetDialog.findViewById(R.id.etSubject);

        etComments=bottomSheetDialog.findViewById(R.id.etComments);
        editRoomBookingContinue=bottomSheetDialog.findViewById(R.id.editRoomBookingContinue);
        editRoomBookingBack=bottomSheetDialog.findViewById(R.id.editRoomBookingBack);
        tvMeetingRoomDescription=bottomSheetDialog.findViewById(R.id.meetingRoomDescription);
        roomTitle=bottomSheetDialog.findViewById(R.id.roomTitle);


        roomTitle.setText(meetingRoomName);

        if(meetingRoomDescription !=null){
            tvMeetingRoomDescription.setText("Description:"+meetingRoomDescription);
        }else {
            tvMeetingRoomDescription.setText("Description:");
        }



        startRoomTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), startRoomTime, "", "");
            }
        });

        endTRoomime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), endTRoomime, "", "");
            }
        });

        editRoomBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        editRoomBookingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            boolean status=true;

                if(startRoomTime.getText().toString().equals("9:00")){
                    Toast.makeText(getContext(),"Please Select Start Time",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }
                if(endTRoomime.getText().toString().equals("9:00")){
                    Toast.makeText(getContext(),"Please Select End Time",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }
                String subject=etSubject.getText().toString();
                String comment=etComments.getText().toString();
                if(subject.isEmpty() || subject.equals("") || subject==null){
                    Toast.makeText(getContext(),"Please Enter Subject",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }

                if(comment.isEmpty() || comment.equals("") || comment==null){
                    Toast.makeText(getContext(),"Please Enter Comment",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }

                if(status){

                   bottomSheetDialog.dismiss();
                   doMeetingRoomBooking(meetingRoomId,startRoomTime.getText().toString(),endTRoomime.getText().toString(),subject,comment);

               }else {
                    Toast.makeText(getContext(),"Please Enter All Details",Toast.LENGTH_LONG).show();
                }

            }
        });


        bottomSheetDialog.show();

    }

    private void doMeetingRoomBooking(int meetingRoomId, String startRoomTime, String endRoomTime,String subject,String comment) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);

        MeetingRoomRequest meetingRoomRequest=new MeetingRoomRequest();
        meetingRoomRequest.setMeetingRoomId(meetingRoomId);
        meetingRoomRequest.setMsTeams(false);
        meetingRoomRequest.setHandleRecurring(false);
        meetingRoomRequest.setOnlineMeeting(false);

        MeetingRoomRequest.Changeset m=meetingRoomRequest.new Changeset();
        m.setId(0);
        m.setDate(binding.locateCalendearView.getText().toString() + "T" + "00:00:00.000" + "Z");

        MeetingRoomRequest.Changeset.Changes changes=m.new Changes();
        changes.setFrom(getCurrentDate() + "" + "T" + startRoomTime + ":" + "00" + "." + "000" + "Z");
        changes.setMyto(getCurrentDate() + "" + "T" + endRoomTime + ":" + "00" + "." + "000" + "Z");
        changes.setComments(comment);
        changes.setSubject(subject);
        changes.setRequest(false);

        m.setChanges(changes);

        List<MeetingRoomRequest.Changeset> changesetList=new ArrayList<>();
        changesetList.add(m);

        meetingRoomRequest.setChangesets(changesetList);

        List<MeetingRoomRequest.Changeset.Changes.Attendees> attendeesList=new ArrayList<>();
        changes.setAttendees(attendeesList);

        List<MeetingRoomRequest.Changeset.Changes.ExternalAttendees> externalAttendeesList=new ArrayList<>();
        changes.setExternalAttendees(externalAttendeesList);

        List<MeetingRoomRequest.DeleteIds> deleteIdsList=new ArrayList<>();
        meetingRoomRequest.setDeletedIds(deleteIdsList);

        System.out.println("BookingMeetingRoom"+meetingRoomRequest);

        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call=apiService.doMeetingRoomBook(meetingRoomRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                BaseResponse baseResponse=response.body();
                System.out.println("MeetingRoomBookResponse"+baseResponse.getResultCode());

                openCheckoutDialog("Booking Succcessfull");

               //ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }
        });



    }

    private void getCarBookingEditList(int id) {

        //String startDate="2022-07-25T00:00:00.000Z";
        //String endDate="2022-07-25T00:00:00.000Z";

        String startDate=binding.locateCalendearView.getText().toString()+" "+binding.locateStartTime.getText().toString()+":00.000Z";
        String endDate=binding.locateCalendearView.getText().toString()+" "+binding.locateEndTime.getText().toString()+":00.000Z";

        //dialog = ProgressDialog.showProgressBar(getContext());
        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CarParkingForEditResponse> call=apiService.getCarParkingEditList(startDate,endDate,id);
        call.enqueue(new Callback<CarParkingForEditResponse>() {
            @Override
            public void onResponse(Call<CarParkingForEditResponse> call, Response<CarParkingForEditResponse> response) {

                CarParkingForEditResponse carParkingForEditResponse=response.body();

                CallCarBookingEditList(carParkingForEditResponse);

               /* for (int i = 0; i <carParkingForEditResponse.getCarParkBookings().size() ; i++) {
                    System.out.println("CarParkingEditListVeHicleNumber"+carParkingForEditResponse.getCarParkBookings().get(i).getVehicleRegNumber());

                }*/

               // ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<CarParkingForEditResponse> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void CallCarBookingEditList(CarParkingForEditResponse carParkingForEditResponse) {

        RecyclerView rvCarEditList;
        TextView editClose,editDate,bookingName;
        LinearLayoutManager linearLayoutManager;

        BottomSheetDialog locateCarEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateCarEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvCarEditList = locateCarEditBottomSheet.findViewById(R.id.rvEditList);
        editClose=locateCarEditBottomSheet.findViewById(R.id.editClose);
        editDate=locateCarEditBottomSheet.findViewById(R.id.editDate);
        bookingName=locateCarEditBottomSheet.findViewById(R.id.bookingName);

        bookingName.setText("Car Booking");

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCarEditList.setLayoutManager(linearLayoutManager);
        rvCarEditList.setHasFixedSize(true);

        CarListToEditAdapter  carListToEditAdapter=new CarListToEditAdapter(getContext(),carParkingForEditResponse.getCarParkBookings(),this);
        rvCarEditList.setAdapter(carListToEditAdapter);

        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateCarEditBottomSheet.dismiss();
            }
        });

        locateCarEditBottomSheet.show();
    }

    //DeskBookingEditList
    private void callBottomSheetToEdit(BookingForEditResponse bookingForEditResponse, String code) {

        RecyclerView rvEditList;
        TextView editClose,editDate,bookingName;
        LinearLayoutManager linearLayoutManager;

        BottomSheetDialog locateEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvEditList = locateEditBottomSheet.findViewById(R.id.rvEditList);
        editClose=locateEditBottomSheet.findViewById(R.id.editClose);
        editDate=locateEditBottomSheet.findViewById(R.id.editDate);
        bookingName=locateEditBottomSheet.findViewById(R.id.bookingName);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvEditList.setLayoutManager(linearLayoutManager);
        rvEditList.setHasFixedSize(true);

        BookingListToEditAdapter bookingListToEditAdapter=new BookingListToEditAdapter(getContext(),bookingForEditResponse.getBookings(),this,code,bookingForEditResponse.getTeamDeskAvailabilities());
        rvEditList.setAdapter(bookingListToEditAdapter);

        //Show Here Current Date
        editDate.setText(binding.locateCalendearView.getText().toString());

        if(code.equals("3")){
            bookingName.setText("Desk Booking");
        }


        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateEditBottomSheet.dismiss();
            }
        });

        locateEditBottomSheet.show();

    }

    private void getBookingListToEdit(String code) {
        binding.locateProgressBar.setVisibility(View.VISIBLE);
        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<BookingForEditResponse> call=apiService.getBookingsForEdit(SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAM_ID),
                SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAMMEMBERSHIP_ID),
                Utils.getCurrentDate(),
                Utils.getCurrentDate());

        call.enqueue(new Callback<BookingForEditResponse>() {
            @Override
            public void onResponse(Call<BookingForEditResponse> call, Response<BookingForEditResponse> response) {

                BookingForEditResponse bookingForEditResponse=response.body();

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                callBottomSheetToEdit(bookingForEditResponse,code);

                //ProgressDialog.dismisProgressBar(getContext(),dialog);




            }

            @Override
            public void onFailure(Call<BookingForEditResponse> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(),dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAvaliableDeskDetails(List<LocateCountryRespose.LocationItemLayout.Desks> desks, int id) {



        binding.locateProgressBar.setVisibility(View.VISIBLE);
       // dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //"2022-07-05T00:00:00.000Z"
        //"2000-01-01T15:50:38.000Z"
        //"2000-01-01T18:00:00.000Z"


        //String toDate=Utils.getCurrentDate()+"T00:00:00Z";
        //System.out.println("ToDateCheckoing"+toDate);

        //Add min and hour
       /* String startTime=Utils.addMinuteWithCurrentTime(1,2);
        String fromTime=startTime+".000Z";
        String endTime=Utils.addMinuteWithCurrentTime(2,9);
        String toTime=endTime+".000Z";*/
        String toDate=binding.locateCalendearView.getText().toString()+"T00:00:00Z";
        String fromTime=binding.locateCalendearView.getText().toString()+" "+binding.locateStartTime.getText().toString()+":00"+".000Z";
        String toTime=binding.locateCalendearView.getText().toString()+" "+binding.locateEndTime.getText().toString()+":00"+".000Z";

        //System.out.println("DateAndStatTimeAndEndTime"+toDate+" "+fromTime+" "+toTime);

        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
        //Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId, now, now, now);
        Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId, toDate, fromTime, toTime);

        call.enqueue(new Callback<DeskAvaliabilityResponse>() {
            @Override
            public void onResponse(Call<DeskAvaliabilityResponse> call, Response<DeskAvaliabilityResponse> response) {

                DeskAvaliabilityResponse deskAvaliabilityResponseList = response.body();

                teamDeskAvaliabilityList = deskAvaliabilityResponseList.getTeamDeskAvaliabilityList();

                //ProgressDialog.dismisProgressBar(getContext(), dialog);



                System.out.println("InsideDataValaibelity");
                //System.out.println("AllDeskSize"+desks.size());
                // System.out.println("ActiveDeskSize"+ deskAvaliabilityResponseList.getLocationDesksList().size());


                //GetTeamDeskIdForBooking
                if (id > 0) {
                    for (int i = 0; i < deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().size(); i++) {

                        if (id == deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().get(i).getDeskId()) {
                            teamDeskIdForBooking = deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().get(i).getTeamDeskId();
                            System.out.println("TeamDeskIdForBooking " + teamDeskIdForBooking);
                        }


                    }
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },2000);

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                /*List<String> deskCodeList = new ArrayList<>();
                for (int i = 0; i < desks.size(); i++) {
                    deskCodeList.add(desks.get(i).getDeskCode());
                }

                if (deskAvaliabilityResponseList.getLocationDesksList() != null) {
                    for (int i = 0; i < deskAvaliabilityResponseList.getLocationDesksList().size(); i++) {

                        if (deskCodeList.contains(deskAvaliabilityResponseList.getLocationDesksList().get(i).getCode())) {
                            System.out.println("AvaliableDesks" + deskAvaliabilityResponseList.getLocationDesksList().get(i).getCode());
                        }

                    }
                }*/


                //ProgressDialog.dismisProgressBar(getContext(), dialog);


            }

            @Override
            public void onFailure(Call<DeskAvaliabilityResponse> call, Throwable t) {

                System.out.println("Failure" + t.getMessage().toString());
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                //ProgressDialog.dismisProgressBar(getContext(), dialog);
            }
        });

    }


    private void getLocateCountryList() {


        if (Utils.isNetworkAvailable(getContext())) {

            //dialog = ProgressDialog.showProgressBar(getContext());
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<LocateCountryRespose>> call = apiService.getLocationCountryList();
            call.enqueue(new Callback<List<LocateCountryRespose>>() {
                @Override
                public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                    List<LocateCountryRespose> locateCountryResposes = response.body();

                    System.out.println("LocateCountryList" + locateCountryResposes.size());

                    CallFloorBottomSheet(locateCountryResposes);


                    //ProgressDialog.dismisProgressBar(getContext(), dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                    //ProgressDialog.dismisProgressBar(getContext(), dialog);
                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
            });

        } else {
            Utils.toastMessage(getContext(), "Please Enable Internet");
        }


    }

    //Select Floor Plan BottomSheeet
    private void CallFloorBottomSheet(List<LocateCountryRespose> locateCountryResposes) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(getLayoutInflater().
                inflate(R.layout.bottom_sheet_locate_floor_filter,
                        new RelativeLayout(getContext())));


        bsLocationSearch = bottomSheetDialog.findViewById(R.id.bsLocationSearch);

        String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
        String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);

        if (buildingName == null && floorName == null) {
            bsLocationSearch.setText("40th Bank Street,30th Floor");
        } else {
            bsLocationSearch.setText(buildingName + "," + floorName);
        }

        countryBlock = bottomSheetDialog.findViewById(R.id.bsCountryBlock);
        statBlock = bottomSheetDialog.findViewById(R.id.bsStateBlock);
        streetBlock = bottomSheetDialog.findViewById(R.id.bsStreetBlock);
        floorBlock = bottomSheetDialog.findViewById(R.id.bsFloorBlock);

        country = bottomSheetDialog.findViewById(R.id.bsCountry);
        state = bottomSheetDialog.findViewById(R.id.bsState);
        street = bottomSheetDialog.findViewById(R.id.bsStreet);
        floor = bottomSheetDialog.findViewById(R.id.bsfloor);


        //Get initial data
        //getCountryStateStreetAndFloorDetails();

        rvCountry = bottomSheetDialog.findViewById(R.id.rvCountry);
        rvState = bottomSheetDialog.findViewById(R.id.rvState);
        rvStreet = bottomSheetDialog.findViewById(R.id.rvStreet);
        rvFloor = bottomSheetDialog.findViewById(R.id.rvFloorList);


        country.setText("Global Location");
        //country.setText(locateCountryResposes.get(0).getName());
        rvCountry.setVisibility(View.INVISIBLE);
        statBlock.setVisibility(View.INVISIBLE);
        rvState.setVisibility(View.INVISIBLE);
        streetBlock.setVisibility(View.INVISIBLE);
        rvStreet.setVisibility(View.INVISIBLE);
        floorBlock.setVisibility(View.INVISIBLE);

        back = bottomSheetDialog.findViewById(R.id.bsBack);
        bsApply = bottomSheetDialog.findViewById(R.id.bsApply);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();


            }
        });

        bsApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                canvasss = 1;

                binding.firstLayout.removeAllViews();
                //binding.secondLayout.removeAllViews();
                initLoadFloorDetails(1);
                bottomSheetDialog.dismiss();
            }
        });


        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                country.setText("Global Location");
                rvCountry.setVisibility(View.VISIBLE);
                showCountryListInAdapter(locateCountryResposes);

                statBlock.setVisibility(View.GONE);
                rvState.setVisibility(View.GONE);
                streetBlock.setVisibility(View.GONE);
                rvStreet.setVisibility(View.GONE);
                floorBlock.setVisibility(View.GONE);
                rvFloor.setVisibility(View.INVISIBLE);

                //getLocateCountryList();
            }
        });

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state.setText("City");
                streetBlock.setVisibility(View.INVISIBLE);
                rvStreet.setVisibility(View.INVISIBLE);
                rvFloor.setVisibility(View.INVISIBLE);
                callCountrysChildData(stateId);


            }
        });

        //Building
        street.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                street.setText("Building");
                floorBlock.setVisibility(View.INVISIBLE);
                floor.setVisibility(View.INVISIBLE);
                rvStreet.setVisibility(View.VISIBLE);
                rvFloor.setVisibility(View.INVISIBLE);
            }
        });

        floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvFloor.setVisibility(View.VISIBLE);
            }
        });



         /*linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
         rvCountry.setLayoutManager(linearLayoutManager);
         rvCountry.setHasFixedSize(true);*/

        bottomSheetDialog.show();

    }

    private void showCountryListInAdapter(List<LocateCountryRespose> locateCountryResposes) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCountry.setLayoutManager(linearLayoutManager);
        rvCountry.setHasFixedSize(true);

        showCountryAdapter = new ShowCountryAdapter(getContext(), locateCountryResposes, this, "COUNTRY");
        rvCountry.setAdapter(showCountryAdapter);

    }

    //Interface To select
    @Override
    public void onSelect(LocateCountryRespose locateCountryRespose, String identifier) {


        switch (identifier) {
            case "COUNTRY":
                state.setText("City");
                stateId = locateCountryRespose.getLocateCountryId();
                country.setText(locateCountryRespose.getName());
                callCountrysChildData(locateCountryRespose.getLocateCountryId());
                break;

            case "STATE":
                state.setText(locateCountryRespose.getName());
                SessionHandler.getInstance().save(getContext(), AppConstants.BUILDING, locateCountryRespose.getName());
                rvState.setVisibility(View.GONE);
                streetBlock.setVisibility(View.VISIBLE);
                street.setVisibility(View.VISIBLE);
                street.setText("Building");

                SessionHandler.getInstance().saveInt(getContext(), AppConstants.SUB_PARENT_ID, locateCountryRespose.getLocateCountryId());
                getFloorDetails(locateCountryRespose.getLocateCountryId(), false);


                System.out.println("SubParentIdAndItsPosition" + locateCountryRespose.getLocateCountryId() + " ");

                break;

            case "FLOOR":
                floorBlock.setVisibility(View.VISIBLE);
                floor.setVisibility(View.VISIBLE);
                floor.setText(locateCountryRespose.getName());
                SessionHandler.getInstance().save(getContext(), AppConstants.FLOOR, locateCountryRespose.getName());
                rvStreet.setVisibility(View.GONE);
                rvFloor.setVisibility(View.VISIBLE);
                SessionHandler.getInstance().saveInt(getContext(), AppConstants.PARENT_ID, locateCountryRespose.getLocateCountryId());

                if (locateCountryRespose.getSupportZoneLayoutItemsList() != null) {
                    getOtherSubZoneLayoutItems(locateCountryRespose.getSupportZoneLayoutItemsList());
                }

                //Final
                getDeskRoomCarParkingDetails(locateCountryRespose.getLocateCountryId());


                break;


        }

        if (Utils.isNetworkAvailable(getContext())) {

        } else {
            Utils.toastMessage(getContext(), "Please Enable Internet");
        }

    }

    private void getOtherSubZoneLayoutItems(List<LocateCountryRespose.SupportZoneLayoutItems> supportZoneLayoutItemsList) {

        //List<Point> pointList=new ArrayList<>();

        pointList.clear();

        for (int i = 0; i < supportZoneLayoutItemsList.size(); i++) {

            System.out.println("supportZoneLayoutItemsSize" + supportZoneLayoutItemsList.size());
            System.out.println("supportZoneLayoutItemsSize" + supportZoneLayoutItemsList.get(i).getTitle());
            System.out.println("CorrrdnateSize" + supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().size());

            for (int j = 0; j < supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().size(); j++) {

                System.out.println("DATAATATA" + supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(0) + " " + supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(1));

                Point point = new Point(supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(0),
                        supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(1));
                pointList.add(point);

            }

        }

       /* for (int i = 0; i <pointList.size() ; i++) {
            System.out.println("PointListDate "+pointList.get(i).getX()+" "+pointList.get(i).getY());
        }*/

        //ProgressDialog.dismisProgressBar(getContext(), dialog);
        /*  Point point=new Point(coordinateList.get(i).get(0),coordinateList.get(i).get(1));

         */

    }

    private void callCountrysChildData(int parentId) {
        //dialog = ProgressDialog.showProgressBar(getContext());
        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentId);

        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposes = response.body();

                for (int i = 0; i < locateCountryResposes.size(); i++) {
                    System.out.println("CountrychildName" + locateCountryResposes.get(i).getName());
                }

                rvCountry.setVisibility(View.GONE);
                statBlock.setVisibility(View.VISIBLE);
                rvState.setVisibility(View.VISIBLE);

                showCountryChildListInAdapter(locateCountryResposes);

                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void showCountryChildListInAdapter(List<LocateCountryRespose> locateCountryResposes) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvState.setLayoutManager(linearLayoutManager);
        rvState.setHasFixedSize(true);

        showCountryAdapter = new ShowCountryAdapter(getContext(), locateCountryResposes, this, "STATE");
        rvState.setAdapter(showCountryAdapter);
    }

    private void getFloorDetails(int floorId, boolean findCoordinateStatus) {
        binding.locateProgressBar.setVisibility(View.VISIBLE);
        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(floorId);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposes = response.body();

                //ProgressDialog.dismisProgressBar(getContext(), dialog);

                if (findCoordinateStatus) {
                    int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
                    //ProgressDialog.dismisProgressBar(getContext(), dialog);
                    for (int i = 0; i < locateCountryResposes.size(); i++) {

                        if (parentId == locateCountryResposes.get(i).getLocateCountryId()) {
                            if (locateCountryResposes.get(i).getSupportZoneLayoutItemsList() != null) {
                                // ProgressDialog.dismisProgressBar(getContext(), dialog);
                                getOtherSubZoneLayoutItems(locateCountryResposes.get(i).getSupportZoneLayoutItemsList());
                            }
                        }

                    }
                } else {
                    rvStreet.setVisibility(View.VISIBLE);
                    showFloorListInAdapter(locateCountryResposes);

                    //ProgressDialog.dismisProgressBar(getContext(), dialog);

                }


                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                // ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void showFloorListInAdapter(List<LocateCountryRespose> locateCountryResposes) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvStreet.setLayoutManager(linearLayoutManager);
        rvStreet.setHasFixedSize(true);

        showCountryAdapter = new ShowCountryAdapter(getContext(), locateCountryResposes, this, "FLOOR");
        rvStreet.setAdapter(showCountryAdapter);

    }

    //Final
    private void getDeskRoomCarParkingDetails(int parentId) {
        //dialog = ProgressDialog.showProgressBar(getContext());
        binding.locateProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentId);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposeList = response.body();

                System.out.println("InsideFloorDetails");

                for (int j = 0; j < locateCountryResposeList.size(); j++) {
                    System.out.println("InsideFloorName" + locateCountryResposeList.get(j).getName());
                }

                binding.locateProgressBar.setVisibility(View.INVISIBLE);

                showFloorImageAndNameInAdapter(locateCountryResposeList);


                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    //Final Adapter
    private void showFloorImageAndNameInAdapter(List<LocateCountryRespose> locateCountryResposeList) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFloor.setLayoutManager(linearLayoutManager);
        rvFloor.setHasFixedSize(true);

        floorAdapter = new FloorAdapter(getContext(), locateCountryResposeList, this, "FLOOR_NAME");
        rvFloor.setAdapter(floorAdapter);
    }



    //BookBottomSheet
    private void callDeskBookingnBottomSheet(String selctedCode, String key, int id, String code, int requestTeamId, int requestTeamDeskId) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },1000);

        TextView tvDescription,tvLocateDeskBookLocation;

        System.out.println("BookingRequestDetail" + selctedCode + " " + key + " " + id + " " + code);

        BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateCheckInBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_checkin_bottomsheet,
                new RelativeLayout(getContext())));
        bookingDateBlock = locateCheckInBottomSheet.findViewById(R.id.bookingDateBlock);
        bookingStartBlock = locateCheckInBottomSheet.findViewById(R.id.bookingStartBlock);
        bookingEndBlock = locateCheckInBottomSheet.findViewById(R.id.bookingEndBlock);


        locateCheckInDate = locateCheckInBottomSheet.findViewById(R.id.locateCheckInDate);
        locateCheckInTime = locateCheckInBottomSheet.findViewById(R.id.locateCheckInTime);
        locateCheckoutTime = locateCheckInBottomSheet.findViewById(R.id.locateCheckoutTime);

        locateDeskName = locateCheckInBottomSheet.findViewById(R.id.locateDeskName);
        editBookingContinue = locateCheckInBottomSheet.findViewById(R.id.editBookingContinue);
        editBookingBack = locateCheckInBottomSheet.findViewById(R.id.editBookingBack);

        tvLocateDeskBookLocation=locateCheckInBottomSheet.findViewById(R.id.tvLocateDeskBookLocation);
        tvDescription=locateCheckInBottomSheet.findViewById(R.id.tvDescription);

        String buildingName = SessionHandler.getInstance().get(getContext(), AppConstants.BUILDING);
        String floorName = SessionHandler.getInstance().get(getContext(), AppConstants.FLOOR);

        tvLocateDeskBookLocation.setText(buildingName+","+floorName);

        if(deskDescriotion!=null){
            tvDescription.setText("Description:"+deskDescriotion);
        }else {
            tvDescription.setText("Description:");
        }

        //Car Parking Booking Widget
        bookingCommentBlock = locateCheckInBottomSheet.findViewById(R.id.bookingCommentBlock);
        bookingVechicleRegtBlock = locateCheckInBottomSheet.findViewById(R.id.bookingVechicleRegtBlock);
        etComment = locateCheckInBottomSheet.findViewById(R.id.etComment);
        etComment.setText("Comment");
        etVehicleReg = locateCheckInBottomSheet.findViewById(R.id.etVehicleReg);
        etVehicleReg.setText("TN20CX2443");

        //Desk Avaliability Checking
        if (code.equals("3")) {
            bookingCommentBlock.setVisibility(View.GONE);
            bookingVechicleRegtBlock.setVisibility(View.GONE);
            getAvaliableDeskDetails(null, id);
        } else if (code.equals("5")) {
            bookingCommentBlock.setVisibility(View.VISIBLE);
            bookingVechicleRegtBlock.setVisibility(View.VISIBLE);
            getParkingSlotId(selctedCode);
        }

        bookingDateBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetDatePicker(getContext(), getActivity(), "", "", locateCheckInDate);

                //callBookingDatePickerBottomSheet();
            }
        });

        bookingStartBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), locateCheckInTime, "", "");
                //callBookingTimePickerBottomSheet();
            }
        });

        bookingEndBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), locateCheckoutTime, "", "");
                //ProgressDialog.dismisProgressBar(getContext(),dialog);
            }
        });


        locateDeskName.setText(selctedCode);

        editBookingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean status=true;

                if(locateCheckInTime.getText().toString().equals("9:00")){
                    Toast.makeText(getContext(),"Please Select Start Time",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }
                if(locateCheckoutTime.getText().toString().equals("9:00")){
                    Toast.makeText(getContext(),"Please Select End Time",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }

                if(locateCheckInDate.getText().toString().equals("10 Aug")){
                    Toast.makeText(getContext(),"Please Select Date",Toast.LENGTH_LONG).show();
                    status=false;
                }

                if(status){

                    locateCheckInBottomSheet.dismiss();
                    //ProgressDialog.dismisProgressBar(getContext(),dialog);

                    //dialog = ProgressDialog.showProgressBar(getContext());
                    if (code.equals("3")) {

                        if (requestTeamId > 0 && requestTeamDeskId > 0) {
                            //Request Desk Booking
                            requestDeskBooking(requestTeamId, requestTeamDeskId);

                        } else {
                            //Desk Booking
                            deskBooking();
                        }

                    } else if (code.equals("5")) {
                        //Car Booking and Car Request Booking
                        carParkingRequest();
                    }

                }


            }
        });

        editBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateCheckInBottomSheet.dismiss();
            }
        });


        locateCheckInBottomSheet.show();
    }

    private void requestDeskBooking(int requestTeamId, int requestTeamDeskId) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);

        System.out.println("APIITNGRATIONREQUESTDESKBOOKING");

        LocateDeskBookingRequest locateDeskBookingRequest = new LocateDeskBookingRequest();
        locateDeskBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
        locateDeskBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

        LocateDeskBookingRequest.ChangeSets changeSets = locateDeskBookingRequest.new ChangeSets();
        changeSets.setChangeSetId(0);

        changeSets.setChangeSetDate(locateCheckInDate.getText().toString() + "T" + "00:00:00.000" + "Z");

        LocateDeskBookingRequest.ChangeSets.Changes changes = changeSets.new Changes();
        changes.setUsageTypeId(7);

        changes.setFrom(getCurrentDate() + "" + "T" + locateCheckInTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
        changes.setTo(getCurrentDate() + "" + "T" + locateCheckoutTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
        changes.setTimeZoneId("India Standard Time");
        changes.setTeamDeskId(null);
        changes.setRequestedTeamId(requestTeamId);
        changes.setRequestedTeamDeskId(requestTeamDeskId);
        changes.setTypeOfCheckIn(1);

        changeSets.setChanges(changes);

        List<LocateDeskBookingRequest.ChangeSets> changeSetsList = new ArrayList<>();
        changeSetsList.add(changeSets);

        locateDeskBookingRequest.setChangeSets(changeSetsList);

        List<LocateDeskBookingRequest.DeleteIds> deleteIdsList = new ArrayList<>();
        //deleteIdsList.add(deleteIds);

        locateDeskBookingRequest.setDeletedIds(deleteIdsList);

        System.out.println("BookingRequestObject" + locateDeskBookingRequest);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.doRequestDeskBooking(locateDeskBookingRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                BaseResponse baseResponse=response.body();

                openCheckoutDialog("Booking Request Successfull");

                binding.locateProgressBar.setVisibility(View.INVISIBLE);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callInitView();
                    }
                },1000);


                /*if(baseResponse!=null){
                    Toast.makeText(getContext(),baseResponse.getResultCode(),Toast.LENGTH_LONG).show();
                }*/


//                System.out.println("DeskRequestBookingDesk"+baseResponse.getResultCode());

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void getParkingSlotId(String key) {

        int floorPosition = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION);
        for (int i = 0; i < locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().size(); i++) {

            System.out.println("SelectedCodeEquatl" + locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getCode());
            if (key.equals(locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getCode())) {
                System.out.println("SelectedCarParkingSlotId" + locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getId());
                selectedCarParkingSlotId = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getId();
            }

        }


    }

    private void callBookingDatePickerBottomSheet() {



        /*BottomSheetDialog bottomSheetDatePicker = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDatePicker.setContentView((getActivity()).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_date_picker,
                new RelativeLayout(getActivity())));

        TextView calContinue = bottomSheetDatePicker.findViewById(R.id.calenderContinue);
        TextView calBack = bottomSheetDatePicker.findViewById(R.id.calenderBack);
        CalendarView calendarView=bottomSheetDatePicker.findViewById(R.id.datePicker);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String yearInString=String.valueOf(year);
                String monthInString=String.valueOf(month);
                String dayInString=String.valueOf(dayOfMonth);
                String dateInString= yearInString+"-"+monthInString+"-"+dayInString;
                locateCheckInDate.setText(dateInString);
            }
        });

        calContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDatePicker.dismiss();
            }
        });

        calBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDatePicker.dismiss();
            }
        });


        bottomSheetDatePicker.show();*/

    }

    private void openCheckoutDialog(String mesg) {
        Dialog popDialog = new Dialog(getActivity());
        popDialog.setContentView(R.layout.layout_checkout_success);
        popDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView checkDialogClose = popDialog.findViewById(R.id.checkDialogClose);
        TextView dialogMsg = popDialog.findViewById(R.id.dialog_text);
        dialogMsg.setText("" + mesg);

        checkDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog.dismiss();
            }
        });
        popDialog.show();
    }

    private void deskBooking() {

        //dialog = ProgressDialog.showProgressBar(getContext());
        binding.locateProgressBar.setVisibility(View.VISIBLE);

        System.out.println("DeskBookingRequested");

        LocateBookingRequest locateBookingRequest = new LocateBookingRequest();
        locateBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
        locateBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

        //locateBookingRequest.setTeamId(9);
        //locateBookingRequest.setTeamMembershipId(12);

        LocateBookingRequest.ChangeSets changeSets = locateBookingRequest.new ChangeSets();
        changeSets.setChangeSetId(0);
        //changeSets.setChangeSetDate("2022-07-14T00:00:00.000Z");
        changeSets.setChangeSetDate
                (locateCheckInDate.getText().toString() + "T" + "00:00:00.000" + "Z");

        LocateBookingRequest.ChangeSets.Changes changes = changeSets.new Changes();
        changes.setUsageTypeId(2);
        //changes.setFrom("2022-07-21T20:15:00.000Z");
        //changes.setTo("2022-07-21T21:30:00.000Z");

        changes.setFrom(getCurrentDate() + "" + "T" + locateCheckInTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
        changes.setTo(getCurrentDate() + "" + "T" + locateCheckoutTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
        changes.setTimeZoneId("India Standard Time");
        changes.setTeamDeskId(teamDeskIdForBooking);
        changes.setTypeOfCheckIn(1);

        changeSets.setChanges(changes);

        List<LocateBookingRequest.ChangeSets> changeSetsList = new ArrayList<>();
        changeSetsList.add(changeSets);

        locateBookingRequest.setChangeSetsList(changeSetsList);

        LocateBookingRequest.DeleteIds deleteIds = locateBookingRequest.new DeleteIds();
        List<LocateBookingRequest.DeleteIds> deleteIdsList = new ArrayList<>();
        //deleteIdsList.add(deleteIds);

        locateBookingRequest.setDeleteIdsList(deleteIdsList);

        System.out.println("BookingRequestObject" + locateBookingRequest);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.doDeskBooking(locateBookingRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                BaseResponse baseResponse = response.body();
                if (baseResponse != null) {
//                    Toast.makeText(getContext(), baseResponse.getResultCode(), Toast.LENGTH_LONG).show();
                    openCheckoutDialog("Booking Succcessfull");

                    callInitView();


                } else {
                    Toast.makeText(getContext(), "Not Avaliable", Toast.LENGTH_LONG).show();
                }

                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                System.out.println("BookingSuccessInLocate");

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                System.out.println("BookingfailureInLocate");
                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void carParkingRequest() {

        binding.locateProgressBar.setVisibility(View.VISIBLE);
        //dialog = ProgressDialog.showProgressBar(getContext());
        System.out.println("carParkingRequested");

        LocateCarParkBookingRequest locateCarParkBookingRequest = new LocateCarParkBookingRequest();
        locateCarParkBookingRequest.setParkingSlotId(selectedCarParkingSlotId);

        LocateCarParkBookingRequest.CarParkingChangeSets carParkingChangeSets = locateCarParkBookingRequest.new CarParkingChangeSets();
        carParkingChangeSets.setId(0);
        carParkingChangeSets.setDate(locateCheckInDate.getText().toString() + "T" + "00:00:00.000" + "Z");

        LocateCarParkBookingRequest.CarParkingChangeSets.CarParkingChanges carParkingChanges = carParkingChangeSets.new CarParkingChanges();
        carParkingChanges.setFrom(getCurrentDate() + "" + "T" + locateCheckInTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
        carParkingChanges.setTo(getCurrentDate() + "" + "T" + locateCheckoutTime.getText().toString() + ":" + "00" + "." + "000" + "Z");
        carParkingChanges.setComments(etComment.getText().toString());
        carParkingChanges.setBookedForUser(SessionHandler.getInstance().getInt(getContext(), AppConstants.USER_ID));
        carParkingChanges.setVehicleRegNumber(etVehicleReg.getText().toString());

        carParkingChangeSets.setCarParkingChanges(carParkingChanges);

        List<LocateCarParkBookingRequest.CarParkingChangeSets> carParkingChangeSetsList = new ArrayList<>();
        carParkingChangeSetsList.add(carParkingChangeSets);

        locateCarParkBookingRequest.setCarParkingChangeSetsList(carParkingChangeSetsList);

        LocateCarParkBookingRequest.CarParkingDeleteIds carParkingDeleteIds = locateCarParkBookingRequest.new CarParkingDeleteIds();
        List<LocateCarParkBookingRequest.CarParkingDeleteIds> deleteIdsList = new ArrayList<>();
        //deleteIdsList.add(carParkingDeleteIds);

        locateCarParkBookingRequest.setDeleteIdsList(deleteIdsList);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.doCarParkingBooking(locateCarParkBookingRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                BaseResponse baseResponse = response.body();
                if (baseResponse != null) {
                    callInitView();
                    openCheckoutDialog("Booking Succcessfull");
                } else {
                    Toast.makeText(getContext(), "Parking Not Avaliable", Toast.LENGTH_LONG).show();
                }
                /*if(response.code()==200){
                    Toast.makeText(getContext(),"Car Parking Booked Successfully",Toast.LENGTH_LONG).show();
                }*/

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });


    }


    @Override
    public void onEditClick(BookingForEditResponse.Bookings bookings, String code, List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities) {

        TextView startTime,endTime,date,editBookingBack;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_booking,
                new RelativeLayout(getContext()))));

        startTime = bottomSheetDialog.findViewById(R.id.start_time);
        endTime = bottomSheetDialog.findViewById(R.id.end_time);
        date=bottomSheetDialog.findViewById(R.id.date);
        deskRoomName=bottomSheetDialog.findViewById(R.id.tv_desk_room_name);
        TextView continueEditBook=bottomSheetDialog.findViewById(R.id.editBookingContinue);
        LinearLayout llDeskLayout=bottomSheetDialog.findViewById(R.id.ll_desk_layout);
        RelativeLayout repeatBlock=bottomSheetDialog.findViewById(R.id.rl_repeat_block);
        RelativeLayout teamsBlock=bottomSheetDialog.findViewById(R.id.rl_teams_layout);
        TextView tvComments=bottomSheetDialog.findViewById(R.id.tv_comments);
        EditText commentRegistration=bottomSheetDialog.findViewById(R.id.ed_registration);
        editBookingBack=bottomSheetDialog.findViewById(R.id.editBookingBack);

        TextView select=bottomSheetDialog.findViewById(R.id.select_desk_room);

        //Need To Change Date Here
        date.setText(binding.locateCalendearView.getText().toString());

        if(code.equals("3")){


            repeatBlock.setVisibility(View.GONE);
            teamsBlock.setVisibility(View.GONE);
            tvComments.setVisibility(View.GONE);
            commentRegistration.setVisibility(View.GONE);

            startTime.setText( Utils.splitTime(bookings.getFrom()));
            endTime.setText(Utils.splitTime(bookings.getMyto()));
            // date.setText(""+Utils.dayDateMonthFormat(bookings.getDate()));
            deskRoomName.setText(bookings.getDeskCode());
        }else {

        }


        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), startTime, "", "");
                    //Utils.bottomSheetTimePicker(getContext(),getActivity(),startTime,"Start Time","");
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), endTime, "", "");
                //Utils.bottomSheetTimePicker(getContext(),getActivity(),endTime,"End Time","");

            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeskBottomSheetDialogToSelectDeskCode(teamDeskAvailabilities);
            }
        });

        editBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        continueEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean status=true;

                if(startTime.getText().toString().equals("9:00")){
                    Toast.makeText(getContext(),"Please Select Start Time",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }
                if(endTime.getText().toString().equals("9:00")){
                    Toast.makeText(getContext(),"Please Select End Time",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }

               if(status){
                   bottomSheetDialog.dismiss();
                    //Edit DeskBooking
                    doEditDeskBooking(bookings,startTime.getText().toString(),endTime.getText().toString());
                }





            }
        });

        bottomSheetDialog.show();


    }

    private void doEditDeskBooking(BookingForEditResponse.Bookings bookings, String startTime, String endTime) {

        //dialog=ProgressDialog.showProgressBar(getContext());
        binding.locateProgressBar.setVisibility(View.VISIBLE);

        LocateBookingRequest locateBookingRequest = new LocateBookingRequest();
        locateBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAM_ID));
        locateBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(), AppConstants.TEAMMEMBERSHIP_ID));

        LocateBookingRequest.ChangeSets changeSets = locateBookingRequest.new ChangeSets();
        changeSets.setChangeSetId(0);

        //changeSets.setChangeSetDate(startTim+ "T" + "00:00:00.000" + "Z");
        changeSets.setChangeSetDate(Utils.splitDate(bookings.getDate())+"T" +"00:00:00.000"+"Z");

        LocateBookingRequest.ChangeSets.Changes changes = changeSets.new Changes();
        changes.setUsageTypeId(2);

        changes.setFrom(getCurrentDate() + "" + "T" + startTime + ":" + "00" + "." + "000" + "Z");
        changes.setTo(getCurrentDate() + "" + "T" + endTime + ":" + "00" + "." + "000" + "Z");
        changes.setTimeZoneId("India Standard Time");
        if(selectedDeskId>0){
            changes.setTeamDeskId(selectedDeskId);
        }else {
            changes.setTeamDeskId(bookings.teamDeskId);
        }

        changes.setTypeOfCheckIn(1);

        changeSets.setChanges(changes);

        List<LocateBookingRequest.ChangeSets> changeSetsList = new ArrayList<>();
        changeSetsList.add(changeSets);
        locateBookingRequest.setChangeSetsList(changeSetsList);

        LocateBookingRequest.DeleteIds deleteIds = locateBookingRequest.new DeleteIds();
        List<LocateBookingRequest.DeleteIds> deleteIdsList = new ArrayList<>();
        //deleteIdsList.add(deleteIds);

        locateBookingRequest.setDeleteIdsList(deleteIdsList);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.doDeskBooking(locateBookingRequest);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {


                BaseResponse baseResponse = response.body();
                if (baseResponse != null) {
                    callInitView();
                    openCheckoutDialog("Booking Succcessfull");

                } else {
                    Toast.makeText(getContext(), "Not Avaliable"+baseResponse.getResultCode(), Toast.LENGTH_LONG).show();
                }

               // ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                System.out.println("BookingSuccessInLocate");

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                //ProgressDialog.dismisProgressBar(getContext(), dialog);
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });



    }

    private void callDeskBottomSheetDialogToSelectDeskCode(List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities) {

        RecyclerView rvDeskRecycler;
        DeskListRecyclerAdapter deskListRecyclerAdapter;
        TextView bsRepeatBack;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_select_desk,
                new RelativeLayout(getContext()))));

        rvDeskRecycler= bottomSheetDialog.findViewById(R.id.desk_list_select_recycler);
        bsRepeatBack=bottomSheetDialog.findViewById(R.id.bsDeskBack);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDeskRecycler.setLayoutManager(linearLayoutManager);
        rvDeskRecycler.setHasFixedSize(true);

        deskListRecyclerAdapter =new DeskListRecyclerAdapter(getContext(),this,getActivity(),teamDeskAvailabilities,getContext(),bottomSheetDialog);
        rvDeskRecycler.setAdapter(deskListRecyclerAdapter);

        bottomSheetDialog.show();

    }

    @Override
    public void onSelectDesk(int deskId, String deskName) {
        deskRoomName.setText(""+deskName);
        selectedDeskId= deskId;
    }

    @Override
    public void onCarEditClick(CarParkingForEditResponse.CarParkBooking carParkBooking) {

        TextView startTime,endTime,date,editBookingBack;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_edit_booking,
                new RelativeLayout(getContext()))));

        startTime = bottomSheetDialog.findViewById(R.id.start_time);
        endTime = bottomSheetDialog.findViewById(R.id.end_time);
        date=bottomSheetDialog.findViewById(R.id.date);
        deskRoomName=bottomSheetDialog.findViewById(R.id.tv_desk_room_name);
        TextView continueEditBook=bottomSheetDialog.findViewById(R.id.editBookingContinue);
        LinearLayout llDeskLayout=bottomSheetDialog.findViewById(R.id.ll_desk_layout);
        RelativeLayout repeatBlock=bottomSheetDialog.findViewById(R.id.rl_repeat_block);
        RelativeLayout teamsBlock=bottomSheetDialog.findViewById(R.id.rl_teams_layout);
        TextView tvComments=bottomSheetDialog.findViewById(R.id.tv_comments);
        EditText commentRegistration=bottomSheetDialog.findViewById(R.id.ed_registration);
        EditText etBookedBy=bottomSheetDialog.findViewById(R.id.etBookedBy);
        editBookingBack=bottomSheetDialog.findViewById(R.id.editBookingBack);

        TextView select=bottomSheetDialog.findViewById(R.id.select_desk_room);

        repeatBlock.setVisibility(View.GONE);
        teamsBlock.setVisibility(View.GONE);
        llDeskLayout.setVisibility(View.GONE);

        etBookedBy.setVisibility(View.VISIBLE);
        etBookedBy.setText(carParkBooking.getBookedForUserName());
        tvComments.setText("Registration number");
        commentRegistration.setHint("Registration number");

        startTime.setText(Utils.splitTime(carParkBooking.getFrom()));
        endTime.setText(Utils.splitTime(carParkBooking.getMyto()));

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), startTime, "", "");
                //Utils.bottomSheetTimePicker(getContext(),getActivity(),startTime,"Start Time","");
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), endTime, "", "");
                //Utils.bottomSheetTimePicker(getContext(),getActivity(),endTime,"End Time","");

            }
        });

        editBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        continueEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean status=true;

                if(startTime.getText().toString().equals("9:00")){
                    Toast.makeText(getContext(),"Please Select Start Time",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }
                if(endTime.getText().toString().equals("9:00")){
                    Toast.makeText(getContext(),"Please Select End Time",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }


                if(status){
                    //Edit CarParkBooking
                    bottomSheetDialog.dismiss();
                    doEditCarParkBooking(carParkBooking);
                }



            }
        });

        bottomSheetDialog.show();

    }

    private void doEditCarParkBooking(CarParkingForEditResponse.CarParkBooking carParkBooking) {

        binding.locateProgressBar.setVisibility(View.VISIBLE);

        //From Calender
        //String startDate="2022-07-25T11:00:00.000Z";
        String startDate=binding.locateCalendearView.getText().toString()+" "+binding.locateStartTime.getText().toString()+":00.000Z";
        //String endDate=binding.locateCalendearView.getText().toString()+" "+binding.locateEndTime.getText().toString()+":00";


        LocateCarParkEditRequest locateCarParkEditRequest = new LocateCarParkEditRequest();
        locateCarParkEditRequest.setParkingSlotId(carParkBooking.getParkingSlotId());

        List<LocateCarParkEditRequest.CarParkingChangeSets> carParkingChangeSetsList = new ArrayList<>();

        LocateCarParkEditRequest.CarParkingChangeSets carParkingChangeSets = locateCarParkEditRequest.new CarParkingChangeSets();
        carParkingChangeSets.setId(carParkBooking.getId());
        carParkingChangeSets.setDate(startDate);

        LocateCarParkEditRequest.CarParkingChangeSets.CarParkingChanges carParkingChanges = carParkingChangeSets.new CarParkingChanges();
        carParkingChanges.setVehicleRegNumber(carParkBooking.getVehicleRegNumber());

        carParkingChangeSets.setCarParkingChanges(carParkingChanges);

        carParkingChangeSetsList.add(carParkingChangeSets);

        locateCarParkEditRequest.setCarParkingChangeSetsList(carParkingChangeSetsList);

        LocateCarParkEditRequest.CarParkingDeleteIds carParkingDeleteIds = locateCarParkEditRequest.new CarParkingDeleteIds();
        List<LocateCarParkEditRequest.CarParkingDeleteIds> deleteIdsList = new ArrayList<>();
        //deleteIdsList.add(carParkingDeleteIds);

        locateCarParkEditRequest.setDeleteIdsList(deleteIdsList);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.doCarParkingEdit(locateCarParkEditRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                openCheckoutDialog("Success");

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callInitView();
                    }
                },2000);

                System.out.println("CarEditSuccessfull");

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });


    }


    //MyTeamBottomSheet
    private void callMyTeamBottomSheet() {

        TextView myTeamClose;

        BottomSheetDialog myTeamBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        myTeamBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_myteam_bottomsheet,
                new RelativeLayout(getContext())));

        rvMyTeam = myTeamBottomSheet.findViewById(R.id.rvLocateMyTeam);
        myTeamClose = myTeamBottomSheet.findViewById(R.id.myTeamClose);


        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMyTeam.setLayoutManager(linearLayoutManager);
        rvMyTeam.setHasFixedSize(true);


        myTeamClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTeamBottomSheet.dismiss();
            }
        });

        List<String> stringName = new ArrayList<>();
        stringName.add("Bessie Cooper");
        stringName.add("Francene Vandyne");
        stringName.add("Cody Fisher");


        locateMyTeamAdapter = new LocateMyTeamAdapter(getContext(), stringName);
        rvMyTeam.setAdapter(locateMyTeamAdapter);


        myTeamBottomSheet.show();


    }

    private void callLocateFilterBottomSheet() {

        RecyclerView locateFilterMainRV;
        ValuesPOJO valuesPOJO;
        ArrayList<DataModel> mList;
        ItemAdapter adapter;

        TextView locateFilterCancel, locateFilterApply;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((this).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_locate_filter,
                new RelativeLayout(getContext())));

        locateFilterCancel = bottomSheetDialog.findViewById(R.id.locateFilterCancel);
        locateFilterApply = bottomSheetDialog.findViewById(R.id.locateFilterApply);


        locateFilterMainRV = bottomSheetDialog.findViewById(R.id.locateFilterMainRV);
        locateFilterMainRV.setHasFixedSize(true);
        locateFilterMainRV.setLayoutManager(new LinearLayoutManager(getContext()));


        locateFilterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        locateFilterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        mList = new ArrayList<>();

        //list1
        ArrayList<ValuesPOJO> nestedList1 = new ArrayList<>();

        valuesPOJO = new ValuesPOJO("Monitor", false);
        nestedList1.add(valuesPOJO);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Adjustable height", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Laptop stand", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("USB_C Dock", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Charge point", false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Standing desk", false);
        nestedList1.add(valuesPOJO);

        ArrayList<ValuesPOJO> nestedList2 = new ArrayList<>();

        valuesPOJO = new ValuesPOJO("Single", false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Double", false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Ac", false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Non-AC", false);
        nestedList2.add(valuesPOJO);


        mList.add(new DataModel(nestedList1, "Workspaces"));
        mList.add(new DataModel(nestedList2, "Rooms"));

        adapter = new ItemAdapter(mList);
        locateFilterMainRV.setAdapter(adapter);

        bottomSheetDialog.show();

    }


    public void bottomSheetLocateDatePickerInBooking(Context mContext, Activity activity, String title, String date, TextView locateCheckInDateCal){

        BottomSheetDialog bottomSheetDatePicker = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDatePicker.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_date_picker,
                new RelativeLayout(activity)));

        TextView calContinue = bottomSheetDatePicker.findViewById(R.id.calenderContinue);
        TextView calBack = bottomSheetDatePicker.findViewById(R.id.calenderBack);
        CalendarView calendarView=bottomSheetDatePicker.findViewById(R.id.datePicker);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String yearInString=String.valueOf(year);

                int actualMonth=month+1;
                String monthInStringFormat;

                if(actualMonth>=10){
                    monthInStringFormat=String.valueOf(actualMonth);
                }else {
                    String monthInString=String.valueOf(actualMonth);
                    monthInStringFormat="0"+monthInString;
                }
                String dateInString="";

                System.out.println("ContinuPrintHere"+locateCheckInDateCal.getText());
                String dayInString=String.valueOf(dayOfMonth);
                dateInString= yearInString+"-"+monthInStringFormat+"-"+dayInString;
                System.out.println("PickedDate"+dateInString);


                locateCheckInDateCal.setText(""+dateInString);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Date currrentDate=null,d2=null;
                try {
                    currrentDate=formatter.parse(formatter.format(date));
                    d2 = formatter.parse(dateInString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(currrentDate.getDate()==d2.getDate()){
                    System.out.println("BothDateEqual");
                }else if(currrentDate.getDate()<d2.getDate()){
                    System.out.println("SelecctedDateIsHigh");
                    binding.locateStartTime.setText("08:30");
                    binding.locateEndTime.setText("23:59");
                }else if(currrentDate.getDate()>d2.getDate()){
                    System.out.println("SelecctedDateIsLow");
                    binding.locateStartTime.setText("08:00");
                    binding.locateEndTime.setText("23:59");

                }

                System.out.println("CurentDateSelected"+currrentDate+" "+d2);


            }
        });

        calContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callInitView();

                /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Date d1=null,d2=null;
                try {
                    d1=formatter.parse(formatter.format(date));
                    d2 = formatter.parse(dateInString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                System.out.println("CurentDateSelected"+d1+" "+d2);*/

                bottomSheetDatePicker.dismiss();
            }
        });

        calBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDatePicker.dismiss();
            }
        });


        bottomSheetDatePicker.show();

    }

    //Locate Booking TimerPicker BottomSheet
    private void bottomSheetLocateTimePickerInBooking(Context mContext, Activity activity, TextView tv, String title, String date,int i) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((activity).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet,
                new RelativeLayout(activity)));

        TimePicker simpleTimePicker24Hours = bottomSheetDialog.findViewById(R.id.simpleTimePicker);
        //simpleTimePicker24Hours.setIs24HourView(false);
        TextView titleTv = bottomSheetDialog.findViewById(R.id.title);
        TextView dateTv = bottomSheetDialog.findViewById(R.id.date);
        TextView continueTv = bottomSheetDialog.findViewById(R.id.continue_tv);
        TextView backTv = bottomSheetDialog.findViewById(R.id.tv_back);
        titleTv.setText(title);
        dateTv.setText(date);

        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        continueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hour=null,minute=null;
                String getHHour=String.valueOf(simpleTimePicker24Hours.getHour());
                String getMMinute=String.valueOf(simpleTimePicker24Hours.getMinute());

                if(getHHour.length()==1){
                    hour="0"+getHHour;
                }else {
                    hour=getHHour;
                }


                if(getMMinute.length()==1){
                    minute="0"+getMMinute;
                }else {
                    minute=getMMinute;
                }

                System.out.println("GETDATATATATA"+hour+" "+minute);

                tv.setText(hour+":"+minute);


                if(i==2){
                //EndTime Called Here
                binding.firstLayout.removeAllViews();
                    endTimeSelectedStats=1;
                initLoadFloorDetails(2);
                }

                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();


    }

    public void callInitView(){
        binding.firstLayout.removeAllViews();
        initLoadFloorDetails(0);
    }


    @Override
    public void onMeetingEditClick(MeetingListToEditResponse meetingListToEditResponse) {

        TextView startRoomTime,endTRoomime,editRoomBookingContinue,editRoomBookingBack,tvMeetingRoomDescription,roomTitle;
        EditText etParticipants,etSubject,etComments;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_room_booking,
                new RelativeLayout(getContext()))));

        startRoomTime = bottomSheetDialog.findViewById(R.id.tvRoomStartTime);
        endTRoomime = bottomSheetDialog.findViewById(R.id.tvRoomEndTime);
        etParticipants=bottomSheetDialog.findViewById(R.id.etParticipants);
        etSubject=bottomSheetDialog.findViewById(R.id.etSubject);

        etComments=bottomSheetDialog.findViewById(R.id.etComments);
        editRoomBookingContinue=bottomSheetDialog.findViewById(R.id.editRoomBookingContinue);
        editRoomBookingBack=bottomSheetDialog.findViewById(R.id.editRoomBookingBack);
        tvMeetingRoomDescription=bottomSheetDialog.findViewById(R.id.meetingRoomDescription);
        roomTitle=bottomSheetDialog.findViewById(R.id.roomTitle);

        TextView select=bottomSheetDialog.findViewById(R.id.select_desk_room);



        startRoomTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), startRoomTime, "", "");
                //Utils.bottomSheetTimePicker(getContext(),getActivity(),startTime,"Start Time","");
            }
        });

        endTRoomime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), endTRoomime, "", "");
                //Utils.bottomSheetTimePicker(getContext(),getActivity(),endTime,"End Time","");

            }
        });

        editRoomBookingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        editRoomBookingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean status=true;

                if(startRoomTime.getText().toString().equals("9:00")){
                    Toast.makeText(getContext(),"Please Select Start Time",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }
                if(endTRoomime.getText().toString().equals("9:00")){
                    Toast.makeText(getContext(),"Please Select End Time",Toast.LENGTH_LONG).show();
                    status=false;
                    return;
                }


                if(status){
                    //Edit MeeingkBooking
                    String subject=etSubject.getText().toString();
                    String comment=etComments.getText().toString();
                   doEditMeetingRoomBooking(meetingListToEditResponse,startRoomTime.getText().toString(),endTRoomime.getText().toString(),subject,comment);
                    System.out.println("MeeingEditGoHere");
                }



            }
        });

        bottomSheetDialog.show();

    }

    private void doEditMeetingRoomBooking(MeetingListToEditResponse meetingListToEditResponse,  String startTime, String endTime,String subject,String comment) {

        MeetingRoomRequest meetEdit=new MeetingRoomRequest();
        meetEdit.setMeetingRoomId(meetingListToEditResponse.getMeetingRoomId());
        meetEdit.setMsTeams(false);
        meetEdit.setHandleRecurring(false);
        meetEdit.setOnlineMeeting(false);


        //1st
        MeetingRoomRequest.Changeset m=meetEdit.new Changeset();
        m.setId(meetingListToEditResponse.getId());
        String dateStr=binding.locateCalendearView.getText().toString();
        m.setDate( dateStr+ "T" + "00:00:00.000" + "Z");

        MeetingRoomRequest.Changeset.Changes changes=m.new Changes();
        changes.setFrom(getCurrentDate() + "" + "T" + startTime + ":" + "00" + "." + "000" + "Z");
        changes.setMyto(getCurrentDate() + "" + "T" + endTime + ":" + "00" + "." + "000" + "Z");

        m.setChanges(changes);
        List<MeetingRoomRequest.Changeset> changesetList=new ArrayList<>();
        changesetList.add(m);


        //2nd
        m.setId(0);
        m.setDate(meetingListToEditResponse.getDate());

        MeetingRoomRequest.Changeset.Changes changes2=m.new Changes();
        changes2.setFrom(meetingListToEditResponse.getFrom());
        changes2.setMyto(meetingListToEditResponse.getTo());
        changes2.setComments(comment);
        changes2.setSubject(subject);
        changes2.setRequest(false);

        List<MeetingRoomRequest.Changeset.Changes.Attendees> attendeesList=new ArrayList<>();
        changes.setAttendees(attendeesList);

        List<MeetingRoomRequest.Changeset.Changes.ExternalAttendees> externalAttendeesList=new ArrayList<>();
        changes.setExternalAttendees(externalAttendeesList);

        m.setChanges(changes2);
        changesetList.add(m);

        meetEdit.setChangesets(changesetList);

        List<MeetingRoomRequest.DeleteIds> deleteIdsList=new ArrayList<>();
        meetEdit.setDeletedIds(deleteIdsList);

        System.out.println("BookingMeetingRoom"+meetEdit);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.doRoomEdit(meetEdit);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                BaseResponse baseResponse=response.body();
                System.out.println("MeetingRoomEdit");

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });



    }
}
