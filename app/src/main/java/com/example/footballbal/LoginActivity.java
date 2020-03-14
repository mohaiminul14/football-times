package com.example.footballbal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private  Button reg;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();


        email=findViewById(R.id.lgemail);
        password=findViewById(R.id.lgpassword);
        login=findViewById(R.id.lglogin);
        reg=findViewById(R.id.signup);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailtext=email.getText().toString();
                String passtext = password.getText().toString();

                if (!TextUtils.isEmpty(emailtext) && !TextUtils.isEmpty(passtext))
                {

                    firebaseAuth.signInWithEmailAndPassword(emailtext,passtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                if(user.isEmailVerified()) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {Toast.makeText(LoginActivity.this, "not verifyed",Toast.LENGTH_LONG).show();}

                            }

                            else
                            {

                                String a = task.getException().getMessage();

                                Toast.makeText(LoginActivity.this, a,Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                }

            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();


        if (user != null)
        {
            if (user.isEmailVerified()) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }

        else {



        }

    }


}
