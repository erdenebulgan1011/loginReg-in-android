package com.example.lab9;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class Login extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReferenceFromUrl("https://lab99-63b7d-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText mobile = findViewById(R.id.mobile);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final TextView registerNowBtn= findViewById(R.id.registerNowBtn);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String phoneTxt = mobile.getText().toString();
                final String passwordTxt=password.getText().toString();
                if(phoneTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(Login.this, "Please enter your mobile and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phoneTxt)){
                                final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);
                                if(getPassword.equals(passwordTxt)){
                                    Toast.makeText(Login.this, "Success fully login in", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    intent.putExtra("phoneTxt", phoneTxt);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(Login.this, "Wrongpassword", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Login.this, "Wrong password",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}});}}});
        registerNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });}}