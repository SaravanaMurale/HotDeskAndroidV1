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

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.LocateMyTeamAdapter;
import dream.guys.hotdeskandroid.adapter.ShowCountryAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentLocateBinding;
import dream.guys.hotdeskandroid.example.CanvasView;
import dream.guys.hotdeskandroid.model.request.DeskCode;
import dream.guys.hotdeskandroid.model.response.DeskAvaliabilityResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.model.response.LocateFloorResponse;
import dream.guys.hotdeskandroid.utils.ProgressDialog;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocateFragment extends Fragment implements ShowCountryAdapter.OnSelectListener {

    //BottomSheetData
    TextView country,state,street,floor,back;
    RecyclerView rvCountry,rvState,rvStreet;
    ShowCountryAdapter showCountryAdapter;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout countryBlock,statBlock,streetBlock,floorBlock;


    //MyTeamBottomSheet
    RecyclerView rvMyTeam;
    LocateMyTeamAdapter locateMyTeamAdapter;


    FragmentLocateBinding binding;
    TextView locateText,title;


    @BindView(R.id.searchLocate)
    TextView searchLocate;

    @BindView(R.id.ivLocateMyTeam)
    ImageView ivLocateMyTeam;
    @BindView(R.id.ivLocateFilter)
    ImageView ivLocateFilter;
    @BindView(R.id.ivLocateKey)
    ImageView ivLocateKey;

    @BindView(R.id.locateStartTime)
    TextView locateStartTime;
    @BindView(R.id.locateEndTime)
    TextView locateEndTime;

    @BindView(R.id.first)
    ImageView first;

    @BindView(R.id.firstLayout)
    LinearLayout firstLayout;





    CanvasView canvasView;

    Dialog dialog;
    int stateId=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocateBinding.inflate(inflater, container, false);
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

                Utils.bottomSheetTimePicker(getContext(),getActivity(),"","");

            }
        });

        binding.locateEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.bottomSheetTimePicker(getContext(),getActivity(),"","");

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

                callCheckInBottomSheetToBook();
                System.out.println("");

            }
        });


        getLocateDeskRoomCarDesign();

        return root;
    }

    private void callCheckInBottomSheetToBook() {

        BottomSheetDialog locateCheckInBottomSheet = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        locateCheckInBottomSheet.setContentView(getLayoutInflater().inflate(R.layout.dialog_locate_checkin_bottomsheet,
                new RelativeLayout(getContext())));

        locateCheckInBottomSheet.show();
    }

    private void getLocateDeskRoomCarDesign() {

        dialog = ProgressDialog.showProgressBar(getContext());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call = apiService.getCountrysChild(4);
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposeList=response.body();
                int totalDeskSize=locateCountryResposeList.get(0).getLocationItemLayout().getDesks().size();
                System.out.println("TotalDeskSize "+totalDeskSize);


                for (int i = 0; i <1 ; i++) {
                    addView(i);
                }



                ProgressDialog.dismisProgressBar(getContext(),dialog);

                //getAvaliableDeskDetails(locateCountryResposeList.get(0).getLocationItemLayout().getDesks());




            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                ProgressDialog.dismisProgressBar(getContext(),dialog);

            }
        });
    }

    private void addView(int i) {

        View deskView = getLayoutInflater().inflate(R.layout.layout_item_desk, null, false);
        ImageView iv1=deskView.findViewById(R.id.deskkk);


        if(i==0) {

            //iv1.layout(100,200,100,200);
           // iv1.setX(10);
           // iv1.setY(17);


        }


       /* if(i==1) {

            iv1.setX(10);
            iv1.setY(40);

        }*/

     /*   if(i==2) {

            iv1.setX(60);
            iv1.setY(17);

        }

        if(i==3) {

            iv1.setX(48);
            iv1.setY(17);

        }*/
    /*    if(i==1) {
            iv1.setX(38);
            iv1.setY(70);
        }
        if(i==2) {

            iv1.setX(43);
            iv1.setY(17);


        }

        if(i==3) {
            iv1.setX(48);
            iv1.setY(17);

        }

        if(i==4) {

            iv1.setX(54);
            iv1.setY(17);

        }

        if(i==5) {

            iv1.setX(33);
            iv1.setY(22);

        }

        if(i==6) {
            iv1.setX(37);
            iv1.setY(22);
        }

        if(i==7) {

            iv1.setX(42);
            iv1.setY(22);
        }

        if(i==8) {
            iv1.setX(47);
            iv1.setY(22);
        }

        if(i==9) {
            iv1.setX(0);
            iv1.setY(10);

        }

        if(i==10) {
            iv1.setX(63);
            iv1.setY(55);
        }

        if(i==11) {
            iv1.setX(10);
            iv1.setY(10);
        }*/
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

        Call<DeskAvaliabilityResponse> call = apiService.getAvaliableDeskDetails(4,now,now, now);
        call.enqueue(new Callback<DeskAvaliabilityResponse>() {
            @Override
            public void onResponse(Call<DeskAvaliabilityResponse> call, Response<DeskAvaliabilityResponse> response) {

                DeskAvaliabilityResponse deskAvaliabilityResponseList=response.body();

                System.out.println("InsideDataValaibelity");
                System.out.println("AllDeskSize"+desks.size());
                System.out.println("ActiveDeskSize"+ deskAvaliabilityResponseList.getLocationDesksList().size());

                List<String> deskCodeList=new ArrayList<>();
                for (int i = 0; i <desks.size() ; i++) {
                    deskCodeList.add(desks.get(i).getDeskCode());
                }

                for (int i = 0; i <deskAvaliabilityResponseList.getLocationDesksList().size() ; i++) {

                    if(deskCodeList.contains(deskAvaliabilityResponseList.getLocationDesksList().get(i).getCode())){
                        System.out.println("AvaliableDesks"+deskAvaliabilityResponseList.getLocationDesksList().get(i).getCode());
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

    private void CallFloorBottomSheet(List<LocateCountryRespose> locateCountryResposes) {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(getLayoutInflater().
                inflate(R.layout.bottom_sheet_locate_floor_filter,
                new RelativeLayout(getContext())));

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();
            }
        });


        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //country.setText("Global Location");
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
                stateId=locateCountryRespose.getLocateCountryId();
                country.setText(locateCountryRespose.getName());
                callCountrysChildData(locateCountryRespose.getLocateCountryId());
                break;

            case "STATE":
                state.setText(locateCountryRespose.getName());
                rvState.setVisibility(View.GONE);
                getFloorDetails(locateCountryRespose.getLocateCountryId());
                break;

            case "FLOOR":
                streetBlock.setVisibility(View.VISIBLE);
                street.setVisibility(View.VISIBLE);
                street.setText(locateCountryRespose.getName());
                rvStreet.setVisibility(View.GONE);

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