package com.example.crimemanagementapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText newpasswordText;
    EditText newemailText;
    EditText regmobile;
    EditText regname;
    EditText regcity;
    EditText regcountry;
    EditText postalAddress;
    Button regbtn;
    Button mLoginbtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        newemailText = findViewById(R.id.regemail);
        newpasswordText = findViewById(R.id.regpass);
        regname =  findViewById(R.id.regName);
        regmobile =  findViewById(R.id.regMobile);
        regcity =  findViewById(R.id.regCity);
        regcountry =  findViewById(R.id.regCountry);
        postalAddress =  findViewById(R.id.regPostal);
        progressBar = findViewById(R.id.progressBar);
        regbtn = findViewById(R.id.regbutton);
        mLoginbtn = findViewById(R.id.mLoginbtn);
        fStore = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), UserDashboard.class));
            finish();
        }

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = newemailText.getText().toString().trim();
                String password = newpasswordText.getText().toString().trim();
                String name = regname.getText().toString();
                String mobile = regmobile.getText().toString();
                String city = regcity.getText().toString();
                String country = regcountry.getText().toString();
                String address = postalAddress.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    newemailText.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    newpasswordText.setError("Password is Required");
                    return;
                }

                if (password.length() < 6) {
                    newpasswordText.setError("Password must be more than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register the user in Firebase

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Name",name);
                            user.put("email", email);
                            user.put("mobile", mobile);
                            user.put("city", city);
                            user.put("country", country);
                            user.put("address", address);
                            documentReference.set(user).addOnSuccessListener((OnSuccessListener) (aVoid) -> {
                                Log.d(TAG, "onSuccess: user profile is created for " + userID);
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+ e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), UserDashboard.class));

                        }else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent (getApplicationContext(),Login.class));
    }
});

    }
}