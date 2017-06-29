package com.example.ankit.iitmwidget.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import static com.example.ankit.iitmwidget.Data.CoursesContract.CONTENT_AUTHORITY;
import static com.example.ankit.iitmwidget.Data.CoursesContract.CourseEntry;
import static com.example.ankit.iitmwidget.Data.CoursesContract.PATH_COURSE;

/**
 * Created by ANKIT on 11-06-2017.
 */

public class CourseProvider extends ContentProvider {

    public static final String LOG_TAG = CourseProvider.class.getSimpleName();
    private CourseDbHelper mDbHelper;
    private static final int COURSE = 100;
    private static final int COURSE_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_COURSE, COURSE);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_COURSE + "/#", COURSE_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new CourseDbHelper(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COURSE:
                return CourseEntry.CONTENT_LIST_TYPE;
            case COURSE_ID:
                return CourseEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COURSE:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(CourseEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);}
                return rowsDeleted;
            case COURSE_ID:
                // Delete a single row given by the ID in the URI
                selection = CourseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(CourseEntry.TABLE_NAME, selection, selectionArgs);
                if (rowsDeleted != 0) {
                    getContext().getContentResolver().notifyChange(uri, null);}
                return rowsDeleted;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COURSE:
                return updateCourse(uri, contentValues, selection, selectionArgs);
            case COURSE_ID:
                selection = CourseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateCourse(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateCourse(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(CourseEntry.COLUMN_COURSE_NAME)) {
            String name = values.getAsString(CourseEntry.COLUMN_COURSE_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Course requires a name");
            }
        }


        if (values.containsKey(CourseEntry.COLUMN_COURSE_CREDIT)) {
            Integer credits = values.getAsInteger(CourseEntry.COLUMN_COURSE_CREDIT);
            if (credits <= 0 || credits > 20) {
                throw new IllegalArgumentException("Please enter valid credits");
            }
        }


        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();


        int rowsUpdated = database.update(CourseEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Returns the number of database rows affected by the update statement
        return rowsUpdated;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case COURSE:
                cursor = database.query(CourseEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case COURSE_ID:
                selection = CourseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(CourseEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COURSE:
                return insertCourse(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertCourse(Uri uri, ContentValues values) {

        String name = values.getAsString(CourseEntry.COLUMN_COURSE_NAME);
        int credit = values.getAsInteger(CourseEntry.COLUMN_COURSE_CREDIT);
        if (name == null) {
            throw new IllegalArgumentException("Course requires a name");
        }

        if (credit <= 0 || credit > 20) {
            throw new IllegalArgumentException("Please enter valid credits");
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(CourseEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);

    }

}
