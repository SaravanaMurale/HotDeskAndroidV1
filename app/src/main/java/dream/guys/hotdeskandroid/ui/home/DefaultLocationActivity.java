package dream.guys.hotdeskandroid.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.DefaultLocationAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityDefaultLocationBinding;
import dream.guys.hotdeskandroid.model.response.DAOActiveLocation;
import dream.guys.hotdeskandroid.model.response.FirstAidResponse;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.ui.login.LoginActivity;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefaultLocationActivity extends AppCompatActivity implements DefaultLocationAdapter.DefaultLocationInterface {

    ActivityDefaultLocationBinding binding;
    ArrayList<DAOActiveLocation> activeLocationArrayList = new ArrayList<>();
    ArrayList<DAOActiveLocation> finalLocationArrayList = new ArrayList<>();
    DefaultLocationAdapter defaultLocationAdapter;


    //
    ArrayList<DAOActiveLocation> firstParentLocationArrayList = new ArrayList<>();
    ArrayList<DAOActiveLocation> cityLocationArrayList = new ArrayList<>();
    ArrayList<DAOActiveLocation> cityPlaceArrayList = new ArrayList<>();
    ArrayList<DAOActiveLocation> cityPlaceFloorArrayList = new ArrayList<>();

    int floorParentID = 0, cityPlaceID = 0, cityPlaceParentID = 0,cityID = 0,cityParentID = 0,locationID = 0,locationParentID = 0;

    String sFrom = "";
    Context context;
    int mainParentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDefaultLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = DefaultLocationActivity.this;

        getLocateCountryList();

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //New...
        try {
            Intent intent = getIntent();
            if (intent!=null && intent.getStringExtra(AppConstants.FROM)!=null){
                sFrom = intent.getStringExtra(AppConstants.FROM);
            }
        }catch (Exception e){

        }


    }

    //New...
    private void getLocateCountryList() {

        if (Utils.isNetworkAvailable(context)) {
            binding.locateProgressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<LocateCountryRespose>> call = apiService.getLocationCountryList();
            call.enqueue(new Callback<List<LocateCountryRespose>>() {
                @Override
                public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                    if (response.body()!=null && response.code() == 200 && response.body().size()>0) {

                        List<LocateCountryRespose> locateCountryResposes = response.body();
                        locateCountryResposes.sort(Comparator.comparing(LocateCountryRespose::getName, String::compareToIgnoreCase));

                        if (locateCountryResposes.size()>0) {
                            mainParentID = locateCountryResposes.get(0).getParentLocationId();
                            defaultLocationCall();
                        }

                    }else {
                        binding.locateProgressBar.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

                    binding.locateProgressBar.setVisibility(View.INVISIBLE);

                }
            });

        } else {
            Utils.toastMessage(context, getResources().getString(R.string.enable_internet));
        }

    }

    public void defaultLocationCall() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DAOActiveLocation>> call = apiService.getActiveLocations();
        call.enqueue(new Callback<ArrayList<DAOActiveLocation>>() {
            @Override
            public void onResponse(Call<ArrayList<DAOActiveLocation>> call, Response<ArrayList<DAOActiveLocation>> response) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
                if (response.body()!=null && response.code() == 200) {

                    activeLocationArrayList = new ArrayList<>();
                    activeLocationArrayList = response.body();

                    setLogic();

                }

            }

            @Override
            public void onFailure(Call<ArrayList<DAOActiveLocation>> call, Throwable t) {
                binding.locateProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void setLogic() {

        Collections.sort(activeLocationArrayList, new Comparator<DAOActiveLocation>() {
            @Override
            public int compare(DAOActiveLocation str1, DAOActiveLocation str2) {
                if(null == str1.getParentLocationId()) {
                    return null == str2.getParentLocationId() ? 0 : 1;
                }
                else if(null == str2.getParentLocationId()) {
                    return -1;
                }
                return str1.getParentLocationId().compareTo(str2.getParentLocationId());
            }
        });

        //activeLocationArrayList.remove(activeLocationArrayList.size()-1);
        activeLocationArrayList = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().
                filter(val -> val.getParentLocationId() != null).collect(Collectors.toList());

        firstParentLocationArrayList = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(val -> val.getParentLocationId() == mainParentID).collect(Collectors.toList());

        setCityLogic();

    }

    private void setCityLogic() {

        for (int i=0;i<firstParentLocationArrayList.size();i++) {

            //finalLocationArrayList.add(firstParentLocationArrayList.get(i));

            for (int j=0;j<activeLocationArrayList.size();j++) {

                if (firstParentLocationArrayList.get(i).getId()
                        .equals(activeLocationArrayList.get(j).getParentLocationId())) {
                    activeLocationArrayList.get(j).setLevel(2);
                }

            }

        }

        cityLocationArrayList = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(val -> val.getLevel() == 2).collect(Collectors.toList());

        setThirdLvl();

    }

    private void setThirdLvl() {

        for (int j=0;j<cityLocationArrayList.size();j++) {

            for (int k=0;k<activeLocationArrayList.size();k++) {
                if (cityLocationArrayList.get(j).getId().equals(activeLocationArrayList.get(k).getParentLocationId())) {
                    activeLocationArrayList.get(k).setLevel(3);
                }
            }

        }

        cityPlaceArrayList = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(val -> val.getLevel() == 3).collect(Collectors.toList());

        setFourthLvl();

    }

    private void setFourthLvl() {

        for (int j=0;j<cityPlaceArrayList.size();j++) {

            for (int k=0;k<activeLocationArrayList.size();k++) {
                if (cityPlaceArrayList.get(j).getId().equals(activeLocationArrayList.get(k).getParentLocationId())) {
                    activeLocationArrayList.get(k).setLevel(4);
                }
            }

        }

        cityPlaceFloorArrayList = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(val -> val.getLevel() == 4).collect(Collectors.toList());

        setAllaignFloors();

    }

    private void setAllaignFloors() {

        finalLocationArrayList = new ArrayList<>();

        loopa:
        for (int a=0;a<firstParentLocationArrayList.size();a++) {

            finalLocationArrayList.add(firstParentLocationArrayList.get(a));

            loopb:
            for (int b=0;b<cityLocationArrayList.size();){

                if (firstParentLocationArrayList.get(a).getId().
                        equals(cityLocationArrayList.get(b).getParentLocationId())) {

                    finalLocationArrayList.add(cityLocationArrayList.get(b));

                    loopc:
                    for (int c=0;c<cityPlaceArrayList.size();){

                        if (cityLocationArrayList.get(b).getId().equals
                                (cityPlaceArrayList.get(c).getParentLocationId())) {

                            finalLocationArrayList.add(cityPlaceArrayList.get(c));

                            int id = cityPlaceArrayList.get(c).getId();

                            finalLocationArrayList.addAll((ArrayList<DAOActiveLocation>) cityPlaceFloorArrayList.stream().filter(val -> val.getParentLocationId() == id).collect(Collectors.toList()));

                            if (c == cityPlaceArrayList.size()-1){
                                b++;
                                break loopc;
                            }else {
                                c++;
                            }

                            /*for (int d=0;d<cityPlaceFloorArrayList.size();d++){

                                if (cityPlaceArrayList.get(c).getId().equals(cityPlaceFloorArrayList.get(d).getParentLocationId())) {
                                    finalLocationArrayList.add(cityPlaceFloorArrayList.get(d));
                                }
                            }*/

                        }else {
                            //c++;
                            if (c == cityPlaceArrayList.size()-1){
                                b++;
                                break loopc;
                            }else {
                                c++;
                            }
                        }

                    }

                }else {
                    b++;
                }

            }
        }

        setAdapter();


    }


    public void setAdapter() {

        //activeLocationArrayList.sort(Comparator.comparing(DAOActiveLocation::getLevel));

        defaultLocationAdapter = new DefaultLocationAdapter(DefaultLocationActivity.this,
                finalLocationArrayList,this,sFrom);
        binding.recyclerview.setAdapter(defaultLocationAdapter);

    }

    @Override
    public void clickEvent(int position,String floorName,int locationId) {
        if (sFrom!=null && !sFrom.equalsIgnoreCase(AppConstants.DefaultLocation))
            SessionHandler.getInstance().saveInt(DefaultLocationActivity.this,AppConstants.DEFAULT_CAR_PARK_LOCATION_ID,
                locationId);

        Intent intent = new Intent();
        intent.putExtra("List",finalLocationArrayList);
        intent.putExtra("FloorList",cityPlaceFloorArrayList);
        intent.putExtra("Position",position);
        intent.putExtra("floorName",floorName);
        intent.putExtra("sFrom",sFrom);
        setResult(RESULT_OK,intent);
        finish();

    }
}