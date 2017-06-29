//package com.example.ankit.iitmwidget.Widget;
//
//import android.app.IntentService;
//import android.appwidget.AppWidgetManager;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.support.annotation.Nullable;
//
//import com.example.ankit.iitmwidget.R;
//
///**
// * Created by ANKIT on 27-06-2017.
// */
//
//public class UpdateWidgetList extends IntentService{
//
//    public static final String REFRESH = "android.appwidget.action.COURSE_REFRESH";
//    /**
//     * Creates an IntentService.  Invoked by your subclass's constructor.
//     *
//     * @param name Used to name the worker thread, important only for debugging.
//     */
//    public UpdateWidgetList(String name) {
//        super(name);
//    }
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//
//        if(intent.getAction().equals(WidgetProvider.UPDATE_LIST)) {
//            sendBroadcast(new Intent(REFRESH));
//            Context context = getApplicationContext();
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            ComponentName CourseWidget =
//                    new ComponentName(context, WidgetProvider.class);
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(CourseWidget);
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,
//                    R.id.course_list);
//            appWidgetManager.updateAppWidget(context, appWidgetManager, appWidgetIdsf);
//        }
//    }
//
//}
