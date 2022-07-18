package com.example.project_budgeting_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private ProgressBar progressBar3;
    private Button Reset_3;
    private EditText email_val_3;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        Reset_3 = (Button) findViewById(R.id.Reset_3);
        email_val_3 = (EditText) findViewById(R.id.email_address_3);

        auth = FirebaseAuth.getInstance();
        Reset_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }
    public void resetPassword(){
        String email = email_val_3.getText().toString().trim();
        if(email.isEmpty()){
            email_val_3.setError("Email cannot be empty");
            email_val_3.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_val_3.setError("Please provide valid email address");
            email_val_3.requestFocus();
            return;
        }
        progressBar3.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Check your email ",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ForgotPassword.this,"Error occured please try again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}