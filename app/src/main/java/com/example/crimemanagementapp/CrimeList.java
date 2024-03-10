package com.example.crimemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CrimeList extends AppCompatActivity {

    private ListView listViewCrimes;
    private List<String> crimeDataList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_list);

        listViewCrimes = findViewById(R.id.listViewCrimes);

        // Retrieve data from intent extras
        Intent intent = getIntent();
        String crimeType = intent.getStringExtra("crimeType");
        String dateTime = intent.getStringExtra("dateTime");
        String location = intent.getStringExtra("location");
        String description = intent.getStringExtra("description");
        String name = intent.getStringExtra("name");
        String phonenumber = intent.getStringExtra("phonenumber");

        // Create a list to hold crime data
        crimeDataList = new ArrayList<>();
        crimeDataList.add("Crime Type: " + crimeType);
        crimeDataList.add("Date and Time: " + dateTime);
        crimeDataList.add("Location: " + location);
        crimeDataList.add("Description: " + description);
        crimeDataList.add("Name: " + name);
        crimeDataList.add("Phone number: " + phonenumber);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("crimes");

        // Add ValueEventListener to retrieve data from Firebase Database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                crimeDataList.clear(); // Clear previous data
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Crime crime = postSnapshot.getValue(Crime.class);
                    crimeDataList.add(String.valueOf(crime));
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, crimeDataList);
        listViewCrimes.setAdapter(adapter);

        listViewCrimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String crimeDetails = crimeDataList.get(position);
                Crime crime = parseCrimeDetails(crimeDetails);
                if (crime != null) {
                    deleteCrimeFromDatabase(crime);
                }
            }
        });
    }


    // Method to parse crime details string into Crime object
    private Crime parseCrimeDetails(String crimeDetails) {
        // Implement logic to parse crime details string into Crime object
        return null;
    }

    // Method to handle delete button click (defined in list item layout)
    public void onDeleteClicked(View view) {
        // Handle delete button click
        // Extract crime details from list item and delete corresponding crime from Firebase database
        View parent = (View) view.getParent();
        ListView listViewCrimes = parent.findViewById(R.id.listViewCrimes);
        String crimeDetails = listViewCrimes.getTextFilter().toString();
        Crime crime = parseCrimeDetails(crimeDetails);
        if (crime != null) {
            // Delete crime from Firebase database
            deleteCrimeFromDatabase(crime);
        }
    }

    private void deleteCrimeFromDatabase(Crime crime) {
            DatabaseReference crimeRef = FirebaseDatabase.getInstance().getReference().child("crimes").child(crime.getId());
            crimeRef.removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(CrimeList.this, "Crime deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("CrimeList" , "Error deleting crime: " + e.getMessage());
                            Toast.makeText(CrimeList.this, "Error deleting crime" , Toast.LENGTH_SHORT).show();
                        }
                    });
    }
}

