package com.example.sp_check1;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Database_STA {
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://checksp-3bcbc-default-rtdb.firebaseio.com/");

    private int hour;
    private int numberParking;
    private String Day;
    private int week;
    float sum=0;
    public Database_STA(int numberOFP , String Day1, int week1, int hour1 ){
        this.Day=Day1;
        this.hour=hour1;
        this.week=week1;

        this.numberParking=numberOFP;

        databaseReference.child("AStatistics").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child("AStatistics").child("Day").child(Day);
                if (hour==7) {
                    databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 1").child("Time").setValue(hour);
                    databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 1").child("Reserve Parkinglot").setValue(numberParking);

                } else {
                    if (hour==8) {
                        databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 2").child("Time").setValue(hour);
                        databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 2").child("Reserve Parkinglot").setValue(numberParking);

                    } else {
                        if (hour==9) {
                            databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 3").child("Time").setValue(hour);
                            databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 3").child("Reserve Parkinglot").setValue(numberParking);

                        } else {
                            if (hour==10) {
                                databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 4").child("Time").setValue(hour);
                                databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 4").child("Reserve Parkinglot").setValue(numberParking);

                            } else {
                                if (hour==11) {
                                    databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 5").child("Time").setValue(hour);
                                    databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 5").child("Reserve Parkinglot").setValue(numberParking);


                                } else {
                                    if (hour==12) {
                                        databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 6").child("Time").setValue(hour);
                                        databaseReference.child("AStatistics").child("Day").child(Day).child("hours").child("info hour 6").child("Reserve Parkinglot").setValue(numberParking);



                                    }
                                }
                            }

                        }

                    }
                }


//                float aveg=averageW(sum);

//                databaseReference.child("AStatistics").child("Week ").child(Day);
                databaseReference.child("AStatistics").child("Week").child("week Num").setValue(week);


//                databaseReference.child("AStatistics").child("Month").child("week").setValue(week);
//                databaseReference.child("AStatistics").child("Month").child(String.valueOf(week)).child("Reserve Parkinglot").setValue(numberParking);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    }


