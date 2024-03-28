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


public class FeedbackReport extends AppCompatActivity {


    private EditText editTextCrimeType;
    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private EditText editTextDateTime;
    private Button buttonSubmit;
    // Firebase Database reference
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_report);

        // Initialize views
        editTextCrimeType = findViewById(R.id.editTextCrimeType);
        editTextDateTime = findViewById(R.id.editTextDateTime);
        editTextName = findViewById(R.id.editTextName);
        editTextPhoneNumber = findViewById(R.id.phonenumber);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("feedbacks");

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
        EditText NameText = findViewById(R.id.editTextName);
        EditText PhoneNumberText = findViewById(R.id.phonenumber);

        // Get user input
        String feedback = crimeTypeEditText.getText().toString();
        String dateTime = dateTimeEditText.getText().toString();
        String name = NameText.getText().toString();
        String phonenumber = PhoneNumberText.getText().toString();

        // Create a Crime object
        Crime crime = new Crime(feedback, dateTime, name, phonenumber);

        // Push crime data to Firebase Database
        mDatabase.push().setValue(crime);

        // Create intent to pass data to Feedback List
        Intent intent = new Intent(this, FeedbackList.class);
        intent.putExtra("Feedback", feedback);
        intent.putExtra("dateTime", dateTime);
        intent.putExtra("name", name);
        intent.putExtra("phonenumber", phonenumber);

        // Start CrimeListActivity
        startActivity(intent);

        // Validate input
        if (feedback.isEmpty() || dateTime.isEmpty() || name.isEmpty() || phonenumber.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            // Here, you can save the crime report to the database or perform further actions
            // For simplicity, we'll display a toast message indicating successful submission
            Toast.makeText(this, "Feedback submitted successfully", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(feedback)) {
            crimeTypeEditText.setError("Password is Required");
            return;
        }
        if (feedback.length() < 3) {
            crimeTypeEditText.setError("Feedback must be more than 3 characters");
            return;
        }
        if (feedback.matches(".*\\d.*")) {
            crimeTypeEditText.setError("Feedback cannot contain numbers");
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

        if (phonenumber.length() <= 10) {
            PhoneNumberText.setError("Enter your number correctly");
            return;
        }
    }
    private boolean isValidDateTime(String dateTime) {
        // You can implement your own validation logic here based on the expected format
        // For example, if you expect YYYY-MM-DD HH:MM format:
        // Regular expression pattern to match YYYY-MM-DD HH:MM format
        String dateTimePattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
        return dateTime.matches(dateTimePattern);
    }
}