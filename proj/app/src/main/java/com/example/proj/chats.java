package com.example.proj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class chats extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        mAuth=FirebaseAuth.getInstance();
    }

    public void dologout(View view)
    {
        mAuth.getInstance().signOut();
        Intent tir=new Intent(this,reg.class);
        startActivity(tir);
    }
}
