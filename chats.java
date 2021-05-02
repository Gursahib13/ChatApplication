package com.example.testfreg;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chats extends AppCompatActivity
{
    Toolbar mToolbar;
    ViewPager viewPager;
    TabLayout tablayout;
    TabsAcessor tabsAcessor;
    DatabaseReference reference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        mToolbar=findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("SAFECHAT");
        viewPager=findViewById(R.id.main_tabs_page);
        tabsAcessor=new TabsAcessor(getSupportFragmentManager());
        viewPager.setAdapter(tabsAcessor);
        reference= FirebaseDatabase.getInstance().getReference();
        tablayout=findViewById(R.id.main_tabs);
        tablayout.setupWithViewPager(viewPager);
        mAuth=FirebaseAuth.getInstance();
    }
    public void dologout(View view)
    {
        mAuth.signOut();
        Intent i44=new Intent(chats.this,register.class);
        startActivity(i44);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.user_logout)
         {
             mAuth.signOut();
             Intent i44=new Intent(chats.this,register.class);
             startActivity(i44);
         }
        if(item.getItemId()==R.id.create_group)
        {
            creategroup();
        }
        if(item.getItemId()==R.id.user_setting)
        {
            Intent i4=new Intent(chats.this,Seting.class);
            startActivity(i4);
        }
        if(item.getItemId()==R.id.find_friend)
        {
            Intent i5=new Intent(chats.this,findfriends.class);
            startActivity(i5);
           //findfriend();
        }
        return true;
    }

    private void creategroup()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(chats.this,R.style.AlertDialog);
        builder.setTitle("Enter Name for Group :");
        final EditText groupNamefield=new EditText(chats.this);
        groupNamefield.setHint("Group Name");
        builder.setView(groupNamefield);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String groupname=groupNamefield.getText().toString();
                if(TextUtils.isEmpty(groupname))
                {
                    Toast.makeText(chats.this,"Write Group name",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    CreateNewGroup(groupname);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void CreateNewGroup(final String groupname)
    {
        reference.child("Groups").child(groupname).setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            Toast.makeText(chats.this,groupname+" is created Sucessfully",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
