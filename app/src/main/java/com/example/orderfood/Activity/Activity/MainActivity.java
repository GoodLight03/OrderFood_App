package com.example.orderfood.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.orderfood.Activity.Activity.Adaptor.CategoryAdaptor;
import com.example.orderfood.Activity.Activity.Adaptor.PopularAdaptor;
import com.example.orderfood.Activity.Domain.CategoryDomain;
import com.example.orderfood.Activity.Domain.FoodDomain;
import com.example.orderfood.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter,adapter2;
    private RecyclerView recyclerViewCategorylist,recyclerViewPopularlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewCategory();
        recyclerViewpopular();
        bottomNavigation();
    }
    private void bottomNavigation(){
        FloatingActionButton floatingActionButton =findViewById(R.id.cartBtn);
        LinearLayout homeBtn =findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });
    }
    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategorylist=findViewById(R.id.recyclerView);
        recyclerViewCategorylist.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category =new ArrayList<>();
        category.add(new CategoryDomain("pizza","cat_1"));
        category.add(new CategoryDomain("Burger","cat_2"));
        category.add(new CategoryDomain("Hotdog","cat_3"));
        category.add(new CategoryDomain("Drink","cat_4"));
        category.add(new CategoryDomain("Donut","cat_5"));

        adapter=new CategoryAdaptor(category);
        recyclerViewCategorylist.setAdapter(adapter);
    }
    private void recyclerViewpopular(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularlist=findViewById(R.id.recyclerView2);
        recyclerViewPopularlist.setLayoutManager(linearLayoutManager);
        ArrayList<FoodDomain> foodList=new ArrayList<>();
        foodList.add(new FoodDomain("Pepperoni pizza","pop_1","slices peperoni,mozzerella cheese,fresh oregano,ground black pepper,pizza sauce",9.76));
        foodList.add(new FoodDomain("Cheese Burger","pop_2","beef,Gouda Cheese,Special Sauce,Lettuce,tomato",8.79));
        foodList.add(new FoodDomain("Vegetable Pizza","pop_3","olive oil,Vegetable oil,pitted kalamata,cherry tomatoes,fresh oregano,basil",8.5));

        adapter2=new PopularAdaptor(foodList);
        recyclerViewPopularlist.setAdapter(adapter2);
    }
}