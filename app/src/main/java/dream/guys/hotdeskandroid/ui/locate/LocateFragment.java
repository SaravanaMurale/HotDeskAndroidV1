package dream.guys.hotdeskandroid.ui.locate;

import static dream.guys.hotdeskandroid.utils.Utils.getCurrentDate;
import static dream.guys.hotdeskandroid.utils.Utils.getCurrentTime;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.BookingListToEditAdapter;
import dream.guys.hotdeskandroid.adapter.DeskListRecyclerAdapter;
import dream.guys.hotdeskandroid.adapter.FloorAdapter;
import dream.guys.hotdeskandroid.adapter.LocateMyTeamAdapter;
import dream.guys.hotdeskandroid.adapter.ShowCountryAdapter;
import dream.guys.hotdeskandroid.example.CanvasView;
import dream.guys.hotdeskandroid.example.DataModel;
import dream.guys.hotdeskandroid.example.ItemAdapter;
import dream.guys.hotdeskandroid.example.MyCanvasDraw;
import dream.guys.hotdeskandroid.example.ValuesPOJO;
import dream.guys.hotdeskandroid.model.request.DeskStatusModel;
import dream.guys.hotdeskandroid.model.request.LocateBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateDeskBookingRequest;
import dream.guys.hotdeskandroid.model.request.Point;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.BookingForEditResponse;
import dream.guys.hotdeskandroid.model.response.CarParkAvalibilityResponse;
import dream.guys.hotdeskandroid.model.response.CarParkingslotsResponse;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.TeamsResponse;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocateFragment extends Fragment implements ShowCountryAdapter.OnSelectListener, BookingListToEditAdapter.OnEditClickable, DeskListRecyclerAdapter.OnSelectSelected {


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
    dream.guys.hotdeskandroid.databinding.FragmentLocateBinding binding;

    Dialog dialog;
    int stateId = 0;

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

        //params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        dialog = new Dialog(getContext());
        //locateText= root.findViewById(R.id.locate_Text);
        /* locateText.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Utils.bottomSheetTimePicker(getContext(),getActivity(),"End","ckasnckan");
             }
         });*/
 
        /* Bitmap bitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
         Canvas canvas = new Canvas(bitmap);
         RectF rect = new RectF();
         Paint paint = new Paint();
         canvas.drawBitmap(bitmap, null, rect, paint);*/
        //        paint.set(paint);


        binding.locateStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String c = Utils.getCurrentDate() + "T" + getCurrentTime() + ":00Z";
                System.out.println("CurentDateAndTime" + c);

                //Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), binding.locateStartTime,"", "");
                Utils.bottomSheetTimePicker(getContext(), getActivity(), binding.locateStartTime, "Stat Time", getCurrentDate());
                System.out.println("covert12HoursTo24HoursFormat" + Utils.convert12HrsTO24Hrs(binding.locateStartTime.getText().toString()));

                //Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), locateStartTime,"", "");
                //getCurrentDate()+""+"T"+locateStartTime.getText().toString()+":"+"00"+"."+"000"+"Z";

            }
        });

        binding.locateEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePicker(getContext(), getActivity(), binding.locateEndTime, "End Time", getCurrentDate());
                System.out.println("covert12HoursTo24HoursFormat" + Utils.convert12HrsTO24Hrs(binding.locateStartTime.getText().toString()));


            }
        });

        binding.searchLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocateCountryList();

            }
        });

        binding.locateCalendearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetDatePicker(getContext(), getActivity(), "", "", binding.locateCalendearView);

                System.out.println("ReceivedDatePickerInLocate" + binding.locateCalendearView.getText().toString());


                //locateCalendearView.getText().toString()+"T"+"00:00:00.000"+"Z");

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



    private void initLoadFloorDetails(int i) {
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


            //Used For Desk Avaliability Checking
            getAvaliableDeskDetails(null, 0);
            getTeams();

            //Used For Car Parking Avaliability Checking
            getCarParkingSlots(parentId);
            getCarParkingAvalibilitySlots();


            //Load Desk,car and parking details with view
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    getLocateDeskRoomCarDesign(parentId, i);
                }
            }, 4000);


        } else {
            Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCarParkingAvalibilitySlots() {


        String toDate=Utils.getCurrentDate()+"T00:00:00Z";
        System.out.println("ToDateCheckoing"+toDate);

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


            }

            @Override
            public void onFailure(Call<List<CarParkAvalibilityResponse>> call, Throwable t) {

            }
        });

    }

    private void getCarParkingSlots(int parentId) {

        System.out.println("CarPArkingSlotCAlled");
        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<CarParkingslotsResponse>> call = apiService.getCarParkingSlots(parentId);
        call.enqueue(new Callback<List<CarParkingslotsResponse>>() {
            @Override
            public void onResponse(Call<List<CarParkingslotsResponse>> call, Response<List<CarParkingslotsResponse>> response) {
                ProgressDialog.dismisProgressBar(getContext(), dialog);
                carParkingslots=response.body();

                ProgressDialog.dismisProgressBar(getContext(), dialog);

            }

            @Override
            public void onFailure(Call<List<CarParkingslotsResponse>> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(), dialog);

            }
        });
    }

    private void getTeams() {

        //dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<TeamsResponse>> call = apiService.getTeams();
        call.enqueue(new Callback<List<TeamsResponse>>() {
            @Override
            public void onResponse(Call<List<TeamsResponse>> call, Response<List<TeamsResponse>> response) {

                teamsResponseList = response.body();
                ProgressDialog.dismisProgressBar(getContext(), dialog);


            }

            @Override
            public void onFailure(Call<List<TeamsResponse>> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(), dialog);
            }
        });

    }


    private void getLocateDeskRoomCarDesign(int parentId, int id) {

        dialog = ProgressDialog.showProgressBar(getContext());
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

                ProgressDialog.dismisProgressBar(getContext(), dialog);
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


                ProgressDialog.dismisProgressBar(getContext(), dialog);


            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                ProgressDialog.dismisProgressBar(getContext(), dialog);

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
        String startDate="2022-07-23 21:00:00";
        String endDate="2022-07-23 22:30:00";

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
                            if(teamsResponseList.size()>0) {
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

                                        if (teamsResponse.getDeskCount() != 0 && teamsResponse.getAutomaticApprovalStatus() == 3) {
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

            if (carParkingslots.size() > 0) {

                    for (int i = 0; i < carParkingslots.size(); i++) {

                        if (carParkAvalibilityResponseList.size() > 0) {

                            for (int j = 0; j < carParkAvalibilityResponseList.size(); j++) {
                                if (carParkingslots.get(i).getCarParkingSlotId() == carParkAvalibilityResponseList.get(j).getParkingSlotAvalibilityId()) {
                                    CarParkAvalibilityResponse carParkAvalibilityResponse = carParkAvalibilityResponseList.get(j);

                                    if (carParkAvalibilityResponse.isBookedByElse() == false && carParkAvalibilityResponse.isBookedByUser() == false && carParkAvalibilityResponse.isAvailable() == true && (carParkingslots.get(i).getParkingSlotAvailability() == 1 && (carParkingslots.get(i).getAssignessList().size() == 0))) {
                                        deskStatusModel=new DeskStatusModel(key,id,code,1);
                                        System.out.println("CarParkAvaliable");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_avaliable));
                                        break;
                                    } else if (carParkAvalibilityResponse.isBookedByElse() == false && carParkAvalibilityResponse.isBookedByUser() == false && carParkAvalibilityResponse.isAvailable() == true && carParkingslots.get(i).getParkingSlotAvailability() == 2) {
                                        deskStatusModel=new DeskStatusModel(key,id,code,4);

                                        System.out.println("CarParkingRequest");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_request));
                                        break;
                                    } else if (carParkAvalibilityResponse.isBookedByElse() == false && carParkAvalibilityResponse.isBookedByUser() == false && (carParkAvalibilityResponse.isAvailable() == false || carParkingslots.get(i).getParkingSlotAvailability() == 1)) {
                                        deskStatusModel=new DeskStatusModel(key,id,code,0);
                                        System.out.println("CarParkUnAvaliable");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                                        break;
                                    } else if (carParkAvalibilityResponse.isBookedByElse() == true) {
                                        deskStatusModel=new DeskStatusModel(key,id,code,3);
                                        System.out.println("CarParkingBookedOther");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_booked));
                                        break;
                                    } else if (carParkAvalibilityResponse.isBookedByUser() == true) {
                                        deskStatusModel=new DeskStatusModel(key,id,code,2);
                                        System.out.println("BookedForMe");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_bookedbyme));
                                        break;
                                    } else {
                                        deskStatusModel=new DeskStatusModel(key,id,code,0);
                                        System.out.println("CarParkUnAvaliable");
                                        ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                                        break;
                                    }

                                }

                              //  deskStatusModelList.add(deskStatusModel);

                            }

                        } else {
                            System.out.println("CarParkUnAvaliable");
                            deskStatusModel=new DeskStatusModel(key,id,code,0);
                            ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                        }


                        deskStatusModelList.add(deskStatusModel);

                    }

                } else {
                    System.out.println("CarParkUnAvaliable");
                    deskStatusModel=new DeskStatusModel(key,id,code,0);
                    deskStatusModelList.add(deskStatusModel);
                    ivDesk.setImageDrawable(getResources().getDrawable(R.drawable.desk_unavaliable));
                }

            //deskStatusModelList.add(deskStatusModel);




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

        //relativeLayout.leftMargin = Integer.parseInt(valueList.get(0)+10);
        //relativeLayout.topMargin = Integer.parseInt(valueList.get(1)+10);
        //relativeLayout.width = 80;
        //relativeLayout.height = 80;
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

                            selctedCode = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getMeetingRoomsList().get(i).getMeetingRoomCode();
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
                if (code.equals("3") || code.equals("5")) {
                    if(deskStatusModelList!=null){
                        for (int i = 0; i <deskStatusModelList.size() ; i++) {
                            if(key.equals(deskStatusModelList.get(i).getKey())){

                                if(deskStatusModelList.get(i).getStatus()==1){
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
                                    //Booking Request Bottom Sheet
                                    callDeskBookingnBottomSheet(selctedCode, key, id, code,requestTeamId,requestTeamDeskId);


                                }else if(deskStatusModelList.get(i).getStatus()==2){
                                    //Booking Edit

                                    getBookingListToEdit(code);



                                }


                            }
                        }

                    }


                } else if (code.equals("4")) {

                }

            }
        });

        binding.firstLayout.addView(deskView);


    }

    private void callBottomSheetToEdit(BookingForEditResponse bookingForEditResponse, String code) {

        RecyclerView rvEditList;
        TextView editClose,editDate;
        LinearLayoutManager linearLayoutManager;

        BottomSheetDialog locateEditBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateEditBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_edit_booking_bottomsheet,
                new RelativeLayout(getContext())));

        rvEditList = locateEditBottomSheet.findViewById(R.id.rvEditList);
        editClose=locateEditBottomSheet.findViewById(R.id.editClose);
        editDate=locateEditBottomSheet.findViewById(R.id.editDate);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvEditList.setLayoutManager(linearLayoutManager);
        rvEditList.setHasFixedSize(true);

        BookingListToEditAdapter bookingListToEditAdapter=new BookingListToEditAdapter(getContext(),bookingForEditResponse.getBookings(),this,code,bookingForEditResponse.getTeamDeskAvailabilities());
        rvEditList.setAdapter(bookingListToEditAdapter);

        //Show Here Current Date
        editDate.setText(getCurrentDate());


        editClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locateEditBottomSheet.dismiss();
            }
        });

        locateEditBottomSheet.show();

    }

    private void getBookingListToEdit(String code) {

        dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<BookingForEditResponse> call=apiService.getBookingsForEdit(SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAM_ID),
                SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAMMEMBERSHIP_ID),
                Utils.getCurrentDate(),
                Utils.getCurrentDate());

        call.enqueue(new Callback<BookingForEditResponse>() {
            @Override
            public void onResponse(Call<BookingForEditResponse> call, Response<BookingForEditResponse> response) {

                BookingForEditResponse bookingForEditResponse=response.body();

                callBottomSheetToEdit(bookingForEditResponse,code);

                ProgressDialog.dismisProgressBar(getContext(),dialog);




            }

            @Override
            public void onFailure(Call<BookingForEditResponse> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(),dialog);
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAvaliableDeskDetails(List<LocateCountryRespose.LocationItemLayout.Desks> desks, int id) {

        dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //"2022-07-05T00:00:00.000Z"
        //"2000-01-01T15:50:38.000Z"
        //"2000-01-01T18:00:00.000Z"


        String toDate=Utils.getCurrentDate()+"T00:00:00Z";
        System.out.println("ToDateCheckoing"+toDate);

        //Add min and hour
        String startTime=Utils.addMinuteWithCurrentTime(1,2);
        String fromTime=startTime+".000Z";
        String endTime=Utils.addMinuteWithCurrentTime(2,9);
        String toTime=endTime+".000Z";

        System.out.println("DateAndStatTimeAndEndTime"+toDate+" "+fromTime+" "+toTime);

        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
        //Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId, now, now, now);
        Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId, toDate, fromTime, toTime);

        call.enqueue(new Callback<DeskAvaliabilityResponse>() {
            @Override
            public void onResponse(Call<DeskAvaliabilityResponse> call, Response<DeskAvaliabilityResponse> response) {

                DeskAvaliabilityResponse deskAvaliabilityResponseList = response.body();

                teamDeskAvaliabilityList = deskAvaliabilityResponseList.getTeamDeskAvaliabilityList();


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


                ProgressDialog.dismisProgressBar(getContext(), dialog);


            }

            @Override
            public void onFailure(Call<DeskAvaliabilityResponse> call, Throwable t) {

                System.out.println("Failure" + t.getMessage().toString());

                ProgressDialog.dismisProgressBar(getContext(), dialog);
            }
        });

    }


    private void getLocateCountryList() {


        if (Utils.isNetworkAvailable(getContext())) {

            dialog = ProgressDialog.showProgressBar(getContext());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<LocateCountryRespose>> call = apiService.getLocationCountryList();
            call.enqueue(new Callback<List<LocateCountryRespose>>() {
                @Override
                public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                    List<LocateCountryRespose> locateCountryResposes = response.body();

                    System.out.println("LocateCountryList" + locateCountryResposes.size());

                    CallFloorBottomSheet(locateCountryResposes);


                    ProgressDialog.dismisProgressBar(getContext(), dialog);

                }

                @Override
                public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                    ProgressDialog.dismisProgressBar(getContext(), dialog);

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

        ProgressDialog.dismisProgressBar(getContext(), dialog);
        /*  Point point=new Point(coordinateList.get(i).get(0),coordinateList.get(i).get(1));

         */

    }

    private void callCountrysChildData(int parentId) {
        dialog = ProgressDialog.showProgressBar(getContext());
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

                ProgressDialog.dismisProgressBar(getContext(), dialog);


            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(), dialog);
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

            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                // ProgressDialog.dismisProgressBar(getContext(), dialog);
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
        dialog = ProgressDialog.showProgressBar(getContext());
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

                showFloorImageAndNameInAdapter(locateCountryResposeList);


                ProgressDialog.dismisProgressBar(getContext(), dialog);


            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(), dialog);
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





    //Book BottomSheet
    private void callDeskBookingnBottomSheet(String selctedCode, String key, int id, String code, int requestTeamId, int requestTeamDeskId) {

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
            }
        });


        locateDeskName.setText(selctedCode);

        editBookingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locateCheckInBottomSheet.dismiss();

                //dialog = ProgressDialog.showProgressBar(getContext());
                if (code.equals("3")) {

                    if(requestTeamId>0 && requestTeamDeskId>0){
                        //Request Desk Booking
                        requestDeskBooking(requestTeamId,requestTeamDeskId);

                    }else {
                        //Desk Booking
                        deskBooking();
                    }

                } else if (code.equals("5")) {
                    //Car Booking
                    carParkingRequest();
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

                if(baseResponse!=null){
                    Toast.makeText(getContext(),baseResponse.getResultCode(),Toast.LENGTH_LONG).show();
                }


//                System.out.println("DeskRequestBookingDesk"+baseResponse.getResultCode());

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

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

        dialog = ProgressDialog.showProgressBar(getContext());

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


                BaseResponse baseResponse = response.body();
                if (baseResponse != null) {
//                    Toast.makeText(getContext(), baseResponse.getResultCode(), Toast.LENGTH_LONG).show();
                    openCheckoutDialog("Booking Succcessfull");

                } else {
                    Toast.makeText(getContext(), "Not Avaliable", Toast.LENGTH_LONG).show();
                }

                ProgressDialog.dismisProgressBar(getContext(), dialog);
                System.out.println("BookingSuccessInLocate");

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                System.out.println("BookingfailureInLocate");
                ProgressDialog.dismisProgressBar(getContext(), dialog);
            }
        });


    }

    private void carParkingRequest() {

        dialog = ProgressDialog.showProgressBar(getContext());
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

                ProgressDialog.dismisProgressBar(getContext(), dialog);
                BaseResponse baseResponse = response.body();
                if (baseResponse != null) {
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
                ProgressDialog.dismisProgressBar(getContext(), dialog);
            }
        });


    }


    @Override
    public void onEditClick(BookingForEditResponse.Bookings bookings, String code, List<BookingForEditResponse.TeamDeskAvailabilities> teamDeskAvailabilities) {

        TextView startTime,endTime,date;

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

        TextView select=bottomSheetDialog.findViewById(R.id.select_desk_room);

        //Need To Change Date Here
        date.setText(bookings.getDate());

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

        continueEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Edit DeskBooking
                doEditDeskBooking(bookings,startTime.getText().toString(),endTime.getText().toString());



            }
        });

        bottomSheetDialog.show();


    }

    private void doEditDeskBooking(BookingForEditResponse.Bookings bookings, String startTime, String endTime) {

        dialog=ProgressDialog.showProgressBar(getContext());

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

                    //openCheckoutDialog("Booking Succcessfull");

                } else {
                    Toast.makeText(getContext(), "Not Avaliable"+baseResponse.getResultCode(), Toast.LENGTH_LONG).show();
                }

                ProgressDialog.dismisProgressBar(getContext(), dialog);
                System.out.println("BookingSuccessInLocate");

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(), dialog);
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
}
