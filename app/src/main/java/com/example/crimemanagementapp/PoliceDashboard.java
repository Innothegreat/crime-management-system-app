package com.example.crimemanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    public void logout (View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}