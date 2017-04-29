package com.android.hood.thomashare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Navigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    public void loginPage (View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void SignUpPage (View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void addInterest (View view){
        Intent intent = new Intent(this, AddInterest.class);
        startActivity(intent);
    }

    public void uploadFile (View view){
        Intent intent = new Intent(this, Upload.class);
        startActivity(intent);
    }

    public void explorePage (View view){
        Intent intent = new Intent(this, Explore.class);
        startActivity(intent);
    }

    public void discoverPage (View view){
        Intent intent = new Intent(this, Discover.class);
        startActivity(intent);
    }
}
