package com.example.proj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    DatabaseReference rootref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        rootref= FirebaseDatabase.getInstance().getReference();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            verifyuser();
            Intent intent=new Intent(MainActivity.this,chats.class);
            startActivity(intent);
            finish();
        }
    }

    private void verifyuser()
    {
      //  String Currentuserid=mAuth.getCurrentUser().getUid();
//        rootref.child("Users").child(Currentuserid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                if(dataSnapshot.child("name").exists())
//                {
//                    Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    //Intent i44=new Intent(MainActivity.this,Seting.class);
//                //    startActivity(i44);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }


    public void dosignup(View view)
    {
        Intent i6=new Intent(this,reg.class);
        startActivity(i6);
    }

    public void dologin(View view)
    {
        Intent i=new Intent(this,login.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
