package com.example.crimemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrimeReporting extends AppCompatActivity {

    private EditText editTextCrimeType;
    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private EditText editTextDateTime;
    private EditText editTextLocation;
    private EditText editTextDescription;
    private Button buttonSubmit;
    // Firebase Database reference
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_reporting);

        // Initialize views
        editTextCrimeType = findViewById(R.id.editTextCrimeType);
        editTextDateTime = findViewById(R.id.editTextDateTime);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.phonenumber);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("crimes");

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
        // Retrieve data from form fields
        EditText crimeTypeEditText = findViewById(R.id.editTextCrimeType);
        EditText dateTimeEditText = findViewById(R.id.editTextDateTime);
        EditText locationEditText = findViewById(R.id.editTextLocation);
        EditText descriptionEditText = findViewById(R.id.editTextDescription);
        EditText NameText = findViewById(R.id.editTextName);
        EditText PhoneNumberText = findViewById(R.id.phonenumber);

        // Get user input
        String crimeType = crimeTypeEditText.getText().toString();
        String dateTime = dateTimeEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String name = NameText.getText().toString();
        String phonenumber = PhoneNumberText.getText().toString();

        // Create a Crime object
        Crime crime = new Crime(crimeType, dateTime, location, description);

        // Push crime data to Firebase Database
        mDatabase.push().setValue(crime);

        // Create intent to pass data to CrimeList
        Intent intent = new Intent(CrimeReporting.this, CrimeReporting.class);
        intent.putExtra("crimeType", crimeType);
        intent.putExtra("dateTime", dateTime);
        intent.putExtra("location", location);
        intent.putExtra("description", description);
        intent.putExtra("name", name);
        intent.putExtra("phonenumber", phonenumber);

        // Start CrimeListActivity
        startActivity(intent);

        // Validate input
        if (crimeType.isEmpty() || dateTime.isEmpty() || location.isEmpty() || description.isEmpty() || name.isEmpty() || phonenumber.isEmpty()) {
            Toast.makeText(CrimeReporting.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else if (crimeType.length() <3) {
            Toast.makeText(this, "Enter your type of crime correctly", Toast.LENGTH_SHORT).show();
        } else if (crimeType.matches(".*\\d.*")) {
            crimeTypeEditText.setError("Crime Type cannot contain numbers");
        } else if (name.length() < 2) {
            NameText.setError("Please enter your name!!");
        } else {
            // Here, you can save the crime report to the database or perform further actions
            // For simplicity, we'll display a toast message indicating successful submission
            Toast.makeText(this, "Crime report submitted successfully", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(name)) {
            NameText.setError("Name is required");
            return;
        }

        if (name.length() < 2) {
            NameText.setError("Please enter your name!!");
            return;
        }
        if (name.matches(".*\\d.*")) {
            NameText.setError("Name cannot contain numbers");
            return;
        }

        if (TextUtils.isEmpty(phonenumber)) {
            PhoneNumberText.setError("Phone Number is required");
            return;
        }

        if (phonenumber.length() <= 5) {
            PhoneNumberText.setError("Enter your number correctly");
            return;
        }

        if (TextUtils.isEmpty(crimeType)) {
            crimeTypeEditText.setError("Field is required");
            return;
        }

        if (crimeType.length() < 3) {
            crimeTypeEditText.setError("Enter your type of crime correctly");
            return;
        }
        if (crimeType.matches(".*\\d.*")) {
            crimeTypeEditText.setError("Crime Type cannot contain numbers");
            return;
        }

        if (TextUtils.isEmpty(dateTime)) {
            dateTimeEditText.setError("Date and Time is required");
            return;
        }

        if (!isValidDateTime(dateTime)) {
            dateTimeEditText.setError("Invalid date and time format. Please enter in YYYY-MM-DD HH:MM format.");
            return;
        }

        if (TextUtils.isEmpty(location)) {
            locationEditText.setError("Location is required");
            return;
        }

        if (location.length() < 3) {
            locationEditText.setError("Characters must be more than 3 characters");
            return;
        }
        if (location.matches(".*\\d.*")) {
            locationEditText.setError("Location cannot contain numbers");
            return;
        }

        if (TextUtils.isEmpty(description)) {
            descriptionEditText.setError("Field is required");
            return;
        }
        if (description.length() < 3) {
            descriptionEditText.setError("Description must be more than 3 characters");
            return;
        }
        if (description.matches(".*\\d.*")) {
            descriptionEditText.setError("Description cannot contain numbers");
            return;
        }

    }

    private boolean isValidDateTime(String dateTime) {
         //You can implement your own validation logic here based on the expected format
         //For example, if you expect YYYY-MM-DD HH:MM format:
         //Regular expression pattern to match YYYY-MM-DD HH:MM format
        String dateTimePattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
        return dateTime.matches(dateTimePattern);
    }
}
