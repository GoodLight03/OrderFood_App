package com.example.orderfood.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.orderfood.Activity.Activity.Adaptor.CategoryAdaptor;
import com.example.orderfood.Activity.Activity.Adaptor.PopularAdaptor;
import com.example.orderfood.Activity.Activity.Adaptor.foodAdaptor;
import com.example.orderfood.Activity.Domain.DoanDomain;
import com.example.orderfood.Activity.Domain.FoodDomain;
import com.example.orderfood.R;

import java.util.ArrayList;

public class ListSPActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter3;
    private RecyclerView recyclerViewsp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_spactivity);
    }
    private void setRecyclerViewsp(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewsp=findViewById(R.id.recyclerview3);
        recyclerViewsp.setLayoutManager(linearLayoutManager);

        ArrayList<DoanDomain> food=new ArrayList<>();
        food.add(new DoanDomain("Pepperoni pizza","pop_1",9.76));
        food.add(new DoanDomain("Pepperoni pizza","pop_2",8.79));
        food.add(new DoanDomain("Vegetable Pizza","pop_3",8.5));

        adapter3=new foodAdaptor(food);
        recyclerViewsp.setAdapter(adapter3);
    }
}