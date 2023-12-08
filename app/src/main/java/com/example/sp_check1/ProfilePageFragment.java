package com.example.sp_check1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePageFragment extends Fragment {
//    public static final String UserN = "message";
    EditText editName, editLName, editEmail, editUsername, editPassword, editPhone;
    Button saveButton;
    ImageButton logout;
    String userNameTextT;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://checksp-3bcbc-default-rtdb.firebaseio.com/");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile_edit, container, false);

        editName = view.findViewById(R.id.editFName);
        editLName = view.findViewById(R.id.editLName);
        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        editPhone = view.findViewById(R.id.editphone);
        saveButton = view.findViewById(R.id.saveButton);
        logout = view.findViewById(R.id.logout);



        reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String userNameTextT = snapshot.child("Current User").getValue(String.class);
                String FnameUser = snapshot.child(userNameTextT).child("Firstname").getValue(String.class);
                editName.setText(FnameUser);

                String LastnameUser = snapshot.child(userNameTextT).child("Lastname").getValue(String.class);
                String emailUser = snapshot.child(userNameTextT).child("Email").getValue(String.class);
                String phoneUser = snapshot.child(userNameTextT).child("Phone").getValue(String.class);
                String passwordUser = snapshot.child(userNameTextT).child("Password").getValue(String.class);

                editLName.setText(LastnameUser);
                editEmail.setText(emailUser);
                editPhone.setText(phoneUser);
                editPassword.setText(passwordUser);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        reference.child("users").child(userNameTextT).child("Firstname").setValue(editName.getText().toString());
                        reference.child("users").child(userNameTextT).child("Lastname").setValue(editLName.getText().toString());
                        reference.child("users").child(userNameTextT).child("Email").setValue(editEmail.getText().toString());
                        reference.child("users").child(userNameTextT).child("Phone").setValue(editPhone.getText().toString());
                        reference.child("users").child(userNameTextT).child("Password").setValue(editPassword.getText().toString());
                        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show();


                    }

                });
                String FnameUser2 = snapshot.child(userNameTextT).child("Firstname").getValue(String.class);

                editName.setText(FnameUser2);

                String LastnameUser2 = snapshot.child(userNameTextT).child("Lastname").getValue(String.class);
                String emailUser2 = snapshot.child(userNameTextT).child("Email").getValue(String.class);
                String phoneUser2 = snapshot.child(userNameTextT).child("Phone").getValue(String.class);
                String passwordUser2 = snapshot.child(userNameTextT).child("Password").getValue(String.class);

                editLName.setText(LastnameUser2);
                editEmail.setText(emailUser2);
                editPhone.setText(phoneUser2);
                editPassword.setText(passwordUser2);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                // Finish the current activity (ProfilePageFragment)
                getActivity().finish();
            }
        });

        return view;
    }
}