package com.example.crimemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class PoliceDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_dashboard);
    }
    public void crimereports(View view) {
        Intent intent = new Intent(PoliceDashboard.this, CrimeList.class);
        startActivity(intent);
    }
    public void viewprofile(View view) {
        Intent intent = new Intent(PoliceDashboard.this, Profile.class);
        startActivity(intent);
    }
    public void viewmap(View view) {
        Intent intent = new Intent(PoliceDashboard.this, GoogleMap.class);
        startActivity(intent);
    }
    public void logout (View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}