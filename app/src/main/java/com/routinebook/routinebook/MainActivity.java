package com.routinebook.routinebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    ImageButton voiceBtn;

    DatabaseHelper myDB;
    Button saveNote, viewNote, viewRoutine;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        voiceBtn = findViewById(R.id.voiceButton);
        editText = findViewById(R.id.editText);
        saveNote = findViewById(R.id.save);
        viewNote = findViewById(R.id.records);
        viewRoutine = findViewById(R.id.routines);
        myDB = new DatabaseHelper(this);

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                if(editText.length()!= 0){
                    boolean insertData = myDB.addRecord(newEntry);
                    if(insertData){
                        Toast.makeText(MainActivity.this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
                    }
                    editText.setText("");
                }else{
                    Toast.makeText(MainActivity.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }
            }
        });

        viewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordDataActivity.class);
                startActivity(intent);
            }
        });

        viewRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, RoutineActivity.class);
                startActivity(intent1);
            }
        });

        voiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }

    public void speak() {
        Intent intent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent2.putExtra(RecognizerIntent.EXTRA_PROMPT, "What do you what to record?");

        try {
            startActivityForResult(intent2, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
//            Toast.makeText(this, "Error?", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editText.setText(result.get(0));
                }
            }
        }

    }

}
