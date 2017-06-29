package com.example.ankit.iitmwidget.Courses;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.ankit.iitmwidget.R;

import static com.example.ankit.iitmwidget.Data.CoursesContract.CourseEntry;

/**
 * Created by ANKIT on 12-06-2017.
 */

public class CourseCursorAdapter extends CursorAdapter {

    public CourseCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView mCourseSlot = (TextView)view.findViewById(R.id.slot);
        TextView mCourseName = (TextView)view.findViewById(R.id.course);
        TextView mCourseCredits = (TextView)view.findViewById(R.id.credits_entered);

        String slot = cursor.getString(cursor.getColumnIndex(CourseEntry.COLUMN_SLOT_NAME));
        String cName = cursor.getString(cursor.getColumnIndex(CourseEntry.COLUMN_COURSE_NAME));
        int cCredits = cursor.getInt(cursor.getColumnIndex(CourseEntry.COLUMN_COURSE_CREDIT));

        mCourseSlot.setText(slot);
        mCourseName.setText(cName);
        mCourseCredits.setText(Integer.toString(cCredits));
    }
}
