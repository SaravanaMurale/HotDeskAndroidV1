package com.brickendon.hdplus.example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import java.util.ArrayList;

import com.brickendon.hdplus.R;

public class NestedMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataModel> mList;
    private ItemAdapter adapter;

    ValuesPOJO valuesPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_main);

        recyclerView = findViewById(R.id.main_recyclervie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();

        //list1
        ArrayList<ValuesPOJO> nestedList1 = new ArrayList<>();

        valuesPOJO = new ValuesPOJO("Jams and Honey",false);
        nestedList1.add(valuesPOJO);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Pickles and Chutneys",false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Readymade Meals",false);
        nestedList1.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Chyawanprash and Health Foods",false);
        nestedList1.add(valuesPOJO);

        ArrayList<ValuesPOJO> nestedList2 = new ArrayList<>();

        valuesPOJO = new ValuesPOJO("Book",false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Pen",false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Office Chair",false);
        nestedList2.add(valuesPOJO);
        valuesPOJO = new ValuesPOJO("Pencil",false);
        nestedList2.add(valuesPOJO);


        mList.add(new DataModel(nestedList1 , "Instant Food and Noodles"));
        mList.add(new DataModel( nestedList2,"Stationary"));

        adapter = new ItemAdapter(mList);
        recyclerView.setAdapter(adapter);
    }
}