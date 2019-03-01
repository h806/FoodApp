package com.example.foodapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_foodName;
    public ImageView img_food;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        txt_foodName = itemView.findViewById(R.id.food_name);
        img_food     = itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
