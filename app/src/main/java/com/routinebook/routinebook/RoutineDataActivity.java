package com.routinebook.routinebook;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RoutineDataActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    Button deleteRoutine;
    ListView mRoutineView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_chat_layout);

        myDB = new DatabaseHelper(this);
        mRoutineView = (ListView) findViewById(R.id.routineView);
        deleteRoutine = (Button) findViewById(R.id.clearRoutine);

        deleteRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteAllRoutine();
                finish();
                startActivity(getIntent());
            }
        });

        populateRecordView();
    }

    private void populateRecordView() {
        Cursor data = myDB.getRoutineData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {

            int temp = Integer.parseInt(data.getString(1));
            int sec = temp % 60;
            int min = temp / 60;


            String str = data.getString(0) + " : " + min + " min " + sec + " sec" ;
            listData.add(str);
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mRoutineView.setAdapter(adapter);
    }
}

