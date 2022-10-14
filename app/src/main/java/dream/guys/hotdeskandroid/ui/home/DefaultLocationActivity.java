package dream.guys.hotdeskandroid.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import dream.guys.hotdeskandroid.adapter.DefaultLocationAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityDefaultLocationBinding;
import dream.guys.hotdeskandroid.model.response.DAOActiveLocation;
import dream.guys.hotdeskandroid.model.response.FirstAidResponse;
import dream.guys.hotdeskandroid.model.response.IncomingRequestResponse;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDefaultLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        defaultLocationCall();

        binding.profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void defaultLocationCall() {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ArrayList<DAOActiveLocation>> call = apiService.getActiveLocations();
        call.enqueue(new Callback<ArrayList<DAOActiveLocation>>() {
            @Override
            public void onResponse(Call<ArrayList<DAOActiveLocation>> call, Response<ArrayList<DAOActiveLocation>> response) {

                if (response.body()!=null && response.code() == 200) {

                    activeLocationArrayList = new ArrayList<>();
                    activeLocationArrayList = response.body();

                    setLogic();

                }

            }

            @Override
            public void onFailure(Call<ArrayList<DAOActiveLocation>> call, Throwable t) {

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

        firstParentLocationArrayList = (ArrayList<DAOActiveLocation>) activeLocationArrayList.stream().filter(val -> val.getParentLocationId() == 1).collect(Collectors.toList());

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
                finalLocationArrayList,this);
        binding.recyclerview.setAdapter(defaultLocationAdapter);

    }

    @Override
    public void clickEvent(int position,String floorName) {

        Intent intent = new Intent();
        intent.putExtra("List",finalLocationArrayList);
        intent.putExtra("FloorList",cityPlaceFloorArrayList);
        intent.putExtra("Position",position);
        intent.putExtra("floorName",floorName);
        setResult(RESULT_OK,intent);
        finish();

    }
}