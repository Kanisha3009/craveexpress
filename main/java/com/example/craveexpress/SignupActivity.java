package com.example.craveexpress;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        myDb = new DatabaseHelper(this);

        EditText fullName = findViewById(R.id.fullName);
        EditText emailAddress = findViewById(R.id.emailAddress);
        EditText phoneNumber = findViewById(R.id.phoneNumber);
        EditText password = findViewById(R.id.password);
        EditText confirmPassword = findViewById(R.id.confirmPassword);
        CheckBox agreeTermsCheckbox = findViewById(R.id.agreeTermsCheckbox);
        Button signupButton = findViewById(R.id.signupButton);



        signupButton.setOnClickListener(view -> {
            String name = fullName.getText().toString().trim();
            String email = emailAddress.getText().toString().trim();
            String phone = phoneNumber.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String confirmPass = confirmPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!pass.equals(confirmPass)) {
                Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else if (!agreeTermsCheckbox.isChecked()) {
                Toast.makeText(SignupActivity.this, "Please agree to Terms and Privacy", Toast.LENGTH_SHORT).show();
            } else {
                // Placeholder for signup logic
                Toast.makeText(SignupActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                Boolean isInserted = myDb.insertUser(fullName.getText().toString(),
                        emailAddress.getText().toString(),
                        phoneNumber.getText().toString(),
                        password.getText().toString());
                if(isInserted = true)
                    Toast.makeText(SignupActivity.this,"Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(SignupActivity.this,"Data not Inserted", Toast.LENGTH_LONG).show();
                // Navigate to login or main activity
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}

