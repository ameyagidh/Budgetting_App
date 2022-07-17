package com.example.project_budgeting_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText Full_Name;
    private EditText Age;
    private EditText EmailAddress;
    private EditText Password;

    private Button SignUp_;
    private Button Login_;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Full_Name = (EditText) findViewById(R.id.Name);;
        Age = (EditText) findViewById(R.id.Age);;
        EmailAddress = (EditText) findViewById(R.id.emailAddress);
        Password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();


        SignUp_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Full_Name_val;
                String Age_Val;
                String email;
                String password;

                Full_Name_val = Full_Name.getText().toString().trim();
                Age_Val = Age.getText().toString().trim();
                email = EmailAddress.getText().toString().trim();
                password = Password.getText().toString().trim();

                if(Full_Name_val.isEmpty()){
                    Full_Name.setError("Full Name Cannot be Empty");
                    Full_Name.requestFocus();
                    return;
                }

                if(Age_Val.isEmpty()){
                    Age.setError("Age Cannot be Empty");
                    Age.requestFocus();
                    return;
                }

                if(email.isEmpty()){
                    EmailAddress.setError("Email Address Cannot be Empty");
                   EmailAddress.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    EmailAddress.setError("Please provide valid email address");
                    EmailAddress.requestFocus();
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
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user = new User(Full_Name_val,Age_Val,email);


                                    FirebaseDatabase.getInstance().getReference("users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(SignUp.this,"User registered successfully",Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }
                                                    else{
                                                        Toast.makeText(SignUp.this,"Failed to register user please try again",Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }

                                                }
                                            });
                                }
                                else{
                                    Toast.makeText(SignUp.this,"Failed to register user please try again",Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

            }
        });

        Login_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity();
            }
        });
    }
    private void goToActivity() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}