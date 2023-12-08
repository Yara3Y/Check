package com.example.sp_check1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
public class BookingPageFragment extends Fragment {
//    public static final String UserBooking = "message";
  private   DatabaseReference databaseBooking = FirebaseDatabase.getInstance().getReferenceFromUrl("https://checksp-3bcbc-default-rtdb.firebaseio.com/");

    private ImageView qrImageView;
    private Bundle bundle;
    private ViewFlipper viewFlipper;
    private Calendar calendar;
   private String usersBooking=null;
    private View rootView;
    private Button book;
    private TextView tFName,tPhone;
    User user;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         rootView = null;

        TicketStatus ticketStatus = TicketStatus.getInstance(); // create object of TicketStatus class
        rootView = inflater.inflate(R.layout.bookingswitch, container, false); // view flipper page
        viewFlipper = rootView.findViewById(R.id.viewFlipper);
        // check the status then display appropriate page
        if (ticketStatus.getStatusT().equals("reserved")) {
            viewFlipper.setDisplayedChild(1);
            qrImageView = rootView.findViewById(R.id.qrCode);
            tFName = rootView.findViewById(R.id.nameT);
            tPhone =rootView.findViewById(R.id.phoneT);
            TextView hours = rootView.findViewById(R.id.hoursT);
            TextView date = rootView.findViewById(R.id.dateT);
            String bookingId = generateRandomBookingId(); // Generate a unique booking ID for each booking
            String data = "booking:" + bookingId; // Prepend a prefix to the booking ID



            databaseBooking.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    usersBooking=snapshot.child("Current User").getValue(String.class);

                    String FnameUser = snapshot.child(usersBooking).child("Firstname").getValue(String.class);
                    String LastnameUser = snapshot.child(usersBooking).child("Lastname").getValue(String.class);
                    String fullName=FnameUser+" "+LastnameUser;
                    String phoneUser = snapshot.child(usersBooking).child("Phone").getValue(String.class);
                    tFName.setText(fullName);
                    tPhone.setText(phoneUser);
                    databaseBooking.child("users").child(usersBooking).child("Reserve").setValue("Yes");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            calendar= Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            String formattedTime = String.format("%02d:%02d", hour, minute);
            hours.setText(formattedTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/ MM/ yyyy");
            String formattedDate = dateFormat.format(calendar.getTime());

            date.setText(formattedDate);



            try {
                Bitmap qrBitmap = generateQRCode(data);
                qrImageView.setImageBitmap(qrBitmap);
            } catch (WriterException e) {
                e.printStackTrace();}
            // call timer method to start timer
            timer(rootView , viewFlipper);
        }else
        {
            viewFlipper.setDisplayedChild(0);
            book=rootView.findViewById(R.id.book);
            // ticket information (first name , Date, hours, phone)
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(requireContext(), MainPage.class);
                    startActivity(intent);
                }
            });
            WebView webView = rootView.findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript if required

             // Load the GIF image from the assets folder
            webView.loadUrl("file:///android_asset/Car driving.gif");
        }

        return rootView;

    }

    public void timer(View v, ViewFlipper flipper) {
        ProgressBar progressBar = v.findViewById(R.id.progressBar);
        TextView timer = v.findViewById(R.id.timer);

        Timer.startTimer(progressBar, timer, flipper);

    }
    private String generateRandomBookingId() {
        // Generate a unique random booking ID using your desired logic
        Random random = new Random();
        int bookingId = random.nextInt(100000); // Generate a random 5-digit booking ID
        return String.format("%05d", bookingId); // Pad the booking ID with leading zeros if necessary
    }
    private Bitmap generateQRCode(String data) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bitmap;

    }



}