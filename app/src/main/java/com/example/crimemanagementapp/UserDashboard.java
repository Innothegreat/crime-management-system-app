package com.example.crimemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reportCrime(View view) {
        Intent intent = new Intent(UserDashboard.this, CrimeReporting.class);
        startActivity(intent);
    }

    public void viewprofile(View view) {
        Intent intent = new Intent(UserDashboard.this, Profile.class);
        startActivity(intent);
    }

    public void crimereport(View view) {
        Intent intent = new Intent(UserDashboard.this, CrimeList.class);
        startActivity(intent);
    }

    public void viewmap(View view) {
        Intent intent = new Intent(UserDashboard.this, GoogleMap.class);
        startActivity(intent);
    }

    public void logout (View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}