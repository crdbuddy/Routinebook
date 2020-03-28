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

public class RecordDataActivity extends AppCompatActivity {

    //    private static final String TAG = "ListDataActivity";
    DatabaseHelper myDB;
    Button deleteNode;
    ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_layout);

        myDB = new DatabaseHelper(this);
        mListView = (ListView) findViewById(R.id.listView);
        deleteNode = (Button) findViewById(R.id.clearNote);

        deleteNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteAllNote();
                finish();
                startActivity(getIntent());
            }
        });

        populateRecordView();
    }

    private void populateRecordView() {
        Cursor data = myDB.getRecordData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            listData.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
    }
}

