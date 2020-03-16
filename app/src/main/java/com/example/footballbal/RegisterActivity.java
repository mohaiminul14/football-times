package com.example.footballbal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText remail;
    private EditText rpassword;
    private EditText rcpassword;
    private EditText rgname;
    private Button rreg;
    private Button rlogin;
    private Button google;
    static final int Google_sign = 123;
    GoogleSignInClient mg;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        remail = findViewById(R.id.rgemail);
        rpassword = findViewById(R.id.rgpass);
        rcpassword = findViewById(R.id.rgcpass);
        rgname = findViewById(R.id.rgname);
        rreg = findViewById(R.id.rereg);
        rlogin = findViewById(R.id.rglog);
        google = findViewById(R.id.google);

        rlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder().requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        mg = GoogleSignIn.getClient(this, googleSignInOptions);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SigninGoogle();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {

            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

    }

    private void registercheck() {
        final String email = remail.getText().toString();
        final String pass = rpassword.getText().toString().trim();
        final String cpass = rcpassword.getText().toString();
        final String name = rgname.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty()) {
            remail.setError("Email is required");
            remail.requestFocus();
            return;
        } else {

            if (email.matches(emailPattern)) {
                if (pass.isEmpty()) {
                    rpassword.setError("password is required");
                    rpassword.requestFocus();
                    return;
                } else {

                    if (cpass.isEmpty()) {
                        rcpassword.setError("Confirm password is required");
                        rcpassword.requestFocus();
                        return;
                    } else {
                        if (pass.length() < 6) {
                            rpassword.setError("minumum 6 word password");
                            rpassword.requestFocus();
                            return;
                        } else {

                            if (pass.equals(cpass)) {

                                //email is already registered

                                mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                                        boolean check = !task.getResult().getSignInMethods().isEmpty();
                                        if (!check) {
                                            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {

                                                    if (task.isSuccessful()) {
                                                        //register er por ki korte cai

                                                        //email verification
                                                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    //Toast.makeText(RegisterActivity.this, "register", Toast.LENGTH_LONG).show();
                                                                    Toast.makeText(RegisterActivity.this, "please verify", Toast.LENGTH_LONG).show();

                                                                } else {

                                                                    String a = task.getException().getMessage();
                                                                    Toast.makeText(RegisterActivity.this, "error:" + a, Toast.LENGTH_LONG).show();
                                                                }


                                                            }
                                                        });

                                                    } else {
                                                        //onno kno error dile oita dekhaibe
                                                        String a = task.getException().getMessage();
                                                        Toast.makeText(RegisterActivity.this, a, Toast.LENGTH_LONG).show();

                                                    }

                                                }
                                            });
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "email already exits", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });
                            } else {
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

    void SigninGoogle() {
        Intent signIntent = mg.getSignInIntent();
        startActivityForResult(signIntent, Google_sign);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Google_sign) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);

                if (account != null)
                {

                    firebaseAuthWithGoogle(account);

                }

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        Log.d("Tag","firebaseAuthWithGoogle:"+account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this,task ->{
            if (task.isSuccessful())
            {

                Log.d("tag", "Signin successfull");
                Toast.makeText(this, "Google Sing in", Toast.LENGTH_SHORT).show();
                FirebaseUser user = mAuth.getCurrentUser();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
//                upDateUI(user);


            }
            else
            {
                Toast.makeText(this, "Google sign in faild", Toast.LENGTH_SHORT).show();
//                upDateUI(null);
            }

        });
    }

    private void upDateUI(FirebaseUser user) {
        if (user != null)
        {
//            String name = user.getDisplayName();
//            String email = user.getEmail();

        }
        else
        {

        }
    }
}
