package com.example.anacamargos.firebaseapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button signupButton;
    EditText signupName;
    EditText signupMail;
    EditText signupPassword;
    ProgressBar signupProgress;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        signupButton = (Button) findViewById(R.id.signup_button);
        signupName = (EditText) findViewById(R.id.signup_name);
        signupMail = (EditText) findViewById(R.id.signup_mail);
        signupPassword = (EditText) findViewById(R.id.signup_password);
        signupProgress = (ProgressBar) findViewById(R.id.signup_progress);

        signupButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //registrar usuario
        String email = signupMail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();
        String name = signupName.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        /*if (TextUtils.isEmpty(name)) {
            //password is empty
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            return;
        }*/

        //if validations are ok
        signupProgress.setVisibility(View.VISIBLE);
        signupButton.setVisibility(View.INVISIBLE);

        final Intent nova = new Intent(this, MainActivity.class);

        //sign up (registrar usando firebase)
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    signupProgress.setVisibility(View.INVISIBLE);
                    //Toast.makeText(this, "Deu certo", Toast.LENGTH_SHORT).show();
                    startActivity(nova);
                }
            }
        });


    }
}
