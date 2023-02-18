package com.example.eldroid2app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPage extends AppCompatActivity {
    final String TAG = "FIREBASE";
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        db = FirebaseFirestore.getInstance();

        EditText LoginUsernameTextBox, LoginPasswordTextBox;

        Button LoginBtn = findViewById(R.id.LoginBtn);
        Button SignUpBtn = findViewById(R.id.signUpBtn);

        LoginUsernameTextBox = findViewById(R.id.LoginUsernameTextBox);
        LoginPasswordTextBox = findViewById(R.id.LoginPasswordTextBox);

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities();
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uname = LoginUsernameTextBox.getText().toString();
                String password = LoginPasswordTextBox.getText().toString();

                db.collection("Users").document(uname).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String queriedUsername = documentSnapshot.getString("Username");
                        String queriedPassword = documentSnapshot.getString("Password");

                        if (uname.equals(queriedUsername) && password.equals(queriedPassword)){
                            switchToDashboard();
                        }else {
                            Toast.makeText(LoginPage.this,"User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Firestore", "Error reading data from Firestore: " + e.getMessage());
                    }
                });

            }
        });
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, RegistrationPage.class);
        startActivity(switchActivityIntent);
    }

    private void switchToDashboard() {
        Intent switchToDashboardIntent = new Intent(this, MainActivity.class);
        startActivity(switchToDashboardIntent);
    }

//    public void confirmUser(String uname, String password){
//        db.collection("Users").document(uname).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                String queriedUsername = documentSnapshot.getString("Username");
//                String queriedPassword = documentSnapshot.getString("Password");
//
//                if (queriedUsername == null){
//                    Toast.makeText(MainActivity.this,"User does not exist", Toast.LENGTH_SHORT).show();
//                }else {
//
//                }
//            }
//        })
//    }

}