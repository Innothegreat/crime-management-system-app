package com.example.crimemanagementapp;

import android.content.Intent;
import android.os.Bundle;
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
        Crime crime = new Crime(crimeType, dateTime, location, description, name, phonenumber);

        // Push crime data to Firebase Database
        mDatabase.push().setValue(crime);

        // Create intent to pass data to CrimeList
        Intent intent = new Intent(this, CrimeList.class);
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
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Here, you can save the crime report to the database or perform further actions
            // For simplicity, we'll display a toast message indicating successful submission
            Toast.makeText(this, "Crime report submitted successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
