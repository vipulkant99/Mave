package com.example.mave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity
{
    private EditText Email, Mobile, Password;
    private Button SubmitBtn;
    private TextView Txtlogin,TV1;
    private ProgressDialog loadingBar;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_registration);

        SubmitBtn = (Button) findViewById (R.id.btnsubmit);
        Email = (EditText) findViewById (R.id.edtEmail);
        Mobile = (EditText) findViewById (R.id.edtMobile);
        Password = (EditText) findViewById (R.id.edtPassword);
        Txtlogin = (TextView) findViewById (R.id.txtlogin);
        TV1 = (TextView) findViewById (R.id.tv1);
        loadingBar = new  ProgressDialog(this);
        fAuth=FirebaseAuth.getInstance ();



        SubmitBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                SubmitButton();
            }
        });

    }

    private void SubmitButton() {
        String email = Email.getText ().toString ();
        String mobile = Mobile.getText ().toString ();
        String password = Password.getText ().toString ();

        if (TextUtils.isEmpty (email)) {
            Toast.makeText (this, "Please write your email", Toast.LENGTH_SHORT).show ();
        }
        else if (TextUtils.isEmpty (mobile)) {
            Toast.makeText (this, "Please write your mobile number", Toast.LENGTH_SHORT).show ();
        }
        else if (TextUtils.isEmpty (password)) {
            Toast.makeText (this, "Please write your password", Toast.LENGTH_SHORT).show ();
        }
        else {
            loadingBar.setTitle (("Create Account"));
            loadingBar.setMessage ("Please wait while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside (false);
            loadingBar.show ();

            ValidatephoneNumber(email,mobile,password);

            fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                Intent intent = new Intent (RegistrationActivity.this,LoginActivity.class);
                                startActivity (intent);

                            } else {
                                loadingBar.dismiss ();
                                Toast.makeText (RegistrationActivity.this,"Network Error",Toast.LENGTH_SHORT).show ();
                            }

                            // ...
                        }
                    });
        }
    }

    private void ValidatephoneNumber(final String email, final String mobile, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance ().getReference ();
        RootRef.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ( !(dataSnapshot.child ("Users").child (mobile).exists ()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<> ();
                    userdataMap.put ("mobile",mobile);
                    userdataMap.put ("email",email);
                    userdataMap.put ("password",password);

                    RootRef.child ("Users").child (mobile).updateChildren (userdataMap)
                            .addOnCompleteListener (new OnCompleteListener<Void> () {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful ())
                                    {
                                        Toast.makeText (RegistrationActivity.this,"your account has been created",Toast.LENGTH_SHORT).show ();
                                        loadingBar.dismiss ();

                                        Intent intent = new Intent (RegistrationActivity.this,LoginActivity.class);
                                        startActivity (intent);

                                    }
                                    else
                                    {
                                        loadingBar.dismiss ();
                                        Toast.makeText (RegistrationActivity.this,"Network Error",Toast.LENGTH_SHORT).show ();
                                    }
                                }
                            });

                }
                else
                {
                    Toast.makeText (RegistrationActivity.this,"This " + mobile + "already exist.",Toast.LENGTH_SHORT).show ();
                    loadingBar.dismiss ();
                    Toast.makeText (RegistrationActivity.this,"please try again using another mobile number",Toast.LENGTH_SHORT).show ();

                    Intent intent = new Intent (RegistrationActivity.this,MainActivity.class);
                    startActivity (intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}