package com.example.footballbal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar maintoolbar;
    private FirebaseAuth mAuth;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem news;
    TabItem fixture;
    TabItem highlights;
    TabItem channel;
    TabItem stats;
    TabItem howto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.pageview);
        news=findViewById(R.id.news);
        fixture =findViewById(R.id.fixture);
        highlights = findViewById(R.id.high);
        channel = findViewById(R.id.channel);
        stats= findViewById(R.id.stats);
        howto=findViewById(R.id.how);

        mAuth=FirebaseAuth.getInstance();

        maintoolbar=findViewById(R.id.mainToolbar);
        setSupportActionBar(maintoolbar);
        getSupportActionBar().setTitle("");

        pageAdapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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


