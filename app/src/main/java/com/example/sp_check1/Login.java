package com.example.sp_check1;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private FirebaseAuth auth;
   private Intent intentt;
    EditText UsernameText, PasswordText;
    Button loginButton;
    TextView signuptText;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://checksp-3bcbc-default-rtdb.firebaseio.com/");
User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameText = (EditText) findViewById(R.id.Username2);
        PasswordText = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.signin_button);
        signuptText = (TextView) findViewById(R.id.signup_button);
        auth = FirebaseAuth.getInstance();

        user=User.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String userNameText2=UsernameText.getText().toString();
                final String passwordText2=PasswordText.getText().toString();


                if(userNameText2.isEmpty()||passwordText2.isEmpty()){
                    Toast.makeText(Login.this,"Please enter your userName OR password",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if username is exist OR not in database
                            if(snapshot.hasChild(userNameText2)){

                                //now get password of user from database and match it with user enter
                                final String getpassword=snapshot.child(userNameText2)
                                        .child("Password").getValue(String.class);
                                if(getpassword.equals(passwordText2)){
                                    Toast.makeText(Login.this,"Successfully logged in ",Toast.LENGTH_SHORT).show();;

                                    //page after login

                                     intentt=new Intent(Login.this, MainPage.class);
//                                    intentt.putExtra(MainPage.User_Name,userNameText2);
//                                    user.setUsername(userNameText2);
                                    databaseReference.child("users").child("Current User").setValue(userNameText2);
                                    startActivity(intentt);



                                }
                                else{
                                    Toast.makeText(Login.this,"Password is NOT correct ",Toast.LENGTH_SHORT).show();;
                                }
                            }
                            else{
                                Toast.makeText(Login.this,"Username is NOT Found ",Toast.LENGTH_SHORT).show();;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }


            }


        });


        signuptText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this, Signup.class);
                startActivity(intent);

            }
        });



    }

    public void setUsernameText(EditText usernameText) {
        UsernameText = usernameText;
    }

    public EditText getUsernameText() {
        return UsernameText;
    }


}