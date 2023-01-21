package com.vyw.differences;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vyw.differences.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText editTextRegisterName,editTextRegisterEmail,editTextRegisterPassword,editTextRegisterMobile,editTextRegisterConfirmPassowrd;
    String Userid;
    Spinner sp;
    int positionOfSelectedDataFromSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Differences");
        editTextRegisterName=findViewById(R.id.editTextRegisterName);
        editTextRegisterEmail=findViewById(R.id.editTextRegisterEmail);
        editTextRegisterMobile=findViewById(R.id.editTextRegisterMobile);
        editTextRegisterPassword=findViewById(R.id.editTextRegisterPassword);
        editTextRegisterConfirmPassowrd=findViewById(R.id.editTextRegisterConfirmPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);
        TextView textviewRegisterSign = findViewById(R.id.textViewRegisterSignin);
        sp =  findViewById(R.id.spinner);

        //Spinner
        String [] disability = {"Choose a category","Blind","Deaf and dumb"};
        //Convert array to list;
        List<String> disabilitylist = new ArrayList<>
                (Arrays.asList(disability));
        //Intializing array adapter
        ArrayAdapter<String> spinnerArrayAdapter
                = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                disabilitylist
        ){
            @Override
            public boolean isEnabled(int position){
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }
            @Override
            public View getDropDownView(
                    int position, View convertView,
                    @NonNull ViewGroup parent) {

                // Get the item view
                View view = super.getDropDownView(
                        position, convertView, parent);
                TextView textView = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    textView.setTextColor(Color.GRAY);
                }
                else { textView.setTextColor(Color.BLACK); }
                return view;
            }
        };
        //set the dropdown resource
        spinnerArrayAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );
        sp.setAdapter(spinnerArrayAdapter);

        //SignUp link
        String RegisterSignup = "Already have an account? Log in";
        SpannableString ss = new SpannableString(RegisterSignup);
        ClickableSpan clickableSpan1 =  new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(Register.this, Login.class));
            }

        };
        ss.setSpan(clickableSpan1,25,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textviewRegisterSign.setText(ss);
        textviewRegisterSign.setMovementMethod(LinkMovementMethod.getInstance());


        //Button Register
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = editTextRegisterName.getText().toString();
                String textEmail =editTextRegisterEmail.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textPassword = editTextRegisterPassword.getText().toString();
                String textConfirmPassword = editTextRegisterConfirmPassowrd.getText().toString();
                String textdisability = sp.getSelectedItem().toString();
                //conditions
                if (TextUtils.isEmpty(textName)){
                    Toast.makeText(Register.this,"Enter your full name", Toast.LENGTH_LONG).show();
                    editTextRegisterName.setError("Full Name is required");
                    editTextRegisterName.requestFocus();
                }
                else if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(Register.this,"Enter your Email Address", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(Register.this,"Re-enter your Email Address", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Valid Email is required");
                    editTextRegisterEmail.requestFocus();
                }
                else if (textMobile.length()!=10){
                    Toast.makeText(Register.this,"Re-enter your Mobile no.", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Valid Mobile no. is required");
                    editTextRegisterMobile.requestFocus();
                }
                else if (TextUtils.isEmpty(textPassword)){
                    Toast.makeText(Register.this,"Enter your Password", Toast.LENGTH_LONG).show();
                    editTextRegisterPassword.setError("Password is required");
                    editTextRegisterPassword.requestFocus();
                }
                else if (TextUtils.isEmpty(textConfirmPassword)){
                    Toast.makeText(Register.this,"Please confirm your Password", Toast.LENGTH_LONG).show();
                    editTextRegisterConfirmPassowrd.setError("Password Confirmation is required");
                    editTextRegisterConfirmPassowrd.requestFocus();
                }
                else if (!textPassword.equals(textConfirmPassword)){
                    Toast.makeText(Register.this,"Please enter the password again!", Toast.LENGTH_LONG).show();
                    editTextRegisterConfirmPassowrd.setError("Password Confirmation is required");
                    editTextRegisterConfirmPassowrd.requestFocus();
                    editTextRegisterPassword.clearComposingText();//clear the entered password
                    editTextRegisterConfirmPassowrd.clearComposingText();
                }

                //after entering all the required data
                else{
                    registerUser(textEmail,textName,textMobile,textPassword,textdisability);

                }
            }
        });





    }





    //User profile registering to firestore
    private void registerUser(String textEmail, String textName, String textMobile, String textPassword,String textDisability) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore fstore= FirebaseFirestore.getInstance();
        auth.createUserWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Register.this,"Registration Successfull",Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseuser = auth.getCurrentUser();
                    firebaseuser.sendEmailVerification();
                    Userid = auth.getCurrentUser().getUid();
                    DocumentReference documentReference =fstore.collection("Registered Users").document(Userid);
                    //HashMap method to store the data
                    Map<String,Object> user = new HashMap<>();
                    user.put("Name",textName);
                    user.put("Email",textEmail);
                    user.put("Mobile",textMobile);
                    user.put("disability",textDisability);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess:User Profile is created for"+ Userid);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: "+e.toString());
                        }
                    });
                    //Start other activity
                    Intent intent = new Intent(Register.this,Login.class);
                    intent.putExtra("position", positionOfSelectedDataFromSpinner);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }
}

