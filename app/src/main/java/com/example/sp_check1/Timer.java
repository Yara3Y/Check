package com.example.sp_check1;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class Timer extends FragmentActivity {
    ProgressBar progressBar;
    private static CountDownTimer countDownTimer;
    private View rootView;
    private static Button book;
    private static long timeLeftInMillis = 120000; // 15 minutes in milliseconds
    private static final long countdownInterval = 1000; // 1 second
    private static String booked = "";
    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://checksp-3bcbc-default-rtdb.firebaseio.com/");
    static TicketStatus ticketStatus = TicketStatus.getInstance(); // create object of TicketStatus class

    public static void startTimer(final ProgressBar progressBar, final TextView timer, final ViewFlipper viewFlipper) {

        countDownTimer = new CountDownTimer(timeLeftInMillis, countdownInterval) {
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int progress = (int) ((timeLeftInMillis * 100) / 900000); // Calculate progress percentage

                progressBar.setProgress(progress); // Update the progress bar

                // Calculate minutes and seconds from milliseconds
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                // Update your UI with the remaining time
                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                timer.setText(timeLeftFormatted);

                booked ="Reserve";
                updateSensorStatus();


            }


            public void onFinish() {
                progressBar.setProgress(0);
                // Set progress to 100% when the timer finishes
                // This method will be called when the timer finishes
                timer.setText("Timer finished!");
                viewFlipper.setDisplayedChild(0);
                book=viewFlipper.findViewById(R.id.book);
                // ticket information (first name , Date, hours, phone)
                book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext(); // Get the current context
                        Intent intent = new Intent(context, MainPage.class);
                        context.startActivity(intent);
                    }
                });
                WebView webView = viewFlipper.findViewById(R.id.webView);
                webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript if required

                // Load the GIF image from the assets folder
                webView.loadUrl("file:///assets/Car driving.gif");
                booked ="Not Reserve";
                updateSensorStatus();
                updateUserReserve();
            }
        };

        countDownTimer.start();

    }
    public static void updateSensorStatus(){
        databaseReference.child("Sensor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (ticketStatus.getSensor().equals("Sensor1")) {
                    databaseReference.child("Sensor").child("Sensor1").child("status for Reserve").setValue(booked);

                }
                else {
                    databaseReference.child("Sensor").child("Sensor2").child("status for Reserve").setValue(booked);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void updateUserReserve(){
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String usersBooking=snapshot.child("Current User").getValue(String.class);

                databaseReference.child("users").child(usersBooking).child("Reserve").setValue("No");




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}

    public static void cancelTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

