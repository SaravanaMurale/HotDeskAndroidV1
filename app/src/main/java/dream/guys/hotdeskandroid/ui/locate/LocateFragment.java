package dream.guys.hotdeskandroid.ui.locate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import dream.guys.hotdeskandroid.R;
import dream.guys.hotdeskandroid.adapter.ShowCountryAdapter;
import dream.guys.hotdeskandroid.databinding.FragmentLocateBinding;
import dream.guys.hotdeskandroid.example.CanvasView;

public class LocateFragment extends Fragment {

    //BottomSheetData
    TextView country,state,street,floor,back;
    RecyclerView rvCountry,rvState,rvStreet;
    ShowCountryAdapter showCountryAdapter;
    LinearLayoutManager linearLayoutManager;
    RelativeLayout countryBlock,statBlock,streetBlock,floorBlock;


    FragmentLocateBinding binding;
    TextView locateText,title;


    @BindView(R.id.searchLocate)
    EditText searchLocate;

    CanvasView canvasView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //locateText= root.findViewById(R.id.locate_Text);
       /* locateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.bottomSheetTimePicker(getContext(),getActivity(),"End","ckasnckan");
            }
        });*/

        Bitmap bitmap = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        RectF rect = new RectF();
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, null, rect, paint);
//        paint.set(paint);
        binding.searchLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                CallFloorBottomSheet();



            }
        });


        return root;
    }

    private void CallFloorBottomSheet() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.AppBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(getLayoutInflater().inflate(R.layout.bottom_sheet_locate_floor_filter,
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
        getCountryStateStreetAndFloorDetails();

        rvCountry=bottomSheetDialog.findViewById(R.id.rvCountry);
        rvState=bottomSheetDialog.findViewById(R.id.rvState);
        rvStreet=bottomSheetDialog.findViewById(R.id.rvStreet);

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

                statBlock.setVisibility(View.GONE);
                rvState.setVisibility(View.GONE);

                streetBlock.setVisibility(View.GONE);
                rvStreet.setVisibility(View.GONE);
                floorBlock.setVisibility(View.GONE);

                getLocateCountryList();
            }
        });



        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvCountry.setLayoutManager(linearLayoutManager);
        rvCountry.setHasFixedSize(true);




        bottomSheetDialog.show();

    }



    private void getCountryStateStreetAndFloorDetails() {

        country.setText("United Kingdom");
        state.setText("London");
        street.setText("40 Bank Street ");
        floor.setText("Floor");
    }

    private void getLocateCountryList() {

        List<String> countryList=new ArrayList<>();

        countryList.add("United Kingdom");
        countryList.add("France");
        countryList.add("Poland");
        countryList.add("India");
        countryList.add("Australia");

        showCountryAdapter=new ShowCountryAdapter(getContext(),countryList);
        rvCountry.setAdapter(showCountryAdapter);

    }
}