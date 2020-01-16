package com.example.mave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mave.Home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {


    private EditText InputEmail, InputPassword;
    private Button LoginButton;
    private ProgressDialog progressDialog;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.login_activity);

        progressDialog = new ProgressDialog (this);

        LoginButton = (Button) findViewById (R.id.btnlogin);
        InputEmail = (EditText) findViewById (R.id.edtmail);
        InputPassword = (EditText) findViewById (R.id.edtpassword);

        mAuth = FirebaseAuth.getInstance();

        LoginButton.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View view)
            {
                LoginUser ();
            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser!=null){
           SendUserToHomeActivity();
        }
    }

    private void SendUserToHomeActivity() {
        Intent mainIntent=new Intent(LoginActivity.this, HomeActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }



    private void LoginUser()
    {

        final String email = InputEmail.getText ().toString ();
        final String password = InputPassword.getText ().toString ();

        if (TextUtils.isEmpty (email))
        {
            Toast.makeText (LoginActivity.this, "please write your email .", Toast.LENGTH_SHORT).show ();
        }
        if (TextUtils.isEmpty (password))
        {
            Toast.makeText (LoginActivity.this, "please write your password.", Toast.LENGTH_SHORT).show ();
        }
        mAuth.signInWithEmailAndPassword (email, password)
                .addOnCompleteListener (LoginActivity.this, new OnCompleteListener<AuthResult> ()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()) {
                            Toast.makeText (LoginActivity.this, "logged in successfully", Toast.LENGTH_LONG).show ();
                            progressDialog.dismiss ();

                            Intent intent = new Intent (LoginActivity.this, HomeActivity.class);
                            startActivity (intent);

                        } else {
                            Toast.makeText (LoginActivity.this, "login in failed", Toast.LENGTH_LONG).show ();

                        }

                        // ...
                    }
                });

    }
}

