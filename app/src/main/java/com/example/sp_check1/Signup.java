package com.example.sp_check1;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    EditText FirsName, LastName, signupUsername, signupEmail, signupPassword, phone;
    TextView SigninText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirsName = findViewById(R.id.F_Name);
        LastName = findViewById(R.id.L_Name);
        signupUsername = findViewById(R.id.Username);
        signupEmail = findViewById(R.id.email);
        signupPassword = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        SigninText = findViewById(R.id.signin);

        database = FirebaseDatabase.getInstance();
        reference = database.getReferenceFromUrl("https://checksp-3bcbc-default-rtdb.firebaseio.com/");
        signupButton = findViewById(R.id.signUp);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = FirsName.getText().toString();
                String lastName = LastName.getText().toString();
                String username = signupUsername.getText().toString();
                String email = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();
                String phoneNumber = phone.getText().toString();

                if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() ||
                        password.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(Signup.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password)) {
                    Toast.makeText(Signup.this, "Password must be at least 8 characters long and contain " +
                            "\n at least one uppercase letter" +
                            "\n one lowercase letter " +
                            "\n one digit, " +
                            "\n and one special character", Toast.LENGTH_SHORT).show();
                } else {
                    reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                Toast.makeText(Signup.this, "Username is already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference userRef = reference.child("users").child(username);
                                userRef.child("Firstname").setValue(firstName);
                                userRef.child("Lastname").setValue(lastName);
                                userRef.child("Password").setValue(password);
                                userRef.child("Email").setValue(email);
                                userRef.child("Phone").setValue(phoneNumber);

                                Toast.makeText(Signup.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Signup.this, MainPage.class);
                                startActivity(intent);
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

        SigninText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#^&*_\\-?/]).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
