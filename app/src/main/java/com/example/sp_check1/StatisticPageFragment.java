package com.example.sp_check1;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class StatisticPageFragment extends Fragment implements View.OnClickListener {
    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://checksp-3bcbc-default-rtdb.firebaseio.com/");
    XAxis axis;
    BarDataSet barDataSet;
    BarChart barChart;
 private Calendar calendar;
    String[] times = new String[]{"", "7AM", "8AM ", "9AM", "10AM", "11AM", "12PM", "1PM"};
    String[] day = new String[]{"","Sun", "Mon", "Thue", "Wed", "Thu"};
    String[] week = new String[]{" ", " week1 " , " week2 " , " week3 " , " week4 "};
    int []parkinglot=new int[]{0,1,2,3,4};
    String DayName;
    float averageParking;
    ArrayList<BarEntry> barlistX;
    ImageButton buttonDay;
    ImageButton buttonWeek;
    ImageButton buttonMonth;
    TextView X_AxisText;
    TextView Y_AxisText;
//    int count = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistic_page, container, false);

        buttonDay = view.findViewById(R.id.Day);
        buttonWeek = view.findViewById(R.id.Week);
        buttonMonth = view.findViewById(R.id.Month);
        barChart = view.findViewById(R.id.DAYBarChart);
        X_AxisText=view.findViewById(R.id.X_AxisText);
        Y_AxisText=view.findViewById(R.id.Y_AxisText);
        barChart.getAxisRight().setDrawLabels(false);
        getDataDAY();
        buttonDay.setBackgroundResource(R.drawable.btns_change);
        buttonDay.setOnClickListener(this);
        buttonWeek.setOnClickListener(this);
        buttonMonth.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Day) {
            getDataDAY();
            buttonWeek.setBackgroundResource(R.drawable.btns);
            buttonMonth.setBackgroundResource(R.drawable.btns);
            buttonDay.setBackgroundResource(R.drawable.btns_change);

        } else if (v.getId() == R.id.Week) {
            getDataWEEK();
            buttonDay.setBackgroundResource(R.drawable.btns);
            buttonMonth.setBackgroundResource(R.drawable.btns);
            buttonWeek.setBackgroundResource(R.drawable.btns_change);



        } else if (v.getId() == R.id.Month) {
            getDataMONTH();
            buttonDay.setBackgroundResource(R.drawable.btns);
            buttonWeek.setBackgroundResource(R.drawable.btns);
            buttonMonth.setBackgroundResource(R.drawable.btns_change);



        }


    }

    private void getDataDAY() {
//       we make for loop to matching

        ArrayList<Integer> X_realTime = new ArrayList<>();
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        ArrayList<Integer> y_realTime = new ArrayList<>();

        databaseReference1.child("AStatistics").addListenerForSingleValueEvent(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 calendar=Calendar.getInstance();
                //check if username is exist OR not in database
                String today=android.text.format.DateFormat.format("EEEE",calendar).toString();
                String NameDay=today;
//                Toast.makeText(requireContext(),NameDay , Toast.LENGTH_LONG).show();
                X_AxisText.setText(NameDay);
                //change
                Y_AxisText.setText("Number of parking occupied");

                 final int hour1 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 1").child("Time").getValue(Integer.class);

                  final int hour2 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 2").child("Time").getValue(Integer.class);

                final int hourOF3 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 3").child("Time").getValue(Integer.class);
                final int hourOF4 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 4").child("Time").getValue(Integer.class);
                final int hourOF5 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 5").child("Time").getValue(Integer.class);
                final int hourOF6 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 6").child("Time").getValue(Integer.class);

                     X_realTime.add(hour1);
                     X_realTime.add(hour2);
                   X_realTime.add(hourOF3);
                   X_realTime.add(hourOF4);
                   X_realTime.add(hourOF5);
                   X_realTime.add(hourOF6);

                final int NumOfParking1 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 1").child("Reserve Parkinglot").getValue(Integer.class);
                final int NumOfParking2 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 2").child("Reserve Parkinglot").getValue(Integer.class);
//                Toast.makeText(requireContext(),String.valueOf(NumOfParking1) , Toast.LENGTH_LONG).show();

                final int NumOfParking3 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 3").child("Reserve Parkinglot").getValue(Integer.class);
                final int NumOfParking4 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 4").child("Reserve Parkinglot").getValue(Integer.class);
//
                final int NumOfParking5 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 5").child("Reserve Parkinglot").getValue(Integer.class);
                final int NumOfParking6 = snapshot.child("Day").child(NameDay).child("hours").child("info hour 6").child("Reserve Parkinglot").getValue(Integer.class);
                averageParking=NumOfParking1+NumOfParking2+NumOfParking3+NumOfParking4+NumOfParking5+NumOfParking6;
                databaseReference1.child("AStatistics").child("Week").child(NameDay).child("Reserve Parkinglot").setValue(averageParking);

                y_realTime.add(NumOfParking1);
                y_realTime.add(NumOfParking2);
                y_realTime.add(NumOfParking3);
                y_realTime.add(NumOfParking4);
                y_realTime.add(NumOfParking5);
                y_realTime.add(NumOfParking6);
//                Toast.makeText(requireContext(),String.valueOf(NumOfParking) , Toast.LENGTH_LONG).show();
                //switch case each hour
for(int count=0;count< X_realTime.size();++count) {
    if (X_realTime.get(count).equals(7)) {
        x.add(1);
    } else {
        if (X_realTime.get(count).equals(8)) {
            x.add(2);
        } else {
            if (X_realTime.get(count).equals(9)) {
                x.add(3);
            } else {
                if (X_realTime.get(count).equals(10)) {
                    x.add(4);
                } else {
                    if (X_realTime.get(count).equals(11)) {
                        x.add(5);
                    } else {
                        if (X_realTime.get(count).equals(12)) {
                            x.add(6);
                        } else {
                            x.add(7);
                        }
                    }
                }

            }

        }
    }
    //for y
    if (y_realTime.get(count).equals(1)) {
        y.add(1);
    } else {
        if (y_realTime.get(count).equals(2)) {
            y.add(2);
        }

    }
}
                barlistX = new ArrayList<>();

                barchart(x, y);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        axis = barChart.getXAxis();
        axis.setValueFormatter(new IndexAxisValueFormatter(times));


        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setGranularity(1);
        Description XAxisDescription = new Description();
        XAxisDescription.setText(DayName);
        barChart.setDescription(XAxisDescription);
        axis.setGranularityEnabled(true);

    }
    private void getDataWEEK() {


        ArrayList<Integer> X_realTime = new ArrayList<>();
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        ArrayList<Integer> y_realTime = new ArrayList<>();
        databaseReference1.child("AStatistics").addListenerForSingleValueEvent(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //check if username is exist OR not in database
//
                int parkingForMonth=0;
                String []Days={"Sunday","Monday","Tuesday","Wednesday","Thursday"};
                int getNumWeek=snapshot.child("Week").child("week Num").getValue(Integer.class);
//                final String NameDay = snapshot.child("Week").child(Days[3]).getValue().toString();
//                X_realTime.add(NameDay);
              for(int i=0;i<Days.length;++i) {
//                  final int NumOfParking = snapshot.child("Week").child(Days[i]).child("Reserve Parkinglot").getValue(Integer.class);
//
                  DataSnapshot weekSnapshot = snapshot.child("Week");
                  if (weekSnapshot.exists() && weekSnapshot.child(Days[i]).exists()) {
                      DataSnapshot daySnapshot = weekSnapshot.child(Days[i]);
                      Integer numOfParking = daySnapshot.child("Reserve Parkinglot").getValue(Integer.class);
                       y_realTime.add(numOfParking);
                  }


                      //switch case each hour
                  X_AxisText.setText("Day");
                  Y_AxisText.setText("Average of Parking Reserving");

                  if (Days[i].equals("Sunday")) {
                      x.add(1);
                  } else {
                      if (Days[i].equals("Monday")) {
                          x.add(2);
                      } else {
                          if (Days[i].equals("Tuesday")) {
                              x.add(3);
                          } else {
                              if (Days[i].equals("Wednesday")) {
                                  x.add(4);
                              } else {
                                  if (Days[i].equals("Thursday")) {
                                      x.add(5);
                                  }
                              }

                          }
                      }
                  }
//                      7/5
                  
                 float aveg=y_realTime.get(i)/5;

                 int approximateAverage = (int) Math.round(aveg);
                  y.add(approximateAverage);
                  parkingForMonth=+y.get(i);



              }
                Toast.makeText(requireContext(),String.valueOf(parkingForMonth) , Toast.LENGTH_LONG).show();
                barlistX = new ArrayList<>();

                barchart(x,y);

                databaseReference1.child("AStatistics").child("Month").child(String.valueOf(getNumWeek)).child("Reserve Parkinglot").setValue(parkingForMonth);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        axis = barChart.getXAxis();
        axis.setValueFormatter(new IndexAxisValueFormatter(day));


        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setGranularity(1);
        Description XAxisDescription = new Description();
        XAxisDescription.setText(DayName);
        barChart.setDescription(XAxisDescription);
        axis.setGranularityEnabled(true);



    }
    private void getDataMONTH() {

        ArrayList<String> X_realTime = new ArrayList<>();
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        ArrayList<Integer> y_realTime = new ArrayList<>();
        databaseReference1.child("AStatistics").addListenerForSingleValueEvent(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //check if username is exist OR not in database
//

                final int NumOfParking1 = snapshot.child("Month").child("1").child("Reserve Parkinglot").getValue(Integer.class);
                y_realTime.add(NumOfParking1);
                Toast.makeText(requireContext(),String.valueOf(NumOfParking1) , Toast.LENGTH_LONG).show();

                final int NumOfParking2 = snapshot.child("Month").child("2").child("Reserve Parkinglot").getValue(Integer.class);
                y_realTime.add(NumOfParking2);
                final int NumOfParking3 = snapshot.child("Month").child("3").child("Reserve Parkinglot").getValue(Integer.class);
                y_realTime.add(NumOfParking3);
                final int NumOfParking4 = snapshot.child("Month").child("4").child("Reserve Parkinglot").getValue(Integer.class);
                y_realTime.add(NumOfParking4);
//                Toast.makeText(requireContext(),String.valueOf(NumOfParking3) , Toast.LENGTH_LONG).show();

                //switch case each hour
                X_AxisText.setText("Week");
                x.add(1);
                x.add(2);
                x.add(3);
                x.add(4);


//                7/5
  for (int i=0;i<x.size();++i) {
    float aveg = y_realTime.get(i)/ 5;
    int approximateAverage = (int) Math.round(aveg);
    y.add(approximateAverage);
}
//

                barlistX = new ArrayList<>();

                barchart(x,y);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        axis = barChart.getXAxis();
        axis.setValueFormatter(new IndexAxisValueFormatter(week));



        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setGranularity(1);
        Description XAxisDescription = new Description();
        XAxisDescription.setText(DayName);
        barChart.setDescription(XAxisDescription);
        axis.setGranularityEnabled(true);


    }

    public void barchart(ArrayList<Integer> x,ArrayList<Integer> y ){

        for (int i = 0; i < x.size(); ++i) {
            barlistX.add(new BarEntry(x.get(i), y.get(i)));
        }

        BarDataSet barDataSet = new BarDataSet(barlistX, "");
        barDataSet.setDrawIcons(false);

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        Legend legend = barChart.getLegend();
        legend.setExtra(new int[] {Color.TRANSPARENT}, new String[] {""}); // Hide the legend entry for the dataset

        barChart.invalidate();

//        barDataSet=new BarDataSet(barlistX," ");
//        barDataSet.setDrawIcons(false);
//        BarData barData=new BarData(barDataSet);
//        //try
//        barChart.invalidate();

        barChart.setData(barData);

        barDataSet.setColor(R.color.dark_purple);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(12f);
        barDataSet.setBarBorderWidth(1f);
        barDataSet.setBarBorderColor(R.color.dark_purple);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(7);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getDescription().setText("Statistics");
        barChart.animateY(2000);
        barChart.getDescription().setEnabled(true);

    }
}