package com.example.foodapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodapp.Interface.ItemClickListener;
import com.example.foodapp.Model.Food;
import com.example.foodapp.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodListActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference foodlist;
    RecyclerView recycler_food;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    String categoryId = "";

    //search functionality
    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

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
            }
        }

        materialSearchBar = findViewById(R.id.search_bar);
        materialSearchBar.setHint("enter food");
        LoadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //when user type food we change suggest list
                List<String> suggest = new ArrayList<>();
                for (String search:suggestList){
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);

                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when search bar is close
                //restore original adapter
                if (!enabled)
                    recycler_food.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //when search finish
                //show result of search adapter
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(CharSequence text) {
        //when click on search key
       searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
               Food.class,
               R.layout.food_item,
               FoodViewHolder.class,
               foodlist.orderByChild("Name").equalTo(text.toString())) {
           @Override
           protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
               viewHolder.txt_foodName.setText(model.getName());
               Log.d("adapter", "populateViewHolder:  "+model.getName());
               Picasso.get().load(model.getImage()).into(viewHolder.img_food);

               final Food local = model;
               viewHolder.setItemClickListener(new ItemClickListener() {
                   @Override
                   public void onClick(View view, int position, boolean isLongClick) {
                       //start new activity
                       Intent intent  = new Intent(FoodListActivity.this,FoodDetailActivity.class);
                       intent.putExtra("FoodId",searchAdapter.getRef(position).getKey());
                       startActivity(intent);
                   }
               });
           }
       } ;
       recycler_food.setAdapter(searchAdapter);
    }

    private void LoadSuggest() {
        //just load list name
        foodlist.orderByChild("MenuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            Food item = postSnapshot.getValue(Food.class);
                            suggestList.add(item.getName()); // add name of food to suggest list
                            Log.d("adapter", "onDataChange: "+item.getName());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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