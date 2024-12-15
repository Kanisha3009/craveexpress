package com.example.craveexpress;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ReceiptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        // Initialize UI components
        TextView receiptSummary = findViewById(R.id.receiptSummary);
        TextView receiptTotalPrice = findViewById(R.id.receiptTotalPrice);
        Button returnHomeButton = findViewById(R.id.returnHomeButton);

        // Retrieve order details passed from OrderTrackingActivity
        String orderSummary = getIntent().getStringExtra("orderSummary");
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        // Populate the TextViews with order details
        receiptSummary.setText(orderSummary != null ? orderSummary : "No items found");
        receiptTotalPrice.setText(String.format("Total Price: RM%.2f", totalPrice));

        // Handle the Return to Homepage button click
        returnHomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ReceiptActivity.this, RestaurantListActivity.class);
            startActivity(intent);
            finish(); // Close the ReceiptActivity
        });
    }
}
