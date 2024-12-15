package com.example.craveexpress;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        myDb = new DatabaseHelper(this);

        Button confirmOrderButton = findViewById(R.id.confirmOrderButton);
        TextView orderSummary = findViewById(R.id.orderSummary);
        TextView totalPriceView = findViewById(R.id.totalPrice);
        EditText deliveryAddress = findViewById(R.id.deliveryAddress);
        EditText contactNumber = findViewById(R.id.contactNumber);
        RadioGroup paymentMethodGroup = findViewById(R.id.paymentMethodGroup);

        List<FoodDrink> selectedItems = getIntent().getParcelableArrayListExtra("selectedItems");
        double totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);

        // Build order summary
        StringBuilder summary = new StringBuilder();
        for (FoodDrink item : selectedItems) {
            String itemName = item.getName();
            int quantity = item.getQuantity();
            double pricePerUnit = Double.parseDouble(item.getPrice());
            double itemTotal = quantity * pricePerUnit;

            // Save each item in the database
            boolean isInserted = myDb.insertOrderDetail(itemName, quantity, pricePerUnit, itemTotal);
            if (!isInserted) {
                Toast.makeText(this, "Failed to save order item: " + itemName, Toast.LENGTH_SHORT).show();
            }

            summary.append(itemName)
                    .append(" x ")
                    .append(quantity)
                    .append(" = RM")
                    .append(itemTotal)
                    .append("\n");
        }

        // Display the summary in the TextViews
        orderSummary.setText(summary.toString());
        totalPriceView.setText("Total Price: RM" + totalPrice);

        confirmOrderButton.setOnClickListener(view -> {
            // Get user input from delivery address, contact number, and payment method
            String delivery = deliveryAddress.getText().toString();
            String contact = contactNumber.getText().toString();
            int selectedPaymentId = paymentMethodGroup.getCheckedRadioButtonId();
            RadioButton selectedPaymentMethod = findViewById(selectedPaymentId);
            String paymentMethod = selectedPaymentMethod.getText().toString();

            // Save order summary to the database
            boolean isInserted = myDb.insertOrderSummary(summary.toString(), totalPrice, delivery, contact, paymentMethod);
            if (!isInserted) {
                Toast.makeText(this, "Failed to save order summary.", Toast.LENGTH_SHORT).show();
            }

            // Pass order details to the next activity
            Intent intent = new Intent(CheckoutActivity.this, OrderTrackingActivity.class);
            intent.putExtra("orderSummary", summary.toString());
            intent.putExtra("totalPrice", totalPrice);
            startActivity(intent);
        });
    }
}
