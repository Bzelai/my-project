package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class MainActivity<Textview> extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private TextView Signup;
    private Button Login;
    private int counter = 5;
    private FirebaseAuth firebase;
    private ProgressDialog ProDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = (EditText)findViewById(R.id.PTname);
        Password = (EditText)findViewById(R.id.Ppassword);
        Info = (TextView)findViewById(R.id.TVinfo);
        Login = (Button) findViewById(R.id.BTlogin);
        Signup = (TextView)findViewById((R.id.TVsingup));
        firebase = FirebaseAuth.getInstance();
        ProDialog = new ProgressDialog(this);
        FirebaseUser User = firebase.getCurrentUser();

        if(User != null){
            finish();
            startActivity(new Intent(MainActivity.this,Home.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validvalue(Name.getText().toString(),Password.getText().toString());
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Signup.class));
            }
        });
    }

    private void validvalue(String name, String password){
        ProDialog.setMessage("Just a moment");
        ProDialog.show();
        Task<AuthResult> authResultTask = firebase.signInWithEmailAndPassword(name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    ProDialog.dismiss();
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    ProDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    if (counter == 0) {
                        Info.setText("You’ve reached the maximum login attempts");
                        Login.setEnabled(false);
                    }
                }
            }
        });
    }
}