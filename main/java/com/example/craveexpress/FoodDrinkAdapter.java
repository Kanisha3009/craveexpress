package com.example.craveexpress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FoodDrinkAdapter extends RecyclerView.Adapter<FoodDrinkAdapter.FoodDrinkViewHolder> {

    private final List<FoodDrink> foodDrinkList;
    private final OnItemQuantityChangeListener listener;

    public interface OnItemQuantityChangeListener {
        void onQuantityChanged(FoodDrink foodDrink, int quantity);
    }

    public FoodDrinkAdapter(List<FoodDrink> foodDrinkList, OnItemQuantityChangeListener listener) {
        this.foodDrinkList = foodDrinkList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_drink, parent, false);
        return new FoodDrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodDrinkViewHolder holder, int position) {
        FoodDrink foodDrink = foodDrinkList.get(position);
        holder.foodDrinkName.setText(foodDrink.getName());
        holder.foodDrinkPrice.setText("RM" + foodDrink.getPrice());
        holder.foodDrinkImage.setImageResource(foodDrink.getImageResId());

        holder.quantityIncrease.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.foodDrinkQuantity.getText().toString()) + 1;
            holder.foodDrinkQuantity.setText(String.valueOf(quantity));
            listener.onQuantityChanged(foodDrink, quantity);
        });

        holder.quantityDecrease.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.foodDrinkQuantity.getText().toString());
            if (quantity > 0) {
                quantity--;
                holder.foodDrinkQuantity.setText(String.valueOf(quantity));
                listener.onQuantityChanged(foodDrink, quantity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodDrinkList.size();
    }

    public static class FoodDrinkViewHolder extends RecyclerView.ViewHolder {
        ImageView foodDrinkImage;
        TextView foodDrinkName, foodDrinkPrice, foodDrinkQuantity;
        Button quantityIncrease, quantityDecrease;

        public FoodDrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            foodDrinkImage = itemView.findViewById(R.id.foodDrinkImage);
            foodDrinkName = itemView.findViewById(R.id.foodDrinkName);
            foodDrinkPrice = itemView.findViewById(R.id.foodDrinkPrice);
            foodDrinkQuantity = itemView.findViewById(R.id.foodDrinkQuantity);
            quantityIncrease = itemView.findViewById(R.id.quantityIncrease);
            quantityDecrease = itemView.findViewById(R.id.quantityDecrease);
        }
    }
}
