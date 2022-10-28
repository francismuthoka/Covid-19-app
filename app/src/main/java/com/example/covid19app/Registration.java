package com.example.covid19app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://covid19-app-2e757-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final TextInputEditText fullname = findViewById(R.id.fullname);
        final TextInputEditText phone = findViewById(R.id.phone);
        final TextInputEditText email = findViewById(R.id.email);
        final TextInputEditText password = findViewById(R.id.password);
        final TextInputEditText conpassword = findViewById(R.id.conpassword);
        final Button registerBtn = findViewById(R.id.registerBtn);
        final Button log = findViewById(R.id.log);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fullnameText = fullname.getText().toString();
                final String emailText = email.getText().toString();
                final String phoneText = phone.getText().toString();
                final String passwordText = password.getText().toString();
                final String conpasswordText = conpassword.getText().toString();

                if(fullnameText.isEmpty()|| emailText.isEmpty()|| phoneText.isEmpty()||passwordText.isEmpty()||conpasswordText.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please fill all fields fo you to register", Toast.LENGTH_SHORT).show();
                }
                else if(!passwordText.equals(conpasswordText)){
                    Toast.makeText(getApplicationContext(), "passwords are not matching", Toast.LENGTH_SHORT).show();
                }
                else if(phoneText.length() < 10){
                    Toast.makeText(getApplicationContext(), "Your phone number is incomplete", Toast.LENGTH_SHORT).show();

                }
                else if(fullnameText.length() < 5){
                    Toast.makeText(getApplicationContext(), "Provide Your full name kindly", Toast.LENGTH_SHORT).show();

                }
                else{
                    databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneText)){
                                Toast.makeText(getApplicationContext(), "Phone number already registered", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("User").child(phoneText).child("Full Name").setValue(fullnameText);
                                databaseReference.child("User").child(phoneText).child("Email").setValue(emailText);
                                databaseReference.child("User").child(phoneText).child("password").setValue(passwordText);

                                Toast.makeText(getApplicationContext(), "User Registered successfully", Toast.LENGTH_SHORT).show();
                                finish();
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