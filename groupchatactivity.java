package com.example.testfreg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class groupchatactivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageButton imageButton;
    private EditText editText;
    private ScrollView mScrollView;
    private TextView textView;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef,fGroupname,groupmessage;
    private String currentGroupname,currentuserid,currentusername,currentDate,currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchatactivity);
        currentGroupname=getIntent().getExtras().get("groupName").toString();
        Toast.makeText(groupchatactivity.this,currentGroupname+" Pressed",Toast.LENGTH_SHORT).show();
        mAuth=FirebaseAuth.getInstance();
        currentuserid=mAuth.getCurrentUser().getUid();
        RootRef= FirebaseDatabase.getInstance().getReference().child("Users");
        fGroupname= FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroupname);

        mToolbar=findViewById(R.id.group_chat);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(currentGroupname);
        imageButton=findViewById(R.id.send_button);
        editText=findViewById(R.id.input_message);
        mScrollView=findViewById(R.id.scrollview);
        textView=findViewById(R.id.group_chat_text_display);
        getuserinfo();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Savemessageinfotodatabase();
                editText.setText("");
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        fGroupname.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                if(dataSnapshot.exists())
                {
                    DisplayMessages(dataSnapshot);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DisplayMessages(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void DisplayMessages(DataSnapshot dataSnapshot)
    {
        Iterator iterator=dataSnapshot.getChildren().iterator();
        while(iterator.hasNext())
        {
            String chatdate=(String) ((DataSnapshot)iterator.next()).getValue();
            String message=(String) ((DataSnapshot)iterator.next()).getValue();
            String name=(String) ((DataSnapshot)iterator.next()).getValue();
            String time=(String) ((DataSnapshot)iterator.next()).getValue();
            textView.append(name+":\n"+message+"\n"+time+"   "+chatdate+"\n\n\n");
            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    private void Savemessageinfotodatabase()
    {
        String message=editText.getText().toString();
        String messagekey=fGroupname.push().getKey();
        if(TextUtils.isEmpty(message))
        {
            Toast.makeText(groupchatactivity.this,"Please Write Something",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Calendar ccalfordate=Calendar.getInstance();
            SimpleDateFormat currSimpleDateFormat=new SimpleDateFormat("MMM dd, yyyy");
            currentDate=currSimpleDateFormat.format(ccalfordate.getTime());

            Calendar ccalfortime=Calendar.getInstance();
            SimpleDateFormat currentTiming=new SimpleDateFormat("hh:mm a");
            currentTime=currentTiming.format(ccalfortime.getTime());
            HashMap<String,Object> groupmessagekey=new HashMap<>();
            fGroupname.updateChildren(groupmessagekey);
            groupmessage=fGroupname.child(messagekey);
            HashMap<String,Object> messageInfo=new HashMap<>();
            messageInfo.put("name",currentusername);
            messageInfo.put("message",message);
            messageInfo.put("date",currentDate);
            messageInfo.put("time",currentTime);
            groupmessage.updateChildren(messageInfo);
        }
    }

    private void getuserinfo()
    {
        RootRef.child(currentuserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    currentusername=dataSnapshot.child("name").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
