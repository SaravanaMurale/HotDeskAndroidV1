package dream.guys.hotdeskandroid.ui.locate;
import android.app.Dialog;
import android.os.Build;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

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
        import dream.guys.hotdeskandroid.adapter.LocateMyTeamAdapter;
        import dream.guys.hotdeskandroid.adapter.ShowCountryAdapter;
        import dream.guys.hotdeskandroid.example.CanvasView;
        import dream.guys.hotdeskandroid.example.MyCanvasDraw;
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

    //BottomSheetData
    TextView country,state,street,floor,back,bsApply;
    RecyclerView rvCountry,rvState,rvStreet;
    ShowCountryAdapter showCountryAdapter;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout countryBlock,statBlock,streetBlock,floorBlock;

    TextView bsLocationSearch;
 
    RecyclerView rvMyTeam;
    LocateMyTeamAdapter locateMyTeamAdapter;


    dream.guys.hotdeskandroid.databinding.FragmentLocateBinding binding;
    TextView locateText,title;


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


    @BindView(R.id.first)
    ImageView first;

    @BindView(R.id.firstLayout)
    LinearLayout firstLayout;

    @BindView(R.id.secondLayout)
    LinearLayout secondLayout;

    View deskView;



    List<LocateCountryRespose> locateCountryResposeList;


    CanvasView canvasView;

    Dialog dialog;
    int stateId=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = dream.guys.hotdeskandroid.databinding.FragmentLocateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

                Utils.bottomSheetTimePicker(getContext(),getActivity(),null,"","");

            }
        });

        binding.locateEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePicker(getContext(),getActivity(),null,"","");

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

                Utils.bottomSheetDatePicker(getContext(),getActivity(),"","");

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


            }
        });

        binding.ivLocateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

        initLoadFloorDetails();



        return root;
    }

    private void initLoadFloorDetails() {
        int parentId=SessionHandler.getInstance().getInt(getContext(),AppConstants.PARENT_ID);
        if(parentId>0){
            String buildingName=SessionHandler.getInstance().get(getContext(),AppConstants.BUILDING);
            String floorName=SessionHandler.getInstance().get(getContext(),AppConstants.FLOOR);

            if(buildingName==null && floorName==null){
                binding.searchLocate.setHint("40th Bank Street,30th Floor");
            }else {
                binding.searchLocate.setHint(buildingName+","+floorName);
            }

            getLocateDeskRoomCarDesign(parentId);
        }else {
            Toast.makeText(getContext(), "Please Select Floor Details", Toast.LENGTH_SHORT).show();
        }
    }


    //Book BottomSheet
    private void callCheckInBottomSheetToBook(String selctedCode, String key, int id) {

        TextView locateDeskName;

        BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateCheckInBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_checkin_bottomsheet,
                new RelativeLayout(getContext())));

        locateDeskName=locateCheckInBottomSheet.findViewById(R.id.locateDeskName);

        locateDeskName.setText(selctedCode);





        locateCheckInBottomSheet.show();
    }

    private void getLocateDeskRoomCarDesign(int parentId) {

        dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentId);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                locateCountryResposeList=response.body();
                int totalDeskSize=locateCountryResposeList.get(0).getLocationItemLayout().getDesks().size();
                System.out.println("TotalSize"+totalDeskSize);

                //addDottedLine();

                ProgressDialog.dismisProgressBar(getContext(),dialog);
                getAvaliableDeskDetails(locateCountryResposeList.get(0).getLocationItemLayout().getDesks());



                List<String> valueList=new ArrayList<>();
                for(String key:locateCountryResposeList.get(0).getItems().keySet()){
                    System.out.println("Value"+locateCountryResposeList.get(0).getItems().get(key));

                    valueList=locateCountryResposeList.get(0).getItems().get(key);

                    addView(valueList,key);

                    //strings.add(locateCountryResposeList.get(0).getItems().get(key));
 
                     /*for (int i = 0; i <strings.size() ; i++) {
                         System.out.println("InsideValue"+strings.get(i));
 
                     }*/

                }
                ProgressDialog.dismisProgressBar(getContext(),dialog);





            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                ProgressDialog.dismisProgressBar(getContext(),dialog);

            }
        });
    }

    private void addDottedLine() {
 
        /* View dottView = getLayoutInflater().inflate(R.layout.layout_dotted_line, null, false);
         ImageView ivDesk = dottView.findViewById(R.id.dottedImage);
         RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
 
         relativeLayout.leftMargin =300;
         relativeLayout.topMargin = 500;
         ivDesk.setLayoutParams(relativeLayout);
 
         binding.firstLayout.addView(dottView);*/

        MyCanvasDraw myCanvasDraw = new MyCanvasDraw (getContext(),100,200);
        binding.secondLayout.addView(myCanvasDraw);


    }


    private void addView(List<String> valueList, String key) {

        System.out.println("ReceivedKeyInAddView"+key);

        deskView = getLayoutInflater().inflate(R.layout.layout_item_desk, null, false);
        ImageView ivDesk = deskView.findViewById(R.id.ivDesk);
        RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //ivDesk.setRotation(45);

        //SetImageHereBased on Code
             /* String[] result = key.split("_");
              String splitValue=result[1];
 
             if(splitValue.equals(AppConstants.DESK)){
 
             }else if(splitValue.equals(AppConstants.MEETING)){
 
             }else if(splitValue.equals(AppConstants.CAR_PARKING)){
 
             }*/

        relativeLayout.leftMargin = Integer.parseInt(valueList.get(0));
        relativeLayout.topMargin = Integer.parseInt(valueList.get(1));
        relativeLayout.width = 80;
        relativeLayout.height = 80;
        ivDesk.setLayoutParams(relativeLayout);

        //ClickOnImage
        ivDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> onClickValue=  locateCountryResposeList.get(0).getItems().get(key);
                      /*for (int j = 0; j <onClickValue.size() ; j++) {
                         System.out.println("OnClickedKey"+key+"OnClickedValue"+onClickValue.get(j));
                     }*/

                //Split key to get id and code
                String[] result = key.split("_");
                int id=Integer.parseInt(result[0]);
                String code=result[1];
                String selctedCode="";

                //Get code based on id
                if(code.equals(AppConstants.DESK)) {
                    //Get Code For Desk
                    for (int i = 0; i < locateCountryResposeList.get(0).getLocationItemLayout().getDesks().size(); i++) {

                        if (id == locateCountryResposeList.get(0).getLocationItemLayout().getDesks().get(i).getDesksId()) {

                            selctedCode = locateCountryResposeList.get(0).getLocationItemLayout().getDesks().get(i).getDeskCode();

                            System.out.println("ClickedCodeIs" + locateCountryResposeList.get(0).getLocationItemLayout().getDesks().get(i).getDeskCode());

                        }

                    }

                }else if(code.equals(AppConstants.MEETING)){
                    //Get Code For MEETING

                    for (int i = 0; i <locateCountryResposeList.get(0).getLocationItemLayout().getMeetingRoomsList().size() ; i++) {

                        if (id == locateCountryResposeList.get(0).getLocationItemLayout().getMeetingRoomsList().get(i).getMeetingRoomId()) {

                            selctedCode=locateCountryResposeList.get(0).getLocationItemLayout().getMeetingRoomsList().get(i).getMeetingRoomCode();
                        }

                    }


                }else if(code.equals(AppConstants.CAR_PARKING)){
                    //Get Code For CAR_PARKING
                    for (int i = 0; i <locateCountryResposeList.get(0).getLocationItemLayout().getParkingSlotsList().size() ; i++) {
                        if (id == locateCountryResposeList.get(0).getLocationItemLayout().getParkingSlotsList().get(i).getId()) {

                            selctedCode=locateCountryResposeList.get(0).getLocationItemLayout().getParkingSlotsList().get(i).getCode();
                        }

                    }

                }

                callCheckInBottomSheetToBook(selctedCode,key,id);



            }
        });

        binding.firstLayout.addView(deskView);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getAvaliableDeskDetails(List<LocateCountryRespose.LocationItemLayout.Desks> desks) {

        dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        //"2022-07-05T00:00:00.000Z"
        //"2000-01-01T15:50:38.000Z"
        //"2000-01-01T18:00:00.000Z"

        System.out.println("DateFormatInLocate"+Utils.getCurrentDateInDateFormet());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        System.out.println("NowValue"+now);
        System.out.println("DTFValue"+dtf);

        int parentId=SessionHandler.getInstance().getInt(getContext(),AppConstants.PARENT_ID);
        Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(parentId,now,now, now);
        call.enqueue(new Callback<DeskAvaliabilityResponse>() {
            @Override
            public void onResponse(Call<DeskAvaliabilityResponse> call, Response<DeskAvaliabilityResponse> response) {

                DeskAvaliabilityResponse deskAvaliabilityResponseList=response.body();

                System.out.println("InsideDataValaibelity");
                //System.out.println("AllDeskSize"+desks.size());
                // System.out.println("ActiveDeskSize"+ deskAvaliabilityResponseList.getLocationDesksList().size());

                List<String> deskCodeList=new ArrayList<>();
                for (int i = 0; i <desks.size() ; i++) {
                    deskCodeList.add(desks.get(i).getDeskCode());
                }

                if(deskAvaliabilityResponseList.getLocationDesksList()!=null){
                    for (int i = 0; i <deskAvaliabilityResponseList.getLocationDesksList().size() ; i++) {

                        if(deskCodeList.contains(deskAvaliabilityResponseList.getLocationDesksList().get(i).getCode())){
                            System.out.println("AvaliableDesks"+deskAvaliabilityResponseList.getLocationDesksList().get(i).getCode());
                        }

                    }
                }



                ProgressDialog.dismisProgressBar(getContext(),dialog);


            }

            @Override
            public void onFailure(Call<DeskAvaliabilityResponse> call, Throwable t) {

                System.out.println("Failure"+t.getMessage().toString());

                ProgressDialog.dismisProgressBar(getContext(),dialog);
            }
        });

    }

    //MyTeamBottomSheet
    private void callMyTeamBottomSheet() {

        BottomSheetDialog myTeamBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        myTeamBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_myteam_bottomsheet,
                new RelativeLayout(getContext())));

        rvMyTeam=myTeamBottomSheet.findViewById(R.id.rvLocateMyTeam);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMyTeam.setLayoutManager(linearLayoutManager);
        rvMyTeam.setHasFixedSize(true);

        List<String> stringName=new ArrayList<>();
        stringName.add("Bessie Cooper");
        stringName.add("Francene Vandyne");
        stringName.add("Cody Fisher");


        locateMyTeamAdapter=new LocateMyTeamAdapter(getContext(),stringName);
        rvMyTeam.setAdapter(locateMyTeamAdapter);


        myTeamBottomSheet.show();



    }

    private void getLocateCountryList() {


        if (Utils.isNetworkAvailable(getContext())) {

            dialog= ProgressDialog.showProgressBar(getContext());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<LocateCountryRespose>> call = apiService.getLocationCountryList();
            call.enqueue(new Callback<List<LocateCountryRespose>>() {
                @Override
                public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                    List<LocateCountryRespose> locateCountryResposes =response.body();

                    System.out.println("LocateCountryList"+locateCountryResposes.size());

                    CallFloorBottomSheet(locateCountryResposes);


                    ProgressDialog.dismisProgressBar(getContext(),dialog);

                }

                @Override
                public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                    ProgressDialog.dismisProgressBar(getContext(),dialog);

                }
            });

        }else {
            Utils.toastMessage(getContext(), "Please Enable Internet");
        }


    }

    //Select Floor Plan BottomSheeet
    private void CallFloorBottomSheet(List<LocateCountryRespose> locateCountryResposes) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(getLayoutInflater().
                inflate(R.layout.bottom_sheet_locate_floor_filter,
                        new RelativeLayout(getContext())));



        bsLocationSearch=bottomSheetDialog.findViewById(R.id.bsLocationSearch);

        String buildingName=SessionHandler.getInstance().get(getContext(),AppConstants.BUILDING);
        String floorName=SessionHandler.getInstance().get(getContext(),AppConstants.FLOOR);

        if(buildingName==null && floorName==null){
            bsLocationSearch.setText("40th Bank Street,30th Floor");
        }else {
            bsLocationSearch.setText(buildingName+","+floorName);
        }

        countryBlock=bottomSheetDialog.findViewById(R.id.bsCountryBlock);
        statBlock=bottomSheetDialog.findViewById(R.id.bsStateBlock);
        streetBlock=bottomSheetDialog.findViewById(R.id.bsStreetBlock);
        floorBlock=bottomSheetDialog.findViewById(R.id.bsFloorBlock);

        country = bottomSheetDialog.findViewById(R.id.bsCountry);
        state = bottomSheetDialog.findViewById(R.id.bsState);
        street = bottomSheetDialog.findViewById(R.id.bsStreet);
        floor = bottomSheetDialog.findViewById(R.id.bsfloor);


        //Get initial data
        //getCountryStateStreetAndFloorDetails();

        rvCountry=bottomSheetDialog.findViewById(R.id.rvCountry);
        rvState=bottomSheetDialog.findViewById(R.id.rvState);
        rvStreet=bottomSheetDialog.findViewById(R.id.rvStreet);


        country.setText(locateCountryResposes.get(0).getName());
        rvCountry.setVisibility(View.INVISIBLE);
        statBlock.setVisibility(View.INVISIBLE);
        rvState.setVisibility(View.INVISIBLE);
        streetBlock.setVisibility(View.INVISIBLE);
        rvStreet.setVisibility(View.INVISIBLE);
        floorBlock.setVisibility(View.INVISIBLE);

        back=bottomSheetDialog.findViewById(R.id.bsBack);
        bsApply=bottomSheetDialog.findViewById(R.id.bsApply);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();


            }
        });

        bsApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.firstLayout.removeAllViews();
                initLoadFloorDetails();
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

                //getLocateCountryList();
            }
        });

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state.setText("City");
                callCountrysChildData(stateId);

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

        showCountryAdapter=new ShowCountryAdapter(getContext(),locateCountryResposes,this,"COUNTRY");
        rvCountry.setAdapter(showCountryAdapter);

    }

    @Override
    public void onSelect(LocateCountryRespose locateCountryRespose, String identifier) {


        switch (identifier){
            case "COUNTRY":
                state.setText("City");
                stateId=locateCountryRespose.getLocateCountryId();
                country.setText(locateCountryRespose.getName());
                callCountrysChildData(locateCountryRespose.getLocateCountryId());
                break;

            case "STATE":
                state.setText(locateCountryRespose.getName());
                SessionHandler.getInstance().save(getContext(), AppConstants.BUILDING,locateCountryRespose.getName());
                rvState.setVisibility(View.GONE);
                streetBlock.setVisibility(View.VISIBLE);
                street.setVisibility(View.VISIBLE);
                street.setText("Building");
                getFloorDetails(locateCountryRespose.getLocateCountryId());
                break;

            case "FLOOR":
                floorBlock.setVisibility(View.VISIBLE);
                floor.setVisibility(View.VISIBLE);
                floor.setText(locateCountryRespose.getName());
                SessionHandler.getInstance().save(getContext(), AppConstants.FLOOR,locateCountryRespose.getName());
                rvStreet.setVisibility(View.GONE);
                SessionHandler.getInstance().saveInt(getContext(), AppConstants.PARENT_ID,locateCountryRespose.getLocateCountryId());
                getDeskRoomCarParkingDetails(locateCountryRespose.getLocateCountryId());


                break;


        }

        if (Utils.isNetworkAvailable(getContext())) {

        }else {
            Utils.toastMessage(getContext(), "Please Enable Internet");
        }

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

                ProgressDialog.dismisProgressBar(getContext(),dialog);


            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(),dialog);
            }
        });
    }

    private void showCountryChildListInAdapter(List<LocateCountryRespose> locateCountryResposes) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvState.setLayoutManager(linearLayoutManager);
        rvState.setHasFixedSize(true);

        showCountryAdapter=new ShowCountryAdapter(getContext(),locateCountryResposes,this, "STATE");
        rvState.setAdapter(showCountryAdapter);
    }

    private void getFloorDetails(int parentId) {

        dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentId);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposes = response.body();

                for (int i = 0; i <locateCountryResposes.size() ; i++) {

                    System.out.println("GetFloorDetails "+locateCountryResposes.get(i).getName());

                }

                rvStreet.setVisibility(View.VISIBLE);

                showFloorListInAdapter(locateCountryResposes);

                ProgressDialog.dismisProgressBar(getContext(),dialog);

            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(),dialog);
            }
        });

    }

    private void showFloorListInAdapter(List<LocateCountryRespose> locateCountryResposes) {

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvStreet.setLayoutManager(linearLayoutManager);
        rvStreet.setHasFixedSize(true);

        showCountryAdapter=new ShowCountryAdapter(getContext(),locateCountryResposes,this, "FLOOR");
        rvStreet.setAdapter(showCountryAdapter);

    }

    private void getDeskRoomCarParkingDetails(int parentId) {
        dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(parentId);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposeList=response.body();

                System.out.println("InsideFloorDetails");
 
 
 
                     /*for (int j = 0; j <locateCountryResposeList.get(0).getLocationItemLayout().getDesks().size() ; j++) {
                         System.out.println("DeskNameInFloor"+locateCountryResposeList.get(j).getLocationItemLayout().getDesks().get(j).getDeskCode());
                     }*/



                ProgressDialog.dismisProgressBar(getContext(),dialog);



            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {
                ProgressDialog.dismisProgressBar(getContext(),dialog);
            }
        });


    }
}
