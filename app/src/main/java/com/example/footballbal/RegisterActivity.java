package com.example.footballbal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText remail;
    private EditText rpassword;
    private EditText rcpassword;
    private Button rreg;
    private Button rlogin;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user=mAuth.getCurrentUser();

        remail=findViewById(R.id.rgemail);
        rpassword=findViewById(R.id.rgpass);
        rcpassword=findViewById(R.id.rgcpass);
        rreg=findViewById(R.id.rereg);
        rlogin=findViewById(R.id.rglog);

    rlogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    });

        rreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registercheck();
            }
        });
}


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null)
        {
            if (user.isEmailVerified()) {

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }

        }
    }

    private void registercheck() {
        String email = remail.getText().toString();
        String pass = rpassword.getText().toString().trim();
        String cpass = rcpassword.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty()) {
            remail.setError("Email is required");
            remail.requestFocus();
            return;
        } else {

            if (email.matches(emailPattern))
            {
                if (pass.isEmpty())
                {
                    rpassword.setError("password is required");
                    rpassword.requestFocus();
                    return;
                }
                else
                    {

                    if (cpass.isEmpty()) {
                        rcpassword.setError("Confirm password is required");
                        rcpassword.requestFocus();
                        return;
                    }
                    else {
                        if (pass.length() < 6) {
                            rpassword.setError("minumum 6 word password");
                            rpassword.requestFocus();
                            return;
                        }
                        else
                            {

                            if (pass.equals(cpass)) {

                                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            //register er por ki korte cai

                                            //email verification
                                            mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        //Toast.makeText(RegisterActivity.this, "register", Toast.LENGTH_LONG).show();
                                                        Toast.makeText(RegisterActivity.this, "please verify", Toast.LENGTH_LONG).show();

                                                    }
                                                    else{

                                                        String a = task.getException().getMessage();
                                                        Toast.makeText(RegisterActivity.this,"error:"+a,Toast.LENGTH_LONG).show();
                                                    }


                                                }
                                            });

                                        }
                                        else{

                                            //email is already registered
                                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                                                Toast.makeText(RegisterActivity.this, "error", Toast.LENGTH_LONG).show();
                                            }
                                            else
                                            {
                                                String a = task.getException().getMessage();
                                                Toast.makeText(RegisterActivity.this, a, Toast.LENGTH_LONG).show();

                                            }
                                        }

                                    }
                                });
                            }
                            else {
                                rcpassword.setError("password not matched");
                                rcpassword.requestFocus();
                                return;
                            }
                        }
                    }
                }

            } else {
                remail.setError("not valid email");
                remail.requestFocus();
                return;
            }
        }
    }

}
