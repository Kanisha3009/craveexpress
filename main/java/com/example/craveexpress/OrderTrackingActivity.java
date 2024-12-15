package com.example.craveexpress;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OrderTrackingActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);

        Button confirmhomeButton = findViewById(R.id.confirmhomeButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView trackingText = findViewById(R.id.trackingText);

        // Retrieve order details from the Intent
        String orderSummary = getIntent().getStringExtra("orderSummary");
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        confirmhomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(OrderTrackingActivity.this, RestaurantListActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(OrderTrackingActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Simulate order progress
        new Thread(() -> {
            for (int progress = 0; progress <= 100; progress += 10) {
                final int currentProgress = progress;
                runOnUiThread(() -> {
                    progressBar.setProgress(currentProgress);
                    trackingText.setText("Order Progress: " + currentProgress + "%");

                    if (currentProgress == 100) {
                        // Transition to ReceiptActivity
                        Intent intent = new Intent(OrderTrackingActivity.this, ReceiptActivity.class);
                        intent.putExtra("orderSummary", orderSummary);
                        intent.putExtra("totalPrice", totalPrice);
                        startActivity(intent);
                        finish(); // Close OrderTrackingActivity
                    }
                });

                try {
                    Thread.sleep(500); // Simulate a delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
