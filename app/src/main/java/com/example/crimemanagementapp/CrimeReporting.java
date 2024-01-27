package com.example.crimemanagementapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CrimeReporting extends AppCompatActivity {

    private EditText editTextCrimeType;
    private EditText editTextDateTime;
    private EditText editTextLocation;
    private EditText editTextDescription;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_reporting);

        // Initialize views
        editTextCrimeType = findViewById(R.id.editTextCrimeType);
        editTextDateTime = findViewById(R.id.editTextDateTime);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set onClickListener for the submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to handle crime report submission
                submitCrimeReport();
            }
        });
    }

    private void submitCrimeReport() {
        // Get user input
        String crimeType = editTextCrimeType.getText().toString();
        String dateTime = editTextDateTime.getText().toString();
        String location = editTextLocation.getText().toString();
        String description = editTextDescription.getText().toString();

        // Validate input
        if (crimeType.isEmpty() || dateTime.isEmpty() || location.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Here, you can save the crime report to the database or perform further actions
            // For simplicity, we'll display a toast message indicating successful submission
            Toast.makeText(this, "Crime report submitted successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
