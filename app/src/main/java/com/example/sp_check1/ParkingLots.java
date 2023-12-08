package com.example.sp_check1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;


import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParkingLots extends FragmentActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://checksp-3bcbc-default-rtdb.firebaseio.com/");

    private static final String email = "yalharbi172@gmail.com";
    private static final String password = "yara1234";
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private String SensorID = "";
    int count_number_parking = 2;
    int parkingisNOTV = 0;
    Database_STA SendDatabase;

    Button state1;
    Button state2;
    Button state3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lots1);
        mAuth = FirebaseAuth.getInstance();

        state1 = findViewById(R.id.state1);
        state2 = findViewById(R.id.state2);
        state3 = findViewById(R.id.state3);
        ImageButton Back = findViewById(R.id.backButton);



        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(ParkingLots.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        //Handler
        Handler handler = new Handler();

// Define a Runnable that contains the code you want to execute
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                databaseReference.child("Sensor").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("Sensor").child("Sensor1").child("status for Reserve").setValue("Not Reserve");
                        databaseReference.child("Sensor").child("Sensor2").child("status for Reserve").setValue("Not Reserve");

                        final String GetStateCar = snapshot.child("Sensor1").child("status").getValue().toString();
                        final String GetStateRESVE = snapshot.child("Sensor1").child("status for Reserve").getValue().toString();

                        final String day = snapshot.child("Sensor1").child("Time").child("dayName").getValue(String.class);
                        final int hour = snapshot.child("Sensor1").child("Time").child("hour").getValue(Integer.class);
                        final int week = snapshot.child("Sensor1").child("Time").child("week").getValue(Integer.class);
                        if (parkingisNOTV >= 2) {
                            parkingisNOTV = 0;
                            Toast.makeText(ParkingLots.this, "333333.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        if (snapshot.child("Sensor1").hasChild("Time")) {
                            Toast.makeText(ParkingLots.this, day,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ParkingLots.this, " no child.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (GetStateCar.equals("Not available")) {
                            state1.setBackgroundResource(R.drawable.btn_shape2);
                            parkingisNOTV++;
                            count_number_parking = -parkingisNOTV;

                            SendDatabase = new Database_STA(parkingisNOTV, day, week, hour);

                        } else if (GetStateCar.equals("Available")) {
                            state1.setBackgroundResource(R.drawable.btn_shape);

                            count_number_parking = +parkingisNOTV;
                            parkingisNOTV--;
                            SendDatabase = new Database_STA(parkingisNOTV, day, week, hour);
                            if (GetStateRESVE.equals("Reserve")) {
                                state1.setBackgroundResource(R.drawable.btn_shape3);


                            }
                            else {
                                state1.setBackgroundResource(R.drawable.btn_shape);

                            }
                        }

                        final String GetStateCar2 = snapshot.child("Sensor2").child("status").getValue().toString();
                        final String GetStateRESVE2 = snapshot.child("Sensor2").child("status for Reserve").getValue().toString();
                        final String day2 = snapshot.child("Sensor2").child("Time").child("dayName").getValue().toString();
                        final int hour2 = snapshot.child("Sensor2").child("Time").child("hour").getValue(Integer.class);
                        final int week2 = snapshot.child("Sensor2").child("Time").child("week").getValue(Integer.class);

//                        if the hour not the same value hour2
                        if (hour != hour2) {
                            parkingisNOTV = 0;
                            Toast.makeText(ParkingLots.this, "hee", Toast.LENGTH_LONG).show();

                        }
                        if (GetStateCar2.equals("Not available")) {
                            state2.setBackgroundResource(R.drawable.btn_shape2);

                            count_number_parking = count_number_parking - parkingisNOTV;

                            parkingisNOTV++;
                            SendDatabase = new Database_STA(parkingisNOTV, day2, week2, hour2);
                        } else if (GetStateCar2.equals("Available")) {
                            state2.setBackgroundResource(R.drawable.btn_shape);
                            count_number_parking = +parkingisNOTV;
                            parkingisNOTV--;
                            SendDatabase = new Database_STA(parkingisNOTV, day2, week2, hour2);
                            if (GetStateRESVE2.equals("Reserve"))
                            {
                                state2.setBackgroundResource(R.drawable.btn_shape3);

                            }
                            else {
                                state2.setBackgroundResource(R.drawable.btn_shape);

                            }
                        }

//                        DatabaseUpdate(GetStateCar,GetStateCar2);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//this for old data is deleted
                parkingisNOTV = 0;
            }

        };handler.postDelayed(runnable, 1000);



        // نفترض اخذ اللون من الداتا بيز
//        state1.setBackgroundResource(R.drawable.btn_shape2);

        state1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Drawable spotColor = state1.getBackground();

                // get the color of parking spot
                if (spotColor.getConstantState().equals(state1.getContext().getResources().getDrawable(R.drawable.btn_shape2).getConstantState())) {
                    showNotAvaPop();  // add button yellow
                    //state1.setVisibility(View.GONE);

                }
                if (spotColor.getConstantState().equals(state1.getContext().getResources().getDrawable(R.drawable.btn_shape).getConstantState())) {
                    Toast.makeText(state1.getContext(), "Button shape is available", Toast.LENGTH_SHORT).show();
                    SensorID ="Sensor1";
                    checkReserve();

                }
            }
        });

