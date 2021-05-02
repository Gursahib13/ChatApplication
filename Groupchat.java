package com.example.testfreg;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Groupchat extends AppCompatActivity
{
    private Toolbar mToolbar;
    private ImageButton sendmessagebtn;
    private EditText userMessageinput;
    private ScrollView mScrollView;
    private TextView displaytextmessages;
    private  String currentGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
      //  InitilizeFields();
    }

 /*   private void InitilizeFields()
    {
        mToolbar=findViewById(R.id.group_chat_bar_layout1);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Group Name");
        sendmessagebtn=findViewById(R.id.send_message_button1);
        userMessageinput=findViewById(R.id.input_group_message1);
        displaytextmessages=findViewById(R.id.group_chat_text_display1);
        mScrollView=findViewById(R.id.scroll_view1);
        currentGroupName=getIntent().getExtras().get("groupName").toString();
        Toast.makeText(Groupchat.this,currentGroupName,Toast.LENGTH_SHORT).show();

    }*/
}
