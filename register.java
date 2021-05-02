package com.example.testfreg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    EditText name,email,pass;
    Button reg;
DatabaseReference reference;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.editText);
        email=findViewById(R.id.editText2);
        pass=findViewById(R.id.editText3);
        reg=findViewById(R.id.button3);
        progressBar=findViewById(R.id.progressBar2);
        mAuth=FirebaseAuth.getInstance();
        reference=FirebaseDatabase.getInstance().getReference();

    }

    public void gotolgn(View view)
    {
        Intent i4=new Intent(register.this,login.class);
        startActivity(i4);
    }

    public void sosignup(View view)
    {
        String uname=name.getText().toString();
        final String uemail=email.getText().toString();
        final String upass=pass.getText().toString();
        if(uname.isEmpty())
        {
            name.setError("Username Needed");
        }
        else if(uemail.isEmpty())
        {
            email.setError("Email Needed");
        }
        else if(upass.isEmpty())
        {
            pass.setError("Password Needed");
        }
        else if(upass.length()<6)
        {
            pass.setError("Password Length too short");
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(uemail, upass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String currentUser=mAuth.getCurrentUser().getUid();
                          //      String vla=mAuth.getCurrentUser().getEmail();
                                reference.child("Users").child(currentUser).setValue("");
                                progressBar.setVisibility(View.GONE);

                                // Sign in success, update UI with the signed-in user's information
                                Log.i("TAG", "createUserWithEmail:success");

                                Toast.makeText(register.this, "Success", Toast.LENGTH_LONG).show();
                                Intent i7 = new Intent(register.this, Seting.class);
                                startActivity(i7);
                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                // If sign in fails, display a message to the user.
                                Toast.makeText(register.this, "Failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }

    }


}
