package com.example.crimemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeedbackList extends AppCompatActivity {


    private ListView listViewCrimes;
    private List<String> crimeDataList1;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);

        listViewCrimes = findViewById(R.id.listViewCrimes);


        // Retrieve data from intent extras
        Intent intent = getIntent();
        String feedback = intent.getStringExtra("Feedback");
        String dateTime = intent.getStringExtra("dateTime");
        String name = intent.getStringExtra("name");
        String phonenumber = intent.getStringExtra("phonenumber");

        // Create a list to hold crime data
        crimeDataList1 = new ArrayList<>();
        crimeDataList1.add("Feedback: " + feedback);
        crimeDataList1.add("Date and Time: " + dateTime);
        crimeDataList1.add("Name: " + name);
        crimeDataList1.add("Phone number: " + phonenumber);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("feedbacks");

        // Add ValueEventListener to retrieve data from Firebase Database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                crimeDataList1.clear(); // Clear previous data
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Crime crime = postSnapshot.getValue(Crime.class);
                    crimeDataList1.add(String.valueOf(crime));
                }
                // Update ListView with new data
                updateListView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
    // Create adapter and set it to ListView
    private void updateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, crimeDataList1);
        listViewCrimes.setAdapter(adapter);
    }


    // Method to parse crime details string into Crime object
    private Crime parseCrimeDetails(String crimeDetails) {
        // Implement logic to parse crime details string into Crime object
        return null;
    }
}