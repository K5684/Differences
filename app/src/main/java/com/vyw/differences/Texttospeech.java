package com.vyw.differences;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Texttospeech extends AppCompatActivity {
    EditText ttsedittext;
    Button btntts;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texttospeech);
        ttsedittext = findViewById(R.id.ttsedittext);
        getSupportActionBar().setTitle("Differences");
        btntts = findViewById(R.id.btntts);

        // create an object textToSpeech and adding features into it
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        // Adding OnClickListener
        btntts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(ttsedittext.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                Toast
                        .makeText(Texttospeech.this, " Successfully Converted " ,
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}