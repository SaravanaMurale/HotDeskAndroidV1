package dream.guys.hotdeskandroid.ui.locate;

import static dream.guys.hotdeskandroid.utils.Utils.getCurrentDate;
import static dream.guys.hotdeskandroid.utils.Utils.getCurrentTime;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.FloorAdapter;
import dream.guys.hotdeskandroid.adapter.LocateMyTeamAdapter;
import dream.guys.hotdeskandroid.adapter.ShowCountryAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentLocateBinding;
import dream.guys.hotdeskandroid.example.CanvasView;
import dream.guys.hotdeskandroid.example.DataModel;
import dream.guys.hotdeskandroid.example.ItemAdapter;
import dream.guys.hotdeskandroid.example.MyCanvasDraw;
import dream.guys.hotdeskandroid.example.ValuesPOJO;
import dream.guys.hotdeskandroid.model.request.LocateBookingRequest;
import dream.guys.hotdeskandroid.model.request.LocateCarParkBookingRequest;
import dream.guys.hotdeskandroid.model.request.Point;
import dream.guys.hotdeskandroid.model.response.BaseResponse;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocateFragment extends Fragment implements ShowCountryAdapter.OnSelectListener {


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
    int canvasss=0;




    List<LocateCountryRespose> locateCountryResposeList;


    CanvasView canvasView;
    FragmentLocateBinding binding;

    Dialog dialog;
    int stateId = 0;

    boolean keyClickedStats=true;
    List<Point> pointList=new ArrayList<>();

    //CheckInDetails
    TextView locateDeskName,editBookingBack,editBookingContinue;
    RelativeLayout bookingDateBlock,bookingStartBlock,bookingEndBlock,bookingCommentBlock,bookingVechicleRegtBlock;
    EditText etComment,etVehicleReg;
    TextView locateCheckInDate,locateCheckInTime,locateCheckoutTime;
    int teamDeskIdForBooking=0;
    int selectedCarParkingSlotId=0;

    //Zoom
    final static float move=200;
    float ratio=1.0f;
    int baseDist;
    float baseRatio;

    //RelativeLayout.LayoutParams params;


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

                String c=Utils.getCurrentDate()+"T"+getCurrentTime()+":00Z";
                System.out.println("CurentDateAndTime"+c);

                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), locateEndTime,"", "");

                //Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), locateStartTime,"", "");
                //getCurrentDate()+""+"T"+locateStartTime.getText().toString()+":"+"00"+"."+"000"+"Z";

            }
        });

        binding.locateEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePickerInBooking(getContext(), getActivity(), locateEndTime,"", "");

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

                Utils.bottomSheetDatePicker(getContext(), getActivity(),"","",locateCalendearView);
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

                if(keyClickedStats){
                    binding.ivLocateKey.setImageDrawable(getResources().getDrawable(R.drawable.key_icon_orange));
                    binding.locateKeyStatusBlock.setVisibility(View.VISIBLE);
                    keyClickedStats=false;

                }else {
                    binding.ivLocateKey.setImageDrawable(getResources().getDrawable(R.drawable.locate_key));
                    binding.locateKeyStatusBlock.setVisibility(View.INVISIBLE);
                    keyClickedStats=true;
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
            int subParentId=SessionHandler.getInstance().getInt(getContext(), AppConstants.SUB_PARENT_ID);
            boolean findCoordinateStatus=true;
            getFloorDetails(subParentId,findCoordinateStatus);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getLocateDeskRoomCarDesign(parentId,i);
                }
            },2000);



        } else {
            Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
        }
    }




    private void getLocateDeskRoomCarDesign(int parentId, int id ) {

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


                getFloorCoordinates(locateCountryResposeList.get(floorPosition).getCoordinates());


                //addDottedLine();

                ProgressDialog.dismisProgressBar(getContext(), dialog);
                //getAvaliableDeskDetails(locateCountryResposeList.get(floorPosition).getLocationItemLayout().getDesks());


                List<String> valueList = new ArrayList<>();

                if(locateCountryResposeList.get(floorPosition).getItems()!=null){
                    for (String key : locateCountryResposeList.get(floorPosition).getItems().keySet()) {
                        System.out.println("Value" + locateCountryResposeList.get(floorPosition).getItems().get(key));

                        valueList = locateCountryResposeList.get(floorPosition).getItems().get(key);

                        addView(valueList, key, floorPosition);

                        //strings.add(locateCountryResposeList.get(0).getItems().get(key));

                     /*for (int i = 0; i <strings.size() ; i++) {
                         System.out.println("InsideValue"+strings.get(i));

                     }*/

                    }

                }else {
                    Toast.makeText(getContext(),"No Data",Toast.LENGTH_LONG).show();
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


        System.out.println("CoordinateSize"+coordinateList.size());
        //List<Point> pointList=new ArrayList<>();
        for (int i = 0; i <coordinateList.size() ; i++) {

            System.out.println("CoordinateData"+ i +"position"+"size "+coordinateList.get(i).size());

            Point point=new Point(coordinateList.get(i).get(0)+40,coordinateList.get(i).get(1)+20);
            pointList.add(point);


        }
        System.out.println("PointListSize"+pointList.size());

        //binding.secondLayout.postInvalidate();
        //binding.secondLayout.invalidate();

        if(pointList.size()>0){
            MyCanvasDraw  myCanvasDraw = new MyCanvasDraw(getContext(), pointList);

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


    private void addView(List<String> valueList, String key, int floorPosition) {

        System.out.println("ReceivedKeyInAddView" + key);


        deskView = getLayoutInflater().inflate(R.layout.layout_item_desk, null, false);
        ImageView ivDesk = deskView.findViewById(R.id.ivDesk);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

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

        int x=Integer.parseInt(valueList.get(0));
        int y=Integer.parseInt(valueList.get(1));

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

                if(code.equals("3") || code.equals("5")){
                    callDeskBookingnBottomSheet(selctedCode, key, id,code);
                }else if(code.equals("4")){

                }




            }
        });

        binding.firstLayout.addView(deskView);




    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAvaliableDeskDetails(List<LocateCountryRespose.LocationItemLayout.Desks> desks, int id) {

        dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //"2022-07-05T00:00:00.000Z"
        //"2000-01-01T15:50:38.000Z"
        //"2000-01-01T18:00:00.000Z"

        System.out.println("DateFormatInLocate" + Utils.getCurrentDateInDateFormet());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        System.out.println("NowValue" + now);
        System.out.println("DTFValue" + dtf);

        int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
        Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId, now, now, now);
        call.enqueue(new Callback<DeskAvaliabilityResponse>() {
            @Override
            public void onResponse(Call<DeskAvaliabilityResponse> call, Response<DeskAvaliabilityResponse> response) {

                DeskAvaliabilityResponse deskAvaliabilityResponseList = response.body();

                System.out.println("InsideDataValaibelity");
                //System.out.println("AllDeskSize"+desks.size());
                // System.out.println("ActiveDeskSize"+ deskAvaliabilityResponseList.getLocationDesksList().size());


                //GetTeamDeskIdForBooking
                for (int i = 0; i <deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().size() ; i++) {

                    if(id==deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().get(i).getDeskId()){
                         teamDeskIdForBooking=deskAvaliabilityResponseList.getTeamDeskAvaliabilityList().get(i).getTeamDeskId();
                        System.out.println("TeamDeskIdForBooking "+teamDeskIdForBooking);
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

    //MyTeamBottomSheet
    private void callMyTeamBottomSheet() {

        TextView myTeamClose;

        BottomSheetDialog myTeamBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        myTeamBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_myteam_bottomsheet,
                new RelativeLayout(getContext())));

        rvMyTeam = myTeamBottomSheet.findViewById(R.id.rvLocateMyTeam);
        myTeamClose=myTeamBottomSheet.findViewById(R.id.myTeamClose);


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

                canvasss=1;

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


                System.out.println("SubParentIdAndItsPosition"+locateCountryRespose.getLocateCountryId()+" ");

                break;

            case "FLOOR":
                floorBlock.setVisibility(View.VISIBLE);
                floor.setVisibility(View.VISIBLE);
                floor.setText(locateCountryRespose.getName());
                SessionHandler.getInstance().save(getContext(), AppConstants.FLOOR, locateCountryRespose.getName());
                rvStreet.setVisibility(View.GONE);
                rvFloor.setVisibility(View.VISIBLE);
                SessionHandler.getInstance().saveInt(getContext(), AppConstants.PARENT_ID, locateCountryRespose.getLocateCountryId());

                if(locateCountryRespose.getSupportZoneLayoutItemsList()!=null){
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

        for (int i = 0; i <supportZoneLayoutItemsList.size() ; i++) {

            System.out.println("supportZoneLayoutItemsSize"+supportZoneLayoutItemsList.size());
            System.out.println("supportZoneLayoutItemsSize"+supportZoneLayoutItemsList.get(i).getTitle());
            System.out.println("CorrrdnateSize"+supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().size());

            for (int j = 0; j <supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().size() ; j++) {

                System.out.println("DATAATATA"+supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(0) +" "+supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(1));

                Point point=new Point(supportZoneLayoutItemsList.get(i).getSupportZoneCoordinates().get(j).get(0),
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

                if(findCoordinateStatus){
                    int parentId = SessionHandler.getInstance().getInt(getContext(), AppConstants.PARENT_ID);
                    //ProgressDialog.dismisProgressBar(getContext(), dialog);
                    for (int i = 0; i <locateCountryResposes.size() ; i++) {

                        if(parentId==locateCountryResposes.get(i).getLocateCountryId()){
                            if(locateCountryResposes.get(i).getSupportZoneLayoutItemsList()!=null){
                               // ProgressDialog.dismisProgressBar(getContext(), dialog);
                                getOtherSubZoneLayoutItems(locateCountryResposes.get(i).getSupportZoneLayoutItemsList());
                            }
                        }

                    }
                }else {
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


    private void callLocateFilterBottomSheet() {

        RecyclerView locateFilterMainRV;
        ValuesPOJO valuesPOJO;
        ArrayList<DataModel> mList;
        ItemAdapter adapter;

        TextView locateFilterCancel,locateFilterApply;

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView((this).getLayoutInflater().inflate(R.layout.dialog_bottom_sheet_locate_filter,
                new RelativeLayout(getContext())));

        locateFilterCancel=bottomSheetDialog.findViewById(R.id.locateFilterCancel);
        locateFilterApply=bottomSheetDialog.findViewById(R.id.locateFilterApply);



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

        valuesPOJO = new ValuesPOJO("Monitor",false);
        nestedList1.add(valuesPOJO);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Adjustable height",false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Laptop stand",false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("USB_C Dock",false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Charge point",false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Standing desk",false);
        nestedList1.add(valuesPOJO);

        ArrayList<ValuesPOJO> nestedList2 = new ArrayList<>();

        valuesPOJO = new ValuesPOJO("Single",false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Double",false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Ac",false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Non-AC",false);
        nestedList2.add(valuesPOJO);


        mList.add(new DataModel(nestedList1 , "Workspaces"));
        mList.add(new DataModel( nestedList2,"Rooms"));

        adapter = new ItemAdapter(mList);
        locateFilterMainRV.setAdapter(adapter);

        bottomSheetDialog.show();

    }


    //Book BottomSheet
    private void callDeskBookingnBottomSheet(String selctedCode, String key, int id, String code) {

        System.out.println("BookingRequestDetail"+selctedCode+" "+key+" "+id+" "+code);

        BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateCheckInBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_checkin_bottomsheet,
                new RelativeLayout(getContext())));
        bookingDateBlock=locateCheckInBottomSheet.findViewById(R.id.bookingDateBlock);
        bookingStartBlock=locateCheckInBottomSheet.findViewById(R.id.bookingStartBlock);
        bookingEndBlock=locateCheckInBottomSheet.findViewById(R.id.bookingEndBlock);


        locateCheckInDate=locateCheckInBottomSheet.findViewById(R.id.locateCheckInDate);
        locateCheckInTime=locateCheckInBottomSheet.findViewById(R.id.locateCheckInTime);
        locateCheckoutTime=locateCheckInBottomSheet.findViewById(R.id.locateCheckoutTime);

        locateDeskName = locateCheckInBottomSheet.findViewById(R.id.locateDeskName);
        editBookingContinue=locateCheckInBottomSheet.findViewById(R.id.editBookingContinue);
        editBookingBack=locateCheckInBottomSheet.findViewById(R.id.editBookingBack);



        //Car Parking Booking Widget
        bookingCommentBlock=locateCheckInBottomSheet.findViewById(R.id.bookingCommentBlock);
        bookingVechicleRegtBlock=locateCheckInBottomSheet.findViewById(R.id.bookingVechicleRegtBlock);
        etComment=locateCheckInBottomSheet.findViewById(R.id.etComment);
        etComment.setText("Comment");
        etVehicleReg=locateCheckInBottomSheet.findViewById(R.id.etVehicleReg);
        etVehicleReg.setText("TN20CX2443");

        //Desk Avaliability Checking
        if(code.equals("3")) {
            bookingCommentBlock.setVisibility(View.GONE);
            bookingVechicleRegtBlock.setVisibility(View.GONE);
            getAvaliableDeskDetails(null, id);
        }else if(code.equals("5")){
            bookingCommentBlock.setVisibility(View.VISIBLE);
            bookingVechicleRegtBlock.setVisibility(View.VISIBLE);
            getParkingSlotId(selctedCode);
        }

        bookingDateBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetDatePicker(getContext(),getActivity(),"","",locateCheckInDate);

               //callBookingDatePickerBottomSheet();
            }
        });

        bookingStartBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePickerInBooking(getContext(),getActivity(),locateCheckInTime,"","");
                //callBookingTimePickerBottomSheet();
            }
        });

        bookingEndBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePickerInBooking(getContext(),getActivity(),locateCheckoutTime,"","");
            }
        });



        locateDeskName.setText(selctedCode);

        editBookingContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locateCheckInBottomSheet.dismiss();

                //dialog = ProgressDialog.showProgressBar(getContext());
                if(code.equals("3")){
                //Desk Booking
                    deskBookingRequest();
                }else if(code.equals("5")){
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

    private void getParkingSlotId(String key) {

        int floorPosition = SessionHandler.getInstance().getInt(getContext(), AppConstants.FLOOR_POSITION);
        for (int i = 0; i <locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().size() ; i++) {

            System.out.println("SelectedCodeEquatl"+locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getCode());
            if(key.equals(locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getCode())) {
                System.out.println("SelectedCarParkingSlotId" + locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getId());
                selectedCarParkingSlotId = locateCountryResposeList.get(floorPosition).getLocationItemLayout().getParkingSlotsList().get(i).getId();
            }

        }


    }

    private void callBookingTimePickerBottomSheet() {

     //Utils.bottomSheetTimePicker(getContext(),getActivity(),locateCheckInTime,"","");

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


    private void deskBookingRequest() {

        dialog = ProgressDialog.showProgressBar(getContext());

        System.out.println("DeskBookingRequested");

        LocateBookingRequest locateBookingRequest=new LocateBookingRequest();
        locateBookingRequest.setTeamId(SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAM_ID));
        locateBookingRequest.setTeamMembershipId(SessionHandler.getInstance().getInt(getContext(),AppConstants.TEAMMEMBERSHIP_ID));

        //locateBookingRequest.setTeamId(9);
        //locateBookingRequest.setTeamMembershipId(12);

        LocateBookingRequest.ChangeSets changeSets=locateBookingRequest.new ChangeSets();
        changeSets.setChangeSetId(0);
        //changeSets.setChangeSetDate("2022-07-14T00:00:00.000Z");
        changeSets.setChangeSetDate(locateCheckInDate.getText().toString()+"T"+"00:00:00.000"+"Z");

        LocateBookingRequest.ChangeSets.Changes changes=changeSets. new Changes();
        changes.setUsageTypeId(2);
        //changes.setFrom("2022-07-21T20:15:00.000Z");
        //changes.setTo("2022-07-21T21:30:00.000Z");

        changes.setFrom(getCurrentDate()+""+"T"+locateCheckInTime.getText().toString()+":"+"00"+"."+"000"+"Z");
        changes.setTo(getCurrentDate()+""+"T"+locateCheckoutTime.getText().toString()+":"+"00"+"."+"000"+"Z");
        changes.setTimeZoneId("India Standard Time");
        changes.setTeamDeskId(teamDeskIdForBooking);
        changes.setTypeOfCheckIn(1);

        changeSets.setChanges(changes);

        List<LocateBookingRequest.ChangeSets> changeSetsList=new ArrayList<>();
        changeSetsList.add(changeSets);

        locateBookingRequest.setChangeSetsList(changeSetsList);

        LocateBookingRequest.DeleteIds deleteIds=locateBookingRequest.new DeleteIds();
        List<LocateBookingRequest.DeleteIds> deleteIdsList=new ArrayList<>();
        //deleteIdsList.add(deleteIds);

        locateBookingRequest.setDeleteIdsList(deleteIdsList);

        System.out.println("BookingRequestObject"+locateBookingRequest);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.doDeskBooking(locateBookingRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                ProgressDialog.dismisProgressBar(getContext(), dialog);

                BaseResponse baseResponse=response.body();
                if(baseResponse!=null) {
                    Toast.makeText(getContext(), baseResponse.getResultCode(), Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), "Not Avaliable", Toast.LENGTH_LONG).show();
                }

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

        LocateCarParkBookingRequest locateCarParkBookingRequest=new LocateCarParkBookingRequest();
        locateCarParkBookingRequest.setParkingSlotId(selectedCarParkingSlotId);

        LocateCarParkBookingRequest.CarParkingChangeSets carParkingChangeSets=locateCarParkBookingRequest.new CarParkingChangeSets();
        carParkingChangeSets.setId(0);
        carParkingChangeSets.setDate(locateCheckInDate.getText().toString()+"T"+"00:00:00.000"+"Z");

        LocateCarParkBookingRequest.CarParkingChangeSets.CarParkingChanges carParkingChanges=carParkingChangeSets.new CarParkingChanges();
        carParkingChanges.setFrom(getCurrentDate()+""+"T"+locateCheckInTime.getText().toString()+":"+"00"+"."+"000"+"Z");
        carParkingChanges.setTo(getCurrentDate()+""+"T"+locateCheckoutTime.getText().toString()+":"+"00"+"."+"000"+"Z");
        carParkingChanges.setComments(etComment.getText().toString());
        carParkingChanges.setBookedForUser(SessionHandler.getInstance().getInt(getContext(),AppConstants.USER_ID));
        carParkingChanges.setVehicleRegNumber(etVehicleReg.getText().toString());

        carParkingChangeSets.setCarParkingChanges(carParkingChanges);

        List<LocateCarParkBookingRequest.CarParkingChangeSets> carParkingChangeSetsList=new ArrayList<>();
        carParkingChangeSetsList.add(carParkingChangeSets);

        locateCarParkBookingRequest.setCarParkingChangeSetsList(carParkingChangeSetsList);

        LocateCarParkBookingRequest.CarParkingDeleteIds carParkingDeleteIds=locateCarParkBookingRequest.new CarParkingDeleteIds();
        List<LocateCarParkBookingRequest.CarParkingDeleteIds> deleteIdsList=new ArrayList<>();
        //deleteIdsList.add(carParkingDeleteIds);

        locateCarParkBookingRequest.setDeleteIdsList(deleteIdsList);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.doCarParkingBooking(locateCarParkBookingRequest);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                ProgressDialog.dismisProgressBar(getContext(), dialog);
                BaseResponse baseResponse=response.body();
                if (baseResponse!=null){
                    Toast.makeText(getContext(),baseResponse.getResultCode(),Toast.LENGTH_LONG).show();
                }
                else {
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
    public void onDestroy() {
        super.onDestroy();

        System.out.println("OndestroyCalledInLocateFragment");
    }

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        super.dispatchTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }
*/





   /* private class TouchHandler implements View.OnTouchListener
    {
        @Override
        public boolean onTouch(View view, MotionEvent event)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                params.leftMargin = Math.round(event.getX() * 160f / getContext().getResources().getDisplayMetrics().densityDpi);
                params.topMargin = Math.round(event.getY() * 160f / getContext().getResources().getDisplayMetrics().densityDpi);
                //image.setLayoutParams(params);
                //image.setVisibility(View.VISIBLE);
                firstLayout.setPivotX(event.getX());
                firstLayout.setPivotY(event.getY());
                firstLayout.setScaleX(2f);
                firstLayout.setScaleY(2f);
            }
            return true;
        }
    }*/


    /*@Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getPointerCount()==2){
            int action=event.getAction();
            int mainaction=action&MotionEvent.ACTION_MASK;

            if(mainaction==MotionEvent.ACTION_POINTER_DOWN){
                baseDist=getDistace(event);
                baseRatio=ratio;
            }else {

                float scale=(getDistace(event)-baseDist)/move;
                float factor=(float) Math.pow(2,scale);
                ratio=Math.min(1024.0f,Math.max(0.1f,baseRatio*factor));

            }
        }

        return true;
    }*/

    private int getDistace(MotionEvent event) {
        int dx=(int) (event.getX(0)-event.getX(1));
        int dy=(int) (event.getY(0)-event.getY(1));
        return (int)(Math.sqrt(dx*dx+dy*dy));
    }


}
