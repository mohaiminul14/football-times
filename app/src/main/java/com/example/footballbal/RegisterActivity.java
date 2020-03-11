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
import com.google.firebase.auth.FirebaseUser;

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

            String email = remail.getText().toString();
            String password=rpassword.getText().toString();
            String cpassword=rcpassword.getText().toString();

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(cpassword))
            {
                if(password.equals(cpassword))
                {

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful())
                            {
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else
                                {Toast.makeText(RegisterActivity.this, "unsuccess", Toast.LENGTH_SHORT).show();}

                        }
                    });

                }
                else{}

            }

        }
    });
}

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null)
        {

            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
}
