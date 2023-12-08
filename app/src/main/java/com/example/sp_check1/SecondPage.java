package com.example.sp_check1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class SecondPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        //change color "Text"
        TextView textView = findViewById(R.id.textView);
        String text = "Find Parking Easily!";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), 0);
        int lastWordStartIndex = text.lastIndexOf(" ");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#746993")), lastWordStartIndex, text.length(), 0);
        textView.setText(spannableString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SecondPage.this, ThirdPage.class);
                startActivity(intent);
                finish();
            }
        }, 3000); // 3000 milliseconds = 3 seconds delay
    }
}