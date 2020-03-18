package com.example.footballbal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {

    private EditText email;
    private Button submit;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        // indentify
        email = findViewById(R.id.forText);
        submit = findViewById(R.id.submit);
        auth = FirebaseAuth.getInstance();


        //click korle password reset er mail jabe

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String femail=email.getText().toString();//email toString a convert korsi

                //password reset er email pathanor option
                auth.sendPasswordResetEmail(femail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ForgetActivity.this, "password vule gesi", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            Toast.makeText(ForgetActivity.this, "error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });



    }
}