//        State2
        state2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                state2.setBackgroundResource(R.drawable.btn_shape2);

                Drawable spotColor = state2.getBackground();

                // get the color of parking spot
                if (spotColor.getConstantState().equals(state2.getContext().getResources().getDrawable(R.drawable.btn_shape2).getConstantState())) {
                    showNotAvaPop();  // add button yellow
                    //state1.setVisibility(View.GONE);

                }
                if (spotColor.getConstantState().equals(state2.getContext().getResources().getDrawable(R.drawable.btn_shape).getConstantState())) {
                    SensorID ="Sensor2";
                    checkReserve();




                }
            }
        });

//        to back the home page
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParkingLots.this, MainPage.class);
                startActivity(intent);

            }
        });
    }
public void DatabaseUpdate(String stateCAr1,String stateCAr2){

}

    public void hidePage(){
        ViewGroup layout = findViewById(R.id.parkingLots); // Replace "yourLayout" with the ID of your parent layout

        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof Button) {
                child.setVisibility(View.GONE);
            }
        }
    }


    private void showNotAvaPop() {
        //أتأكد من الارقيومنت
        Dialog dialog = new Dialog(ParkingLots.this);
        dialog.setContentView(R.layout.popupnotava);
        Button closeButton = dialog.findViewById(R.id.btnOK);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss(); // Close the pop-up window
            }
        });
        dialog.show();
    }


    private void showAvaPop() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popupava);

        // button working ..
        Button cancelButton = dialog.findViewById(R.id.closepop);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // only for test the button color
//                state1.setBackgroundResource(R.drawable.btn_shape3);
                dialog.dismiss(); // Close the pop-up window
            }
        });
        Button yesButton = dialog.findViewById(R.id.btnYes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hidePage();
                TicketStatus ticketStatus = TicketStatus.getInstance();
                ticketStatus.setStatusT("reserved");
                ticketStatus.setSensor(SensorID);
                Intent intent = new Intent(getApplicationContext(), MainPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                Toast.makeText(state1.getContext(), "You can show your ticket in booking page!", Toast.LENGTH_LONG).show();
                dialog.dismiss();

                hidePage();
            }
        });
        dialog.show();


    }
    private void showDialog() {
        Toast toast = Toast.makeText(ParkingLots.this, "You have already booking !!", Toast.LENGTH_SHORT);
//        View toastView = toast.getView();
//        TextView toastMessage = toastView.findViewById(android.R.id.message);
//        toastMessage.setTextSize(18);
//        toastMessage.setTextColor(Color.WHITE);
//        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross2, 0, 0, 0);
//        toastMessage.setGravity(Gravity.CENTER);
//        toastMessage.setCompoundDrawablePadding(16);
//        toastView.setBackgroundResource(R.drawable.toast);
//        toast.setGravity(Gravity.TOP,50,160);
        toast.show();
    }
    public void checkReserve(){
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user =snapshot.child("Current User").getValue().toString();
                String reserve=snapshot.child(user).child("Reserve").getValue().toString();
                if(reserve.equals("Yes"))
                {
                    showDialog();
                }
                else{
                    showAvaPop();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}


