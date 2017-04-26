package com.example.nhnent.contentproviderstudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nhnent on 2017. 4. 25..
 */

public class StudentsDBManager {
    static final String DB_STUDENTS = "students.db";
    static final String TABLE_STUDENTS = "Students";
    static final int DB_VERSION = 1;


    Context context = null;

    private static StudentsDBManager dbManager = null;
    private SQLiteDatabase database = null;

    public static StudentsDBManager getInstance(Context context) { //singleton
        if (dbManager == null) {
            dbManager = new StudentsDBManager(context);
        }

        return dbManager;
    }

    public long insert(ContentValues rowValue) {
        return database.insert(TABLE_STUDENTS, null, rowValue);
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return database.query(TABLE_STUDENTS,
                            columns,
                            selection,
                            selectionArgs,
                            groupBy,
                            having,
                            orderBy);
    }

    private StudentsDBManager(Context context) {
        this.context = context;

        database = context.openOrCreateDatabase(DB_STUDENTS, Context.MODE_PRIVATE, null);

        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STUDENTS +
                        "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "number TEXT, " +
                        "name TEXT, " +
                        "department TEXT, " +
                        "grade INTEGER ); ");
    }
}
