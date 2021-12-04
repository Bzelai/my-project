package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Signup extends AppCompatActivity {


    private EditText Name,Password,Email;
    private TextView Login;
    private Button BTSsignup;
    private FirebaseAuth firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        BTSsignup = (Button) findViewById(R.id.BTSsignup);
        Name = (EditText) findViewById(R.id.PTSname);
        Password = (EditText) findViewById(R.id.TSpassword);
        Email = (EditText) findViewById(R.id.Temail);
        Login = (TextView) findViewById(R.id.TValreadyhave);
        firebase = FirebaseAuth.getInstance();
        BTSsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validvalue()){//upload data to data base.
                    String UserEmail = Email.getText().toString();
                    String UserPassword = Password.getText().toString();
                    firebase.createUserWithEmailAndPassword(UserEmail,UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NotNull Task<AuthResult> task) {
                            Toast.makeText(Signup.this, "" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            if(task.isSuccessful()){
                                Toast.makeText(Signup.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Signup.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(Signup.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this,MainActivity.class));
            }
        });

    }

    /*private void EmailVerification(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Email sent.");
                        }
                    }
                });
    }*/

    private boolean validvalue(){
        String name = Name.getText().toString();
        String password = Password.getText().toString();
        String email = Email.getText().toString();
        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(this,"Please put all details",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
