package com.example.sp_check1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class ThirdPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page);
        TextView textView = findViewById(R.id.Save);
        String text = "Save your time..";

        SpannableString spannableString = new SpannableString(text);
        int firstWordEndIndex = text.indexOf(" ");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#746993")), 0, firstWordEndIndex, 0);
        spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), firstWordEndIndex, text.length(), 0);
        textView.setText(spannableString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ThirdPage.this, FourthlyPage.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}