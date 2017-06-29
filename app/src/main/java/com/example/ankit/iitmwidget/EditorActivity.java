package com.example.ankit.iitmwidget;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ankit.iitmwidget.Data.CoursesContract.CourseEntry;
import com.example.ankit.iitmwidget.Widget.WidgetProvider;


/**
 * Created by ANKIT on 10-06-2017.
 */
public class EditorActivity extends AppCompatActivity {

    /**
     * EditText field to enter the slot for the course
     */
    private EditText mSlotEditText;

    /**
     * EditText field to enter the name of the course
     */
    private EditText mCourseNameEditText;

    /**
     * EditText field to enter the credit of the course
     */
    private EditText mCreditEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mSlotEditText = (EditText) findViewById(R.id.edit_slot);
        mCourseNameEditText = (EditText) findViewById(R.id.edit_course_name);
        mCreditEditText = (EditText) findViewById(R.id.edit_credit);
        Button mDone = (Button) findViewById(R.id.done);
        mDone.setOnClickListener(new  View.OnClickListener(){

            @Override
            public void onClick(View v) {
                insertData();
                refreshWidget();
                finish();

            }
        });

    }

    private void refreshWidget() {
        WidgetProvider.sendRefreshBroadcast(this);
    }

    private void insertData() {

        String courseSlot = mSlotEditText.getText().toString().trim();
        String courseName = mCourseNameEditText.getText().toString().trim();
        String courseCredit = mCreditEditText.getText().toString().trim();


        ContentValues values = new ContentValues();
        values.put(CourseEntry.COLUMN_SLOT_NAME, courseSlot);
        values.put(CourseEntry.COLUMN_COURSE_NAME, courseName);
        values.put(CourseEntry.COLUMN_COURSE_CREDIT, courseCredit);

        Uri newUri = getContentResolver().insert(CourseEntry.CONTENT_URI , values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.editor_insert_course_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_insert_course_successful),
                    Toast.LENGTH_SHORT).show();
        }
        Log.v("EditorActivity", "Row id "+ newUri);
    }

}
