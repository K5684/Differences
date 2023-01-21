package com.vyw.differences;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText editTextLoginEmail, editTextLoginPassword;
    private FirebaseAuth authprofile;
    private TextView textViewLoginSign, textViewForgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Differences");
        editTextLoginEmail = findViewById(R.id.editTextLoginEmailAddress);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        textViewForgotpass = findViewById(R.id.textViewforgotpassword);
        textViewLoginSign = findViewById(R.id.textViewLoginSign);
        authprofile = FirebaseAuth.getInstance();


        //Show/Hide password
        ImageView ImageviewHideShow = findViewById(R.id.showhide);
        ImageviewHideShow.setImageResource(R.drawable.ic_hide_pwd);
        ImageviewHideShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextLoginPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    //if Password is visible then hide it
                    editTextLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //Change Icon
                    ImageviewHideShow.setImageResource(R.drawable.ic_hide_pwd);
                } else {
                    editTextLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ImageviewHideShow.setImageResource(R.drawable.ic_show_pwd);
                }

            }
        });

        //Textview signup
        String s = "Dont have an account? Sign Up";
        SpannableString Sign = new SpannableString(s);
        ClickableSpan ClickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        };
        Sign.setSpan(ClickableSpan1, 22, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewLoginSign.setText(Sign);
        textViewLoginSign.setMovementMethod(LinkMovementMethod.getInstance());

        //Textview ForgotPassword
        textViewForgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
            }
        });


        Button buttonLogin = findViewById(R.id.loginbutton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textloginemail = editTextLoginEmail.getText().toString();
                String textlogipassword = editTextLoginPassword.getText().toString();
                if (TextUtils.isEmpty(textloginemail)) {
                    Toast.makeText(Login.this, "Enter your Email", Toast.LENGTH_LONG).show();
                    editTextLoginEmail.setError("Email is required");
                    editTextLoginEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textloginemail).matches()) {
                    Toast.makeText(Login.this, "Re-enter your Email Address", Toast.LENGTH_LONG).show();
                    editTextLoginEmail.setError("Valid Email is required");
                    editTextLoginPassword.requestFocus();
                } else if (TextUtils.isEmpty(textlogipassword)) {
                    Toast.makeText(Login.this, "Enter your password", Toast.LENGTH_LONG).show();
                    editTextLoginPassword.setError("Password is required");
                    editTextLoginPassword.requestFocus();
                } else {
                    loginuser(textloginemail, textlogipassword);
                }

            }
        });
    }

    private void loginuser(String textloginemail, String textlogipassword) {
        authprofile.signInWithEmailAndPassword(textloginemail, textlogipassword).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Logged in", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //To keep user logged in
    @Override
    protected void onStart() {
        super.onStart();
        if (authprofile.getCurrentUser() != null) {
            Toast.makeText(Login.this, "Already Logged In", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(Login.this, "You can Login now", Toast.LENGTH_SHORT).show();
        }
    }
}

