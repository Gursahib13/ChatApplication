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
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    EditText lemail,lpass;
    Button lgn;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lemail=findViewById(R.id.editText4);
        lpass=findViewById(R.id.editText5);
        lgn=findViewById(R.id.button4);
        progressBar=findViewById(R.id.progressBar);
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        Intent to=new Intent(login.this,register.class);
        startActivity(to);
    }

    public void gotosignup(View view)
    {
        Intent i88=new Intent(login.this,register.class);
        startActivity(i88);
    }

    public void dologin(View view)
    {
        final String uemail=lemail.getText().toString();
        final String upass=lpass.getText().toString();
        if(uemail.isEmpty())
        {
            lemail.setError("Email Needed");
        }
        else if(upass.isEmpty())
        {
            lpass.setError("Password Needed");
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(uemail, upass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(login.this, "Auth Success.",
                                        Toast.LENGTH_SHORT).show();
                                Log.i("TAG", "User " + user.toString());
                                Intent i8 = new Intent(login.this, chats.class);
                                startActivity(i8);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.i("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(login.this, "Auth failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }
    }


}
