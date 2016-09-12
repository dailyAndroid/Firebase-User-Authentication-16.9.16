package com.example.hwhong.googlefirebaseauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button register;
    private TextView question;
    private EditText emailET, passwordET;

    private ProgressDialog dialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        register = (Button) findViewById(R.id.registerBut);
        question = (TextView) findViewById(R.id.questionTV);
        emailET = (EditText) findViewById(R.id.twoEmail);
        passwordET = (EditText) findViewById(R.id.twoPassword);

        register.setOnClickListener(this);
        question.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.registerBut:
                registerUser();
                break;
            case R.id.questionTV:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }

    }

    private void registerUser() {

        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please Enter all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.setMessage("Registering User...");
        dialog.show();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Unsuccessful Registration", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
