package com.example.ankit.iitmwidget.Courses;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.ankit.iitmwidget.Data.CoursesContract.CourseEntry;
import com.example.ankit.iitmwidget.EditorActivity;
import com.example.ankit.iitmwidget.R;

import static com.example.ankit.iitmwidget.Data.CoursesContract.CourseEntry.CONTENT_URI;

/**
 * Created by ANKIT on 10-06-2017.
 */

public class CourseList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int COURSE_LOADER = 0;
    CourseCursorAdapter mCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list);


        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseList.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        ListView courseListView = (ListView)findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        courseListView.setEmptyView(emptyView);
        mCursorAdapter = new CourseCursorAdapter(this, null);
        courseListView.setAdapter(mCursorAdapter);
        getLoaderManager().initLoader(COURSE_LOADER, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = new String[]{CourseEntry.ID, CourseEntry.COLUMN_SLOT_NAME, CourseEntry.COLUMN_COURSE_NAME, CourseEntry.COLUMN_COURSE_CREDIT};
        return new CursorLoader(this, CONTENT_URI, projection, null, null, null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}


