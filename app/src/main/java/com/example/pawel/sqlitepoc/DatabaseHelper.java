package com.example.pawel.sqlitepoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "MotorcyclesDatabase";
    public static final String databaseTable = "Motorcycles";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
        SQLiteDatabase db= this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + databaseTable + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, CAPACITY INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + databaseTable);
        onCreate(db);
    }

    public boolean writeData(String name, int capacity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name", name);
        cv.put("Capacity", capacity);
        if (db.insert(databaseTable, null, cv) == -1) {
            return false;
        } else {
            return true;
        }
    }

    public SQLiteCursor readData() {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteCursor cursor = (SQLiteCursor) db.rawQuery("SELECT * FROM " + databaseTable, null);
        return cursor;
    }

    public boolean updateData(String id, String name, int capacity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ID", id);
        cv.put("Name", name);
        cv.put("Capacity", capacity);
        db.update(databaseTable, cv, "ID = ?", new String[] { id });
        return true;
    }

    public boolean deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.delete(databaseTable, "ID = ?", new String[] { id }) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
