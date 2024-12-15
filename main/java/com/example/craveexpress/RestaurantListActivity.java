package com.example.craveexpress;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.restaurantRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Insert sample restaurant data if database is empty
        if (dbHelper.getAllRestaurants().isEmpty()) {
            dbHelper.insertRestaurant("Lil' Bird & The Big Bear Cafe", "4.5", R.drawable.restaurant_1);
            dbHelper.insertRestaurant("The Humble Food Company", "4.2", R.drawable.restaurant_2);
            dbHelper.insertRestaurant("One Serambi Cafe", "4.3", R.drawable.restaurant_3);
        }

        // Fetch all restaurants from the database
        List<Restaurant> restaurantList = dbHelper.getAllRestaurants();

        if (restaurantList != null && !restaurantList.isEmpty()) {
            // Set up RecyclerView Adapter
            RestaurantAdapter adapter = new RestaurantAdapter(restaurantList, this);
            recyclerView.setAdapter(adapter);
        } else {
            // Handle empty list (optional)
            System.out.println("No restaurants found in the database.");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close(); // Close the database helper when activity is destroyed
        }
    }
}
