package com.example.covid19app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://covid19-app-2e757-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button notreg = findViewById(R.id.notreg);
        final TextInputEditText phone = findViewById(R.id.phone);
        final TextInputEditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final ProgressBar progress = findViewById(R.id.progress);

        notreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });

        progress.setVisibility(View.GONE);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneText = phone.getText().toString();
                final String passwordText = password.getText().toString();

                if (phoneText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your phone number or password", Toast.LENGTH_SHORT).show();

                } else {
                    databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(phoneText)) {
                                final String getPassword = snapshot.child(phoneText).child("password").getValue(String.class);
                                if (getPassword.equals(passwordText)) {
                                    Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_LONG).show();
                                    progress.setVisibility(View.VISIBLE);
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                } /*else {
                                    Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                                    progress.setVisibility(View.VISIBLE);
                                }*/

                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Phone Number or password", Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });
    }
}