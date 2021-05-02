package com.example.testfreg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Profile extends AppCompatActivity
{
    String reciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        reciver=getIntent().getExtras().get("visit").toString();
        Toast.makeText(Profile.this,"Welcome"+reciver,Toast.LENGTH_SHORT).show();
    }
}
