package com.example.ankit.iitmwidget.Widget;

/**
 * Created by ANKIT on 05-06-2017.
 */

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.ankit.iitmwidget.R;

@TargetApi(Build.VERSION_CODES.N)
public class WidgetProvider extends AppWidgetProvider {
    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
    public static String UPDATE_LIST = "android.appwidget.action.APPWIDGET_UPDATE";
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName cn = intent.getComponent();
        //ComponentName cn = new ComponentName(context, WidgetProvider.class);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(cn);
        if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            appWidgetManager.notifyAppWidgetViewDataChanged( appWidgetIds, R.id.course_list);
            onUpdate(context, appWidgetManager,appWidgetIds);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        // Iterate through each widget, creating a RemoteViews object and
        // applying the modified RemoteViews to each widget.
        int [] realTimeWidgetsId = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context,WidgetProvider.class));
        int currentDay = localCalendar.get(Calendar.DATE);
        int currentYear = localCalendar.get(Calendar.YEAR);
        Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();
        for (int id: realTimeWidgetsId) {
            // Create a Remote View
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_provider_layout);
            views.setTextViewText(R.id.todays_date, Integer.toString(currentDay));
            views.setTextViewText(R.id.todays_day,getDayOfTheWeek());
            views.setTextViewText(R.id.current_month, getMonth() + " " + Integer.toString(currentYear));
            Intent itemViewIntent = new Intent(context, CourseRemoteViewsService.class);
            views.setRemoteAdapter(R.id.course_list, itemViewIntent);
            //updateList(context, id);
//            Intent intent = new Intent(context, WidgetProvider.class);
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, realTimeWidgetsId);
//            PendingIntent refresh = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            views.setOnClickPendingIntent(R.id.refresh,refresh );
            appWidgetManager.updateAppWidget(id, views);
        }
    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(context, WidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent( new ComponentName(context, WidgetProvider.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context,
                                          AppWidgetManager appWidgetManager, int appWidgetId,
                                          Bundle newOptions) {
      onUpdate(context, appWidgetManager, null);
        //Do some operation here, once you see that the widget has change its size or position.
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }


    public String getDayOfTheWeek() {
        String day;
        int currentDayOfWeek = localCalendar.get(Calendar.DAY_OF_WEEK);
        switch (currentDayOfWeek) {
            case 1:
                day = "SUN";
                break;
            case 2:
                day = "MON";
                break;
            case 3:
                day = "TUE";
                break;
            case 4:
                day = "WED";
                break;
            case 5:
                day = "THU";
                break;
            case 6:
                day = "FRI";
                break;
            default:
                day = "SAT";
                break;
        }
        return day;
    }

    public String getMonth() {
        String monthString;
        int month = localCalendar.get(Calendar.MONTH);
        switch (month + 1) {
            case 1:
                monthString = "Jan";
                break;
            case 2:
                monthString = "Feb";
                break;
            case 3:
                monthString = "Mar";
                break;
            case 4:
                monthString = "Apr";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "Jun";
                break;
            case 7:
                monthString = "Jul";
                break;
            case 8:
                monthString = "Aug";
                break;
            case 9:
                monthString = "Sep";
                break;
            case 10:
                monthString = "Oct";
                break;
            case 11:
                monthString = "Nov";
                break;
            default:
                monthString = "Dec";
                break;
        }
        return monthString;
    }

}
