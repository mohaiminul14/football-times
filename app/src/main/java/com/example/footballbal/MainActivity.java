package com.example.footballbal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar maintoolbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

        maintoolbar=findViewById(R.id.mainToolbar);
        setSupportActionBar(maintoolbar);
        getSupportActionBar().setTitle("");

    }





    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser =  mAuth.getCurrentUser();
        if (firebaseUser!=null && firebaseUser.isEmailVerified())
        {

        }
        else
        {

            Intent intent =new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.nev_log,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logout:
                mAuth.signOut();
                Intent intent =new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                return  true;

            default:
                return false;
        }

    }
}


