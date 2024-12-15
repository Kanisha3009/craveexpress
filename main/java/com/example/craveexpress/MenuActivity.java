package com.example.craveexpress;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private final List<FoodDrink> selectedItems = new ArrayList<>();
    private double totalPrice = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        RecyclerView foodDrinkRecyclerView = findViewById(R.id.foodDrinkRecyclerView);
        foodDrinkRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Hardcoded food and drink list
        List<FoodDrink> foodDrinkList = new ArrayList<>();
        foodDrinkList.add(new FoodDrink("English Breakfast", "19.90", R.drawable.foodmenu_1));
        foodDrinkList.add(new FoodDrink("Fish N Chips", "24.90", R.drawable.foodmenu_2));
        foodDrinkList.add(new FoodDrink("Chicken Wrap", "14.50", R.drawable.foodmenu_3));
        foodDrinkList.add(new FoodDrink("Chicken Burger", "18.90", R.drawable.foodmenu_4));
        foodDrinkList.add(new FoodDrink("Salad of The Day", "12.50", R.drawable.foodmenu_5));
        foodDrinkList.add(new FoodDrink("Blue Lagoon", "7.50", R.drawable.drinkmenu_1));
        foodDrinkList.add(new FoodDrink("Cafe Latte", "6.90", R.drawable.drinkmenu_2));
        foodDrinkList.add(new FoodDrink("Chocolate Milkshake", "12.90", R.drawable.drinkmenu_3));

        // Set adapter
        FoodDrinkAdapter adapter = new FoodDrinkAdapter(foodDrinkList, this::updateSelectedItems);
        foodDrinkRecyclerView.setAdapter(adapter);

        // Confirm order button
        Button confirmOrderButton = findViewById(R.id.confirmOrderButton);
        confirmOrderButton.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, CheckoutActivity.class);
            intent.putParcelableArrayListExtra("selectedItems", new ArrayList<>(selectedItems));
            intent.putExtra("totalPrice", totalPrice);
            startActivity(intent);
        });
    }

    private void updateSelectedItems(FoodDrink foodDrink, int quantity) {
        selectedItems.removeIf(item -> item.getName().equals(foodDrink.getName())); // Avoid duplicates
        if (quantity > 0) {
            foodDrink.setQuantity(quantity);
            selectedItems.add(foodDrink);
        }
        totalPrice = selectedItems.stream().mapToDouble(item -> item.getQuantity() * Double.parseDouble(item.getPrice())).sum();
    }
}
