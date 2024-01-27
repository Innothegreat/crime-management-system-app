package com.example.crimemanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {

    private EditText emailText;
    private EditText passwordText;
    private Button loginBtn;
    private Button mregbtn;
    private Button userbtn;
    private FirebaseAuth fireAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        fireAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.loginemail);
        passwordText = findViewById(R.id.loginpassword);
        loginBtn = findViewById(R.id.loginbtn);
        mregbtn = findViewById(R.id.loginregbtn);
        userbtn = findViewById(R.id.loginuserbtn);
        progressBar = findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    emailText.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordText.setError("Password is Required");
                    return;
                }

                if (password.length() < 6) {
                    passwordText.setError("Password must be more than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // AUTHENTICATE THE USER

                fireAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminLogin.this, "Logged In Successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), PoliceDashboard.class));
                        }else {
                            Toast.makeText(AdminLogin.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }


        });


        mregbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (getApplicationContext(),Register.class));
            }
        });

        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (getApplicationContext(),Login.class));
            }
        });

    }
}