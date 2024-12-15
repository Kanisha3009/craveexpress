package com.example.craveexpress;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize DatabaseHelper
        myDb = new DatabaseHelper(this);

        // Get references to UI elements
        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signupButton);
        EditText emailField = findViewById(R.id.emailField);
        EditText passwordField = findViewById(R.id.passwordField);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        CheckBox rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);

        // Handle Login Button Click
        loginButton.setOnClickListener(view -> {
            // Retrieve email and password entered by the user
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            // Validate input fields
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(MainActivity.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "Please enter your password.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Authenticate the user
            try {
                boolean isAuthenticated = myDb.checkUser(email, password);
                Log.d("MainActivity", "Authentication result: " + isAuthenticated);
                if (isAuthenticated) {
                    // If authentication is successful, show a success message and navigate to the next activity
                    Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity
                } else {
                    // If authentication fails, show an error message
                    Toast.makeText(MainActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("MainActivity", "Error during login: ", e);
                Toast.makeText(MainActivity.this, "An error occurred during login.", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Signup Button Click
        signupButton.setOnClickListener(view -> {
            // Navigate to SignupActivity
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // Handle Forgot Password Click
        forgotPassword.setOnClickListener(view ->
                Toast.makeText(MainActivity.this, "Forgot Password clicked!", Toast.LENGTH_SHORT).show()
        );

        // Handle Remember Me Checkbox
        rememberMeCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(MainActivity.this, "Remember Me enabled!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Remember Me disabled!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
