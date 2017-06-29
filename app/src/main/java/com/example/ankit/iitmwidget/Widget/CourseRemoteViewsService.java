package com.example.ankit.iitmwidget.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ANKIT on 08-06-2017.
 */

public class CourseRemoteViewsService extends RemoteViewsService {

    public static final String WIDGET_ITEM_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    @Override
    public CourseRemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CourseRemoteViewsFactory(getApplicationContext(), intent);
    }


}
