package com.example.project_budgeting_app;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText Email_ID;
    private EditText Password;
    private Button Login;
    private Button Forgot_Password;
    private Button Sign_Up;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email_ID = (EditText) findViewById(R.id.EmailAddress);
        Password = (EditText) findViewById(R.id.Password);

        Login = (Button) findViewById(R.id.Login);
        Forgot_Password = (Button) findViewById(R.id.ForgotPassword);
        Sign_Up = (Button) findViewById(R.id.SignUp);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = Email_ID.getText().toString().trim();
                password = Password.getText().toString().trim();


            }
        });

        Forgot_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity();
            }
        });

    }

    private void goToActivity() {
        Intent intent_ = new Intent(this,SignUp.class);
        Log.d("Ameya", "goToActivity: In");
        startActivity(intent_);
        Log.d("Ameya", "goToActivity: =qqq");
    }
}