package com.example.foodapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;



    public MenuViewHolder(View itemView) {
        super(itemView);

        Log.d("sss", "MenuViewHolder: ");
        txtMenuName = itemView.findViewById(R.id.menu_name);
        imageView   = itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);
    }


}
