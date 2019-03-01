package com.example.foodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.Model.Food;
import com.example.foodapp.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodListActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference foodlist;
    RecyclerView recycler_food;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    String categoryId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //init firebase
        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Food");

        recycler_food = findViewById(R.id.recycler_food);
        recycler_food.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_food.setLayoutManager(layoutManager);

        //get intent
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryID");

            if (!categoryId.isEmpty()) {
                loadListFood(categoryId.trim());
                Toast.makeText(FoodListActivity.this, categoryId, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadListFood(String categoryId) {

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodlist.orderByChild("MenuId").equalTo(categoryId)) //like select * from foods where menuId =

        {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {


                viewHolder.txt_foodName.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.img_food);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //start new activity
                        Intent intent  = new Intent(FoodListActivity.this,FoodDetailActivity.class);
                        intent.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };

        Log.d("adapter", "loadListFood: "+adapter.getItemCount());
        recycler_food.setAdapter(adapter);
    }
}