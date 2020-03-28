package com.routinebook.routinebook;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RoutineActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    Button btnChart, btnSleep, btnStudy, btnWork, btnDine, btnWorkout, btnGame,
            btnNap, btnSocial, btnGroom, btnRelax, btnChore, btnDrive;
    TextView currentStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_layout);

        myDB = new DatabaseHelper(this);
        btnChart = findViewById(R.id.btnChart);
        btnSleep = (Button) findViewById(R.id.btnSleep);
        btnStudy = (Button) findViewById(R.id.btnStudy);
        btnWork = (Button) findViewById(R.id.btnWork);
        btnDine = (Button) findViewById(R.id.btnDine);
        btnWorkout = (Button) findViewById(R.id.btnWorkout);
        btnGame = (Button) findViewById(R.id.btnGame);
        btnNap = (Button) findViewById(R.id.btnNap);
        btnSocial = (Button) findViewById(R.id.btnSocial);
        btnGroom = (Button) findViewById(R.id.btnGroom);
        btnRelax = (Button) findViewById(R.id.btnRelax);
        btnChore = (Button) findViewById(R.id.btnChore);
        btnDrive = (Button) findViewById(R.id.btnDrive);

        currentStatus = (TextView) findViewById(R.id.currentStatus);

        Cursor statusFromData = myDB.getTimeData();
        if (statusFromData.moveToNext()) {
            currentStatus.setText(statusFromData.getString(1));
        }

        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = currentStatus.getText().toString();
                Log.d("hi", temp);
                if (!temp.equals("N/A") && temp.length() > 1)  accTime(temp);
                Intent intent1 = new Intent(RoutineActivity.this, RoutineDataActivity.class);
                startActivity(intent1);
            }
        });

        btnSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("internet");
            }
        });

        btnStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("study");
            }
        });

        btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("work");
            }
        });

        btnDine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("dine");
            }
        });

        btnWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("workout");
            }
        });

        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("game");
            }
        });

        btnNap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("shop");
            }
        });

        btnSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("outdoor");
            }
        });

        btnGroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("groom");
            }
        });

        btnRelax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("relax");
            }
        });

        btnChore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("chore");
            }
        });

        btnDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accTime("drive");
            }
        });
    }

    public void accTime(String type) {
        currentStatus.setText(type);

        //start
        Cursor data = myDB.getTimeData();

        if (!data.moveToNext()) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            myDB.addNewStatus(type, simpleDateFormat.format(calendar.getTime()));
        } else {
            String preTime = "";
            preTime = data.getString(2);
            String preType = "";
            preType = data.getString(1);

            // add to table 2
            try {
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date previous = format.parse(preTime);
                Date current = new Date();

                long mills = current.getTime() - previous.getTime();
                long ms = Math.abs(mills);
                int hour = (int) (ms/(1000 * 60 * 60));
                int min = (int) (ms/(1000*60)) % 60;
                int sec = (int) (ms / 1000) % 60;
                myDB.updateTime(type,format.format(current));

                // get routine data
                Cursor routineData = myDB.getRoutineData();
                int accTime = 0;
                while(routineData.moveToNext()) {
                    if (routineData.getString(0).equals(preType)) {
                        accTime = Integer.parseInt(routineData.getString(1));
                    }
                }
                int inputTime = hour * 60 * 60 + min * 60 + sec;
                if (accTime != 0) {
                    myDB.addRoutine(preType, inputTime + accTime);
                } else {
                    myDB.addRoutine(preType, inputTime);
                }
                Toast.makeText(RoutineActivity.this, "Routine recorded", Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}
