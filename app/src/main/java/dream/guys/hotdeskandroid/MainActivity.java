package dream.guys.hotdeskandroid;

import static dream.guys.hotdeskandroid.utils.MyApp.getContext;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import dream.guys.hotdeskandroid.adapter.SearchRecyclerAdapter;
import dream.guys.hotdeskandroid.databinding.ActivityMainBinding;
import dream.guys.hotdeskandroid.example.MyCanvasDraw;
import dream.guys.hotdeskandroid.model.request.Point;
import dream.guys.hotdeskandroid.model.response.GlobalSearchResponse;
import dream.guys.hotdeskandroid.model.response.LocateCountryRespose;
import dream.guys.hotdeskandroid.utils.AppConstants;
import dream.guys.hotdeskandroid.utils.SessionHandler;
import dream.guys.hotdeskandroid.utils.Utils;
import dream.guys.hotdeskandroid.webservice.ApiClient;
import dream.guys.hotdeskandroid.webservice.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements SearchRecyclerAdapter.GlobalSearchOnClickable {
    ActivityMainBinding binding;
    BottomNavigationView navView;
    NavController navController;


    private ScaleGestureDetector mScaleGestureDetector;
    GestureDetector gestureDetector;
    private float mScale = 1f;

    LinearLayout searchLayout;
    LinearLayoutManager linearLayoutManager;
    Dialog dialog;
    SearchRecyclerAdapter searchRecyclerAdapter;
    List<GlobalSearchResponse.Results> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new Dialog(this);

        uiInit();
        nightModeConfig();
//        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(binding.serachBar.getWindowToken(), 0);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.searchRecycler.setLayoutManager(linearLayoutManager);
        binding.searchRecycler.setHasFixedSize(true);
        searchRecyclerAdapter=new SearchRecyclerAdapter(getApplicationContext(),MainActivity.this,list,this);
        binding.searchRecycler.setAdapter(searchRecyclerAdapter);

        // homeBookingListAdapter=new HomeBookingListAdapter(getContext(), getActivity(), recyclerModelArrayList);

        binding.serachBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()==0){
                    list.clear();
                    searchRecyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(binding.serachBar.getWindowToken(), 0);
                callSearchRecyclerData(s.toString());
            }
        });
        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.serachBar.clearComposingText();
                binding.searchLayout.setVisibility(View.GONE);
            }
        });


      gestureDetector = new GestureDetector(this, new GestureListener());

      mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener(){
            @Override
            public boolean onScale(ScaleGestureDetector detector) {

                System.out.println("DectorValue"+detector.getScaleFactor());

                float scale = 1 - detector.getScaleFactor();
                float prevScale = mScale;
                mScale += scale;

                System.out.println("ZoomMuraliScaleValue"+mScale);
                System.out.println("ZoomScale"+scale);

                if(mScale>0) {

                    if (mScale > 10f)
                        mScale = 10f;

                    ScaleAnimation scaleAnimation = new ScaleAnimation(1f / prevScale, 1f / mScale, 1f / prevScale, 1f / mScale, detector.getFocusX(), detector.getFocusY());
                    scaleAnimation.setDuration(0);
                    scaleAnimation.setFillAfter(true);
                    /*ScrollView layout = findViewById(R.id.scrollView);
                    if (layout != null) {
                        layout.startAnimation(scaleAnimation);
                    }*/

                }else {
                    System.out.println("You Cant zoom more than this");
                }
                    return true;

            }
        });
    }

    private void callSearchRecyclerData(String searchText) {
        if (Utils.isNetworkAvailable(this)) {
//            dialog= ProgressDialog.showProgressBar(this);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<GlobalSearchResponse> call = apiService.getGlobalSearchData(40,searchText);
            call.enqueue(new Callback<GlobalSearchResponse>() {
                @Override
                public void onResponse(Call<GlobalSearchResponse> call, Response<GlobalSearchResponse> response) {
//                    Toast.makeText(MainActivity.this, "on res", Toast.LENGTH_SHORT).show();
                    if(response.code()==200){
//                        ProgressDialog.dismisProgressBar(MainActivity.this,dialog);
                        list.clear();
                        if (response.body().getResults()!=null)
                            list.addAll(response.body().getResults());
                        Toast.makeText(MainActivity.this, "ls "+list.size(), Toast.LENGTH_SHORT).show();

                        Toast.makeText(MainActivity.this, "200"+searchText, Toast.LENGTH_SHORT).show();
                        searchRecyclerAdapter.notifyDataSetChanged();
                        Log.d("Search", "onResponse: 200");
                    }else if(response.code()==401){
                        //Handle if token got expired
//                        ProgressDialog.dismisProgressBar(MainActivity.this,dialog);

                    } else {
//                        ProgressDialog.dismisProgressBar(MainActivity.this,dialog);
                        Log.d("Search", "onResponse: else");
                    }

                }
                @Override
                public void onFailure(Call<GlobalSearchResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "on fail", Toast.LENGTH_SHORT).show();
//                    ProgressDialog.dismisProgressBar(MainActivity.this,dialog);
                    Log.d("Search", "onResponse: fail"+t.getMessage());
                }
            });

        } else {
            Utils.toastMessage(this, "Please Enable Internet");
        }
    }

    public void showSearch(){
        binding.searchLayout.setVisibility(View.VISIBLE);
    }

    private void nightModeConfig() {
        int nightModeFlags = getApplicationContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }
    }

    private void uiInit() {
        navView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        NavigationUI.setupWithNavController(binding.navView, navController);
        navView.setItemIconTintList(null);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        mScaleGestureDetector.onTouchEvent(ev);
        gestureDetector.onTouchEvent(ev);
        return gestureDetector.onTouchEvent(ev);
    }



    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    public void getFloorCoordinatesInMain(List<List<Integer>> coordinateList, LinearLayout secondLayout){
        List<Point> pointList=new ArrayList<>();
        System.out.println("CoordinateSize" + coordinateList.size());

        for (int i = 0; i < coordinateList.size(); i++) {

            System.out.println("CoordinateData" + i + "position" + "size " + coordinateList.get(i).size());

            Point point = new Point(coordinateList.get(i).get(0) + 40, coordinateList.get(i).get(1) + 20);
            pointList.add(point);

        }

        if (pointList.size() > 0) {
            MyCanvasDraw myCanvasDraw = new MyCanvasDraw(getContext(), pointList);

            secondLayout.addView(myCanvasDraw);

        }

    }

    @Override
    public void onClickGlobalSearch(GlobalSearchResponse.Results results) {

        SessionHandler.getInstance().saveInt(getContext(), AppConstants.PARENT_ID, results.getCurrentLocation().getParentLocationId());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<LocateCountryRespose>> call=apiService.getCountrysChild(results.getCurrentLocation().getParentLocationId());
        call.enqueue(new Callback<List<LocateCountryRespose>>() {
            @Override
            public void onResponse(Call<List<LocateCountryRespose>> call, Response<List<LocateCountryRespose>> response) {

                List<LocateCountryRespose> locateCountryResposeList=response.body();

                for (int i = 0; i <locateCountryResposeList.size() ; i++) {

                    if(results.getCurrentLocation().getId()==locateCountryResposeList.get(i).getLocateCountryId()){
                        int floorPosition=i;
                        System.out.println("FloorPositionInMainActivity"+floorPosition);
                        SessionHandler.getInstance().saveInt(MainActivity.this, AppConstants.FLOOR_POSITION,floorPosition);
                        binding.searchRecycler.setVisibility(View.GONE);
                        NavController navController1 = Navigation.findNavController(MainActivity.this, R.id.navigation_home);
                        //NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
                        navController1.navigate(R.id.navigation_locate);
                    }else {
                        Utils.toastMessage(MainActivity.this,"Selected Floor Is Not Avaliable");
                    }



                }

            }

            @Override
            public void onFailure(Call<List<LocateCountryRespose>> call, Throwable t) {

            }
        });

        //Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_navigation_locate2);
        //NavController navController = Navigation.findNavController(navView);
        //navController.navigate(R.id.action_navigation_home_to_navigation_locate2);




        /*navView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navView.setItemIconTintList(null);*/

    }
}