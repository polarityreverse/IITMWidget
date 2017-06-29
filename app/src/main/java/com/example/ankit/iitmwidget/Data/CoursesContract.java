package com.example.ankit.iitmwidget.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ANKIT on 10-06-2017.
 */

public final class CoursesContract {

    private CoursesContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.ankit.iitmwidget";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_COURSE = "course";

    public static final class CourseEntry implements BaseColumns

    {
        /* The MIME type of the {@link #CONTENT_URI} for a list of courses.
                */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single course.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE;


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_COURSE);
        public static final String TABLE_NAME = "course";
        public static final String ID = BaseColumns._ID;
        public static final String COLUMN_SLOT_NAME = "slot";
        public static final String COLUMN_COURSE_NAME = "course";
        public static final String COLUMN_COURSE_CREDIT = "credit";

    }
}
