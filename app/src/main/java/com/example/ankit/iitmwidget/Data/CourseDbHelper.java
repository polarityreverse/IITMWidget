package com.example.ankit.iitmwidget.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ankit.iitmwidget.Data.CoursesContract.CourseEntry;

/**
 * Created by ANKIT on 10-06-2017.
 */

public class CourseDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "coursedetail";
    private static final int DATABASE_VERSION = 1;

    public CourseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_COURSE_TABLE = "CREATE TABLE " + CourseEntry.TABLE_NAME + "(" + CourseEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CourseEntry.COLUMN_SLOT_NAME + " TEXT, "
                + CourseEntry.COLUMN_COURSE_NAME + " TEXT, "
                + CourseEntry.COLUMN_COURSE_CREDIT + " INTEGER NOT NULL)";
        db.execSQL(SQL_CREATE_COURSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
