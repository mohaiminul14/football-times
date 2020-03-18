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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private  Button reg;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private Button google;
    private Button forget;
    static final int Google_sign = 123;
    GoogleSignInClient mg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //identyfy
        firebaseAuth=FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();


        email=findViewById(R.id.lgemail);
        password=findViewById(R.id.lgpassword);
        login=findViewById(R.id.lglogin);
        reg=findViewById(R.id.signup);
        google = findViewById(R.id.google);
        forget = findViewById(R.id.forgot);



        //login diya forget password a jabe
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(intent);

            }
        });

        //login korbe

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //email and password tostring a convert hobe
                String emailtext=email.getText().toString();
                String passtext = password.getText().toString();

                // jodi email field and password field empty nah thake
                if (!TextUtils.isEmpty(emailtext) && !TextUtils.isEmpty(passtext))
                {

                    //auth a email and password pathailo
                    firebaseAuth.signInWithEmailAndPassword(emailtext,passtext).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //success ful hoile mainactivity te jabe
                            if (task.isSuccessful())
                            {
                                //aikhane user ase kina and oi user verifyed kina seta check korbe
                                if(user !=null && user.isEmailVerified()) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    //error
                                    Toast.makeText(LoginActivity.this, "not verifyed",Toast.LENGTH_LONG).show();}

                            }

                            else
                            {

                                //error
                                String a = Objects.requireNonNull(task.getException()).getMessage();

                                Toast.makeText(LoginActivity.this, a,Toast.LENGTH_LONG).show();

                            }

                        }
                    });

                }

            }
        });


        //gmail diya login er jonno  client id diya email send kore
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        //googlesignin client
        mg = GoogleSignIn.getClient(this, googleSignInOptions);


        //google button a click korle ja hobe
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //function
                SigninGoogle();
            }
        });


        //signup chap dile register activity a jabo
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }



    //apps open hoyar por a aita check korbe
    @Override
    protected void onStart() {
        super.onStart();

        //user jodi  thake and email jodi verifyed hoy tahole  login a jaito
            if (user!=null && user.isEmailVerified()) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
    }

    //SinginGoogle mathod
    void SigninGoogle() {
        Intent signIntent = mg.getSignInIntent();
        startActivityForResult(signIntent, Google_sign);
    }

    //SinginGoogle mathod er result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //jodi request code  and  googlesing ek hoyaccount theke data nebe
        if (requestCode == Google_sign) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                //account er result
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //account null hoile  function a jaibe
                if (account != null)
                {

                    firebaseAuthWithGoogle(account);

                }

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    //new mathod
    private void firebaseAuthWithGoogle(GoogleSignInAccount account)
    {
        Log.d("Tag","firebaseAuthWithGoogle:"+account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this,task ->{
            if (task.isSuccessful())
            {

                Log.d("tag", "Signin successfull");
                Toast.makeText(this, "Google Sing in", Toast.LENGTH_SHORT).show();
                FirebaseUser user = firebaseAuth.getCurrentUser();
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


}
