package com.vyw.differences;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class UserProfile extends AppCompatActivity {
    private TextView Name,Email,Mobile,Disability;
    private FirebaseAuth auth;
    private FirebaseFirestore fstore;
    private String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setTitle("Differences");
        //Binding Views
        Name=findViewById(R.id.textView_show_full_name);
        Email=findViewById(R.id.textView_show_email);
        Mobile=findViewById(R.id.textView_show_mob);
        Disability=findViewById(R.id.textView_show_disability);
        auth= FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        UserID = auth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("Registered Users").document(UserID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Name.setText(value.getString("Name"));
                Email.setText(value.getString("Email"));
                Mobile.setText(value.getString("Mobile"));
                Disability.setText(value.getString("disability"));
            }
        });



    }
}