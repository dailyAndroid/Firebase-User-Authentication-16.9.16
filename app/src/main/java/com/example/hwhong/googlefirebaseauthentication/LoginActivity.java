package com.example.hwhong.googlefirebaseauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by hwhong on 9/12/16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailET, passwordET;
    private Button button;
    private TextView textView;

    private FirebaseAuth auth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        dialog = new ProgressDialog(this);

        button = (Button) findViewById(R.id.sign_in_But);
        textView = (TextView) findViewById(R.id.registerTextView);
        emailET = (EditText) findViewById(R.id.twoEmail);
        passwordET = (EditText) findViewById(R.id.twoPassword);

        button.setOnClickListener(this);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.sign_in_But:
                loginUser();
                break;
            case R.id.registerTextView:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
        }
    }

    private void loginUser() {

        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please Enter all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.setMessage("Authenticating User ...");
        dialog.show();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();
                if(task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
            }
        });
    }
}
