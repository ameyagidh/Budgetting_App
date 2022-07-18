package com.example.project_budgeting_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private Button LogOut;
    private FirebaseAuth auth;
    private TextView FullName_1;
    private TextView Age_1;
    private TextView EmailAddress_1;
    private String userId_1;
    private FirebaseUser user;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FullName_1 = (TextView)findViewById(R.id.FullName_1);
        EmailAddress_1 = (TextView)findViewById(R.id.EmailAddress_1);
        Age_1 = (TextView)findViewById(R.id.Age_1);

        LogOut = (Button) findViewById(R.id.Logout);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               FirebaseAuth.getInstance().signOut();
               goToActivity();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId_1 = user.getUid();

        reference.child(userId_1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null) {
                    String fullNamee = userProfile.fullName;
                    String emailIdd = userProfile.email;
                    String agee = userProfile.age;

                    FullName_1.setText(fullNamee);
                    EmailAddress_1.setText(emailIdd);
                    Age_1.setText(agee);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ProfileActivity.this,"Database error, please try again",Toast.LENGTH_LONG).show();

            }
        });

    }

    public void goToActivity(){
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
    }
}