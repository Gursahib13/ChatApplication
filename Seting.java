package com.example.testfreg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Seting extends AppCompatActivity
{
    Button Update;
    EditText Username,status;
    CircleImageView circleImageView;
    private String currentUserID;
    FirebaseAuth mAuth;
    DatabaseReference RootRef;
    private static final int gallerypic=1;
    private StorageReference UserProfileImagesRef;
    private ProgressDialog loadingBar;
    private Toolbar Settingtoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);
        mAuth=FirebaseAuth.getInstance();
        currentUserID=mAuth.getCurrentUser().getUid();
        Update=findViewById(R.id.set_update);
        loadingBar=new ProgressDialog(this);
        Username=findViewById(R.id.set_user_name);
        status=findViewById(R.id.set_profile_status);
        RootRef= FirebaseDatabase.getInstance().getReference();
        UserProfileImagesRef= FirebaseStorage.getInstance().getReference().child("Profile Images");
        circleImageView=findViewById(R.id.profile);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent gallery=new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,gallerypic);
            }
        });
        //Username.setVisibility(View.INVISIBLE);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                doset();

            }
        });
        RetriveUserInfo();
    }

    @Override
    public void onBackPressed() {
        Intent i44=new Intent(Seting.this,chats.class);
        startActivity(i44);
    }

    private void doset()
    {
        String setUserName=Username.getText().toString();
        String setStatus=status.getText().toString();
        if(TextUtils.isEmpty(setUserName))
        {
            Toast.makeText(this,"Please Write Username",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(setStatus))
        {
            Toast.makeText(this,"Please Write Status",Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String,Object> profileMap=new HashMap<>();
            profileMap.put("uid",currentUserID);
            profileMap.put("name",setUserName);
            profileMap.put("status",setStatus);
            RootRef.child("Users").child(currentUserID).updateChildren(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        Intent ichat =new Intent(Seting.this,chats.class);
                        startActivity(ichat);
                        Toast.makeText(Seting.this,"Profile Updated Sucessfully....",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String message=task.getException().toString();
                        Toast.makeText(Seting.this,"Error:"+message,Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(((requestCode == gallerypic) && (resultCode == RESULT_OK) && (data != null)))
        {
            Uri Imageuri=data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK)
            {
                Uri resulturi=result.getUri();
                loadingBar.setTitle("Profile Image");
                loadingBar.setMessage("Please wait while its updating");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();


                final StorageReference filePath=UserProfileImagesRef.child(currentUserID +".jpg");
                UploadTask uploadTask=filePath.putFile(resulturi);
                Task<Uri> urltask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            Uri downloadUri=task.getResult();
                            Toast.makeText(Seting.this,"Sucessfully Upload", Toast.LENGTH_SHORT).show();
                            if(downloadUri!=null)
                            {
                                String downloadurl=downloadUri.toString();
                                RootRef.child("Users").child(currentUserID).child("image").setValue(downloadurl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        loadingBar.dismiss();
                                        if(!task.isSuccessful())
                                        {
                                            String error=task.getException().toString();
                                            Toast.makeText(Seting.this,"Error"+error, Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {

                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(Seting.this,"Error", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    }
                });
                /*filePath.putFile(resulturi).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Seting.this,"Profile Image Uploaded Sucessfully",Toast.LENGTH_SHORT).show();
                       final String downloadurl=task.getResult().getDownloadUrl().toString();
                        RootRef.child("Users").child(currentUserID).child("image").setValue(downloadurl).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Seting.this,"Image saved",Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }
                                else
                                {
                                    String message=task.getException().toString();
                                    Toast.makeText(Seting.this,"Error"+message,Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }

                            }
                        });

                    }
                    else
                    {
                        String message=task.getException().toString();
                        Toast.makeText(Seting.this,"Error"+message,Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    }
                });*/
            }
        }
    }

    private void RetriveUserInfo()
    {
        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image"))))
                {
                    String recieveUsername=dataSnapshot.child("name").getValue().toString();
                    String recievestatus=dataSnapshot.child("status").getValue().toString();
                    String recieveimage=dataSnapshot.child("image").getValue().toString();
                    Username.setText(recieveUsername);
                    status.setText(recievestatus);
                    Picasso.get().load(recieveimage).into(circleImageView);

                }
                else if((dataSnapshot.exists())&&(dataSnapshot.hasChild("name")))
                {
                    String getUsername=dataSnapshot.child("name").getValue().toString();
                    String getstatus=dataSnapshot.child("status").getValue().toString();
                    Username.setText(getUsername);
                    status.setText(getstatus);
                }
                else
                {
                    Username.setVisibility(View.VISIBLE);
                    Toast.makeText(Seting.this,"Please set Up some Information",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
