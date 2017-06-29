package com.example.ankit.iitmwidget.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ankit.iitmwidget.R;

import static com.example.ankit.iitmwidget.Data.CoursesContract.CourseEntry;

/**
 * Created by ANKIT on 08-06-2017.
 */

public class CourseRemoteViewsFactory  implements RemoteViewsService.RemoteViewsFactory{

    private Context context;
    private Intent intent;
    private int widgetId;
    String[] projection = new String[]{CourseEntry.ID, CourseEntry.COLUMN_SLOT_NAME, CourseEntry.COLUMN_COURSE_NAME, CourseEntry.COLUMN_COURSE_CREDIT};
    private Cursor cursor;



    public CourseRemoteViewsFactory(Context context, Intent intent) {
        // Optional constructor implementation.
        // Useful for getting references to the
        // Context of the calling widget
        this.context = context;
        this.intent = intent;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    // Set up any connections / cursors to your data source.
    // Heavy lifting, like downloading data should be
    // deferred to onDataSetChanged()or getViewAt().
    // Taking more than 20 seconds in this call will result
    // in an ANR.
    public void onCreate() {
        cursor = context.getContentResolver().query(CourseEntry.CONTENT_URI,projection,null,null,null);
    }

    // Called when the underlying data collection being displayed is
    // modified. You can use the AppWidgetManager’s
    // notifyAppWidgetViewDataChanged method to trigger this handler.
    @Override
    public void onDataSetChanged() {
        if(cursor != null){
        cursor.close();}
        Log.v("CourseUpdate", "calling data update");
         cursor = context.getContentResolver().query(CourseEntry.CONTENT_URI,projection,null,null,null);
        // TODO Processing when underlying data has changed.
    }

    // Return the number of items in the collection being displayed.
    public int getCount() {
        if (cursor != null)
            return cursor.getCount();
        else
            return 0;}

    // Return true if the unique IDs provided by each item are stable --
    // that is, they don’t change at run time.
    public boolean hasStableIds() {
        return false;
    }
    // Return the unique ID associated with the item at a given index.
    public long getItemId(int index) {

        if (cursor != null)
            return cursor.getLong(cursor.getColumnIndex(CourseEntry.ID));
        else
            return index;
    }
    // The number of different view definitions. Usually 1.
    public int getViewTypeCount() {
        return 1;
    }
    // Optionally specify a “loading” view to display. Return null to
    // use the default.
    public RemoteViews getLoadingView() {
        return null;
    }
    // Create and populate the View to display at the given index.
    public RemoteViews getViewAt(int index) {
        // Create a view to display at the required index.
        cursor.moveToPosition(index);
        String id = cursor.getString(cursor.getColumnIndex(CourseEntry.ID));
        String courseName = cursor.getString(cursor.getColumnIndex(CourseEntry.COLUMN_COURSE_NAME));
        String courseSlot = cursor.getString(cursor.getColumnIndex(CourseEntry.COLUMN_SLOT_NAME));
        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.widget_item_layout);

        rv.setTextViewText(R.id.course_name,courseName);
        rv.setTextViewText(R.id.time_slot, "8:00 - 9:00 AM");
        rv.setTextViewText(R.id.slot, courseSlot);

        // Create an item-specific fill-in Intent that will populate
        // the Pending Intent template created in the App Widget Provider.
//        Intent fillInIntent = new Intent();
//        Uri uri = Uri.withAppendedPath(CourseEntry.CONTENT_URI, id);
//        fillInIntent.setData(uri);
//        rv.setOnClickFillInIntent(R.id.course, fillInIntent);
//
        return rv;
    }
    // Close connections, cursors, or any other persistent state you
    // created in onCreate.
    public void onDestroy() {
        if(cursor != null){
        cursor.close();}
    }

}

