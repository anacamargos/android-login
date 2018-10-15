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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton;
    private ProgressBar loginProgess;
    private EditText loginMail;
    private EditText loginPassword;
    private TextView signUp;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        signUp = (TextView) findViewById(R.id.login_textView);
        loginPassword = (EditText) findViewById(R.id.login_password);
        loginMail = (EditText) findViewById(R.id.login_mail);
        loginButton = (Button) findViewById(R.id.login_button);
        loginProgess = (ProgressBar) findViewById(R.id.login_progress);
        loginProgess.setVisibility(View.INVISIBLE);

        loginButton.setOnClickListener(this);
        signUp.setOnClickListener(this);

        /*
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgess.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.INVISIBLE);
            }
        }); */
    }

    public void loginUser ( ) {


        String email = loginMail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

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

        //if validations are ok
        loginProgess.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);

        final Intent nova = new Intent(this, MainActivity.class);

        //sign in (fazer login usando firebase)
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful() ) {
                    loginProgess.setVisibility(View.INVISIBLE);
                    //start the MainActivity
                    startActivity(nova);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == loginButton) {
            //login
            loginUser();
        }

        if (v == signUp) {
            //open the signUp activity
            startActivity(new Intent(this,SignUpActivity.class));
        }
    }
}
