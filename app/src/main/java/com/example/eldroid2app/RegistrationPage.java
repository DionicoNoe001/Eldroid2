package com.example.eldroid2app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationPage extends AppCompatActivity {
    final String TAG = "FIREBASE";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        db = FirebaseFirestore.getInstance();

        EditText UsernameTextBox, PasswordTextBox;

        Button AddBtn = findViewById(R.id.AddBtn);

        UsernameTextBox = findViewById(R.id.UsernameTextBox);
        PasswordTextBox = findViewById(R.id.PasswordTextBox);

        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = UsernameTextBox.getText().toString();
                String passwordInput = PasswordTextBox.getText().toString();

                if(!usernameInput.isEmpty() && !passwordInput.isEmpty())
                {
                    addUser(usernameInput,passwordInput);
                }
                else
                {
                    Toast.makeText(RegistrationPage.this,"Please make sure there are no empty fields", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void addUser(String uname, String password)
    {

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Username", uname);
        user.put("Password", password);

        // Add a new document with a generated ID
        db.collection("Users").document(uname)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + uname);
                        Toast.makeText(RegistrationPage.this, "Successfully Added " + uname, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistrationPage.this, "Error adding user " + e, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Error adding document", e);
                    }
                });
    }
}