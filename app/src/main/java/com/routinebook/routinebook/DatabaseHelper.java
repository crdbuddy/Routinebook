package com.routinebook.routinebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DatabaseHelper";

    public static final String TABLE_NAME = "note_data";
    public static final String KEY_NOTE = "note";
    public static final String TABLE_NAME2 = "routine_data";
    public static final String KEY_DURATION = "duration";
    public static final String TABLE_NAME3 = "time_data";
    public static final String KEY_TYPE = "previous_type";
    public static final String KEY_PREVIOUS = "previous_time";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NOTE + " TEXT)";

        String createTable2 = "CREATE TABLE " + TABLE_NAME2 + " (ROUTINE TEXT PRIMARY KEY, " +
                KEY_DURATION + " INTEGER)";

        String createTable3 = "CREATE TABLE " + TABLE_NAME3 + " (TIME TEXT PRIMARY KEY, " +
                KEY_TYPE + " TEXT, " + KEY_PREVIOUS + " TEXT)";

        db.execSQL(createTable);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }

    public boolean addRecord(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NOTE, item);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) return false;
        return true;
    }

    public Cursor getRecordData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public void deleteAllNote () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

    public boolean addRoutine(String item, int min) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ROUTINE", item);
        contentValues.put(KEY_DURATION, min);
        long result = db.replace(TABLE_NAME2, null, contentValues);

        if (result == -1) return false;
        return true;
    }

    public Cursor getRoutineData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null);
        return data;
    }

    public void deleteAllRoutine() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME2);
        db.execSQL("delete from "+ TABLE_NAME3);
        db.close();
    }

    public Cursor getTimeData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME3, null);
        return data;
    }

    public void addNewStatus(String type, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", "time");
        contentValues.put(KEY_TYPE, type);
        contentValues.put(KEY_PREVIOUS, time);
        db.insert(TABLE_NAME3, null, contentValues);
    }

    public void updateTime(String type, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", "time");
        contentValues.put(KEY_TYPE, type);
        contentValues.put(KEY_PREVIOUS, time);

        try {
            db.replace(TABLE_NAME3, null, contentValues);
        } catch (Exception e) {
            db.insert(TABLE_NAME3, null, contentValues);
        }

    }

}
