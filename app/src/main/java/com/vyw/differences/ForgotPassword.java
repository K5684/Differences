package com.vyw.differences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.vyw.differences.R;

public class ForgotPassword extends AppCompatActivity {
    private Button btnResetPass;
    private EditText edittextResetEmail;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setTitle("Differences");
        btnResetPass = findViewById(R.id.button_password_reset);
        edittextResetEmail = findViewById(R.id.editText_password_reset_email);
        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resetemail = edittextResetEmail.getText().toString();

                if (TextUtils.isEmpty(resetemail)){
                    Toast.makeText(ForgotPassword.this,"Enter your Email Address", Toast.LENGTH_LONG).show();
                    edittextResetEmail.setError("Email is required");
                    edittextResetEmail.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(resetemail).matches()){
                    Toast.makeText(ForgotPassword.this,"Re-enter your Email Address", Toast.LENGTH_LONG).show();
                    edittextResetEmail.setError("Valid Email is required");
                    edittextResetEmail.requestFocus();
                }
                else{
                    resetpassword(resetemail);
                }


            }
        });
    }

    private void resetpassword(String resetemail) {
        auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(resetemail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Kindly check your indox for Password Reset Link", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPassword.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(ForgotPassword.this,"Something went wrong", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}