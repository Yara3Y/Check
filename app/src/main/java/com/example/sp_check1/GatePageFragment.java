package com.example.sp_check1;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.animation.Animation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class GatePageFragment extends Fragment {
    DatabaseReference databaseReferenceforN = FirebaseDatabase.getInstance().getReferenceFromUrl("https://checksp-3bcbc-default-rtdb.firebaseio.com/");

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_button_page, container, false);

        ImageButton gate1 = view.findViewById(R.id.gate1);
        ImageButton gate2 = view.findViewById(R.id.gate2);
        ImageButton gate3 = view.findViewById(R.id.gate3);
       ImageButton notification=view.findViewById(R.id.notification);
        showDialog_Notificatin();
        gate1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.move);
                gate1.startAnimation(animation);
                showDialog();
            }
        });

        gate2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.move);
                gate2.startAnimation(animation);
                showDialog();
            }
        });

        gate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(requireContext(), R.anim.move);
                gate3.startAnimation(animation);

                Intent intent = new Intent(requireContext(),ParkingLots.class);
                startActivity(intent);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = AnimationUtils.loadAnimation(requireContext(), R.anim.bell);
                notification.startAnimation(anim);
                showDialog_Notificatin();
            }
        });
        return view;
    }

    private void showDialog() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.popupforgate);
        Button closeButton = dialog.findViewById(R.id.OK);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss(); // Close the pop-up window
            }
        });
        dialog.show();

    }
    @SuppressLint("ResourceAsColor")
    private void showDialog_Notificatin() {
        databaseReferenceforN.child("Sensor").addListenerForSingleValueEvent(new ValueEventListener() {

                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                      final String GetStateCar = snapshot.child("Sensor1").child("status").getValue().toString();
                      final String GetStateCar2 = snapshot.child("Sensor2").child("status").getValue().toString();
                        if(GetStateCar.equals("Not available")&&GetStateCar2.equals("Not available")){
                            Dialog dialog = new Dialog(requireContext());
                            dialog.setContentView(R.layout.popupforcar);
                            Button closeButton = dialog.findViewById(R.id.OK);
                            closeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog.dismiss(); // Close the pop-up window
                                }
                            });
                            dialog.show();

                               }
                   }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

             }
        });
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.popupforavaliable);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.popshap));
        Button closeButton = dialog.findViewById(R.id.OK2);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss(); // Close the pop-up window
            }
        });
        dialog.show();

    }


    }
