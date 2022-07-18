package com.example.project_budgeting_app;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

                if(email.isEmpty()){
                    Email_ID.setError("Email cannot be empty");
                    Email_ID.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Email_ID.setError("Please provide valid email address");
                    Email_ID.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    Password.setError("Password Cannot be Empty");
                    Password.requestFocus();
                    return;
                }

                if(password.length() < 6){
                    Password.setError("Password Cannot be of length less than 6");
                    Password.requestFocus();
                    return;
                }
                
                progressBar.setVisibility(View.VISIBLE);
                
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // user dashboard
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if(user.isEmailVerified()){
                                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                            }else{
                                Toast.makeText(MainActivity.this,"Check your credientials and please try again",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Failed to Login user please try again",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        Forgot_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_ = new Intent(MainActivity.this,ForgotPassword.class);
                startActivity(i_);
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