package com.example.orderfood.Activity.Activity.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfood.Activity.Domain.DoanDomain;
import com.example.orderfood.Activity.Domain.FoodDomain;
import com.example.orderfood.R;

import java.util.ArrayList;

public class foodAdaptor extends RecyclerView.Adapter<foodAdaptor.ViewHolder>{
    ArrayList<DoanDomain> food;

    public foodAdaptor(ArrayList<DoanDomain> food) {
        this.food = food;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_food,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titletxt.setText(food.get(position).getTitletxt());
        holder.fee.setText(String.valueOf(food.get(position).getFee()));

        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(food.get(position).getPicCart(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()) //thu vien opensource ho tro load anh
                .load(drawableResourceId)
                .into(holder.picCart);

    }

    @Override
    public int getItemCount() {
        return food.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titletxt;
        ImageView picCart;
        TextView fee;
        ConstraintLayout foodlayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titletxt=itemView.findViewById(R.id.titleTxt);
            picCart=itemView.findViewById(R.id.picCart);
            fee=itemView.findViewById(R.id.feeEachitem);
            foodlayout=itemView.findViewById(R.id.foodlayout);
        }
    }
}
